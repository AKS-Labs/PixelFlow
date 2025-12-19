package com.aks_labs.pixelflow.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.dp

@Composable
fun OrganizeScreenshotAnimation(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "OrganizeScreenshotTransition")
    
    // Timer for the entire 10s sequence
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10000f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "AnimationTime"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val scale = size.width / 400f
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        
        // --- 1. Phone Frame ---
        drawRect(
            color = Color(0xFF1A1A1A),
            topLeft = Offset(50f * scale, 100f * scale),
            size = Size(300f * scale, 600f * scale),
            style = Fill,
            alpha = 1f
        )
        // Note: Using simpler rects to avoid complex RoundRect if not needed, 
        // but SVG has rx=30.
        drawPath(
            path = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(50f * scale, 100f * scale, 350f * scale, 700f * scale),
                        cornerRadius = CornerRadius(30f * scale)
                    )
                )
            },
            color = Color(0xFF1A1A1A)
        )
        
        // Inner Black Frame
        drawPath(
            path = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(58f * scale, 108f * scale, 342f * scale, 692f * scale),
                        cornerRadius = CornerRadius(26f * scale)
                    )
                )
            },
            color = Color.Black
        )

        // Screen Background & Flash Effect
        val screenColor = when {
            time in 3000f..3100f -> Color(0xFFF0F9FF)
            else -> Color.White
        }
        
        val screenPath = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(58f * scale, 108f * scale, 342f * scale, 692f * scale),
                    cornerRadius = CornerRadius(26f * scale)
                )
            )
        }
        
        drawPath(path = screenPath, color = screenColor)
        
        // Flash Overlay
        if (time in 3000f..3200f) {
            val flashAlpha = if (time < 3100f) (time - 3000f) / 100f * 0.8f else (3200f - time) / 100f * 0.8f
            drawPath(path = screenPath, color = Color(0xFFE0F2FE), alpha = flashAlpha)
        }

        // --- 2. Content Inside Screen ---
        clipPath(screenPath) {
            // App content placeholders
            drawContentPlaceholders(scale)

            // Three finger swipe down
            if (time in 1500f..3500f) {
                drawThreeFingerSwipe(time, scale)
            }

            // Floating screenshot bubble
            if (time >= 3300f && time < 6800f) {
                drawFloatingBubble(time, scale)
            }

            // Dragging finger
            if (time in 4800f..6800f) {
                drawDraggingFinger(time, scale)
            }

            // Folder targets
            if (time in 5200f..7200f) {
                drawFolderTargets(time, scale)
            }

            // Success indicator
            if (time in 6500f..7300f) {
                drawSuccessIndicator(time, scale)
            }
        }
        
        // Camera Notch
        drawOval(
            color = Color(0xFF1A1A1A),
            topLeft = Offset(170f * scale, 110f * scale),
            size = Size(60f * scale, 16f * scale)
        )
    }
}

private fun DrawScope.drawContentPlaceholders(scale: Float) {
    drawRoundRect(
        color = Color(0xFFE5E7EB),
        topLeft = Offset(80f * scale, 140f * scale),
        size = Size(240f * scale, 14f * scale),
        cornerRadius = CornerRadius(7f * scale)
    )
    drawRoundRect(
        color = Color(0xFFE5E7EB),
        topLeft = Offset(80f * scale, 168f * scale),
        size = Size(180f * scale, 14f * scale),
        cornerRadius = CornerRadius(7f * scale)
    )
    drawRoundRect(
        color = Color(0xFFE5E7EB),
        topLeft = Offset(80f * scale, 196f * scale),
        size = Size(200f * scale, 14f * scale),
        cornerRadius = CornerRadius(7f * scale)
    )
    drawRoundRect(
        color = Color(0xFFF3F4F6),
        topLeft = Offset(80f * scale, 230f * scale),
        size = Size(240f * scale, 80f * scale),
        cornerRadius = CornerRadius(12f * scale)
    )
}

