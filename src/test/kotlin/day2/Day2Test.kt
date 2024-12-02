package day2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.readInput

class Day2KtTest {

    @Test
    fun `part 1 - example input`() {
        val input = """
            7 6 4 2 1
            1 2 7 8 9
            9 7 6 2 1
            1 3 2 4 5
            8 6 4 4 1
            1 3 6 7 9
        """.trimIndent()

        val reports = parseReports(input.lines())
        val safeReports = countSafeReports(reports)

        assertThat(safeReports).isEqualTo(2)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(2)

        val reports = parseReports(input.lines())
        val safeReports = countSafeReports(reports)

        assertThat(safeReports).isEqualTo(411)
    }

    @Test
    fun `part 2 - example input`() {
        val input = """
            7 6 4 2 1
            1 2 7 8 9
            9 7 6 2 1
            1 3 2 4 5
            8 6 4 4 1
            1 3 6 7 9
        """.trimIndent()

        val reports = parseReports(input.lines())
        val safeReports = countSafeReportsWithDampener(reports)

        assertThat(safeReports).isEqualTo(4)
    }

    @Test
    fun `part 2 - puzzle input`() {
        val input = readInput(2)

        val reports = parseReports(input.lines())
        val safeReports = countSafeReportsWithDampener(reports)

        assertThat(safeReports).isEqualTo(465)
    }
}

