package com.akslabs.pixelscreenshots.ui.theme

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
import com.akslabs.pixelscreenshots.data.SharedPrefsManager.ThemeMode
import com.akslabs.pixelscreenshots.ui.viewmodels.MainViewModel

@Composable
fun PixelScreenshotsTheme(
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
        ThemeMode.AMOLED -> true
        ThemeMode.LIGHT -> false
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            val dynamicScheme = if (useDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            
            if (themeMode == ThemeMode.AMOLED) {
                dynamicScheme.copy(
                    background = Color.Black,
                    surface = Color.Black,
                    onBackground = Color.White,
                    onSurface = Color.White
                )
            } else if (useDarkTheme && themeMode == ThemeMode.DARK) {
                // Polished dark mode - use dynamic colors as they are, or slightly adjust background if desired.
                // By default dynamicDarkColorScheme is not amoled black.
                dynamicScheme
            } else {
                dynamicScheme
            }
        }
        themeMode == ThemeMode.AMOLED -> darkColorScheme(
             background = Color.Black,
             surface = Color.Black,
             onBackground = Color.White,
             onSurface = Color.White
        )
        useDarkTheme -> darkColorScheme(
             background = PolishedDarkBackground,
             surface = DarkSurface,
             onBackground = Color.White,
             onSurface = Color.White
        )
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
