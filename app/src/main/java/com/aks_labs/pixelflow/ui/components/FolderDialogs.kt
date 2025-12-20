package com.akslabs.pixelscreenshots.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.akslabs.pixelscreenshots.R
import com.akslabs.pixelscreenshots.data.models.SimpleFolder
import com.akslabs.pixelscreenshots.ui.screens.getIconResId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFolderDialog(
    onDismiss: () -> Unit,
    onAddFolder: (name: String, iconName: String) -> Unit
) {
    var folderName by remember { mutableStateOf("") }
    // Default icon
    val selectedIconName = "ic_images"

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.add_folder), fontWeight = FontWeight.Bold) },
        text = {
            Column {
                OutlinedTextField(
                    value = folderName,
                    onValueChange = { folderName = it },
                    label = { Text("Folder Name") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { if (folderName.isNotBlank()) onAddFolder(folderName, selectedIconName) },
                enabled = folderName.isNotBlank()
            ) {
                Text(text = "Create")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors()
            ) {
                Text(text = "Cancel")
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(28.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFolderDialog(
    folder: SimpleFolder,
    onDismiss: () -> Unit,
    onUpdateFolder: (SimpleFolder) -> Unit,
    onDelete: () -> Unit
) {
    var folderName by remember { mutableStateOf(folder.name) }
    // Keep existing icon or default
    val selectedIconName = folder.iconName

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.edit_folder), fontWeight = FontWeight.Bold) },
        text = {
            Column {
                OutlinedTextField(
                    value = folderName,
                    onValueChange = { folderName = it },
                    label = { Text("Folder Name") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = onDelete,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Delete Folder")
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
                Text(text = "Save")
            }
        },
        dismissButton = {
            Button(
                 onClick = onDismiss,
                 colors = ButtonDefaults.textButtonColors()
            ) {
                Text(text = "Cancel")
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(28.dp)
    )
}
