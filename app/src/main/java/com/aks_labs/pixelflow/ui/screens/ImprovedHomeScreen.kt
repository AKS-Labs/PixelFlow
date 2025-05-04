package com.aks_labs.pixelflow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aks_labs.pixelflow.data.models.SimpleScreenshot
import com.aks_labs.pixelflow.ui.components.ScreenshotFullscreenViewer
import com.aks_labs.pixelflow.ui.components.ScreenshotGridItem
import com.aks_labs.pixelflow.ui.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ImprovedHomeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    // Get the current screenshots
    val screenshots by viewModel.filteredScreenshots.collectAsState()
    val gridColumns by viewModel.gridColumns.collectAsState()
    val context = LocalContext.current

    // Refresh state
    var refreshing by remember { mutableStateOf(false) }

    // Refresh function
    val onRefresh = {
        refreshing = true
        viewModel.loadDeviceScreenshots()
        refreshing = false
        Unit
    }

    // Pull-to-refresh state
    val pullRefreshState = rememberPullRefreshState(refreshing, onRefresh)

    // Refresh screenshots when the screen is shown
    LaunchedEffect(Unit) {
        viewModel.loadDeviceScreenshots()
    }

    // State for fullscreen viewer
    var showFullscreenViewer by remember { mutableStateOf(false) }
    var selectedScreenshotIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pixel Screenshots") },
                actions = {
                    IconButton(onClick = { /* TODO: Implement search */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                    IconButton(onClick = { /* TODO: Implement notifications */ }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications"
                        )
                    }
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .pullRefresh(pullRefreshState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Screenshots Section
                ScreenshotsSection(
                    screenshots = screenshots,
                    gridColumns = gridColumns,
                    onScreenshotClick = { screenshot ->
                        selectedScreenshotIndex = screenshots.indexOf(screenshot)
                        showFullscreenViewer = true
                    }
                )
            }

            // Pull to refresh indicator
            PullRefreshIndicator(
                refreshing = refreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

        // Fullscreen viewer
        if (showFullscreenViewer && screenshots.isNotEmpty()) {
            ScreenshotFullscreenViewer(
                screenshots = screenshots,
                initialIndex = selectedScreenshotIndex,
                onClose = { showFullscreenViewer = false },
                onShare = { screenshot ->
                    viewModel.shareScreenshot(context, screenshot)
                },
                onDelete = { screenshot ->
                    viewModel.deleteScreenshot(screenshot)
                }
            )
        }
    }
}



@Composable
fun ScreenshotsSection(
    screenshots: List<SimpleScreenshot>,
    gridColumns: Int,
    onScreenshotClick: (SimpleScreenshot) -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        // Section header
        Text(
            text = "Screenshots",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        if (screenshots.isEmpty()) {
            // Empty state
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No screenshots yet",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            // Screenshots grid - use a fixed height based on number of screenshots
            val gridHeight = when {
                screenshots.size <= 2 -> 200.dp
                screenshots.size <= 4 -> 400.dp
                else -> 600.dp
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(gridColumns),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(gridHeight)
            ) {
                items(screenshots) { screenshot ->
                    ScreenshotGridItem(
                        screenshot = screenshot,
                        isSelected = false,
                        onClick = { onScreenshotClick(screenshot) },
                        onLongClick = { /* TODO: Implement selection mode */ }
                    )
                }
            }
        }
    }
}
