package com.akslabs.pixelscreenshots.ui.screens

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akslabs.pixelscreenshots.ui.components.FeaturesAnimation
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.akslabs.pixelscreenshots.R
import com.akslabs.pixelscreenshots.data.SharedPrefsManager
import com.akslabs.pixelscreenshots.ui.components.ManageFilesAnimation
import com.akslabs.pixelscreenshots.ui.components.MediaAccessAnimation
import com.akslabs.pixelscreenshots.ui.components.OrganizeScreenshotAnimation
import com.akslabs.pixelscreenshots.ui.viewmodels.MainViewModel

/**
 * Extension function to find the Activity from a Context
 */
fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}

enum class PermissionSetupStep {
    STORAGE_PERMISSION,
    OVERLAY_PERMISSION,
    MANAGE_FILES_PERMISSION,
    PERMISSION_SUMMARY
}

@Composable
fun PermissionSetupScreen(
    navController: NavController,
    sharedPrefsManager: SharedPrefsManager
) {
    var currentStep by remember { mutableStateOf(PermissionSetupStep.STORAGE_PERMISSION) }
    val context = LocalContext.current

    // Track permission states
    var hasStoragePermission by remember { mutableStateOf(false) }
    var hasOverlayPermission by remember { mutableStateOf(false) }
    var hasManageFilesPermission by remember { mutableStateOf(false) }

    // Function to check all permission states
    fun checkAllPermissions() {
        // Check storage permission
        hasStoragePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) ==
                    android.content.pm.PackageManager.PERMISSION_GRANTED
        } else {
            context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    android.content.pm.PackageManager.PERMISSION_GRANTED
        }

        // Check overlay permission
        hasOverlayPermission = Settings.canDrawOverlays(context)

        // Check manage files permission
        hasManageFilesPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            true // Not needed for Android < 11
        }

        // Automatically advance to next screen if permission was granted
        when (currentStep) {
            PermissionSetupStep.STORAGE_PERMISSION -> {
                if (hasStoragePermission) {
                    currentStep = PermissionSetupStep.OVERLAY_PERMISSION
                }
            }
            PermissionSetupStep.OVERLAY_PERMISSION -> {
                if (hasOverlayPermission) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        currentStep = PermissionSetupStep.MANAGE_FILES_PERMISSION
                    } else {
                        currentStep = PermissionSetupStep.PERMISSION_SUMMARY
                    }
                }
            }
            PermissionSetupStep.MANAGE_FILES_PERMISSION -> {
                if (hasManageFilesPermission) {
                    currentStep = PermissionSetupStep.PERMISSION_SUMMARY
                }
            }
            else -> { /* No action needed for summary screen */ }
        }
    }

    // Initial check of all permissions
    LaunchedEffect(Unit) {
        checkAllPermissions()
    }

    // Check permissions periodically to detect changes
    LaunchedEffect(Unit) {
        // Check permissions every 500ms to detect when they change
        while (true) {
            kotlinx.coroutines.delay(500)
            checkAllPermissions()
        }
    }

    // Storage permission launcher
    val storagePermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasStoragePermission = isGranted
        if (isGranted) {
            // Move to next step only when permission is granted
            currentStep = PermissionSetupStep.OVERLAY_PERMISSION
        }
    }

    // Activity result launcher for overlay permission
    val overlayPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        // Check if permission was granted
        val hasPermission = Settings.canDrawOverlays(context)
        hasOverlayPermission = hasPermission
        if (hasPermission) {
            // Move to next step only when permission is granted
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                currentStep = PermissionSetupStep.MANAGE_FILES_PERMISSION
            } else {
                currentStep = PermissionSetupStep.PERMISSION_SUMMARY
            }
        }
    }

    // Activity result launcher for manage files permission
    val manageFilesPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        // Check if permission was granted
        val hasPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            true
        }
        hasManageFilesPermission = hasPermission
        if (hasPermission) {
            // Move to summary screen only when permission is granted
            currentStep = PermissionSetupStep.PERMISSION_SUMMARY
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (currentStep) {
            PermissionSetupStep.STORAGE_PERMISSION -> {
                PermissionScreen(
                    title = "PixelScreenshots needs access to photos",
                    description = "To detect and organize screenshots, we need permission to access your media.",
                    buttonText = "Grant Permission",
                    nativeAnimationType = NativeAnimationType.MEDIA_ACCESS,
                    onButtonClick = {
                        // Use MainActivity's method to request storage permissions
                        // This ensures permissions are only requested when the user clicks the button
                        val activity = context.findActivity()
                        if (activity is com.akslabs.pixelscreenshots.MainActivity) {
                            activity.requestStoragePermissions()
                            // Permission state will be checked in the lifecycle observer when activity resumes
                        } else {
                            // Fallback to direct permission request if activity reference is not available
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                storagePermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                            } else {
                                storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }
                        }
                    }
                )
            }

            PermissionSetupStep.OVERLAY_PERMISSION -> {
                PermissionScreen(
                    title = "PixelScreenshots needs to display over other apps",
                    description = "To show floating bubbles for screenshots, we need permission to draw over other apps.",
                    buttonText = "Grant Permission",
                    nativeAnimationType = NativeAnimationType.ORGANIZE_SCREENSHOT,
                    onButtonClick = {
                        // Use MainActivity's method to request overlay permission
                        val activity = context.findActivity()
                        if (activity is com.akslabs.pixelscreenshots.MainActivity) {
                            activity.requestOverlayPermission()
                            // Permission state will be checked in the lifecycle observer when activity resumes
                        } else {
                            // Fallback to direct permission request
                            val intent = Intent(
                                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:${context.packageName}")
                            )
                            overlayPermissionLauncher.launch(intent)
                        }
                    }
                )
            }

            PermissionSetupStep.MANAGE_FILES_PERMISSION -> {
                PermissionScreen(
                    title = "PixelScreenshots needs to manage files",
                    description = "To organize screenshots into folders, we need permission to manage all files.",
                    buttonText = "Grant Permission",
                    nativeAnimationType = NativeAnimationType.MANAGE_FILES,
                    onButtonClick = {
                        // Use MainActivity's method to request manage files permission
                        val activity = context.findActivity()
                        if (activity is com.akslabs.pixelscreenshots.MainActivity) {
                            activity.requestManageExternalStoragePermission()
                            // Permission state will be checked in the lifecycle observer when activity resumes
                        } else {
                            // Fallback to direct permission request
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                                intent.data = Uri.parse("package:${context.packageName}")
                                manageFilesPermissionLauncher.launch(intent)
                            }
                        }
                    }
                )
            }

            PermissionSetupStep.PERMISSION_SUMMARY -> {
                PermissionSummaryScreen(
                    hasStoragePermission = hasStoragePermission,
                    hasOverlayPermission = hasOverlayPermission,
                    hasManageFilesPermission = hasManageFilesPermission,
                    onContinueClick = {
                        // Check if all required permissions are granted
                        if (hasStoragePermission && hasOverlayPermission &&
                            (hasManageFilesPermission || Build.VERSION.SDK_INT < Build.VERSION_CODES.R)) {
                            // All permissions granted, mark onboarding as completed and navigate to home
                            sharedPrefsManager.setOnboardingCompleted(true)

                            // Use MainActivity's method to check permissions and start service
                            val activity = context.findActivity()
                            if (activity is com.akslabs.pixelscreenshots.MainActivity) {
                                activity.checkPermissionsAndStartService()
                            }

                            navController.navigate("home") {
                                popUpTo("permission_setup") { inclusive = true }
                            }
                        } else {
                            // Not all permissions granted, go back to the first missing permission
                            if (!hasStoragePermission) {
                                currentStep = PermissionSetupStep.STORAGE_PERMISSION
                            } else if (!hasOverlayPermission) {
                                currentStep = PermissionSetupStep.OVERLAY_PERMISSION
                            } else if (!hasManageFilesPermission && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                currentStep = PermissionSetupStep.MANAGE_FILES_PERMISSION
                            }
                        }
                    }
                )
            }
        }
    }
}

