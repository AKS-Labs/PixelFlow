package com.aks_labs.pixelflow

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aks_labs.pixelflow.services.FloatingBubbleService
import com.aks_labs.pixelflow.ui.screens.FolderManagementScreen
import com.aks_labs.pixelflow.ui.screens.HomeScreen
import com.aks_labs.pixelflow.ui.screens.ScreenshotHistoryScreen
import com.aks_labs.pixelflow.ui.screens.SettingsScreen
import com.aks_labs.pixelflow.ui.theme.PixelFlowTheme
import com.aks_labs.pixelflow.ui.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {

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

        // Check and request permissions
        checkAndRequestPermissions()

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
            startFloatingBubbleService()
        }
    }

    private fun startFloatingBubbleService() {
        val intent = Intent(this, FloatingBubbleService::class.java)
        startService(intent)
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

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
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