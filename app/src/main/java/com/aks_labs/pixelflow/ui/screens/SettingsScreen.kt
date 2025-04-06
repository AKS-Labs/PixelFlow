package com.aks_labs.pixelflow.ui.screens

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aks_labs.pixelflow.R
import com.aks_labs.pixelflow.ui.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: MainViewModel,
    isBubbleEnabled: Boolean
) {
    val context = LocalContext.current
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.settings)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
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
                .padding(16.dp)
        ) {
            // Floating Bubble Setting
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.enable_floating_bubble),
                    style = MaterialTheme.typography.titleMedium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Switch(
                    checked = isBubbleEnabled,
                    onCheckedChange = { viewModel.setBubbleEnabled(it) },
                    modifier = Modifier.align(Alignment.Start)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Overlay Permission Button (if needed)
            if (!viewModel.hasOverlayPermission(context)) {
                Button(
                    onClick = {
                        context.startActivity(viewModel.getOverlayPermissionIntent(context))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Grant Overlay Permission")
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Folder Management Button
            Button(
                onClick = { navController.navigate("folders") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.manage_folders))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Screenshot History Button
            Button(
                onClick = { navController.navigate("history") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.screenshot_history))
            }
        }
    }
}
