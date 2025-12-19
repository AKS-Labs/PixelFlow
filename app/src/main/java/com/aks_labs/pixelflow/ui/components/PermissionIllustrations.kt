package com.aks_labs.pixelflow.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath

@Composable
fun OrganizeScreenshotAnimation(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "OrganizeScreenshotTransition")
    
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
        
        // --- 1. Phone Frame with Correct Rounding ---
        val outerRect = Rect(50f * scale, 100f * scale, 350f * scale, 700f * scale)
        val outerPath = Path().apply {
            addRoundRect(RoundRect(outerRect, CornerRadius(30f * scale)))
        }
        drawPath(path = outerPath, color = Color(0xFF1A1A1A))
        
        val innerRect = Rect(58f * scale, 108f * scale, 342f * scale, 692f * scale)
        val innerPath = Path().apply {
            addRoundRect(RoundRect(innerRect, CornerRadius(26f * scale)))
        }
        drawPath(path = innerPath, color = Color.Black)

        // Screen Background & Flash Effect
        val screenColor = when {
            time in 3000f..3100f -> Color(0xFFF0F9FF)
            else -> Color.White
        }
        
        drawPath(path = innerPath, color = screenColor)
        
        // Flash Overlay
        if (time in 3000f..3200f) {
            val flashAlpha = if (time < 3100f) (time - 3000f) / 100f * 0.8f else (3200f - time) / 100f * 0.8f
            drawPath(path = innerPath, color = Color(0xFFE0F2FE), alpha = flashAlpha)
        }

        // --- 2. Content Inside Screen ---
        clipPath(innerPath) {
            drawContentPlaceholders(scale)

            if (time in 1500f..3500f) drawThreeFingerSwipe(time, scale)
            if (time >= 3300f && time < 6800f) drawFloatingBubble(time, scale)
            if (time in 4800f..6800f) drawDraggingFinger(time, scale)
            if (time in 5200f..7200f) drawFolderTargets(time, scale)
            if (time in 6500f..7300f) drawSuccessIndicator(time, scale)
        }
        
        // Camera Notch
        drawOval(
            color = Color(0xFF1A1A1A),
            topLeft = Offset(170f * scale, 110f * scale),
            size = Size(60f * scale, 16f * scale)
        )
    }
}

@Composable
fun MediaAccessAnimation(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "MediaAccessTransition")
    
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 4000f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "AnimationTime"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val scale = size.width / 400f
        val centerX = size.width / 2f
        val centerY = size.height / 2f

        // Draw a pulse effect for "discovery"
        val pulseProgress = (time % 1000) / 1000f
        val pulseScale = 1.0f + 0.2f * pulseProgress
        val pulseAlpha = 0.1f * (1f - pulseProgress)
        
        drawCircle(
            color = Color(0xFF3B82F6),
            center = Offset(centerX, centerY),
            radius = 120f * scale * pulseScale,
            alpha = pulseAlpha
        )

        // Draw some "photo" cards floating around in a circle
        for (i in 0 until 5) {
            val baseAngle = (72 * i).toDouble()
            val rotationAngle = (time / 4000f) * 360f
            val currentAngle = baseAngle + rotationAngle
            
            val radius = 90f * scale
            val x = centerX + Math.cos(Math.toRadians(currentAngle)).toFloat() * radius
            val y = centerY + Math.sin(Math.toRadians(currentAngle)).toFloat() * radius
            
            drawRoundRect(
                color = Color(0xFFE2E8F0),
                topLeft = Offset(x - 30f * scale, y - 40f * scale),
                size = Size(60f * scale, 80f * scale),
                cornerRadius = CornerRadius(8f * scale),
                style = Fill
            )
            drawRoundRect(
                color = Color(0xFFCBD5E1),
                topLeft = Offset(x - 30f * scale, y - 40f * scale),
                size = Size(60f * scale, 80f * scale),
                cornerRadius = CornerRadius(8f * scale),
                style = Stroke(width = 1f * scale)
            )
        }

        // Central Icon (Gallery/Image)
        drawRoundRect(
            color = Color(0xFF3B82F6),
            topLeft = Offset(centerX - 40f * scale, centerY - 40f * scale),
            size = Size(80f * scale, 80f * scale),
            cornerRadius = CornerRadius(16f * scale)
        )
        // Simple "mountain" icon in center
        val iconPath = Path().apply {
            moveTo(centerX - 25f * scale, centerY + 20f * scale)
            lineTo(centerX - 5f * scale, centerY - 10f * scale)
            lineTo(centerX + 10f * scale, centerY + 5f * scale)
            lineTo(centerX + 25f * scale, centerY - 5f * scale)
            lineTo(centerX + 25f * scale, centerY + 20f * scale)
            close()
        }
        drawPath(path = iconPath, color = Color.White)
    }
}

