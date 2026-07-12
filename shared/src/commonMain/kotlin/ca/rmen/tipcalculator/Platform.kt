package ca.rmen.tipcalculator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform