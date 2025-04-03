package com.aks_labs.pixelflow.services

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
import android.os.IBinder
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import kotlin.math.abs

/**
 * Service to display and manage the floating bubble
 */
class FloatingBubbleService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var bubbleView: View
    private lateinit var dragZonesView: View

    private lateinit var screenshotDetector: ScreenshotDetector
    private lateinit var sharedPrefsManager: SharedPrefsManager

    private var currentScreenshotPath: String? = null
    private var folders: List<SimpleFolder> = emptyList()
    private var serviceJob: Job? = null

    private val NOTIFICATION_ID = 1001
    private val CHANNEL_ID = "floating_bubble_channel"

    override fun onCreate() {
        super.onCreate()

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
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Create notification channel for foreground service
        createNotificationChannel()

        // Start as foreground service with notification
        startForeground(NOTIFICATION_ID, createNotification())

        // Start observing for screenshots
        screenshotDetector.startObserving()

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()

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
            e.printStackTrace()
        }

        // Cancel coroutines
        serviceJob?.cancel()
    }

    /**
     * Handle a new screenshot being taken
     */
    private fun handleNewScreenshot(screenshotPath: String) {
        currentScreenshotPath = screenshotPath

        // Create and show the floating bubble with the screenshot
        CoroutineScope(Dispatchers.Main).launch {
            showFloatingBubble(screenshotPath)
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

        // Inflate the bubble layout
        bubbleView = LayoutInflater.from(this).inflate(R.layout.floating_bubble, null)

        // Set the screenshot as the bubble image
        val bubbleImageView = bubbleView.findViewById<ImageView>(R.id.bubble_image)
        val bitmap = BitmapFactory.decodeFile(screenshotPath)
        bubbleImageView.setImageBitmap(bitmap)

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

        params.gravity = Gravity.TOP or Gravity.START
        params.x = 100
        params.y = 100

        // Add the bubble to the window
        windowManager.addView(bubbleView, params)

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
                        params.x = initialX + dx.toInt()
                        params.y = initialY + dy.toInt()

                        // Update the bubble position
                        windowManager.updateViewLayout(view, params)

                        // Check if bubble is over any drag zone
                        if (::dragZonesView.isInitialized) {
                            val dragZone = dragZonesView.findViewById<CircularDragZone>(R.id.circular_drag_zone)
                            dragZone?.highlightZoneAt(event.rawX, event.rawY)
                        }
                    }
                    true
                }

                MotionEvent.ACTION_UP -> {
                    if (!isDragging) {
                        // It was a click, not a drag
                        // Handle preview functionality here
                        showScreenshotPreview()
                    } else {
                        // It was a drag, check if dropped on a zone
                        if (::dragZonesView.isInitialized) {
                            val dragZone = dragZonesView.findViewById<CircularDragZone>(R.id.circular_drag_zone)
                            val folderId = dragZone?.getFolderIdAt(event.rawX, event.rawY)

                            if (folderId != null) {
                                // Screenshot was dropped on a folder
                                handleScreenshotDrop(folderId)
                            }
                        }

                        // Hide drag zones
                        hideDragZones()
                        showingDragZones = false
                    }
                    true
                }

                else -> false
            }
        }
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

        // Inflate the drag zones layout
        dragZonesView = LayoutInflater.from(this).inflate(R.layout.circular_drag_zones, null)

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

        // Set up the folders in the drag zone
        val dragZone = dragZonesView.findViewById<CircularDragZone>(R.id.circular_drag_zone)
        dragZone?.setFolders(folders)
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
                val folder = sharedPrefsManager.getFolder(folderId) ?: return@launch

                // Create a copy of the screenshot in our app's files directory
                val screenshotFile = File(screenshotPath)
                val timestamp = System.currentTimeMillis()
                val newFileName = "screenshot_${timestamp}.jpg"
                val internalDir = File(filesDir, "screenshots/${folder.name}")
                internalDir.mkdirs()

                val newFile = File(internalDir, newFileName)

                // Copy the file
                screenshotFile.inputStream().use { input ->
                    FileOutputStream(newFile).use { output ->
                        input.copyTo(output)
                    }
                }

                // Create a thumbnail
                val thumbnailDir = File(filesDir, "thumbnails/${folder.name}")
                thumbnailDir.mkdirs()
                val thumbnailFile = File(thumbnailDir, "thumb_$newFileName")
                createThumbnail(screenshotPath, thumbnailFile.absolutePath)

                // Save to database
                val screenshot = SimpleScreenshot(
                    id = sharedPrefsManager.generateScreenshotId(),
                    filePath = newFile.absolutePath,
                    thumbnailPath = thumbnailFile.absolutePath,
                    folderId = folderId,
                    originalTimestamp = screenshotFile.lastModified()
                )

                sharedPrefsManager.addScreenshot(screenshot)

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
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@FloatingBubbleService,
                        "Failed to save screenshot: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    /**
     * Create a thumbnail from the original screenshot
     */
    private fun createThumbnail(sourcePath: String, destPath: String) {
        try {
            // Load the original bitmap
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            BitmapFactory.decodeFile(sourcePath, options)

            // Calculate sample size
            options.inSampleSize = calculateInSampleSize(options, 200, 200)

            // Decode with sample size
            options.inJustDecodeBounds = false
            val bitmap = BitmapFactory.decodeFile(sourcePath, options)

            // Save the thumbnail
            FileOutputStream(destPath).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            }

            bitmap.recycle()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Calculate sample size for bitmap downsampling
     */
    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
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
     * Create the foreground service notification
     */
    private fun createNotification(): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("PixelFlow")
            .setContentText("Monitoring for screenshots")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }
}
