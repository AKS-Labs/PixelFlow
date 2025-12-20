package com.akslabs.pixelscreenshots.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.akslabs.pixelscreenshots.data.SharedPrefsManager
import com.akslabs.pixelscreenshots.services.ViewBasedFloatingBubbleService

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

                // Check if onboarding is completed before starting the service
                val sharedPrefsManager = SharedPrefsManager(context)
                if (!sharedPrefsManager.isOnboardingCompleted()) {
                    Log.d(TAG, "Onboarding not completed, not starting service")
                    return
                }

                // Start the service immediately with a short delay
                startServiceWithRetry(context, 3000) // First attempt after 3 seconds

                // Also schedule additional attempts with increasing delays
                // This ensures the service starts even if the first attempt fails
                Handler(Looper.getMainLooper()).postDelayed({
                    startServiceWithRetry(context, 0) // Second attempt after 15 seconds total
                }, 15000)

                Handler(Looper.getMainLooper()).postDelayed({
                    startServiceWithRetry(context, 0) // Third attempt after 30 seconds total
                }, 30000)
            }
        }
    }

    /**
     * Starts the service with retry logic and initial delay
     */
    private fun startServiceWithRetry(context: Context, initialDelayMs: Long) {
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                // Start the ViewBasedFloatingBubbleService
                val serviceIntent = Intent(context, ViewBasedFloatingBubbleService::class.java)
                serviceIntent.action = "START_ON_BOOT"

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(serviceIntent)
                } else {
                    context.startService(serviceIntent)
                }

                Log.d(TAG, "Service started successfully after boot")
            } catch (e: Exception) {
                Log.e(TAG, "Error starting service after boot", e)
            }
        }, initialDelayMs)
    }
}