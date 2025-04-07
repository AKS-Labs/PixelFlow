package com.aks_labs.pixelflow.ui.components.compose

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.compose.ui.geometry.Offset
import kotlin.math.abs

/**
 * Utility class to handle touch events for the floating bubble.
 */
class TouchHandler(
    private val windowManager: WindowManager,
    private val screenWidth: Int,
    private val screenHeight: Int,
    private val onDragStart: () -> Unit,
    private val onDragEnd: () -> Unit,
    private val onDragMove: (x: Float, y: Float) -> Unit,
    private val onClick: () -> Unit,
    private val onDrop: (x: Float, y: Float) -> Unit
) {
    // Touch handling state
    private var initialX = 0
    private var initialY = 0
    private var initialTouchX = 0f
    private var initialTouchY = 0f
    private var isDragging = false
    private var lastAction = 0
    private var startClickTime = 0L
    private val clickDuration = 200L // milliseconds
    
    /**
     * Sets up a touch listener for the given view.
     */
    @SuppressLint("ClickableViewAccessibility")
    fun setupTouchListener(view: View, params: WindowManager.LayoutParams) {
        view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Save initial position
                    initialX = params.x
                    initialY = params.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    
                    // Save time for click detection
                    startClickTime = System.currentTimeMillis()
                    
                    // Reset drag state
                    isDragging = false
                    lastAction = event.action
                    
                    true
                }
                
                MotionEvent.ACTION_MOVE -> {
                    // Calculate movement
                    val dx = event.rawX - initialTouchX
                    val dy = event.rawY - initialTouchY
                    
                    // Check if this is a drag or just a tap
                    if (!isDragging && (abs(dx) > 10 || abs(dy) > 10)) {
                        isDragging = true
                        onDragStart()
                    }
                    
                    if (isDragging) {
                        // Update position
                        var newX = initialX + dx.toInt()
                        var newY = initialY + dy.toInt()
                        
                        // Keep bubble on screen
                        newX = newX.coerceIn(0, screenWidth - view.width)
                        newY = newY.coerceIn(0, screenHeight - view.height)
                        
                        // Update layout params
                        params.x = newX
                        params.y = newY
                        
                        // Update window
                        windowManager.updateViewLayout(view, params)
                        
                        // Notify listener
                        onDragMove(event.rawX, event.rawY)
                    }
                    
                    lastAction = event.action
                    true
                }
                
                MotionEvent.ACTION_UP -> {
                    // Check if this was a click
                    val clickTime = System.currentTimeMillis() - startClickTime
                    
                    if (!isDragging && clickTime < clickDuration) {
                        // It was a click
                        onClick()
                    } else if (isDragging) {
                        // It was a drag, handle drop
                        onDragEnd()
                        onDrop(event.rawX, event.rawY)
                    }
                    
                    lastAction = event.action
                    true
                }
                
                else -> false
            }
        }
    }
    
    /**
     * Snaps the view to the nearest edge of the screen.
     */
    fun snapToEdge(view: View, params: WindowManager.LayoutParams) {
        val middle = screenWidth / 2
        val goRight = params.x + view.width / 2 > middle
        
        val targetX = if (goRight) screenWidth - view.width else 0
        
        // Animate to edge
        val startX = params.x
        val distance = abs(targetX - startX)
        val duration = (distance / 5).coerceIn(100, 300)
        
        val animator = ValueAnimator(duration.toLong())
        animator.addUpdateListener { progress ->
            params.x = startX + ((targetX - startX) * progress).toInt()
            try {
                windowManager.updateViewLayout(view, params)
            } catch (e: Exception) {
                // View might have been removed
            }
        }
        animator.start()
    }
    
    /**
     * Simple value animator for smooth transitions.
     */
    private class ValueAnimator(private val duration: Long) {
        private val startTime = System.currentTimeMillis()
        private val interpolator = AccelerateInterpolator()
        private var updateListener: ((Float) -> Unit)? = null
        private var isRunning = false
        private val handler = android.os.Handler(android.os.Looper.getMainLooper())
        private val updateRunnable = object : Runnable {
            override fun run() {
                if (!isRunning) return
                
                val elapsed = System.currentTimeMillis() - startTime
                val progress = (elapsed / duration.toFloat()).coerceIn(0f, 1f)
                val interpolatedProgress = interpolator.getInterpolation(progress)
                
                updateListener?.invoke(interpolatedProgress)
                
                if (progress < 1f) {
                    handler.postDelayed(this, 16) // ~60fps
                } else {
                    isRunning = false
                }
            }
        }
        
        fun addUpdateListener(listener: (Float) -> Unit) {
            updateListener = listener
        }
        
        fun start() {
            isRunning = true
            handler.post(updateRunnable)
        }
    }
}
