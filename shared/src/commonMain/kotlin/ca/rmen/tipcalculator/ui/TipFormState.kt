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
        get() = amountWithTax.isMoney() && taxAmount.isMoney() && numberCustomer.isInt()

    fun toTipInputOrNull() : TipInput? {
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
