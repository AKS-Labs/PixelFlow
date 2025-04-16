package com.aks_labs.pixelflow.ui.components

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
import com.aks_labs.pixelflow.R
import com.aks_labs.pixelflow.data.models.Folder
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
    }

    // Folders to display
    private val folders = mutableListOf<Folder>()

    // Zone positions
    private val zonePositions = mutableListOf<ZonePosition>()

    // Currently highlighted zone
    private var highlightedZoneIndex = -1

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

    // Callback for folder selection
    private var onFolderSelectedListener: ((String) -> Unit)? = null

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
     * Updates the highlighted zone based on the given coordinates.
     * Returns the adjusted position with magnetic attraction if applicable.
     */
    fun updateHighlight(x: Float, y: Float): Pair<Float, Float> {
        val oldHighlightedZoneIndex = highlightedZoneIndex
        highlightedZoneIndex = -1

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

        if (oldHighlightedZoneIndex != highlightedZoneIndex) {
            // If we entered a new zone (not just exited one), provide haptic feedback
            if (highlightedZoneIndex != -1) {
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
     * Calculates the positions of the zones in a responsive arc layout that adapts from a semi-circle
     * to a full circle as more folders are added, starting from the bottom of the screen like a sunrise.
     */
    private fun calculateZonePositions() {
        zonePositions.clear()

        val centerX = width / 2f
        val centerY = height - ZONE_RADIUS_HIGHLIGHTED - 40f // Position at the bottom with padding

        val folderCount = folders.size
        if (folderCount == 0) return

        // Calculate the optimal radius based on screen size and folder count
        // We use a smaller radius for fewer folders and a larger one for more folders
        val screenSize = min(width, height)
        val baseRadius = screenSize * 0.35f // Base radius as a percentage of screen size

        // Adjust radius based on folder count to prevent overlap
        val arcRadius = when {
            folderCount <= 3 -> baseRadius * 0.85f
            folderCount <= 5 -> baseRadius * 0.9f
            folderCount <= 8 -> baseRadius * 0.95f
            folderCount <= 12 -> baseRadius * 1.0f
            else -> baseRadius * 1.1f
        }

        // For a single folder, place it at the bottom center
        if (folderCount == 1) {
            val x = centerX
            val y = centerY
            zonePositions.add(ZonePosition(x, y))
            return
        }

        // Calculate the angle range based on folder count
        // Start with a small arc at the bottom (like sunrise)
        // Gradually expand upward as more folders are added
        val maxAngleRange = PI // 180 degrees (semi-circle)
        val minAngleRange = PI / 3 // 60 degrees (small arc at bottom)

        // Calculate the angle range using a smooth transition formula
        // This creates a gradual expansion from a small arc to a semi-circle
        val transitionFactor = min(1.0, (folderCount - 2) / 10.0) // Transition completes at 12 folders
        val angleRange = minAngleRange + (maxAngleRange - minAngleRange) * transitionFactor

        // Calculate the starting angle to center the arc at the bottom
        // We always start from the bottom and expand upward
        // PI/2 is the bottom of the circle (90 degrees)
        val startAngle = PI / 2 - angleRange / 2
        val endAngle = PI / 2 + angleRange / 2

        // Calculate the angle step between each zone
        val angleStep = angleRange / (folderCount - 1)

        for (i in 0 until folderCount) {
            // Calculate the angle for this zone
            val angle = startAngle + i * angleStep

            // Calculate the position using standard parametric circle equations
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

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateZonePositions()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw each zone
        for (i in zonePositions.indices) {
            val zone = zonePositions[i]
            val isHighlighted = i == highlightedZoneIndex

            // Determine the radius based on highlight state
            val radius = if (isHighlighted) ZONE_RADIUS_HIGHLIGHTED else ZONE_RADIUS

            // Set the zone color to match the reference image
            zonePaint.color = if (isHighlighted)
                ContextCompat.getColor(context, R.color.colorAccent)
            else
                Color.rgb(240, 240, 240) // Light gray like in the reference image

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
     * Data class to hold the position of a zone.
     */
    private data class ZonePosition(val x: Float, val y: Float)
}