private fun DrawScope.drawThreeFingerSwipe(time: Float, scale: Float) {
    val alpha = when {
        time < 1800f -> (time - 1500f) / 300f
        time > 3200f -> (3500f - time) / 300f
        else -> 1f
    }
    
    val fingerYOffset = if (time in 2000f..3000f) (time - 2000f) / 1000f * 120f else if (time >= 3000f) 120f else -100f
    
    val fingerXs = listOf(150f, 200f, 250f)
    fingerXs.forEach { x ->
        val drawX = x * scale
        val drawY = (250f + fingerYOffset) * scale
        
        drawCircle(
            color = Color(0xFF3B82F6),
            center = Offset(drawX, drawY),
            radius = 18f * scale,
            alpha = 0.3f * alpha
        )
        drawCircle(
            color = Color(0xFF2563EB),
            center = Offset(drawX, drawY),
            radius = 12f * scale,
            alpha = alpha
        )
        
        // Arrow path
        val arrowPath = Path().apply {
            moveTo(drawX, drawY + 20f * scale)
            lineTo(drawX, drawY + 90f * scale)
            moveTo(drawX - 10f * scale, drawY + 80f * scale)
            lineTo(drawX, drawY + 90f * scale)
            lineTo(drawX + 10f * scale, drawY + 80f * scale)
        }
        drawPath(
            path = arrowPath,
            color = Color(0xFF3B82F6),
            style = Stroke(width = 6f * scale, cap = StrokeCap.Round, join = StrokeJoin.Round),
            alpha = 0.8f * alpha
        )
    }
}

private fun DrawScope.drawFloatingBubble(time: Float, scale: Float) {
    val bubbleAlpha = if (time < 3600f) (time - 3300f) / 300f else 1f
    
    var bubbleX = 310f
    var bubbleY = 400f
    var bubbleR = 30f
    
    if (time in 5000f..6500f) {
        val progress = (time - 5000f) / 1500f
        bubbleX = 310f - progress * 110f
        bubbleY = 400f + progress * 170f
    } else if (time >= 6500f) {
        bubbleX = 200f
        bubbleY = 570f
        val shrinkProgress = (time - 6500f) / 300f
        bubbleR = 30f * (1f - shrinkProgress.coerceIn(0f, 1f))
    }
    
    if (bubbleR > 0) {
        // Shadow
        drawCircle(
            color = Color.Black,
            center = Offset((bubbleX + 2f) * scale, (bubbleY + 2f) * scale),
            radius = bubbleR * scale,
            alpha = 0.1f * bubbleAlpha
        )
        // Bubble
        drawCircle(
            color = Color.White,
            center = Offset(bubbleX * scale, bubbleY * scale),
            radius = bubbleR * scale,
            alpha = bubbleAlpha
        )
        drawCircle(
            color = Color(0xFFCBD5E1),
            center = Offset(bubbleX * scale, bubbleY * scale),
            radius = bubbleR * scale,
            style = Stroke(width = 2f * scale),
            alpha = bubbleAlpha
        )
        
        // Mini content inside bubble (scaled)
        if (bubbleR > 5f) {
            val contentScale = bubbleR / 30f
            val baseContentX = bubbleX - 20f * contentScale
            val baseContentY = bubbleY - 15f * contentScale
            
            drawRoundRect(
                color = Color(0xFFE5E7EB),
                topLeft = Offset(baseContentX, baseContentY + 0f * contentScale),
                size = Size(20f * scale * contentScale, 3f * scale * contentScale),
                cornerRadius = CornerRadius(1.5f * scale * contentScale),
                alpha = bubbleAlpha
            )
            drawRoundRect(
                color = Color(0xFFE5E7EB),
                topLeft = Offset(baseContentX, baseContentY + 7f * contentScale),
                size = Size(15f * scale * contentScale, 3f * scale * contentScale),
                cornerRadius = CornerRadius(1.5f * scale * contentScale),
                alpha = bubbleAlpha
            )
            drawRoundRect(
                color = Color(0xFFE5E7EB),
                topLeft = Offset(baseContentX, baseContentY + 14f * contentScale),
                size = Size(18f * scale * contentScale, 3f * scale * contentScale),
                cornerRadius = CornerRadius(1.5f * scale * contentScale),
                alpha = bubbleAlpha
            )
            drawRoundRect(
                color = Color(0xFFF3F4F6),
                topLeft = Offset(baseContentX, baseContentY + 22f * contentScale),
                size = Size(20f * scale * contentScale, 8f * scale * contentScale),
                cornerRadius = CornerRadius(1.5f * scale * contentScale),
                alpha = bubbleAlpha
            )
        }
    }
}

