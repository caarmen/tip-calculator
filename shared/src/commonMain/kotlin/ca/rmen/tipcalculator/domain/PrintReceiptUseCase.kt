package ca.rmen.tipcalculator.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class PrintReceiptUseCase(
    private val reportPathProvider: ReportPathProvider,
    private val tipReporter: TipReporter,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend operator fun invoke(tipInput: TipInput, tipCalculations: TipCalculations): String =
        withContext(dispatcher) {
            val reportPath = reportPathProvider.reportPath("report.txt")
            tipReporter.generateTipReport(tipInput, reportPath, tipCalculations)
            reportPath
        }
}
