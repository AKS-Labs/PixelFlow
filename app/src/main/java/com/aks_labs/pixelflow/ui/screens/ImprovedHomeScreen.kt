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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
//import com.aks_labs.pixelflow.ui.components.ScreenshotFullscreenViewer
import com.aks_labs.pixelflow.ui.components.ScreenshotGridItem
import com.aks_labs.pixelflow.ui.components.ScreenshotCarousel
import com.aks_labs.pixelflow.ui.components.ImmersiveImageViewer
import com.aks_labs.pixelflow.ui.components.SelectionManager
import com.aks_labs.pixelflow.ui.components.rememberSelectionManager
import com.aks_labs.pixelflow.ui.components.compose.SearchBar
import com.aks_labs.pixelflow.ui.viewmodels.MainViewModel
import com.aks_labs.pixelflow.data.SharedPrefsManager.ThemeMode

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ImprovedHomeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    // Get the current screenshots as PagingItems
    // We strictly use the new pager
    val screenshots = viewModel.screenshotPager.collectAsLazyPagingItems()
    
    val gridColumns by viewModel.gridColumns.collectAsState()
    val context = LocalContext.current

    // Get the current theme mode
    val currentThemeMode by viewModel.themeMode.collectAsState()

    // State for the theme dropdown menu
    var showThemeMenu by remember { mutableStateOf(false) }

    // Search state
    var searchQuery by remember { mutableStateOf("") }

    // Selection manager for multi-select functionality
    val selectionManager = rememberSelectionManager()

    // Grid state for drag selection
    val gridState = rememberLazyGridState()

    // Track drag selection state
    var isDragging by remember { mutableStateOf(false) }
    var lastDraggedIndex by remember { mutableStateOf(-1) }

    // Refresh function using Paging 3
    val pullRefreshState = rememberPullRefreshState(
        refreshing = screenshots.loadState.refresh is androidx.paging.LoadState.Loading,
        onRefresh = { screenshots.refresh() }
    )

    // Initial load handled by Paging 3 automatic collection

    // State for fullscreen viewer
    // State for viewers
    var showCarousel by remember { mutableStateOf(false) }
    var showImmersiveViewer by remember { mutableStateOf(false) }
    var selectedScreenshotIndex by remember { mutableStateOf(0) }
    
    // We need to pass the list to fullscreen viewer. 
    // Since fullscreen viewer expects a List, we can pass the snapshot of loaded items.
    val loadedScreenshots = screenshots.itemSnapshotList.items

    // Handle back press for viewers
    if (showCarousel || showImmersiveViewer) {
        androidx.activity.compose.BackHandler {
            if (showImmersiveViewer) {
                showImmersiveViewer = false
                showCarousel = true
            } else {
                showCarousel = false
            }
        }
    }

    if (showImmersiveViewer && loadedScreenshots.isNotEmpty()) {
        ImmersiveImageViewer(
            screenshots = loadedScreenshots,
            initialIndex = selectedScreenshotIndex,
            onClose = { 
                showImmersiveViewer = false 
                showCarousel = true
            },
            onShare = { screenshot ->
                viewModel.shareScreenshot(context, screenshot)
            },
            onDelete = { screenshot ->
                viewModel.deleteScreenshot(screenshot)
                screenshots.refresh()
            }
        )
    } else if (showCarousel && loadedScreenshots.isNotEmpty()) {
        ScreenshotCarousel(
            screenshots = loadedScreenshots,
            initialIndex = selectedScreenshotIndex,
            onClose = { showCarousel = false },
            onScreenshotClick = {
                // Determine current index from carousel? 
                // Currently ScreenshotCarousel doesn't pass back current index on click, 
                // but we can assume the user wants to see the one they are looking at.
                // However, Pager state is internal to Carousel. 
                // We should pass the initial index as the clicked one if we haven't updated it.
                // Ideally Carousel should report current page.
                // For simplicity, we just open ImmersiveViewer. 
                // Note: If user swiped in Carousel, we need to know the new index.
                // We'll trust the Carousel kept initialIndex or need to update logic to track it.
                // Updating ScreenshotCarousel to expose tracking might be needed, 
                // but for now let's assume index is handled if we pass state.
                showImmersiveViewer = true
                showCarousel = false
            },
            onShare = { screenshot -> viewModel.shareScreenshot(context, screenshot) },
            onDelete = { screenshot ->
                viewModel.deleteScreenshot(screenshot)
                screenshots.refresh()
            },
            onEdit = { /* TODO */ },
            onMove = { /* TODO */ },
            onAddNote = { /* TODO */ },
            onPageChanged = { index ->
                selectedScreenshotIndex = index
            }
        )
    } else {
        // Only show the normal UI if fullscreen viewer is not active
        Scaffold(
            // No topBar parameter - we'll create our own custom top section
        ) { innerPadding ->
            // Custom top section with padding to account for status bar
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                // Custom top section with title and menu
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
                ) {
                    if (selectionManager.selectionMode) {
                        // Selection mode header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { selectionManager.toggleSelectionMode() }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Exit Selection Mode"
                                )
                            }

                            Text(
                                text = "${selectionManager.getSelectionCount()} Selected",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            // Selection mode actions
                            IconButton(onClick = {
                                // Select all currently loaded screenshots
                                for (screenshot in loadedScreenshots) {
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
                                val selectedScreenshots = selectionManager.getSelectedScreenshots()
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
                                val selectedScreenshots = selectionManager.getSelectedScreenshots()
                                selectedScreenshots.forEach { screenshot ->
                                    viewModel.deleteScreenshot(screenshot)
                                }
                                selectionManager.toggleSelectionMode()
                                // Refresh the list
                                screenshots.refresh()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete"
                                )
                            }
                        }
                    } else {
                        // Normal mode header with title and menu
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "PixelFlow",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            // Three-dot menu for theme/settings
                            Box {
                                IconButton(onClick = { showThemeMenu = true }) {
                                    Icon(
                                        imageVector = Icons.Default.MoreVert,
                                        contentDescription = "More options"
                                    )
                                }

                                // Theme dropdown menu
                                DropdownMenu(
                                    expanded = showThemeMenu,
                                    onDismissRequest = { showThemeMenu = false }
                                ) {
                                    // Theme menu header
                                    DropdownMenuItem(
                                        text = { Text("Change Theme") },
                                        onClick = { /* Just a header */ },
                                        enabled = false
                                    )

                                    // System theme option
                                    DropdownMenuItem(
                                        text = { Text("System theme") },
                                        onClick = {
                                            viewModel.setThemeMode(ThemeMode.SYSTEM)
                                            showThemeMenu = false
                                        },
                                        trailingIcon = if (currentThemeMode == ThemeMode.SYSTEM) {
                                            { Icon(Icons.Default.Done, contentDescription = "Selected") }
                                        } else null
                                    )

                                    // Light theme option
                                    DropdownMenuItem(
                                        text = { Text("Light theme") },
                                        onClick = {
                                            viewModel.setThemeMode(ThemeMode.LIGHT)
                                            showThemeMenu = false
                                        },
                                        trailingIcon = if (currentThemeMode == ThemeMode.LIGHT) {
                                            { Icon(Icons.Default.Done, contentDescription = "Selected") }
                                        } else null
                                    )

                                    // Dark theme option
                                    DropdownMenuItem(
                                        text = { Text("Dark theme") },
                                        onClick = {
                                            viewModel.setThemeMode(ThemeMode.DARK)
                                            showThemeMenu = false
                                        },
                                        trailingIcon = if (currentThemeMode == ThemeMode.DARK) {
                                            { Icon(Icons.Default.Done, contentDescription = "Selected") }
                                        } else null
                                    )

                                    // Settings option
                                    DropdownMenuItem(
                                        text = { Text("Settings") },
                                        onClick = {
                                            navController.navigate("settings")
                                            showThemeMenu = false
                                        },
                                        leadingIcon = {
                                            Icon(
                                                imageVector = Icons.Default.Settings,
                                                contentDescription = "Settings"
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                // Search bar from Search.kt
                if (!selectionManager.selectionMode) {
                    SearchBar(
                        query = searchQuery,
                        onQueryChange = { newQuery ->
                            searchQuery = newQuery
                            // TODO: Implement search functionality
                        }
                    )
                }

                // Main content area with pull-to-refresh
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
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
                            onDraggedIndexChanged = { index: Int -> lastDraggedIndex = index },
                            onScreenshotClick = { screenshot: SimpleScreenshot ->
                                if (selectionManager.selectionMode) {
                                    selectionManager.toggleSelection(screenshot)
                                } else {
                                    // We need to find the index of this screenshot in the loaded list
                                    val index = loadedScreenshots.indexOfFirst { item -> item.id == screenshot.id }
                                    if (index != -1) {
                                        selectedScreenshotIndex = index
                                        showCarousel = true
                                    }
                                }
                            }
                        )
                    }

                    // Pull to refresh indicator
                    PullRefreshIndicator(
                        refreshing = screenshots.loadState.refresh is androidx.paging.LoadState.Loading,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            }
        }
    }
}



@Composable
fun ScreenshotsSection(
    screenshots: androidx.paging.compose.LazyPagingItems<SimpleScreenshot>,
    gridColumns: Int,
    selectionManager: SelectionManager,
    gridState: LazyGridState,
    isDragging: Boolean,
    onDragStart: () -> Unit,
    onDragEnd: () -> Unit,
    onDraggedIndexChanged: (Int) -> Unit,
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

        if (screenshots.itemCount == 0 && screenshots.loadState.refresh !is androidx.paging.LoadState.Loading) {
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
                                // Start drag selection mode
                                selectionManager.startDragSelection()
                                onDragStart()
                            },
                            onDragEnd = {
                                // End drag selection mode
                                selectionManager.stopDragSelection()
                                onDragEnd()
                            },
                            onDragCancel = {
                                // End drag selection mode
                                selectionManager.stopDragSelection()
                                onDragEnd()
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                // Just consume the drag event
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
                    items(
                        count = screenshots.itemCount,
                        key = { index -> 
                            val item = screenshots[index]
                            item?.id ?: index 
                        }
                    ) { index ->
                        val screenshot = screenshots[index]
                        
                        if (screenshot != null) {
                            val isSelected = selectionManager.isSelected(screenshot)

                            // Create a reference to track this item's position
                            val itemPositionRef = remember { mutableStateOf(Rect.Zero) }

                            // Use a simpler approach for drag selection
                            if (isDragging && selectionManager.isDragSelecting) {
                                // If we're in drag selection mode, select this item when it's rendered
                                selectionManager.selectDuringDrag(screenshot)
                                onDraggedIndexChanged(index)
                            }

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
                            )
                        } else {
                            // Placeholder if null (loading)
                             Box(
                                modifier = Modifier
                                    .aspectRatio(0.5625f)
                                    .background(Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                            )
                        }
                    }
                }
            }
        }
    }
}
