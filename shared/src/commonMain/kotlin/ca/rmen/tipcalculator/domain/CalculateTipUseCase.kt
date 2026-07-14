package ca.rmen.tipcalculator.domain

class CalculateTipUseCase(
    private val reportPathProvider: ReportPathProvider,
) {
    operator fun invoke(tipInput: TipInput): TipResult {
        val reportPath = reportPathProvider.reportPath("report.txt")
        println("write report to $reportPath")
        return calculateTip(tipInput, reportPath)
    }
}

expect fun calculateTip(
    tipInput: TipInput,
    reportPath: String,
): TipResult