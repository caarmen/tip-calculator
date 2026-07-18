package ca.rmen.tipcalculator.ui

import androidx.compose.runtime.saveable.SaverScope
import ca.rmen.tipcalculator.domain.ServiceLevel
import ca.rmen.tipcalculator.domain.TipInput
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class TipFormState(
    val amountWithTax: String,
    val taxAmount: String,
    val serviceLevel: ServiceLevel,
    val numberCustomer: String,
) {
    val isValid: Boolean
        get() = amountWithTax.isPositiveMoney() && taxAmount.isMoney() && numberCustomer.isPositiveInt()
                && amountWithTax.isNotBlank() && taxAmount.isNotBlank() && numberCustomer.isNotBlank()

    fun updateAmountWithTax(newValue: String): TipFormState =
        if (newValue != amountWithTax && newValue.isPositiveMoney()) {
            copy(amountWithTax = newValue)
        } else this

    fun updateTaxAmount(newValue: String): TipFormState =
        if (newValue != taxAmount && newValue.isMoney()) {
            copy(taxAmount = newValue)
        } else this

    fun updateServiceLevel(newValue: ServiceLevel): TipFormState =
        if (newValue != serviceLevel) {
            copy(serviceLevel = newValue)
        } else this

    fun updateNumberCustomer(newValue: String): TipFormState =
        if (newValue != numberCustomer && newValue.isPositiveInt()) {
            copy(numberCustomer = newValue)
        } else this

    fun toTipInputOrNull(): TipInput? {
        if (isValid) {
            return TipInput(
                amountWithTax = amountWithTax.toDouble(),
                taxAmount = taxAmount.toDouble(),
                serviceLevel = serviceLevel,
                numberCustomer = numberCustomer.toInt(),
            )
        }
        return null
    }

    companion object {
        val Saver = object : androidx.compose.runtime.saveable.Saver<TipFormState, Any> {
            override fun SaverScope.save(value: TipFormState): String = Json.encodeToString(value)

            override fun restore(value: Any): TipFormState? {
                return Json.decodeFromString(value as String)
            }
        }
    }
}
