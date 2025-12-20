package com.akslabs.pixelscreenshots.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import android.util.Log

import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.core.app.NotificationCompat
import com.akslabs.pixelscreenshots.MainActivity
import com.akslabs.pixelscreenshots.R
import com.akslabs.pixelscreenshots.data.SharedPrefsManager
import com.akslabs.pixelscreenshots.data.models.SimpleFolder
import com.akslabs.pixelscreenshots.data.models.SimpleScreenshot
import com.akslabs.pixelscreenshots.pixelFlowApp
import com.akslabs.pixelscreenshots.ui.components.compose.BubbleState
import com.akslabs.pixelscreenshots.ui.components.compose.CircularDragZone
import com.akslabs.pixelscreenshots.ui.components.compose.ComposeViewFactory
import com.akslabs.pixelscreenshots.ui.components.compose.DragZoneState
import com.akslabs.pixelscreenshots.ui.components.compose.FloatingBubble
import com.akslabs.pixelscreenshots.ui.components.compose.TouchHandler
import com.akslabs.pixelscreenshots.ui.components.compose.isPointInFlowerShape
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

/**
 * Service to display and manage the floating bubble using Jetpack Compose.
 */
class ComposeFloatingBubbleService : Service() {

    companion object {
        private const val TAG = "ComposeFloatingBubbleService"
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "PixelScreenshotsServiceChannel"

        // Action to restart the service
        const val ACTION_RESTART_SERVICE = "com.akslabs.pixelscreenshots.RESTART_SERVICE"
        const val ACTION_START_FROM_APP = "START_FROM_APP"

        // Flag to track if the service is running
        @Volatile
        private var isServiceRunning = false

        // Check if the service is running
        fun isRunning(): Boolean {
            return isServiceRunning
        }
    }

    // Service components
    private lateinit var windowManager: WindowManager
    private lateinit var screenshotDetector: ScreenshotDetector
    private lateinit var sharedPrefsManager: SharedPrefsManager

    // Coroutine scope for background tasks
    private var serviceJob: Job? = null
    private val serviceScope = CoroutineScope(Dispatchers.Default)

    // Views
    private var bubbleView: View? = null
    private var dragZonesView: View? = null
    private var previewView: View? = null

    // State for Compose
    private val bubbleState = mutableStateOf(BubbleState())
    private val dragZoneState = mutableStateOf(DragZoneState())

    // Service state
    private var currentScreenshotPath: String? = null
    private var folders: List<SimpleFolder> = emptyList()
    private var width: Int = 0
    private var height: Int = 0
    private var isDragging = false
    private var showingDragZones = false

    // Restart handler
    private var restartHandler: Handler? = null
    private var restartRunnable: Runnable? = null
    private val restartInterval = 60 * 60 * 1000L // 1 hour

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service onCreate called")

        isServiceRunning = true

        // Initialize components
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        screenshotDetector = ScreenshotDetector(this) { path -> handleNewScreenshot(path) }
        sharedPrefsManager = pixelFlowApp.sharedPrefsManager

        // Get screen dimensions
        val displayMetrics = resources.displayMetrics
        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels

        // Set up restart handler
        restartHandler = Handler(Looper.getMainLooper())
        restartRunnable = Runnable {
            Log.d(TAG, "Scheduled restart of service")
            val intent = Intent(this, ComposeFloatingBubbleService::class.java)
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

        // Start the periodic check
        restartHandler?.removeCallbacks(restartRunnable!!)
        restartHandler?.postDelayed(restartRunnable!!, restartInterval)
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

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.d(TAG, "Service onDestroy called")

        // Clean up resources
        isServiceRunning = false
        serviceJob?.cancel()
        serviceScope.cancel()
        restartHandler?.removeCallbacks(restartRunnable!!)
        screenshotDetector.stopObserving()

        // Remove any views
        removeBubble()
        hideDragZones()

        super.onDestroy()
    }

