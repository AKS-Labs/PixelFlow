package com.aks_labs.pixelflow.ui.components.compose

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aks_labs.pixelflow.data.models.SimpleFolder
import kotlin.math.cos
import kotlin.math.sin

/**
 * A composable that displays circular drag zones in a semi-circle pattern.
 * M3 Expressive Redesign: Uses dynamic colors and rounded shapes.
 */
@Composable
fun CircularDragZone(
    folders: List<SimpleFolder>,
    highlightedIndex: Int = -1,
    onFolderSelected: (Long) -> Unit = {}
) {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    
    // M3 Expressive Colors
    val primaryColor = MaterialTheme.colorScheme.primaryContainer
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimaryContainer
    val surfaceColor = MaterialTheme.colorScheme.surfaceVariant
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val outlineColor = MaterialTheme.colorScheme.outline

    // Base zone radius
    val baseZoneRadius = remember { 110.dp } // Slightly smaller for cleaner look
    
    // Pulse animation for highlighted zones
    val pulseAnim = rememberInfiniteTransition(label = "pulse")
    val pulseScale by pulseAnim.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
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
            val centerY = size.height - size.height / 4f // 3/4 down
            val centerDistance = size.width * 0.4f

            val folderCount = folders.size
            
            // Angle calculations for distribution
            val minAngleRange = 150f
            val maxAngleRange = 320f
            val transitionFactor = if (folderCount <= 5) 0f else kotlin.math.min(1f, (folderCount - 5f) / 10f)
            val angleRange = minAngleRange + (maxAngleRange - minAngleRange) * transitionFactor
            val arcCenterAngle = 90f // Bottom center
            val startAngle = arcCenterAngle - angleRange / 2
            val angleStep = if (folderCount > 1) angleRange / (folderCount - 1) else 0f

            folders.forEachIndexed { index, folder ->
                // Calculate position
                val angleInDegrees = if (folderCount == 1) 90f else startAngle + index * angleStep
                val angleInRadians = Math.toRadians(angleInDegrees.toDouble())

                val x = if (folderCount == 1) centerX else centerX + centerDistance * cos(angleInRadians).toFloat()
                val y = if (folderCount == 1) size.height - 250.dp.toPx() else centerY + centerDistance * sin(angleInRadians).toFloat()

                // Calculate radius
                val isHighlighted = index == highlightedIndex
                val currentScale = if (isHighlighted) pulseScale else 1f
                val radius = with(density) { baseZoneRadius.toPx() * currentScale }
                
                // M3 "Squircle" Shape using RoundedRect
                val rectSize = radius * 1.8f // Width/Height of the box
                val topLeft = Offset(x - rectSize/2, y - rectSize/2)
                val cornerRadius = CornerRadius(rectSize * 0.4f, rectSize * 0.4f) // Large corners for squircle effect

                // Draw Drop Shadow
                // Note: Canvas shadows are tricky, simulating with offset drawing
                if (isHighlighted) {
                     drawRoundRect(
                        color = Color.Black.copy(alpha = 0.2f),
                        topLeft = topLeft + Offset(0f, 10f),
                        size = Size(rectSize, rectSize),
                        cornerRadius = cornerRadius
                    )
                }

                // Draw Background
                drawRoundRect(
                    color = if (isHighlighted) primaryColor else surfaceColor,
                    topLeft = topLeft,
                    size = Size(rectSize, rectSize),
                    cornerRadius = cornerRadius,
                    style = Fill
                )
                
                // Draw Border
                drawRoundRect(
                    color = if (isHighlighted) onPrimaryColor else outlineColor.copy(alpha = 0.5f),
                    topLeft = topLeft,
                    size = Size(rectSize, rectSize),
                    cornerRadius = cornerRadius,
                    style = Stroke(width = if (isHighlighted) 6f else 2f)
                )

                // Draw Text
                val nativePaint = Paint().apply {
                    color = (if (isHighlighted) onPrimaryColor else onSurfaceColor).toArgb()
                    textSize = 42f
                    textAlign = Paint.Align.CENTER
                    typeface = Typeface.DEFAULT_BOLD
                }
                
                drawContext.canvas.nativeCanvas.drawText(
                    folder.name,
                    x,
                    y + 15f, // Approximate vertical center alignment adjust
                    nativePaint
                )
            }
        }
    }
}

// Keeping the helper for touch detection but updating logic to match new shape
fun isPointInFlowerShape(
    x: Float,
    y: Float,
    centerX: Float,
    centerY: Float,
    radius: Float
): Boolean {
    // Simplified hit detection: Check distance to center of zone
    // Using a slightly larger radius for easier dropping
    val hitRadius = radius * 1.2f
    val dx = x - centerX
    val dy = y - centerY
    return (dx * dx + dy * dy) <= (hitRadius * hitRadius)
}
