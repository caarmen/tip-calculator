package ca.rmen.tipcalculator

import androidx.compose.ui.window.ComposeUIViewController
import dev.zacsweers.metro.createGraph
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    val graph = createGraph<IosAppGraph>()
    return ComposeUIViewController { App(graph.calculateTipUseCase) }
}
