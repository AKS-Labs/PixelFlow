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

private val DarkColorScheme = darkColorScheme(
    primary = DarkAccent,
    secondary = DarkSecondary,
    tertiary = InfoBlue,
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkCardBackground,
    error = ErrorRed,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = Color.White.copy(alpha = 0.8f),
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = LightAccent,
    secondary = LightSecondary,
    tertiary = InfoBlue,
    background = LightBackground,
    surface = LightSurface,
    surfaceVariant = LightCardBackground,
    error = ErrorRed,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onSurfaceVariant = Color.Black.copy(alpha = 0.8f),
    onError = Color.White
)

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
            if (useDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        useDarkTheme -> DarkColorScheme
        else -> LightColorScheme
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
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
