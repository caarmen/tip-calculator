package ca.rmen.tipcalculator.domain

class PrintReceiptUseCase(
    private val reportPathProvider: ReportPathProvider,
) {
    operator fun invoke(tipInput: TipInput, tipCalculations: TipCalculations): String {
        val reportPath = reportPathProvider.reportPath("report.txt")
        generateTipReport(tipInput, reportPath, tipCalculations)
        return reportPath
    }
}

expect fun generateTipReport(
    tipInput: TipInput,
    reportPath: String,
    tipCalculations: TipCalculations,
)