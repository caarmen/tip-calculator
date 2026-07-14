package ca.rmen.tipcalculator

import androidx.compose.ui.window.ComposeUIViewController
import ca.rmen.tipcalculator.di.appModule
import ca.rmen.tipcalculator.domain.IosReportPathProvider
import ca.rmen.tipcalculator.domain.ReportPathProvider
import org.koin.core.context.startKoin
import org.koin.dsl.module
import platform.UIKit.UIViewController

private var koinStarted = false

fun MainViewController(): UIViewController {
    initKoin()
    return ComposeUIViewController { App() }
}

private fun initKoin() {
    if (!koinStarted) {
        startKoin {
            modules(
                appModule(),
                module {
                    single<ReportPathProvider> { IosReportPathProvider() }
                },
            )
        }
        koinStarted = true
    }
}
