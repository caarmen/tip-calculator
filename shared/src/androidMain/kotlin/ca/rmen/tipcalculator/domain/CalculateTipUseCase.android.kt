package ca.rmen.tipcalculator.domain

import ca.rmen.tipcalculator.handleTipRequest

actual fun calculateTip(
    tipInput: TipInput,
    reportPath: String,
): TipResult {
    val result: DoubleArray = handleTipRequest(
        tipInput.amountWithTax,
        tipInput.taxAmount,
        tipInput.serviceLevel.value,
        tipInput.numberCustomer,
        reportPath,
    )
    return TipResult(
        totalTip = result[0],
        tipPerPerson = result[1],
    )
}