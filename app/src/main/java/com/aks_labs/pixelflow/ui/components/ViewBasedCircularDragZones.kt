package com.aks_labs.pixelflow.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
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
        private const val ZONE_RADIUS = 80f // Base radius of each zone
        private const val ZONE_RADIUS_HIGHLIGHTED = 90f // Radius when highlighted
        private const val SEMI_CIRCLE_RADIUS = 300f // Radius of the semi-circle
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
     */
    fun updateHighlight(x: Float, y: Float) {
        val oldHighlightedZoneIndex = highlightedZoneIndex
        highlightedZoneIndex = -1

        for (i in zonePositions.indices) {
            val zone = zonePositions[i]
            val distance = calculateDistance(x, y, zone.x, zone.y)

            if (distance <= ZONE_RADIUS) {
                highlightedZoneIndex = i
                break
            }
        }

        if (oldHighlightedZoneIndex != highlightedZoneIndex) {
            invalidate()
        }
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

        val folderCount = folders.size
        if (folderCount == 0) return

        // Calculate the angle between each zone
        val angleStep = PI / (folderCount - 1)

        for (i in 0 until folderCount) {
            // Calculate the angle for this zone (0 to PI)
            val angle = i * angleStep.toDouble()

            // Calculate the position
            val x = centerX + SEMI_CIRCLE_RADIUS * cos(angle).toFloat()
            val y = centerY - SEMI_CIRCLE_RADIUS * sin(angle).toFloat()

            zonePositions.add(ZonePosition(x, y))
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

            // Draw the zone circle
            zonePaint.color = if (isHighlighted)
                ContextCompat.getColor(context, R.color.colorAccent)
            else
                Color.WHITE

            canvas.drawCircle(zone.x, zone.y, radius, zonePaint)
            canvas.drawCircle(zone.x, zone.y, radius, zoneStrokePaint)

            // Draw the folder name
            if (i < folders.size) {
                val folder = folders[i]
                canvas.drawText(folder.name, zone.x, zone.y + 8f, textPaint)
            }
        }
    }

    /**
     * Data class to hold the position of a zone.
     */
    private data class ZonePosition(val x: Float, val y: Float)
}
