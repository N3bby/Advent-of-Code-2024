package day20

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.Position
import util.computeDistancesFromStart
import util.readInput


class Day20KtTest {

    @Test
    fun `part 1 - example input`() {
        val input = """
            ###############
            #...#...#.....#
            #.#.#.#.#.###.#
            #S#...#.#.#...#
            #######.#.#.###
            #######.#.#...#
            #######.#.###.#
            ###..E#...#...#
            ###.#######.###
            #...###...#...#
            #.#####.#.###.#
            #.#...#.#.#...#
            #.#.#.#.#.#.###
            #...#...#...###
            ###############
        """.trimIndent()

        val racetrack = parseRacetrack(input)
        val cheats = racetrack.findCheats()
            .groupBy { it.secondsSaved }
            .mapValues { it.value.size }

        assertThat(cheats).containsExactlyInAnyOrderEntriesOf(
            mapOf<SecondsSaved, Int>(
                2 to 14,
                4 to 14,
                6 to 2,
                8 to 4,
                10 to 2,
                12 to 3,
                20 to 1,
                36 to 1,
                38 to 1,
                40 to 1,
                64 to 1,
            )
        )
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(20)

        val racetrack = parseRacetrack(input)
        val cheats = racetrack.findCheats()
            .groupBy { it.secondsSaved }
            .mapValues { it.value.size }

        val cheatsSavingAtLeast100Seconds = cheats
            .filterKeys { it >= 100 }
            .values.sum()
        assertThat(cheatsSavingAtLeast100Seconds).isEqualTo(1452)
    }
}

