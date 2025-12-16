package com.aks_labs.pixelflow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aks_labs.pixelflow.R
import com.aks_labs.pixelflow.data.models.SimpleFolder
import com.aks_labs.pixelflow.ui.viewmodels.MainViewModel
import java.io.File
import com.aks_labs.pixelflow.ui.components.AddFolderDialog
import com.aks_labs.pixelflow.ui.components.EditFolderDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderManagementScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val folders by viewModel.folders.collectAsState(initial = emptyList())
    var showAddDialog by remember { mutableStateOf(false) }
    var editingFolder by remember { mutableStateOf<SimpleFolder?>(null) }
    var folderToDelete by remember { mutableStateOf<SimpleFolder?>(null) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "Collections",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(id = R.string.add_folder))
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(160.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(folders) { folder ->
                    CollectionGridItem(
                        folder = folder,
                        onEdit = { editingFolder = folder },
                        onDelete = { folderToDelete = folder },
                        screenshotCount = viewModel.getScreenshotCountForFolder(folder.id),
                        viewModel = viewModel
                    )
                }
            }
        }

        // Add Folder Dialog
        if (showAddDialog) {
            AddFolderDialog(
                onDismiss = { showAddDialog = false },
                onAddFolder = { name, iconName ->
                    viewModel.addFolder(name, iconName)
                    showAddDialog = false
                }
            )
        }

        // Edit Folder Dialog
        editingFolder?.let { folder ->
            EditFolderDialog(
                folder = folder,
                onDismiss = { editingFolder = null },
                onUpdateFolder = { updatedFolder ->
                    viewModel.updateFolder(updatedFolder)
                    editingFolder = null
                },
                onDelete = {
                    folderToDelete = folder
                    editingFolder = null
                }
            )
        }

        // Delete Folder Confirmation Dialog
        folderToDelete?.let { folder ->
            AlertDialog(
                onDismissRequest = { folderToDelete = null },
                title = { Text("Delete Folder") },
                text = { Text("Are you sure you want to delete the folder '${folder.name}'? This action cannot be undone.") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.deleteFolder(folder)
                            folderToDelete = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    Button(onClick = { folderToDelete = null }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
fun CollectionGridItem(
    folder: SimpleFolder,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    screenshotCount: Int,
    viewModel: MainViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.85f)
            .clickable { onEdit() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Folder Preview Area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                val thumbnailPaths = viewModel.getScreenshotThumbnailsForFolder(folder.id)
                FolderThumbnailStack(thumbnailPaths = thumbnailPaths, folderIconName = folder.iconName)
            }

            // Info Area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(16.dp)
            ) {
                Text(
                    text = folder.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(
                    text = "$screenshotCount screenshots",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun FolderThumbnailStack(
    thumbnailPaths: List<String>,
    folderIconName: String
) {
    val context = LocalContext.current
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (thumbnailPaths.isEmpty()) {
             // Show large icon
             val iconResId = getIconResId(folderIconName)
             Icon(
                 painter = painterResource(id = iconResId),
                 contentDescription = null,
                 modifier = Modifier.size(64.dp),
                 tint = MaterialTheme.colorScheme.primary
             )
        } else {
            // Stack up to 3 thumbnails
            val displayedThumbs = thumbnailPaths.take(3).reversed()
            
            displayedThumbs.forEachIndexed { index, path ->
                // Reverse index because we are drawing from back to front (reversed list)
                // Real index in the original list 0..2
                // We want the first item (0) to be on top.
                // If list is [A, B, C], displayedThumbs is [C, B, A]
                // index 0 = C (bottom), index 1 = B (middle), index 2 = A (top)
                
                val scale = 0.8f + (index * 0.1f) // 0.8, 0.9, 1.0
                val yOffset = ((2 - index) * 8).dp // 16dp, 8dp, 0dp
                
                Card(
                     modifier = Modifier
                         .size(80.dp) // Base size
                         .offset(y = yOffset)
                         .zIndex(index.toFloat()), // Compose doesn't have zIndex modifier in Box directly without explicit import, but usually loop order defines Z.
                     shape = RoundedCornerShape(12.dp),
                     elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(File(path))
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

// Extension to help Z-Index if needed, or just rely on drawing order
fun Modifier.zIndex(zIndex: Float) = this

fun getIconResId(iconName: String): Int {
    return when (iconName) {
        "ic_quotes" -> R.drawable.ic_quotes
        "ic_tricks" -> R.drawable.ic_tricks
        "ic_images" -> R.drawable.ic_images
        "ic_posts" -> R.drawable.ic_posts
        "ic_trash" -> R.drawable.ic_trash
        else -> R.drawable.ic_images
    }
}

// Dialogs moved to ui.components.FolderDialogs.kt
