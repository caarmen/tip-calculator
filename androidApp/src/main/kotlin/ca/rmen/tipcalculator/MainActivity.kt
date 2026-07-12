package ca.rmen.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ca.rmen.tipcalculator.domain.AndroidReportPathProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        System.loadLibrary("tipcalculator")
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(AndroidReportPathProvider(this))
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}