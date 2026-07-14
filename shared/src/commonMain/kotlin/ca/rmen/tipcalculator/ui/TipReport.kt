package ca.rmen.tipcalculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TipReport(tipReportContent: List<String>) {
    val barHeight = 48.dp
    val holeDiameter = barHeight / 4
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Box(modifier = Modifier.fillMaxSize().clipToBounds().pointerInput(Unit) {
        detectTransformGestures { _, pan, zoom, _ ->
            scale = (scale * zoom).coerceIn(0.2f, 3f)
            offset += pan
        }
    }) {
        Column(modifier = Modifier.fillMaxWidth().graphicsLayer(
            scaleX = scale,
            scaleY = scale,
            translationX = offset.x,
            translationY = offset.y
        )) {
            tipReportContent.forEachIndexed { index, line ->
                val greenBar = Color(red = 196, green = 218, blue = 208)
                val greenBarBorder = Color(red = 130, 150, 140)
                Row(
                    modifier = Modifier.fillMaxWidth().background(
                        if (index % 2 == 0) greenBar else Color.White
                    ).border(width = 1.dp, color = greenBarBorder)
                        .height(barHeight)
                        .padding(horizontal = 8.dp, vertical = 4.dp),

                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PaperHole(diameter = holeDiameter)

                    Text(
                        line,
                        fontFamily = FontFamily.Monospace,
                        softWrap = false,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    PaperHole(diameter = holeDiameter)
                }
            }
        }
    }
}

@Composable
fun PaperHole(diameter: Dp, modifier: Modifier = Modifier) {
    Box(modifier = modifier.size(diameter).clip(CircleShape).background(Color.Black))
}

@Preview
@Composable
private fun TipReportPreview() {
    TipReport(tipReportContent = listOf("******", "line 1", "line 2"))
}