private fun DrawScope.drawDraggingFinger(time: Float, scale: Float) {
    val alpha = when {
        time < 5000f -> (time - 4800f) / 200f
        time > 6600f -> (6800f - time) / 200f
        else -> 1f
    }.coerceIn(0f, 1f)
    
    var fingerOffsetX = 0f
    var fingerOffsetY = 0f
    
    if (time in 5000f..6500f) {
        val progress = (time - 5000f) / 1500f
        fingerOffsetX = -110f * progress
        fingerOffsetY = 170f * progress
    } else if (time > 6500f) {
        fingerOffsetX = -110f
        fingerOffsetY = 170f
    }
    
    val baseCX = 310f + fingerOffsetX
    val baseCY = 400f + fingerOffsetY
    
    // Finger pad
    drawOval(
        color = Color(0xFFFFDBCC),
        topLeft = Offset((baseCX - 12f) * scale, (baseCY - 16f) * scale),
        size = Size(24f * scale, 32f * scale),
        alpha = alpha
    )
    drawOval(
        color = Color(0xFFFFB89D),
        topLeft = Offset((baseCX - 12f) * scale, (baseCY - 16f) * scale),
        size = Size(24f * scale, 32f * scale),
        style = Stroke(width = 1f * scale),
        alpha = alpha
    )
    
    // Fingertip
    drawOval(
        color = Color(0xFFFFD4B8),
        topLeft = Offset((baseCX - 11f) * scale, (baseCY - 12f - 12f) * scale),
        size = Size(22f * scale, 24f * scale),
        alpha = alpha
    )
    drawOval(
        color = Color(0xFFFFB89D),
        topLeft = Offset((baseCX - 11f) * scale, (baseCY - 12f - 12f) * scale),
        size = Size(22f * scale, 24f * scale),
        style = Stroke(width = 1f * scale),
        alpha = alpha
    )
    
    // Fingernail
    drawOval(
        color = Color(0xFFFFE4D9),
        topLeft = Offset((baseCX - 7f) * scale, (baseCY - 6f - 17f) * scale),
        size = Size(14f * scale, 12f * scale),
        alpha = 0.8f * alpha
    )
    
    // Knuckle line
    drawLine(
        color = Color(0xFFFFB89D),
        start = Offset((baseCX - 10f) * scale, (baseCY + 5f) * scale),
        end = Offset((baseCX + 10f) * scale, (baseCY + 5f) * scale),
        strokeWidth = 1f * scale,
        alpha = 0.5f * alpha
    )
}

private fun DrawScope.drawFolderTargets(time: Float, scale: Float) {
    val alpha = when {
        time < 5500f -> (time - 5200f) / 300f
        time > 6900f -> (7200f - time) / 300f
        else -> 1f
    }.coerceIn(0f, 1f)
    
    val yOffset = if (time in 5200f..5600f) (5600f - time) / 400f * 30f else 0f
    
    val folders = listOf(
        Triple(110f, 590f, Color(0xFFFEF3C7) to Color(0xFFFBBF24)),
        Triple(200f, 570f, Color(0xFFDBEAFE) to Color(0xFF3B82F6)),
        Triple(290f, 590f, Color(0xFFFCE7F3) to Color(0xFFEC4899))
    )
    
    folders.forEach { (x, y, colors) ->
        val drawX = x * scale
        val drawY = (y + yOffset) * scale
        
        // Circle background
        drawCircle(
            color = colors.first,
            center = Offset(drawX, drawY),
            radius = 32f * scale,
            alpha = alpha
        )
        drawCircle(
            color = colors.second,
            center = Offset(drawX, drawY),
            radius = 32f * scale,
            style = Stroke(width = 3f * scale),
            alpha = alpha
        )
        
        // Folder icon
        val folderPath = Path().apply {
            moveTo((x - 12f) * scale, (y - 5f + yOffset) * scale)
            lineTo((x - 2f) * scale, (y - 11f + yOffset) * scale)
            lineTo((x + 8f) * scale, (y - 7f + yOffset) * scale)
            lineTo((x + 12f) * scale, (y - 5f + yOffset) * scale)
            lineTo((x + 12f) * scale, (y + 11f + yOffset) * scale)
            lineTo((x - 12f) * scale, (y + 11f + yOffset) * scale)
            close()
        }
        drawPath(path = folderPath, color = colors.second, alpha = 0.9f * alpha)
    }
}

private fun DrawScope.drawSuccessIndicator(time: Float, scale: Float) {
    val alpha = if (time < 6800f) (time - 6500f) / 300f else (7300f - time) / 500f
    val radius1 = 36f * scale
    val radius2 = 28f * scale
    
    drawCircle(
        color = Color(0xFF3B82F6),
        center = Offset(200f * scale, 570f * scale),
        radius = radius1,
        alpha = 0.15f * alpha.coerceIn(0f, 1f)
    )
    drawCircle(
        color = Color(0xFF3B82F6),
        center = Offset(200f * scale, 570f * scale),
        radius = radius2,
        alpha = 0.2f * alpha.coerceIn(0f, 1f)
    )
}
