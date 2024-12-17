package day16

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
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

        // Extension for part 2
        val shortestPath = maze.getShortestPath()!!
        assertThat(shortestPath.getScore()).isEqualTo(7036)
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

    @Test
    fun `part 2 - example input`() {
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
        val paths = maze.getShortestPaths()
        val positionsPartOfShortestPath = paths.flatMap { it }.toSet()

        // val toCsv = Grid.generate(maze.bounds) {
        //     when {
        //         maze.nodes.contains(it) -> maze.distances[it]!!.toString() + if (positionsPartOfShortestPath.contains(it)) " O" else ""
        //         else -> '#'
        //     }
        // }.toCsv()
        // println(toCsv)

        assertThat(positionsPartOfShortestPath.size).isEqualTo(45)
    }

    @Test
    fun `part 2 - example input 2`() {
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
        val paths = maze.getShortestPaths()
        val positionsPartOfShortestPath = paths.flatMap { it }.toSet()

        // val toCsv = Grid.generate(maze.bounds) {
        //     when {
        //         maze.nodes.contains(it) -> maze.distances[it]!!.toString() + if (positionsPartOfShortestPath.contains(it)) " O" else ""
        //         else -> '#'
        //     }
        // }.toCsv()
        // println(toCsv)

        assertThat(positionsPartOfShortestPath.size).isEqualTo(64)
    }

    @Test
    @Disabled("Takes ~10 seconds to run")
    fun `part 2 - puzzle input`() {
        val input = readInput(16)

        val maze = parseMaze(input)
        val paths = maze.getShortestPaths()
        val positionsPartOfShortestPath = paths.flatMap { it }.toSet()

        assertThat(positionsPartOfShortestPath.size).isEqualTo(471)
    }
}

