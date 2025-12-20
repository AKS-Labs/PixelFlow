package com.akslabs.pixelscreenshots.services

import android.content.ContentResolver
import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Class to detect screenshots taken on the device
 */
class ScreenshotDetector(
    private val context: Context,
    private val onScreenshotTaken: (String) -> Unit
) {
    companion object {
        private const val TAG = "ScreenshotDetector"
    }
    private var contentObserver: ContentObserver? = null
    private var isObserving = false
    private var lastCheckedTimestamp = System.currentTimeMillis() / 1000

    /**
     * Check if the detector is currently observing
     */
    fun isObserving(): Boolean {
        return isObserving
    }

    /**
     * Start observing for screenshots
     */
    fun startObserving() {
        if (isObserving) {
            Log.d(TAG, "Already observing for screenshots")
            return
        }

        Log.d(TAG, "Starting screenshot observation")
        val contentResolver = context.contentResolver
        val handler = Handler(Looper.getMainLooper())

        // Update the timestamp to now
        lastCheckedTimestamp = System.currentTimeMillis() / 1000

        contentObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean, uri: Uri?) {
                super.onChange(selfChange, uri)
                Log.d(TAG, "Content change detected: $uri")
                uri?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            checkIfScreenshot(contentResolver, it)
                        } catch (e: Exception) {
                            Log.e(TAG, "Error checking screenshot", e)
                        }
                    }
                }
            }
        }

        try {
            // Register the observer for external storage changes
            contentResolver.registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                true,
                contentObserver!!
            )

            isObserving = true
            Log.d(TAG, "Successfully registered content observer")

            // Do an initial check for recent screenshots
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // Check for any screenshots taken in the last 10 seconds
                    // This helps if we missed any while the service was restarting
                    val timeThreshold = (System.currentTimeMillis() / 1000 - 10).toString()
                    val projection = arrayOf(
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DATE_ADDED
                    )

                    val selection = "${MediaStore.Images.Media.DATE_ADDED} >= ?"
                    val selectionArgs = arrayOf(timeThreshold)
                    val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

                    contentResolver.query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        selection,
                        selectionArgs,
                        sortOrder
                    )?.use { cursor ->
                        if (cursor.moveToFirst()) {
                            val displayNameIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
                            val dataIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)

                            if (displayNameIndex >= 0 && dataIndex >= 0) {
                                val displayName = cursor.getString(displayNameIndex)
                                val filePath = cursor.getString(dataIndex)

                                if (isScreenshotFilename(displayName) && File(filePath).exists()) {
                                    Log.d(TAG, "Found recent screenshot during startup: $filePath")
                                    onScreenshotTaken(filePath)
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error checking for recent screenshots", e)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error registering content observer", e)
        }
    }

    /**
     * Stop observing for screenshots
     */
    fun stopObserving() {
        if (!isObserving) {
            Log.d(TAG, "Not observing, nothing to stop")
            return
        }

        try {
            contentObserver?.let {
                try {
                    context.contentResolver.unregisterContentObserver(it)
                    Log.d(TAG, "Successfully unregistered content observer")
                } catch (e: Exception) {
                    Log.e(TAG, "Error unregistering content observer", e)
                } finally {
                    contentObserver = null
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping screenshot observation", e)
            // Force reset the state even if there was an error
            contentObserver = null
        }

        isObserving = false
        Log.d(TAG, "Screenshot observation stopped")
    }

    /**
     * Check if the given URI is a screenshot
     */
    private suspend fun checkIfScreenshot(contentResolver: ContentResolver, uri: Uri) {
        withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Checking if URI is a screenshot: $uri")
                val projection = arrayOf(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DATE_ADDED
                )

                // Look for images added in the last 5 seconds
                val selection = "${MediaStore.Images.Media.DATE_ADDED} >= ?"
                val timeThreshold = (System.currentTimeMillis() / 1000 - 5).toString()
                val selectionArgs = arrayOf(timeThreshold)

                Log.d(TAG, "Querying for images added after: $timeThreshold")

                contentResolver.query(
                    uri,
                    projection,
                    selection,
                    selectionArgs,
                    "${MediaStore.Images.Media.DATE_ADDED} DESC"
                )?.use { cursor ->
                    Log.d(TAG, "Query returned ${cursor.count} results")

                    if (cursor.moveToFirst()) {
                        val displayNameIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
                        val dataIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)

                        if (displayNameIndex >= 0 && dataIndex >= 0) {
                            val displayName = cursor.getString(displayNameIndex)
                            val filePath = cursor.getString(dataIndex)

                            Log.d(TAG, "Found image: $displayName at $filePath")

                            // Check if this is a screenshot based on filename
                            val isScreenshot = isScreenshotFilename(displayName)
                            val fileExists = File(filePath).exists()

                            Log.d(TAG, "Is screenshot by name: $isScreenshot, File exists: $fileExists")

                            if (isScreenshot && fileExists) {
                                Log.d(TAG, "Screenshot detected: $filePath")
                                withContext(Dispatchers.Main) {
                                    onScreenshotTaken(filePath)
                                }
                            } else {
                                Log.d(TAG, "Not a screenshot or file doesn't exist")
                            }
                        } else {
                            Log.e(TAG, "Column indices not found: displayName=$displayNameIndex, data=$dataIndex")
                        }
                    } else {
                        Log.d(TAG, "No images found in the cursor")
                    }
                } ?: Log.e(TAG, "Query returned null cursor")
            } catch (e: Exception) {
                Log.e(TAG, "Error checking for screenshot", e)
            }
        }
    }

    /**
     * Check if the filename indicates this is a screenshot
     */
    private fun isScreenshotFilename(filename: String): Boolean {
        val lowercaseName = filename.lowercase()
        return lowercaseName.contains("screenshot") ||
               lowercaseName.contains("screen_shot") ||
               lowercaseName.contains("screen-shot") ||
               lowercaseName.startsWith("scr_") ||
               lowercaseName.contains("capture") ||
               lowercaseName.contains("snap") ||
               // Additional patterns for different device manufacturers
               lowercaseName.startsWith("img") ||
               lowercaseName.matches(Regex("\\d{8}_\\d{6}\\.jpg")) || // Date pattern like 20230101_123456.jpg
               lowercaseName.matches(Regex("\\d{14}\\.jpg")) || // Timestamp pattern
               lowercaseName.contains("shot") ||
               lowercaseName.contains("screen")
    }
}
