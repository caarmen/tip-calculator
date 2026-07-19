package ca.rmen.tipcalculator.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import tipcalculator.shared.generated.resources.Res
import tipcalculator.shared.generated.resources.decterm
import tipcalculator.shared.generated.resources.doto

@Composable
private fun appTypography(
    fontFamily: FontFamily,
    fontSizeMultiplier: Double = 1.0,
): Typography =
    with(MaterialTheme.typography) {
        copy(
            displayLarge = displayLarge.copy(
                fontFamily = fontFamily,
                fontSize = displayLarge.fontSize * fontSizeMultiplier
            ),
            displayMedium = displayMedium.copy(
                fontFamily = fontFamily,
                fontSize = displayMedium.fontSize * fontSizeMultiplier
            ),
            displaySmall = displaySmall.copy(
                fontFamily = fontFamily,
                fontSize = displaySmall.fontSize * fontSizeMultiplier
            ),
            headlineLarge = headlineLarge.copy(
                fontFamily = fontFamily,
                fontSize = headlineLarge.fontSize * fontSizeMultiplier
            ),
            headlineMedium = headlineMedium.copy(
                fontFamily = fontFamily,
                fontSize = headlineMedium.fontSize * fontSizeMultiplier
            ),
            headlineSmall = headlineSmall.copy(
                fontFamily = fontFamily,
                fontSize = headlineSmall.fontSize * fontSizeMultiplier
            ),
            titleLarge = titleLarge.copy(
                fontFamily = fontFamily,
                fontSize = titleLarge.fontSize * fontSizeMultiplier
            ),
            titleMedium = titleMedium.copy(
                fontFamily = fontFamily,
                fontSize = titleMedium.fontSize * fontSizeMultiplier
            ),
            titleSmall = titleSmall.copy(
                fontFamily = fontFamily,
                fontSize = titleSmall.fontSize * fontSizeMultiplier
            ),
            bodyLarge = bodyLarge.copy(
                fontFamily = fontFamily,
                fontSize = bodyLarge.fontSize * fontSizeMultiplier
            ),
            bodyMedium = bodyMedium.copy(
                fontFamily = fontFamily,
                fontSize = bodyMedium.fontSize * fontSizeMultiplier
            ),
            bodySmall = bodySmall.copy(
                fontFamily = fontFamily,
                fontSize = bodySmall.fontSize * fontSizeMultiplier
            ),
            labelLarge = labelLarge.copy(
                fontFamily = fontFamily,
                fontSize = labelLarge.fontSize * fontSizeMultiplier
            ),
            labelMedium = labelMedium.copy(
                fontFamily = fontFamily,
                fontSize = labelMedium.fontSize * fontSizeMultiplier
            ),
            labelSmall = labelSmall.copy(
                fontFamily = fontFamily,
                fontSize = labelSmall.fontSize * fontSizeMultiplier
            ),
        )
    }

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = appTypography(
            // https://www.dafont.com/dec-terminal-modern.font
            fontFamily = FontFamily(
                Font(Res.font.decterm, FontWeight.Black)
            ),
            fontSizeMultiplier = 1.5,
        ),
        content = content,
    )
}

@Composable
fun ReportTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = appTypography(
            // https://github.com/oliverlalan/Doto
            fontFamily = FontFamily(
                Font(Res.font.doto, FontWeight.Normal)
            ),
        ),
        content = content,
    )
}