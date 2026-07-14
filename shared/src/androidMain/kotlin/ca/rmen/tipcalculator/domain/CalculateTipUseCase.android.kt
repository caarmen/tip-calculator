package ca.rmen.tipcalculator.domain

actual fun calculateTip(
    tipInput: TipInput,
): TipCalculations {
    val result: DoubleArray = ca.rmen.tipcalculator.calculateTip(
        tipInput.amountWithTax,
        tipInput.taxAmount,
        tipInput.serviceLevel.value,
        tipInput.numberCustomer,
    )
    return TipCalculations(
        totalTip = result[0],
        tipPerPerson = result[1],
    )
}