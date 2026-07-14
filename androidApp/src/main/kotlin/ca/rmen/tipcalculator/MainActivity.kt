package ca.rmen.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ca.rmen.tipcalculator.di.appModule
import ca.rmen.tipcalculator.domain.AndroidReportPathProvider
import ca.rmen.tipcalculator.domain.ReportPathProvider
import org.koin.core.context.startKoin
import org.koin.dsl.module

private var koinStarted = false

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        System.loadLibrary("tipcalculator")
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        initKoin()

        setContent {
            App()
        }
    }

    private fun initKoin() {
        if (!koinStarted) {
            startKoin {
                modules(
                    appModule(),
                    module {
                        single<ReportPathProvider> { AndroidReportPathProvider(this@MainActivity) }
                    },
                )
            }
            koinStarted = true
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(PreviewReportPathProvider())
}

private class PreviewReportPathProvider : ReportPathProvider {
    override fun reportPath(filename: String): String {
        return "/tmp/report.txt"
    }
}
