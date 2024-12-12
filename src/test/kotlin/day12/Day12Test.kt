package day12

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.Grid
import util.readInput

class Day12KtTest {

    @Test
    fun `part 1 - example input`() {
        val input = """
            RRRRIICCFF
            RRRRIICCCF
            VVRRRCCFFF
            VVRCCCJFFF
            VVVVCJJCFE
            VVIVCCJJEE
            VVIIICJJEE
            MIIIIIJJEE
            MIIISIJEEE
            MMMISSJEEE
        """.trimIndent()

        val grid = Grid.fromString(input)
        val totalPrice = getTotalPriceOfFencing(grid)

        assertThat(totalPrice).isEqualTo(1930)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(12)

        val grid = Grid.fromString(input)
        val totalPrice = getTotalPriceOfFencing(grid)

        assertThat(totalPrice).isEqualTo(1431440)
    }
}

