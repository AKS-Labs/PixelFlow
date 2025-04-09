package com.aks_labs.pixelflow.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

/**
 * Utility class for screen-related operations.
 */
object ScreenUtils {

    /**
     * Shows a toast message on the main thread.
     */
    fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, message, duration).show()
        }
    }
}
