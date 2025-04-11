package com.aks_labs.pixelflow.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

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
    // Always use dark theme for this app, as shown in the reference images
    darkTheme: Boolean = true,
    // Dynamic color is available on Android 12+, but we'll disable it to match the reference design
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}