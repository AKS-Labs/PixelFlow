package com.akslabs.pixelscreenshots.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.akslabs.pixelscreenshots.R
import com.akslabs.pixelscreenshots.data.models.Folder
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * A custom view that displays circular drag zones in a semi-circular pattern.
 */
class ViewBasedCircularDragZones @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "CircularDragZones"
        private const val ZONE_RADIUS = 110f // Base radius of each zone
        private const val ZONE_RADIUS_HIGHLIGHTED = 120f // Radius when highlighted
        private const val SEMI_CIRCLE_RADIUS_RATIO = 0.4f // Ratio of screen height for semi-circle radius
        private const val MAGNETIC_ATTRACTION_DISTANCE = 150f // Distance for magnetic attraction
        private const val MAGNETIC_ATTRACTION_STRENGTH = 0.3f // Strength of magnetic attraction (0-1)
        private const val WAVE_COUNT = 12 // Number of waves around the perimeter
        private const val WAVE_AMPLITUDE = 5f // Amplitude of the waves (how pronounced they are)
        private const val VIBRATION_DURATION = 20L // Duration of vibration feedback in milliseconds

        // Action zones constants
        private const val ACTION_ZONE_RADIUS = 80f // Base radius of each action zone
        private const val ACTION_ZONE_RADIUS_HIGHLIGHTED = 90f // Radius when highlighted
        private const val ACTION_SEMI_CIRCLE_RADIUS_RATIO = 0.25f // Ratio of screen height for action semi-circle radius

        // Action types
        const val ACTION_DELETE = 0
        const val ACTION_SHARE = 1
        const val ACTION_TRASH = 2
    }

    // Folders to display
    private val folders = mutableListOf<Folder>()

    // Zone positions
    private val zonePositions = mutableListOf<ZonePosition>()

    // Action zone positions
    private val actionZonePositions = mutableListOf<ActionZonePosition>()

    // Currently highlighted zone
    private var highlightedZoneIndex = -1

    // Currently highlighted action zone
    private var highlightedActionZoneIndex = -1

    // Flag to indicate if action zones are visible
    private var showActionZones = false

    // Flag to indicate if dynamic colors should be used
    var useDynamicColors = false
        set(value) {
            field = value
            invalidate()
        }

    // Paint objects
    private val zonePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val zoneStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.DKGRAY
        style = Paint.Style.STROKE
        strokeWidth = 1.5f
        isAntiAlias = true
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 14f * resources.displayMetrics.density // 14sp
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
        isFakeBoldText = true
    }

    // Action zone paint objects
    private val actionZonePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val actionZoneStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.DKGRAY
        style = Paint.Style.STROKE
        strokeWidth = 1.5f
        isAntiAlias = true
    }

    private val actionIconPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 18f * resources.displayMetrics.density // 18sp
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
        isFakeBoldText = true
    }

    // Callback for folder selection
    private var onFolderSelectedListener: ((String) -> Unit)? = null

    // Callback for action selection
    private var onActionSelectedListener: ((Int) -> Unit)? = null

    /**
     * Sets the folders to display.
     */
    fun setFolders(newFolders: List<Folder>) {
        folders.clear()
        folders.addAll(newFolders)
        calculateZonePositions()
        invalidate()
    }

    /**
     * Sets the listener for folder selection.
     */
    fun setOnFolderSelectedListener(listener: (String) -> Unit) {
        onFolderSelectedListener = listener
    }

    /**
     * Sets the listener for action selection.
     */
    fun setOnActionSelectedListener(listener: (Int) -> Unit) {
        onActionSelectedListener = listener
    }

    /**
     * Shows the action zones.
     */
    fun showActionZones() {
        showActionZones = true
        calculateActionZonePositions()
        invalidate()
    }

    /**
     * Hides the action zones.
     */
    fun hideActionZones() {
        showActionZones = false
        highlightedActionZoneIndex = -1
        invalidate()
    }

    /**
     * Toggles the visibility of action zones.
     */
    fun toggleActionZones() {
        if (showActionZones) {
            hideActionZones()
        } else {
            showActionZones()
        }
    }

    /**
     * Updates the highlighted zone based on the given coordinates.
     * Returns the adjusted position with magnetic attraction if applicable.
     */
    fun updateHighlight(x: Float, y: Float): Pair<Float, Float> {
        val oldHighlightedZoneIndex = highlightedZoneIndex
        val oldHighlightedActionZoneIndex = highlightedActionZoneIndex
        highlightedZoneIndex = -1
        highlightedActionZoneIndex = -1

        // Check action zones first if they're visible
        if (showActionZones) {
            var closestActionZoneIndex = -1
            var closestActionDistance = Float.MAX_VALUE

            // Find the closest action zone and check if we're inside any action zone
            for (i in actionZonePositions.indices) {
                val zone = actionZonePositions[i]
                val distance = calculateDistance(x, y, zone.x, zone.y)

                if (distance < closestActionDistance) {
                    closestActionDistance = distance
                    closestActionZoneIndex = i
                }

                if (distance <= ACTION_ZONE_RADIUS) {
                    highlightedActionZoneIndex = i
                    // If we're inside an action zone, don't check regular zones
                    if (oldHighlightedActionZoneIndex != highlightedActionZoneIndex) {
                        vibrateDevice()
                        invalidate()
                    }
                    return Pair(x, y)
                }
            }

            // Apply magnetic attraction for action zones
            if (highlightedActionZoneIndex == -1 && closestActionZoneIndex != -1 &&
                closestActionDistance <= MAGNETIC_ATTRACTION_DISTANCE) {
                val zone = actionZonePositions[closestActionZoneIndex]
                val attractionStrength = MAGNETIC_ATTRACTION_STRENGTH *
                    (1 - closestActionDistance / MAGNETIC_ATTRACTION_DISTANCE)
                val dirX = zone.x - x
                val dirY = zone.y - y
                val newX = x + dirX * attractionStrength
                val newY = y + dirY * attractionStrength
                val newDistance = calculateDistance(newX, newY, zone.x, zone.y)

                if (newDistance <= ACTION_ZONE_RADIUS) {
                    highlightedActionZoneIndex = closestActionZoneIndex
                    if (oldHighlightedActionZoneIndex != highlightedActionZoneIndex) {
                        vibrateDevice()
                        invalidate()
                    }
                    return Pair(newX, newY)
                }
            }
        }

        // If no action zone is highlighted, check regular zones
        var closestZoneIndex = -1
        var closestDistance = Float.MAX_VALUE

        // Find the closest zone and check if we're inside any zone
        for (i in zonePositions.indices) {
            val zone = zonePositions[i]
            val distance = calculateDistance(x, y, zone.x, zone.y)

            if (distance < closestDistance) {
                closestDistance = distance
                closestZoneIndex = i
            }

            if (distance <= ZONE_RADIUS) {
                highlightedZoneIndex = i
            }
        }

        // Apply magnetic attraction if we're close to a zone but not inside it
        if (highlightedZoneIndex == -1 && closestZoneIndex != -1 && closestDistance <= MAGNETIC_ATTRACTION_DISTANCE) {
            val zone = zonePositions[closestZoneIndex]

            // Calculate attraction strength based on distance
            val attractionStrength = MAGNETIC_ATTRACTION_STRENGTH * (1 - closestDistance / MAGNETIC_ATTRACTION_DISTANCE)

            // Calculate the direction vector from current position to zone center
            val dirX = zone.x - x
            val dirY = zone.y - y

            // Apply attraction
            val newX = x + dirX * attractionStrength
            val newY = y + dirY * attractionStrength

            // Check if the new position would be inside the zone
            val newDistance = calculateDistance(newX, newY, zone.x, zone.y)
            if (newDistance <= ZONE_RADIUS) {
                highlightedZoneIndex = closestZoneIndex
            }

            if (oldHighlightedZoneIndex != highlightedZoneIndex) {
                // If we entered a new zone (not just exited one), provide haptic feedback
                if (highlightedZoneIndex != -1) {
                    vibrateDevice()
                }
                invalidate()
            }

            return Pair(newX, newY)
        }

        if (oldHighlightedZoneIndex != highlightedZoneIndex ||
            oldHighlightedActionZoneIndex != highlightedActionZoneIndex) {
            // If we entered a new zone (not just exited one), provide haptic feedback
            if (highlightedZoneIndex != -1 || highlightedActionZoneIndex != -1) {
                vibrateDevice()
            }
            invalidate()
        }

        return Pair(x, y)
    }

    /**
     * Gets the folder ID at the given coordinates, or null if none.
     */
    fun getFolderIdAt(x: Float, y: Float): String? {
        for (i in zonePositions.indices) {
            val zone = zonePositions[i]
            val distance = calculateDistance(x, y, zone.x, zone.y)

            if (distance <= ZONE_RADIUS) {
                return folders[i].id
            }
        }

        return null
    }

    /**
     * Gets the action type at the given coordinates, or -1 if none.
     */
    fun getActionAt(x: Float, y: Float): Int {
        if (!showActionZones) return -1

        for (i in actionZonePositions.indices) {
            val zone = actionZonePositions[i]
            val distance = calculateDistance(x, y, zone.x, zone.y)

            if (distance <= ACTION_ZONE_RADIUS) {
                return zone.actionType
            }
        }

        return -1
    }

    /**
     * Calculates the positions of the zones in a perfect semi-circular arc layout.
     */
    private fun calculateZonePositions() {
        zonePositions.clear()

        val centerX = width / 2f
        val centerY = height - 200f // Position near bottom of screen with fixed padding

        val folderCount = folders.size
        if (folderCount == 0) return

        // For a single folder, place it at the bottom center
        if (folderCount == 1) {
            val x = centerX
            val y = height - ZONE_RADIUS_HIGHLIGHTED - 40f
            zonePositions.add(ZonePosition(x, y))
            return
        }

        // Calculate the arc radius based on screen width
        val arcRadius = width * 0.4f // 40% of screen width

        // Use a fixed semi-circular arc (180 degrees) for all folder counts
        // This creates a perfect sunrise/sunset arc at the bottom of the screen
        val startAngle = PI // 180 degrees (left side)
        val endAngle = 2 * PI // 360 degrees (right side)

        // Calculate the angle step between each zone
        val angleStep = (endAngle - startAngle) / (folderCount - 1) // -1 to include both endpoints

        for (i in 0 until folderCount) {
            // Calculate the angle for this zone
            val angle = startAngle + i * angleStep

            // Calculate the position using standard parametric circle equations
            // We're using a semi-circle at the bottom, so we need to adjust the angle
            val x = centerX + arcRadius * cos(angle).toFloat()
            val y = centerY + arcRadius * sin(angle).toFloat()

            // Ensure the zone is fully visible on screen
            val minX = ZONE_RADIUS_HIGHLIGHTED + 20f
            val maxX = width - ZONE_RADIUS_HIGHLIGHTED - 20f
            val minY = ZONE_RADIUS_HIGHLIGHTED + 20f // Allow some space at the top
            val maxY = height - ZONE_RADIUS_HIGHLIGHTED - 20f

            // Make sure min is less than max
            val adjustedX = if (minX < maxX) x.coerceIn(minX, maxX) else x
            val adjustedY = if (minY < maxY) y.coerceIn(minY, maxY) else y

            zonePositions.add(ZonePosition(adjustedX, adjustedY))
        }
    }

    /**
     * Calculates the distance between two points.
     */
    private fun calculateDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        val dx = x2 - x1
        val dy = y2 - y1
        return kotlin.math.sqrt(dx * dx + dy * dy)
    }

    /**
     * Calculates the positions of the action zones in a small concentric arc.
     */
    private fun calculateActionZonePositions() {
        actionZonePositions.clear()

        val centerX = width / 2f
        val centerY = height - ACTION_ZONE_RADIUS_HIGHLIGHTED - 40f // Position at the bottom with padding

        // We always have 3 action zones: Delete, Share, Trash
        val actionCount = 3

        // Calculate the arc radius based on screen size
        val actionArcRadius = width * ACTION_SEMI_CIRCLE_RADIUS_RATIO

        // Use a fixed angle range for the action zones (90 degrees)
        val angleRange = PI / 2

        // Center the arrangement by adjusting the starting angle
        val startAngle = (PI - angleRange) / 2

        // Calculate the angle step between each action zone
        val angleStep = angleRange / (actionCount - 1)

        // Action types and colors
        val actionTypes = intArrayOf(ACTION_DELETE, ACTION_SHARE, ACTION_TRASH)
        val actionColors = intArrayOf(
            Color.rgb(244, 67, 54),  // Red for delete
            Color.rgb(33, 150, 243), // Blue for share
            Color.rgb(76, 175, 80)   // Green for trash
        )

        for (i in 0 until actionCount) {
            // Calculate the angle for this action zone
            val angle = startAngle + (i * angleStep)

            // Calculate the position - note we're using a semi-circle at the bottom of the screen
            // so we use sin for X and cos for Y (with cos inverted to point upward)
            val x = centerX + actionArcRadius * sin(angle - PI/2).toFloat()
            val y = centerY - actionArcRadius * cos(angle - PI/2).toFloat()

            // Ensure the zone is fully visible on screen
            val minX = ACTION_ZONE_RADIUS_HIGHLIGHTED + 20f
            val maxX = width - ACTION_ZONE_RADIUS_HIGHLIGHTED - 20f
            val minY = 100f // Allow some space at the top
            val maxY = height - ACTION_ZONE_RADIUS_HIGHLIGHTED - 20f

            // Make sure min is less than max
            val adjustedX = if (minX < maxX) x.coerceIn(minX, maxX) else x
            val adjustedY = if (minY < maxY) y.coerceIn(minY, maxY) else y

            actionZonePositions.add(ActionZonePosition(adjustedX, adjustedY, actionTypes[i], actionColors[i]))
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateZonePositions()
        if (showActionZones) {
            calculateActionZonePositions()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw each zone
        for (i in zonePositions.indices) {
            val zone = zonePositions[i]
            val isHighlighted = i == highlightedZoneIndex

            // Determine the radius based on highlight state
            val radius = if (isHighlighted) ZONE_RADIUS_HIGHLIGHTED else ZONE_RADIUS

            // Set the zone color to match the reference image or use dynamic colors
            if (isHighlighted) {
                if (useDynamicColors && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    // Use Primary Container for highlight as requested (fallback to strong purple for contrast)
                    zonePaint.color = getThemeColor(com.google.android.material.R.attr.colorPrimaryContainer, Color.parseColor("#9647DB"))
                } else {
                    zonePaint.color = ContextCompat.getColor(context, R.color.colorAccent)
                }
            } else {
                if (useDynamicColors && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    // Use Secondary Container for regular zones as requested
                    zonePaint.color = getThemeColor(com.google.android.material.R.attr.colorPrimaryFixedDim, Color.parseColor("#E8DEF8"))
                } else {
                    zonePaint.color = Color.rgb(240, 240, 240) // Light gray like in the reference image
                }
            }

            // Create the wavy-edged circular path
            val path = createFlowerPath(zone.x, zone.y, radius)

            // Draw the zone with a subtle shadow for depth
            zonePaint.setShadowLayer(8f, 0f, 2f, Color.argb(60, 0, 0, 0))
            canvas.drawPath(path, zonePaint)

            // Draw a very subtle stroke
            zoneStrokePaint.color = Color.argb(30, 0, 0, 0) // Very transparent black
            zoneStrokePaint.strokeWidth = 1f
            canvas.drawPath(path, zoneStrokePaint)

            // Draw the folder name
            if (i < folders.size) {
                val folder = folders[i]
                textPaint.color = Color.rgb(30, 30, 30) // Dark gray text like in the reference image
                textPaint.textSize = 16f * resources.displayMetrics.density // 16sp
                textPaint.isFakeBoldText = false // Regular text weight

                // Center the text properly both horizontally and vertically
                // No need to measure text width since we're using Paint.Align.CENTER

                // Get text height metrics for vertical centering
                val textBounds = Rect()
                textPaint.getTextBounds(folder.name, 0, folder.name.length, textBounds)
                val textHeight = textBounds.height()

                // Draw text centered both horizontally and vertically
                // The Paint.Align.CENTER handles horizontal centering, but we need to adjust vertical position
                // The baseline is at y position, so we need to offset by half the text height
                canvas.drawText(folder.name, zone.x, zone.y + textHeight / 3, textPaint)
            }
        }

        // Draw action zones if visible
        if (showActionZones) {
            for (i in actionZonePositions.indices) {
                val zone = actionZonePositions[i]
                val isHighlighted = i == highlightedActionZoneIndex

                // Determine the radius based on highlight state
                val radius = if (isHighlighted) ACTION_ZONE_RADIUS_HIGHLIGHTED else ACTION_ZONE_RADIUS

                // Set the zone color based on action type
                // actionZonePaint.color = zone.color // Original static color

                if (isHighlighted) {
                    if (useDynamicColors && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        // Use Primary Container for highlight as requested (fallback to strong purple for contrast)
                        actionZonePaint.color = getThemeColor(com.google.android.material.R.attr.colorPrimaryContainer, Color.parseColor("#9647DB"))
                    } else {
                         // Default highlight
                         actionZonePaint.color = ContextCompat.getColor(context, R.color.colorAccent)
                    }
                } else {
                    if (useDynamicColors && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        // Use Secondary Container for action zones as requested
                        actionZonePaint.color = getThemeColor(com.google.android.material.R.attr.colorSecondaryContainer, Color.parseColor("#E8DEF8"))
                    } else {
                        actionZonePaint.color = zone.color
                    }
                }

                // Create the wavy-edged circular path
                val path = createFlowerPath(zone.x, zone.y, radius)

                // Draw the zone with a subtle shadow for depth
                actionZonePaint.setShadowLayer(8f, 0f, 2f, Color.argb(60, 0, 0, 0))
                canvas.drawPath(path, actionZonePaint)

                // Draw a very subtle stroke
                actionZoneStrokePaint.color = Color.argb(30, 0, 0, 0) // Very transparent black
                actionZoneStrokePaint.strokeWidth = 1f
                canvas.drawPath(path, actionZoneStrokePaint)

                // Draw the action icon/text
                actionIconPaint.color = Color.WHITE
                actionIconPaint.textSize = 18f * resources.displayMetrics.density // 18sp
                actionIconPaint.isFakeBoldText = true

                // Get the icon text based on action type
                val iconText = when (zone.actionType) {
                    ACTION_DELETE -> "🗑️"
                    ACTION_SHARE -> "📤"
                    ACTION_TRASH -> "🗄️"
                    else -> ""
                }

                // Get text height metrics for vertical centering
                val textBounds = Rect()
                actionIconPaint.getTextBounds(iconText, 0, iconText.length, textBounds)
                val textHeight = textBounds.height()

                // Draw text centered both horizontally and vertically
                canvas.drawText(iconText, zone.x, zone.y + textHeight / 3, actionIconPaint)
            }
        }
    }

    /**
     * Creates a wavy-edged circular path that exactly matches the reference image.
     */
    private fun createFlowerPath(centerX: Float, centerY: Float, radius: Float): Path {
        val path = Path()

        // For a shape like in the reference image, we need a simple wavy circle
        // with gentle, evenly-spaced undulations

        // Start at the first point (right side of the circle)
        var angle = 0.0
        var waveRadius = radius + WAVE_AMPLITUDE * Math.cos(WAVE_COUNT * angle)
        var x = centerX + waveRadius * Math.cos(angle)
        var y = centerY + waveRadius * Math.sin(angle)
        path.moveTo(x.toFloat(), y.toFloat())

        // Add points around the circle with high precision for smooth curves
        // Using 720 points (2 points per degree) for extra smoothness
        val angleIncrement = 2.0 * Math.PI / 720.0

        for (i in 1 until 721) {
            angle = i * angleIncrement

            // Calculate the radius with a gentle wave
            // Using cosine for symmetrical, rounded waves
            waveRadius = radius + WAVE_AMPLITUDE * Math.cos(WAVE_COUNT * angle)

            // Calculate the point coordinates
            x = centerX + waveRadius * Math.cos(angle)
            y = centerY + waveRadius * Math.sin(angle)

            // Add the point to the path
            path.lineTo(x.toFloat(), y.toFloat())
        }

        // Close the path
        path.close()
        return path
    }

    /**
     * Vibrates the device to provide feedback.
     */
    private fun vibrateDevice() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                val vibrator = vibratorManager.defaultVibrator
                vibrator.vibrate(VibrationEffect.createOneShot(VIBRATION_DURATION, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
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
     * Helper to resolve a color attribute from the current theme.
     * Returns defaultColor if resolution fails.
     */
    private fun getThemeColor(attrResId: Int, defaultColor: Int): Int {
        val typedValue = android.util.TypedValue()
        val resolved = context.theme.resolveAttribute(attrResId, typedValue, true)
        return if (resolved) typedValue.data else defaultColor
    }

    /**
     * Data class to hold the position of a zone.
     *//* ... existing data classes ... */
    private data class ZonePosition(val x: Float, val y: Float)

    /**
     * Data class to hold the position and properties of an action zone.
     */
    private data class ActionZonePosition(val x: Float, val y: Float, val actionType: Int, val color: Int)
}
