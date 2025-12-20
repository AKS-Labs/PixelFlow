package com.akslabs.pixelscreenshots.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import com.akslabs.pixelscreenshots.MainActivity
import com.akslabs.pixelscreenshots.R
import com.akslabs.pixelscreenshots.data.models.SimpleFolder
import com.akslabs.pixelscreenshots.data.models.SimpleScreenshot
import com.akslabs.pixelscreenshots.services.ScreenshotDetector
import com.akslabs.pixelscreenshots.pixelFlowApp
import com.akslabs.pixelscreenshots.ui.components.compose.BubbleState
import com.akslabs.pixelscreenshots.ui.components.compose.ComposeViewFactory
import com.akslabs.pixelscreenshots.ui.components.compose.DragZoneState
import com.akslabs.pixelscreenshots.ui.components.compose.FloatingBubble
import com.akslabs.pixelscreenshots.utils.BitmapUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Service to display and manage the floating bubble using Jetpack Compose only.
 * This implementation avoids using any XML layouts or traditional Views.
 */
class ComposeOnlyFloatingBubbleService : Service() {

    companion object {
        private const val TAG = "ComposeOnlyBubbleService"
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "PixelScreenshotsServiceChannel"

        // Action to restart the service
        const val ACTION_RESTART_SERVICE = "com.akslabs.pixelscreenshots.RESTART_SERVICE"

        // Flag to track if the service is running
        @Volatile
        private var isServiceRunning = false

        // Check if the service is running
        fun isRunning(): Boolean {
            return isServiceRunning
        }
    }

    // Window manager for adding views
    private lateinit var windowManager: WindowManager

    // Screen dimensions
    private var width: Int = 0
    private var height: Int = 0

    // Screenshot detector
    private lateinit var screenshotDetector: ScreenshotDetector

    // Service scope for coroutines
    private val serviceScope = CoroutineScope(Dispatchers.Default)
    private var serviceJob: Job? = null

    // Views
    private var bubbleView: android.view.View? = null
    private var dragZonesView: android.view.View? = null
    private var previewView: android.view.View? = null

    // State
    private var currentScreenshotPath: String? = null
    private var folders: List<SimpleFolder> = emptyList()
    private var showingDragZones = false

    // Bubble and drag zone states
    private val bubbleState = mutableStateOf(BubbleState())
    private val dragZoneState = mutableStateOf(DragZoneState())

    // Restart handler
    private var restartHandler: Handler? = null
    private var restartRunnable: Runnable? = null
    private val restartInterval = 60 * 60 * 1000L // 1 hour

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service onCreate")

        isServiceRunning = true

        // Initialize window manager
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        // Get screen dimensions
        val displayMetrics = resources.displayMetrics
        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels

        // Initialize screenshot detector
        screenshotDetector = ScreenshotDetector(this) { path ->
            handleNewScreenshot(path)
        }

        // Load folders
        refreshFolders()

        // Set up restart handler
        restartHandler = Handler(Looper.getMainLooper())
        restartRunnable = Runnable {
            Log.d(TAG, "Scheduled restart of service")
            val intent = Intent(this, ComposeOnlyFloatingBubbleService::class.java)
            intent.action = ACTION_RESTART_SERVICE
            startService(intent)

            // Schedule next restart
            restartHandler?.postDelayed(restartRunnable!!, restartInterval)
        }

        // Start a new job for this service instance
        serviceJob = serviceScope.launch {
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
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service onStartCommand called with action: ${intent?.action}")

        // Start foreground service with notification
        startForeground(NOTIFICATION_ID, createNotification())

        when (intent?.action) {
            "START_ON_BOOT" -> {
                Log.d(TAG, "Service starting after boot")
                startScreenshotDetection()
            }
            "START_FROM_APP" -> {
                Log.d(TAG, "Service starting from app")
                startScreenshotDetection()
            }
            "MANUAL_TEST" -> {
                Log.d(TAG, "Manual test requested")
                testWithRecentScreenshot()
            }
            "STOP_SERVICE" -> {
                Log.d(TAG, "Stop service requested")
                stopSelf()
                return START_NOT_STICKY
            }
            ACTION_RESTART_SERVICE -> {
                Log.d(TAG, "Service restarting from scheduled task")
                startScreenshotDetection()
            }
        }

        // Schedule a restart after 1 hour to ensure the service keeps running
        restartHandler?.removeCallbacks(restartRunnable!!)
        restartHandler?.postDelayed(restartRunnable!!, restartInterval)

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.d(TAG, "Service onDestroy")

        isServiceRunning = false

        // Clean up resources
        screenshotDetector.stopObserving()

        // Cancel coroutines
        serviceJob?.cancel()
        serviceScope.cancel()

        // Remove callbacks
        restartHandler?.removeCallbacks(restartRunnable!!)

        // Remove views
        removeBubble()
        hideDragZones()
        removePreview()

        super.onDestroy()
    }

