package ca.rmen.tipcalculator

import androidx.lifecycle.ViewModel
import ca.rmen.gnucobol.kmp.GnuCOBOL
import ca.rmen.tipcalculator.domain.CalculateTipUseCase
import ca.rmen.tipcalculator.domain.TipInput
import ca.rmen.tipcalculator.domain.TipCalculations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM

class TipCalculatorViewModel(
    private val useCase: CalculateTipUseCase,
) : ViewModel() {

    init {
        GnuCOBOL.initialize()
    }
    val tipCalculations: StateFlow<TipCalculations?>
        field: MutableStateFlow<TipCalculations?> = MutableStateFlow(null)

    val tipReportContent: StateFlow<String>
        field: MutableStateFlow<String> = MutableStateFlow("")

    fun calculateTip(tipInput: TipInput) {
        val tipResult = useCase.invoke(tipInput)
        tipCalculations.value = tipResult.tipCalculations
        FileSystem.SYSTEM.read(tipResult.reportPath.toPath()) {
            tipReportContent.value = readUtf8()
        }
    }
}