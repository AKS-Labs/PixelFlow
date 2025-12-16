package com.aks_labs.pixelflow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign // Added Missing Import
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aks_labs.pixelflow.data.models.SimpleScreenshot
import com.aks_labs.pixelflow.ui.components.ImmersiveImageViewer
import com.aks_labs.pixelflow.ui.components.ScreenshotCarousel
import com.aks_labs.pixelflow.ui.components.ScreenshotGridItem
import com.aks_labs.pixelflow.ui.components.rememberSelectionManager
import com.aks_labs.pixelflow.ui.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderDetailsScreen(
    navController: NavController,
    viewModel: MainViewModel,
    folderId: Long
) {
    val context = LocalContext.current
    
    // Get Folder Name first
    val folders by viewModel.folders.collectAsState(initial = emptyList())
    val folderName = remember(folders, folderId) {
        folders.find { it.id == folderId }?.name ?: "Folder"
    }

    // Get screenshots by folder name (scans filesystem)
    val screenshots by viewModel.getScreenshotsForFolderByNameAsFlow(folderName).collectAsState(initial = emptyList())

    // Refresh function (can be removed if not used elsewhere, but keeping for now)
    fun refreshScreenshots() {
        // The flow now handles updates automatically.
        // This function can be kept for explicit refresh if needed, but its body might be empty
        // or trigger a re-fetch in the ViewModel if that's ever implemented.
    }

    LaunchedEffect(folderId) {
        // Data is now collected via flow, so manual refresh on launch is not strictly necessary
        // unless there's a specific reason to force a ViewModel data refresh.
    }

    val selectionManager = rememberSelectionManager()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val gridColumns by viewModel.gridColumns.collectAsState()
    
    // Viewer States
    var showCarousel by remember { mutableStateOf(false) }
    var showImmersiveViewer by remember { mutableStateOf(false) }
    var selectedScreenshotIndex by remember { mutableStateOf(0) }

    // Handle Back Press for Viewers
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

    if (showImmersiveViewer && screenshots.isNotEmpty()) {
        ImmersiveImageViewer(
            screenshots = screenshots,
            initialIndex = selectedScreenshotIndex,
            onClose = { 
                showImmersiveViewer = false
                showCarousel = true 
            },
            onShare = { screenshot -> viewModel.shareScreenshot(context, screenshot) },
            onDelete = { screenshot ->
                viewModel.deleteScreenshot(screenshot)
                refreshScreenshots()
                // If empty -> close viewer? ImmersiveImageViewer might handle it or crash if list empty
                if (screenshots.isEmpty()) showImmersiveViewer = false
            }
        )
    } else if (showCarousel && screenshots.isNotEmpty()) {
        ScreenshotCarousel(
            screenshots = screenshots,
            initialIndex = selectedScreenshotIndex,
            onClose = { showCarousel = false },
            onScreenshotClick = {
                showImmersiveViewer = true
                showCarousel = false
            },
            onShare = { s -> viewModel.shareScreenshot(context, s) },
            onDelete = { s ->
                viewModel.deleteScreenshot(s)
                refreshScreenshots()
                 if (screenshots.isEmpty()) showCarousel = false
            },
            onEdit = {},
            onMove = {},
            onAddNote = {},
            onPageChanged = { index -> selectedScreenshotIndex = index }
        )
    } else {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                LargeTopAppBar(
                    title = {
                         if (selectionManager.selectionMode) {
                              Text("${selectionManager.getSelectionCount()} Selected")
                         } else {
                             Text(folderName, fontWeight = FontWeight.Bold)
                         }
                    },
                    navigationIcon = {
                        IconButton(onClick = { 
                            if (selectionManager.selectionMode) selectionManager.toggleSelectionMode() 
                            else navController.navigateUp() 
                        }) {
                            Icon(
                                imageVector = if (selectionManager.selectionMode) Icons.Default.Close else Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {
                        if (selectionManager.selectionMode) {
                            IconButton(onClick = {
                                screenshots.forEach { 
                                     if (!selectionManager.isSelected(it)) selectionManager.toggleSelection(it)
                                }
                            }) {
                                Icon(Icons.Default.Done, contentDescription = "Select All")
                            }
                            IconButton(onClick = {
                                viewModel.shareMultipleScreenshots(context, selectionManager.getSelectedScreenshots())
                            }) {
                                Icon(Icons.Default.Share, contentDescription = "Share")
                            }
                            IconButton(onClick = {
                                selectionManager.getSelectedScreenshots().forEach { 
                                    viewModel.deleteScreenshot(it) 
                                }
                                selectionManager.toggleSelectionMode()
                                refreshScreenshots()
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }
                    },
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        scrolledContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (screenshots.isEmpty()) {
                     Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                         Text(
                             text = "No screenshots in this folder",
                             style = MaterialTheme.typography.bodyLarge, // Requires Typography import or use default
                             textAlign = TextAlign.Center, // Requires TextAlign import
                             color = MaterialTheme.colorScheme.onSurfaceVariant
                         )
                     }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(gridColumns),
                        state = rememberLazyGridState(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(screenshots, key = { it.id }) { screenshot ->
                            ScreenshotGridItem(
                                screenshot = screenshot,
                                isSelected = selectionManager.isSelected(screenshot),
                                onClick = {
                                    if (selectionManager.selectionMode) {
                                        selectionManager.toggleSelection(screenshot)
                                    } else {
                                        selectedScreenshotIndex = screenshots.indexOf(screenshot)
                                        showCarousel = true
                                    }
                                },
                                onLongClick = {
                                    if (!selectionManager.selectionMode) selectionManager.toggleSelectionMode()
                                    selectionManager.toggleSelection(screenshot)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
