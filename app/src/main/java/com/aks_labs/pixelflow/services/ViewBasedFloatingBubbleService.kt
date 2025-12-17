package com.aks_labs.pixelflow.services

import android.app.Service
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.content.FileProvider
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import com.aks_labs.pixelflow.R
import com.aks_labs.pixelflow.data.models.Folder
import com.aks_labs.pixelflow.receivers.ScreenshotBroadcastReceiver
import com.aks_labs.pixelflow.ui.components.DirectActionExecutor
import com.aks_labs.pixelflow.ui.components.ViewBasedCircularDragZones
import com.aks_labs.pixelflow.ui.components.FloatingActionButtons
import com.aks_labs.pixelflow.utils.BitmapUtils
import com.aks_labs.pixelflow.utils.ScreenUtils
import java.io.File
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * A service that displays a floating bubble with screenshot thumbnails
 * and allows dragging them to different folders.
 * This implementation uses traditional Views instead of Compose.
 */
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.aks_labs.pixelflow.pixelFlowApp

/**
 * A service that displays a floating bubble with screenshot thumbnails
 * and allows dragging them to different folders.
 * This implementation uses traditional Views instead of Compose.
 */
class ViewBasedFloatingBubbleService : Service() {

    companion object {
        private const val TAG = "ViewBasedFloatingBubbleService"

        // Action to start the service from the app
        const val ACTION_START_FROM_APP = "START_FROM_APP"

        // Notification ID for foreground service
        private const val NOTIFICATION_ID = 1001

        // Flag to track if the service is running
        private var isServiceRunning = false

        /**
         * Check if the service is running
         */
        fun isRunning(): Boolean {
            return isServiceRunning
        }
    }

    // Window manager for adding and removing views
    private lateinit var windowManager: WindowManager

    // Coroutine scope for the service
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    // Dynamic color preference
    private var isDynamicColorsEnabled = false

    // Screen dimensions
    private var width = 0
    private var height = 0

    // Views
    private var bubbleView: View? = null
    private var dragZonesView: ViewBasedCircularDragZones? = null
    private var actionButtonsView: FloatingActionButtons? = null

    // Current screenshot
    private var currentScreenshotPath: String? = null
    private var currentScreenshotBitmap: Bitmap? = null

    // Direct action executor
    private lateinit var directActionExecutor: DirectActionExecutor

    /**
     * Gets the current screenshot path.
     */
    fun getCurrentScreenshotPath(): String? {
        return currentScreenshotPath
    }

    /**
     * Clears the current screenshot data.
     */
    fun clearCurrentScreenshot() {
        currentScreenshotPath = null
        currentScreenshotBitmap = null
    }

    // Drag state
    private var isDragging = false
    private var showingDragZones = false
    private var showingActionButtons = false

    // Vibration control
    private var lastVibrationTime = 0L

    // Initial touch position for drag calculations
    private var initialTouchX = 0f
    private var initialTouchY = 0f

    // Initial bubble position for drag calculations
    private var initialX = 0
    private var initialY = 0

    // Folders for drag zones
    private val folders = mutableListOf<Folder>()

    // Handler for UI updates
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service onCreate called")

        // Set the running flag
        isServiceRunning = true

        // Initialize window manager
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // Get screen dimensions
        val displayMetrics = resources.displayMetrics
        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels

        // Initialize direct action executor
        directActionExecutor = DirectActionExecutor(this, this)

        // Initialize folders
        initFolders()

        // Observe dynamic color preference
        observeDynamicColorPreference()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service onStartCommand called with action: ${intent?.action}")

        // Start foreground service with notification
        startForeground(NOTIFICATION_ID, createNotification())

        if (intent?.action == ACTION_START_FROM_APP) {
            Log.d(TAG, "Service starting from app")
            startScreenshotDetection()
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    /**
     * Creates a notification for the foreground service.
     */
    private fun createNotification(): android.app.Notification {
        val channelId = "PixelFlow_Channel"

        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = android.app.NotificationChannel(
                channelId,
                "PixelFlow Service",
                android.app.NotificationManager.IMPORTANCE_LOW
            )
            channel.description = "Used for the screenshot detection service"

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as android.app.NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Create a pending intent for the notification
        val pendingIntent = android.app.PendingIntent.getActivity(
            this,
            0,
            Intent(this, Class.forName("com.aks_labs.pixelflow.MainActivity")),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                android.app.PendingIntent.FLAG_IMMUTABLE or android.app.PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                android.app.PendingIntent.FLAG_UPDATE_CURRENT
            }
        )

        // Build the notification
        return android.app.Notification.Builder(this, channelId)
            .setContentTitle("PixelFlow")
            .setContentText("Detecting screenshots...")
            .setSmallIcon(R.drawable.ic_folder)
            .setContentIntent(pendingIntent)
            .build()
    }

    override fun onDestroy() {
        Log.d(TAG, "Service onDestroy called")

        // Unregister content observer
        if (contentObserver != null) {
            try {
                contentResolver.unregisterContentObserver(contentObserver!!)
                contentObserver = null
                Log.d(TAG, "Content observer unregistered")
            } catch (e: Exception) {
                Log.e(TAG, "Error unregistering content observer", e)
            }
        }

        // Clean up views
        removeBubble()
        hideDragZones()
        hideActionButtons()

        // Reset the running flag
        isServiceRunning = false

        // Reset the running flag
        isServiceRunning = false

        // Cancel coroutine scope
        serviceScope.cancel()

        super.onDestroy()
    }

    private fun observeDynamicColorPreference() {
        val DYNAMIC_COLORS_ENABLED_KEY = booleanPreferencesKey("dynamic_colors_enabled")
        serviceScope.launch {
            application.pixelFlowApp.dataStore.data
                .map { preferences ->
                    preferences[DYNAMIC_COLORS_ENABLED_KEY] ?: false
                }
                .collect { enabled ->
                    isDynamicColorsEnabled = enabled
                    // Update existing drag zones if they exist
                    dragZonesView?.useDynamicColors = enabled
                }
        }
    }

    /**
     * Initialize the folders for drag zones.
     * Loads folders from SharedPrefsManager to ensure custom folders are included.
     */
    private fun initFolders() {
        // Clear existing folders
        folders.clear()

        try {
            // Get the SharedPrefsManager instance
            val sharedPrefsManager = com.aks_labs.pixelflow.data.SharedPrefsManager(this)

            // Initialize default folders if none exist
            sharedPrefsManager.initializeDefaultFolders()

            // Get all folders from SharedPrefsManager
            val allFolders = sharedPrefsManager.foldersValue

            // Convert and add all folders to our list
            allFolders.forEach { simpleFolder ->
                folders.add(Folder(
                    id = simpleFolder.id.toString(),
                    name = simpleFolder.name,
                    iconResId = R.drawable.ic_folder
                ))
            }

            Log.d(TAG, "Loaded ${folders.size} folders from SharedPrefsManager")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading folders from SharedPrefsManager", e)

            // Fallback to default folders if there's an error
            folders.add(Folder("1", "Tricks", R.drawable.ic_folder))
            folders.add(Folder("2", "Quotes", R.drawable.ic_folder))
            folders.add(Folder("3", "Recipes", R.drawable.ic_folder))
            folders.add(Folder("4", "Ideas", R.drawable.ic_folder))
            folders.add(Folder("5", "Other", R.drawable.ic_folder))
        }
    }

