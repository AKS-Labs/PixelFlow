package com.aks_labs.pixelflow.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import androidx.cardview.widget.CardView
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.PixelFormat
import android.util.Log
import java.io.File as JavaFile
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.animation.ValueAnimator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.aks_labs.pixelflow.MainActivity
import com.aks_labs.pixelflow.R
import com.aks_labs.pixelflow.data.SharedPrefsManager
import com.aks_labs.pixelflow.data.models.SimpleFolder
import com.aks_labs.pixelflow.data.models.SimpleScreenshot
import com.aks_labs.pixelflow.pixelFlowApp
import com.aks_labs.pixelflow.ui.components.CircularDragZone
import com.aks_labs.pixelflow.ui.components.ComposeCircularDragZone
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream as JavaFileOutputStream
import kotlin.math.abs

/**
 * Service to display and manage the floating bubble
 */
class FloatingBubbleService : Service() {

    // Extension function to convert dp to pixels
    private val Int.dp: Float
        get() = this * resources.displayMetrics.density

    // Extension function to convert dp to pixels
    private fun Float.toPx(): Float = this * resources.displayMetrics.density

    companion object {
        private const val TAG = "FloatingBubbleService"
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "PixelFlowServiceChannel"

        // Action to restart the service
        const val ACTION_RESTART_SERVICE = "com.aks_labs.pixelflow.RESTART_SERVICE"

        // Flag to track if the service is running
        @Volatile
        private var isServiceRunning = false

        // Check if the service is running
        fun isRunning(): Boolean {
            return isServiceRunning
        }
    }

    private lateinit var windowManager: WindowManager
    private lateinit var bubbleView: View
    private lateinit var dragZonesView: View

    private lateinit var screenshotDetector: ScreenshotDetector
    private lateinit var sharedPrefsManager: SharedPrefsManager

    private var currentScreenshotPath: String? = null
    private var folders: List<SimpleFolder> = emptyList()

    // Screen dimensions
    private val displayMetrics: DisplayMetrics by lazy {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        metrics
    }
    private val width: Int by lazy { displayMetrics.widthPixels }
    private val height: Int by lazy { displayMetrics.heightPixels }

    // Service lifecycle variables
    private var serviceJob: Job? = null
    private var restartHandler: Handler? = null
    private var restartRunnable: Runnable? = null
    private val restartInterval = 60 * 1000L // 1 minute
    private var isBeingDestroyed = false

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service onCreate called")

        // Initialize window manager
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        // Initialize SharedPrefsManager
        sharedPrefsManager = pixelFlowApp.sharedPrefsManager

        // Initialize screenshot detector
        screenshotDetector = ScreenshotDetector(this) { screenshotPath ->
            handleNewScreenshot(screenshotPath)
        }

        // Load folders
        folders = sharedPrefsManager.foldersValue

        // Initialize restart handler
        restartHandler = Handler(Looper.getMainLooper())
        restartRunnable = Runnable {
            Log.d(TAG, "Periodic service check running")
            if (!screenshotDetector.isObserving()) {
                Log.d(TAG, "Screenshot detector not observing, restarting")
                screenshotDetector.startObserving()
            }
            // Schedule the next check
            restartHandler?.postDelayed(restartRunnable!!, restartInterval)
        }

        // Set the service as running
        isServiceRunning = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service onStartCommand called with action: ${intent?.action}")

        // Create notification channel for foreground service
        createNotificationChannel()

        // Start as foreground service with notification
        startForeground(NOTIFICATION_ID, createNotification())
        Log.d(TAG, "Service started in foreground")

        // Cancel any existing jobs
        serviceJob?.cancel()

