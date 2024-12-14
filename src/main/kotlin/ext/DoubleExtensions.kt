package ext

fun Double.isNaturalNumber(delta: Double = 0.0000000001): Boolean {
    return this % 1.0 < delta
}
