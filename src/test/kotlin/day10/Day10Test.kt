package day10

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.Grid
import util.readInput

class Day10KtTest {

    @Test
    fun `part 1 - example input`() {
        val input = """
            89010123
            78121874
            87430965
            96549874
            45678903
            32019012
            01329801
            10456732
        """.trimIndent()

        val topographicMap = Grid.fromString(input, Char::digitToInt)
        val trailHeads = findTrailHeads(topographicMap)
        val score = trailHeads.sumOf { trailHead -> findReachableSummits(trailHead, topographicMap).score }

        assertThat(score).isEqualTo(36)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(10)

        val topographicMap = Grid.fromString(input, Char::digitToInt)
        val trailHeads = findTrailHeads(topographicMap)
        val score = trailHeads.sumOf { trailHead -> findReachableSummits(trailHead, topographicMap).score }

        assertThat(score).isEqualTo(709)
    }

    @Test
    fun `part 2 - example input`() {
        val input = """
            89010123
            78121874
            87430965
            96549874
            45678903
            32019012
            01329801
            10456732
        """.trimIndent()

        val topographicMap = Grid.fromString(input, Char::digitToInt)
        val trailHeads = findTrailHeads(topographicMap)
        val score = trailHeads.sumOf { trailHead -> findReachableSummits(trailHead, topographicMap).rating }

        assertThat(score).isEqualTo(81)
    }

    @Test
    fun `part 2 - puzzle input`() {
        val input = readInput(10)

        val topographicMap = Grid.fromString(input, Char::digitToInt)
        val trailHeads = findTrailHeads(topographicMap)
        val score = trailHeads.sumOf { trailHead -> findReachableSummits(trailHead, topographicMap).rating }

        assertThat(score).isEqualTo(1326)
    }
}

