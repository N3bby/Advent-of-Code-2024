package util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PrimesKtTest {

    @Test
    fun `isPrime - should be correct`() {
        assertThat(isPrime(1L)).isFalse()
        assertThat(isPrime(13L)).isTrue()
        assertThat(isPrime(6L)).isFalse()
        assertThat(isPrime(4L)).isFalse()
    }

    @Test
    fun `factorizeToPrimes - should return correct values`() {
        assertThat(factorizeToPrimes(100L)).containsExactlyInAnyOrder(2L, 2L, 5L, 5L)
        assertThat(factorizeToPrimes(60L)).containsExactlyInAnyOrder(2L, 2L, 3L, 5L)
        assertThat(factorizeToPrimes(90L)).containsExactlyInAnyOrder(2L, 3L, 3L, 5L)
    }

    @Test
    fun `leastCommonMultiple - of 2 numbers should be correct`() {
        assertThat(leastCommonMultiple(60L, 90L)).isEqualTo(180)
        assertThat(leastCommonMultiple(10L, 10L)).isEqualTo(10L)
    }

    @Test
    fun `leastCommonMultiple - of n numbers should be correct`() {
        assertThat(leastCommonMultiple(listOf(6L, 9L, 12L))).isEqualTo(36L)
    }
}