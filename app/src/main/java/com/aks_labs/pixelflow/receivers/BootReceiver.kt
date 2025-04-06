package com.aks_labs.pixelflow.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.aks_labs.pixelflow.services.FloatingBubbleService

/**
 * Receiver to start the FloatingBubbleService when the device boots
 */
class BootReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "BootReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Received intent: ${intent.action}")

        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED,
            "android.intent.action.QUICKBOOT_POWERON",
            Intent.ACTION_MY_PACKAGE_REPLACED -> {
                Log.d(TAG, "Starting FloatingBubbleService after boot or app update")

                // Start the FloatingBubbleService
                val serviceIntent = Intent(context, FloatingBubbleService::class.java)
                serviceIntent.action = "START_ON_BOOT"

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(serviceIntent)
                } else {
                    context.startService(serviceIntent)
                }
            }
        }
    }
}
