package ca.rmen.tipcalculator

import ca.rmen.tipcalculator.domain.CalculateTipUseCase
import ca.rmen.tipcalculator.domain.ReportPathProvider

private class PreviewReportPathProvider : ReportPathProvider {
    override fun reportPath(filename: String): String = "/tmp/report.txt"
}

fun previewCalculateTipUseCase(): CalculateTipUseCase = CalculateTipUseCase(PreviewReportPathProvider())
