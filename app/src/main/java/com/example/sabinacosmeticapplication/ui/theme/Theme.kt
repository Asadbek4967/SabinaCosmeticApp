package com.example.sabinacosmeticapplication.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = BrandPink,
    secondary = BrandBlue,
    background = AppBackground,
    surface = AppSurface,
    onPrimary = AppSurface,
    onSecondary = AppSurface,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    error = Danger
)

private val DarkColorScheme = darkColorScheme(
    primary = BrandPink,
    secondary = BrandBlue,
    background = TextPrimary,
    surface = Color(0xFF1A1A1A),
    onPrimary = AppSurface,
    onSecondary = AppSurface,
    onBackground = AppSurface,
    onSurface = AppSurface,
    error = Danger
)

@Composable
fun SabinaCosmeticApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}