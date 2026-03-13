package com.example.sabinacosmeticapplication.ui.theme

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
    primary = PrimaryPink,
    onPrimary = TextWhite,
    secondary = AccentRose,
    onSecondary = TextWhite,
    tertiary = FlashSaleColor,
    onTertiary = TextWhite,
    background = TextPrimary,
    onBackground = TextWhite,
    surface = Color(0xFF1A1C1E),
    onSurface = TextWhite,
    error = ErrorRed,
    onError = TextWhite
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryPink,
    onPrimary = TextWhite,
    secondary = AccentRose,
    onSecondary = TextWhite,
    tertiary = FlashSaleColor,
    onTertiary = TextWhite,
    background = BackgroundLight,
    onBackground = TextPrimary,
    surface = SurfaceWhite,
    onSurface = TextPrimary,
    error = ErrorRed,
    onError = TextWhite
)

@Composable
fun SabinaCosmeticApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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