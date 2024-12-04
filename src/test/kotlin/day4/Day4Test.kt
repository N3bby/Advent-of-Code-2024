package day4

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.Grid
import util.readInput


class Day4KtTest {

    private val XMAS = "XMAS"

    @Test
    fun `part 1 - example input`() {
        val input = """
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX
        """.trimIndent()

        val grid = Grid.fromString(input)

        assertThat(findWordMatchesInGrid(grid, XMAS)).isEqualTo(18)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(4)

        val grid = Grid.fromString(input)

        assertThat(findWordMatchesInGrid(grid, XMAS)).isEqualTo(2532)
    }

    @Test
    fun `part 2 - example input`() {
        val input = """
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX
        """.trimIndent()

        val grid = Grid.fromString(input)

        assertThat(findKernelMatchesInGrid(grid, xmasKernels)).isEqualTo(9)
    }

    @Test
    fun `part 2 - puzzle input`() {
        val input = readInput(4)

        val grid = Grid.fromString(input)

        assertThat(findKernelMatchesInGrid(grid, xmasKernels)).isEqualTo(1941)
    }

}

