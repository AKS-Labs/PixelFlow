package com.akslabs.pixelscreenshots.ui.components

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.akslabs.pixelscreenshots.services.ViewBasedFloatingBubbleService
import java.io.File

/**
 * A direct action executor that bypasses the normal action button flow.
 * This is a last resort approach when the normal action buttons aren't working.
 */
class DirectActionExecutor(
    private val context: Context,
    private val service: ViewBasedFloatingBubbleService
) {
    companion object {
        private const val TAG = "DirectActionExecutor"
        private const val VIBRATION_DURATION = 50L
    }

    /**
     * Executes the delete action directly.
     */
    fun executeDeleteAction(bubbleView: View?) {
        Log.d(TAG, "DIRECT EXECUTION: Delete action")
        
        // Provide haptic feedback
        vibrateDevice()
        
        // Animate the bubble disappearing if available
        bubbleView?.let { view ->
            view.animate()
                .alpha(0f)
                .scaleX(0f)
                .scaleY(0f)
                .setDuration(300)
                .start()
        }
        
        // Execute the delete action directly
        try {
            val currentScreenshotPath = service.getCurrentScreenshotPath()
            if (currentScreenshotPath != null) {
                val file = File(currentScreenshotPath)
                if (file.exists() && file.delete()) {
                    Toast.makeText(context, "Screenshot deleted", Toast.LENGTH_SHORT).show()
                    
                    // Remove the bubble
                    service.removeBubble()
                    service.hideDragZones()
                    service.clearCurrentScreenshot()
                } else {
                    Toast.makeText(context, "Failed to delete screenshot", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "No screenshot to delete", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error executing delete action", e)
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Executes the share action directly.
     */
    fun executeShareAction(bubbleView: View?) {
        Log.d(TAG, "DIRECT EXECUTION: Share action")
        
        // Provide haptic feedback
        vibrateDevice()
        
        // Animate the bubble disappearing if available
        bubbleView?.let { view ->
            view.animate()
                .alpha(0f)
                .scaleX(0f)
                .scaleY(0f)
                .setDuration(300)
                .start()
        }
        
        // Execute the share action directly
        try {
            service.shareScreenshot()
        } catch (e: Exception) {
            Log.e(TAG, "Error executing share action", e)
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Executes the trash action directly.
     */
    fun executeTrashAction(bubbleView: View?) {
        Log.d(TAG, "DIRECT EXECUTION: Trash action")
        
        // Provide haptic feedback
        vibrateDevice()
        
        // Animate the bubble disappearing if available
        bubbleView?.let { view ->
            view.animate()
                .alpha(0f)
                .scaleX(0f)
                .scaleY(0f)
                .setDuration(300)
                .start()
        }
        
        // Execute the trash action directly
        try {
            service.moveToTrash()
        } catch (e: Exception) {
            Log.e(TAG, "Error executing trash action", e)
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Provides haptic feedback.
     */
    private fun vibrateDevice() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                val vibrator = vibratorManager.defaultVibrator
                vibrator.vibrate(VibrationEffect.createOneShot(VIBRATION_DURATION, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(VIBRATION_DURATION, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(VIBRATION_DURATION)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error vibrating device", e)
        }
    }
}
