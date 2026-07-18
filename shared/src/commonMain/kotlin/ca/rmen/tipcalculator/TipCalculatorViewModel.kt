package ca.rmen.tipcalculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.rmen.gnucobol.kmp.GnuCOBOL
import ca.rmen.tipcalculator.domain.CalculateTipUseCase
import ca.rmen.tipcalculator.domain.PrintReceiptUseCase
import ca.rmen.tipcalculator.domain.TipCalculations
import ca.rmen.tipcalculator.domain.TipInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM

class TipCalculatorViewModel(
    private val calculateUseCase: CalculateTipUseCase,
    private val printUseCase: PrintReceiptUseCase,
) : ViewModel() {

    init {
        GnuCOBOL.initialize()
    }

    val tipCalculations: StateFlow<TipCalculations?>
        field: MutableStateFlow<TipCalculations?> = MutableStateFlow(null)

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
        calculateTip(tipInput)
        tipCalculations.value?.let {
            tipReportContent.value = listOf()
            viewModelScope.launch {
                val reportPath = printUseCase(tipInput, it)
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