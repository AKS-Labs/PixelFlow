package com.aks_labs.pixelflow.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aks_labs.pixelflow.data.models.SimpleScreenshot
import java.io.File

/**
 * A vertical fullscreen carousel for browsing screenshots.
 * 
 * @param screenshots The list of screenshots to display
 * @param initialIndex The initial index to display
 * @param onClose Callback when the back button is clicked
 * @param onScreenshotClick Callback when a screenshot is clicked (to open immersive viewer)
 * @param onShare Callback when share button is clicked
 * @param onDelete Callback when delete button is clicked
 * @param onEdit Callback when edit button is clicked
 * @param onMove Callback when move button is clicked
 * @param onAddNote Callback when add note button is clicked
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ScreenshotCarousel(
    screenshots: List<SimpleScreenshot>,
    initialIndex: Int,
    onClose: () -> Unit,
    onScreenshotClick: () -> Unit,
    onShare: (SimpleScreenshot) -> Unit,
    onDelete: (SimpleScreenshot) -> Unit,
    onEdit: (SimpleScreenshot) -> Unit = {},
    onMove: (SimpleScreenshot) -> Unit = {},
    onAddNote: (SimpleScreenshot) -> Unit = {},
    onPageChanged: (Int) -> Unit = {}
) {
    val pagerState = rememberPagerState(
        initialPage = initialIndex,
        pageCount = { screenshots.size }
    )
    val context = LocalContext.current

    LaunchedEffect(pagerState.currentPage) {
        onPageChanged(pagerState.currentPage)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Carousel",
                        style = MaterialTheme.typography.titleLarge
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Settings placeholder */ }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Description text
                Text(
                    text = "Fullscreen",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                
                Text(
                    text = "Fullscreen carousels allow browsing with a focus on one item at a time.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )

                // Debug options button (visual placeholder based on screenshot)
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable { /* No action */ }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Show debug options",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                // Vertical Pager
                VerticalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                    pageSpacing = 16.dp,
                    pageSize = PageSize.Fill
                ) { page ->
                    val screenshot = screenshots.getOrNull(page)
                    if (screenshot != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(24.dp))
                                .background(Color.Black.copy(alpha = 0.1f)) // Placeholder bg
                                .clickable { onScreenshotClick() }
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(File(screenshot.filePath))
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Screenshot",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            
                            // Overlay buttons at top of the card
                            Row(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Add Note Button
                                SmallOverlayButton(
                                    icon = Icons.Default.Add,
                                    contentDescription = "Add Note",
                                    onClick = { onAddNote(screenshot) }
                                )
                                
                                // Move Button
                                SmallOverlayButton(
                                    icon = Icons.Default.Send,
                                    contentDescription = "Move",
                                    onClick = { onMove(screenshot) }
                                )
                                
                                // Edit Button
                                SmallOverlayButton(
                                    icon = Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    onClick = { onEdit(screenshot) }
                                )
                                
                                // Share Button
                                SmallOverlayButton(
                                    icon = Icons.Default.Share,
                                    contentDescription = "Share",
                                    onClick = { onShare(screenshot) }
                                )
                                
                                // Delete Button
                                SmallOverlayButton(
                                    icon = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    onClick = { onDelete(screenshot) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SmallOverlayButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Black.copy(alpha = 0.4f),
            contentColor = Color.White
        ),
        modifier = Modifier.size(36.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(20.dp)
        )
    }
}
