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
                fontSize = bodyLarge.fontSize * fontSizeMultiplier
            ),
            displayMedium = displayMedium.copy(
                fontFamily = fontFamily,
                fontSize = bodyLarge.fontSize * fontSizeMultiplier
            ),
            displaySmall = displaySmall.copy(
                fontFamily = fontFamily,
                fontSize = bodyLarge.fontSize * fontSizeMultiplier
            ),
            headlineLarge = headlineLarge.copy(
                fontFamily = fontFamily,
                fontSize = bodyLarge.fontSize * fontSizeMultiplier
            ),
            headlineMedium = headlineMedium.copy(
                fontFamily = fontFamily,
                fontSize = bodyLarge.fontSize * fontSizeMultiplier
            ),
            headlineSmall = headlineSmall.copy(
                fontFamily = fontFamily,
                fontSize = bodyLarge.fontSize * fontSizeMultiplier
            ),
            titleLarge = titleLarge.copy(
                fontFamily = fontFamily,
                fontSize = bodyLarge.fontSize * fontSizeMultiplier
            ),
            titleMedium = titleMedium.copy(
                fontFamily = fontFamily,
                fontSize = bodyLarge.fontSize * fontSizeMultiplier
            ),
            titleSmall = titleSmall.copy(
                fontFamily = fontFamily,
                fontSize = bodyLarge.fontSize * fontSizeMultiplier
            ),
            bodyLarge = bodyLarge.copy(
                fontFamily = fontFamily,
                fontSize = bodyLarge.fontSize * fontSizeMultiplier
            ),
            bodyMedium = bodyMedium.copy(
                fontFamily = fontFamily,
                fontSize = bodyLarge.fontSize * fontSizeMultiplier
            ),
            bodySmall = bodySmall.copy(
                fontFamily = fontFamily,
                fontSize = bodyLarge.fontSize * fontSizeMultiplier
            ),
            labelLarge = labelLarge.copy(
                fontFamily = fontFamily,
                fontSize = bodyLarge.fontSize * fontSizeMultiplier
            ),
            labelMedium = labelMedium.copy(
                fontFamily = fontFamily,
                fontSize = bodyLarge.fontSize * fontSizeMultiplier
            ),
            labelSmall = labelSmall.copy(
                fontFamily = fontFamily,
                fontSize = bodyLarge.fontSize * fontSizeMultiplier
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