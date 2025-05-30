package com.aks_labs.pixelflow.ui.screens

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.aks_labs.pixelflow.services.ViewBasedFloatingBubbleService
import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.aks_labs.pixelflow.R
import com.aks_labs.pixelflow.data.SharedPrefsManager.ThemeMode
import com.aks_labs.pixelflow.ui.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    // Get the current theme mode
    val currentThemeMode by viewModel.themeMode.collectAsState()

    // State for the dropdown menu
    var showThemeMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                actions = {
                    // Theme menu button
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
                        // System theme option
                        DropdownMenuItem(
                            text = { Text("System theme") },
                            leadingIcon = null,
                            onClick = {
                                viewModel.setThemeMode(ThemeMode.SYSTEM)
                                showThemeMenu = false
                            },
                            trailingIcon = if (currentThemeMode == ThemeMode.SYSTEM) {
                                { Icon(Icons.Default.Check, contentDescription = "Selected") }
                            } else null
                        )

                        // Light theme option
                        DropdownMenuItem(
                            text = { Text("Light theme") },
                            leadingIcon = null,
                            onClick = {
                                viewModel.setThemeMode(ThemeMode.LIGHT)
                                showThemeMenu = false
                            },
                            trailingIcon = if (currentThemeMode == ThemeMode.LIGHT) {
                                { Icon(Icons.Default.Check, contentDescription = "Selected") }
                            } else null
                        )

                        // Dark theme option
                        DropdownMenuItem(
                            text = { Text("Dark theme") },
                            leadingIcon = null,
                            onClick = {
                                viewModel.setThemeMode(ThemeMode.DARK)
                                showThemeMenu = false
                            },
                            trailingIcon = if (currentThemeMode == ThemeMode.DARK) {
                                { Icon(Icons.Default.Check, contentDescription = "Selected") }
                            } else null
                        )
                    }

                    // Settings button
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(id = R.string.settings)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        // Define the items for our LazyColumn
        val context = LocalContext.current
        val testActivityClass = remember {
            try {
                Class.forName("com.aks_labs.pixelflow.ui.test.ComposeServiceTestActivity")
            } catch (e: Exception) {
                null
            }
        }

        // Define our UI items
        data class HomeItem(val id: String, val content: @Composable () -> Unit)

        val items = listOf(
            HomeItem("header") {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "PixelFlow",
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Capture and organize your screenshots with ease",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            },
            HomeItem("permissions") {
                PermissionStatusCard()
            },
            HomeItem("service_controls") {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Service Controls",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Button(
                            onClick = {
                                // Start the service normally
                                val intent = Intent(context, ViewBasedFloatingBubbleService::class.java)
                                intent.action = ViewBasedFloatingBubbleService.ACTION_START_FROM_APP
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    context.startForegroundService(intent)
                                } else {
                                    context.startService(intent)
                                }
                                Toast.makeText(context, "Service started", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Start Screenshot Detection")
                        }

                        Button(
                            onClick = {
                                // Check if the service is running
                                val isRunning = ViewBasedFloatingBubbleService.isRunning()
                                Toast.makeText(
                                    context,
                                    "Service is ${if (isRunning) "running" else "not running"}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Check Service Status")
                        }

                        Button(
                            onClick = {
                                // Manually trigger the test mode
                                val intent = Intent(context, ViewBasedFloatingBubbleService::class.java)
                                intent.action = "MANUAL_TEST"
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    context.startForegroundService(intent)
                                } else {
                                    context.startService(intent)
                                }
                                Toast.makeText(context, "Testing with recent image...", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Test with Recent Image")
                        }

                        Button(
                            onClick = {
                                // Stop the service
                                val intent = Intent(context, ViewBasedFloatingBubbleService::class.java)
                                intent.action = "STOP_SERVICE"
                                // First send the stop intent to set the flag
                                context.startService(intent)
                                // Then actually stop the service
                                context.stopService(intent)
                                Toast.makeText(context, "Service stopped", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Stop Service")
                        }
                    }
                }
            },
            HomeItem("navigation_buttons") {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "App Navigation",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Button(
                            onClick = { navController.navigate("folders") },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = stringResource(id = R.string.manage_folders))
                        }

                        Button(
                            onClick = { navController.navigate("history") },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = stringResource(id = R.string.screenshot_history))
                        }

                        Button(
                            onClick = {
                                testActivityClass?.let { activityClass ->
                                    val intent = Intent(context, activityClass)
                                    context.startActivity(intent)
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Test Compose Service")
                        }

                        Button(
                            onClick = { navController.navigate("settings") },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = stringResource(id = R.string.settings))
                        }
                    }
                }
            }
        )

        // Create a LazyColumn with simple fade-in animation
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(items) { index, item ->
                // Simple fade-in animation without bounce
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(durationMillis = 300, delayMillis = index * 50)),
                    exit = fadeOut()
                ) {
                    item.content()
                }
            }
        }
    }
}

