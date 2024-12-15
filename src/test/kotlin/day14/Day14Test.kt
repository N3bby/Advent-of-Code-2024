package day14

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
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

    @Test
    @Disabled("Takes 30 seconds too run, and doesn't have an automatic assertion")
    fun `part 2 - puzzle input`() {
        val input = readInput(14)

        val robots = parseRobots(input)
        val bounds = Bounds(101, 103)
        robots.moveUntilChristmasTree(bounds)

        // Test outputs an image file to <PROJECT_ROOT>/img
        // My answer was: 8280
    }

    @Test
    @Disabled("Takes about a minute to run")
    fun `part 2 - puzzle input - using variances`() {
        // This seems like a more general solution for if you don't know what the tree looks like
        // Not sure though...

        val input = readInput(14)

        val robots = parseRobots(input)
        val bounds = Bounds(101, 103)
        val second = robots.getSecondWithClosestRobots(bounds)

        assertThat(second).isEqualTo(8280)
    }
}

