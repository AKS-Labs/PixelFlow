package com.aks_labs.pixelflow.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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
    getThumbnailPath: (Long) -> String?,
    modifier: Modifier = Modifier
) {
    if (folders.isEmpty()) return

    val carouselState = rememberCarouselState { folders.size }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        HorizontalMultiBrowseCarousel(
            state = carouselState,
            preferredItemWidth = 160.dp,
            itemSpacing = 8.dp,
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Height for the carousel content
        ) { index ->
            val folder = folders[index]
            val thumbnailPath = getThumbnailPath(folder.id)

            AlbumCarouselItem(
                folder = folder,
                thumbnailPath = thumbnailPath,
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Image Area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (thumbnailPath != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(File(thumbnailPath))
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    // Fallback icon
                     val iconResId = when (folder.iconName) {
                        "ic_quotes" -> R.drawable.ic_quotes
                        "ic_tricks" -> R.drawable.ic_tricks
                        "ic_images" -> R.drawable.ic_images
                        "ic_posts" -> R.drawable.ic_posts
                        "ic_trash" -> R.drawable.ic_trash
                        else -> R.drawable.ic_images
                    }
                    
                    androidx.compose.material3.Icon(
                         painter = painterResource(id = iconResId),
                         contentDescription = null,
                         modifier = Modifier.size(48.dp),
                         tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Text Label
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = folder.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
