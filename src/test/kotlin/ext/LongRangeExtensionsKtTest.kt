package ext

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LongRangeExtensionsKtTest {

    @Test
    fun `intersect - returns same range if it is fully contained within it`() {
        val overlap = (1L..10L).intersect(2L..6L)
        assertThat(overlap).isEqualTo(2L..6L)
    }

    @Test
    fun `intersect - returns null if there is no overlap`() {
        val overlap = (1L..10L).intersect(15L..20L)
        assertThat(overlap).isNull()
    }

    @Test
    fun `intersect - returns overlap on the lower end`() {
        val overlap = (10L..14L).intersect(5L..14L)
        assertThat(overlap).isEqualTo(10L..14L)
    }

    @Test
    fun `intersect - returns overlap on the upper end`() {
        val overlap = (1L..10L).intersect(8L..16L)
        assertThat(overlap).isEqualTo(8L..10L)
    }

    @Test
    fun `intersect - returns source range if given range completely contains it`() {
        val overlap = (369583040L .. 369875982L).intersect(313833516L..429550890L)
        assertThat(overlap).isEqualTo(369583040L .. 369875982L)
    }

    @Test
    fun `difference - returns null if range is contained within`() {
        val difference = (1L..10L).difference(2L..6L)
        assertThat(difference).isEmpty()
    }

    @Test
    fun `difference - returns given range if it is fully outside of it`() {
        val difference = (1L..10L).difference(15L..20L)
        assertThat(difference).isEqualTo(listOf(15L..20L))
    }

    @Test
    fun `difference - returns part that does not overlap at the lower end`() {
        val difference = (5L..10L).difference(2L..6L)
        assertThat(difference).isEqualTo(listOf(2L..4L))
    }

    @Test
    fun `difference - returns part that does not overlap at the upper end`() {
        val difference = (10L..14L).difference(5L..14L)
        assertThat(difference).isEqualTo(listOf(5L..9L))
    }

    @Test
    fun `difference - returns parts that do not overlap at the lower and upper end if given range fully contains range`() {
        val difference = (5L..10L).difference(1L..15L)
        assertThat(difference).isEqualTo(listOf(1L..4L, 11L..15L))
    }

    @Test
    fun `difference - returns parts that do not overlap at the lower and upper end if given range fully contains range upper exact`() {
        val difference = (5L..10L).difference(1L..10L)
        assertThat(difference).isEqualTo(listOf(1L..4L))
    }

    @Test
    fun `difference - returns parts that do not overlap at the lower and upper end if given range fully contains range lower exact`() {
        val difference = (5L..10L).difference(5L..15L)
        assertThat(difference).isEqualTo(listOf(11L..15L))
    }

}