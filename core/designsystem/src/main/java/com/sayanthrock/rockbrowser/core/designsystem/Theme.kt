package com.sayanthrock.rockbrowser.core.designsystem
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
private val DarkColorScheme = darkColorScheme(primary = SubtleMetallic, background = Black)
private val LightColorScheme = lightColorScheme(primary = SubtleAccent, background = White)
@Composable fun RockBrowserTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme, typography = Typography, shapes = Shapes, content = content)
}
