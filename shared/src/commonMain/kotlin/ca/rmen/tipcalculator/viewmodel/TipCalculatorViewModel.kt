package ca.rmen.tipcalculator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.rmen.gnucobol.kmp.GnuCOBOL
import ca.rmen.tipcalculator.domain.calculator.CalculateTipUseCase
import ca.rmen.tipcalculator.domain.model.TipCalculations
import ca.rmen.tipcalculator.domain.model.TipInput
import ca.rmen.tipcalculator.domain.reporter.PrintReceiptUseCase
import ca.rmen.tipcalculator.ui.TipResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import org.koin.core.annotation.KoinViewModel
import kotlin.math.abs
import kotlin.math.round


@KoinViewModel
class TipCalculatorViewModel(
    private val calculateUseCase: CalculateTipUseCase,
    private val printUseCase: PrintReceiptUseCase,
) : ViewModel() {

    init {
        GnuCOBOL.initialize()
    }

    private val tipCalculations = MutableStateFlow<TipCalculations?>(null)

    val tipResultState: Flow<TipResultState?> = tipCalculations.map { resultState ->
        resultState?.let {
            TipResultState(
                totalTip = it.totalTip.toMoneyString(),
                tipPerPerson = it.tipPerPerson.toMoneyString(),
            )
        }
    }

    val tipReportContent: StateFlow<List<String>>
        field: MutableStateFlow<List<String>> = MutableStateFlow(listOf())

    fun calculateTip(tipInput: TipInput) {
        viewModelScope.launch {
            tipCalculations.value = calculateUseCase(tipInput)
        }
    }

    fun resetCalculations() {
        tipCalculations.value = null
    }

    fun printReceipt(tipInput: TipInput) {
        viewModelScope.launch {
            calculateUseCase.invoke(tipInput).let { calculations ->
                tipCalculations.value = calculations
                tipReportContent.value = listOf()
                val reportPath = printUseCase(tipInput, calculations)
                FileSystem.SYSTEM.read(reportPath.toPath()) {
                    val lines = readUtf8().split("\n")
                    val columnCount = lines[0].length
                    tipReportContent.value = lines.map { line ->
                        line.padEnd(columnCount, ' ')
                    }
                }
            }
        }
    }
}

/**
 * For now, assume non-negative values and two decimal places.
 * TODO: use proper monetary formatting apis.
 */
private fun Double.toMoneyString(): String {
    val cents = round(abs(this) * 100).toLong()
    val wholePart = cents / 100
    val fractionPart = (cents % 100).toString().padStart(2, '0')
    return "$wholePart.$fractionPart"
}