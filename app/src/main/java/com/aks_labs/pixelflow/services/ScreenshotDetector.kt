package com.aks_labs.pixelflow.services

import android.content.ContentResolver
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
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
    private var contentObserver: ContentObserver? = null
    private var isObserving = false
    
    /**
     * Start observing for screenshots
     */
    fun startObserving() {
        if (isObserving) return
        
        val contentResolver = context.contentResolver
        val handler = Handler(Looper.getMainLooper())
        
        contentObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean, uri: Uri?) {
                super.onChange(selfChange, uri)
                uri?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        checkIfScreenshot(contentResolver, it)
                    }
                }
            }
        }
        
        // Register the observer for external storage changes
        contentResolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            true,
            contentObserver!!
        )
        
        isObserving = true
    }
    
    /**
     * Stop observing for screenshots
     */
    fun stopObserving() {
        if (!isObserving) return
        
        contentObserver?.let {
            context.contentResolver.unregisterContentObserver(it)
            contentObserver = null
        }
        
        isObserving = false
    }
    
    /**
     * Check if the given URI is a screenshot
     */
    private suspend fun checkIfScreenshot(contentResolver: ContentResolver, uri: Uri) {
        withContext(Dispatchers.IO) {
            try {
                val projection = arrayOf(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DATE_ADDED
                )
                
                val selection = "${MediaStore.Images.Media.DATE_ADDED} >= ?"
                val selectionArgs = arrayOf((System.currentTimeMillis() / 1000 - 5).toString())
                
                contentResolver.query(
                    uri,
                    projection,
                    selection,
                    selectionArgs,
                    "${MediaStore.Images.Media.DATE_ADDED} DESC"
                )?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val displayNameIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
                        val dataIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                        
                        if (displayNameIndex >= 0 && dataIndex >= 0) {
                            val displayName = cursor.getString(displayNameIndex)
                            val filePath = cursor.getString(dataIndex)
                            
                            // Check if this is a screenshot based on filename
                            if (isScreenshotFilename(displayName) && File(filePath).exists()) {
                                withContext(Dispatchers.Main) {
                                    onScreenshotTaken(filePath)
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
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
               lowercaseName.contains("snap")
    }
}
