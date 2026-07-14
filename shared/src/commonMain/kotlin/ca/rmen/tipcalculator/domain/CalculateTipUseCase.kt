package ca.rmen.tipcalculator.domain

class CalculateTipUseCase(
    private val reportPathProvider: ReportPathProvider,
) {
    operator fun invoke(tipInput: TipInput): TipResult {
        val reportPath = reportPathProvider.reportPath("report.txt")
        return TipResult(
            calculateTip(tipInput, reportPath),
            reportPath,
        )
    }
}

expect fun calculateTip(
    tipInput: TipInput,
    reportPath: String,
): TipCalculations