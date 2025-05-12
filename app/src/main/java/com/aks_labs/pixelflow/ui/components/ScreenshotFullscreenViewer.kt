package com.aks_labs.pixelflow.ui.components

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager
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
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
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

    // Get the window to hide system UI
    val view = LocalView.current
    val context = LocalContext.current

    // Hide system UI when entering fullscreen viewer
    DisposableEffect(Unit) {
        if (!view.isInEditMode) {
            val window = (context as Activity).window
            val originalSystemUiVisibility = window.decorView.systemUiVisibility
            val originalFlags = window.attributes.flags

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
            // Ensure the box takes up the entire screen including status bar area
            .background(Color.Black) // Set background to black to avoid any transparent areas
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
            contentScale = ContentScale.FillBounds, // Use FillBounds to ensure the image covers the entire screen
            modifier = Modifier
                .fillMaxSize()
                // Add a black background to the image itself to ensure no transparent areas
                .background(Color.Black)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offsetX
                    translationY = offsetY
                    // Ensure the image is rendered at the highest level
                    alpha = 1f
                    // Disable clipping to allow content to extend beyond bounds if needed
                    clip = false
                }
        )
    }
}
