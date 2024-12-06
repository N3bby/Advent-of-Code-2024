package day6

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import util.readInput

class Day6KtTest {

    @Test
    fun `part 1 - example input`() {
        val input = """
            ....#.....
            .........#
            ..........
            ..#.......
            .......#..
            ..........
            .#..^.....
            ........#.
            #.........
            ......#...
        """.trimIndent()

        val (map, guard) = parseMap(input)
        val movedGuard = guard.moveUntilOutOfBounds(map)

        assertThat(movedGuard.visitedPositions.map { it.first }.toSet().size).isEqualTo(41)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(6)

        val (map, guard) = parseMap(input)
        val movedGuard = guard.moveUntilOutOfBounds(map)

        assertThat(movedGuard.visitedPositions.map { it.first }.toSet().size).isEqualTo(4374)
    }

    @Test
    fun `part 2 - example input`() {
        val input = """
            ....#.....
            .........#
            ..........
            ..#.......
            .......#..
            ..........
            .#..^.....
            ........#.
            #.........
            ......#...
        """.trimIndent()

        val (map, guard) = parseMap(input)
        val obstructionsThatCauseLoop = findManualObstructionsThatCauseLoop(map, guard)

        assertThat(obstructionsThatCauseLoop).isEqualTo(6)
    }

    @Test
    @Disabled("Takes 15s to run on my M1 Macbook Pro")
    fun `part 2 - puzzle input`() {
        val input = readInput(6)

        val (map, guard) = parseMap(input)
        val obstructionsThatCauseLoop = findManualObstructionsThatCauseLoop(map, guard)

        assertThat(obstructionsThatCauseLoop).isEqualTo(1705)
    }
}

