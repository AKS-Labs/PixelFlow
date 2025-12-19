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
    val infiniteTransition = rememberInfiniteTransition(label = "MediaAccessPremium")
    
    // Global pulse and rotation
    val pulseProg by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing)), label = "Pulse"
    )
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(20000, easing = LinearEasing)), label = "Rotation"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val scale = size.width / 400f
        val centerX = size.width / 2f
        val centerY = size.height / 2f

        // 1. Background Grid (Depth)
        val gridAlpha = 0.05f
        for (i in -4..4) {
            drawLine(Color.Gray, Offset(centerX + i * 80f * scale, 0f), Offset(centerX + i * 80f * scale, size.height), strokeWidth = 1f * scale, alpha = gridAlpha)
            drawLine(Color.Gray, Offset(0f, centerY + i * 80f * scale), Offset(size.width, centerY + i * 80f * scale), strokeWidth = 1f * scale, alpha = gridAlpha)
        }

        // 2. Lidar-style Scanning Pulse
        drawCircle(
            brush = Brush.radialGradient(
                0.0f to Color(0xFF3B82F6).copy(alpha = 0.3f * (1f - pulseProg)),
                1.0f to Color(0xFF3B82F6).copy(alpha = 0f),
                center = Offset(centerX, centerY),
                radius = (200f * scale * pulseProg).coerceAtLeast(0.01f)
            ),
            radius = 200f * scale * pulseProg,
            center = Offset(centerX, centerY)
        )
        drawCircle(
            color = Color(0xFF3B82F6),
            center = Offset(centerX, centerY),
            radius = 200f * scale * pulseProg,
            style = Stroke(width = 2f * scale),
            alpha = 0.2f * (1f - pulseProg)
        )

        // 3. Living Thumbnails
        for (i in 0 until 6) {
            val baseAngle = (60 * i).toDouble()
            val currentAngle = baseAngle + rotation
            val radius = 120f * scale
            val x = centerX + Math.cos(Math.toRadians(currentAngle)).toFloat() * radius
            val y = centerY + Math.sin(Math.toRadians(currentAngle)).toFloat() * radius
            
            // Individual card float
            val floatOffset = Math.sin(Math.toRadians(currentAngle * 2)).toFloat() * 10f * scale
            
            val cardX = x
            val cardY = y + floatOffset

            // Shadow
            drawRoundRect(
                color = Color.Black.copy(alpha = 0.1f),
                topLeft = Offset(cardX - 32f * scale, cardY - 42f * scale),
                size = Size(64f * scale, 84f * scale),
                cornerRadius = CornerRadius(10f * scale)
            )
            // Card body
            drawRoundRect(
                color = Color.White,
                topLeft = Offset(cardX - 30f * scale, cardY - 40f * scale),
                size = Size(60f * scale, 80f * scale),
                cornerRadius = CornerRadius(8f * scale)
            )
            // Image Placeholder Gradient
            drawRoundRect(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFF1F5F9), Color(0xFFE2E8F0)),
                    start = Offset(cardX - 30f * scale, cardY - 40f * scale),
                    end = Offset(cardX + 30f * scale, cardY + 10f * scale)
                ),
                topLeft = Offset(cardX - 25f * scale, cardY - 35f * scale),
                size = Size(50f * scale, 45f * scale),
                cornerRadius = CornerRadius(4f * scale)
            )
            // Lines
            drawRoundRect(Color(0xFFF1F5F9), Offset(cardX - 25f * scale, cardY + 15f * scale), Size(40f * scale, 6f * scale), CornerRadius(3f * scale))
            drawRoundRect(Color(0xFFF1F5F9), Offset(cardX - 25f * scale, cardY + 25f * scale), Size(25f * scale, 6f * scale), CornerRadius(3f * scale))
        }

        // 4. Central Premium Icon
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFF3B82F6), Color(0xFF2563EB)),
                center = Offset(centerX, centerY),
                radius = 50f * scale
            ),
            center = Offset(centerX, centerY),
            radius = 50f * scale
        )
        // Icon White Content
        val iconWidth = 40f * scale
        val iconHeight = 30f * scale
        val ipath = Path().apply {
            moveTo(centerX - 20f * scale, centerY + 15f * scale)
            lineTo(centerX - 5f * scale, centerY - 10f * scale)
            lineTo(centerX + 5f * scale, centerY + 5f * scale)
            lineTo(centerX + 15f * scale, centerY - 5f * scale)
            lineTo(centerX + 20f * scale, centerY + 15f * scale)
            close()
        }
        drawPath(path = ipath, color = Color.White)
        drawCircle(Color.White, radius = 5f * scale, center = Offset(centerX + 12f * scale, centerY - 15f * scale))
    }
}

