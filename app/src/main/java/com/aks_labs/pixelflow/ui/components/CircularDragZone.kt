package com.aks_labs.pixelflow.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.aks_labs.pixelflow.R
import com.aks_labs.pixelflow.data.models.SimpleFolder
import kotlin.math.cos
import kotlin.math.sin

/**
 * Custom view for displaying circular drag zones in a semi-circle pattern
 */
class CircularDragZone @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Paint objects
    private val zonePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY
        alpha = (255 * 0.8).toInt() // 80% opacity
        style = Paint.Style.FILL
    }

    private val highlightPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        alpha = (255 * 0.9).toInt() // 90% opacity
        style = Paint.Style.FILL
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 40f
        textAlign = Paint.Align.CENTER
    }

    // Folders and zones
    private var folders: List<SimpleFolder> = emptyList()
    private val zoneRects = mutableListOf<Pair<RectF, Long>>() // Rect and folder ID
    private var highlightedZoneIndex = -1

    // Zone dimensions
    private val zoneRadius = 120f
    private val centerDistance = 500f // Distance from center to zone centers

    /**
     * Set the folders to display
     */
    fun setFolders(folders: List<SimpleFolder>) {
        this.folders = folders
        calculateZones()
        invalidate()
    }

    /**
     * Calculate the positions of each zone
     */
    private fun calculateZones() {
        zoneRects.clear()

        if (folders.isEmpty()) return

        val centerX = width / 2f
        val centerY = height - 200f // Position near bottom of screen

        // Calculate angle step based on number of folders
        val folderCount = folders.size
        val angleRange = 180f // Semi-circle (180 degrees)
        val angleStep = angleRange / (folderCount - 1)

        folders.forEachIndexed { index, folder ->
            // Calculate angle in radians (starting from bottom and going around)
            val angleInDegrees = 180f + index * angleStep
            val angleInRadians = Math.toRadians(angleInDegrees.toDouble())

            // Calculate position
            val x = centerX + centerDistance * cos(angleInRadians).toFloat()
            val y = centerY + centerDistance * sin(angleInRadians).toFloat()

            // Create rectangle for the zone
            val rect = RectF(
                x - zoneRadius,
                y - zoneRadius,
                x + zoneRadius,
                y + zoneRadius
            )

            zoneRects.add(Pair(rect, folder.id))
        }
    }

    /**
     * Highlight the zone at the given coordinates
     */
    fun highlightZoneAt(x: Float, y: Float) {
        var found = false

        for (i in zoneRects.indices) {
            val (rect, _) = zoneRects[i]
            if (isPointInCircle(x, y, rect)) {
                highlightedZoneIndex = i
                found = true
                break
            }
        }

        if (!found) {
            highlightedZoneIndex = -1
        }

        invalidate()
    }

    /**
     * Get the folder ID at the given coordinates
     */
    fun getFolderIdAt(x: Float, y: Float): Long? {
        for ((rect, folderId) in zoneRects) {
            if (isPointInCircle(x, y, rect)) {
                return folderId
            }
        }
        return null
    }

    /**
     * Check if a point is inside a circle
     */
    private fun isPointInCircle(x: Float, y: Float, rect: RectF): Boolean {
        val centerX = rect.centerX()
        val centerY = rect.centerY()
        val radius = rect.width() / 2

        val distance = PointF(x - centerX, y - centerY)
        return (distance.x * distance.x + distance.y * distance.y) <= (radius * radius)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateZones()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw each zone
        zoneRects.forEachIndexed { index, (rect, folderId) ->
            // Use highlighted paint if this zone is highlighted
            val paint = if (index == highlightedZoneIndex) highlightPaint else zonePaint

            // Draw the circle
            canvas.drawCircle(rect.centerX(), rect.centerY(), zoneRadius, paint)

            // Draw the folder name
            val folder = folders.find { it.id == folderId }
            folder?.let {
                canvas.drawText(
                    it.name,
                    rect.centerX(),
                    rect.centerY() + textPaint.textSize / 3, // Adjust for text baseline
                    textPaint
                )
            }
        }
    }
}
