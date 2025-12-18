package com.aks_labs.pixelflow.ui.screens

import androidx.activity.compose.BackHandler

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

    // Folder data for Carousel
    val folders by viewModel.folders.collectAsState(initial = emptyList())
    // Get thumbnails map from ViewModel (assuming it exposes it, we saw _albumsThumbnailsMap in MainViewModel)
    // We might need to trigger a refresh for thumbnails if they aren't loaded. 
    // In MainViewModel, `refreshAlbums` populates the map.
    // Let's trigger it once when folders are loaded.
    LaunchedEffect(folders) {
        if (folders.isNotEmpty()) {
             // We need to trigger thumbnail loading. 
             // Ideally this should be automatic in ViewModel, but per existing code:
             viewModel.refreshAlbums(context, folders, com.aks_labs.pixelflow.data.models.MediaItemSortMode.DateTaken)
        }
    }

    // Selection manager for multi-select functionality
    val selectionManager = rememberSelectionManager()

    // Grid state for drag selection
    val gridState = rememberLazyGridState()

    // Track drag selection state
    var isDragging by remember { mutableStateOf(false) }
    var lastDraggedIndex by remember { mutableStateOf(-1) }
    var dragSelectionMode by remember { mutableStateOf<Boolean?>(null) } // null = not started, true = selecting, false = deselecting

    // Dialog state for delete confirmation
    var showDeleteDialog by remember { mutableStateOf(false) }

    // BackHandler for selection mode
    if (selectionManager.selectionMode) {
        BackHandler {
            selectionManager.toggleSelectionMode()
        }
    }

    // Refresh function using Paging 3
    val pullRefreshState = rememberPullRefreshState(
        refreshing = screenshots.loadState.refresh is androidx.paging.LoadState.Loading,
        onRefresh = { 
            screenshots.refresh() 
            // Also refresh albums
             if (folders.isNotEmpty()) {
                 viewModel.refreshAlbums(context, folders, com.aks_labs.pixelflow.data.models.MediaItemSortMode.DateTaken)
             }
        }
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
                // Custom top section with title and menu - FIXED at top
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
                                // Show delete confirmation dialog
                                if (selectionManager.getSelectionCount() > 0) {
                                    showDeleteDialog = true
                                }
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
                        // Screenshots Section with Scrollable Header (SearchBar + Carousel)
                        ScreenshotsSection(
                            screenshots = screenshots,
                            gridColumns = gridColumns,
                            selectionManager = selectionManager,
                            gridState = gridState,
                            isDragging = isDragging,
                            onDragStart = { 
                                isDragging = true
                                dragSelectionMode = null // Reset on new drag
                            },
                            onDragEnd = { 
                                isDragging = false
                                dragSelectionMode = null // Reset after drag
                            },
                            onDraggedIndexChanged = { index: Int -> lastDraggedIndex = index },
                            dragSelectionMode = dragSelectionMode,
                            onDragSelectionModeSet = { mode -> dragSelectionMode = mode },
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
                            },
                            headerContent = {
                                if (!selectionManager.selectionMode) {
                                    Column {
                                        SearchBar(
                                            query = searchQuery,
                                            onQueryChange = { newQuery ->
                                                searchQuery = newQuery
                                                // TODO: Implement search functionality
                                            }
                                        )
                                        
                                        // Album Carousel
                                        com.aks_labs.pixelflow.ui.components.AlbumCarousel(
                                            folders = folders,
                                            onAlbumClick = { folder ->
                                                navController.navigate("folder_details/${folder.id}")
                                            },
                                            onSeeAllClick = {
                                                navController.navigate("folders")
                                            },
                                            getThumbnailPaths = { folderId ->
                                                // Access the SnapshotStateMap directly to ensure recomposition when it updates
                                                val thumbnail = viewModel.albumsThumbnailsMap[folderId]
                                                if (thumbnail != null) listOf(thumbnail.filePath) else emptyList()
                                            }
                                        )
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

                // Delete confirmation dialog
                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        title = { Text("Delete Screenshots") },
                        text = {
                            Text("Are you sure you want to delete ${selectionManager.getSelectionCount()} selected screenshot(s)?")
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    // Delete selected screenshots
                                    val selectedScreenshots = selectionManager.getSelectedScreenshots()
                                    selectedScreenshots.forEach { screenshot ->
                                        viewModel.deleteScreenshot(screenshot)
                                    }
                                    selectionManager.toggleSelectionMode()
                                    screenshots.refresh()
                                    showDeleteDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Text("Delete")
                            }
                        },
                        dismissButton = {
                            Button(onClick = { showDeleteDialog = false }) {
                                Text("Cancel")
                            }
                        }
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
    dragSelectionMode: Boolean?,
    onDragSelectionModeSet: (Boolean) -> Unit,
    onScreenshotClick: (SimpleScreenshot) -> Unit,
    headerContent: @Composable () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
    ) {
        // Section header - REMOVED fixed "Screenshots" text, moving it inside if needed or just implicit.
        
        if (screenshots.itemCount == 0 && screenshots.loadState.refresh !is androidx.paging.LoadState.Loading) {
            // Empty state
            // Still show header even if empty
            Column {
                headerContent()
                
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
                    // HEADER ITEM
                    item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(gridColumns) }) {
                        Column {
                            headerContent()
                            Text(
                                text = if (selectionManager.selectionMode) "Select Screenshots" else "Screenshots",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }

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

                            // Use toggle approach for drag selection
                            if (isDragging && selectionManager.isDragSelecting) {
                                // Determine selection mode on first item if not set
                                if (dragSelectionMode == null) {
                                    val shouldSelect = !isSelected
                                    onDragSelectionModeSet(shouldSelect)
                                    selectionManager.setSelectionDuringDrag(screenshot, shouldSelect)
                                } else {
                                    // Apply the determined mode to all subsequent items
                                    selectionManager.setSelectionDuringDrag(screenshot, dragSelectionMode)
                                }
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
