package day7

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.readInput

class Day7KtTest {

    val OPERATORS_PART_1 = listOf(
        Operator.PLUS,
        Operator.MULIPLY,
    )

    val OPERATORS_PART_2 = listOf(
        Operator.PLUS,
        Operator.MULIPLY,
        Operator.COMBINE,
    )

    @Test
    fun `part 1 - example input`() {
        val input = """
            190: 10 19
            3267: 81 40 27
            83: 17 5
            156: 15 6
            7290: 6 8 6 15
            161011: 16 10 13
            192: 17 8 14
            21037: 9 7 18 13
            292: 11 6 16 20
        """.trimIndent()

        val equations = parseEquations(input)

        assertThat(equations.totalCalibrationResult(OPERATORS_PART_1)).isEqualTo(3749)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(7)

        val equations = parseEquations(input)

        assertThat(equations.totalCalibrationResult(OPERATORS_PART_1)).isEqualTo(5702958180383)
    }

    @Test
    fun `part 2 - example input`() {
        val input = """
            190: 10 19
            3267: 81 40 27
            83: 17 5
            156: 15 6
            7290: 6 8 6 15
            161011: 16 10 13
            192: 17 8 14
            21037: 9 7 18 13
            292: 11 6 16 20
        """.trimIndent()

        val equations = parseEquations(input)

        assertThat(equations.totalCalibrationResult(OPERATORS_PART_2)).isEqualTo(11387)
    }

    @Test
    fun `part 2 - puzzle input`() {
        val input = readInput(7)

        val equations = parseEquations(input)

        assertThat(equations.totalCalibrationResult(OPERATORS_PART_2)).isEqualTo(92612386119138)
    }
}