@Composable
fun ManageFilesAnimation(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "ManageFilesTransition")
    
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 3000f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "AnimationTime"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val scale = size.width / 400f
        val centerX = size.width / 2f
        val centerY = size.height / 2f

        // Draw three folders at bottom
        val colors = listOf(Color(0xFFFBBF24), Color(0xFF3B82F6), Color(0xFFEC4899))
        colors.forEachIndexed { index, color ->
            val folderX = centerX + (index - 1) * 110f * scale
            val folderY = centerY + 100f * scale
            
            drawCircle(color = color.copy(alpha = 0.05f), center = Offset(folderX, folderY), radius = 45f * scale)
            
            val folderPath = Path().apply {
                moveTo(folderX - 25f * scale, folderY - 5f * scale)
                lineTo(folderX - 10f * scale, folderY - 15f * scale)
                lineTo(folderX + 10f * scale, folderY - 10f * scale)
                lineTo(folderX + 25f * scale, folderY - 5f * scale)
                lineTo(folderX + 25f * scale, folderY + 20f * scale)
                lineTo(folderX - 25f * scale, folderY + 20f * scale)
                close()
            }
            drawPath(path = folderPath, color = color)
        }

        // Animating "files" flying into folders
        val fileTime = time % 1000
        val folderIndex = (time / 1000).toInt() % 3
        val fileProgress = fileTime / 1000f
        
        val targetX = centerX + (folderIndex - 1) * 110f * scale
        val targetY = centerY + 100f * scale
        
        val startX = centerX
        val startY = centerY - 150f * scale
        
        val currentX = startX + (targetX - startX) * fileProgress
        val currentY = startY + (targetY - startY) * fileProgress
        
        val fileAlpha = when {
            fileProgress < 0.2f -> fileProgress * 5f
            fileProgress > 0.8f -> (1f - fileProgress) * 5f
            else -> 1f
        }.coerceIn(0f, 1f)

        // File Card
        drawRoundRect(
            color = Color.White,
            topLeft = Offset(currentX - 20f * scale, currentY - 25f * scale),
            size = Size(40f * scale, 50f * scale),
            cornerRadius = CornerRadius(6f * scale),
            alpha = fileAlpha
        )
        drawRoundRect(
            color = Color(0xFFCBD5E1),
            topLeft = Offset(currentX - 20f * scale, currentY - 25f * scale),
            size = Size(40f * scale, 50f * scale),
            cornerRadius = CornerRadius(6f * scale),
            style = Stroke(width = 1f * scale),
            alpha = fileAlpha
        )
        // File lines
        drawLine(Color(0xFFE2E8F0), Offset(currentX - 12f * scale, currentY - 10f * scale), Offset(currentX + 12f * scale, currentY - 10f * scale), strokeWidth = 2f * scale, alpha = fileAlpha)
        drawLine(Color(0xFFE2E8F0), Offset(currentX - 12f * scale, currentY), Offset(currentX + 8f * scale, currentY), strokeWidth = 2f * scale, alpha = fileAlpha)
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
        drawCircle(
            color = Color.Black.copy(alpha = 0.1f * bubbleAlpha),
            center = Offset((bubbleX + 2f) * scale, (bubbleY + 2f) * scale),
            radius = bubbleR * scale
        )
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
    }
}

private fun DrawScope.drawDraggingFinger(time: Float, scale: Float) {
    val alpha = when {
        time < 5000f -> (time - 4800f) / 200f
        time > 6600f -> (6800f - time) / 200f
        else -> 1f
    }.coerceIn(0f, 1f)
    
    var progress = 0f
    if (time in 5000f..6500f) progress = (time - 5000f) / 1500f
    else if (time >= 6500f) progress = 1f
    
    val baseCX = 310f - 110f * progress
    val baseCY = 400f + 170f * progress
    
    drawOval(Color(0xFFFFDBCC), Offset((baseCX - 12f) * scale, (baseCY - 16f) * scale), Size(24f * scale, 32f * scale), alpha = alpha)
    drawOval(Color(0xFFFFD4B8), Offset((baseCX - 11f) * scale, (baseCY - 24f) * scale), Size(22f * scale, 24f * scale), alpha = alpha)
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
    }
}

private fun DrawScope.drawSuccessIndicator(time: Float, scale: Float) {
    val alpha = if (time < 6800f) (time - 6500f) / 300f else (7300f - time) / 500f
    drawCircle(
        color = Color(0xFF3B82F6).copy(alpha = 0.15f * alpha.coerceIn(0f, 1f)),
        center = Offset(200f * scale, 570f * scale),
        radius = 36f * scale
    )
}
