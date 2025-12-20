package com.akslabs.pixelscreenshots.ui.screens

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
//import androidx.compose.material.icons.filled.Palette
//import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.Edit
import com.akslabs.pixelscreenshots.R
import com.akslabs.pixelscreenshots.ui.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: MainViewModel,
    isBubbleEnabled: Boolean
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.settings),
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
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                SettingsSectionHeader(title = "General")
            }

            item {
                SettingsSwitchItem(
                    title = stringResource(id = R.string.enable_floating_bubble),
                    subtitle = "Show a floating bubble after taking a screenshot",
                    icon = Icons.Outlined.CheckCircle,
                    checked = isBubbleEnabled,
                    onCheckedChange = { viewModel.setBubbleEnabled(it) }
                )
            }

            item {
                SettingsSwitchItem(
                    title = "Dynamic Colors",
                    subtitle = "Use system colors for drag zones (Android 12+)",
                    icon = androidx.compose.material.icons.Icons.Default.Edit,
                    checked = viewModel.isDynamicColorsEnabled.collectAsState(initial = false).value,
                    onCheckedChange = { viewModel.setDynamicColorsEnabled(it) }
                )
            }

            item {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }

            item {
                SettingsSectionHeader(title = "Organization")
            }

            item {
                SettingsNavigationItem(
                    title = stringResource(id = R.string.manage_folders),
                    subtitle = "Create and edit your collections",
                    icon = Icons.Default.ArrowBack,
                    onClick = { navController.navigate("folders") }
                )
            }

            item {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }

            item {
                SettingsSectionHeader(title = "Permissions")
            }
            
            item {
                val hasOverlay = viewModel.hasOverlayPermission(context)
                SettingsNavigationItem(
                    title = "Overlay Permission",
                    subtitle = if (hasOverlay) "Granted" else "Tap to grant permission",
                    icon = Icons.Default.ArrowBack, // Fallback for Layers
                    onClick = {
                         if (!hasOverlay) {
                             context.startActivity(viewModel.getOverlayPermissionIntent(context))
                         }
                    },
                    trailingContent = {
                         if (hasOverlay) {
                             Text(
                                 text = "On",
                                 style = MaterialTheme.typography.bodyMedium,
                                 color = MaterialTheme.colorScheme.primary
                             )
                         }
                    }
                )
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                 item {
                     SettingsNavigationItem(
                         title = "Notification Permission",
                         subtitle = "Required for background analysis",
                         icon = Icons.Default.ArrowBack,
                         onClick = {
                             val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                                 putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                             }
                             context.startActivity(intent)
                         }
                     )
                 }
            }
        }
    }
}

@Composable
fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
    )
}

@Composable
fun SettingsSwitchItem(
    title: String,
    subtitle: String? = null,
    icon: ImageVector? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    ListItem(
        headlineContent = { Text(text = title, fontWeight = FontWeight.Medium) },
        supportingContent = subtitle?.let { { Text(text = it) } },
        leadingContent = icon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        trailingContent = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                    checkedTrackColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.clickable { onCheckedChange(!checked) }
    )
}

@Composable
fun SettingsNavigationItem(
    title: String,
    subtitle: String? = null,
    icon: ImageVector? = null,
    onClick: () -> Unit,
    trailingContent: @Composable (() -> Unit)? = null
) {
    ListItem(
        headlineContent = { Text(text = title, fontWeight = FontWeight.Medium) },
        supportingContent = subtitle?.let { { Text(text = it) } },
        leadingContent = icon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        trailingContent = trailingContent,
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.clickable(onClick = onClick)
    )
}