@Composable
fun ManageFilesAnimation(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "ManageFilesPremium")
    
    val time by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 3000f,
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing)), label = "Timer"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val scale = size.width / 400f
        val centerX = size.width / 2f
        val centerY = size.height / 2f

        // Folders with interaction
        val folderConfig = listOf(
            Triple(centerX - 120f * scale, Color(0xFFFBBF24), "Yellow"),
            Triple(centerX, Color(0xFF3B82F6), "Blue"),
            Triple(centerX + 120f * scale, Color(0xFFEC4899), "Pink")
        )

        val cycleTime = time % 1000
        val activeFolderIndex = (time / 1000).toInt() % 3
        
        // --- Draw Folders ---
        folderConfig.forEachIndexed { index, (fx, color, _) ->
            // Reactive bounce when file drops in
            val isActive = index == activeFolderIndex
            val bounce = if (isActive && cycleTime > 800) {
                val bProg = (cycleTime - 800) / 200f
                Math.sin(bProg * Math.PI).toFloat() * 15f * scale
            } else 0f

            val fy = centerY + 120f * scale
            
            // Glow
            drawCircle(
                color = color.copy(alpha = 0.1f),
                center = Offset(fx, fy),
                radius = (45f + (if(isActive) 5f else 0f)) * scale
            )
            
            // Folder Path (Premium)
            val fPath = Path().apply {
                moveTo(fx - 35f * scale, fy - 10f * scale)
                lineTo(fx - 15f * scale, fy - 25f * scale)
                lineTo(fx + 10f * scale, fy - 20f * scale)
                lineTo(fx + 35f * scale, fy - 10f * scale)
                lineTo(fx + 35f * scale, fy + 30f * scale)
                lineTo(fx - 35f * scale, fy + 30f * scale)
                close()
            }
            // Add a small lid path for "open" effect if active
            val lidPath = Path().apply {
                val lidAngle = if (isActive && cycleTime in 700f..1000f) -20f else 0f
                moveTo(fx - 35f * scale, fy - 10f * scale)
                lineTo(fx + 35f * scale, fy - 10f * scale)
                lineTo(fx + 38f * scale, fy - 15f * scale + lidAngle * scale)
                lineTo(fx - 32f * scale, fy - 15f * scale + lidAngle * scale)
                close()
            }
            
            drawPath(fPath, brush = Brush.verticalGradient(listOf(color, color.copy(alpha = 0.8f))))
            drawPath(lidPath, color.copy(alpha = 0.9f))
        }

        // --- Draw Flying Files & Trails ---
        val fileProgress = (cycleTime / 1000f)
        val targetX = folderConfig[activeFolderIndex].first
        val targetY = centerY + 120f * scale
        val startX = centerX
        val startY = centerY - 150f * scale
        
        val currX = startX + (targetX - startX) * fileProgress
        val currY = startY + (targetY - startY) * fileProgress
        
        if (fileProgress < 0.9f) {
            // Trail
            for (j in 1..5) {
                val tProg = (fileProgress - j * 0.05f).coerceAtLeast(0f)
                val tx = startX + (targetX - startX) * tProg
                val ty = startY + (targetY - startY) * tProg
                drawCircle(
                    color = Color.White.copy(alpha = 0.3f / j),
                    center = Offset(tx, ty),
                    radius = 10f * scale * (1f - j * 0.1f)
                )
            }

            // File Card
            val fAlpha = when {
                fileProgress < 0.2f -> fileProgress * 5f
                fileProgress > 0.8f -> (1f - fileProgress) * 5f
                else -> 1f
            }
            
            drawRoundRect(
                color = Color.White,
                topLeft = Offset(currX - 25f * scale, currY - 30f * scale),
                size = Size(50f * scale, 65f * scale),
                cornerRadius = CornerRadius(8f * scale),
                alpha = fAlpha
            )
            drawRoundRect(
                color = folderConfig[activeFolderIndex].second.copy(alpha = 0.3f),
                topLeft = Offset(currX - 25f * scale, currY - 30f * scale),
                size = Size(50f * scale, 65f * scale),
                cornerRadius = CornerRadius(8f * scale),
                style = Stroke(width = 2f * scale),
                alpha = fAlpha
            )
            // Accent
            drawRoundRect(
                folderConfig[activeFolderIndex].second,
                Offset(currX - 15f * scale, currY - 20f * scale),
                Size(30f * scale, 6f * scale),
                CornerRadius(2f * scale),
                alpha = fAlpha
            )
        }
        
        // --- Success "Puff" ---
        if (cycleTime > 900) {
            val pX = targetX
            val pY = targetY
            val pProg = (cycleTime - 900) / 100f
            drawCircle(
                color = Color.White.copy(alpha = 0.5f * (1f - pProg)),
                center = Offset(pX, pY),
                radius = 60f * scale * pProg
            )
        }
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
