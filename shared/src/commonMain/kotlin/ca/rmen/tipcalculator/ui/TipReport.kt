package ca.rmen.tipcalculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TipReport(tipReportContent: List<String>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        tipReportContent.forEachIndexed { index, line ->
            val greenBar = Color(red = 196, green = 218, blue = 208)
            val greenBarBorder = Color(red = 130, 150, 140)
            Text(
                line,
                fontFamily = FontFamily.Monospace,
                softWrap = false,
                modifier = Modifier
                    .fillMaxWidth().background(
                        if (index % 2 == 0) greenBar else Color.White
                    )
                    .border(width = 1.dp, color = greenBarBorder)
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }
    }
}

@Preview
@Composable
private fun TipReportPreview() {
    TipReport(tipReportContent = listOf("******", "line 1", "line 2"))
}