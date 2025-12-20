package com.akslabs.pixelscreenshots.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * A composable that detects pinch-to-zoom gestures and adjusts the number of columns in a grid.
 *
 * @param minColumns The minimum number of columns allowed
 * @param maxColumns The maximum number of columns allowed
 * @param initialColumns The initial number of columns
 * @param onColumnsChanged Callback when the number of columns changes
 * @param content The content to display
 */
@Composable
fun ZoomableGrid(
    minColumns: Int = 2,
    maxColumns: Int = 5,
    initialColumns: Int = 3,
    onColumnsChanged: (Int) -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    var currentColumns by remember { mutableStateOf(initialColumns) }
    var scale by remember { mutableStateOf(1f) }
    val scaleAnimatable = remember { Animatable(1f) }
    val coroutineScope = rememberCoroutineScope()

    // Initial setup
    LaunchedEffect(Unit) {
        onColumnsChanged(currentColumns)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, _, zoom, _ ->
                    // Accumulate the scale
                    scale *= zoom

                    // Calculate new column count based on scale
                    val newColumns = when {
                        scale > 1.2f -> (currentColumns - 1).coerceAtLeast(minColumns)
                        scale < 0.8f -> (currentColumns + 1).coerceAtMost(maxColumns)
                        else -> currentColumns
                    }

                    // If columns changed, update and reset scale
                    if (newColumns != currentColumns) {
                        currentColumns = newColumns
                        onColumnsChanged(newColumns)
                        scale = 1f

                        // Animate the scale back to 1f
                        coroutineScope.launch {
                            scaleAnimatable.snapTo(if (scale > 1f) 1.2f else 0.8f)
                            scaleAnimatable.animateTo(
                                targetValue = 1f,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                        }
                    }
                }
            },
        content = content
    )
}
