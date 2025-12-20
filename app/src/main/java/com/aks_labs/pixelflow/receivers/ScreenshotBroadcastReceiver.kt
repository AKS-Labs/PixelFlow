package com.akslabs.pixelscreenshots.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.akslabs.pixelscreenshots.pixelFlowApp
import com.akslabs.pixelscreenshots.services.ViewBasedFloatingBubbleService

/**
 * BroadcastReceiver to listen for screenshot events
 */
class ScreenshotBroadcastReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "ScreenshotReceiver"
        const val ACTION_SCREENSHOT_DETECTED = "com.akslabs.pixelscreenshots.SCREENSHOT_DETECTED"
        const val ACTION_SCREENSHOT_MOVED = "com.akslabs.pixelscreenshots.SCREENSHOT_MOVED"
        const val EXTRA_SCREENSHOT_PATH = "screenshot_path"
        const val EXTRA_FOLDER_ID = "folder_id"

        /**
         * Register the receiver with the given context
         */
        fun register(context: Context): ScreenshotBroadcastReceiver {
            val receiver = ScreenshotBroadcastReceiver()
            val filter = IntentFilter().apply {
                addAction(ACTION_SCREENSHOT_DETECTED)
                addAction(ACTION_SCREENSHOT_MOVED)
                addAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED)
            }
            context.registerReceiver(receiver, filter)
            Log.d(TAG, "ScreenshotBroadcastReceiver registered")
            return receiver
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Received broadcast: ${intent.action}")

        when (intent.action) {
            ACTION_SCREENSHOT_DETECTED -> {
                val screenshotPath = intent.getStringExtra(EXTRA_SCREENSHOT_PATH)
                Log.d(TAG, "Screenshot detected: $screenshotPath")

                // Use immediate refresh for better responsiveness
                context.pixelFlowApp.mainViewModel?.refreshScreenshotsImmediately()
            }

            ACTION_SCREENSHOT_MOVED -> {
                val screenshotPath = intent.getStringExtra(EXTRA_SCREENSHOT_PATH)
                val folderId = intent.getLongExtra(EXTRA_FOLDER_ID, -1)
                Log.d(TAG, "Screenshot moved: $screenshotPath to folder: $folderId")

                // Use immediate refresh for better responsiveness
                context.pixelFlowApp.mainViewModel?.refreshScreenshotsImmediately()
            }

            Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Intent.ACTION_MEDIA_SCANNER_FINISHED -> {
                Log.d(TAG, "Media scanner event: ${intent.action}")

                // Use immediate refresh for better responsiveness
                context.pixelFlowApp.mainViewModel?.refreshScreenshotsImmediately()
            }
        }
    }
}
