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
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.aks_labs.pixelflow.R
import com.aks_labs.pixelflow.ui.theme.DarkCardBackground
import com.aks_labs.pixelflow.ui.theme.SuccessGreen
import com.aks_labs.pixelflow.ui.theme.ErrorRed
import com.aks_labs.pixelflow.ui.theme.WarningAmber
import com.aks_labs.pixelflow.ui.theme.InfoBlue
import com.aks_labs.pixelflow.ui.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    // State for service status
    var isServiceRunning by remember { mutableStateOf(ViewBasedFloatingBubbleService.isRunning()) }
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Update service status periodically
    LaunchedEffect(Unit) {
        isServiceRunning = ViewBasedFloatingBubbleService.isRunning()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                // Use default colors
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = stringResource(id = R.string.settings)
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
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Status Section
            StatusSection(isServiceRunning = isServiceRunning)

            // Permission Status Card
            PermissionStatusCard()

            // Features Section
            FeaturesSection(navController)

            // Service Controls
            ServiceControlsSection(
                isServiceRunning = isServiceRunning,
                onStartService = {
                    val intent = Intent(context, ViewBasedFloatingBubbleService::class.java)
                    intent.action = ViewBasedFloatingBubbleService.ACTION_START_FROM_APP
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(intent)
                    } else {
                        context.startService(intent)
                    }
                    Toast.makeText(context, "Service started", Toast.LENGTH_SHORT).show()
                    isServiceRunning = true
                },
                onStopService = {
                    val intent = Intent(context, ViewBasedFloatingBubbleService::class.java)
                    intent.action = "STOP_SERVICE"
                    context.startService(intent)
                    context.stopService(intent)
                    Toast.makeText(context, "Service stopped", Toast.LENGTH_SHORT).show()
                    isServiceRunning = false
                },
                onTestService = {
                    val intent = Intent(context, ViewBasedFloatingBubbleService::class.java)
                    intent.action = "MANUAL_TEST"
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(intent)
                    } else {
                        context.startService(intent)
                    }
                    Toast.makeText(context, "Testing with recent image...", Toast.LENGTH_SHORT).show()
                }
            )

            // Storage Usage Section
            StorageUsageSection()

            // Advanced Features Section
            AdvancedFeaturesSection(navController)

            // Bottom spacing
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// Status Section - Shows app name, logo, and service status
@Composable
fun StatusSection(isServiceRunning: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App Logo
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            // Animated logo
            val infiniteTransition = rememberInfiniteTransition(label = "logo_animation")
            val scale by infiniteTransition.animateFloat(
                initialValue = 0.9f,
                targetValue = 1.1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "scale_animation"
            )

            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "PixelFlow Logo",
                tint = Color.White,
                modifier = Modifier
                    .size(48.dp)
                    .scale(scale)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // App Name
        Text(
            text = "PixelFlow",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        // App Description
        Text(
            text = "Capture and organize your screenshots with ease",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Service Status
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(if (isServiceRunning) SuccessGreen.copy(alpha = 0.2f) else ErrorRed.copy(alpha = 0.2f))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = if (isServiceRunning) Icons.Filled.Check else Icons.Filled.Warning,
                contentDescription = "Service Status",
                tint = if (isServiceRunning) SuccessGreen else ErrorRed
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = if (isServiceRunning) "Service Running" else "Service Stopped",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = if (isServiceRunning) SuccessGreen else ErrorRed
            )
        }
    }
}

// Permission Status Card - Shows required permissions
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
                updatePermissionStatus(context, setStoragePermission, setOverlayPermission, setManageStoragePermission)
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
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkCardBackground
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Permissions Info",
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Required Permissions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

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

            val allPermissionsGranted = hasStoragePermission && hasOverlayPermission &&
                    (Build.VERSION.SDK_INT < Build.VERSION_CODES.R || hasManageStoragePermission)

            Text(
                text = if (allPermissionsGranted) "All permissions granted" else "All permissions are required for the app to function properly",
                style = MaterialTheme.typography.bodyMedium,
                color = if (allPermissionsGranted) SuccessGreen else ErrorRed,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// Features Section - Shows main app features
@Composable
fun FeaturesSection(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Features",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FeatureItem(
                icon = Icons.Filled.Settings,
                title = "Manage Folders",
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate("folders") }
            )

            FeatureItem(
                icon = Icons.Filled.Info,
                title = "Screenshot History",
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate("history") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FeatureItem(
                icon = Icons.Filled.Search,
                title = "Google Lens",
                modifier = Modifier.weight(1f),
                onClick = { /* Show info about double-tap feature */ }
            )

            FeatureItem(
                icon = Icons.Filled.Settings,
                title = "Settings",
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate("settings") }
            )
        }
    }
}

// Service Controls Section - Start/Stop service
@Composable
fun ServiceControlsSection(
    isServiceRunning: Boolean,
    onStartService: () -> Unit,
    onStopService: () -> Unit,
    onTestService: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkCardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = if (isServiceRunning) Icons.Filled.PlayArrow else Icons.Filled.Close,
                    contentDescription = "Service Controls",
                    tint = if (isServiceRunning) SuccessGreen else MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Service Controls",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Screenshot Detection",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )

                Switch(
                    checked = isServiceRunning,
                    onCheckedChange = { if (it) onStartService() else onStopService() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = SuccessGreen,
                        checkedTrackColor = SuccessGreen.copy(alpha = 0.5f),
                        uncheckedThumbColor = MaterialTheme.colorScheme.onSurface,
                        uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onTestService,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Test Service"
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("Test with Recent Image")
            }
        }
    }
}

// Storage Usage Section
@Composable
fun StorageUsageSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkCardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Storage Usage",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Simulated storage usage
            val usedStorage = 8.09f // MB
            val totalStorage = 64f // GB
            val usagePercentage = (usedStorage / 1024f) / totalStorage

            Text(
                text = "$usedStorage MB used of $totalStorage GB",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LinearProgressIndicator(
                progress = usagePercentage,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )
        }
    }
}

// Advanced Features Section
@Composable
fun AdvancedFeaturesSection(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Advanced Features",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Test activity button
        val context = LocalContext.current
        val testActivityClass = remember {
            try {
                Class.forName("com.aks_labs.pixelflow.ui.test.ComposeServiceTestActivity")
            } catch (e: Exception) {
                null
            }
        }

        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    testActivityClass?.let { activityClass ->
                        val intent = Intent(context, activityClass)
                        context.startActivity(intent)
                    }
                },
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Test Compose Service",
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Test Compose Service",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Test the Compose-based floating bubble implementation",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

// Feature Item Component
@Composable
fun FeatureItem(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = DarkCardBackground
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

// Permission Item Component
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
                    color = if (isGranted) SuccessGreen else ErrorRed,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isGranted) Icons.Filled.Check else Icons.Filled.Close,
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
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
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
