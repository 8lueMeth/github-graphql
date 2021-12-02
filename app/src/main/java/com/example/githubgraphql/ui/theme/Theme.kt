package com.example.githubgraphql.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val LightColorPalette = darkColors(
    primary = Wisteria,
    primaryVariant = Ultramarine,
    secondary = EggBlue,
    background = ShadowBlack,
    surface = ShadowBlack,
    error = Charm,
    onPrimary = Black,
    onSecondary = Black,
    onError = Black,
    onBackground = White,
    onSurface = White,
)

@Composable
fun GithubGraphQLTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}