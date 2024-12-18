package day17

import day17.InstructionType.*
import day17.ProgramElement.Operand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.readInput

class Day17KtTest {

    @Test
    fun `part 1 - example input`() {
        val input = """
           Register A: 729
           Register B: 0
           Register C: 0

           Program: 0,1,5,4,3,0
        """.trimIndent()

        val computer = parseComputer(input).runUntilHalt()

        assertThat(computer.printOutput()).isEqualTo("4,6,3,5,6,3,5,2,1,0")
    }

    @Test
    fun `instructions should produce the correct results`() {
        // Divides A by 2^combo, writes to A
        assertThat(ADV.execute(Operand(2), computer(8, 0, 0))).isEqualTo(computer(2, 0, 0))

        // XORs B and literal operand, writes to B.
        // e.g. 110 and 010 XOR to 100
        assertThat(BXL.execute(Operand(6), computer(0, 2, 0))).isEqualTo(computer(0, 4, 0))

        // Calculates combo % 8, writes to B
        assertThat(BST.execute(Operand(5), computer(0, 12, 0))).isEqualTo(computer(0, 4, 0))

        // Does nothing when A is 0
        // Jumps to literal when A is not 0
        assertThat(JNZ.execute(Operand(2), computer(0, 0, 0))).isEqualTo(computer(0, 0, 0))
        assertThat(JNZ.execute(Operand(17), computer(1, 0, 0))).isEqualTo(computer(1, 0, 0).copy(instructionPointer = 17))

        // XORs B and C, writes to B
        assertThat(BXC.execute(Operand(2), computer(0, 6, 2))).isEqualTo(computer(0, 4, 2))

        // Calculates combo % 8, writes to output
        assertThat(OUT.execute(Operand(3), computer(0, 0, 0))).isEqualTo(computer(0, 0, 0).copy(output = listOf(3)))

        // Divides A by 2^combo, writes to B
        assertThat(BDV.execute(Operand(2), computer(8, 0, 0))).isEqualTo(computer(8, 2, 0))

        // Divides A by 2^combo, writes to C
        assertThat(CDV.execute(Operand(2), computer(8, 0, 0))).isEqualTo(computer(8, 0, 2))
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(17)

        val computer = parseComputer(input).runUntilHalt()

        assertThat(computer.printOutput()).isEqualTo("1,6,7,4,3,0,5,0,6")

    }
}

fun computer(a: Long, b: Long, c: Long) = Computer(
    program = emptyList(),
    registers = Registers(a, b, c),
    instructionPointer = 0
)

