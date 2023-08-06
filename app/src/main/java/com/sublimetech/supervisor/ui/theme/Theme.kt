package com.sublimetech.supervisor.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Orange,
    primaryVariant = LightOrange,
    secondary = Purple,
    background = Dark,
    surface = DarkBlue,
    onPrimary = LightGray,
)

private val LightColorPalette = lightColors(
    primary = Purple,
    primaryVariant = LightPurple,
    secondary = Orange,
    background = White,
    surface = LightGray,
    onPrimary = LightGray,
    /* Other default colors to override

    */
)

@Composable
fun SupervisorTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}