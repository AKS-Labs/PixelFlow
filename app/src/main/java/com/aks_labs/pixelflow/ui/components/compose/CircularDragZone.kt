package com.aks_labs.pixelflow.ui.components.compose

import android.graphics.Paint
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.aks_labs.pixelflow.data.models.SimpleFolder
import kotlin.math.cos
import kotlin.math.sin

/**
 * A composable that displays circular drag zones in a semi-circle pattern.
 *
 * @param folders The list of folders to display
 * @param highlightedIndex The index of the highlighted folder (-1 for none)
 * @param onFolderSelected Callback for when a folder is selected, with the folder ID
 */
@Composable
fun CircularDragZone(
    folders: List<SimpleFolder>,
    highlightedIndex: Int = -1,
    onFolderSelected: (Long) -> Unit = {}
) {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    // Base zone radius plus extra 10dp
    val baseZoneRadius = remember { 120.dp }
    val extraRadius = remember { 10.dp }
    val zoneRadius = baseZoneRadius + extraRadius

    // Additional 10dp for highlighted zones
    val highlightExtraRadius = remember { 10.dp }

    // Number of petals for the flower/gear shape
    val petalCount = remember { 12 }
    val petalDepth = remember { 0.15f }

    // Pulse animation for highlighted zones
    val pulseAnim = rememberInfiniteTransition(label = "pulse")
    val pulseScale by pulseAnim.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            if (folders.isEmpty()) return@Canvas

            val centerX = size.width / 2f
            val centerY = size.height - size.height / 4f // Position the center point at 3/4 of the screen height
            val centerDistance = size.width * 0.4f // 40% of screen width

            val folderCount = folders.size

            // For a single folder, place it at the bottom center
            if (folderCount == 1) {
                val folder = folders[0]
                val x = centerX
                val y = size.height - 200.dp.toPx() // Place at the bottom with padding

                // Calculate radius with animation if needed
                val radius = with(density) {
                    var r = zoneRadius.toPx()
                    if (0 == highlightedIndex) {
                        r += highlightExtraRadius.toPx()
                        r *= pulseScale // Apply pulse animation
                    }
                    r
                }

                // Create flower/gear path
                val path = createFlowerPath(x, y, radius, petalCount, petalDepth)

                // Draw shadow for highlighted zone
                if (0 == highlightedIndex) {
                    translate(2f, 4f) {
                        drawPath(
                            path = path,
                            color = Color.Black.copy(alpha = 0.16f),
                            style = Stroke(width = 3f)
                        )
                    }
                }

                // Draw zone background (white with 100% opacity)
                drawPath(
                    path = path,
                    color = Color.White,
                    style = Fill
                )

                // Draw border
                drawPath(
                    path = path,
                    color = Color.White,
                    style = Stroke(width = 3f)
                )

                // Draw highlight ring for highlighted zone
                if (0 == highlightedIndex) {
                    val highlightPath = createFlowerPath(x, y, radius * 1.05f, petalCount, petalDepth)
                    drawPath(
                        path = highlightPath,
                        color = Color.White,
                        style = Stroke(width = 6f)
                    )
                }

                // Draw folder name with black text for better contrast
                drawContext.canvas.nativeCanvas.drawText(
                    folder.name,
                    x,
                    y + 15f,
                    Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 40f
                        textAlign = Paint.Align.CENTER
                    }
                )

                return@Canvas
            }

            // Calculate the angle range based on folder count
            // For few folders (2-8), use a bottom arc (150 degrees)
            // For more folders (9+), gradually expand to a full circle (360 degrees)
            val minFolders = 2
            val maxFolders = 16 // At this point, we'll have a full circle

            // Start with a bottom arc (150 degrees)
            val minAngleRange = 150f // About 150 degrees
            val maxAngleRange = 360f // 360 degrees (full circle)

            // Calculate the transition factor (0 to 1) based on folder count
            val transitionFactor = if (folderCount <= 8) {
                0f // Use the minimum arc for 2-8 folders
            } else {
                kotlin.math.min(1f, (folderCount - 8f) / (maxFolders - 8f))
            }

            // Calculate the actual angle range using the transition factor
            val angleRange = minAngleRange + (maxAngleRange - minAngleRange) * transitionFactor

            // For few folders, center the arc at the bottom (90 degrees)
            val arcCenterAngle = 90f // Bottom center (90 degrees)

            // Calculate the start and end angles to center the arc
            val startAngle = arcCenterAngle - angleRange / 2
            val endAngle = arcCenterAngle + angleRange / 2

            // Calculate the angle step between each zone
            val angleStep = angleRange / folderCount

            folders.forEachIndexed { index, folder ->
                val angleInDegrees = startAngle + index * angleStep
                val angleInRadians = Math.toRadians(angleInDegrees.toDouble())

                val x = centerX + centerDistance * cos(angleInRadians).toFloat()
                val y = centerY + centerDistance * sin(angleInRadians).toFloat()

                // Calculate radius with animation if needed
                val radius = with(density) {
                    var r = zoneRadius.toPx()
                    if (index == highlightedIndex) {
                        r += highlightExtraRadius.toPx()
                        r *= pulseScale // Apply pulse animation
                    }
                    r
                }

                // Create flower/gear path
                val path = createFlowerPath(x, y, radius, petalCount, petalDepth)

                // Draw shadow for highlighted zone
                if (index == highlightedIndex) {
                    translate(2f, 4f) {
                        drawPath(
                            path = path,
                            color = Color.Black.copy(alpha = 0.16f),
                            style = Stroke(width = 3f)
                        )
                    }
                }

                // Draw zone background (white with 100% opacity)
                drawPath(
                    path = path,
                    color = Color.White,
                    style = Fill
                )

                // Draw border
                drawPath(
                    path = path,
                    color = Color.White,
                    style = Stroke(width = 3f)
                )

                // Draw highlight ring for highlighted zone
                if (index == highlightedIndex) {
                    val highlightPath = createFlowerPath(x, y, radius * 1.05f, petalCount, petalDepth)
                    drawPath(
                        path = highlightPath,
                        color = Color.White,
                        style = Stroke(width = 6f)
                    )
                }

                // Draw folder name with black text for better contrast
                drawContext.canvas.nativeCanvas.drawText(
                    folder.name,
                    x,
                    y + 15f,
                    Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 40f
                        textAlign = Paint.Align.CENTER
                    }
                )
            }
        }
    }
}

