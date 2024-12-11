package day11

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.readInput

class Day11KtTest {

    @Test
    fun `part 1 - example input`() {
        val input = "125 17"

        val stones = parseStones(input)
        val stonesAfter25Blinks = stones.stonesAfterBlinks(25)

        assertThat(stonesAfter25Blinks).isEqualTo(55312)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(11)

        val stones = parseStones(input)
        val stonesAfter25Blinks = stones.stonesAfterBlinks(25)

        assertThat(stonesAfter25Blinks).isEqualTo(231278)
    }

    @Test
    fun `part 2 - puzzle input`() {
        val input = readInput(11)

        val stones = parseStones(input)
        val stonesAfter25Blinks = stones.stonesAfterBlinks(75)

        assertThat(stonesAfter25Blinks).isEqualTo(274229228071551)
    }

}



