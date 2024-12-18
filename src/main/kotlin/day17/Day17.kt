package day17

import day17.InstructionType.entries
import day17.ProgramElement.Instruction
import day17.ProgramElement.Operand
import kotlin.math.pow


sealed class ProgramElement {

    data class Operand(private val value: Long) : ProgramElement() {

        fun getComboValue(computer: Computer): Long = when (value) {
            in 0L..3L -> value
            4L -> computer.registers.a
            5L -> computer.registers.b
            6L -> computer.registers.c
            7L -> throw IllegalStateException("Reserved operand: 7")
            else -> throw IllegalStateException("Unknown operand: $value")
        }

        fun getLiteralValue(): Long = value

    }

    data class Instruction(private val type: InstructionType) : ProgramElement() {

        fun execute(operand: Operand, computer: Computer): Computer {
            val updatedComputer = type.execute(operand, computer)
            return if (type == InstructionType.JNZ && updatedComputer.instructionPointer != computer.instructionPointer) {
                updatedComputer
            } else {
                updatedComputer.copy(instructionPointer = updatedComputer.instructionPointer + 2)
            }
        }

    }

}

enum class InstructionType(val opcode: Int, val execute: (Operand, Computer) -> Computer) {
    ADV(0, { operand, computer ->
        val operandValue = operand.getComboValue(computer)
        val aValue = computer.registers.a

        val result = aValue / 2.0.pow(operandValue.toDouble())

        computer.updateRegisterA(result.toLong())
    }),
    BXL(1, { operand, computer ->
        val operandValue = operand.getLiteralValue()
        val bValue = computer.registers.b

        val result = bValue.xor(operandValue)

        computer.updateRegisterB(result)
    }),
    BST(2, { operand, computer ->
        val operandValue = operand.getComboValue(computer)

        val result = operandValue % 8

        computer.updateRegisterB(result)
    }),
    JNZ(3, { operand, computer ->
        val operandValue = operand.getLiteralValue()
        val aValue = computer.registers.a

        if(aValue != 0L) {
            computer.copy(instructionPointer = operandValue.toInt())
        } else {
            computer
        }
    }),
    BXC(4, { operand, computer ->
        val bValue = computer.registers.b
        val cValue = computer.registers.c

        val result = bValue.xor(cValue)

        computer.updateRegisterB(result)
    }),
    OUT(5, { operand, computer ->
        val operandValue = operand.getComboValue(computer)

        val result = operandValue % 8

        computer.writeOutput(result)
    }),
    BDV(6, { operand, computer ->
        val operandValue = operand.getComboValue(computer)
        val aValue = computer.registers.a

        val result = aValue / 2.0.pow(operandValue.toDouble())

        computer.updateRegisterB(result.toLong())
    }),
    CDV(7, { operand, computer ->
        val operandValue = operand.getComboValue(computer)
        val aValue = computer.registers.a

        val result = aValue / 2.0.pow(operandValue.toDouble())

        computer.updateRegisterC(result.toLong())
    });

    companion object {
        fun fromOpcode(opcode: Int) =
            entries.find { it.opcode == opcode } ?: throw IllegalArgumentException("Invalid opcode: $opcode")
    }
}

data class Registers(val a: Long, val b: Long, val c: Long)

data class Computer(
    private val program: List<ProgramElement>,
    val registers: Registers,
    val instructionPointer: Int = 0,
    private val output: List<Long> = emptyList(),
) {

    fun runUntilHalt(): Computer {
        var computer = this
        while (computer.instructionPointer < program.size) {
            val instruction = program[computer.instructionPointer] as Instruction
            val operand = program[computer.instructionPointer + 1] as Operand

            computer = instruction.execute(operand, computer)
        }
        return computer
    }

    fun updateRegisters(registers: Registers): Computer = copy(registers = registers)
    fun updateRegisterA(value: Long) = updateRegisters(registers.copy(a = value))
    fun updateRegisterB(value: Long) = updateRegisters(registers.copy(b = value))
    fun updateRegisterC(value: Long) = updateRegisters(registers.copy(c = value))

    fun writeOutput(value: Long) = copy(output = output + value)
    fun printOutput(): String = output.joinToString(",")

    companion object

}

fun parseComputer(input: String): Computer {
    val (registersStr, programStr) = input.split("\n\n")

    val (a, b, c) = registersStr.lines().map { it.split(":").last().trim().toLong() }

    val program = mutableListOf<ProgramElement>()
    for ((index, value) in programStr.split(":").last().trim().split(",").withIndex()) {
        if (index % 2 == 0) {
            program.add(Instruction(InstructionType.fromOpcode(value.toInt())))
        } else {
            program.add(Operand(value.toLong()))
        }
    }

    return Computer(program, Registers(a, b, c))
}
