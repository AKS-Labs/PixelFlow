package com.aks_labs.pixelflow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aks_labs.pixelflow.data.models.SimpleFolder
import com.aks_labs.pixelflow.data.models.SimpleScreenshot
import com.aks_labs.pixelflow.ui.components.ScreenshotFullscreenViewer
import com.aks_labs.pixelflow.ui.components.ScreenshotGridItem
import com.aks_labs.pixelflow.ui.components.SelectionManager
import com.aks_labs.pixelflow.ui.components.rememberSelectionManager
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

    // Selection manager for multi-select functionality
    val selectionManager = rememberSelectionManager()

    // Grid state for drag selection
    val gridState = rememberLazyGridState()

    // Track drag selection state
    var isDragging by remember { mutableStateOf(false) }
    var lastDraggedIndex by remember { mutableStateOf(-1) }

    // Track touch position for drag selection
    var currentDragPosition by remember { mutableStateOf(Offset.Zero) }

    // Refresh function
    val onRefresh = {
        refreshing = true
        viewModel.refreshScreenshotsImmediately()
        refreshing = false
        Unit
    }

    // Pull-to-refresh state
    val pullRefreshState = rememberPullRefreshState(refreshing, onRefresh)

    // Refresh screenshots when the screen is shown or becomes active
    LaunchedEffect(Unit) {
        viewModel.refreshScreenshotsImmediately()
    }

    // State for fullscreen viewer
    var showFullscreenViewer by remember { mutableStateOf(false) }
    var selectedScreenshotIndex by remember { mutableStateOf(0) }

    // If fullscreen viewer is shown, display it outside the Scaffold
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
    } else {
        // Only show the normal UI if fullscreen viewer is not active
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        if (selectionManager.selectionMode) {
                            Text("${selectionManager.getSelectionCount()} Selected")
                        } else {
                            Text("Pixel Screenshots")
                        }
                    },
                    navigationIcon = {
                        if (selectionManager.selectionMode) {
                            IconButton(onClick = { selectionManager.toggleSelectionMode() }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Exit Selection Mode"
                                )
                            }
                        }
                    },
                    actions = {
                        if (selectionManager.selectionMode) {
                            // Selection mode actions
                            IconButton(onClick = {
                                // Select all screenshots
                                screenshots.forEach { screenshot ->
                                    if (!selectionManager.isSelected(screenshot)) {
                                        selectionManager.toggleSelection(screenshot)
                                    }
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "Select All"
                                )
                            }

                            IconButton(onClick = {
                                // Share selected screenshots
                                val selectedScreenshots = selectionManager.getSelectedScreenshots(screenshots)
                                if (selectedScreenshots.isNotEmpty()) {
                                    // Use the new method to share multiple screenshots
                                    viewModel.shareMultipleScreenshots(context, selectedScreenshots)
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Share"
                                )
                            }

                            IconButton(onClick = {
                                // Delete selected screenshots
                                val selectedScreenshots = selectionManager.getSelectedScreenshots(screenshots)
                                selectedScreenshots.forEach { screenshot ->
                                    viewModel.deleteScreenshot(screenshot)
                                }
                                selectionManager.toggleSelectionMode()
                                // Refresh the list
                                viewModel.refreshScreenshotsImmediately()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete"
                                )
                            }
                        } else {
                            // Normal mode actions
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
                        .padding(bottom = 0.dp)
                ) {
                    // Screenshots Section
                    ScreenshotsSection(
                        screenshots = screenshots,
                        gridColumns = gridColumns,
                        selectionManager = selectionManager,
                        gridState = gridState,
                        isDragging = isDragging,
                        onDragStart = { isDragging = true },
                        onDragEnd = { isDragging = false },
                        onDraggedIndexChanged = { index -> lastDraggedIndex = index },
                        onDragPositionChanged = { position -> currentDragPosition = position },
                        onScreenshotClick = { screenshot ->
                            if (selectionManager.selectionMode) {
                                selectionManager.toggleSelection(screenshot)
                            } else {
                                selectedScreenshotIndex = screenshots.indexOf(screenshot)
                                showFullscreenViewer = true
                            }
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
        }
    }
}



@Composable
fun ScreenshotsSection(
    screenshots: List<SimpleScreenshot>,
    gridColumns: Int,
    selectionManager: SelectionManager,
    gridState: LazyGridState,
    isDragging: Boolean,
    onDragStart: () -> Unit,
    onDragEnd: () -> Unit,
    onDraggedIndexChanged: (Int) -> Unit,
    onDragPositionChanged: (Offset) -> Unit, // Keep this for future improvements
    onScreenshotClick: (SimpleScreenshot) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
    ) {
        // Section header
        Text(
            text = if (selectionManager.selectionMode) "Select Screenshots" else "Screenshots",
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
            // Use LazyVerticalGrid with weight to avoid white space at bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                onDragStart()
                                onDragPositionChanged(offset)
                            },
                            onDragEnd = {
                                onDragEnd()
                            },
                            onDragCancel = {
                                onDragEnd()
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                // Update the current drag position
                                onDragPositionChanged(change.position)
                            }
                        )
                    }
            ) {
                LazyVerticalGrid(
                    state = gridState,
                    columns = GridCells.Fixed(gridColumns),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 0.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    items(screenshots) { screenshot ->
                        val isSelected = selectionManager.isSelected(screenshot)

                        val itemIndex = screenshots.indexOf(screenshot)

                        // Create a reference to track this item's position
                        val itemPositionRef = remember { mutableStateOf(Rect.Zero) }

                        ScreenshotGridItem(
                            screenshot = screenshot,
                            isSelected = isSelected,
                            onClick = { onScreenshotClick(screenshot) },
                            onLongClick = {
                                if (!selectionManager.selectionMode) {
                                    selectionManager.toggleSelectionMode()
                                }
                                selectionManager.toggleSelection(screenshot)
                            },
                            modifier = Modifier
                                .onGloballyPositioned { coordinates ->
                                    // Store this item's position on screen
                                    itemPositionRef.value = coordinates.boundsInWindow()
                                }
                                .pointerInput(isDragging, screenshot.id) {
                                    if (isDragging) {
                                        detectTapGestures { offset ->
                                            // When dragging over an item, select it
                                            if (!selectionManager.selectionMode) {
                                                selectionManager.toggleSelectionMode()
                                            }
                                            if (!selectionManager.isSelected(screenshot)) {
                                                selectionManager.toggleSelection(screenshot)
                                                onDraggedIndexChanged(itemIndex)
                                            }
                                        }
                                    }
                                }
                        )
                    }
                }
            }
        }
    }
}
