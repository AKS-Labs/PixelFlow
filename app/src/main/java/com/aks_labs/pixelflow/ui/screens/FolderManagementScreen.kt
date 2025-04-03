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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aks_labs.pixelflow.R
import com.aks_labs.pixelflow.data.models.SimpleFolder
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.manage_folders)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_folder)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(folders) { folder ->
                    FolderItem(
                        folder = folder,
                        onEdit = { editingFolder = folder },
                        onDelete = {
                            if (!folder.isDefault) {
                                viewModel.deleteFolder(folder)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
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
    }
}

@Composable
fun FolderItem(
    folder: SimpleFolder,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Folder icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
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

            Spacer(modifier = Modifier.width(16.dp))

            // Folder name
            Text(
                text = folder.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )

            // Edit button
            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(id = R.string.edit_folder)
                )
            }

            // Delete button (disabled for default folders)
            IconButton(
                onClick = onDelete,
                enabled = !folder.isDefault
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete_folder),
                    tint = if (folder.isDefault) Color.Gray else MaterialTheme.colorScheme.error
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
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !folder.isDefault
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
                        onClick = { selectedIconName = "ic_quotes" },
                        enabled = !folder.isDefault
                    )

                    FolderIconOption(
                        iconResId = R.drawable.ic_tricks,
                        iconName = "ic_tricks",
                        isSelected = selectedIconName == "ic_tricks",
                        onClick = { selectedIconName = "ic_tricks" },
                        enabled = !folder.isDefault
                    )

                    FolderIconOption(
                        iconResId = R.drawable.ic_images,
                        iconName = "ic_images",
                        isSelected = selectedIconName == "ic_images",
                        onClick = { selectedIconName = "ic_images" },
                        enabled = !folder.isDefault
                    )

                    FolderIconOption(
                        iconResId = R.drawable.ic_posts,
                        iconName = "ic_posts",
                        isSelected = selectedIconName == "ic_posts",
                        onClick = { selectedIconName = "ic_posts" },
                        enabled = !folder.isDefault
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
                enabled = folderName.isNotBlank() && !folder.isDefault
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
