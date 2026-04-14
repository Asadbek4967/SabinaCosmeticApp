package com.example.sabinacosmeticapplication.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = AppColors.Primary,
    onPrimary = AppColors.OnPrimary,
    primaryContainer = AppColors.SurfaceVariant,
    onPrimaryContainer = AppColors.PrimaryText,

    secondary = AppColors.Accent,
    onSecondary = AppColors.OnAccent,
    secondaryContainer = AppColors.InfoBackground,
    onSecondaryContainer = AppColors.PrimaryText,

    tertiary = AppColors.Success,
    onTertiary = AppColors.OnPrimary,
    tertiaryContainer = AppColors.SuccessBackground,
    onTertiaryContainer = AppColors.Success,

    background = AppColors.Background,
    onBackground = AppColors.PrimaryText,

    surface = AppColors.Surface,
    onSurface = AppColors.PrimaryText,
    surfaceVariant = AppColors.SurfaceVariant,
    onSurfaceVariant = AppColors.SecondaryText,

    error = AppColors.Error,
    onError = AppColors.OnPrimary,
    errorContainer = AppColors.ErrorBackground,
    onErrorContainer = AppColors.Error,

    outline = AppColors.Border,
    outlineVariant = AppColors.Divider,
    scrim = Color.Black.copy(alpha = 0.32f)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFF9FAFB),
    onPrimary = Color(0xFF111827),
    primaryContainer = Color(0xFF1F2937),
    onPrimaryContainer = Color(0xFFF9FAFB),

    secondary = Color(0xFF60A5FA),
    onSecondary = Color(0xFF0F172A),
    secondaryContainer = Color(0xFF1E3A8A),
    onSecondaryContainer = Color(0xFFEFF6FF),

    tertiary = Color(0xFF4ADE80),
    onTertiary = Color(0xFF052E16),
    tertiaryContainer = Color(0xFF14532D),
    onTertiaryContainer = Color(0xFFDCFCE7),

    background = Color(0xFF0B1220),
    onBackground = Color(0xFFF9FAFB),

    surface = Color(0xFF111827),
    onSurface = Color(0xFFF9FAFB),
    surfaceVariant = Color(0xFF1F2937),
    onSurfaceVariant = Color(0xFFCBD5E1),

    error = Color(0xFFF87171),
    onError = Color(0xFF450A0A),
    errorContainer = Color(0xFF7F1D1D),
    onErrorContainer = Color(0xFFFEE2E2),

    outline = Color(0xFF334155),
    outlineVariant = Color(0xFF1E293B),
    scrim = Color.Black.copy(alpha = 0.50f)
)

@Composable
fun SabinaCosmeticApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes.Material,
        content = content
    )
}