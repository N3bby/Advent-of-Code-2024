package util

import ext.multiplication
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.pow

fun isPrime(value: Long): Boolean {
    if (value == 1L) return false

    for (number in 2L..ceil(value / 2.0).toLong()) {
        if (value % number == 0L) return false
    }

    return true
}

private fun getNextPrime(value: Long): Long {
    var currentVal = value + 1
    while (true) {
        if (isPrime(currentVal)) return currentVal
        currentVal++
    }
}

fun factorizeToPrimes(value: Long): List<Long> {
    val factors = mutableListOf<Long>()

    var currentPrime = 2L
    var currentValue = value

    while (currentValue != 1L) {
        if (currentValue % currentPrime == 0L) {
            factors.add(currentPrime)
            currentValue /= currentPrime
        } else {
            currentPrime = getNextPrime(currentPrime)
        }
    }

    return factors
}

/**
 * Source: https://www.cuemath.com/numbers/lcm-least-common-multiple/
 *
 * Step 1: The prime factorization of 60 and 90 are: 60 = 2 × 2 × 3 × 5 and 90 = 2 × 3 × 3 × 5
 * Step 2: If we write these prime factors in their exponent form it will be expressed as, 60 = 22 × 31 × 51 and 90 = 21 × 32 × 51
 * Step 3: Now, we will find the product of only those factors that have the highest powers among these. This will be, 22 × 32 × 51 = 4 × 9 × 5 = 180
 */
fun leastCommonMultiple(value1: Long, value2: Long): Long {
    val value1Factors = factorizeToPrimes(value1)
    val value2Factors = factorizeToPrimes(value2)

    val value1FactorsWithExponent = value1Factors.groupBy { it }.mapValues { entry -> entry.value.size.toLong() }
    val value2FactorsWithExponent = value2Factors.groupBy { it }.mapValues { entry -> entry.value.size.toLong() }

    val uniqueFactors = (value1Factors + value2Factors).toSet()

    return uniqueFactors.map { factor ->
        val largestExponent = max(
            value1FactorsWithExponent[factor] ?: 0L,
            value2FactorsWithExponent[factor] ?: 0L
        )
        factor.toDouble().pow(largestExponent.toDouble()).toLong()
    }.multiplication()
}

fun leastCommonMultiple(values: List<Long>): Long {
    return values.reduce { acc, value -> leastCommonMultiple(acc, value) }
}