package day8

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.readInput

class Day8KtTest {

    @Test
    fun `part 1 - example input`() {
        val input = """
            ............
            ........0...
            .....0......
            .......0....
            ....0.......
            ......A.....
            ............
            ............
            ........A...
            .........A..
            ............
            ............
        """.trimIndent()

        val (bounds, antennas) = parseAntennas(input)
        val antinodes = findAntinodes(bounds, antennas, false)

        assertThat(antinodes.size).isEqualTo(14)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(8)

        val (bounds, antennas) = parseAntennas(input)
        val antinodes = findAntinodes(bounds, antennas, false)

        assertThat(antinodes.size).isEqualTo(278)
    }

    @Test
    fun `part 2 - example input`() {
        val input = """
            ............
            ........0...
            .....0......
            .......0....
            ....0.......
            ......A.....
            ............
            ............
            ........A...
            .........A..
            ............
            ............
        """.trimIndent()

        val (bounds, antennas) = parseAntennas(input)
        val antinodes = findAntinodes(bounds, antennas, true)

        assertThat(antinodes.size).isEqualTo(34)
    }

    @Test
    fun `part 2 - puzzle input`() {
        val input = readInput(8)

        val (bounds, antennas) = parseAntennas(input)
        val antinodes = findAntinodes(bounds, antennas, true)

        assertThat(antinodes.size).isEqualTo(1067)
    }
}

