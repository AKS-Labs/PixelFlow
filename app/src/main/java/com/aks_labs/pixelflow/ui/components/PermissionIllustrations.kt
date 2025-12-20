package com.akslabs.pixelscreenshots.ui.components

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
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp

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
    val infiniteTransition = rememberInfiniteTransition(label = "ManageFilesCreative")
    
    val time by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 4500f,
        animationSpec = infiniteRepeatable(tween(4500, easing = LinearEasing)), label = "GlobalTimer"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val scale = size.width / 400f
        val centerX = size.width / 2f
        val centerY = size.height / 2f

        // --- 1. Background Circuitry & Nodes (Atmosphere) ---
        val circuitryAlpha = 0.05f
        val circuitryColor = Color(0xFF94A3B8)
        
        // Static motherboard lines (Filling the edges)
        drawLine(circuitryColor, Offset(40f * scale, centerY - 160f * scale), Offset(360f * scale, centerY - 160f * scale), 1f * scale, alpha = circuitryAlpha)
        drawLine(circuitryColor, Offset(40f * scale, centerY + 180f * scale), Offset(360f * scale, centerY + 180f * scale), 1f * scale, alpha = circuitryAlpha)
        drawLine(circuitryColor, Offset(centerX, 40f * scale), Offset(centerX, size.height - 40f * scale), 1f * scale, alpha = circuitryAlpha)
        
        // Diagonal connecting lines for depth
        drawLine(circuitryColor, Offset(40f * scale, centerY - 160f * scale), Offset(centerX, 40f * scale), 1.5f * scale, alpha = circuitryAlpha * 0.7f)
        drawLine(circuitryColor, Offset(360f * scale, centerY - 160f * scale), Offset(centerX, 40f * scale), 1.5f * scale, alpha = circuitryAlpha * 0.7f)

        // Pulsing nodes at key intersections
        val nodePulse = 1f + 0.2f * Math.sin(time.toDouble() * 0.006).toFloat()
        val nodes = listOf(
            Offset(centerX, centerY - 160f * scale),
            Offset(40f * scale, centerY - 160f * scale),
            Offset(360f * scale, centerY - 160f * scale),
            Offset(centerX, 40f * scale),
            Offset(centerX, size.height - 40f * scale)
        )
        nodes.forEach { pos ->
            drawCircle(circuitryColor, 6f * scale * nodePulse, pos, alpha = circuitryAlpha * 4)
            drawCircle(circuitryColor, 14f * scale * nodePulse, pos, alpha = circuitryAlpha * 1.5f, style = Stroke(1.2f * scale))
        }

        // --- 2. Floating Atmospheric Particles (Subtle Motion) ---
        for (i in 0 until 24) {
            val pPhase = (time + i * 187f) % 4500f / 4500f
            val pX = (15f + (i * 17f)) * scale % size.width
            val pY = (size.height - (pPhase * size.height))
            val pAlpha = (1f - Math.abs(pPhase - 0.5f) * 2f) * 0.12f
            
            drawCircle(
                color = if (i % 3 == 0) Color(0xFF3B82F6) else Color.White,
                radius = (1.2f + (i % 2) * 1f) * scale,
                center = Offset(pX, pY),
                alpha = pAlpha
            )
        }

        // --- 3. Enhanced Data Streams (Higher Variety) ---
        val streamAlpha = 0.06f
        for (i in 0 until 20) {
            val sX = (centerX - 195f * scale) + (i * 20.5f * scale)
            val sSpeed = 0.25f + (i % 5) * 0.18f
            val sY = (time * sSpeed * scale) % size.height
            val sLen = 80f * scale * (1f + (i % 4) * 0.25f)
            
            drawLine(
                color = Color.Gray,
                start = Offset(sX, sY),
                end = Offset(sX, sY + sLen),
                strokeWidth = (1f + (i % 2) * 1f) * scale,
                alpha = streamAlpha
            )
        }

        // --- 4. Digital Platform & Grid ---
        val platformPath = Path().apply {
            moveTo(centerX - 190f * scale, centerY + 190f * scale)
            lineTo(centerX + 190f * scale, centerY + 190f * scale)
            lineTo(centerX + 155f * scale, centerY + 155f * scale)
            lineTo(centerX - 155f * scale, centerY + 155f * scale)
            close()
        }
        drawPath(platformPath, brush = Brush.verticalGradient(listOf(Color(0xFFE2E8F0).copy(alpha = 0.7f), Color.Transparent)))
        
        // Grid pattern on the platform for depth
        for (i in -4..4) {
            val gxOffset = i * 42f * scale
            drawLine(Color.White, Offset(centerX + gxOffset, centerY + 190f * scale), Offset(centerX + i * 34f * scale, centerY + 155f * scale), 1f * scale, alpha = 0.12f)
        }

        // --- 5. Folders (Positioned on Platform) ---
        val folderConfig = listOf(
            Triple(centerX - 110f * scale, Color(0xFFFBBF24), "Docs"),
            Triple(centerX, Color(0xFF3B82F6), "Images"),
            Triple(centerX + 110f * scale, Color(0xFFEC4899), "Audio")
        )

        val cycleDuration = 1500f
        val cycleTime = time % cycleDuration
        val activeIndex = (time / cycleDuration).toInt() % 3
        
        folderConfig.forEachIndexed { index, (fx, color, _) ->
            val isActive = index == activeIndex
            val fy = centerY + 120f * scale
            
            val jiggle = if (isActive && cycleTime > 1100f) {
                val jProg = (cycleTime - 1100f) / 400f
                Math.sin(jProg * Math.PI * 4).toFloat() * 5f * scale
            } else 0f

            drawCircle(
                color = Color.Black.copy(alpha = 0.06f),
                center = Offset(fx, fy + 15f * scale),
                radius = 50f * scale
            )
            
            val fPath = Path().apply {
                moveTo(fx - 42f * scale + jiggle, fy - 15f * scale)
                lineTo(fx - 22f * scale + jiggle, fy - 28f * scale)
                lineTo(fx + 22f * scale + jiggle, fy - 24f * scale)
                lineTo(fx + 42f * scale + jiggle, fy - 15f * scale)
                lineTo(fx + 42f * scale + jiggle, fy + 35f * scale)
                lineTo(fx - 42f * scale + jiggle, fy + 35f * scale)
                close()
            }
            drawPath(fPath, brush = Brush.verticalGradient(listOf(color.copy(alpha = 0.98f), color)))
            
            val lidAngle = if (isActive && cycleTime in 1000f..1400f) -40f else 0f
            val lidPath = Path().apply {
                moveTo(fx - 42f * scale + jiggle, fy - 15f * scale)
                lineTo(fx + 42f * scale + jiggle, fy - 15f * scale)
                lineTo(fx + 46f * scale + jiggle, fy - 24f * scale + lidAngle * scale)
                lineTo(fx - 38f * scale + jiggle, fy - 24f * scale + lidAngle * scale)
                close()
            }
            drawPath(lidPath, color.copy(alpha = 0.92f))
        }

        // --- 6. Central Spawner Hub & Immersive Glow ---
        val hubY = centerY - 115f * scale
        val hubBounce = Math.sin(time.toDouble() * 0.005).toFloat() * 8f * scale
        
        // Massive radial glow
        drawCircle(
            brush = Brush.radialGradient(listOf(Color(0xFF3B82F6).copy(alpha = 0.3f), Color.Transparent), center = Offset(centerX, hubY + hubBounce), radius = 100f * scale),
            radius = 100f * scale,
            center = Offset(centerX, hubY + hubBounce)
        )
        
        val hubRadius = 52f * scale
        drawCircle(
            brush = Brush.radialGradient(listOf(Color(0xFF94A3B8), Color(0xFF475569)), center = Offset(centerX, hubY + hubBounce), radius = hubRadius),
            radius = hubRadius,
            center = Offset(centerX, hubY + hubBounce)
        )
        
        rotate(degrees = time * 0.2f, pivot = Offset(centerX, hubY + hubBounce)) {
            drawRect(Color.White, Offset(centerX - 16f * scale, hubY - 16f * scale + hubBounce), Size(32f * scale, 32f * scale), style = Stroke(3.5f * scale), alpha = 0.75f)
            drawCircle(Color.White, radius = 5f * scale, center = Offset(centerX, hubY + hubBounce), alpha = 0.9f)
        }

        // --- 7. Flying Files & Particle Trails ---
        val fProg = (cycleTime / cycleDuration)
        if (fProg < 0.95f) {
            val startX = centerX
            val startY = hubY + hubBounce
            val targetX = folderConfig[activeIndex].first
            val targetY = centerY + 110f * scale

            val controlX = centerX + (targetX - centerX) * 0.35f
            val controlY = centerY - 220f * scale

            val currX = (1 - fProg) * (1 - fProg) * startX + 2 * (1 - fProg) * fProg * controlX + fProg * fProg * targetX
            val currY = (1 - fProg) * (1 - fProg) * startY + 2 * (1 - fProg) * fProg * controlY + fProg * fProg * targetY

            val fileColor = folderConfig[activeIndex].second
            val fAlpha = if (fProg < 0.12f) fProg * 8.3f else if (fProg > 0.88f) (1f - fProg) * 8.3f else 1f
            
            for (j in 1..15) {
                val t = (fProg - j * 0.018f).coerceAtLeast(0f)
                val tx = (1 - t) * (1 - t) * startX + 2 * (1 - t) * t * controlX + t * t * targetX
                val ty = (1 - t) * (1 - t) * startY + 2 * (1 - t) * t * controlY + t * t * targetY
                drawCircle(
                    color = fileColor.copy(alpha = 0.35f / j),
                    center = Offset(tx, ty),
                    radius = 12f * scale * (1f - j * 0.06f)
                )
            }

            rotate(degrees = fProg * 630f, pivot = Offset(currX, currY)) {
                drawRoundRect(
                    color = Color.White,
                    topLeft = Offset(currX - 26f * scale, currY - 34f * scale),
                    size = Size(52f * scale, 68f * scale),
                    cornerRadius = CornerRadius(9f * scale),
                    alpha = fAlpha.coerceIn(0f, 1f)
                )
                drawRoundRect(
                    color = fileColor.copy(alpha = 0.75f),
                    topLeft = Offset(currX - 26f * scale, currY - 34f * scale),
                    size = Size(52f * scale, 68f * scale),
                    cornerRadius = CornerRadius(9f * scale),
                    style = Stroke(width = 3.5f * scale),
                    alpha = fAlpha.coerceIn(0f, 1f)
                )
                when (activeIndex) {
                    0 -> {
                        drawLine(fileColor, Offset(currX - 16f * scale, currY - 5f * scale), Offset(currX + 16f * scale, currY - 5f * scale), strokeWidth = 3f * scale, alpha = fAlpha)
                        drawLine(fileColor, Offset(currX - 16f * scale, currY + 5f * scale), Offset(currX + 10f * scale, currY + 5f * scale), strokeWidth = 3f * scale, alpha = fAlpha)
                    }
                    1 -> drawCircle(color = fileColor, center = Offset(currX, currY), radius = 11f * scale, alpha = fAlpha)
                    2 -> {
                        drawLine(fileColor, Offset(currX - 14f * scale, currY - 10f * scale), Offset(currX + 14f * scale, currY + 10f * scale), strokeWidth = 4.5f * scale, alpha = fAlpha)
                        drawLine(fileColor, Offset(currX - 14f * scale, currY + 10f * scale), Offset(currX + 14f * scale, currY - 10f * scale), strokeWidth = 1.2f * scale, alpha = fAlpha * 0.6f)
                    }
                }
            }
        }

        // --- 8. Impact & Energetic Shockwave ---
        if (cycleTime > 1300f) {
            val iProg = (cycleTime - 1300f) / 200f
            val ifx = folderConfig[activeIndex].first
            val ify = centerY + 120f * scale
            
            drawCircle(
                color = folderConfig[activeIndex].second.copy(alpha = 0.5f * (1f - iProg)),
                center = Offset(ifx, ify),
                radius = 90f * scale * iProg,
                style = Stroke(width = 3.5f * scale)
            )
            if (iProg > 0.25f) {
                val sProg = (iProg - 0.25f) / 0.75f
                drawCircle(
                    color = Color.White.copy(alpha = 0.25f * (1f - sProg)),
                    center = Offset(ifx, ify),
                    radius = 50f * scale * sProg,
                    style = Stroke(width = 1.5f * scale)
                )
            }
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
@Composable
fun FeaturesAnimation(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "FeaturesAnimationTransition")
    
    val time by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 4000f,
        animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing)), label = "GlobalTimer"
    )

    Canvas(modifier = modifier.fillMaxWidth().height(280.dp)) {
        val scale = size.width / 400f
        val centerX = size.width / 2f
        val centerY = size.height / 2f

        // --- 1. Background Particles & Atmosphere ---
        for (i in 0 until 15) {
            val pPhase = (time + i * 267f) % 4000f / 4000f
            val pX = (centerX - 180f * scale) + (i * 25f * scale) % (360f * scale)
            val pY = (centerY - 100f * scale) + (Math.sin(pPhase * 2 * Math.PI).toFloat() * 60f * scale)
            val pAlpha = (1f - Math.abs(pPhase - 0.5f) * 2f) * 0.1f
            
            drawCircle(
                color = if (i % 2 == 0) Color(0xFF3B82F6) else Color(0xFF94A3B8),
                radius = (2f + (i % 3)) * scale,
                center = Offset(pX, pY),
                alpha = pAlpha
            )
        }

        // --- 2. Central Phone Mockup ---
        val phoneW = 140f * scale
        val phoneH = 260f * scale
        val phoneX = centerX - phoneW / 2
        val phoneY = centerY - phoneH / 2
        
        // Shadow
        drawRoundRect(
            color = Color.Black.copy(alpha = 0.05f),
            topLeft = Offset(phoneX + 4f * scale, phoneY + 6f * scale),
            size = Size(phoneW, phoneH),
            cornerRadius = CornerRadius(24f * scale)
        )
        
        // Body
        drawRoundRect(
            color = Color(0xFFF1F5F9),
            topLeft = Offset(phoneX, phoneY),
            size = Size(phoneW, phoneH),
            cornerRadius = CornerRadius(24f * scale),
            style = Fill
        )
        drawRoundRect(
            color = Color(0xFFCBD5E1),
            topLeft = Offset(phoneX, phoneY),
            size = Size(phoneW, phoneH),
            cornerRadius = CornerRadius(24f * scale),
            style = Stroke(width = 2f * scale)
        )
        
        // Screen area
        val screenMargin = 8f * scale
        drawRoundRect(
            color = Color.White,
            topLeft = Offset(phoneX + screenMargin, phoneY + screenMargin),
            size = Size(phoneW - screenMargin * 2, phoneH - screenMargin * 2),
            cornerRadius = CornerRadius(18f * scale)
        )
        
        // Fake content (Screenshot mockup)
        val contentX = phoneX + screenMargin + 10f * scale
        val contentY = phoneY + screenMargin + 20f * scale
        
        // Header in mockup
        drawRect(Color(0xFFE2E8F0), Offset(contentX, contentY), Size(40f * scale, 8f * scale), alpha = 0.8f)
        
        // Image placehold in mockup
        drawRoundRect(
            color = Color(0xFFF3F4F6),
            topLeft = Offset(contentX, contentY + 20f * scale),
            size = Size(phoneW - (screenMargin + 10f * scale) * 2, 80f * scale),
            cornerRadius = CornerRadius(8f * scale)
        )
        
        // Text lines in mockup
        for (i in 0..3) {
            val ty = contentY + 115f * scale + i * 15f * scale
            val tw = if (i == 3) 50f else 80f
            drawRect(Color(0xFFF1F5F9), Offset(contentX, ty), Size(tw * scale, 6f * scale))
        }

        // --- 3. OCR Scanning Beam ---
        val scanPhase = (time % 2000f) / 2000f
        val scanY = phoneY + screenMargin + (scanPhase * (phoneH - screenMargin * 2))
        
        if (scanPhase < 0.95f) {
            val beamAlpha = if (scanPhase < 0.1f) scanPhase * 10f else if (scanPhase > 0.85f) (0.95f - scanPhase) * 10f else 1f
            
            // Beam line
            drawLine(
                brush = Brush.horizontalGradient(listOf(Color.Transparent, Color(0xFF3B82F6).copy(alpha = 0.6f * beamAlpha), Color.Transparent)),
                start = Offset(phoneX + screenMargin, scanY),
                end = Offset(phoneX + phoneW - screenMargin, scanY),
                strokeWidth = 3f * scale
            )
            
            // Beam glow
            drawRect(
                brush = Brush.verticalGradient(listOf(Color(0xFF3B82F6).copy(alpha = 0.15f * beamAlpha), Color.Transparent)),
                topLeft = Offset(phoneX + screenMargin, scanY - 40f * scale),
                size = Size(phoneW - screenMargin * 2, 40f * scale)
            )
        }

        // --- 4. Floating Feature Icons ---
        
        // Icon 1: Search/OCR (Top Left)
        val icon1X = centerX - 120f * scale
        val icon1Y = centerY - 80f * scale + Math.sin(time * 0.003).toFloat() * 10f * scale
        drawIconBackground(icon1X, icon1Y, scale, Color(0xFFDBEAFE), Color(0xFF3B82F6))
        drawSearchIcon(icon1X, icon1Y, scale, Color(0xFF2563EB))
        
        // Icon 2: Folder/Organization (Bottom Right)
        val icon2X = centerX + 120f * scale
        val icon2Y = centerY + 60f * scale + Math.cos(time * 0.003).toFloat() * 10f * scale
        drawIconBackground(icon2X, icon2Y, scale, Color(0xFFFEF3C7), Color(0xFFFBBF24))
        drawFolderIcon(icon2X, icon2Y, scale, Color(0xFFD97706))
        
        // Icon 3: Shield/Privacy (Top Right)
        val icon3X = centerX + 110f * scale
        val icon3Y = centerY - 100f * scale + Math.sin(time * 0.0025 + 1f).toFloat() * 8f * scale
        drawIconBackground(icon3X, icon3Y, scale, Color(0xFFDCFCE7), Color(0xFF22C55E))
        drawShieldIcon(icon3X, icon3Y, scale, Color(0xFF16A34A))
    }
}

