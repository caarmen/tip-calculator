package ca.rmen.tipcalculator.ui

import androidx.compose.runtime.saveable.SaverScope
import ca.rmen.tipcalculator.domain.model.ServiceLevel
import ca.rmen.tipcalculator.domain.model.TipInput
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
        get() = amountWithTax.toPositiveMoney() != null
                && taxAmount.toMoney() != null
                && numberCustomer.isPositiveInt()
                && amountWithTax.isNotBlank()
                && taxAmount.isNotBlank()
                && numberCustomer.isNotBlank()

    /**
     * Return a new state with the new amountWithTax value if:
     * - we were able to transform newValue to a positive monetary value
     * - and the new value is different from the previous value
     *
     * Otherwise, return the current state.
     */
    fun updateAmountWithTax(newValue: String): TipFormState =
        newValue.toPositiveMoney()?.let { transformedValue ->
            if (transformedValue != amountWithTax) {
                copy(amountWithTax = transformedValue)
            } else this
        } ?: this

    /**
     * Return a new state with the new taxAmount value if:
     * - we were able to transform newValue to a monetary value
     * - and the new value is different from the previous value
     *
     * Otherwise, return the current state.
     */
    fun updateTaxAmount(newValue: String): TipFormState =
        newValue.toMoney()?.let { transformedValue ->
            if (transformedValue != taxAmount) {
                copy(taxAmount = transformedValue)
            } else this
        } ?: this

    fun updateServiceLevel(newValue: ServiceLevel): TipFormState = if (newValue != serviceLevel) {
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
