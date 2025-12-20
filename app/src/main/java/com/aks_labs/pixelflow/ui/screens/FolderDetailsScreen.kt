package com.akslabs.pixelscreenshots.ui.screens

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.clickable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.akslabs.pixelscreenshots.data.models.SimpleScreenshot
import com.akslabs.pixelscreenshots.ui.components.ImmersiveImageViewer
import com.akslabs.pixelscreenshots.ui.components.ScreenshotCarousel
import com.akslabs.pixelscreenshots.ui.components.ScreenshotGridItem
import com.akslabs.pixelscreenshots.ui.components.rememberSelectionManager
import com.akslabs.pixelscreenshots.ui.viewmodels.MainViewModel

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

    // Refresh function to force reload screenshots after deletion
    fun refreshScreenshots() {
        // Force SharedPrefsManager to reload data from storage
        // This will emit new values through the Flow, updating the UI
        viewModel.getSharedPrefsManager().refresh()
    }

    LaunchedEffect(folderId) {
        // Data is now collected via flow, so manual refresh on launch is not strictly necessary
        // unless there's a specific reason to force a ViewModel data refresh.
    }

    val selectionManager = rememberSelectionManager()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val gridColumns by viewModel.gridColumns.collectAsState()
    
    var showCarousel by remember { mutableStateOf(false) }
    var showImmersiveViewer by remember { mutableStateOf(false) }
    var selectedScreenshotIndex by remember { mutableStateOf(0) }
    
    // State for rename dialog
    var showRenameDialog by remember { mutableStateOf(false) }
    var newFolderName by remember { mutableStateOf(folderName) }
    
    // State for delete confirmation dialog
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    // State for physical folder delete confirmation dialog
    var showFolderDeleteDialog by remember { mutableStateOf(false) }

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
                // Use deleteScreenshotByPath for filesystem-scanned screenshots
                viewModel.deleteScreenshotByPath(screenshot.filePath)
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
                // Use deleteScreenshotByPath for filesystem-scanned screenshots
                viewModel.deleteScreenshotByPath(s.filePath)
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
                             Text(
                                 text = folderName, 
                                 fontWeight = FontWeight.Bold,
                                 modifier = Modifier.clickable { 
                                     // Get the folder first
                                     val folder = folders.find { it.id == folderId }
                                     // Only allow rename if folder is editable
                                     if (folder != null && folder.isEditable) {
                                         showRenameDialog = true
                                         newFolderName = folderName
                                     }
                                 }
                             )
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
                                showDeleteDialog = true
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        } else {
                            // Delete button for the entire folder
                            IconButton(onClick = {
                                showFolderDeleteDialog = true
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete Folder")
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
    
    // Rename folder dialog
    if (showRenameDialog) {
        AlertDialog(
            onDismissRequest = { showRenameDialog = false },
            title = { Text("Rename Folder") },
            text = {
                OutlinedTextField(
                    value = newFolderName,
                    onValueChange = { newFolderName = it },
                    label = { Text("Folder Name") },
                    singleLine = true
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newFolderName.isNotBlank() && newFolderName != folderName) {
                            val folder = folders.find { it.id == folderId }
                            if (folder != null) {
                                val updatedFolder = folder.copy(name = newFolderName.trim())
                                viewModel.updateFolder(updatedFolder)
                            }
                        }
                        showRenameDialog = false
                    },
                    enabled = newFolderName.isNotBlank()
                ) {
                    Text("Rename")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRenameDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    
    // Delete screenshots confirmation dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Screenshots") },
            text = { 
                Text("Are you sure you want to delete ${selectionManager.getSelectionCount()} selected screenshot(s)? This action cannot be undone.") 
            },
            confirmButton = {
                Button(
                    onClick = {
                        selectionManager.getSelectedScreenshots().forEach { screenshot ->
                            // Use deleteScreenshotByPath for filesystem-scanned screenshots
                            viewModel.deleteScreenshotByPath(screenshot.filePath)
                        }
                        selectionManager.toggleSelectionMode()
                        refreshScreenshots()
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
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Physical folder delete confirmation dialog
    if (showFolderDeleteDialog) {
        val folder = folders.find { it.id == folderId }
        AlertDialog(
            onDismissRequest = { showFolderDeleteDialog = false },
            title = { Text("Delete Folder Physically") },
            text = { 
                Text("Are you sure you want to physically delete the folder '${folder?.name}' and all its screenshots? This will permanently remove them from your device storage and cannot be undone.") 
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteFolderPhysically(folderId)
                        showFolderDeleteDialog = false
                        navController.navigateUp()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Delete Permanently")
                }
            },
            dismissButton = {
                TextButton(onClick = { showFolderDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