    /**
     * Creates a notification for the foreground service.
     */
    private fun createNotification(): Notification {
        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Pixel Screenshots Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Keeps Pixel Screenshots running to detect screenshots"
                setShowBadge(false)
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        // Create a pending intent for the notification
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        // Build the notification
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("PixelScreenshots is running")
            .setContentText("Detecting screenshots in background")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()
    }

    /**
     * Starts the screenshot detection.
     */
    private fun startScreenshotDetection() {
        Log.d(TAG, "Starting screenshot detection")
        screenshotDetector.startObserving()
    }

    /**
     * Handles a new screenshot.
     */
    private fun handleNewScreenshot(screenshotPath: String) {
        try {
            Log.d(TAG, "New screenshot detected: $screenshotPath")

            // Save the current screenshot path
            currentScreenshotPath = screenshotPath

            // Check if the file exists
            val screenshotFile = File(screenshotPath)
            if (!screenshotFile.exists()) {
                Log.e(TAG, "Screenshot file does not exist: $screenshotPath")
                return
            }

            Log.d(TAG, "Screenshot file exists, size: ${screenshotFile.length()} bytes")

            // Create and show the floating bubble with the screenshot
            serviceScope.launch(Dispatchers.Main) {
                try {
                    Log.d(TAG, "Showing floating bubble for screenshot")

                    // Check if we can draw overlays
                    if (!Settings.canDrawOverlays(this@ComposeOnlyFloatingBubbleService)) {
                        Log.e(TAG, "Cannot draw overlays, permission not granted")
                        return@launch
                    }

                    showFloatingBubble(screenshotPath)
                    Log.d(TAG, "Floating bubble shown successfully")
                } catch (e: Exception) {
                    Log.e(TAG, "Error showing floating bubble", e)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error handling new screenshot", e)
        }
    }

    /**
     * Waits for the screenshot file to be ready.
     */
    private fun waitForScreenshotFile(screenshotPath: String) {
        serviceScope.launch {
            var attempts = 0
            val maxAttempts = 5
            var fileReady = false

            while (attempts < maxAttempts && !fileReady) {
                attempts++

                // Check if the file exists and has content
                val file = File(screenshotPath)
                if (file.exists() && file.length() > 0) {
                    Log.d(TAG, "Screenshot file is now ready after $attempts attempts")
                    fileReady = true

                    // Show the floating bubble
                    showFloatingBubble(screenshotPath)
                } else {
                    Log.d(TAG, "Screenshot file not ready yet, attempt $attempts of $maxAttempts")
                    delay(500) // Wait 500ms before checking again
                }
            }

            if (!fileReady) {
                Log.e(TAG, "Screenshot file never became ready after $maxAttempts attempts")
            }
        }
    }

    /**
     * Tests the service with a recent screenshot.
     */
    private fun testWithRecentScreenshot() {
        serviceScope.launch {
            try {
                // Get a recent screenshot from SharedPrefsManager
                val sharedPrefsManager = pixelFlowApp.sharedPrefsManager
                val recentScreenshots = sharedPrefsManager.screenshotsValue

                if (recentScreenshots.isNotEmpty()) {
                    val screenshot = recentScreenshots[0]
                    Log.d(TAG, "Testing with recent screenshot: ${screenshot.filePath}")
                    handleNewScreenshot(screenshot.filePath)
                } else {
                    Log.d(TAG, "No recent screenshots found for testing")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error testing with recent screenshot", e)
            }
        }
    }

    /**
     * Refreshes the folder list.
     */
    private fun refreshFolders() {
        serviceScope.launch {
            try {
                val sharedPrefsManager = pixelFlowApp.sharedPrefsManager
                folders = sharedPrefsManager.foldersValue
                Log.d(TAG, "Refreshed folders: ${folders.size}")
            } catch (e: Exception) {
                Log.e(TAG, "Error refreshing folders", e)
            }
        }
    }

    /**
     * Shows the floating bubble with the screenshot.
     */
    private fun showFloatingBubble(screenshotPath: String) {
        try {
            Log.d(TAG, "Showing floating bubble for screenshot: $screenshotPath")

            // Remove existing bubble if any
            removeBubble()

            // Load the bitmap
            val bitmap = BitmapUtils.loadBitmapFromFile(screenshotPath)
            if (bitmap == null) {
                Log.e(TAG, "Failed to load bitmap from file: $screenshotPath")
                return
            }

            // Update the bubble state
            bubbleState.value = bubbleState.value.copy(
                bitmap = bitmap,
                isVisible = true,
                isDragging = false
            )

            // Create a ComposeView with the FloatingBubble using our factory
            val composeView = ComposeViewFactory.createComposeView(this) {
                FloatingBubble(
                    screenshotBitmap = bitmap,
                    size = Dp(90f),
                    isDragging = bubbleState.value.isDragging,
                    onClick = { showScreenshotPreview() },
                    onDrag = { offset -> handleBubbleDrag(offset) },
                    onDragStart = {
                        bubbleState.value = bubbleState.value.copy(isDragging = true)
                        if (!showingDragZones) {
                            showDragZones()
                            showingDragZones = true
                        }
                    },
                    onDragEnd = {
                        bubbleState.value = bubbleState.value.copy(isDragging = false)
                        // Check if we should hide drag zones
                        if (showingDragZones) {
                            hideDragZones()
                            showingDragZones = false
                        }
                    }
                )
            }

            // Store the view
            bubbleView = composeView

            // Set up window parameters for the bubble
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

            // Position the bubble on the right side near volume keys
            params.gravity = Gravity.TOP or Gravity.START
            params.x = width - 100  // Near right edge
            params.y = 300 // Position near volume keys

            // Add the bubble to the window
            windowManager.addView(bubbleView, params)

        } catch (e: Exception) {
            Log.e(TAG, "Error showing floating bubble", e)
        }
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
     * Handles dragging of the bubble.
     */
    private fun handleBubbleDrag(offset: Offset) {
        if (bubbleView == null) return

        // Get the current layout parameters
        val params = bubbleView?.layoutParams as? WindowManager.LayoutParams ?: return

        // Update the position
        params.x += offset.x.toInt()
        params.y += offset.y.toInt()

        // Ensure the bubble stays within screen bounds
        params.x = params.x.coerceIn(0, width - 200)
        params.y = params.y.coerceIn(0, height - 200)

        // Update the view
        windowManager.updateViewLayout(bubbleView, params)

        // Check if we're over any drag zones
        checkDragZoneHighlight(params.x.toFloat(), params.y.toFloat())
    }

    /**
     * Checks if the bubble is over a drag zone and highlights it.
     */
    private fun checkDragZoneHighlight(x: Float, y: Float) {
        // Calculate the center of the bubble
        val bubbleSize = 90.dp.toPx()
        val centerX = x + bubbleSize / 2
        val centerY = y + bubbleSize / 2

        // Check each folder zone
        var highlightedIndex = -1
        for (i in folders.indices) {
            // This is a simplified check - in a real app, you'd check if the point is within the zone
            // For now, we'll just use a simple distance check to the center of each zone
            val zoneX = width / 2f
            val zoneY = height - 200f - (i * 120f)
            val distance = Math.sqrt(Math.pow((centerX - zoneX).toDouble(), 2.0) +
                                    Math.pow((centerY - zoneY).toDouble(), 2.0))

            if (distance < 150) {
                highlightedIndex = i
                break
            }
        }

        // Update the drag zone state if the highlighted index changed
        if (dragZoneState.value.highlightedIndex != highlightedIndex) {
            dragZoneState.value = dragZoneState.value.copy(highlightedIndex = highlightedIndex)
        }
    }

    /**
     * Shows the drag zones.
     */
    private fun showDragZones() {
        try {
            // Update the drag zone state
            dragZoneState.value = dragZoneState.value.copy(
                folders = folders,
                isVisible = true,
                highlightedIndex = -1
            )

            // Create a ComposeView with the CircularDragZone using our factory
            val composeView = ComposeViewFactory.createComposeView(this) {
                com.akslabs.pixelscreenshots.ui.components.compose.CircularDragZone(
                    folders = folders,
                    highlightedIndex = dragZoneState.value.highlightedIndex,
                    onFolderSelected = { folderId -> handleScreenshotDrop(folderId) }
                )
            }

            // Store the view
            dragZonesView = composeView

            // Set up window parameters for the drag zones
            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                if (Build.VERSION.SDK_INT >= 26)
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                else
                    WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT
            )

            // Add the drag zones to the window
            windowManager.addView(dragZonesView, params)
        } catch (e: Exception) {
            Log.e(TAG, "Error showing drag zones", e)
        }
    }

    /**
     * Hides the drag zones.
     */
    private fun hideDragZones() {
        if (dragZonesView != null) {
            try {
                windowManager.removeView(dragZonesView)
                dragZonesView = null
                dragZoneState.value = dragZoneState.value.copy(isVisible = false)
                Log.d(TAG, "Drag zones hidden")
            } catch (e: Exception) {
                Log.e(TAG, "Error hiding drag zones", e)
            }
        }
    }

    /**
     * Handles dropping a screenshot on a folder.
     */
    private fun handleScreenshotDrop(folderId: Long) {
        Log.d(TAG, "Screenshot dropped on folder: $folderId")

        // Get the current screenshot path
        val screenshotPath = currentScreenshotPath
        if (screenshotPath == null) {
            Log.e(TAG, "No current screenshot path")
            return
        }

        // Move the screenshot to the folder
        serviceScope.launch {
            try {
                // Get the folder
                val sharedPrefsManager = pixelFlowApp.sharedPrefsManager
                val folder = sharedPrefsManager.foldersValue.find { it.id == folderId }
                if (folder == null) {
                    Log.e(TAG, "Folder not found: $folderId")
                    return@launch
                }

                // Create a new screenshot object
                val screenshot = SimpleScreenshot(
                    id = sharedPrefsManager.generateScreenshotId(),
                    filePath = screenshotPath,
                    thumbnailPath = screenshotPath,
                    folderId = folderId,
                    originalTimestamp = System.currentTimeMillis()
                )

                // Save the screenshot to SharedPrefsManager
                sharedPrefsManager.addScreenshot(screenshot)

                Log.d(TAG, "Screenshot saved to folder: ${folder.name}")

                // Hide the bubble and drag zones
                launch(Dispatchers.Main) {
                    removeBubble()
                    hideDragZones()
                    showingDragZones = false
                }

                // Vibrate to indicate success
                vibrateDevice()

            } catch (e: Exception) {
                Log.e(TAG, "Error handling screenshot drop", e)
            }
        }
    }

    /**
     * Shows the screenshot preview.
     */
    private fun showScreenshotPreview() {
        try {
            // Log that the preview was requested
            Log.d(TAG, "Screenshot preview requested")

            // Create a ComposeView with the ScreenshotPreview using our factory
            val composeView = ComposeViewFactory.createComposeView(this) {
                com.akslabs.pixelscreenshots.ui.components.compose.ScreenshotPreview(
                    isVisible = true,
                    screenshotBitmap = bubbleState.value.bitmap,
                    onClose = { hideScreenshotPreview() },
                    onShare = { shareScreenshot() },
                    onEdit = { editScreenshot() },
                    onDelete = { deleteScreenshot() }
                )
            }

            // Store the view
            previewView = composeView

            // Set up window parameters for the preview
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

            // Add the preview to the window
            windowManager.addView(previewView, params)

            // Hide the bubble while showing the preview
            hideBubble()
        } catch (e: Exception) {
            Log.e(TAG, "Error showing screenshot preview", e)
        }
    }

    /**
     * Hides the screenshot preview.
     */
    private fun hideScreenshotPreview() {
        try {
            if (previewView != null) {
                windowManager.removeView(previewView)
                previewView = null
            }

            // Show the bubble again
            showBubble()
        } catch (e: Exception) {
            Log.e(TAG, "Error hiding screenshot preview", e)
        }
    }

    /**
     * Shows the bubble again after it was hidden.
     */
    private fun showBubble() {
        if (bubbleView != null || currentScreenshotPath == null) return

        // Show the bubble with the current screenshot
        showFloatingBubble(currentScreenshotPath!!)
    }

    /**
     * Hides the bubble temporarily.
     */
    private fun hideBubble() {
        try {
            if (bubbleView != null) {
                windowManager.removeView(bubbleView)
                bubbleView = null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error hiding bubble", e)
        }
    }

    /**
     * Removes the preview view.
     */
    private fun removePreview() {
        if (previewView != null) {
            try {
                windowManager.removeView(previewView)
                previewView = null
                Log.d(TAG, "Preview removed")
            } catch (e: Exception) {
                Log.e(TAG, "Error removing preview", e)
            }
        }
    }

    /**
     * Shares the current screenshot.
     */
    private fun shareScreenshot() {
        Log.d(TAG, "Share screenshot requested")
        // Implementation for sharing the screenshot
    }

    /**
     * Edits the current screenshot.
     */
    private fun editScreenshot() {
        Log.d(TAG, "Edit screenshot requested")
        // Implementation for editing the screenshot
    }

    /**
     * Deletes the current screenshot.
     */
    private fun deleteScreenshot() {
        Log.d(TAG, "Delete screenshot requested")
        // Implementation for deleting the screenshot
    }

    /**
     * Vibrates the device to provide haptic feedback.
     */
    private fun vibrateDevice() {
        try {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as android.os.VibratorManager
                vibratorManager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                getSystemService(Context.VIBRATOR_SERVICE) as android.os.Vibrator
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
     * Extension function to convert dp to pixels.
     */
    private fun Dp.toPx(): Float {
        return this.value * resources.displayMetrics.density
    }
}
