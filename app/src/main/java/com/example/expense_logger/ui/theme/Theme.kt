package com.example.expense_logger.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PinkPrimary,
    secondary = PinkDark,
    background = PinkLight,
    surface = PinkLight,
    onPrimary = Color.White,
    onBackground = TextDark
)

@Composable
fun Expense_LoggerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}
