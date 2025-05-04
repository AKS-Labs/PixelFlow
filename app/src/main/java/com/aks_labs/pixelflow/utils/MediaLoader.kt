package com.aks_labs.pixelflow.utils

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.aks_labs.pixelflow.data.models.SimpleScreenshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Utility class to load media files from the device
 */
class MediaLoader(private val context: Context) {

    private val contentResolver: ContentResolver = context.contentResolver
    private val TAG = "MediaLoader"

    /**
     * Load all screenshots from the device
     */
    suspend fun loadScreenshots(): List<SimpleScreenshot> = withContext(Dispatchers.IO) {
        val screenshots = mutableListOf<SimpleScreenshot>()

        try {
            val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.DATE_MODIFIED
            )

            // Look for files that are likely screenshots
            val selection = "${MediaStore.Images.Media.DISPLAY_NAME} LIKE ?"
            val selectionArgs = arrayOf("%screenshot%")
            val sortOrder = "${MediaStore.Images.Media.DATE_MODIFIED} DESC"

            contentResolver.query(
                collection,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                val dateAddedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
                val dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn)
                    val data = cursor.getString(dataColumn)
                    val dateAdded = cursor.getLong(dateAddedColumn) * 1000 // Convert to milliseconds
                    val dateModified = cursor.getLong(dateModifiedColumn) * 1000 // Convert to milliseconds

                    // Check if the file exists
                    val file = File(data)
                    if (file.exists() && isScreenshotFilename(name)) {
                        // We could use this contentUri for loading with Glide/Coil if needed
                        // val contentUri = ContentUris.withAppendedId(
                        //     MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        //     id
                        // )

                        val screenshot = SimpleScreenshot(
                            id = id,
                            filePath = data,
                            thumbnailPath = data, // Use the same path for thumbnail for now
                            folderId = 0, // Default folder
                            originalTimestamp = dateAdded,
                            savedTimestamp = dateModified
                        )

                        screenshots.add(screenshot)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading screenshots", e)
        }

        return@withContext screenshots
    }

    /**
     * Check if a filename is likely a screenshot
     */
    private fun isScreenshotFilename(filename: String): Boolean {
        val lowercaseName = filename.lowercase()
        return lowercaseName.contains("screenshot") ||
               lowercaseName.contains("screen_") ||
               lowercaseName.contains("screen-") ||
               lowercaseName.contains("shot_") ||
               lowercaseName.contains("capture")
    }
}
