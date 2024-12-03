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
            Instruction.Multiplication("mul(2,4)"),
            Instruction.Multiplication("mul(5,5)"),
            Instruction.Multiplication("mul(11,8)"),
            Instruction.Multiplication("mul(8,5)")
        )

        assertThat(instructions.sum()).isEqualTo(161)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(3)

        val instructions = findValidMultiplicationInstructions(input)

        assertThat(instructions.sum()).isEqualTo(0)
    }

    @Test
    fun `part 2 - example input`() {
        val input = """
            xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
        """.trimIndent()

        val instructions = findValidInstructions(input)

        assertThat(instructions).containsExactly(
            Instruction.Multiplication("mul(2,4)"),
            Instruction.Dont,
            Instruction.Multiplication("mul(5,5)"),
            Instruction.Multiplication("mul(11,8)"),
            Instruction.Do,
            Instruction.Multiplication("mul(8,5)")
        )

        assertThat(instructions.collapseConditionals().sum()).isEqualTo(48)
    }

    @Test
    fun `part 2 - puzzle input`() {
        val input = readInput(3)

        val instructions = findValidInstructions(input)

        assertThat(instructions.collapseConditionals().sum()).isEqualTo(0)
    }
}

