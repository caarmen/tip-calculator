package ca.rmen.tipcalculator.domain.reporter

import ca.rmen.tipcalculator.domain.model.TipCalculations
import ca.rmen.tipcalculator.domain.model.TipInput
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
class PrintReceiptUseCase(
    private val reportPathProvider: ReportPathProvider,
    private val tipReporter: TipReporter,
    @Named("io") private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(tipInput: TipInput, tipCalculations: TipCalculations): String =
        withContext(dispatcher) {
            val reportPath = reportPathProvider.reportPath("report.txt")
            tipReporter.generateTipReport(tipInput, reportPath, tipCalculations)
            reportPath
        }
}
