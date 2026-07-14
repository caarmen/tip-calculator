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

fun String.isInt(): Boolean {
    if (isBlank()) return true
    try {
        toInt()
        return true
    } catch (_: NumberFormatException) {
        return false
    }
}
