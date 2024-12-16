package day16

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.Position
import util.readInput

class Day16KtTest {

    @Test
    fun `part 1 - example input`() {
        val input = """
            ###############
            #.......#....E#
            #.#.###.#.###.#
            #.....#.#...#.#
            #.###.#####.#.#
            #.#.#.......#.#
            #.#.#####.###.#
            #...........#.#
            ###.#.#####.#.#
            #...#.....#.#.#
            #.#.#.###.#.#.#
            #.....#...#.#.#
            #.###.#.#.#.#.#
            #S..#.....#...#
            ###############
        """.trimIndent()

        val maze = parseMaze(input)
        val minScore = maze.getLowestScore()

        assertThat(minScore).isEqualTo(7036)
    }

    @Test
    fun `part 1 - example input 2`() {
        val input = """
            #################
            #...#...#...#..E#
            #.#.#.#.#.#.#.#.#
            #.#.#.#...#...#.#
            #.#.#.#.###.#.#.#
            #...#.#.#.....#.#
            #.#.#.#.#.#####.#
            #.#...#.#.#.....#
            #.#.#####.#.###.#
            #.#.#.......#...#
            #.#.###.#####.###
            #.#.#...#.....#.#
            #.#.#.#####.###.#
            #.#.#.........#.#
            #.#.#.#########.#
            #S#.............#
            #################
        """.trimIndent()

        val maze = parseMaze(input)
        val minScore = maze.getLowestScore()

        assertThat(minScore).isEqualTo(11048)
    }

    @Test
    fun `part 1 - alternate test case`() {
        val input = """
            ###########################
            #######################..E#
            ######################..#.#
            #####################..##.#
            ####################..###.#
            ###################..##...#
            ##################..###.###
            #################..####...#
            ################..#######.#
            ###############..##.......#
            ##############..###.#######
            #############..####.......#
            ############..###########.#
            ###########..##...........#
            ##########..###.###########
            #########..####...........#
            ########..###############.#
            #######..##...............#
            ######..###.###############
            #####..####...............#
            ####..###################.#
            ###..##...................#
            ##..###.###################
            #..####...................#
            #.#######################.#
            #S........................#
            ###########################
        """.trimIndent()

        val maze = parseMaze(input)
        val minScore = maze.getLowestScore()

        assertThat(minScore).isEqualTo(21148)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(16)

        val maze = parseMaze(input)
        val minScore = maze.getLowestScore()

        assertThat(minScore).isEqualTo(98416)

    }
}

