package com.aks_labs.pixelflow.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aks_labs.pixelflow.data.models.SimpleScreenshot
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * A full-screen viewer for screenshots with zoom and swipe gestures.
 *
 * @param screenshots The list of screenshots to display
 * @param initialIndex The initial index to display
 * @param onClose Callback when the viewer is closed
 * @param onShare Callback when a screenshot is shared
 * @param onDelete Callback when a screenshot is deleted
 */
@Composable
fun ScreenshotFullscreenViewer(
    screenshots: List<SimpleScreenshot>,
    initialIndex: Int,
    onClose: () -> Unit,
    onShare: (SimpleScreenshot) -> Unit,
    onDelete: (SimpleScreenshot) -> Unit
) {
    var currentIndex by remember { mutableStateOf(initialIndex) }
    var showControls by remember { mutableStateOf(true) }
    val currentScreenshot = screenshots.getOrNull(currentIndex) ?: return
    val scope = rememberCoroutineScope()

    // Auto-hide controls after a delay
    LaunchedEffect(showControls) {
        if (showControls) {
            delay(2000) // Shorter delay for better UX
            showControls = false
        }
    }

    // Handle system back button
    BackHandler {
        onClose()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { showControls = !showControls }
                )
            }
    ) {
        // Current screenshot - truly fullscreen without app bar
        ZoomableScreenshot(
            screenshot = currentScreenshot,
            onTap = { showControls = !showControls }
        )

        // Minimal navigation controls - just swipe indicators at the edges
        if (currentIndex > 0) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(40.dp)
                    .align(Alignment.CenterStart)
                    .pointerInput(Unit) {
                        detectTapGestures {
                            scope.launch {
                                if (currentIndex > 0) {
                                    currentIndex--
                                }
                            }
                        }
                    }
            )
        }

        if (currentIndex < screenshots.size - 1) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(40.dp)
                    .align(Alignment.CenterEnd)
                    .pointerInput(Unit) {
                        detectTapGestures {
                            scope.launch {
                                if (currentIndex < screenshots.size - 1) {
                                    currentIndex++
                                }
                            }
                        }
                    }
            )
        }

        // Close button - minimal and transparent
        AnimatedVisibility(
            visible = showControls,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .padding(16.dp)
        ) {
            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.3f), CircleShape)
                    .size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        // Minimal bottom controls (share, delete) - more transparent and smaller
        AnimatedVisibility(
            visible = showControls,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .background(Color.Black.copy(alpha = 0.3f))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                IconButton(
                    onClick = { onShare(currentScreenshot) },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = {
                        onDelete(currentScreenshot)
                        // Navigate to previous screenshot if available, otherwise close
                        if (screenshots.size > 1) {
                            if (currentIndex > 0) {
                                currentIndex--
                            } else if (currentIndex < screenshots.size - 1) {
                                // Stay on same index which will now show the next item
                            } else {
                                onClose()
                            }
                        } else {
                            onClose()
                        }
                    },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

/**
 * A zoomable screenshot component.
 *
 * @param screenshot The screenshot to display
 * @param onTap Callback when the screenshot is tapped
 */
@Composable
fun ZoomableScreenshot(
    screenshot: SimpleScreenshot,
    onTap: () -> Unit
) {
    val context = LocalContext.current
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(1f, 3f)

                    // Only apply offset if zoomed in
                    if (scale > 1f) {
                        val maxX = (scale - 1) * size.width / 2
                        val maxY = (scale - 1) * size.height / 2

                        offsetX = (offsetX + pan.x).coerceIn(-maxX, maxX)
                        offsetY = (offsetY + pan.y).coerceIn(-maxY, maxY)
                    } else {
                        // Reset offset when zoomed out
                        offsetX = 0f
                        offsetY = 0f
                    }
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        // Reset zoom on double tap
                        scale = if (scale > 1f) 1f else 2f
                        offsetX = 0f
                        offsetY = 0f
                    },
                    onTap = { onTap() }
                )
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(File(screenshot.filePath))
                .crossfade(true)
                .build(),
            contentDescription = "Screenshot",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offsetX
                    translationY = offsetY
                }
        )
    }
}
