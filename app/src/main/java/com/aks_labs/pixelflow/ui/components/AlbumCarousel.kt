package com.aks_labs.pixelflow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aks_labs.pixelflow.R
import com.aks_labs.pixelflow.data.models.SimpleFolder
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumCarousel(
    folders: List<SimpleFolder>,
    onAlbumClick: (SimpleFolder) -> Unit,
    onSeeAllClick: () -> Unit,
    getThumbnailPaths: (Long) -> List<String>,
    modifier: Modifier = Modifier
) {
    if (folders.isEmpty()) return

    val carouselState = rememberCarouselState { folders.size }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        // Header
        // Removed inner horizontal padding because the parent (LazyVerticalGrid) already provides 16dp padding.
        // This ensures alignment with the "Screenshots" title.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp), 
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Collections",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            TextButton(
                onClick = onSeeAllClick,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "See all",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        // Carousel
        HorizontalMultiBrowseCarousel(
            state = carouselState,
            preferredItemWidth = 120.dp,
            itemSpacing = 8.dp,
            // Set horizontal content padding to 0 because parent already has 16dp.
            // This ensures the first item aligns perfectly with the "Collections" title and grid items.
            contentPadding = PaddingValues(horizontal = 0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp) // Adjusted height for strict square look
        ) { index ->
            val folder = folders[index]
            val thumbnailPaths = getThumbnailPaths(folder.id)
            val mainThumbnail = thumbnailPaths.firstOrNull()

            AlbumCarouselItem(
                folder = folder,
                thumbnailPath = mainThumbnail,
                onClick = { onAlbumClick(folder) }
            )
        }
    }
}

@Composable
fun AlbumCarouselItem(
    folder: SimpleFolder,
    thumbnailPath: String?,
    onClick: () -> Unit
) {
    // Strict rounded corners as requested
    val shape = RoundedCornerShape(16.dp)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Main Thumbnail
            if (thumbnailPath != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(File(thumbnailPath))
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape) // Strictly clip the image to the card shape
                )
            } else {
                // Fallback Icon
                 val iconResId = when (folder.iconName) {
                    "ic_quotes" -> R.drawable.ic_quotes
                    "ic_tricks" -> R.drawable.ic_tricks
                    "ic_images" -> R.drawable.ic_images
                    "ic_posts" -> R.drawable.ic_posts
                    "ic_trash" -> R.drawable.ic_trash
                    else -> R.drawable.ic_images
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material3.Icon(
                         painter = painterResource(id = iconResId),
                         contentDescription = null,
                         modifier = Modifier.size(48.dp),
                         tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            // Translucent Name Overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.6f)
                            )
                        )
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = folder.name,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
        }
    }
}