private fun DrawScope.drawIconBackground(x: Float, y: Float, scale: Float, bgColor: Color, strokeColor: Color) {
    val radius = 28f * scale
    drawCircle(
        color = bgColor,
        center = Offset(x, y),
        radius = radius
    )
    drawCircle(
        color = strokeColor,
        center = Offset(x, y),
        radius = radius,
        style = Stroke(width = 2f * scale),
        alpha = 0.5f
    )
}

private fun DrawScope.drawSearchIcon(x: Float, y: Float, scale: Float, color: Color) {
    val r = 8f * scale
    drawCircle(color, r, Offset(x - 2f * scale, y - 2f * scale), style = Stroke(2.5f * scale))
    drawLine(color, Offset(x + 2f * scale, y + 2f * scale), Offset(x + 10f * scale, y + 10f * scale), strokeWidth = 3f * scale, cap = StrokeCap.Round)
}

private fun DrawScope.drawFolderIcon(x: Float, y: Float, scale: Float, color: Color) {
    val w = 18f * scale
    val h = 14f * scale
    val fx = x - w/2
    val fy = y - h/2 + 2f * scale
    
    val path = Path().apply {
        moveTo(fx, fy)
        lineTo(fx + 6f * scale, fy)
        lineTo(fx + 8f * scale, fy - 3f * scale)
        lineTo(fx + w, fy - 3f * scale)
        lineTo(fx + w, fy + h)
        lineTo(fx, fy + h)
        close()
    }
    drawPath(path, color, style = Stroke(width = 2.5f * scale, join = StrokeJoin.Round))
}

private fun DrawScope.drawShieldIcon(x: Float, y: Float, scale: Float, color: Color) {
    val w = 16f * scale
    val h = 20f * scale
    val sx = x
    val sy = y - h/2
    
    val path = Path().apply {
        moveTo(sx, sy)
        lineTo(sx + w/2, sy + 4f * scale)
        lineTo(sx + w/2, sy + h - 6f * scale)
        quadraticBezierTo(sx + w/2, sy + h, sx, sy + h)
        quadraticBezierTo(sx - w/2, sy + h, sx - w/2, sy + h - 6f * scale)
        lineTo(sx - w/2, sy + 4f * scale)
        close()
    }
    drawPath(path, color, style = Stroke(width = 2.5f * scale, join = StrokeJoin.Round))
}