    /**
     * Called when a new screenshot is detected.
     */
    private fun onNewScreenshot(screenshotPath: String) {
        Log.d(TAG, "New screenshot detected: $screenshotPath")

        // Get the file name to check if it's already processed
        val fileName = File(screenshotPath).name

        // Skip if already processed
        if (processedScreenshots.contains(fileName)) {
            Log.d(TAG, "Skipping already processed screenshot: $fileName")
            return
        }

        // Check if the file exists and has content
        val file = File(screenshotPath)
        if (file.exists() && file.length() > 0) {
            Log.d(TAG, "Screenshot file exists, size: ${file.length()} bytes")

            // Save the current screenshot path
            currentScreenshotPath = screenshotPath

            // Load the bitmap
            currentScreenshotBitmap = BitmapUtils.loadBitmapFromFile(screenshotPath)

            // Broadcast the screenshot detection event
            val intent = Intent(ScreenshotBroadcastReceiver.ACTION_SCREENSHOT_DETECTED).apply {
                putExtra(ScreenshotBroadcastReceiver.EXTRA_SCREENSHOT_PATH, screenshotPath)
            }
            sendBroadcast(intent)
            Log.d(TAG, "Broadcast sent for screenshot detection: $screenshotPath")

            // Show the floating bubble
            showFloatingBubble(currentScreenshotBitmap)
        } else {
            Log.d(TAG, "Screenshot file is still pending, waiting for it to be ready")

            // Wait for the file to be ready
            waitForScreenshotFile(screenshotPath)
        }
    }

    /**
     * Waits for a screenshot file to be ready.
     */
    private fun waitForScreenshotFile(screenshotPath: String) {
        // Get the file name to check if it's already processed
        val fileName = File(screenshotPath).name

        // Skip if already processed
        if (processedScreenshots.contains(fileName)) {
            Log.d(TAG, "Skipping already processed screenshot in waitForScreenshotFile: $fileName")
            return
        }

        Thread {
            var attempts = 0
            val maxAttempts = 10
            var fileReady = false

            while (attempts < maxAttempts && !fileReady) {
                attempts++
                Log.d(TAG, "Waiting for screenshot file to be ready, attempt $attempts")

                try {
                    Thread.sleep(500) // Wait for 500ms

                    // Check again if it's been processed while we were waiting
                    if (processedScreenshots.contains(fileName)) {
                        Log.d(TAG, "Screenshot was processed by another thread while waiting: $fileName")
                        break
                    }

                    val file = File(screenshotPath)
                    if (file.exists() && file.length() > 0) {
                        fileReady = true
                        Log.d(TAG, "Screenshot file is now ready: $screenshotPath")

                        // Update on the main thread
                        handler.post {
                            onNewScreenshot(screenshotPath)
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error waiting for screenshot file", e)
                }
            }

            if (!fileReady) {
                Log.e(TAG, "Screenshot file never became ready after $maxAttempts attempts")
            }
        }.start()
    }

    /**
     * Shows the floating bubble with the screenshot thumbnail.
     */
    private fun showFloatingBubble(bitmap: Bitmap?) {
        Log.d(TAG, "Showing floating bubble for screenshot")

        // Remove any existing bubble
        removeBubble()

        // Create a new bubble view
        val inflater = LayoutInflater.from(this)
        val bubbleLayout = inflater.inflate(R.layout.view_floating_bubble, null) as FrameLayout

        // Get the card view and image view
        val cardView = bubbleLayout.findViewById<CardView>(R.id.bubble_card)
        val imageView = bubbleLayout.findViewById<ImageView>(R.id.bubble_image)

        // Set the screenshot bitmap
        imageView.setImageBitmap(bitmap)

        // Set up window parameters
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= 26)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        // Position the bubble on the right side of the screen
        params.gravity = Gravity.TOP or Gravity.START
        params.x = width - 200
        params.y = height / 3

        // Set up touch listener for drag and drop
        bubbleLayout.setOnTouchListener(BubbleTouchListener())

        // Add the view to the window manager
        windowManager.addView(bubbleLayout, params)

        // Save the view
        bubbleView = bubbleLayout

        Log.d(TAG, "Floating bubble shown successfully")
    }

    /**
     * Removes the floating bubble.
     */
    fun removeBubble() {
        if (bubbleView != null) {
            try {
                windowManager.removeView(bubbleView)
                bubbleView = null
                Log.d(TAG, "Bubble removed")
            } catch (e: Exception) {
                Log.e(TAG, "Error removing bubble", e)
            }
        }
    }

    /**
     * Shows the drag zones.
     */
    private fun showDragZones() {
        try {
            Log.d(TAG, "Showing drag zones - START")

            // Refresh folders from SharedPrefsManager to ensure we have the latest
            refreshFolders()
            Log.d(TAG, "Folders count: ${folders.size}")

            // First, hide any existing drag zones to prevent duplicates
            hideDragZones()

            // Apply the app theme to the context so attributes resolve correctly
            val themedContext = android.view.ContextThemeWrapper(this, R.style.Theme_PixelFlow)

            // Create a new drag zones view
            dragZonesView = ViewBasedCircularDragZones(themedContext).apply {
                setFolders(folders)
                setOnFolderSelectedListener { folderId ->
                    handleScreenshotDrop(folderId)
                }
                setOnActionSelectedListener { actionType ->
                    handleAction(actionType)
                }
            }

            // Set up window parameters for the drag zones
            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                if (Build.VERSION.SDK_INT >= 26)
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                else
                    WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )

            // Ensure we pass the dynamic color setting
            dragZonesView?.useDynamicColors = isDynamicColorsEnabled

            // Add to window manager
            windowManager.addView(dragZonesView, params)

            // Set the flag to indicate drag zones are showing
            showingDragZones = true

            Log.d(TAG, "Drag zones added to window manager")
            Log.d(TAG, "Showing drag zones - COMPLETE")
        } catch (e: Exception) {
            Log.e(TAG, "Error showing drag zones", e)
        }
    }

