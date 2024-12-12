package day12

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.Grid
import util.getContiguousGroups
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
        val totalPrice = getTotalPriceOfFencing(grid, Region::priceOfFencing)

        assertThat(totalPrice).isEqualTo(1930)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(12)

        val grid = Grid.fromString(input)
        val totalPrice = getTotalPriceOfFencing(grid, Region::priceOfFencing)

        assertThat(totalPrice).isEqualTo(1431440)
    }

    @Test
    fun `part 2 - custom base cases`() {
        testSides("""
            .#.
            ###
        """.trimIndent(), 8)
        testSides("""
            ##
            ##
        """.trimIndent(), 4)
        testSides("""
            ##.
            #..
            ##.
        """.trimIndent(), 8)
    }

    fun testSides(input: String, expectedSides: Int) {
        val grid = Grid.fromString(input)
        val region = grid.getContiguousGroups()
            .map { Region(it, grid) }
            .first { region -> region.valueInGrid == '#' }

        assertThat(region.sides).isEqualTo(expectedSides)
    }

    @Test
    fun `part 2 - special inside corner case`() {
        val input = """
            AAAAAA
            AAABBA
            AAABBA
            ABBAAA
            ABBAAA
            AAAAAA
        """.trimIndent()

        val grid = Grid.fromString(input)
        val sidesPerRegion = grid.getContiguousGroups()
            .map { Region(it, grid) }
            .map { region -> region.valueInGrid to region.sides }
        val totalPrice = getTotalPriceOfFencing(grid, Region::priceOfFencingDiscounted)

        assertThat(sidesPerRegion).containsExactlyInAnyOrder(
            'A' to 12,
            'B' to 4,
            'B' to 4,
        )
        assertThat(totalPrice).isEqualTo(368)
    }

    @Test
    fun `part 2 - example input`() {
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
        val sidesPerRegion = grid.getContiguousGroups()
            .map { Region(it, grid) }
            .map { region -> region.valueInGrid to region.sides }
        val totalPrice = getTotalPriceOfFencing(grid, Region::priceOfFencingDiscounted)

        assertThat(sidesPerRegion).containsExactlyInAnyOrder(
            'R' to 10,
            'I' to 4,
            'C' to 22,
            'F' to 12,
            'V' to 10,
            'J' to 12,
            'C' to 4,
            'E' to 8,
            'I' to 16,
            'M' to 6,
            'S' to 6,
        )
        assertThat(totalPrice).isEqualTo(1206)
    }

    @Test
    fun `part 2 - puzzle input`() {
        val input = readInput(12)

        val grid = Grid.fromString(input)
        val sidesPerRegion = grid.getContiguousGroups()
            .map { Region(it, grid) }
            .map { region -> region.valueInGrid to region.sides }
        println(sidesPerRegion.size)
        val totalPrice = getTotalPriceOfFencing(grid, Region::priceOfFencingDiscounted)

        assertThat(totalPrice).isEqualTo(869070)
    }
}