@Composable
fun PermissionStatusCard() {
    val context = LocalContext.current

    // Check permissions
    // Use mutableStateOf to allow updating the permission status
    val (hasStoragePermission, setStoragePermission) = remember { mutableStateOf(false) }
    val (hasOverlayPermission, setOverlayPermission) = remember { mutableStateOf(false) }
    val (hasManageStoragePermission, setManageStoragePermission) = remember { mutableStateOf(false) }

    // Effect to update permission status when the screen is shown
    LaunchedEffect(Unit) {
        updatePermissionStatus(context, setStoragePermission, setOverlayPermission, setManageStoragePermission)
    }

    // Effect to update permission status when the app comes back to foreground
    DisposableEffect(Unit) {
        val activity = context as? ComponentActivity
        val lifecycleObserver = object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                updatePermissionStatus(
                    context,
                    setStoragePermission,
                    setOverlayPermission,
                    setManageStoragePermission
                )
            }
        }

        activity?.lifecycle?.addObserver(lifecycleObserver)

        onDispose {
            activity?.lifecycle?.removeObserver(lifecycleObserver)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Required Permissions",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Storage permission
            PermissionItem(
                title = "Storage Access",
                description = "Required to detect and manage screenshots",
                isGranted = hasStoragePermission,
                onClick = {
                    // Can't request directly from here
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Overlay permission
            PermissionItem(
                title = "Draw Over Apps",
                description = "Required to show floating bubble",
                isGranted = hasOverlayPermission,
                onClick = {
                    val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                    intent.data = Uri.parse("package:${context.packageName}")
                    context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Manage storage permission (Android 11+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                PermissionItem(
                    title = "Manage All Files",
                    description = "Required to organize screenshots in folders",
                    isGranted = hasManageStoragePermission,
                    onClick = {
                        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                        intent.data = Uri.parse("package:${context.packageName}")
                        context.startActivity(intent)
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "All permissions are required for the app to function properly",
                style = MaterialTheme.typography.bodyMedium,
                color = if (!hasStoragePermission || !hasOverlayPermission ||
                        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !hasManageStoragePermission)) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                },
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun PermissionItem(
    title: String,
    description: String,
    isGranted: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Status indicator
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = if (isGranted) Color.Green else Color.Red,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isGranted) Icons.Default.Check else Icons.Default.Close,
                contentDescription = if (isGranted) "Granted" else "Not Granted",
                tint = Color.White
            )
        }

        Column(modifier = Modifier
            .weight(1f)
            .padding(horizontal = 16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }

        if (!isGranted) {
            Button(onClick = onClick) {
                Text("Grant")
            }
        }
    }
}



/**
 * Update permission status
 */
private fun updatePermissionStatus(
    context: Context,
    setStoragePermission: (Boolean) -> Unit,
    setOverlayPermission: (Boolean) -> Unit,
    setManageStoragePermission: (Boolean) -> Unit
) {
    // Check storage permission
    val hasStorage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_MEDIA_IMAGES
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
    } else {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
    }
    setStoragePermission(hasStorage)

    // Check overlay permission
    val hasOverlay = Settings.canDrawOverlays(context)
    setOverlayPermission(hasOverlay)

    // Check manage storage permission
    val hasManageStorage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        Environment.isExternalStorageManager()
    } else {
        true // Not needed for Android < 11
    }
    setManageStoragePermission(hasManageStorage)
}
