package ca.rmen.tipcalculator

import androidx.compose.ui.window.ComposeUIViewController
import ca.rmen.tipcalculator.domain.IosReportPathProvider

fun MainViewController() = ComposeUIViewController { App(IosReportPathProvider()) }