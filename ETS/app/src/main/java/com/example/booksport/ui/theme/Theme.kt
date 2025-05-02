package com.example.booksport.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    secondary = GreenSecondary,
    background = WhiteBackground,
    surface = WhiteBackground,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = BlackText,
    onSurface = BlackText
)

@Composable
fun BookSportTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}

