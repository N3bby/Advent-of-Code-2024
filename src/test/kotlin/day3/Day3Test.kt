package day3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.readInput

class Day3KtTest {

    @Test
    fun `part 1 - example input`() {
        val input = """
            xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
        """.trimIndent()

        val instructions = findValidMultiplicationInstructions(input)

        assertThat(instructions).containsExactly(
            MultiplicationInstruction("mul(2,4)"),
            MultiplicationInstruction("mul(5,5)"),
            MultiplicationInstruction("mul(11,8)"),
            MultiplicationInstruction("mul(8,5)")
        )

        assertThat(instructions.sum()).isEqualTo(161)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(3)

        val instructions = findValidMultiplicationInstructions(input)

        assertThat(instructions.sum()).isEqualTo(0)
    }
}