    /**
     * Creates the foreground service notification.
     */
    private fun createNotification(): Notification {
        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "PixelScreenshots Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Keeps the screenshot detection service running"
                setShowBadge(false)
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        // Create intent for notification click
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        // Build the notification
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("PixelScreenshots")
            .setContentText("Screenshot detection is active")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    /**
     * Starts the screenshot detection.
     */
    private fun startScreenshotDetection() {
        if (screenshotDetector.isObserving()) {
            Log.d(TAG, "Already observing for screenshots")
            return
        }

        // Load folders
        folders = sharedPrefsManager.foldersValue

        // Start observing for screenshots
        screenshotDetector.startObserving()
        Log.d(TAG, "Screenshot detector started")
    }

    /**
     * Handles a new screenshot.
     */
    private fun handleNewScreenshot(screenshotPath: String) {
        Log.d(TAG, "New screenshot detected: $screenshotPath")

        // Store the current screenshot path
        currentScreenshotPath = screenshotPath

        // Check if the file exists and is ready
        val screenshotFile = File(screenshotPath)
        if (!screenshotFile.exists() || screenshotFile.length() == 0L) {
            Log.d(TAG, "Screenshot file is still pending, waiting for it to be ready")
            waitForScreenshotFile(screenshotPath)
            return
        }

        Log.d(TAG, "Screenshot file exists, size: ${screenshotFile.length()} bytes")

        // Save the screenshot to the 'Unsorted' folder
        serviceScope.launch(Dispatchers.IO) {
            val unsortedFolder = sharedPrefsManager.getFolderByName("Unsorted")
            if (unsortedFolder != null) {
                saveScreenshotToFolder(screenshotPath, unsortedFolder)
            } else {
                Log.e(TAG, "Unsorted folder not found, cannot save screenshot")
            }
        }

        // Create and show the floating bubble with the screenshot
        serviceScope.launch(Dispatchers.Main) {
            try {
                Log.d(TAG, "Showing floating bubble for screenshot")

                // Check if we can draw overlays
                if (!Settings.canDrawOverlays(this@ComposeFloatingBubbleService)) {
                    Log.e(TAG, "Cannot draw overlays, permission not granted")
                    return@launch
                }

                showFloatingBubble(screenshotPath)
                Log.d(TAG, "Floating bubble shown successfully")
            } catch (e: Exception) {
                Log.e(TAG, "Error showing floating bubble", e)
            }
        }
    }

    /**
     * Waits for the screenshot file to be ready.
     */
    private fun waitForScreenshotFile(screenshotPath: String) {
        serviceScope.launch {
            var attempts = 0
            val maxAttempts = 5

            while (attempts < maxAttempts) {
                attempts++
                delay(500) // Wait 500ms between attempts

                Log.d(TAG, "Waiting for screenshot file to be ready, attempt $attempts")

                // Check if the file exists and has content
                val file = File(screenshotPath)
                if (file.exists() && file.length() > 0) {
                    handleNewScreenshot(screenshotPath)
                    return@launch
                }

                // If the file is still pending, try to find it in the Screenshots directory
                if (attempts >= maxAttempts) {
                    val screenshotsDir = File(
                        android.os.Environment.getExternalStoragePublicDirectory(
                            android.os.Environment.DIRECTORY_PICTURES
                        ), "Screenshots"
                    )

                    if (screenshotsDir.exists() && screenshotsDir.isDirectory) {
                        val files = screenshotsDir.listFiles()
                        val recentScreenshot = files?.maxByOrNull { it.lastModified() }

                        if (recentScreenshot != null && recentScreenshot.exists() && recentScreenshot.length() > 0) {
                            Log.d(TAG, "Found recent screenshot in Screenshots directory: ${recentScreenshot.absolutePath}")
                            handleNewScreenshot(recentScreenshot.absolutePath)
                            return@launch
                        }
                    }
                }
            }

            Log.e(TAG, "Failed to get screenshot after $maxAttempts attempts")
        }
    }

    /**
     * Tests the service with a recent screenshot.
     */
    private fun testWithRecentScreenshot() {
        serviceScope.launch {
            try {
                val screenshotsDir = File(
                    android.os.Environment.getExternalStoragePublicDirectory(
                        android.os.Environment.DIRECTORY_PICTURES
                    ), "Screenshots"
                )

                if (screenshotsDir.exists() && screenshotsDir.isDirectory) {
                    val files = screenshotsDir.listFiles()
                    val recentScreenshot = files?.maxByOrNull { it.lastModified() }

                    if (recentScreenshot != null && recentScreenshot.exists()) {
                        Log.d(TAG, "Testing with recent screenshot: ${recentScreenshot.absolutePath}")
                        handleNewScreenshot(recentScreenshot.absolutePath)
                    } else {
                        Log.e(TAG, "No screenshots found for testing")
                    }
                } else {
                    Log.e(TAG, "Screenshots directory not found")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error testing with recent screenshot", e)
            }
        }
    }

    /**
     * Shows the floating bubble with the screenshot.
     */
    private fun showFloatingBubble(screenshotPath: String) {
        try {
            // Load the screenshot bitmap
            val bitmap = loadScreenshotBitmap(screenshotPath) ?: return

            // Update the bubble state
            bubbleState.value = bubbleState.value.copy(
                bitmap = bitmap,
                isVisible = true,
                isDragging = false
            )

            // Create a ComposeView with the FloatingBubble using our factory
            val composeView = com.akslabs.pixelscreenshots.ui.components.compose.ComposeViewFactory.createComposeView(this) {
                com.akslabs.pixelscreenshots.ui.components.compose.FloatingBubble(
                    screenshotBitmap = bitmap,
                    size = androidx.compose.ui.unit.Dp(90f),
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
                        if (showingDragZones) {
                            // Check if we dropped on a zone
                            val params = bubbleView?.layoutParams as? WindowManager.LayoutParams
                            if (params != null) {
                                val droppedFolderId = checkDroppedOnZone(params.x.toFloat(), params.y.toFloat())
                                if (droppedFolderId != null) {
                                    handleScreenshotDrop(droppedFolderId)
                                }
                            }
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

            // Set up touch listener for dragging
            setupBubbleTouchListener(bubbleView!!, params)
        } catch (e: Exception) {
            Log.e(TAG, "Error showing floating bubble", e)
        }
    }

    // Extension function to convert dp to pixels
    private fun Float.toPx(): Float = this * resources.displayMetrics.density

    // Extension property to represent dp values
    private val Int.dp: Int
        get() = this

    // Extension function to convert dp to pixels
    private fun Int.toPx(): Float = this.toFloat() * resources.displayMetrics.density

    /**
     * Loads a bitmap from the screenshot path.
     */
    private fun loadScreenshotBitmap(screenshotPath: String): Bitmap? {
        try {
            // Check if the screenshot file exists
            val screenshotFile = File(screenshotPath)
            if (!screenshotFile.exists()) {
                Log.e(TAG, "Screenshot file does not exist: $screenshotPath")
                return null
            }

            // Decode the bitmap with options to prevent OutOfMemoryError
            val options = BitmapFactory.Options().apply {
                inSampleSize = 2  // Scale down by factor of 2
            }
            val bitmap = BitmapFactory.decodeFile(screenshotPath, options)

            if (bitmap == null) {
                Log.e(TAG, "Failed to decode bitmap from: $screenshotPath")
                return null
            }

            return bitmap
        } catch (e: Exception) {
            Log.e(TAG, "Error loading screenshot bitmap", e)
            return null
        }
    }

    /**
     * Composable function for the floating bubble.
     */
    @OptIn(androidx.compose.animation.ExperimentalAnimationApi::class)
    @Composable
    private fun FloatingBubbleContent(
        state: MutableState<BubbleState>,
        onDrag: (Offset) -> Unit,
        onClick: () -> Unit
    ) {
        AnimatedVisibility(
            visible = state.value.isVisible,
            enter = fadeIn() + scaleIn(initialScale = 0.5f),
            exit = fadeOut() + scaleOut()
        ) {
            state.value.bitmap?.let { bitmap ->
                FloatingBubble(
                    screenshotBitmap = bitmap,
                    isDragging = state.value.isDragging,
                    onClick = onClick,
                    onDrag = onDrag
                )
            }
        }
    }

    /**
     * Sets up the touch listener for the bubble.
     */
    private fun setupBubbleTouchListener(view: View, params: WindowManager.LayoutParams) {
        // We're now handling touch events in the Compose component
        // This method is kept for compatibility with the existing code
        // No need to set up touch listeners as they're handled in the Compose component
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
     * Shows the screenshot preview.
     */
    private fun showScreenshotPreview() {
        try {
            // Log that the preview was requested
            Log.d(TAG, "Screenshot preview requested")

            // Create a ComposeView with the ScreenshotPreview using our factory
            val composeView = com.akslabs.pixelscreenshots.ui.components.compose.ComposeViewFactory.createComposeView(this) {
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
     * Shares the current screenshot.
     */
    private fun shareScreenshot() {
        val screenshotPath = currentScreenshotPath ?: return

        // Create a share intent
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/png"

        // Get the file URI
        val fileUri = androidx.core.content.FileProvider.getUriForFile(
            this,
            "${packageName}.fileprovider",
            java.io.File(screenshotPath)
        )

        // Add the URI to the intent
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        // Create a chooser and start it
        val chooserIntent = Intent.createChooser(shareIntent, "Share Screenshot")
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(chooserIntent)

        // Hide the preview
        hideScreenshotPreview()
    }

    /**
     * Edits the current screenshot.
     */
    private fun editScreenshot() {
        // For now, just log that the edit was requested
        Log.d(TAG, "Screenshot edit requested")

        // Hide the preview
        hideScreenshotPreview()
    }

    /**
     * Deletes the current screenshot.
     */
    private fun deleteScreenshot() {
        val screenshotPath = currentScreenshotPath ?: return

        // Delete the file
        val file = java.io.File(screenshotPath)
        if (file.exists()) {
            if (file.delete()) {
                Log.d(TAG, "Screenshot deleted: $screenshotPath")

                // Notify the media scanner about the deleted file
                val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                val contentUri = android.net.Uri.fromFile(file)
                mediaScanIntent.data = contentUri
                sendBroadcast(mediaScanIntent)
            } else {
                Log.e(TAG, "Failed to delete screenshot: $screenshotPath")
            }
        }

        // Hide the preview and bubble
        hideScreenshotPreview()
        hideBubble()
    }

    /**
     * Checks if the bubble is over any drag zones and highlights the appropriate one.
     */
    private fun checkDragZoneHighlight(x: Float, y: Float): Int {
        // Calculate which zone is under the bubble
        val zoneIndex = calculateZoneIndex(x, y)

        // Update the drag zone state if the highlight changed
        if (zoneIndex != dragZoneState.value.highlightedIndex) {
            dragZoneState.value = dragZoneState.value.copy(highlightedIndex = zoneIndex)
        }

        return zoneIndex
    }

    /**
     * Calculates which zone is under the given coordinates.
     */
    private fun calculateZoneIndex(x: Float, y: Float): Int {
        if (folders.isEmpty()) return -1

        // Calculate zone positions
        val centerX = width / 2f
        val centerY = height - 200f
        val centerDistance = minOf(width, height) * 0.4f

        val folderCount = folders.size
        val angleRange = if (folderCount <= 5) 180f else 270f
        val startAngle = if (folderCount <= 5) 180f else 135f
        val angleStep = angleRange / folderCount

        // Check each zone
        folders.forEachIndexed { index, _ ->
            val angleInDegrees = startAngle + index * angleStep
            val angleInRadians = Math.toRadians(angleInDegrees.toDouble())

            val zoneX = centerX + centerDistance * cos(angleInRadians).toFloat()
            val zoneY = centerY + centerDistance * sin(angleInRadians).toFloat()

            // Check if the point is in the zone
            if (isPointInFlowerShape(x, y, zoneX, zoneY, 130f)) {
                return index
            }
        }

        return -1
    }

    /**
     * Checks if the bubble was dropped on a zone and returns the folder ID if so.
     */
    private fun checkDroppedOnZone(x: Float, y: Float): Long? {
        val highlightedIndex = checkDragZoneHighlight(x, y)
        return if (highlightedIndex >= 0 && highlightedIndex < folders.size) {
            folders[highlightedIndex].id
        } else {
            null
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
            val composeView = com.akslabs.pixelscreenshots.ui.components.compose.ComposeViewFactory.createComposeView(this) {
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
     * Composable function for the drag zones.
     */
    @OptIn(androidx.compose.animation.ExperimentalAnimationApi::class)
    @Composable
    private fun DragZonesContent(
        state: MutableState<DragZoneState>,
        onFolderSelected: (Long) -> Unit
    ) {
        AnimatedVisibility(
            visible = state.value.isVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            CircularDragZone(
                folders = state.value.folders,
                highlightedIndex = state.value.highlightedIndex,
                onFolderSelected = onFolderSelected
            )
        }
    }

    /**
     * Hides the drag zones.
     */
    private fun hideDragZones() {
        try {
            if (dragZonesView != null) {
                windowManager.removeView(dragZonesView)
                dragZonesView = null
            }

            // Update state
            dragZoneState.value = dragZoneState.value.copy(
                isVisible = false,
                highlightedIndex = -1
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error hiding drag zones", e)
        }
    }

    /**
     * Removes the floating bubble.
     */
    private fun removeBubble() {
        try {
            if (bubbleView != null) {
                windowManager.removeView(bubbleView)
                bubbleView = null
            }

            // Update state
            bubbleState.value = bubbleState.value.copy(
                isVisible = false,
                isDragging = false
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error removing bubble", e)
        }
    }

    /**
     * Handles dropping a screenshot on a folder.
     */
    private fun handleScreenshotDrop(folderId: Long) {
        val screenshotPath = currentScreenshotPath ?: return
        val folder = sharedPrefsManager.getFolder(folderId) ?: return

        serviceScope.launch(Dispatchers.IO) {
            saveScreenshotToFolder(screenshotPath, folder)
        }
    }

    /**
     * Saves a screenshot to a specific folder.
     */
    private fun saveScreenshotToFolder(screenshotPath: String, folder: SimpleFolder) {
        try {
            // Check if the screenshot file exists
            val screenshotFile = File(screenshotPath)
            if (!screenshotFile.exists()) {
                Log.e(TAG, "Screenshot file does not exist: $screenshotPath")
                return
            }

            val timestamp = System.currentTimeMillis()
            val newFileName = "screenshot_${timestamp}.jpg"

            // Get the folder directory in external storage
            val folderDir = sharedPrefsManager.getFolderDirectory(folder.name)
            if (!folderDir.exists() && !folderDir.mkdirs()) {
                Log.e(TAG, "Failed to create folder directory: ${folderDir.absolutePath}")
                return
            }

            val newFile = File(folderDir, newFileName)

            try {
                // Move the file (copy then delete original)
                screenshotFile.inputStream().use { input ->
                    FileOutputStream(newFile).use { output ->
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
                // Create thumbnail directory if it doesn't exist
                val thumbnailDir = File(folderDir, "thumbnails")
                if (!thumbnailDir.exists() && !thumbnailDir.mkdirs()) {
                    Log.e(TAG, "Failed to create thumbnail directory: ${thumbnailDir.absolutePath}")
                }

                val thumbnailFile = File(thumbnailDir, "thumb_$newFileName")
                val thumbnailCreated = createThumbnail(newFile.absolutePath, thumbnailFile.absolutePath)

                if (thumbnailCreated) {
                    // Save to database
                    val screenshot = SimpleScreenshot(
                        id = sharedPrefsManager.generateScreenshotId(),
                        filePath = newFile.absolutePath,
                        thumbnailPath = thumbnailFile.absolutePath,
                        folderId = folder.id,
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

            // Vibrate to provide feedback
            vibrateDevice()

            Log.d(TAG, "Screenshot added to folder: ${folder.id}")
        } catch (e: Exception) {
            Log.e(TAG, "Error handling screenshot drop", e)
        }
    }

    /**
     * Vibrates the device to provide feedback.
     */
    private fun vibrateDevice() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(50)
        }
    }

    /**
     * Creates a thumbnail from a screenshot.
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
            FileOutputStream(destPath).use { out ->
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
            }

            // Clean up
            if (scaledBitmap != originalBitmap) {
                scaledBitmap.recycle()
            }
            originalBitmap.recycle()

            return File(destPath).exists() && File(destPath).length() > 0
        } catch (e: Exception) {
            Log.e(TAG, "Error creating thumbnail", e)
            return false
        }
    }
}