enum class NativeAnimationType {
    NONE, ORGANIZE_SCREENSHOT, MEDIA_ACCESS, MANAGE_FILES
}

@Composable
fun PermissionScreen(
    title: String,
    description: String,
    buttonText: String,
    illustrationRes: Int? = null,
    nativeAnimationType: NativeAnimationType = NativeAnimationType.NONE,
    onButtonClick: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    val currentStep = when(title) {
        "PixelScreenshots needs access to photos" -> 0
        "PixelScreenshots needs to display over other apps" -> 1
        "PixelScreenshots needs to manage files" -> 2
        else -> 0
    }
    val totalSteps = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) 3 else 2

    LaunchedEffect(title) {
        visible = false
        // Small delay before fading in
        kotlinx.coroutines.delay(100)
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(48.dp))

                // App logo
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "PixelScreenshots Logo",
                    modifier = Modifier.size(78.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Title
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 36.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Description
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Illustration area (exactly below text)
                Box(
                    modifier = Modifier.size(240.dp),
                    contentAlignment = Alignment.Center
                ) {
                    when (nativeAnimationType) {
                        NativeAnimationType.ORGANIZE_SCREENSHOT -> {
                            OrganizeScreenshotAnimation(modifier = Modifier.size(240.dp))
                        }
                        NativeAnimationType.MEDIA_ACCESS -> {
                            MediaAccessAnimation(modifier = Modifier.size(240.dp))
                        }
                        NativeAnimationType.MANAGE_FILES -> {
                            ManageFilesAnimation(modifier = Modifier.size(240.dp))
                        }
                        else -> {
                            if (illustrationRes != null) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(illustrationRes)
                                        .decoderFactory(SvgDecoder.Factory())
                                        .build(),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                // Fallback logo
                                Image(
                                    painter = painterResource(id = R.drawable.logo),
                                    contentDescription = null,
                                    modifier = Modifier.size(120.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Page indicators
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    for (i in 0 until totalSteps) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(width = if (i == currentStep) 24.dp else 8.dp, height = 8.dp)
                                .background(
                                    color = if (i == currentStep) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )
                    }
                }
            }

            // Grant button in bottom right corner
            TextButton(
                onClick = onButtonClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Grant",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun PermissionSummaryScreen(
    hasStoragePermission: Boolean,
    hasOverlayPermission: Boolean,
    hasManageFilesPermission: Boolean,
    onContinueClick: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Check if all permissions are granted
    val allPermissionsGranted = hasStoragePermission && hasOverlayPermission &&
            (hasManageFilesPermission || Build.VERSION.SDK_INT < Build.VERSION_CODES.R)

    LaunchedEffect(Unit) {
        visible = false
        // Small delay before fading in
        kotlinx.coroutines.delay(100)
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (allPermissionsGranted) {
                // High-fidelity Onboarding Summary
                OnboardingSummaryScreen(onStartClick = onContinueClick)
            } else {
                // Fallback Permission Status List
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(48.dp))

                    // App logo
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "PixelFlow Logo",
                        modifier = Modifier.size(88.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Title
                    Text(
                        text = "Required Permissions",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 36.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Description
                    Text(
                        text = "Please grant all required permissions to use PixelFlow",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Permission cards
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Storage permission
                            PermissionStatusItem(
                                title = "Storage Access",
                                description = "Required to detect and manage screenshots",
                                isGranted = hasStoragePermission,
                                onClick = if (!hasStoragePermission) {
                                    { onContinueClick() }
                                } else null
                            )

                            // Overlay permission
                            PermissionStatusItem(
                                title = "Draw Over Apps",
                                description = "Required to show floating bubble",
                                isGranted = hasOverlayPermission,
                                onClick = if (!hasOverlayPermission) {
                                    { onContinueClick() }
                                } else null
                            )

                            // Manage files permission (Android 11+)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                PermissionStatusItem(
                                    title = "Manage All Files",
                                    description = "Required to organize screenshots in folders",
                                    isGranted = hasManageFilesPermission,
                                    onClick = if (!hasManageFilesPermission) {
                                        { onContinueClick() }
                                    } else null
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Page indicators
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val totalSteps = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) 4 else 3
                        for (i in 0 until totalSteps) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(width = if (i == totalSteps - 1) 24.dp else 8.dp, height = 8.dp)
                                    .background(
                                        color = if (i == totalSteps - 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                            )
                        }
                    }

                    // Continue button
                    Box(modifier = Modifier.fillMaxWidth()) {
                        TextButton(
                            onClick = onContinueClick,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(bottom = 16.dp),
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = if (allPermissionsGranted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                            ),
                            enabled = allPermissionsGranted
                        ) {
                            Text(
                                text = "Next",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun OnboardingSummaryScreen(onStartClick: () -> Unit) {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        // App logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "PixelFlow Logo",
            modifier = Modifier.size(64.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Search your screenshots with on-device OCR",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 36.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "PixelFlow is the new home for all your screenshots. With on-device 100% offline OCR processing, you can search screenshots by text inside them and copy text too — no internet required.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Feature Animation
        FeaturesAnimation(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
        )
        
        Spacer(modifier = Modifier.height(40.dp))
        
        // "How it works" Section
        FeatureSectionHeader(title = "How it works")
        
        FeatureBulletItem(
            icon = Icons.Default.Star,
            title = "Floating Bubble Innovation",
            description = "Shows screenshots as a floating bubble for instant drag and drop into folders. No more notification clutter."
        )
        
        FeatureBulletItem(
            icon = Icons.Default.Search,
            title = "Powerful Interactions",
            description = "Double tap the floating bubble to search the image with Google Lens, copy OCR text, and other smart features."
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // "Privacy" Section
        FeatureSectionHeader(title = "Privacy & Data")
        
        FeatureBulletItem(
            icon = Icons.Default.Lock,
            title = "Privacy Perfection",
            description = "Your data never leaves your device. No AI bullshit with your privacy – just 100% offline security. Enjoy."
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Footer links
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Privacy Policy",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { /* Handle privacy click */ }
            )
            Text(
                text = " • ",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
            Text(
                text = "Terms of Service",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { /* Handle terms click */ }
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Start Button
        Button(
            onClick = onStartClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Start Using App",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
            }
        }
        
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
fun FeatureSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun PermissionStatusItem(
    title: String,
    description: String,
    isGranted: Boolean,
    onClick: (() -> Unit)?
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
                    color = if (isGranted)
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    else
                        MaterialTheme.colorScheme.error.copy(alpha = 0.8f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isGranted) Icons.Default.Check else Icons.Default.Close,
                contentDescription = if (isGranted) "Granted" else "Not Granted",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
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

        if (onClick != null) {
            Button(
                onClick = onClick,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Grant")
            }
        }
    }
}

@Composable
fun FeatureBulletItem(icon: ImageVector, title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                lineHeight = 20.sp
            )
        }
    }
}