/**
 * Creates a flower/gear shaped path.
 */
private fun createFlowerPath(
    centerX: Float,
    centerY: Float,
    radius: Float,
    petalCount: Int,
    petalDepth: Float
): Path {
    val path = Path()
    val angleStep = (2 * Math.PI / petalCount).toFloat()
    val startAngle = 0f

    // Start point
    val startX = centerX + radius * cos(startAngle)
    val startY = centerY + radius * sin(startAngle)
    path.moveTo(startX, startY)

    // Create petals
    for (i in 1..petalCount) {
        val outerAngle = i * angleStep
        val midAngle = outerAngle - (angleStep / 2)

        val innerRadius = radius * (1 - petalDepth)

        val midX = centerX + innerRadius * cos(midAngle)
        val midY = centerY + innerRadius * sin(midAngle)

        val outerX = centerX + radius * cos(outerAngle)
        val outerY = centerY + radius * sin(outerAngle)

        // Use lineTo instead of quadTo for simpler path
        path.lineTo(midX, midY)
        path.lineTo(outerX, outerY)
    }

    path.close()
    return path
}

/**
 * Checks if a point is inside a flower/gear shape.
 * Simplified approach using distance from center with extra margin for petals.
 */
fun isPointInFlowerShape(
    x: Float,
    y: Float,
    centerX: Float,
    centerY: Float,
    radius: Float
): Boolean {
    val distanceX = x - centerX
    val distanceY = y - centerY
    val distanceSquared = distanceX * distanceX + distanceY * distanceY

    // Check if within the outer circle with extra margin for petals
    val outerRadiusSquared = radius * radius * 1.1f

    return distanceSquared <= outerRadiusSquared
}

/**
 * Calculates a magnetic attraction point if the given point is near a zone.
 */
fun calculateMagneticPoint(
    x: Float,
    y: Float,
    centerX: Float,
    centerY: Float,
    radius: Float
): Offset? {
    val distanceX = x - centerX
    val distanceY = y - centerY
    val distance = kotlin.math.sqrt(distanceX * distanceX + distanceY * distanceY)

    // If we're close enough for magnetic attraction but not inside
    if (distance < radius * 2.5f && distance > radius) {
        // Calculate attraction point (move 30% of the way toward the center)
        val attractionFactor = 0.3f
        val attractX = x + (centerX - x) * attractionFactor
        val attractY = y + (centerY - y) * attractionFactor
        return Offset(attractX, attractY)
    }

    return null
}
