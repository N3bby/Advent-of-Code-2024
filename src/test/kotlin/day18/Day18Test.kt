package day18

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.Bounds
import util.Position
import util.readInput

class Day18KtTest {

    @Test
    fun `part 1 - example input`() {
        val input = """
            5,4
            4,2
            4,5
            3,0
            2,1
            6,3
            2,4
            1,5
            0,6
            3,3
            2,6
            5,1
            1,2
            5,5
            2,5
            6,5
            1,4
            0,4
            6,4
            1,1
            6,1
            1,0
            0,5
            1,6
            2,0
        """.trimIndent()

        val bytes = parseBytes(input)
        val bounds = Bounds(7, 7)

        val amountOfStepsTaken = getAmountOfStepsUntilExit(
            bounds = bounds,
            bytes = bytes,
            fallenBytes = 12
        )

        assertThat(amountOfStepsTaken).isEqualTo(22)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(18)

        val bytes = parseBytes(input)
        val bounds = Bounds(71, 71)

        val amountOfStepsTaken = getAmountOfStepsUntilExit(
            bounds = bounds,
            bytes = bytes,
            fallenBytes = 1024,
        )

        assertThat(amountOfStepsTaken).isEqualTo(322)
    }

    @Test
    fun `part 2 - example input`() {
        val input = """
            5,4
            4,2
            4,5
            3,0
            2,1
            6,3
            2,4
            1,5
            0,6
            3,3
            2,6
            5,1
            1,2
            5,5
            2,5
            6,5
            1,4
            0,4
            6,4
            1,1
            6,1
            1,0
            0,5
            1,6
            2,0
        """.trimIndent()

        val bytes = parseBytes(input)
        val bounds = Bounds(7, 7)

        val byteThatBlocksPath = getFirstByteThatBlocksPath(bounds, bytes)

        assertThat(byteThatBlocksPath).isEqualTo(Position(6, 1))
    }

    @Test
    fun `part 2 - puzzle input`() {
        val input = readInput(18)

        val bytes = parseBytes(input)
        val bounds = Bounds(71, 71)

        val byteThatBlocksPath = getFirstByteThatBlocksPath(bounds, bytes)

        assertThat(byteThatBlocksPath).isEqualTo(Position(60, 21))
    }
}

