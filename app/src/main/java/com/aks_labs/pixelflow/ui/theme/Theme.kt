package com.aks_labs.pixelflow.ui.theme

import android.app.Activity
import android.os.Build
import android.view.WindowManager
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aks_labs.pixelflow.data.SharedPrefsManager.ThemeMode
import com.aks_labs.pixelflow.ui.viewmodels.MainViewModel

@Composable
fun PixelFlowTheme(
    // These parameters are optional and will be overridden by the viewModel's theme mode if provided
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    viewModel: MainViewModel = viewModel(),
    content: @Composable () -> Unit
) {
    // Get the theme mode from the viewModel
    val themeMode by viewModel.themeMode.collectAsState()

    // Determine if dark theme should be used based on the theme mode
    val useDarkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (useDarkTheme) {
                // For Dark Mode, we want to support AMOLED black
                // We'll take the dynamic dark scheme but force the background to black
                dynamicDarkColorScheme(context).copy(
                    background = Color.Black,
                    surface = Color.Black,
                    // surfaceContainer not available in M3 1.1.2
                    onBackground = Color.White,
                    onSurface = Color.White
                )
            } else {
                dynamicLightColorScheme(context)
            }
        }
        useDarkTheme -> darkColorScheme(
             background = Color.Black,
             surface = Color.Black,
             onBackground = Color.White,
             onSurface = Color.White
        ) // Fallback if dynamic not available
        else -> lightColorScheme()
    }

    // Set up transparent status bar
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()

            // Make status bar icons dark or light based on theme
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !useDarkTheme

            // Make the status bar transparent and extend content behind it
            WindowCompat.setDecorFitsSystemWindows(window, false)

            // Ensure the navigation bar is also transparent but visible
            window.navigationBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !useDarkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Assuming Typography is defined in Type.kt
        content = content
    )
}
