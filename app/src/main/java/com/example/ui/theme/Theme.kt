package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = Color.Black,
    primaryContainer = DeepNavySurfaceElevated,
    onPrimaryContainer = NeonCyan,
    secondary = NeonPurple,
    onSecondary = Color.White,
    secondaryContainer = DeepNavySurfaceElevated,
    onSecondaryContainer = Color(0xFFD6BBFF),
    tertiary = NeonEmerald,
    onTertiary = Color.Black,
    background = DeepNavyBackground,
    onBackground = TextPrimaryDark,
    surface = DeepNavySurface,
    onSurface = TextPrimaryDark,
    surfaceVariant = DeepNavySurfaceElevated,
    onSurfaceVariant = TextSecondaryDark,
    outline = DeepNavyBorder,
    error = StatusError,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = ElectricBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDCEBFF),
    onPrimaryContainer = Color(0xFF003882),
    secondary = NeonPurple,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFEBDCFF),
    onSecondaryContainer = Color(0xFF450F85),
    tertiary = Color(0xFF00A370),
    onTertiary = Color.White,
    background = LightBackground,
    onBackground = TextPrimaryLight,
    surface = LightSurface,
    onSurface = TextPrimaryLight,
    surfaceVariant = LightSurfaceElevated,
    onSurfaceVariant = TextSecondaryLight,
    outline = LightBorder,
    error = StatusError,
    onError = Color.White
)

@Composable
fun ZainXAppHubTheme(
    darkTheme: Boolean = true, // Dark by default as requested in Design System
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.background.toArgb()
                window.navigationBarColor = colorScheme.background.toArgb()
                val controller = WindowCompat.getInsetsController(window, view)
                controller.isAppearanceLightStatusBars = !darkTheme
                controller.isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
