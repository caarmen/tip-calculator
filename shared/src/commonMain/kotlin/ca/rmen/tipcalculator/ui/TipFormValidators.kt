package ca.rmen.tipcalculator.ui

fun String.toMoney(): String? {
    if (isBlank()) return ""
    val transformedValue = replace(",", ".")
    return try {
        transformedValue.toDouble()
        transformedValue
    } catch (_: NumberFormatException) {
        null
    }
}

fun String.toPositiveMoney(): String? {
    if (isBlank()) {
        return ""
    }
    val transformedValue = replace(",", ".")
    return try {
        if (transformedValue.toDouble() > 0) transformedValue else null
    } catch (_: NumberFormatException) {
        null
    }
}

fun String.isPositiveInt(): Boolean {
    if (isBlank()) return true
    try {
        return toInt() > 0
    } catch (_: NumberFormatException) {
        return false
    }
}
