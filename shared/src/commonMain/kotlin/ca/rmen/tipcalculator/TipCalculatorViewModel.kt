package ca.rmen.tipcalculator

import androidx.lifecycle.ViewModel
import ca.rmen.gnucobol.kmp.GnuCOBOL
import ca.rmen.tipcalculator.domain.CalculateTipUseCase
import ca.rmen.tipcalculator.domain.TipInput
import ca.rmen.tipcalculator.domain.TipResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TipCalculatorViewModel(
    private val useCase: CalculateTipUseCase,
) : ViewModel() {

    init {
        GnuCOBOL.initialize()
    }
    val tipResult: StateFlow<TipResult?>
        field: MutableStateFlow<TipResult?> = MutableStateFlow(null)

    fun calculateTip(tipInput: TipInput) {
        tipResult.value = useCase.invoke(tipInput)
    }
}