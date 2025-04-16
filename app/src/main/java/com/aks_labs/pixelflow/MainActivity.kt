package com.aks_labs.pixelflow

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
// import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aks_labs.pixelflow.services.ViewBasedFloatingBubbleService
import com.aks_labs.pixelflow.ui.screens.FolderManagementScreen
import com.aks_labs.pixelflow.ui.screens.HomeScreen
import com.aks_labs.pixelflow.ui.screens.PermissionSetupScreen
import com.aks_labs.pixelflow.ui.screens.ScreenshotHistoryScreen
import com.aks_labs.pixelflow.ui.screens.SettingsScreen
import com.aks_labs.pixelflow.ui.theme.PixelFlowTheme
import com.aks_labs.pixelflow.ui.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        if (allGranted) {
            checkOverlayPermission()
        } else {
            Toast.makeText(
                this,
                getString(R.string.permission_rationale),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()

        setContent {
            val viewModel: MainViewModel = viewModel()
            val navController = rememberNavController()

            PixelFlowTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    PixelFlowApp(navController, viewModel)
                }
            }
        }
    }

    private fun checkAndRequestPermissions() {
        val permissions = mutableListOf<String>()

        // Storage permissions
        if (Build.VERSION.SDK_INT >= 33) { // TIRAMISU is API 33
            permissions.add(Manifest.permission.READ_MEDIA_IMAGES)
        } else if (Build.VERSION.SDK_INT >= 30) { // Android 11+ (R)
            // For Android 11+, we need to use the Storage Access Framework
            // or request MANAGE_EXTERNAL_STORAGE permission
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        } else {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        // Request permissions
        if (permissions.isNotEmpty()) {
            requestPermissionLauncher.launch(permissions.toTypedArray())
        } else {
            checkOverlayPermission()
        }
    }

    private fun checkOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(
                this,
                getString(R.string.overlay_permission_rationale),
                Toast.LENGTH_LONG
            ).show()

            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivity(intent)
        } else {
            // Check for MANAGE_EXTERNAL_STORAGE permission on Android 11+
            if (Build.VERSION.SDK_INT >= 30 && !Environment.isExternalStorageManager()) {
                Toast.makeText(
                    this,
                    "Please grant all files access permission to save screenshots in folders",
                    Toast.LENGTH_LONG
                ).show()

                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            } else {
                // All permissions granted, start the service
                startFloatingBubbleService()
            }
        }
    }

    fun startFloatingBubbleService() {
        try {
            Log.d(TAG, "Starting ViewBasedFloatingBubbleService")
            val intent = Intent(this, ViewBasedFloatingBubbleService::class.java)

            // Add a specific action to indicate this is a normal start
            intent.action = ViewBasedFloatingBubbleService.ACTION_START_FROM_APP

            // For Android 8.0 (Oreo) and above, we need to start as a foreground service
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.d(TAG, "Starting foreground service on Android O+")
                startForegroundService(intent)
            } else {
                Log.d(TAG, "Starting normal service on Android pre-O")
                startService(intent)
            }

            Log.d(TAG, "Service start requested successfully")
            Toast.makeText(this, "Screenshot detection service started", Toast.LENGTH_SHORT).show()

            // Schedule a check to verify the service is running
            Handler(Looper.getMainLooper()).postDelayed({
                if (!ViewBasedFloatingBubbleService.isRunning()) {
                    Log.w(TAG, "Service not running after start request, trying again")
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(intent)
                    } else {
                        startService(intent)
                    }
                }
            }, 5000) // Check after 5 seconds
        } catch (e: Exception) {
            Log.e(TAG, "Error starting ViewBasedFloatingBubbleService", e)
            Toast.makeText(this, "Error starting service: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Don't stop the service when the activity is destroyed
        // The service will continue running in the background
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PixelFlowApp(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val isBubbleEnabled by viewModel.isBubbleEnabled.collectAsState(initial = true)
    var showPreview by remember { mutableStateOf(false) }
    var previewPath by remember { mutableStateOf("") }

    // Check if onboarding has been completed
    val context = LocalContext.current
    val activity = context as MainActivity
    val sharedPrefsManager = remember { (context.applicationContext as PixelFlowApplication).sharedPrefsManager }
    val onboardingCompleted = remember { sharedPrefsManager.isOnboardingCompleted() }

    // Start service if permissions are granted and onboarding is completed
    LaunchedEffect(onboardingCompleted) {
        if (onboardingCompleted) {
            // Check if all permissions are granted before starting service
            val hasStoragePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) ==
                        android.content.pm.PackageManager.PERMISSION_GRANTED
            } else {
                context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        android.content.pm.PackageManager.PERMISSION_GRANTED
            }

            val hasOverlayPermission = Settings.canDrawOverlays(context)

            val hasManageFilesPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Environment.isExternalStorageManager()
            } else {
                true // Not needed for Android < 11
            }

            if (hasStoragePermission && hasOverlayPermission &&
                (hasManageFilesPermission || Build.VERSION.SDK_INT < Build.VERSION_CODES.R)) {
                activity.startFloatingBubbleService()
            }
        }
    }

    // Set the start destination based on onboarding status
    val startDestination = if (onboardingCompleted) "home" else "permission_setup"

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("permission_setup") {
                PermissionSetupScreen(navController, sharedPrefsManager)
            }
            composable("home") {
                HomeScreen(navController, viewModel)
            }
            composable("settings") {
                SettingsScreen(navController, viewModel, isBubbleEnabled)
            }
            composable("folders") {
                FolderManagementScreen(navController, viewModel)
            }
            composable("history") {
                ScreenshotHistoryScreen(navController, viewModel)
            }
        }
    }
}