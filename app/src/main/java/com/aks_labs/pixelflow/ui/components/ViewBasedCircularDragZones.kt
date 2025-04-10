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
        private const val PETAL_COUNT = 12 // Number of petals in the flower shape
        private const val PETAL_DEPTH = 0.3f // How deep the curves between petals are (0-1)
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

            // Create a flower shape path
            val path = createFlowerPath(zone.x, zone.y, radius)

            // Draw the zone with enhanced shadow for depth
            zonePaint.setShadowLayer(16f, 0f, 6f, Color.argb(120, 0, 0, 0))
            canvas.drawPath(path, zonePaint)

            // Draw a subtle inner shadow/highlight for 3D effect
            if (!isHighlighted) {
                val highlightPaint = Paint(Paint.ANTI_ALIAS_FLAG)
                highlightPaint.color = Color.argb(40, 255, 255, 255)
                highlightPaint.style = Paint.Style.STROKE
                highlightPaint.strokeWidth = 8f
                canvas.drawPath(path, highlightPaint)
            }

            // Draw the stroke with a thinner line for elegance
            zoneStrokePaint.strokeWidth = 1.5f
            canvas.drawPath(path, zoneStrokePaint)

            // Draw the folder name
            if (i < folders.size) {
                val folder = folders[i]
                textPaint.color = if (isHighlighted) Color.WHITE else Color.BLACK
                textPaint.textSize = 14f * resources.displayMetrics.density // 14sp
                textPaint.isFakeBoldText = true // Make text slightly bolder

                // Center the text properly
                val textWidth = textPaint.measureText(folder.name)
                canvas.drawText(folder.name, zone.x - textWidth / 2, zone.y + 4f, textPaint)
            }
        }
    }

    /**
     * Creates a flower-shaped path for a zone, similar to the reference image.
     */
    private fun createFlowerPath(centerX: Float, centerY: Float, radius: Float): Path {
        val path = Path()

        // Calculate the angle between each petal
        val angleStep = (2.0 * Math.PI / PETAL_COUNT)

        // We'll use Bezier curves to create smooth, rounded petals
        // For each petal, we'll create 4 points:
        // 1. The start point (at the petal base)
        // 2. The control point for the first curve
        // 3. The control point for the second curve
        // 4. The end point (at the next petal base)

        for (i in 0 until PETAL_COUNT) {
            // Calculate the angles for this petal
            val startAngle = i * angleStep
            val peakAngle = startAngle + (angleStep / 2.0)
            val endAngle = startAngle + angleStep

            // Calculate the points for this petal
            val innerRadius = radius * (1.0 - PETAL_DEPTH)

            // Start point (petal base)
            val startX = centerX + (innerRadius * Math.cos(startAngle)).toFloat()
            val startY = centerY + (innerRadius * Math.sin(startAngle)).toFloat()

            // Peak point (petal tip)
            val peakX = centerX + (radius * Math.cos(peakAngle)).toFloat()
            val peakY = centerY + (radius * Math.sin(peakAngle)).toFloat()

            // End point (next petal base)
            val endX = centerX + (innerRadius * Math.cos(endAngle)).toFloat()
            val endY = centerY + (innerRadius * Math.sin(endAngle)).toFloat()

            // Control points for the curves (to create rounded petals)
            val ctrl1X = centerX + (radius * 0.9 * Math.cos(startAngle + angleStep * 0.3)).toFloat()
            val ctrl1Y = centerY + (radius * 0.9 * Math.sin(startAngle + angleStep * 0.3)).toFloat()
            val ctrl2X = centerX + (radius * 0.9 * Math.cos(endAngle - angleStep * 0.3)).toFloat()
            val ctrl2Y = centerY + (radius * 0.9 * Math.sin(endAngle - angleStep * 0.3)).toFloat()

            // If this is the first petal, move to the start point
            if (i == 0) {
                path.moveTo(startX, startY)
            }

            // Draw the petal using cubic Bezier curves
            path.cubicTo(ctrl1X, ctrl1Y, peakX, peakY, peakX, peakY)
            path.cubicTo(peakX, peakY, ctrl2X, ctrl2Y, endX, endY)
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
