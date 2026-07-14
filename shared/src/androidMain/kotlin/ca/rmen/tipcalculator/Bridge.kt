package ca.rmen.tipcalculator

external fun handleTipRequest(
    amountWithTax: Double,
    taxAmount: Double,
    serviceLevel: Int,
    numberCustomer: Int,
    reportFilePath: String,
): DoubleArray

external fun calculateTip(
    amountWithTax: Double,
    taxAmount: Double,
    serviceLevel: Int,
    numberCustomer: Int,
): DoubleArray

external fun generateTipReport(
    amountWithTax: Double,
    taxAmount: Double,
    serviceLevel: Int,
    numberCustomer: Int,
    reportFilePath: String,
    totalWithTip: Double,
    totalTip: Double,
    tipPerPerson: Double,
    pretaxAmount: Double,
    tipPercentage: Double,
)

