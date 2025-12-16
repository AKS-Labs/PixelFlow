package com.aks_labs.pixelflow.ui.components.compose

import android.graphics.Bitmap
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
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
        targetValue = if (isDragging) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "bubbleScale"
    )

    // Animate the elevation when dragging
    val elevation by animateDpAsState(
        targetValue = if (isDragging) 12.dp else 6.dp,
        animationSpec = tween(150),
        label = "bubbleElevation"
    )
    
    // M3 Expressive Shape: "Squircle" or Large Rounded Corner
    val shape = RoundedCornerShape(28.dp)

    Surface(
        modifier = Modifier
            .size(size)
            .scale(scale)
            .shadow(elevation, shape)
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
        shape = shape,
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 6.dp,
        border = androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                bitmap = screenshotBitmap.asImageBitmap(),
                contentDescription = "Screenshot",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape)
            )
        }
    }
}
