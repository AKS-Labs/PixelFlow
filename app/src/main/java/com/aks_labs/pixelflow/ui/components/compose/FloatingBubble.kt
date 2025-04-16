package com.aks_labs.pixelflow.ui.components.compose

import android.graphics.Bitmap
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A composable that displays a floating bubble with a screenshot.
 *
 * @param screenshotBitmap The bitmap to display in the bubble
 * @param size The size of the bubble (default: 90.dp)
 * @param isDragging Whether the bubble is currently being dragged
 * @param onClick Callback for when the bubble is clicked
 * @param onDrag Callback for when the bubble is dragged, with the drag amount
 */
@Composable
fun FloatingBubble(
    screenshotBitmap: Bitmap,
    size: Dp = 90.dp,
    isDragging: Boolean = false,
    onClick: () -> Unit = {},
    onDrag: (androidx.compose.ui.geometry.Offset) -> Unit = {},
    onDragStart: () -> Unit = {},
    onDragEnd: () -> Unit = {}
) {
    // Scale factor for when the bubble is being dragged
    val scale by animateFloatAsState(
        targetValue = if (isDragging) 1.056f else 1f, // 95dp/90dp = 1.056
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "bubbleScale"
    )

    // Animate the elevation when dragging
    val elevation by animateDpAsState(
        targetValue = if (isDragging) 8.dp else 4.dp,
        animationSpec = tween(150),
        label = "bubbleElevation"
    )

    // Create the card with a circular shape
    Card(
        modifier = Modifier
            .size(size)
            .scale(scale) // Apply scale animation
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { onDragStart() },
                    onDragEnd = { onDragEnd() },
                    onDragCancel = { onDragEnd() },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        onDrag(dragAmount)
                    }
                )
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick() }
                )
            },
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        // Display the screenshot image
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                bitmap = screenshotBitmap.asImageBitmap(),
                contentDescription = "Screenshot",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }
    }
}
