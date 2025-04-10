package com.aks_labs.pixelflow.services

import android.app.Service
import android.content.ContentResolver
import android.content.Intent
import android.database.ContentObserver
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.aks_labs.pixelflow.R
import com.aks_labs.pixelflow.data.models.Folder
import com.aks_labs.pixelflow.ui.components.ViewBasedCircularDragZones
import com.aks_labs.pixelflow.utils.BitmapUtils
import com.aks_labs.pixelflow.utils.ScreenUtils
import java.io.File
import kotlin.math.pow
import kotlin.math.sqrt

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

    // Screen dimensions
    private var width = 0
    private var height = 0

    // Views
    private var bubbleView: View? = null
    private var dragZonesView: ViewBasedCircularDragZones? = null

    // Current screenshot
    private var currentScreenshotPath: String? = null
    private var currentScreenshotBitmap: Bitmap? = null

    // Drag state
    private var isDragging = false
    private var showingDragZones = false

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

        // Initialize folders
        initFolders()
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

        // Clean up views
        removeBubble()
        hideDragZones()

        // Reset the running flag
        isServiceRunning = false

        super.onDestroy()
    }

    /**
     * Initialize the folders for drag zones.
     */
    private fun initFolders() {
        // Clear existing folders
        folders.clear()

        // Add default folders
        folders.add(Folder("1", "Tricks", R.drawable.ic_folder))
        folders.add(Folder("2", "Quotes", R.drawable.ic_folder))
        folders.add(Folder("3", "Recipes", R.drawable.ic_folder))
        folders.add(Folder("4", "Ideas", R.drawable.ic_folder))
        folders.add(Folder("5", "Other", R.drawable.ic_folder))
    }

    /**
     * Called when a new screenshot is detected.
     */
    private fun onNewScreenshot(screenshotPath: String) {
        Log.d(TAG, "New screenshot detected: $screenshotPath")

        // Check if the file exists and has content
        val file = File(screenshotPath)
        if (file.exists() && file.length() > 0) {
            Log.d(TAG, "Screenshot file exists, size: ${file.length()} bytes")

            // Save the current screenshot path
            currentScreenshotPath = screenshotPath

            // Load the bitmap
            currentScreenshotBitmap = BitmapUtils.loadBitmapFromFile(screenshotPath)

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
        Thread {
            var attempts = 0
            val maxAttempts = 10
            var fileReady = false

            while (attempts < maxAttempts && !fileReady) {
                attempts++
                Log.d(TAG, "Waiting for screenshot file to be ready, attempt $attempts")

                try {
                    Thread.sleep(500) // Wait for 500ms

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
    private fun removeBubble() {
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
            Log.d(TAG, "Folders count: ${folders.size}")

            // First, hide any existing drag zones to prevent duplicates
            hideDragZones()

            // Create a new drag zones view
            dragZonesView = ViewBasedCircularDragZones(this).apply {
                setFolders(folders)
                setOnFolderSelectedListener { folderId ->
                    handleScreenshotDrop(folderId)
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

            // Add the drag zones to the window
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
     * Hides the drag zones.
     */
    private fun hideDragZones() {
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

            // Move the screenshot to the folder
            moveScreenshotToFolder(currentScreenshotPath!!, folder)

            // Remove the bubble and hide drag zones
            removeBubble()
            hideDragZones()
        }
    }

    /**
     * Moves a screenshot to a folder.
     */
    private fun moveScreenshotToFolder(screenshotPath: String, folder: Folder) {
        // Create the folder directory if it doesn't exist
        val folderDir = java.io.File(getExternalFilesDir(null), "PixelFlow/${folder.name}")
        if (!folderDir.exists()) {
            folderDir.mkdirs()
        }

        // Get the source file
        val sourceFile = java.io.File(screenshotPath)
        if (!sourceFile.exists()) {
            Log.e(TAG, "Source file does not exist: $screenshotPath")
            return
        }

        // Create the destination file
        val destFile = java.io.File(folderDir, sourceFile.name)

        try {
            // Copy the file
            sourceFile.copyTo(destFile, overwrite = true)
            Log.d(TAG, "Screenshot copied to folder: ${folder.name}")

            // Show a toast notification
            ScreenUtils.showToast(this, "Screenshot saved to ${folder.name}")
        } catch (e: Exception) {
            Log.e(TAG, "Error copying screenshot to folder", e)
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
     * Starts the screenshot detection.
     */
    private fun startScreenshotDetection() {
        Log.d(TAG, "Starting screenshot detection")

        // Create and register the content observer
        val contentObserver = createScreenshotContentObserver()
        val contentResolver = contentResolver
        contentResolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            true,
            contentObserver
        )

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

                            Log.d(TAG, "Is screenshot by name: $isScreenshot, File exists: $fileExists")

                            if (isScreenshot && fileExists) {
                                Log.d(TAG, "Screenshot detected: $path")

                                // Process on the main thread
                                handler.post {
                                    onNewScreenshot(path)
                                }
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

                        if (dataIndex >= 0) {
                            val path = cursor.getString(dataIndex)
                            Log.d(TAG, "Found recent screenshot during startup: $path")

                            // Process on the main thread
                            handler.post {
                                onNewScreenshot(path)
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
     * Touch listener for the bubble to handle drag and drop.
     */
    private inner class BubbleTouchListener : View.OnTouchListener {
        override fun onTouch(view: View, event: MotionEvent): Boolean {
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

                        // Scale up the bubble with bouncing animation
                        view.animate()
                            .scaleX(1.055f)
                            .scaleY(1.055f)
                            .setDuration(150)
                            .setInterpolator(android.view.animation.OvershootInterpolator(1.2f))
                            .start()
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
                    // Check if we were dragging
                    if (isDragging) {
                        // Reset dragging state
                        isDragging = false

                        // Scale down the bubble with bouncing animation
                        view.animate()
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .setDuration(200)
                            .setInterpolator(android.view.animation.BounceInterpolator())
                            .start()

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
                        }
                    } else {
                        // It was a click, not a drag
                        if (isClick(event)) {
                            // Show screenshot preview
                            showScreenshotPreview()
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
}
