package ca.rmen.tipcalculator.domain

data class TipInput(
    val amountWithTax: Double,
    val taxAmount: Double,
    val serviceLevel: Int,
    val numberCustomer: Int,
)
