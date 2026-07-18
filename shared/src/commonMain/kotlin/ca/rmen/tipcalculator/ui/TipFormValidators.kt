package ca.rmen.tipcalculator.ui

fun String.isMoney(): Boolean {
    if (isBlank()) return true
    try {
        toDouble()
        return true
    } catch (_: NumberFormatException) {
        return false
    }
}

fun String.isPositiveMoney(): Boolean {
    if (isBlank()) return true
    try {
        return toDouble() > 0
    } catch (_: NumberFormatException) {
        return false
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
