package com.aks_labs.pixelflow.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import androidx.core.content.ContextCompat
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.aks_labs.pixelflow.R
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * A view that displays action buttons in a circular arrangement around a floating bubble.
 */
class FloatingActionButtons(
    context: Context,
    private val bubbleX: Int,
    private val bubbleY: Int,
    private val bubbleSize: Int
) : View(context) {

    companion object {
        private const val TAG = "FloatingActionButtons"
        private const val BUTTON_RADIUS = 65f // Base radius of each action button (increased by 15dp)
        private const val BUTTON_RADIUS_HIGHLIGHTED = 75f // Radius when highlighted (increased by 15dp)
        private const val ORBIT_RADIUS_RATIO = 1.5f // Ratio of bubble size for orbit radius (closer to bubble)
        private const val WAVE_COUNT = 12 // Number of waves around the perimeter
        private const val WAVE_AMPLITUDE = 5f // Amplitude of the waves (how pronounced they are)
        private const val VIBRATION_DURATION = 20L // Duration of vibration feedback in milliseconds

        // Action types
        const val ACTION_DELETE = 0
        const val ACTION_SHARE = 1
        const val ACTION_TRASH = 2
    }

    // Action button positions
    private val buttonPositions = mutableListOf<ActionButtonPosition>()

    // Currently highlighted button
    private var highlightedButtonIndex = -1

    // Paint objects
    private val buttonPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val buttonStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.DKGRAY
        style = Paint.Style.STROKE
        strokeWidth = 1.5f
        isAntiAlias = true
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 18f * resources.displayMetrics.density // 18sp
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
        isFakeBoldText = true
    }

    // Icons for action buttons
    private val deleteIcon = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_delete)
    private val shareIcon = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_share)

    // Callback for action selection
    private var onActionSelectedListener: ((Int) -> Unit)? = null

    init {
        // Make the background transparent
        setBackgroundColor(Color.TRANSPARENT)
        calculateButtonPositions()
    }

    /**
     * Sets the listener for action selection.
     */
    fun setOnActionSelectedListener(listener: (Int) -> Unit) {
        Log.d(TAG, "Setting action selected listener")
        onActionSelectedListener = listener
    }

    /**
     * Updates the bubble position and recalculates button positions.
     */
    fun updateBubblePosition(x: Int, y: Int) {
        if (x != bubbleX || y != bubbleY) {
            calculateButtonPositions()
            invalidate()
        }
    }

    /**
     * Calculates the positions of the action buttons in a circular arrangement around the bubble.
     */
    private fun calculateButtonPositions() {
        buttonPositions.clear()

        // Calculate the orbit radius based on bubble size
        val orbitRadius = bubbleSize * ORBIT_RADIUS_RATIO

        // We always have 3 action buttons: Delete, Share, Trash
        val actionCount = 3

        // Action types and colors
        val actionTypes = intArrayOf(ACTION_DELETE, ACTION_SHARE, ACTION_TRASH)

        // Use white as default color and colorAccent for highlighted, just like drag zones
        val defaultColor = Color.rgb(240, 240, 240) // Light gray like in drag zones

        // All buttons use the same color scheme now
        val actionColors = intArrayOf(
            defaultColor,  // Same default color for all buttons
            defaultColor,  // Same default color for all buttons
            defaultColor   // Same default color for all buttons
        )

        // Get screen dimensions to ensure buttons are fully visible
        val displayMetrics = context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        // Determine which side of the screen the bubble is closer to
        val isNearRight = bubbleX > screenWidth / 2

        // Position buttons in a tighter cluster around the bubble
        // Calculate angles for a more compact arrangement
        val angles = if (isNearRight) {
            // If bubble is on the right side of screen, position buttons to the left of bubble
            arrayOf(225.0, 180.0, 135.0) // Bottom-left, left, top-left
        } else {
            // If bubble is on the left side of screen, position buttons to the right of bubble
            arrayOf(315.0, 0.0, 45.0) // Bottom-right, right, top-right
        }

        for (i in 0 until actionCount) {
            // Get the angle for this button in radians
            val angleInRadians = Math.toRadians(angles[i])

            // Calculate the position using standard parametric circle equations
            val x = bubbleX + orbitRadius * cos(angleInRadians).toFloat()
            val y = bubbleY + orbitRadius * sin(angleInRadians).toFloat()

            // Ensure the button is fully visible on screen
            val minX = BUTTON_RADIUS_HIGHLIGHTED + 20f
            val maxX = screenWidth - BUTTON_RADIUS_HIGHLIGHTED - 20f
            val minY = BUTTON_RADIUS_HIGHLIGHTED + 20f
            val maxY = screenHeight - BUTTON_RADIUS_HIGHLIGHTED - 20f

            // Make sure min is less than max
            val adjustedX = if (minX < maxX) x.coerceIn(minX, maxX) else x
            val adjustedY = if (minY < maxY) y.coerceIn(minY, maxY) else y

            buttonPositions.add(
                ActionButtonPosition(
                    adjustedX,
                    adjustedY,
                    actionTypes[i],
                    actionColors[i]
                )
            )
        }
    }

    /**
     * Updates the highlighted button based on the given coordinates.
     * Returns true if a button is highlighted.
     */
    fun updateHighlight(x: Float, y: Float): Boolean {
        val oldHighlightedButtonIndex = highlightedButtonIndex
        highlightedButtonIndex = -1

        // Use the same threshold as in getActionAt for consistency
        val HIGHLIGHT_THRESHOLD = BUTTON_RADIUS_HIGHLIGHTED

        for (i in buttonPositions.indices) {
            val button = buttonPositions[i]
            val distance = calculateDistance(x, y, button.x, button.y)

            if (distance <= HIGHLIGHT_THRESHOLD) {
                highlightedButtonIndex = i
                Log.d(TAG, "Highlighting button $i (type=${button.actionType}) at distance $distance")
                break
            }
        }

        // Only invalidate if the highlighted button changed
        if (oldHighlightedButtonIndex != highlightedButtonIndex) {
            // If we entered a new button (not just exited one), provide haptic feedback
            if (highlightedButtonIndex != -1 && oldHighlightedButtonIndex == -1) {
                // Only vibrate when first entering a button, not when moving between buttons
                vibrateDevice()
            }
            invalidate()
        }

        return highlightedButtonIndex != -1
    }



    /**
     * Gets the position of a specific action button.
     * Returns a Pair of (x, y) coordinates, or null if the button doesn't exist.
     */
    fun getButtonPosition(actionType: Int): Pair<Float, Float>? {
        // Find the button with the specified action type
        val button = buttonPositions.find { it.actionType == actionType }

        // Return the position as a Pair if found, or null if not found
        return button?.let { Pair(it.x, it.y) }
    }

    /**
     * Checks if the given coordinates are over a button and returns the button's action type.
     * Returns -1 if no button is found at the coordinates.
     */
    fun getActionAt(x: Float, y: Float): Int {
        Log.d(TAG, "Checking for action at coordinates: ($x, $y)")

        // Find the closest button
        var closestButton: ActionButtonPosition? = null
        var closestDistance = Float.MAX_VALUE

        for (button in buttonPositions) {
            val distance = calculateDistance(x, y, button.x, button.y)
            Log.d(TAG, "Button type=${button.actionType} at distance $distance")

            if (distance < closestDistance) {
                closestDistance = distance
                closestButton = button
            }
        }

        // Use a reasonable threshold for action detection
        // This should match the visual size of the button for intuitive interaction
        val ACTION_THRESHOLD = BUTTON_RADIUS * 1.5f

        // If we found a button and it's within our threshold
        if (closestButton != null && closestDistance <= ACTION_THRESHOLD) {
            val actionType = closestButton.actionType
            Log.d(TAG, "Found action $actionType at distance $closestDistance")
            return actionType
        }

        Log.d(TAG, "No action found at coordinates: ($x, $y)")
        return -1
    }

    /**
     * Executes the action at the given coordinates directly.
     * Returns true if an action was executed, false otherwise.
     */
    fun executeActionAt(x: Float, y: Float): Boolean {
        Log.d(TAG, "Attempting to execute action at coordinates: ($x, $y)")

        // Get the action at the coordinates
        val actionType = getActionAt(x, y)

        // If we found an action, execute it
        if (actionType != -1) {
            // Provide haptic feedback
            vibrateDevice()

            // Invoke the action listener directly
            onActionSelectedListener?.let { listener ->
                Log.d(TAG, "Invoking action listener for action $actionType")
                try {
                    // Execute the action in a try-catch block
                    listener(actionType)
                    Log.d(TAG, "Action listener executed successfully")
                    return true
                } catch (e: Exception) {
                    Log.e(TAG, "Error executing action listener", e)
                    return false
                }
            } ?: run {
                Log.e(TAG, "Action listener is null for action $actionType")
                return false
            }
        }

        Log.d(TAG, "No action found at coordinates: ($x, $y)")
        return false
    }



    /**
     * Calculates the distance between two points.
     */
    private fun calculateDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        return sqrt((x2 - x1).pow(2) + (y2 - y1).pow(2))
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

    /**
     * Creates a wavy-edged circular path for the button.
     */
    private fun createWavyPath(centerX: Float, centerY: Float, radius: Float): Path {
        val path = Path()
        val angleStep = 2 * Math.PI / WAVE_COUNT

        // Start at the rightmost point
        var angle = 0.0
        val startX = centerX + radius * cos(angle).toFloat()
        val startY = centerY + radius * sin(angle).toFloat()
        path.moveTo(startX, startY)

        // Draw the wavy circle
        for (i in 1..WAVE_COUNT) {
            // Calculate the control points for the quadratic curve
            val controlAngle1 = angle + angleStep / 3
            val controlX1 = centerX + (radius + WAVE_AMPLITUDE) * cos(controlAngle1).toFloat()
            val controlY1 = centerY + (radius + WAVE_AMPLITUDE) * sin(controlAngle1).toFloat()

            val controlAngle2 = angle + 2 * angleStep / 3
            val controlX2 = centerX + (radius + WAVE_AMPLITUDE) * cos(controlAngle2).toFloat()
            val controlY2 = centerY + (radius + WAVE_AMPLITUDE) * sin(controlAngle2).toFloat()

            // Calculate the end point for this segment
            angle += angleStep
            val endX = centerX + radius * cos(angle).toFloat()
            val endY = centerY + radius * sin(angle).toFloat()

            // Draw a cubic Bezier curve for this segment
            path.cubicTo(controlX1, controlY1, controlX2, controlY2, endX, endY)
        }

        path.close()
        return path
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw each action button
        for (i in buttonPositions.indices) {
            val button = buttonPositions[i]
            val isHighlighted = i == highlightedButtonIndex

            // Determine the radius based on highlight state
            val radius = if (isHighlighted) BUTTON_RADIUS_HIGHLIGHTED else BUTTON_RADIUS

            // Set the button color based on highlight state
            buttonPaint.color = if (isHighlighted) {
                ContextCompat.getColor(context, R.color.colorAccent)
            } else {
                button.color
            }

            // Create the wavy-edged circular path
            val path = createWavyPath(button.x, button.y, radius)

            // Draw the button with a subtle shadow for depth
            buttonPaint.setShadowLayer(8f, 0f, 2f, Color.argb(60, 0, 0, 0))
            canvas.drawPath(path, buttonPaint)

            // Draw a very subtle stroke
            buttonStrokePaint.color = Color.argb(30, 0, 0, 0) // Very transparent black
            buttonStrokePaint.strokeWidth = 1f
            canvas.drawPath(path, buttonStrokePaint)

            // Draw the action icon or text based on action type
            when (button.actionType) {
                ACTION_DELETE -> {
                    // Draw delete icon
                    deleteIcon?.let { icon ->
                        // Calculate icon bounds to center it in the button
                        val iconSize = (radius * 0.8f).toInt() // Bigger icon size (80% of button radius)
                        val left = (button.x - iconSize / 2).toInt()
                        val top = (button.y - iconSize / 2).toInt()
                        val right = left + iconSize
                        val bottom = top + iconSize

                        // Set icon bounds and color
                        icon.setBounds(left, top, right, bottom)
                        icon.setTint(Color.BLACK)

                        // Draw the icon
                        icon.draw(canvas)
                    }
                }
                ACTION_SHARE -> {
                    // Draw share icon
                    shareIcon?.let { icon ->
                        // Calculate icon bounds to center it in the button
                        val iconSize = (radius * 0.8f).toInt() // Bigger icon size (80% of button radius)
                        val left = (button.x - iconSize / 2).toInt()
                        val top = (button.y - iconSize / 2).toInt()
                        val right = left + iconSize
                        val bottom = top + iconSize

                        // Set icon bounds and color
                        icon.setBounds(left, top, right, bottom)
                        icon.setTint(Color.BLACK)

                        // Draw the icon
                        icon.draw(canvas)
                    }
                }
                ACTION_TRASH -> {
                    // Draw "Trash" text
                    textPaint.color = Color.BLACK
                    textPaint.textSize = 16f * resources.displayMetrics.density
                    textPaint.isFakeBoldText = true

                    val trashText = "Trash"

                    // Get text height metrics for vertical centering
                    val textBounds = Rect()
                    textPaint.getTextBounds(trashText, 0, trashText.length, textBounds)
                    val textHeight = textBounds.height()

                    // Draw text centered both horizontally and vertically
                    canvas.drawText(trashText, button.x, button.y + textHeight / 3, textPaint)
                }
            }
        }
    }

    /**
     * Data class to hold the position and properties of an action button.
     */
    private data class ActionButtonPosition(
        val x: Float,
        val y: Float,
        val actionType: Int,
        val color: Int
    )
}