    /**
     * Refreshes the folders list from SharedPrefsManager.
     * This ensures we always have the latest folders including any custom ones.
     */
    private fun refreshFolders() {
        try {
            // Get the SharedPrefsManager instance
            val sharedPrefsManager = com.aks_labs.pixelflow.data.SharedPrefsManager(this)

            // Get all folders from SharedPrefsManager
            val allFolders = sharedPrefsManager.foldersValue

            // Clear existing folders and add the updated ones
            folders.clear()
            allFolders.forEach { simpleFolder ->
                folders.add(Folder(
                    id = simpleFolder.id.toString(),
                    name = simpleFolder.name,
                    iconResId = R.drawable.ic_folder
                ))
            }

            Log.d(TAG, "Refreshed ${folders.size} folders from SharedPrefsManager")
        } catch (e: Exception) {
            Log.e(TAG, "Error refreshing folders from SharedPrefsManager", e)
        }
    }

    /**
     * Hides the drag zones.
     */
    fun hideDragZones() {
        try {
            Log.d(TAG, "Hiding drag zones - START")
            if (dragZonesView != null) {
                windowManager.removeView(dragZonesView)
                dragZonesView = null
                Log.d(TAG, "Drag zones removed from window manager")
            } else {
                Log.d(TAG, "No drag zones view to remove")
            }

            // Update the flag
            showingDragZones = false
            Log.d(TAG, "Drag zones hidden successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error hiding drag zones", e)
            // Make sure the flag is reset even if there's an error
            showingDragZones = false
        }
    }

    // Keep track of processed screenshots to avoid re-detecting them
    private val processedScreenshots = mutableSetOf<String>()

    /**
     * Handles dropping a screenshot into a folder.
     */
    private fun handleScreenshotDrop(folderId: String) {
        Log.d(TAG, "Screenshot dropped in folder: $folderId")

        // Get the folder
        val folder = folders.find { it.id == folderId }
        if (folder != null && currentScreenshotPath != null) {
            Log.d(TAG, "Moving screenshot to folder: ${folder.name}")

            // Vibrate to provide feedback
            vibrateDevice()

            // Store the current screenshot path to prevent re-detection
            val screenshotPath = currentScreenshotPath!!
            val screenshotName = java.io.File(screenshotPath).name

            // Add to processed screenshots set
            processedScreenshots.add(screenshotName)
            Log.d(TAG, "Added to processed screenshots: $screenshotName")

            // First remove the bubble with animation
            if (bubbleView != null) {
                bubbleView?.animate()
                    ?.alpha(0f)
                    ?.scaleX(0f)
                    ?.scaleY(0f)
                    ?.setDuration(300)
                    ?.withEndAction {
                        // Move the screenshot to the folder
                        moveScreenshotToFolder(screenshotPath, folder)

                        // Remove the bubble and hide drag zones
                        removeBubble()
                        hideDragZones()

                        // Clear the current screenshot path to prevent duplicates
                        currentScreenshotPath = null
                        currentScreenshotBitmap = null
                    }
                    ?.start()
            } else {
                // If no bubble view (unlikely), just process normally
                moveScreenshotToFolder(screenshotPath, folder)
                removeBubble()
                hideDragZones()
                currentScreenshotPath = null
                currentScreenshotBitmap = null
            }
        }
    }

