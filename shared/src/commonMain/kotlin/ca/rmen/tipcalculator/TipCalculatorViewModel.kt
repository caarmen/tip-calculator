package ca.rmen.tipcalculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.rmen.gnucobol.kmp.GnuCOBOL
import ca.rmen.tipcalculator.domain.CalculateTipUseCase
import ca.rmen.tipcalculator.domain.TipInput
import ca.rmen.tipcalculator.domain.TipCalculations
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import kotlin.time.Duration.Companion.milliseconds

class TipCalculatorViewModel(
    private val useCase: CalculateTipUseCase,
) : ViewModel() {

    companion object {
        val timeEmitOneReportLine = 500.milliseconds
    }
    init {
        GnuCOBOL.initialize()
    }

    val tipCalculations: StateFlow<TipCalculations?>
        field: MutableStateFlow<TipCalculations?> = MutableStateFlow(null)

    val tipReportContent: StateFlow<List<String>>
        field: MutableStateFlow<List<String>> = MutableStateFlow(listOf())

    fun calculateTip(tipInput: TipInput) {
        val tipResult = useCase.invoke(tipInput)
        tipCalculations.value = tipResult.tipCalculations
        viewModelScope.launch {
            FileSystem.SYSTEM.read(tipResult.reportPath.toPath()) {
                tipReportContent.value = emptyList()
                val linesBuffer = mutableListOf<String>()
                val lines = readUtf8().split("\n")
                val columnCount = lines[0].length
                lines.forEach {
                    delay(timeEmitOneReportLine)
                    linesBuffer.add(it.padEnd(columnCount, ' '))
                    tipReportContent.value = linesBuffer.toList()
                }
            }
        }
    }
}