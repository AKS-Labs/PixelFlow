package com.akslabs.pixelscreenshots.ui.components

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.toSize
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.akslabs.pixelscreenshots.data.models.SimpleScreenshot
import kotlinx.coroutines.delay
import java.io.File
import kotlin.math.abs

/**
 * A full-screen viewer for screenshots with zoom and swipe gestures.
 * Renamed from ScreenshotFullscreenViewer.
 *
 * @param screenshots The list of screenshots to display
 * @param initialIndex The initial index to display
 * @param onClose Callback when the viewer is closed
 * @param onShare Callback when a screenshot is shared
 * @param onDelete Callback when a screenshot is deleted
 */
@Composable
fun ImmersiveImageViewer(
    screenshots: List<SimpleScreenshot>,
    initialIndex: Int,
    onClose: () -> Unit,
    onShare: (SimpleScreenshot) -> Unit,
    onDelete: (SimpleScreenshot) -> Unit
) {
    var currentIndex by remember { mutableStateOf(initialIndex) }
    var showControls by remember { mutableStateOf(true) }
    val currentScreenshot = screenshots.getOrNull(currentIndex) ?: return
    
    // State for delete confirmation dialog
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    // State for swipe animation
    var swipeOffset by remember { mutableStateOf(0f) }
    var isAnimating by remember { mutableStateOf(false) }
    var swipeDirection by remember { mutableStateOf(0) } // -1 for left, 1 for right, 0 for none

    // Animate the swipe offset
    val animatedSwipeOffset by animateFloatAsState(
        targetValue = swipeOffset,
        animationSpec = tween(durationMillis = 250),
        finishedListener = {
            // Reset the offset after animation completes
            if (isAnimating) {
                // Change the index based on swipe direction
                if (swipeDirection < 0 && currentIndex < screenshots.size - 1) {
                    currentIndex++
                } else if (swipeDirection > 0 && currentIndex > 0) {
                    currentIndex--
                }

                // Reset state
                swipeOffset = 0f
                isAnimating = false
                swipeDirection = 0
            }
        }
    )

    // Track whether the image is zoomed in
    val isZoomed = remember { mutableStateOf(false) }

    // Get the window to hide system UI
    val view = LocalView.current
    val context = LocalContext.current

    // Hide system UI when entering fullscreen viewer
    DisposableEffect(Unit) {
        if (!view.isInEditMode) {
            val window = (context as Activity).window
            
            // Set window to be fullscreen and extend into the cutout area
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                window.attributes.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }

            // Make sure the window background is transparent to avoid black bars
            window.setBackgroundDrawableResource(android.R.color.transparent)

            // Set the status bar and navigation bar to be fully transparent
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()

            // Add flags to make the app truly fullscreen
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)

            // Set system UI flags for true fullscreen
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

            // Make content draw behind system bars
            WindowCompat.setDecorFitsSystemWindows(window, false)

            // Hide system UI completely for fullscreen experience
            WindowInsetsControllerCompat(window, view).let { controller ->
                // Hide both status bar and navigation bar
                controller.hide(WindowInsetsCompat.Type.systemBars())
                // Allow showing bars with swipe
                controller.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }

        onDispose {
            if (!view.isInEditMode) {
                val window = (context as Activity).window

                // Remove fullscreen flags
                window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)

                // Reset cutout mode
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    window.attributes.layoutInDisplayCutoutMode =
                        WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
                }

                // Restore system UI when leaving fullscreen
                WindowCompat.setDecorFitsSystemWindows(window, false)
                WindowInsetsControllerCompat(window, view).show(WindowInsetsCompat.Type.systemBars())
            }
        }
    }

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
            // Ensure the box takes up the entire screen including status bar area
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { showControls = !showControls }
                )
            }
    ) {
        // Add a transparent overlay for horizontal swipe detection
        // This is placed before the ZoomableScreenshot so it doesn't interfere with zoom gestures
        if (!isAnimating && !isZoomed.value) { // Only enable swipe when not animating and not zoomed
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onDragStart = { /* No action needed */ },
                            onDragEnd = {
                                // Determine if the swipe was significant enough to change the image
                                val swipeThreshold = size.width / 6 // More reasonable threshold (1/6 of screen width)

                                if (abs(swipeOffset) > swipeThreshold) {
                                    // Determine swipe direction
                                    if (swipeOffset < 0 && currentIndex < screenshots.size - 1) {
                                        // Swipe right-to-left (next image)
                                        isAnimating = true
                                        swipeDirection = -1
                                        swipeOffset = -size.width.toFloat() // Animate to full screen width
                                    } else if (swipeOffset > 0 && currentIndex > 0) {
                                        // Swipe left-to-right (previous image)
                                        isAnimating = true
                                        swipeDirection = 1
                                        swipeOffset = size.width.toFloat() // Animate to full screen width
                                    } else {
                                        // Can't swipe in this direction, animate back
                                        swipeOffset = 0f
                                    }
                                } else {
                                    // Not enough swipe distance, reset position
                                    swipeOffset = 0f
                                }
                            },
                            onDragCancel = {
                                // Reset position on cancel
                                swipeOffset = 0f
                            },
                            onHorizontalDrag = { change, dragAmount ->
                                change.consume()

                                // Only allow swiping in valid directions
                                val canSwipeLeft = currentIndex < screenshots.size - 1
                                val canSwipeRight = currentIndex > 0

                                // Apply resistance at the edges
                                val newOffset = swipeOffset + dragAmount
                                swipeOffset = when {
                                    // Swiping right-to-left but at the last image
                                    newOffset < 0 && !canSwipeLeft -> 0f
                                    // Swiping left-to-right but at the first image
                                    newOffset > 0 && !canSwipeRight -> 0f
                                    // Normal swipe within bounds, but limit to screen width
                                    else -> newOffset.coerceIn(-size.width.toFloat(), size.width.toFloat())
                                }
                            }
                        )
                    }
            )
        }

        // Current screenshot - truly fullscreen without app bar
        ZoomableScreenshot(
            screenshot = currentScreenshot,
            onTap = { showControls = !showControls },
            offsetX = animatedSwipeOffset, // Apply the swipe animation offset
            isZoomed = isZoomed // Pass the zoom state
        )

        // Close button - minimal and transparent
        AnimatedVisibility(
            visible = showControls,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.TopStart)
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
                    onClick = { showDeleteDialog = true },
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
        
        // Delete confirmation dialog
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Delete Screenshot?") },
                text = { Text("Are you sure you want to delete this screenshot? This action cannot be undone.") },
                confirmButton = {
                    Button(
                        onClick = {
                            onDelete(currentScreenshot)
                            showDeleteDialog = false
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
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
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
    onTap: () -> Unit,
    offsetX: Float = 0f, // Add parameter for swipe animation
    isZoomed: MutableState<Boolean> = remember { mutableStateOf(false) } // Add parameter to communicate zoom state
) {
    val context = LocalContext.current
    var scale by remember { mutableStateOf(1f) }
    var zoomOffsetX by remember { mutableStateOf(0f) } // Renamed to avoid conflict
    var offsetY by remember { mutableStateOf(0f) }
    var size by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }

    // Update the isZoomed state whenever scale changes
    LaunchedEffect(scale) {
        isZoomed.value = scale > 1f
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            // Ensure the box takes up the entire screen including status bar area
            .background(Color.Black) // Set background to black to avoid any transparent areas
            .onGloballyPositioned { coordinates ->
                size = androidx.compose.ui.geometry.Size(
                    coordinates.size.width.toFloat(),
                    coordinates.size.height.toFloat()
                )
            }
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(1f, 3f)

                    // Only apply offset if zoomed in
                    if (scale > 1f) {
                        val maxX = (scale - 1) * size.width / 2
                        val maxY = (scale - 1) * size.height / 2

                        zoomOffsetX = (zoomOffsetX + pan.x).coerceIn(-maxX, maxX)
                        offsetY = (offsetY + pan.y).coerceIn(-maxY, maxY)
                    } else {
                        // Reset offset when zoomed out
                        zoomOffsetX = 0f
                        offsetY = 0f
                    }
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        // Reset zoom on double tap
                        scale = if (scale > 1f) 1f else 2f
                        zoomOffsetX = 0f
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
            contentScale = ContentScale.FillBounds, // Use FillBounds to ensure the image covers the entire screen
            modifier = Modifier
                .fillMaxSize()
                // Add a black background to the image itself to ensure no transparent areas
                .background(Color.Black)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = zoomOffsetX + offsetX // Combine zoom offset with swipe offset
                    translationY = offsetY
                    // Ensure the image is rendered at the highest level
                    alpha = 1f
                    // Disable clipping to allow content to extend beyond bounds if needed
                    clip = false
                }
        )
    }
}
