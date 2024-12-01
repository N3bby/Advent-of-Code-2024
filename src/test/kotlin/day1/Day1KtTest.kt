package day1

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.readInput

class Day1KtTest {

    @Test
    fun `part 1 - example input`() {
        val input = """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3
        """.trimIndent()

        val (list1, list2) = parseLocationIds(input)
        val totalDistance = getTotalDistanceBetweenLists(list1, list2)

        assertThat(totalDistance).isEqualTo(11)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val (list1, list2) = parseLocationIds(readInput(1))
        val totalDistance = getTotalDistanceBetweenLists(list1, list2)

        assertThat(totalDistance).isEqualTo(1660292)
    }

    @Test
    fun `part 2 - example input`() {
        val input = """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3
        """.trimIndent()

        val (list1, list2) = parseLocationIds(input)
        val similarityScore = getSimilarityScore(list1, list2)

        assertThat(similarityScore).isEqualTo(31)
    }

    @Test
    fun `part 2 - puzzle input`() {
        val (list1, list2) = parseLocationIds(readInput(1))
        val similarityScore = getSimilarityScore(list1, list2)

        assertThat(similarityScore).isEqualTo(22776016)
    }

}