    /**
     * Moves a screenshot to a folder.
     */
    private fun moveScreenshotToFolder(screenshotPath: String, folder: Folder) {
        try {
            // Create the main PixelFlow directory in Pictures
            val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val pixelFlowDir = java.io.File(picturesDir, "PixelFlow")
            if (!pixelFlowDir.exists()) {
                val created = pixelFlowDir.mkdirs()
                Log.d(TAG, "Created PixelFlow directory: $created")
            }

            // Create the folder directory if it doesn't exist
            val folderDir = java.io.File(pixelFlowDir, folder.name)
            if (!folderDir.exists()) {
                val created = folderDir.mkdirs()
                Log.d(TAG, "Created folder directory ${folder.name}: $created")
            }

            // Get the source file
            val sourceFile = java.io.File(screenshotPath)
            if (!sourceFile.exists()) {
                Log.e(TAG, "Source file does not exist: $screenshotPath")
                return
            }

            // Create the destination file
            val destFile = java.io.File(folderDir, sourceFile.name)

            // Use FileInputStream and FileOutputStream for more reliable file copying
            val inputStream = java.io.FileInputStream(sourceFile)
            val outputStream = java.io.FileOutputStream(destFile)
            val buffer = ByteArray(1024)
            var length: Int

            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()

            Log.d(TAG, "Screenshot copied to folder: ${folder.name} at ${destFile.absolutePath}")

            // Show a toast notification
            ScreenUtils.showToast(this, "Screenshot saved to ${folder.name}")

            // Scan the file so it appears in the gallery
            MediaScannerConnection.scanFile(
                this,
                arrayOf(destFile.absolutePath),
                arrayOf("image/jpeg"),
                null
            )

            // Delete the original file if it's in the Screenshots folder
            // This makes it a move operation rather than a copy
            if (sourceFile.parent?.contains("Screenshots") == true) {
                if (sourceFile.delete()) {
                    Log.d(TAG, "Original screenshot deleted successfully")
                    // Notify the media scanner that the file has been deleted
                    MediaScannerConnection.scanFile(
                        this,
                        arrayOf(sourceFile.absolutePath),
                        null,
                        null
                    )

                    // Broadcast the screenshot moved event
                    val intent = Intent(ScreenshotBroadcastReceiver.ACTION_SCREENSHOT_MOVED).apply {
                        putExtra(ScreenshotBroadcastReceiver.EXTRA_SCREENSHOT_PATH, destFile.absolutePath)
                        putExtra(ScreenshotBroadcastReceiver.EXTRA_FOLDER_ID, folder.id.toLongOrNull() ?: -1L)
                    }
                    sendBroadcast(intent)
                    Log.d(TAG, "Broadcast sent for screenshot moved: ${destFile.absolutePath} to folder: ${folder.name}")
                } else {
                    Log.e(TAG, "Failed to delete original screenshot")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error copying screenshot to folder (Ask Gemini)", e)
            ScreenUtils.showToast(this, "Error saving screenshot")
        }
    }

    /**
     * Checks if the bubble is over any drag zones and returns the folder ID.
     */
    private fun checkDroppedOnZone(x: Float, y: Float): String? {
        if (dragZonesView == null) return null

        return dragZonesView?.getFolderIdAt(x, y)
    }

    /**
     * Checks if the bubble is over any action button and returns the action type.
     * Returns -1 if not over any action button.
     */
    private fun checkDroppedOnActionButton(x: Float, y: Float): Int {
        if (actionButtonsView == null) return -1

        // First try using the action buttons view's built-in method
        val actionType = actionButtonsView?.getActionAt(x, y) ?: -1
        if (actionType != -1) {
            Log.d(TAG, "Found action $actionType using getActionAt")
            return actionType
        }

        // If that fails, try a more direct approach with generous thresholds
        val deleteButtonPosition = calculateDeleteButtonPosition()
        val shareButtonPosition = calculateShareButtonPosition()
        val trashButtonPosition = calculateTrashButtonPosition()

        val deleteButtonDistance = calculateDistance(x, y, deleteButtonPosition.first, deleteButtonPosition.second)
        val shareButtonDistance = calculateDistance(x, y, shareButtonPosition.first, shareButtonPosition.second)
        val trashButtonDistance = calculateDistance(x, y, trashButtonPosition.first, trashButtonPosition.second)

        Log.d(TAG, "Delete button distance: $deleteButtonDistance")
        Log.d(TAG, "Share button distance: $shareButtonDistance")
        Log.d(TAG, "Trash button distance: $trashButtonDistance")

        // Find the closest button with a very generous threshold
        val minDistance = minOf(deleteButtonDistance, shareButtonDistance, trashButtonDistance)
        val threshold = 300f // Extremely generous threshold

        if (minDistance < threshold) {
            return when (minDistance) {
                deleteButtonDistance -> {
                    Log.d(TAG, "Closest to DELETE button")
                    FloatingActionButtons.ACTION_DELETE
                }
                shareButtonDistance -> {
                    Log.d(TAG, "Closest to SHARE button")
                    FloatingActionButtons.ACTION_SHARE
                }
                trashButtonDistance -> {
                    Log.d(TAG, "Closest to TRASH button")
                    FloatingActionButtons.ACTION_TRASH
                }
                else -> -1
            }
        }

        return -1
    }

    // Content observer for screenshot detection
    private var contentObserver: ContentObserver? = null

    /**
     * Starts the screenshot detection.
     */
    private fun startScreenshotDetection() {
        Log.d(TAG, "Starting screenshot detection")

        // Check if already observing
        if (contentObserver != null) {
            Log.d(TAG, "Already observing for screenshots")
            return
        }

        // Create and register the content observer
        contentObserver = createScreenshotContentObserver()
        val contentResolver = contentResolver
        contentResolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            true,
            contentObserver!!
        )
        Log.d(TAG, "Content observer registered successfully")

        // Check for recent screenshots
        checkForRecentScreenshots()
    }

    /**
     * Creates a content observer for detecting screenshots.
     */
    private fun createScreenshotContentObserver(): ContentObserver {
        return object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean, uri: Uri?) {
                super.onChange(selfChange, uri)

                if (uri != null) {
                    Log.d(TAG, "Content change detected: $uri")
                    checkIfScreenshot(uri)
                }
            }
        }
    }

    /**
     * Checks if a URI points to a screenshot.
     */
    private fun checkIfScreenshot(uri: Uri) {
        Thread {
            try {
                Log.d(TAG, "Checking if URI is a screenshot: $uri")

                val projection = arrayOf(
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATE_ADDED
                )

                val lastCheckTime = System.currentTimeMillis() / 1000 - 10 // Last 10 seconds
                Log.d(TAG, "Querying for images added after: $lastCheckTime")

                val selection = "${MediaStore.Images.Media.DATE_ADDED} >= ?"
                val selectionArgs = arrayOf(lastCheckTime.toString())

                contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    "${MediaStore.Images.Media.DATE_ADDED} DESC"
                )?.use { cursor ->
                    Log.d(TAG, "Query returned ${cursor.count} results")

                    if (cursor.moveToFirst()) {
                        val dataIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                        val nameIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)

                        if (dataIndex >= 0 && nameIndex >= 0) {
                            val path = cursor.getString(dataIndex)
                            val name = cursor.getString(nameIndex)

                            Log.d(TAG, "Found image: $name at $path")

                            // Check if it's a screenshot by name
                            val isScreenshot = name.lowercase().contains("screenshot")
                            val fileExists = File(path).exists()
                            val isAlreadyProcessed = processedScreenshots.contains(name)

                            Log.d(TAG, "Is screenshot by name: $isScreenshot, File exists: $fileExists, Already processed: $isAlreadyProcessed")

                            if (isScreenshot && fileExists && !isAlreadyProcessed) {
                                Log.d(TAG, "Screenshot detected: $path")

                                // Process on the main thread
                                handler.post {
                                    onNewScreenshot(path)
                                }
                            } else if (isScreenshot && isAlreadyProcessed) {
                                Log.d(TAG, "Skipping already processed screenshot: $name")
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error checking if URI is a screenshot", e)
            }
        }.start()
    }

    /**
     * Checks for recent screenshots when the service starts.
     */
    private fun checkForRecentScreenshots() {
        Thread {
            try {
                val projection = arrayOf(
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATE_ADDED
                )

                val lastCheckTime = System.currentTimeMillis() / 1000 - 60 // Last minute

                val selection = "${MediaStore.Images.Media.DATE_ADDED} >= ? AND ${MediaStore.Images.Media.DISPLAY_NAME} LIKE ?"
                val selectionArgs = arrayOf(lastCheckTime.toString(), "%screenshot%")

                contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    "${MediaStore.Images.Media.DATE_ADDED} DESC"
                )?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val dataIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                        val nameIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)

                        if (dataIndex >= 0 && nameIndex >= 0) {
                            val path = cursor.getString(dataIndex)
                            val name = cursor.getString(nameIndex)
                            val isAlreadyProcessed = processedScreenshots.contains(name)

                            Log.d(TAG, "Found recent screenshot during startup: $path, Already processed: $isAlreadyProcessed")

                            if (!isAlreadyProcessed) {
                                // Process on the main thread
                                handler.post {
                                    onNewScreenshot(path)
                                }
                            } else {
                                Log.d(TAG, "Skipping already processed screenshot: $name")
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error checking for recent screenshots", e)
            }
        }.start()
    }

    /**
     * Vibrates the device to provide feedback.
     */
    private fun vibrateDevice() {
        try {
            val vibrator = getSystemService(VIBRATOR_SERVICE) as android.os.Vibrator
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(android.os.VibrationEffect.createOneShot(50, android.os.VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(50)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error vibrating device", e)
        }
    }

    /**
     * Calculates the distance between two points.
     */
    private fun calculateDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        return sqrt((x2 - x1).pow(2) + (y2 - y1).pow(2))
    }

    /**
     * Touch listener for the bubble to handle drag and drop.
     */
    private inner class BubbleTouchListener : View.OnTouchListener {
        // Gesture detector for double-tap detection
        private val gestureDetector = GestureDetectorCompat(this@ViewBasedFloatingBubbleService,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    Log.d(TAG, "Double tap detected")
                    // Launch Google Lens with the current screenshot
                    launchGoogleLens()
                    return true
                }

                override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                    // Only handle single tap if we're not dragging
                    if (!isDragging && isClick(e)) {
                        // Show screenshot preview
                        showScreenshotPreview()
                        return true
                    }
                    return false
                }

                override fun onLongPress(e: MotionEvent) {
                    Log.d(TAG, "Long press detected")
                    // Show action buttons around the bubble
                    showActionButtons()
                    // Set isDragging to true to allow immediate dragging after long press
                    isDragging = true
                }
            })

        override fun onTouch(view: View, event: MotionEvent): Boolean {
            // Let the gesture detector handle the event first for double-tap detection
            if (gestureDetector.onTouchEvent(event)) {
                return true
            }

            val params = view.layoutParams as WindowManager.LayoutParams

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Save initial position
                    initialX = params.x
                    initialY = params.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    return true
                }

                MotionEvent.ACTION_MOVE -> {
                    // Calculate new position
                    params.x = initialX + (event.rawX - initialTouchX).toInt()
                    params.y = initialY + (event.rawY - initialTouchY).toInt()

                    // Ensure the bubble stays within screen bounds
                    params.x = params.x.coerceIn(0, width - view.width)
                    params.y = params.y.coerceIn(0, height - view.height)

                    // Update the view
                    windowManager.updateViewLayout(view, params)

                    // Start dragging if we've moved enough
                    if (!isDragging && isSignificantMove(event)) {
                        isDragging = true

                        // Show drag zones
                        if (!showingDragZones) {
                            showDragZones()
                        }

                        // Scale up the bubble - REMOVED per user request
                        // view.animate()
                        //     .scaleX(1.056f)
                        //     .scaleY(1.056f)
                        //     .setDuration(150)
                        //     .setInterpolator(android.view.animation.OvershootInterpolator(1.2f))
                        //     .start()
                    }

                    // Update action buttons position if they're showing
                    if (showingActionButtons && isDragging) {
                        // Get the center of the bubble
                        val centerX = params.x + view.width / 2
                        val centerY = params.y + view.height / 2

                        // Update action buttons position
                        actionButtonsView?.updateBubblePosition(centerX, centerY)

                        // Check if we're hovering over an action button
                        val isOverButton = actionButtonsView?.updateHighlight(centerX.toFloat(), centerY.toFloat()) ?: false

                        // Get the action type for more detailed logging
                        if (isOverButton) {
                            val actionType = actionButtonsView?.getActionAt(centerX.toFloat(), centerY.toFloat()) ?: -1
                            Log.d(TAG, "Hovering over action button at ($centerX, $centerY) with type: $actionType")

                            // We no longer need to provide haptic feedback here
                            // The FloatingActionButtons class handles this when the highlighted button changes

                            // Scale the bubble - REMOVED
                            // view.animate()
                            //    .scaleX(1.1f)
                            //    .scaleY(1.1f)
                            //    .setDuration(100)
                            //    .start()
                        } else {
                            // Reset the scale - REMOVED
                            // view.animate()
                            //    .scaleX(1.05f)
                            //    .scaleY(1.05f)
                            //    .setDuration(100)
                            //    .start()
                        }
                    }

                    // Update highlighted zone and apply magnetic attraction
                    if (isDragging && dragZonesView != null) {
                        val centerX = params.x + view.width / 2f
                        val centerY = params.y + view.height / 2f

                        // Get potentially adjusted position with magnetic attraction
                        val (newX, newY) = dragZonesView?.updateHighlight(centerX, centerY) ?: Pair(centerX, centerY)

                        // Apply the magnetic attraction if position changed
                        if (newX != centerX || newY != centerY) {
                            // Calculate new params position from center point
                            params.x = (newX - view.width / 2f).toInt()
                            params.y = (newY - view.height / 2f).toInt()

                            // Ensure the bubble stays within screen bounds
                            params.x = params.x.coerceIn(0, width - view.width)
                            params.y = params.y.coerceIn(0, height - view.height)

                            // Update the view position
                            windowManager.updateViewLayout(view, params)
                        }
                    }

                    return true
                }

                MotionEvent.ACTION_UP -> {
                    // Get the center of the bubble
                    val centerX = params.x + view.width / 2f
                    val centerY = params.y + view.height / 2f

                    // Check if we were showing action buttons
                    if (showingActionButtons && actionButtonsView != null) {
                        // Only execute action if we were actually dragging
                        if (isDragging) {
                            // Check if we're over an action button
                            val actionType = actionButtonsView!!.getActionAt(centerX, centerY)

                            if (actionType != -1) {
                                // We're over an action button, execute the action
                                Log.d(TAG, "Dropped on action button at ($centerX, $centerY) with action type $actionType")

                                // Handle the action
                                handleAction(actionType)

                                // Animate the bubble disappearing
                                Log.d(TAG, "Starting disappear animation")
                                view.animate()
                                    .alpha(0f)
                                    .scaleX(0f)
                                    .scaleY(0f)
                                    .setDuration(300)
                                    .start()

                                // Remove the bubble after a delay to ensure animation completes
                                Handler(Looper.getMainLooper()).postDelayed({
                                    Log.d(TAG, "Removing bubble after delay")
                                    removeBubble()
                                }, 350)

                                return true
                            }
                        }

                        // Always hide action buttons when touch ends, regardless of whether we were dragging
                        Log.d(TAG, "Hiding action buttons on touch release")
                        hideActionButtons()
                    }

                    // Check if we were dragging
                    if (isDragging) {
                        // Reset dragging state
                        isDragging = false

                        // Get the center of the bubble
                        val centerX = params.x + view.width / 2f
                        val centerY = params.y + view.height / 2f

                        // Check if we're over an action zone in the drag zones
                        val dragZoneActionType = dragZonesView?.getActionAt(centerX, centerY) ?: -1
                        if (dragZoneActionType != -1) {
                            // Handle the action
                            handleAction(dragZoneActionType)
                            return true
                        }

                        // Scale down the bubble - REMOVED
                        // view.animate()
                        //     .scaleX(1.0f)
                        //     .scaleY(1.0f)
                        //     .setDuration(200)
                        //     .setInterpolator(android.view.animation.BounceInterpolator())
                        //     .start()

                        // Check if we dropped on a zone
                        val droppedFolderId = checkDroppedOnZone(
                            params.x + view.width / 2f,
                            params.y + view.height / 2f
                        )

                        if (droppedFolderId != null) {
                            // Handle the drop
                            handleScreenshotDrop(droppedFolderId)
                        } else {
                            // Hide drag zones
                            hideDragZones()

                            // Snap to the nearest edge
                            snapToEdge(view, params)
                        }
                    }

                    return true
                }
            }

            return false
        }

        /**
         * Checks if the movement is significant enough to be considered a drag.
         */
        private fun isSignificantMove(event: MotionEvent): Boolean {
            val dx = event.rawX - initialTouchX
            val dy = event.rawY - initialTouchY
            val distance = sqrt(dx.pow(2) + dy.pow(2))
            return distance > 20f // 20dp threshold
        }

        /**
         * Checks if the touch event is a click (not a drag).
         */
        private fun isClick(event: MotionEvent): Boolean {
            val dx = event.rawX - initialTouchX
            val dy = event.rawY - initialTouchY
            val distance = sqrt(dx.pow(2) + dy.pow(2))
            return distance < 10f // 10dp threshold
        }
    }

    /**
     * Shows a preview of the screenshot.
     */
    private fun showScreenshotPreview() {
        Log.d(TAG, "Showing screenshot preview")

        // TODO: Implement screenshot preview
        // For now, just show a toast
        ScreenUtils.showToast(this, "Screenshot preview")
    }

    /**
     * Shows the action buttons around the floating bubble.
     */
    private fun showActionButtons() {
        try {
            Log.d(TAG, "Showing action buttons")

            // First, hide any existing action buttons to prevent duplicates
            hideActionButtons()

            // Get the bubble position and size
            val bubbleView = this.bubbleView ?: return
            val bubbleParams = bubbleView.layoutParams as WindowManager.LayoutParams
            val bubbleSize = bubbleView.width

            // Apply the app theme to the context so attributes resolve correctly
            val themedContext = android.view.ContextThemeWrapper(this, R.style.Theme_PixelFlow)

            // Create a new action buttons view
            actionButtonsView = FloatingActionButtons(
                themedContext,
                bubbleParams.x + bubbleSize / 2,
                bubbleParams.y + bubbleSize / 2,
                bubbleSize
            ).apply {
                setOnActionSelectedListener { actionType ->
                    handleAction(actionType)
                }
                useDynamicColors = isDynamicColorsEnabled
            }

            // Set up window parameters for the action buttons
            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                if (Build.VERSION.SDK_INT >= 26)
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                else
                    WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )

            // Add the action buttons to the window
            windowManager.addView(actionButtonsView, params)

            // Set the flag to indicate action buttons are showing
            showingActionButtons = true

            Log.d(TAG, "Action buttons added to window manager")
        } catch (e: Exception) {
            Log.e(TAG, "Error showing action buttons", e)
        }
    }

    /**
     * Hides the action buttons.
     */
    private fun hideActionButtons() {
        try {
            Log.d(TAG, "Hiding action buttons")
            if (actionButtonsView != null) {
                windowManager.removeView(actionButtonsView)
                actionButtonsView = null
                Log.d(TAG, "Action buttons removed from window manager")
            }

            // Update the flag
            showingActionButtons = false
        } catch (e: Exception) {
            Log.e(TAG, "Error hiding action buttons", e)
            // Make sure the flag is reset even if there's an error
            showingActionButtons = false
        }
    }

    /**
     * Handles an action selection.
     * DRAG ZONE APPROACH: Using the same approach as drag zones
     */
    private fun handleAction(actionType: Int) {
        try {
            Log.d(TAG, "DRAG ZONE APPROACH: handleAction called with actionType: $actionType")

            // DRAG ZONE APPROACH: Execute the action BEFORE hiding UI elements
            // This ensures the action is executed immediately

            // Handle the action based on type
            when (actionType) {
                // Delete actions
                ViewBasedCircularDragZones.ACTION_DELETE, FloatingActionButtons.ACTION_DELETE -> {
                    // Delete the screenshot
                    val source = if (actionType == ViewBasedCircularDragZones.ACTION_DELETE) "drag zone" else "floating button"
                    Log.d(TAG, "DRAG ZONE APPROACH: Executing DELETE action from $source")

                    // Check if we have a screenshot to delete
                    if (currentScreenshotPath == null) {
                        Log.e(TAG, "DRAG ZONE APPROACH: Cannot delete screenshot: currentScreenshotPath is null")
                        ScreenUtils.showToast(this, "No screenshot to delete")
                        return
                    }

                    // Provide haptic feedback
                    vibrateDevice()

                    // Delete the screenshot IMMEDIATELY
                    Log.d(TAG, "DRAG ZONE APPROACH: Calling deleteScreenshot() directly")
                    deleteScreenshot()
                    Log.d(TAG, "DRAG ZONE APPROACH: deleteScreenshot() completed")
                }

                // Share actions
                ViewBasedCircularDragZones.ACTION_SHARE, FloatingActionButtons.ACTION_SHARE -> {
                    // Share the screenshot
                    val source = if (actionType == ViewBasedCircularDragZones.ACTION_SHARE) "drag zone" else "floating button"
                    Log.d(TAG, "DRAG ZONE APPROACH: Executing SHARE action from $source")

                    // Check if we have a screenshot to share
                    if (currentScreenshotPath == null) {
                        Log.e(TAG, "DRAG ZONE APPROACH: Cannot share screenshot: currentScreenshotPath is null")
                        ScreenUtils.showToast(this, "No screenshot to share")
                        return
                    }

                    // Provide haptic feedback
                    vibrateDevice()

                    // Share the screenshot IMMEDIATELY
                    Log.d(TAG, "DRAG ZONE APPROACH: Calling shareScreenshot() directly")
                    shareScreenshot()
                    Log.d(TAG, "DRAG ZONE APPROACH: shareScreenshot() completed")
                }

                // Trash actions
                ViewBasedCircularDragZones.ACTION_TRASH, FloatingActionButtons.ACTION_TRASH -> {
                    // Move to trash folder
                    val source = if (actionType == ViewBasedCircularDragZones.ACTION_TRASH) "drag zone" else "floating button"
                    Log.d(TAG, "DRAG ZONE APPROACH: Executing TRASH action from $source")

                    // Check if we have a screenshot to move
                    if (currentScreenshotPath == null) {
                        Log.e(TAG, "DRAG ZONE APPROACH: Cannot move screenshot to trash: currentScreenshotPath is null")
                        ScreenUtils.showToast(this, "No screenshot to move")
                        return
                    }

                    // Provide haptic feedback
                    vibrateDevice()

                    // Move to trash IMMEDIATELY
                    Log.d(TAG, "DRAG ZONE APPROACH: Calling moveToTrash() directly")
                    moveToTrash()
                    Log.d(TAG, "DRAG ZONE APPROACH: moveToTrash() completed")
                }

                // Unknown action
                else -> {
                    Log.d(TAG, "DRAG ZONE APPROACH: Unknown action type: $actionType")
                }
            }

            // DRAG ZONE APPROACH: Hide UI elements AFTER executing the action
            Log.d(TAG, "DRAG ZONE APPROACH: Hiding UI elements after action execution")
            dragZonesView?.hideActionZones()
            hideActionButtons()
        } catch (e: Exception) {
            Log.e(TAG, "Error handling action", e)
            ScreenUtils.showToast(this, "Error: ${e.message}")
        }
    }

    /**
     * Deletes the current screenshot.
     * DRAG ZONE APPROACH: Using the same approach as drag zones
     */
    private fun deleteScreenshot() {
        Log.d(TAG, "DRAG ZONE APPROACH: deleteScreenshot() called")
        if (currentScreenshotPath == null) {
            Log.d(TAG, "DRAG ZONE APPROACH: No screenshot to delete - path is null")
            ScreenUtils.showToast(this, "No screenshot to delete")
            return
        }

        try {
            val file = File(currentScreenshotPath!!)
            Log.d(TAG, "DRAG ZONE APPROACH: Attempting to delete file: ${file.absolutePath}")

            if (file.exists()) {
                Log.d(TAG, "DRAG ZONE APPROACH: File exists, attempting to delete")
                val deleted = file.delete()
                Log.d(TAG, "DRAG ZONE APPROACH: File deletion result: $deleted")

                if (deleted) {
                    Log.d(TAG, "DRAG ZONE APPROACH: Screenshot deleted successfully")
                    ScreenUtils.showToast(this, "Screenshot deleted")

                    // Notify the media scanner that the file has been deleted
                    MediaScannerConnection.scanFile(
                        this,
                        arrayOf(file.absolutePath),
                        null,
                        null
                    )

                    // DRAG ZONE APPROACH: Add to processed screenshots to prevent re-detection
                    processedScreenshots.add(file.name)
                    Log.d(TAG, "DRAG ZONE APPROACH: Added to processed screenshots: ${file.name}")

                    // Remove the bubble
                    Log.d(TAG, "DRAG ZONE APPROACH: Removing bubble after deletion")
                    removeBubble()
                    hideDragZones()
                    currentScreenshotPath = null
                    currentScreenshotBitmap = null
                } else {
                    Log.e(TAG, "DRAG ZONE APPROACH: Failed to delete screenshot")
                    ScreenUtils.showToast(this, "Failed to delete screenshot")
                }
            } else {
                Log.e(TAG, "DRAG ZONE APPROACH: Screenshot file does not exist: ${file.absolutePath}")
                ScreenUtils.showToast(this, "Screenshot file not found")
            }
        } catch (e: Exception) {
            Log.e(TAG, "DRAG ZONE APPROACH: Error deleting screenshot", e)
            ScreenUtils.showToast(this, "Error deleting screenshot")
        }
    }

    /**
     * Shares the current screenshot.
     * DRAG ZONE APPROACH: Using the same approach as drag zones
     */
    fun shareScreenshot() {
        Log.d(TAG, "DRAG ZONE APPROACH: shareScreenshot() called")
        if (currentScreenshotPath == null) {
            Log.d(TAG, "DRAG ZONE APPROACH: No screenshot to share - path is null")
            ScreenUtils.showToast(this, "No screenshot to share")
            return
        }

        try {
            val file = File(currentScreenshotPath!!)
            Log.d(TAG, "DRAG ZONE APPROACH: Attempting to share file: ${file.absolutePath}")

            if (!file.exists()) {
                Log.e(TAG, "DRAG ZONE APPROACH: Screenshot file does not exist: ${file.absolutePath}")
                ScreenUtils.showToast(this, "Screenshot file not found")
                return
            }

            Log.d(TAG, "DRAG ZONE APPROACH: File exists, creating URI")
            val uri = FileProvider.getUriForFile(
                this,
                "${packageName}.fileprovider",
                file
            )
            Log.d(TAG, "DRAG ZONE APPROACH: Created URI: $uri")

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            val chooser = Intent.createChooser(intent, "Share Screenshot").apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            // DRAG ZONE APPROACH: Add to processed screenshots to prevent re-detection
            processedScreenshots.add(file.name)
            Log.d(TAG, "DRAG ZONE APPROACH: Added to processed screenshots: ${file.name}")

            // DRAG ZONE APPROACH: Remove the bubble before starting the share activity
            removeBubble()
            hideDragZones()

            Log.d(TAG, "DRAG ZONE APPROACH: Starting share activity")
            startActivity(chooser)
            Log.d(TAG, "DRAG ZONE APPROACH: Share activity started successfully")

            // DRAG ZONE APPROACH: Clear current screenshot data
            currentScreenshotPath = null
            currentScreenshotBitmap = null
        } catch (e: Exception) {
            Log.e(TAG, "DRAG ZONE APPROACH: Error sharing screenshot", e)
            ScreenUtils.showToast(this, "Error sharing screenshot")
        }
    }

    /**
     * Moves the current screenshot to the trash folder.
     * DRAG ZONE APPROACH: Using the same approach as drag zones
     */
    fun moveToTrash() {
        Log.d(TAG, "DRAG ZONE APPROACH: moveToTrash() called")
        if (currentScreenshotPath == null) {
            Log.d(TAG, "DRAG ZONE APPROACH: No screenshot to move - path is null")
            ScreenUtils.showToast(this, "No screenshot to move")
            return
        }

        try {
            // Get the file name before moving
            val file = File(currentScreenshotPath!!)
            val fileName = file.name

            Log.d(TAG, "DRAG ZONE APPROACH: Creating trash folder")
            // Create a trash folder
            val trashFolder = Folder("trash", "Trash", R.drawable.ic_folder)

            Log.d(TAG, "DRAG ZONE APPROACH: Moving screenshot to trash folder: ${currentScreenshotPath}")
            // Move the screenshot to the trash folder
            moveScreenshotToFolder(currentScreenshotPath!!, trashFolder)

            // DRAG ZONE APPROACH: Add to processed screenshots to prevent re-detection
            processedScreenshots.add(fileName)
            Log.d(TAG, "DRAG ZONE APPROACH: Added to processed screenshots: $fileName")

            // Remove the bubble
            Log.d(TAG, "DRAG ZONE APPROACH: Removing bubble after moving to trash")
            removeBubble()
            hideDragZones()
            currentScreenshotPath = null
            currentScreenshotBitmap = null

            Log.d(TAG, "DRAG ZONE APPROACH: Screenshot successfully moved to trash")
            ScreenUtils.showToast(this, "Screenshot moved to trash")
        } catch (e: Exception) {
            Log.e(TAG, "DRAG ZONE APPROACH: Error moving screenshot to trash", e)
            ScreenUtils.showToast(this, "Error moving screenshot to trash")
        }
    }

    /**
     * Launches Google Lens with the current screenshot.
     */
    private fun launchGoogleLens() {
        Log.d(TAG, "Launching Google Lens with screenshot")

        try {
            if (currentScreenshotPath == null) {
                Log.e(TAG, "Cannot launch Google Lens: No screenshot available")
                ScreenUtils.showToast(this, "No screenshot available")
                return
            }

            // Create a content URI for the screenshot file using FileProvider
            val screenshotFile = File(currentScreenshotPath!!)
            val screenshotUri = FileProvider.getUriForFile(
                this,
                "${packageName}.fileprovider",
                screenshotFile
            )

            Log.d(TAG, "Screenshot URI: $screenshotUri")

            // Try different approaches to launch Google Lens
            var success = false

            // Approach 1: Use Google Lens directly with ACTION_SEND
            try {
                val lensIntent = Intent(Intent.ACTION_SEND)
                lensIntent.type = "image/*"
                lensIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri)
                lensIntent.setPackage("com.google.android.googlequicksearchbox")
                lensIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                lensIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                startActivity(lensIntent)
                vibrateDevice() // Provide haptic feedback
                Log.d(TAG, "Google Lens launched with ACTION_SEND")
                success = true
            } catch (e: Exception) {
                Log.e(TAG, "Failed to launch Google Lens with ACTION_SEND: ${e.message}")
            }

            // Approach 2: Use Google Photos with ACTION_SEND
            if (!success) {
                try {
                    val photosIntent = Intent(Intent.ACTION_SEND)
                    photosIntent.type = "image/*"
                    photosIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri)
                    photosIntent.setPackage("com.google.android.apps.photos")
                    photosIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    photosIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                    startActivity(photosIntent)
                    vibrateDevice() // Provide haptic feedback
                    Log.d(TAG, "Google Photos launched with ACTION_SEND")
                    success = true
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to launch Google Photos: ${e.message}")
                }
            }

            // Approach 3: Use a chooser with ACTION_SEND
            if (!success) {
                try {
                    val sendIntent = Intent(Intent.ACTION_SEND)
                    sendIntent.type = "image/*"
                    sendIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri)
                    sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                    val chooser = Intent.createChooser(sendIntent, "Search with Google Lens")
                    chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                    startActivity(chooser)
                    vibrateDevice() // Provide haptic feedback
                    Log.d(TAG, "Chooser launched with ACTION_SEND")
                    success = true
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to launch chooser: ${e.message}")
                }
            }

            // Approach 4: Use Google app with ACTION_VIEW
            if (!success) {
                try {
                    val googleIntent = Intent(Intent.ACTION_VIEW)
                    googleIntent.setPackage("com.google.android.googlequicksearchbox")
                    googleIntent.data = screenshotUri
                    googleIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    googleIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                    startActivity(googleIntent)
                    vibrateDevice() // Provide haptic feedback
                    Log.d(TAG, "Google app launched with ACTION_VIEW")
                    success = true
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to launch Google app with ACTION_VIEW: ${e.message}")
                }
            }

            // Approach 5: Use web browser with lens.google.com
            if (!success) {
                try {
                    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://lens.google.com"))
                    webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                    startActivity(webIntent)
                    vibrateDevice() // Provide haptic feedback
                    Log.d(TAG, "Web Google Lens launched")
                    success = true
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to launch web Google Lens: ${e.message}")
                }
            }

            // If all approaches failed
            if (!success) {
                ScreenUtils.showToast(this, "Google Lens not available")
                Log.e(TAG, "Google Lens not available - all approaches failed")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error launching Google Lens", e)
            ScreenUtils.showToast(this, "Error launching Google Lens")
        }
    }

    // calculateDistance method is already defined elsewhere in this class

    /**
     * Calculates the position of the delete button based on the bubble position.
     * Returns a Pair of (x, y) coordinates.
     */
    private fun calculateDeleteButtonPosition(): Pair<Float, Float> {
        val bubbleView = this.bubbleView ?: return Pair(0f, 0f)
        val bubbleParams = bubbleView.layoutParams as WindowManager.LayoutParams
        val bubbleSize = bubbleView.width
        val bubbleCenterX = bubbleParams.x + bubbleSize / 2f
        val bubbleCenterY = bubbleParams.y + bubbleSize / 2f

        // Calculate the orbit radius based on bubble size
        val orbitRadius = bubbleSize * 1.5f

        // Determine which side of the screen the bubble is closer to
        val isNearRight = bubbleCenterX > width / 2

        // Calculate the angle for the delete button (top position)
        val angle = if (isNearRight) Math.toRadians(225.0) else Math.toRadians(315.0)

        // Calculate the position using standard parametric circle equations
        val x = bubbleCenterX + orbitRadius * cos(angle).toFloat()
        val y = bubbleCenterY + orbitRadius * sin(angle).toFloat()

        return Pair(x, y)
    }

    /**
     * Calculates the position of the share button based on the bubble position.
     * Returns a Pair of (x, y) coordinates.
     */
    private fun calculateShareButtonPosition(): Pair<Float, Float> {
        val bubbleView = this.bubbleView ?: return Pair(0f, 0f)
        val bubbleParams = bubbleView.layoutParams as WindowManager.LayoutParams
        val bubbleSize = bubbleView.width
        val bubbleCenterX = bubbleParams.x + bubbleSize / 2f
        val bubbleCenterY = bubbleParams.y + bubbleSize / 2f

        // Calculate the orbit radius based on bubble size
        val orbitRadius = bubbleSize * 1.5f

        // Determine which side of the screen the bubble is closer to
        val isNearRight = bubbleCenterX > width / 2

        // Calculate the angle for the share button (middle position)
        val angle = if (isNearRight) Math.toRadians(180.0) else Math.toRadians(0.0)

        // Calculate the position using standard parametric circle equations
        val x = bubbleCenterX + orbitRadius * cos(angle).toFloat()
        val y = bubbleCenterY + orbitRadius * sin(angle).toFloat()

        return Pair(x, y)
    }

    /**
     * Calculates the position of the trash button based on the bubble position.
     * Returns a Pair of (x, y) coordinates.
     */
    private fun calculateTrashButtonPosition(): Pair<Float, Float> {
        val bubbleView = this.bubbleView ?: return Pair(0f, 0f)
        val bubbleParams = bubbleView.layoutParams as WindowManager.LayoutParams
        val bubbleSize = bubbleView.width
        val bubbleCenterX = bubbleParams.x + bubbleSize / 2f
        val bubbleCenterY = bubbleParams.y + bubbleSize / 2f

        // Calculate the orbit radius based on bubble size
        val orbitRadius = bubbleSize * 1.5f

        // Determine which side of the screen the bubble is closer to
        val isNearRight = bubbleCenterX > width / 2

        // Calculate the angle for the trash button (bottom position)
        val angle = if (isNearRight) Math.toRadians(135.0) else Math.toRadians(45.0)

        // Calculate the position using standard parametric circle equations
        val x = bubbleCenterX + orbitRadius * cos(angle).toFloat()
        val y = bubbleCenterY + orbitRadius * sin(angle).toFloat()

        return Pair(x, y)
    }

    /**
     * Snaps the bubble to the nearest edge of the screen.
     */
    private fun snapToEdge(view: View, params: WindowManager.LayoutParams) {
        // Calculate the center of the bubble
        val centerX = params.x + view.width / 2

        // Determine which edge is closer (left or right)
        val toRight = centerX > width / 2

        // Calculate the target X position
        val targetX = if (toRight) width - view.width else 0

        // Animate the bubble to the edge
        val animator = android.animation.ValueAnimator.ofInt(params.x, targetX)
        animator.duration = 300
        animator.interpolator = android.view.animation.DecelerateInterpolator()
        animator.addUpdateListener { animation ->
            params.x = animation.animatedValue as Int
            try {
                windowManager.updateViewLayout(view, params)
            } catch (e: Exception) {
                Log.e(TAG, "Error updating bubble position during snap", e)
                animator.cancel()
            }
        }
        animator.start()
    }
}
