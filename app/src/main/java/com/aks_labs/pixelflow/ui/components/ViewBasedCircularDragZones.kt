package com.aks_labs.pixelflow.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
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
import kotlin.math.sin

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
        private const val ZONE_RADIUS = 90f // Base radius of each zone
        private const val ZONE_RADIUS_HIGHLIGHTED = 100f // Radius when highlighted
        private const val SEMI_CIRCLE_RADIUS_RATIO = 0.4f // Ratio of screen height for semi-circle radius
        private const val MAGNETIC_ATTRACTION_DISTANCE = 150f // Distance for magnetic attraction
        private const val MAGNETIC_ATTRACTION_STRENGTH = 0.3f // Strength of magnetic attraction (0-1)
        private const val WAVE_COUNT = 8 // Number of waves in the flower/gear shape
        private const val WAVE_AMPLITUDE = 15f // Amplitude of the waves
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
    }

    private val zoneStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 24f
        textAlign = Paint.Align.CENTER
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
     * Calculates the positions of the zones in a semi-circular pattern.
     */
    private fun calculateZonePositions() {
        zonePositions.clear()

        val centerX = width / 2f
        val centerY = height - 100f // Position near the bottom

        // Calculate semi-circle radius based on screen size
        val semiCircleRadius = height * SEMI_CIRCLE_RADIUS_RATIO

        val folderCount = folders.size
        if (folderCount == 0) return

        // Calculate the angle between each zone
        val angleStep = PI / (folderCount - 1)

        for (i in 0 until folderCount) {
            // Calculate the angle for this zone (0 to PI)
            val angle = i * angleStep.toDouble()

            // Calculate the position
            val x = centerX + semiCircleRadius * cos(angle).toFloat()
            val y = centerY - semiCircleRadius * sin(angle).toFloat()

            // Ensure the zone is fully visible on screen
            val minX = ZONE_RADIUS_HIGHLIGHTED + 20f
            val maxX = width - ZONE_RADIUS_HIGHLIGHTED - 20f
            val minY = ZONE_RADIUS_HIGHLIGHTED + 20f
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

            // Set the zone color
            zonePaint.color = if (isHighlighted)
                ContextCompat.getColor(context, R.color.colorAccent)
            else
                Color.WHITE

            // Create a flower/gear shape path
            val path = createFlowerPath(zone.x, zone.y, radius)

            // Draw the zone with shadow
            zonePaint.setShadowLayer(12f, 0f, 4f, Color.argb(100, 0, 0, 0))
            canvas.drawPath(path, zonePaint)

            // Draw the stroke
            canvas.drawPath(path, zoneStrokePaint)

            // Draw the folder name
            if (i < folders.size) {
                val folder = folders[i]
                textPaint.color = if (isHighlighted) Color.WHITE else Color.BLACK
                canvas.drawText(folder.name, zone.x, zone.y + 8f, textPaint)
            }
        }
    }

    /**
     * Creates a flower/gear-shaped path for a zone.
     */
    private fun createFlowerPath(centerX: Float, centerY: Float, radius: Float): Path {
        val path = Path()

        // Calculate points around the circle
        val angleStep = (2 * Math.PI / WAVE_COUNT).toFloat()

        // Start at the first point
        var angle = 0.0
        var waveRadius = radius + WAVE_AMPLITUDE * Math.sin(WAVE_COUNT * angle)
        var x = centerX + waveRadius * Math.cos(angle)
        var y = centerY + waveRadius * Math.sin(angle)
        path.moveTo(x.toFloat(), y.toFloat())

        // Add the rest of the points
        for (i in 1 until 360) {
            angle = i * Math.PI / 180.0
            waveRadius = radius + WAVE_AMPLITUDE * Math.sin(WAVE_COUNT * angle)
            x = centerX + waveRadius * Math.cos(angle)
            y = centerY + waveRadius * Math.sin(angle)
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
