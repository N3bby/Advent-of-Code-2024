package day14

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.Bounds
import util.readInput

class Day14KtTest {

    @Test
    fun `part 1 - example input`() {
        val input = """
            p=0,4 v=3,-3
            p=6,3 v=-1,-3
            p=10,3 v=-1,2
            p=2,0 v=2,-1
            p=0,0 v=1,3
            p=3,0 v=-2,-2
            p=7,6 v=-1,-3
            p=3,0 v=-1,-2
            p=9,3 v=2,3
            p=7,3 v=-1,2
            p=2,4 v=2,-3
            p=9,5 v=-3,-3
        """.trimIndent()

        val robots = parseRobots(input)
        val bounds = Bounds(11, 7)
        val safetyFactor = robots
            .move(100, bounds)
            .getSafetyFactor(bounds)

        assertThat(safetyFactor).isEqualTo(12)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(14)

        val robots = parseRobots(input)
        val bounds = Bounds(101, 103)
        val safetyFactor = robots
            .move(100, bounds)
            .getSafetyFactor(bounds)

        assertThat(safetyFactor).isEqualTo(231019008)
    }


}

