package ca.rmen.tipcalculator.ui

import androidx.compose.runtime.saveable.SaverScope
import ca.rmen.tipcalculator.domain.ServiceLevel
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class TipFormState(
    val amountWithTax: String,
    val taxAmount: String,
    val serviceLevel: ServiceLevel,
    val numberCustomer: String,
) {
    val canSubmit: Boolean
        get() = amountWithTax.isNotBlank() && taxAmount.isNotBlank() && numberCustomer.isNotBlank()

    companion object {
        val Saver = object : androidx.compose.runtime.saveable.Saver<TipFormState, Any> {
            override fun SaverScope.save(value: TipFormState): String = Json.encodeToString(value)

            override fun restore(value: Any): TipFormState? {
                return Json.decodeFromString(value as String)
            }
        }
    }
}
