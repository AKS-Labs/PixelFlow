package com.akslabs.pixelscreenshots.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.File

/**
 * Utility class for bitmap operations.
 */
object BitmapUtils {

    private const val TAG = "BitmapUtils"

    /**
     * Loads a bitmap from a file path.
     */
    fun loadBitmapFromFile(filePath: String): Bitmap? {
        return try {
            val file = File(filePath)
            if (!file.exists()) {
                Log.e(TAG, "File does not exist: $filePath")
                return null
            }

            val options = BitmapFactory.Options().apply {
                inSampleSize = 4  // Scale down to 1/4 of the original size
            }
            BitmapFactory.decodeFile(filePath, options)
        } catch (e: Exception) {
            Log.e(TAG, "Error loading bitmap from file: $filePath", e)
            null
        }
    }
}
