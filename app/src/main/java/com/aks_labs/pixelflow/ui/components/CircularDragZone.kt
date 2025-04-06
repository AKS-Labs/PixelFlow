package com.aks_labs.pixelflow.ui.components

import android.content.Context
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
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

    // Material Design 3 colors - using official Material You colors
    private val md3Colors = listOf(
        Color.parseColor("#006E1C"), // M3 Green Primary
        Color.parseColor("#6750A4"), // M3 Purple Primary
        Color.parseColor("#B4261E"), // M3 Error
        Color.parseColor("#7D5260"), // M3 Tertiary
        Color.parseColor("#0061A4"), // M3 Blue Primary
        Color.parseColor("#984700"), // M3 Orange Primary
        Color.parseColor("#984061"), // M3 Pink Primary
        Color.parseColor("#006A6A"), // M3 Cyan Primary
        Color.parseColor("#006874"), // M3 Teal Primary
        Color.parseColor("#7D5700")  // M3 Amber Primary
    )

    // Number of "petals" or "gear teeth" for the flower/gear shape
    private val petalCount = 12

    // How pronounced the petals/teeth are (0.0-0.3 is a good range)
    private val petalDepth = 0.15f

    // Path for drawing the flower/gear shape
    private val flowerPath = android.graphics.Path()

    // Paint objects
    private val zonePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE // All zones are white as requested
        style = Paint.Style.FILL
        alpha = 255 // 100% opacity
    }

    private val highlightPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE // Highlighted zones are also white
        style = Paint.Style.FILL
        alpha = 255 // 100% opacity
    }

    private val zoneBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 3f
        alpha = 255 // 100% opacity
    }

    private val zoneShadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.FILL
        alpha = 40 // 16% opacity - keeping shadow semi-transparent
        setShadowLayer(12f, 0f, 4f, Color.BLACK)
    }

    private val zoneHighlightRingPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 6f
        alpha = 255 // 100% opacity
    }

    // Inner glow paint for a more modern look
    private val innerGlowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 8f
        alpha = 255 // 100% opacity
        maskFilter = android.graphics.BlurMaskFilter(15f, android.graphics.BlurMaskFilter.Blur.NORMAL)
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK // Black text for better contrast on white background
        textSize = 40f
        textAlign = Paint.Align.CENTER
    }

    // Folders and zones
    private var folders: List<SimpleFolder> = emptyList()
    private val zoneRects = mutableListOf<Pair<RectF, Long>>() // Rect and folder ID
    var highlightedZoneIndex = -1
        private set

    // Zone dimensions
    private var zoneRadius = 120f // Base size
    private var extraRadius = 0f // Will be set to 10dp in dp-to-px conversion
    private var centerDistance = 500f // Distance from center to zone centers

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

        // Adjust the angle range based on folder count
        // For fewer folders, use a semi-circle at the bottom
        // For more folders, expand to a full circle
        val angleRange = if (folderCount <= 5) 180f else 270f
        val startAngle = if (folderCount <= 5) 180f else 135f
        val angleStep = angleRange / folderCount

        // Calculate radius based on screen size
        val screenRadius = Math.min(width, height) * 0.4f

        // Update zone dimensions based on screen size
        zoneRadius = Math.min(width, height) * 0.08f
        centerDistance = screenRadius

        folders.forEachIndexed { index, folder ->
            // Calculate angle in radians (starting from bottom and going around)
            val angleInDegrees = startAngle + index * angleStep
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
        var closestIndex = -1
        var closestDistance = Float.MAX_VALUE

        // Find the closest zone and check if we're inside any zone
        for (i in zoneRects.indices) {
            val (rect, _) = zoneRects[i]
            val centerX = rect.centerX()
            val centerY = rect.centerY()
            val distance = Math.sqrt(
                Math.pow((x - centerX).toDouble(), 2.0) +
                Math.pow((y - centerY).toDouble(), 2.0)
            ).toFloat()

            // Check if this is the closest zone so far
            if (distance < closestDistance) {
                closestDistance = distance
                closestIndex = i
            }

            // Check if we're inside this zone
            if (isPointInCircle(x, y, rect)) {
                highlightedZoneIndex = i
                found = true
                break
            }
        }

        // If not inside any zone but close to one (magnetic attraction)
        if (!found && closestIndex >= 0) {
            // If we're within the magnetic range of the closest zone
            if (closestDistance < zoneRadius * 2.5) {
                highlightedZoneIndex = closestIndex
                found = true
            } else {
                highlightedZoneIndex = -1
            }
        } else if (!found) {
            highlightedZoneIndex = -1
        }

        invalidate()
    }

    /**
     * Get magnetic attraction point for the current highlighted zone
     * Returns null if no magnetic attraction should occur
     */
    fun getMagneticPoint(x: Float, y: Float): PointF? {
        if (highlightedZoneIndex >= 0) {
            val (rect, _) = zoneRects[highlightedZoneIndex]
            val centerX = rect.centerX()
            val centerY = rect.centerY()
            val distance = Math.sqrt(
                Math.pow((x - centerX).toDouble(), 2.0) +
                Math.pow((y - centerY).toDouble(), 2.0)
            ).toFloat()

            // If we're close enough for magnetic attraction but not inside
            if (distance < zoneRadius * 2.5 && distance > zoneRadius) {
                // Calculate attraction point (move 30% of the way toward the center)
                val attractionFactor = 0.3f
                val attractX = x + (centerX - x) * attractionFactor
                val attractY = y + (centerY - y) * attractionFactor
                return PointF(attractX, attractY)
            }
        }
        return null
    }

    /**
     * Get the folder ID at the given coordinates
     */
    fun getFolderIdAt(x: Float, y: Float): Long? {
        for ((index, pair) in zoneRects.withIndex()) {
            val (rect, folderId) = pair
            if (isPointInCircle(x, y, rect)) {
                // Animate the zone when selected
                animateZone(index)
                return folderId
            }
        }
        return null
    }

    /**
     * Animate a zone with a pulse effect
     */
    fun animateZone(index: Int) {
        // Stop any running animation
        animator.cancel()

        // Set the zone to animate
        animatedZoneIndex = index

        // Start the animation
        animator.start()
    }

    /**
     * Check if a point is inside our flower/gear shape
     * We'll use a slightly simplified approach - check if it's within the outer circle,
     * but give a bit more leeway to account for the petals
     */
    private fun isPointInCircle(x: Float, y: Float, rect: RectF): Boolean {
        val centerX = rect.centerX()
        val centerY = rect.centerY()
        val radius = rect.width() / 2

        // Calculate distance from center
        val distanceX = x - centerX
        val distanceY = y - centerY
        val distanceSquared = distanceX * distanceX + distanceY * distanceY

        // Check if within the outer circle (with a bit of extra margin for the petals)
        val outerRadiusSquared = radius * radius * 1.1f

        return distanceSquared <= outerRadiusSquared
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateZones()
    }

    // Animation properties
    private var animatedZoneIndex = -1
    private var animationScale = 1.0f
    private var pulseScale = 1.0f
    private val animator = ValueAnimator.ofFloat(1.0f, 1.2f, 1.0f).apply {
        duration = 300
        interpolator = OvershootInterpolator()
        addUpdateListener {
            animationScale = it.animatedValue as Float
            invalidate()
        }
    }

    // Continuous pulse animation for highlighted zones
    private val pulseAnimator = ValueAnimator.ofFloat(1.0f, 1.05f, 1.0f).apply {
        duration = 1500
        repeatCount = ValueAnimator.INFINITE
        repeatMode = ValueAnimator.RESTART
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener {
            pulseScale = it.animatedValue as Float
            invalidate()
        }
    }

    init {
        // Start the pulse animation
        pulseAnimator.start()

        // Convert 10dp to pixels for the extra radius
        extraRadius = 10 * resources.displayMetrics.density
    }

    /**
     * Create a flower/gear shaped path
     */
    private fun createFlowerPath(centerX: Float, centerY: Float, radius: Float): android.graphics.Path {
        flowerPath.reset()

        // Calculate points for the flower/gear shape
        val angleStep = (2 * Math.PI / petalCount).toFloat()

        // Start at the first point
        val startAngle = 0f
        val startX = centerX + radius * cos(startAngle)
        val startY = centerY + radius * sin(startAngle)
        flowerPath.moveTo(startX, startY)

        // Create the flower/gear shape by alternating between outer and inner points
        for (i in 1..petalCount) {
            val outerAngle = i * angleStep
            val midAngle = outerAngle - (angleStep / 2)

            // Calculate the inner radius (creates the "dip" between petals)
            val innerRadius = radius * (1 - petalDepth)

            // Add the inner point (the "dip" between petals)
            val midX = centerX + innerRadius * cos(midAngle)
            val midY = centerY + innerRadius * sin(midAngle)

            // Add the outer point (the tip of the petal)
            val outerX = centerX + radius * cos(outerAngle)
            val outerY = centerY + radius * sin(outerAngle)

            // Add these points to the path with a quadratic curve for smoothness
            flowerPath.quadTo(midX, midY, outerX, outerY)
        }

        // Close the path
        flowerPath.close()
        return flowerPath
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Enable hardware acceleration for shadow and blur effects
        setLayerType(LAYER_TYPE_HARDWARE, null)

        // Draw each zone
        zoneRects.forEachIndexed { index, (rect, folderId) ->
            // Get folder (we'll only use this for the name, not color)
            val folder = folders.find { it.id == folderId }

            // All zones are white as requested
            // We're not using different colors for different folders anymore

            // Calculate radius with animation if needed
            // Start with base radius + extra 10dp
            var radius = zoneRadius + extraRadius

            if (index == animatedZoneIndex) {
                radius *= animationScale
            } else if (index == highlightedZoneIndex) {
                // Increase size by additional 10dp when highlighted (convert dp to px)
                val density = resources.displayMetrics.density
                val highlightExtraRadiusPx = 10 * density
                radius += highlightExtraRadiusPx
                radius *= pulseScale // Keep the pulse effect
            }

            val centerX = rect.centerX()
            val centerY = rect.centerY()

            // Create the flower/gear path
            val path = createFlowerPath(centerX, centerY, radius)

            // Draw shadow first (only for highlighted or animated zones)
            if (index == highlightedZoneIndex || index == animatedZoneIndex) {
                // Save the canvas state before applying transformations
                canvas.save()
                canvas.translate(2f, 4f) // Offset for shadow
                canvas.drawPath(path, zoneShadowPaint)
                canvas.restore()
            }

            // Draw the flower/gear shape background
            canvas.drawPath(path, if (index == highlightedZoneIndex) highlightPaint else zonePaint)

            // Draw inner glow for a more modern look
            if (index == highlightedZoneIndex) {
                canvas.drawPath(path, innerGlowPaint)
            }

            // Draw border
            canvas.drawPath(path, zoneBorderPaint)

            // Draw highlight ring for highlighted zone
            if (index == highlightedZoneIndex) {
                // Create a slightly larger path for the highlight ring
                val highlightPath = createFlowerPath(centerX, centerY, radius * 1.05f)
                canvas.drawPath(highlightPath, zoneHighlightRingPaint)
            }

            // Draw the folder name
            folder?.let {
                // Always use black text for better contrast on white background
                textPaint.color = Color.BLACK

                canvas.drawText(
                    it.name,
                    centerX,
                    centerY + textPaint.textSize / 3, // Adjust for text baseline
                    textPaint
                )
            }
        }
    }
}
