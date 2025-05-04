package com.aks_labs.pixelflow.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aks_labs.pixelflow.data.models.SimpleScreenshot
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * A grid item that displays a screenshot thumbnail.
 *
 * @param screenshot The screenshot to display
 * @param isSelected Whether the screenshot is selected
 * @param onClick Callback when the screenshot is clicked
 * @param onLongClick Callback when the screenshot is long-clicked
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScreenshotGridItem(
    screenshot: SimpleScreenshot,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 0.95f else 1f,
        label = "selection scale"
    )

    Card(
        modifier = modifier
            .padding(4.dp)
            .scale(scale)
            .aspectRatio(0.5625f) // 9:16 aspect ratio for screenshots
            .clip(MaterialTheme.shapes.medium),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 12.dp else 6.dp, // Increased shadow
            pressedElevation = 8.dp // Shadow when pressed
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                )
        ) {
            // Screenshot image
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(File(screenshot.thumbnailPath))
                    .crossfade(true)
                    .build(),
                contentDescription = "Screenshot",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Selection overlay
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                )
            }

            // No date overlay - removed as requested
        }
    }
}