        // Start a new job for this service instance
        serviceJob = CoroutineScope(Dispatchers.Default).launch {
            // This keeps the coroutine alive as long as the service is running
            try {
                while (isActive) {
                    delay(5000) // Check every 5 seconds
                    if (!screenshotDetector.isObserving()) {
                        Log.d(TAG, "Screenshot detector stopped, restarting it")
                        screenshotDetector.startObserving()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error in service job", e)
            }
        }

        // Start the periodic check
        restartHandler?.removeCallbacks(restartRunnable!!)
        restartHandler?.postDelayed(restartRunnable!!, restartInterval)

        // Handle different intents
        when (intent?.action) {
            ACTION_RESTART_SERVICE -> {
                Log.d(TAG, "Service restart requested")
                // Make sure we're observing
                screenshotDetector.startObserving()
            }
            "START_ON_BOOT" -> {
                Log.d(TAG, "Service starting after boot")
                screenshotDetector.startObserving()
            }
            "MANUAL_TEST" -> {
                Log.d(TAG, "Manual test requested")
                // Find a recent image to test with
                findRecentImageForTest()
            }
            "STOP_SERVICE" -> {
                Log.d(TAG, "Service stop requested by user")
                // Set the flag to indicate we're being explicitly stopped
                isBeingDestroyed = true
                // Don't do anything else - the service will be stopped by the system
            }
            "SERVICE_RESTARTING" -> {
                Log.d(TAG, "Service restarting after being killed")
                // Reset the flag
                isBeingDestroyed = false
                // Start observing for screenshots
                screenshotDetector.startObserving()
            }
            else -> {
                // Check if we have a test screenshot path
                val testScreenshotPath = intent?.getStringExtra("test_screenshot_path")
                if (testScreenshotPath != null) {
                    Log.d(TAG, "Received test screenshot path: $testScreenshotPath")
                    // Handle the test screenshot directly
                    handleNewScreenshot(testScreenshotPath)
                } else {
                    // Start observing for screenshots
                    screenshotDetector.startObserving()
                    Log.d(TAG, "Screenshot detector started")
                }
            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.d(TAG, "Service onDestroy called")
        super.onDestroy()

        // Set the service as not running
        isServiceRunning = false

        // Cancel the restart handler
        restartHandler?.removeCallbacks(restartRunnable!!)
        restartHandler = null

        // Cancel the service job
        serviceJob?.cancel()
        serviceJob = null

        // Stop observing for screenshots
        screenshotDetector.stopObserving()

        // Remove views from window manager
        try {
            if (::bubbleView.isInitialized && bubbleView.isAttachedToWindow) {
                windowManager.removeView(bubbleView)
            }
            if (::dragZonesView.isInitialized && dragZonesView.isAttachedToWindow) {
                windowManager.removeView(dragZonesView)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error removing views", e)
        }

        // Restart the service if it was killed unexpectedly
        try {
            // Only restart if we're not being explicitly stopped by the user
            if (!isBeingDestroyed) {
                Log.d(TAG, "Service was killed unexpectedly, restarting")
                val intent = Intent(this, FloatingBubbleService::class.java)
                intent.action = "SERVICE_RESTARTING"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent)
                } else {
                    startService(intent)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to restart service", e)
        }

        // Cancel coroutines
        serviceJob?.cancel()
    }

    /**
     * Handle a new screenshot being taken
     */
    private fun handleNewScreenshot(screenshotPath: String) {
        try {
            Log.d(TAG, "New screenshot detected: $screenshotPath")

            // Check if the screenshot file exists
            val screenshotFile = JavaFile(screenshotPath)
            if (!screenshotFile.exists()) {
                Log.e(TAG, "Screenshot file does not exist: $screenshotPath")
                return
            }

            Log.d(TAG, "Screenshot file exists, size: ${screenshotFile.length()} bytes")

            // If the file size is 0 or the path contains '.pending', wait for it to be ready
            if (screenshotFile.length() == 0L || screenshotPath.contains(".pending")) {
                Log.d(TAG, "Screenshot file is still pending, waiting for it to be ready")
                waitForScreenshotAndShow(screenshotPath)
                return
            }

            currentScreenshotPath = screenshotPath

            // Create and show the floating bubble with the screenshot
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    Log.d(TAG, "Showing floating bubble for screenshot")

                    // Check if we can draw overlays
                    if (!Settings.canDrawOverlays(this@FloatingBubbleService)) {
                        Log.e(TAG, "Cannot draw overlays, permission not granted")
                        Toast.makeText(
                            this@FloatingBubbleService,
                            "Permission to draw over other apps is required",
                            Toast.LENGTH_LONG
                        ).show()
                        return@launch
                    }

                    showFloatingBubble(screenshotPath)
                    Log.d(TAG, "Floating bubble shown successfully")
                } catch (e: Exception) {
                    Log.e(TAG, "Error showing floating bubble", e)
                    Toast.makeText(
                        this@FloatingBubbleService,
                        "Error displaying screenshot: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error handling new screenshot", e)
        }
    }

    /**
     * Show the floating bubble with the screenshot
     */
    private fun showFloatingBubble(screenshotPath: String) {
        if (!Settings.canDrawOverlays(this)) {
            return
        }

        // Remove existing bubble if any
        try {
            if (::bubbleView.isInitialized && bubbleView.isAttachedToWindow) {
                windowManager.removeView(bubbleView)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Create a custom bubble view programmatically
        val frameLayout = FrameLayout(this)
        val cardView = CardView(this)
        val imageView = ImageView(this)

        // Configure the card view
        cardView.radius = 45.dp.toPx()
        cardView.cardElevation = 6.dp.toPx()
        cardView.setCardBackgroundColor(Color.WHITE)

        // Configure the image view
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP

        // Add the image view to the card view
        cardView.addView(imageView, FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        ))

        // Add the card view to the frame layout
        frameLayout.addView(cardView, FrameLayout.LayoutParams(
            90.dp.toPx().toInt(),
            90.dp.toPx().toInt(),
            Gravity.CENTER
        ))

        // Set the bubble view
        bubbleView = frameLayout

        try {
            // Set the screenshot as the bubble image
            val bubbleImageView = imageView
            val bubbleCard = cardView

            // Apply initial animation
            bubbleView.alpha = 0f
            bubbleView.scaleX = 0.5f
            bubbleView.scaleY = 0.5f

            // Check if the screenshot file exists
            val screenshotFile = File(screenshotPath)
            if (!screenshotFile.exists()) {
                Log.e(TAG, "Screenshot file does not exist: $screenshotPath")
                stopSelf()
                return
            }

            // Decode the bitmap with options to prevent OutOfMemoryError
            val options = BitmapFactory.Options().apply {
                inSampleSize = 2  // Scale down by factor of 2
            }
            val bitmap = BitmapFactory.decodeFile(screenshotPath, options)

            if (bitmap == null) {
                Log.e(TAG, "Failed to decode bitmap from: $screenshotPath")
                stopSelf()
                return
            }

            // Set the bitmap directly (CardView handles the circular shape)
            bubbleImageView.setImageBitmap(bitmap)
        } catch (e: Exception) {
            Log.e(TAG, "Error setting bubble image", e)
            stopSelf()
            return
        }

        // Set up window parameters for the bubble
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= 26) // O is API 26
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        // Position the bubble on the right side near volume keys
        params.gravity = Gravity.TOP or Gravity.START
        params.x = width - 100  // Near right edge
        params.y = 300 // Position near volume keys

        // Add the bubble to the window
        windowManager.addView(bubbleView, params)

        // Animate the bubble entrance
        bubbleView.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(300)
            .setInterpolator(OvershootInterpolator(1.5f))
            .start()

        // Set up touch listener for dragging
        setupBubbleTouchListener(bubbleView, params)
    }

    /**
     * Set up touch listener for the bubble
     */
    private fun setupBubbleTouchListener(view: View, params: WindowManager.LayoutParams) {
        var initialX = 0
        var initialY = 0
        var initialTouchX = 0f
        var initialTouchY = 0f
        var isDragging = false
        var showingDragZones = false

        view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Record initial positions
                    initialX = params.x
                    initialY = params.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    isDragging = false
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    // Calculate new position
                    val dx = event.rawX - initialTouchX
                    val dy = event.rawY - initialTouchY

                    // If moved enough, consider it a drag
                    if (!isDragging && (abs(dx) > 10 || abs(dy) > 10)) {
                        isDragging = true

                        // Show drag zones when dragging starts
                        if (!showingDragZones) {
                            showDragZones()
                            showingDragZones = true
                        }
                    }

                    if (isDragging) {
                        // Calculate new position with bounds checking
                        var newX = (initialX + dx.toInt()).coerceIn(0, width - view.width)
                        var newY = (initialY + dy.toInt()).coerceIn(0, height - view.height)

                        // Check for magnetic attraction to drag zones
                        if (::dragZonesView.isInitialized) {
                            val dragZone = dragZonesView as ComposeCircularDragZone

                            // Highlight the zone the bubble is over or near
                            dragZone.highlightZoneAt(event.rawX, event.rawY)

                            // Check if bubble is over any drag zone
                            val zoneIndex = dragZone.getZoneIndexAt(event.rawX, event.rawY)
                            if (zoneIndex >= 0) {
                                // Shrink when over a drop zone
                                view.animate()
                                    .scaleX(0.9f)
                                    .scaleY(0.9f)
                                    .setDuration(100)
                                    .start()
                            } else {
                                // Add a bigger scale effect while dragging with material design elevation
                                // Scale by 5dp (from 90dp to 95dp)
                                // Ensure we scale both X and Y equally to maintain the circular shape
                                val scaleValue = 1.055f // 95/90 = 1.055
                                view.animate()
                                    .scaleX(scaleValue)
                                    .scaleY(scaleValue)
                                    .setDuration(150)
                                    .start()

                                // Update the CardView's corner radius to maintain perfect circle
                                val bubbleCard = (view as FrameLayout).getChildAt(0) as CardView
                                val newSize = (90 * scaleValue).toInt()
                                val newRadius = newSize / 2f
                                bubbleCard.radius = newRadius

                                // Increase elevation for dragging effect
                                bubbleCard.animate()
                                    .translationZ(16f)
                                    .setDuration(150)
                                    .start()
                            }
                        } else {
                            // Add a bigger scale effect while dragging with material design elevation
                            // Scale by 5dp (from 90dp to 95dp)
                            // Ensure we scale both X and Y equally to maintain the circular shape
                            val scaleValue = 1.055f // 95/90 = 1.055
                            view.animate()
                                .scaleX(scaleValue)
                                .scaleY(scaleValue)
                                .setDuration(150)
                                .start()

                            // Increase elevation for dragging effect
                            // Find the CardView (it's the first child of the FrameLayout)
                            val bubbleCard = (view as FrameLayout).getChildAt(0) as CardView

                            // Update the CardView's corner radius to maintain perfect circle
                            val newSize = (90 * scaleValue).toInt()
                            val newRadius = newSize / 2f
                            bubbleCard.radius = newRadius

                            bubbleCard.animate()
                                .translationZ(16f)
                                .setDuration(150)
                                .start()
                        }

                        // Update the bubble position
                        params.x = newX
                        params.y = newY
                        windowManager.updateViewLayout(view, params)
                    }
                    true
                }

                MotionEvent.ACTION_UP -> {
                    // Return bubble to normal size with bounce effect
                    view.animate()
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .setDuration(200)
                        .setInterpolator(OvershootInterpolator(1.2f))
                        .start()

                    // Reset elevation
                    // Find the CardView (it's the first child of the FrameLayout)
                    val bubbleCard = (view as FrameLayout).getChildAt(0) as CardView

                    // Reset the corner radius to maintain perfect circle
                    bubbleCard.radius = 45f // Half of 90dp

                    bubbleCard.animate()
                        .translationZ(0f)
                        .setDuration(200)
                        .start()

                    if (!isDragging) {
                        // It was a click, not a drag
                        // Handle preview functionality here
                        showScreenshotPreview()
                    } else {
                        // It was a drag, check if dropped on a zone
                        if (::dragZonesView.isInitialized) {
                            val dragZone = dragZonesView as ComposeCircularDragZone
                            val zoneIndex = dragZone.getZoneIndexAt(event.rawX, event.rawY)

                            if (zoneIndex >= 0) {
                                // Get the folder ID from the zone index
                                val folder = folders.getOrNull(zoneIndex)
                                if (folder != null) {
                                    // Vibrate to provide feedback
                                    vibrateDevice()

                                    // Handle screenshot drop on folder with success animation
                                    view.animate()
                                        .alpha(0.0f)
                                        .scaleX(0.0f)
                                        .scaleY(0.0f)
                                        .setDuration(300)
                                        .setInterpolator(AccelerateInterpolator())
                                        .withEndAction {
                                            handleScreenshotDrop(folder.id)
                                            removeBubble()
                                        }
                                        .start()
                                }
                            } else {
                                // Snap to edge if not dropped on a folder
                                snapToEdge(view, params)
                            }

                            // Hide drag zones with fade out animation
                            dragZonesView.animate()
                                .alpha(0.0f)
                                .setDuration(200)
                                .withEndAction {
                                    hideDragZones()
                                }
                                .start()
                            showingDragZones = false
                        } else {
                            // Snap to edge if drag zones aren't initialized
                            snapToEdge(view, params)
                        }
                    }
                    true
                }

                else -> false
            }
        }
    }

    /**
     * Snap the bubble to the nearest edge of the screen
     */
    private fun snapToEdge(view: View, params: WindowManager.LayoutParams) {
        // Determine which edge is closer
        val toRight = params.x > width / 2

        // Calculate target X position
        val targetX = if (toRight) width - view.width else 0

        // Animate to edge
        val animator = ValueAnimator.ofInt(params.x, targetX)
        animator.duration = 200
        animator.interpolator = OvershootInterpolator(1.1f)
        animator.addUpdateListener { animation ->
            params.x = animation.animatedValue as Int
            try {
                windowManager.updateViewLayout(view, params)
            } catch (e: Exception) {
                // View might have been removed
            }
        }
        animator.start()
    }

    /**
     * Show the circular drag zones
     */
    private fun showDragZones() {
        if (!Settings.canDrawOverlays(this)) {
            return
        }

        // Remove existing drag zones if any
        try {
            if (::dragZonesView.isInitialized && dragZonesView.isAttachedToWindow) {
                windowManager.removeView(dragZonesView)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Create the drag zones view using the Compose-based implementation
        dragZonesView = ComposeCircularDragZone(this)

        // Set initial alpha for animation
        dragZonesView.alpha = 0f

        // Set up window parameters for the drag zones
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            if (Build.VERSION.SDK_INT >= 26) // O is API 26
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT
        )

        // Add the drag zones to the window
        windowManager.addView(dragZonesView, params)

        // Animate the drag zones entrance
        dragZonesView.animate()
            .alpha(1f)
            .setDuration(200)
            .start()

        // Set up the folders in the drag zone
        (dragZonesView as ComposeCircularDragZone).apply {
            setFolders(folders)
            setOnFolderSelectedListener { folderId -> handleScreenshotDrop(folderId) }
        }
    }

    /**
     * Hide the circular drag zones
     */
    private fun hideDragZones() {
        try {
            if (::dragZonesView.isInitialized && dragZonesView.isAttachedToWindow) {
                windowManager.removeView(dragZonesView)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Handle dropping a screenshot on a folder
     */
    private fun handleScreenshotDrop(folderId: Long) {
        val screenshotPath = currentScreenshotPath ?: return

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Get the folder
                val folder = sharedPrefsManager.getFolder(folderId)
                if (folder == null) {
                    Log.e(TAG, "Folder not found with ID: $folderId")
                    return@launch
                }

                // Check if the screenshot file exists
                val screenshotFile = JavaFile(screenshotPath)
                if (!screenshotFile.exists()) {
                    Log.e(TAG, "Screenshot file does not exist: $screenshotPath")
                    return@launch
                }

                val timestamp = System.currentTimeMillis()
                val newFileName = "screenshot_${timestamp}.jpg"

                // Get the folder directory in external storage
                val folderDir = sharedPrefsManager.getFolderDirectory(folder.name)
                if (!folderDir.exists() && !folderDir.mkdirs()) {
                    Log.e(TAG, "Failed to create folder directory: ${folderDir.absolutePath}")
                    return@launch
                }

                val newFile = JavaFile(folderDir, newFileName)

                try {
                    // Move the file (copy then delete original)
                    screenshotFile.inputStream().use { input ->
                        JavaFileOutputStream(newFile).use { output ->
                            input.copyTo(output)
                        }
                    }

                    // Verify the copy was successful
                    if (newFile.exists() && newFile.length() > 0) {
                        // Delete the original file
                        if (!screenshotFile.delete()) {
                            Log.w(TAG, "Failed to delete original screenshot: $screenshotPath")
                        }
                    } else {
                        Log.e(TAG, "Failed to copy screenshot to: ${newFile.absolutePath}")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error copying screenshot", e)
                }

                try {
                    // Create a thumbnail
                    val thumbnailDir = JavaFile(filesDir, "thumbnails")
                    if (!thumbnailDir.exists() && !thumbnailDir.mkdirs()) {
                        Log.e(TAG, "Failed to create thumbnail directory: ${thumbnailDir.absolutePath}")
                    }

                    val thumbnailFile = JavaFile(thumbnailDir, "thumb_$newFileName")
                    val thumbnailCreated = createThumbnail(newFile.absolutePath, thumbnailFile.absolutePath)

                    if (thumbnailCreated) {
                        // Save to database
                        val screenshot = SimpleScreenshot(
                            id = sharedPrefsManager.generateScreenshotId(),
                            filePath = newFile.absolutePath,
                            thumbnailPath = thumbnailFile.absolutePath,
                            folderId = folderId,
                            originalTimestamp = screenshotFile.lastModified()
                        )

                        sharedPrefsManager.addScreenshot(screenshot)
                        Log.d(TAG, "Screenshot saved successfully to ${folder.name} folder")
                    } else {
                        Log.e(TAG, "Failed to create thumbnail for: ${newFile.absolutePath}")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error creating thumbnail or saving screenshot data", e)
                }

                // Show success message
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@FloatingBubbleService,
                        "Screenshot successfully moved to ${folder.name}",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Remove the bubble
                    try {
                        if (::bubbleView.isInitialized && bubbleView.isAttachedToWindow) {
                            windowManager.removeView(bubbleView)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error handling screenshot drop", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@FloatingBubbleService,
                        "Failed to save screenshot: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Make sure to remove the bubble even if there's an error
                    removeBubble()
                }
            }
        }
    }



    /**
     * Show a preview of the screenshot
     */
    private fun showScreenshotPreview() {
        val screenshotPath = currentScreenshotPath ?: return

        // Launch preview activity
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("PREVIEW_SCREENSHOT", screenshotPath)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    }

    /**
     * Create the notification channel
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) { // O is API 26
            val name = "Floating Bubble Service"
            val descriptionText = "Shows floating bubbles for screenshots"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Wait for the screenshot file to be ready and then show it
     */
    private fun waitForScreenshotAndShow(screenshotPath: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var retryCount = 0
                val maxRetries = 10
                var fileReady = false
                var finalPath = screenshotPath

                while (!fileReady && retryCount < maxRetries) {
                    retryCount++
                    delay(500) // Wait 500ms between checks

                    // Check if the original path exists and has content
                    val originalFile = JavaFile(screenshotPath)
                    if (originalFile.exists() && originalFile.length() > 0) {
                        fileReady = true
                        finalPath = screenshotPath
                        Log.d(TAG, "Original screenshot file is now ready: $finalPath")
                        break
                    }

                    // If the path contains '.pending', check if a non-pending version exists
                    if (screenshotPath.contains(".pending")) {
                        val nonPendingPath = screenshotPath.replace(Regex("[.]pending-[0-9]+"), "")
                        val nonPendingFile = JavaFile(nonPendingPath)

                        if (nonPendingFile.exists() && nonPendingFile.length() > 0) {
                            fileReady = true
                            finalPath = nonPendingPath
                            Log.d(TAG, "Non-pending screenshot file found: $finalPath")
                            break
                        }
                    }

                    // Check the Screenshots directory for recent files
                    if (retryCount > 5) {
                        val screenshotsDir = JavaFile(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES), "Screenshots")

                        if (screenshotsDir.exists() && screenshotsDir.isDirectory) {
                            val files = screenshotsDir.listFiles()
                            files?.sortByDescending { it.lastModified() }

                            val recentFile = files?.firstOrNull {
                                it.isFile && it.length() > 0 &&
                                System.currentTimeMillis() - it.lastModified() < 10000 // Less than 10 seconds old
                            }

                            if (recentFile != null) {
                                fileReady = true
                                finalPath = recentFile.absolutePath
                                Log.d(TAG, "Found recent screenshot in Screenshots directory: $finalPath")
                                break
                            }
                        }
                    }

                    Log.d(TAG, "Waiting for screenshot file to be ready, attempt $retryCount")
                }

                if (fileReady) {
                    // Now show the floating bubble with the ready file
                    handleNewScreenshot(finalPath)
                } else {
                    Log.e(TAG, "Failed to get a ready screenshot file after $maxRetries attempts")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error waiting for screenshot", e)
            }
        }
    }

    /**
     * Find a recent image to test the floating bubble
     */
    private fun findRecentImageForTest() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d(TAG, "Finding recent image for test")
                val contentResolver = contentResolver
                val projection = arrayOf(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DATE_ADDED
                )

                // Get the most recent image
                val selection = "${MediaStore.Images.Media.MIME_TYPE} = ?"
                val selectionArgs = arrayOf("image/jpeg")
                val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

                contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder
                )?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val dataIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                        if (dataIndex >= 0) {
                            val imagePath = cursor.getString(dataIndex)
                            Log.d(TAG, "Found recent image for test: $imagePath")

                            // Show the floating bubble with this image
                            withContext(Dispatchers.Main) {
                                handleNewScreenshot(imagePath)
                                Toast.makeText(
                                    this@FloatingBubbleService,
                                    "Testing with image: $imagePath",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    } else {
                        Log.e(TAG, "No images found to test with")
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@FloatingBubbleService,
                                "No images found to test with",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } ?: run {
                    Log.e(TAG, "Query returned null cursor")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@FloatingBubbleService,
                            "Error accessing images",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error finding recent image for test", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@FloatingBubbleService,
                        "Error: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    /**
     * Create a thumbnail from a screenshot
     */
    private fun createThumbnail(sourcePath: String, destPath: String): Boolean {
        try {
            // Load the original bitmap with options to reduce memory usage
            val options = BitmapFactory.Options().apply {
                inSampleSize = 4  // Scale down by factor of 4
            }
            val originalBitmap = BitmapFactory.decodeFile(sourcePath, options) ?: return false

            // Create a scaled bitmap for the thumbnail
            val maxSize = 200
            val width = originalBitmap.width
            val height = originalBitmap.height
            val ratio = Math.min(maxSize.toFloat() / width, maxSize.toFloat() / height)

            val scaledBitmap = Bitmap.createScaledBitmap(
                originalBitmap,
                (width * ratio).toInt(),
                (height * ratio).toInt(),
                true
            )

            // Save the thumbnail
            JavaFileOutputStream(destPath).use { out ->
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
            }

            // Clean up
            if (scaledBitmap != originalBitmap) {
                scaledBitmap.recycle()
            }
            originalBitmap.recycle()

            return JavaFile(destPath).exists() && JavaFile(destPath).length() > 0
        } catch (e: Exception) {
            Log.e(TAG, "Error creating thumbnail", e)
            return false
        }
    }

    /**
     * Create a circular bitmap from a square bitmap
     */
    private fun getCircularBitmap(bitmap: Bitmap): Bitmap {
        try {
            // Create a new bitmap with the same dimensions
            val output = Bitmap.createBitmap(
                bitmap.width,
                bitmap.height,
                Bitmap.Config.ARGB_8888
            )

            // Create a canvas to draw on the new bitmap
            val canvas = Canvas(output)

            // Set up the paint
            val paint = Paint().apply {
                isAntiAlias = true
                color = Color.BLACK
            }

            // Create a rectangle representing the bitmap dimensions
            val rect = Rect(0, 0, bitmap.width, bitmap.height)

            // Draw a circle on the canvas
            canvas.drawCircle(
                bitmap.width / 2f,
                bitmap.height / 2f,
                Math.min(bitmap.width, bitmap.height) / 2f, // Use the smaller dimension for perfect circle
                paint
            )

            // Set the xfermode to clip the bitmap to the circle
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

            // Draw the bitmap onto the canvas with the circle mask
            canvas.drawBitmap(bitmap, rect, rect, paint)

            return output
        } catch (e: Exception) {
            Log.e(TAG, "Error creating circular bitmap", e)
            // Return the original bitmap if there's an error
            return bitmap
        }
    }

    /**
     * Vibrate the device to provide haptic feedback
     */
    private fun vibrateDevice() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                val vibrator = vibratorManager.defaultVibrator
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(100)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Remove the floating bubble
     */
    private fun removeBubble() {
        try {
            if (::bubbleView.isInitialized) {
                try {
                    windowManager.removeView(bubbleView)
                    Log.d(TAG, "Removed bubble view from window manager")
                } catch (e: Exception) {
                    Log.e(TAG, "Error removing bubble view", e)
                }
            }

            // Don't stop the service - we want it to keep running to detect more screenshots
            // Just clear the current screenshot path so we're ready for the next one
            currentScreenshotPath = null
            Log.d(TAG, "Bubble removed, service continues running")
        } catch (e: Exception) {
            Log.e(TAG, "Error in removeBubble", e)
        }
    }

    /**
     * Create the foreground service notification
     */
    private fun createNotification(): Notification {
        // Intent to open the app
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        // Intent to restart the service
        val restartIntent = Intent(this, FloatingBubbleService::class.java).apply {
            action = ACTION_RESTART_SERVICE
        }
        val restartPendingIntent = PendingIntent.getService(
            this,
            1,
            restartIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("PixelFlow is running")
            .setContentText("Monitoring for screenshots and ready to organize them")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .addAction(R.drawable.ic_notification, "Restart Detection", restartPendingIntent)
            .build()
    }
}
