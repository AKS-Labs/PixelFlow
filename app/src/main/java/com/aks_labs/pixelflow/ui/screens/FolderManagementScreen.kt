package com.aks_labs.pixelflow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.io.File
import com.aks_labs.pixelflow.R
import com.aks_labs.pixelflow.data.models.SimpleFolder
import com.aks_labs.pixelflow.ui.theme.CollectionsItemBackground
import com.aks_labs.pixelflow.ui.theme.LightAccent
import com.aks_labs.pixelflow.ui.theme.LightBackground
import com.aks_labs.pixelflow.ui.viewmodels.MainViewModel

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

    Scaffold(
        containerColor = LightBackground,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Collections",
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = LightBackground,
                    titleContentColor = Color.Black
                ),
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(LightAccent)
                            .clickable { navController.navigateUp() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(LightAccent)
                            .clickable { showAddDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.add_folder),
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "My collections",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(folders) { folder ->
                    CollectionItem(
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
fun CollectionItem(
    folder: SimpleFolder,
    onEdit: () -> Unit = {},  // Not used in current UI but kept for future use
    onDelete: () -> Unit = {},  // Not used in current UI but kept for future use
    screenshotCount: Int,
    viewModel: MainViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clickable { /* Handle click on collection item */ },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = CollectionsItemBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Folder icon or stacked screenshots preview
            Box(
                modifier = Modifier
                    .size(width = 60.dp, height = 50.dp)
            ) {
                // Get thumbnail paths for this folder
                val thumbnailPaths = viewModel.getScreenshotThumbnailsForFolder(folder.id)
                val context = LocalContext.current

                // Create stacked effect with actual thumbnails
                if (screenshotCount > 0 && thumbnailPaths.isNotEmpty()) {
                    // Determine how many thumbnails we have (up to 3)
                    val thumbnailCount = thumbnailPaths.size

                    // If we have at least 1 thumbnail, show it as the bottom/left card
                    if (thumbnailCount >= 1) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .offset(x = 0.dp, y = 0.dp)
                                .shadow(1.dp, RoundedCornerShape(8.dp))
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            // Check if file exists before loading
                            val file = File(thumbnailPaths[0])
                            if (file.exists() && file.length() > 0) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(file)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Screenshot 1",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                // Show folder icon if file doesn't exist
                                val iconResId = when (folder.iconName) {
                                    "ic_quotes" -> R.drawable.ic_quotes
                                    "ic_tricks" -> R.drawable.ic_tricks
                                    "ic_images" -> R.drawable.ic_images
                                    "ic_posts" -> R.drawable.ic_posts
                                    "ic_trash" -> R.drawable.ic_trash
                                    else -> R.drawable.ic_images
                                }
                                Icon(
                                    painter = painterResource(id = iconResId),
                                    contentDescription = folder.name,
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }

                    // If we have at least 2 thumbnails, show the second as the middle card
                    if (thumbnailCount >= 2) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .offset(x = 8.dp, y = 0.dp)
                                .shadow(1.dp, RoundedCornerShape(8.dp))
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            // Check if file exists before loading
                            val file = File(thumbnailPaths[1])
                            if (file.exists() && file.length() > 0) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(file)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Screenshot 2",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                // Show folder icon if file doesn't exist
                                val iconResId = when (folder.iconName) {
                                    "ic_quotes" -> R.drawable.ic_quotes
                                    "ic_tricks" -> R.drawable.ic_tricks
                                    "ic_images" -> R.drawable.ic_images
                                    "ic_posts" -> R.drawable.ic_posts
                                    "ic_trash" -> R.drawable.ic_trash
                                    else -> R.drawable.ic_images
                                }
                                Icon(
                                    painter = painterResource(id = iconResId),
                                    contentDescription = folder.name,
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }

                    // If we have 3 thumbnails, show the third as the top/right card
                    if (thumbnailCount >= 3) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .offset(x = 16.dp, y = 0.dp)
                                .shadow(1.dp, RoundedCornerShape(8.dp))
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            // Check if file exists before loading
                            val file = File(thumbnailPaths[2])
                            if (file.exists() && file.length() > 0) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(file)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Screenshot 3",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                // Show folder icon if file doesn't exist
                                val iconResId = when (folder.iconName) {
                                    "ic_quotes" -> R.drawable.ic_quotes
                                    "ic_tricks" -> R.drawable.ic_tricks
                                    "ic_images" -> R.drawable.ic_images
                                    "ic_posts" -> R.drawable.ic_posts
                                    "ic_trash" -> R.drawable.ic_trash
                                    else -> R.drawable.ic_images
                                }
                                Icon(
                                    painter = painterResource(id = iconResId),
                                    contentDescription = folder.name,
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                } else {
                    // Empty state - show folder icon in a gray box
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .shadow(1.dp, RoundedCornerShape(8.dp))
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        // Get icon resource ID from name
                        val iconResId = when (folder.iconName) {
                            "ic_quotes" -> R.drawable.ic_quotes
                            "ic_tricks" -> R.drawable.ic_tricks
                            "ic_images" -> R.drawable.ic_images
                            "ic_posts" -> R.drawable.ic_posts
                            "ic_trash" -> R.drawable.ic_trash
                            else -> R.drawable.ic_images
                        }

                        Icon(
                            painter = painterResource(id = iconResId),
                            contentDescription = folder.name,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Collection info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = folder.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Text(
                    text = "$screenshotCount screenshots",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFolderDialog(
    onDismiss: () -> Unit,
    onAddFolder: (name: String, iconName: String) -> Unit
) {
    var folderName by remember { mutableStateOf("") }
    var selectedIconName by remember { mutableStateOf("ic_images") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.add_folder)) },
        text = {
            Column {
                OutlinedTextField(
                    value = folderName,
                    onValueChange = { folderName = it },
                    label = { Text("Folder Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Select Icon")

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FolderIconOption(
                        iconResId = R.drawable.ic_quotes,
                        iconName = "ic_quotes",
                        isSelected = selectedIconName == "ic_quotes",
                        onClick = { selectedIconName = "ic_quotes" }
                    )

                    FolderIconOption(
                        iconResId = R.drawable.ic_tricks,
                        iconName = "ic_tricks",
                        isSelected = selectedIconName == "ic_tricks",
                        onClick = { selectedIconName = "ic_tricks" }
                    )

                    FolderIconOption(
                        iconResId = R.drawable.ic_images,
                        iconName = "ic_images",
                        isSelected = selectedIconName == "ic_images",
                        onClick = { selectedIconName = "ic_images" }
                    )

                    FolderIconOption(
                        iconResId = R.drawable.ic_posts,
                        iconName = "ic_posts",
                        isSelected = selectedIconName == "ic_posts",
                        onClick = { selectedIconName = "ic_posts" }
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (folderName.isNotBlank()) {
                        onAddFolder(folderName, selectedIconName)
                    }
                },
                enabled = folderName.isNotBlank()
            ) {
                Text(text = "Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFolderDialog(
    folder: SimpleFolder,
    onDismiss: () -> Unit,
    onUpdateFolder: (SimpleFolder) -> Unit
) {
    var folderName by remember { mutableStateOf(folder.name) }
    var selectedIconName by remember { mutableStateOf(folder.iconName) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.edit_folder)) },
        text = {
            Column {
                OutlinedTextField(
                    value = folderName,
                    onValueChange = { folderName = it },
                    label = { Text("Folder Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Select Icon")

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FolderIconOption(
                        iconResId = R.drawable.ic_quotes,
                        iconName = "ic_quotes",
                        isSelected = selectedIconName == "ic_quotes",
                        onClick = { selectedIconName = "ic_quotes" }
                    )

                    FolderIconOption(
                        iconResId = R.drawable.ic_tricks,
                        iconName = "ic_tricks",
                        isSelected = selectedIconName == "ic_tricks",
                        onClick = { selectedIconName = "ic_tricks" }
                    )

                    FolderIconOption(
                        iconResId = R.drawable.ic_images,
                        iconName = "ic_images",
                        isSelected = selectedIconName == "ic_images",
                        onClick = { selectedIconName = "ic_images" }
                    )

                    FolderIconOption(
                        iconResId = R.drawable.ic_posts,
                        iconName = "ic_posts",
                        isSelected = selectedIconName == "ic_posts",
                        onClick = { selectedIconName = "ic_posts" }
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (folderName.isNotBlank()) {
                        onUpdateFolder(folder.copy(name = folderName, iconName = selectedIconName))
                    }
                },
                enabled = folderName.isNotBlank()
            ) {
                Text(text = "Update")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}

@Composable
fun FolderIconOption(
    iconResId: Int,
    iconName: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surfaceVariant
            )
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = iconName,
            tint = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
    }
}
