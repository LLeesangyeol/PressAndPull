package com.example.pressandpull.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MonoColorScheme = lightColorScheme(
    primary = Color(0xFF050505),
    onPrimary = Color.White,
    secondary = Color(0xFF2D2D2D),
    onSecondary = Color.White,
    tertiary = Color(0xFF666666),
    background = Color.White,
    onBackground = Color(0xFF050505),
    surface = Color.White,
    onSurface = Color(0xFF050505),
    surfaceVariant = Color(0xFFF4F4F4),
    onSurfaceVariant = Color(0xFF171717),
    outline = Color(0xFFE2E2E2),
    error = Color(0xFFB00020)
)

@Composable
fun PressAndPullTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MonoColorScheme,
        typography = Typography,
        content = content
    )
}

