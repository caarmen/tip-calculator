package ca.rmen.tipcalculator.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import kotlin.math.roundToInt

@Composable
fun ScaleToFitWidth(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
        modifier = modifier,
    ) { measurables, constraints ->
        val measurable = measurables.first()

        // Measure the content with no width limit, to get its natural/intrinsic size
        val placeable = measurable.measure(Constraints())

        val availableWidth = constraints.maxWidth
        val scale = if (placeable.width > availableWidth) {
            availableWidth.toFloat() / placeable.width.toFloat()
        } else {
            1f
        }

        val scaledWidth = (placeable.width * scale).roundToInt()
        val scaledHeight = (placeable.height * scale).roundToInt()

        layout(scaledWidth, scaledHeight) {
            placeable.placeWithLayer(0, 0) {
                scaleX = scale
                scaleY = scale
                transformOrigin = TransformOrigin(0f, 0f)
            }
        }
    }
}