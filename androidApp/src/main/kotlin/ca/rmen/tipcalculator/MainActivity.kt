package ca.rmen.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.zacsweers.metro.createGraphFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        System.loadLibrary("tipcalculator")
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val graph = createGraphFactory<AndroidAppGraph.Factory>().create(this)

        setContent {
            App(graph.calculateTipUseCase)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(previewCalculateTipUseCase())
}
