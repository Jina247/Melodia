package com.jina.clonespotify.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightPinkScheme = lightColorScheme(
    primary = Color(0xFFF48FB1),       // Main rosy pink
    onPrimary = Color.White,            // Text/icons on primary
    secondary = Color(0xFFFFD1DC),      // Soft blush
    onSecondary = Color(0xFF2A2A2A),    // Text on blush
    background = Color(0xFFFFF5F7),     // Creamy background
    onBackground = Color(0xFF2A2A2A),   // Main text
    surface = Color(0xFFFFFFFF),        // Cards, sheets, etc.
    onSurface = Color(0xFF2A2A2A),      // Text/icons on surface
    error = Color(0xFFE57373)
)

private val DarkPinkScheme = darkColorScheme(
    primary = Color(0xFFF06292),        // Brighter pink for contrast
    onPrimary = Color(0xFF1C1B1F),
    secondary = Color(0xFFF8BBD0),      // Muted rose
    onSecondary = Color(0xFF1C1B1F),
    background = Color(0xFF1C1B1F),     // Deep charcoal
    onBackground = Color(0xFFFFEFF3),   // Light text
    surface = Color(0xFF2A2A2A),        // Cards, player, etc.
    onSurface = Color(0xFFFFEFF3),
    error = Color(0xFFFF8A80)
)

// Typography
private val AppTypography = Typography(
    displayLarge = Typography().displayLarge.copy(color = Color(0xFF2A2A2A)),
    titleLarge = Typography().titleLarge.copy(color = Color(0xFF2A2A2A)),
    bodyMedium = Typography().bodyMedium.copy(color = Color(0xFF2A2A2A))
)

// Theme Application
@Composable
fun CloneSpotifyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkPinkScheme else LightPinkScheme

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}