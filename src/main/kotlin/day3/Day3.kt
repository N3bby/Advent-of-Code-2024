package day3

sealed class Instruction {
    data object Do : Instruction()

    data object Dont : Instruction()

    data class Multiplication(private val instruction: String): Instruction() {
        override fun toString(): String = instruction

        private val operands by lazy {
            Regex("""mul\((\d+),(\d+)\)""")
                .find(instruction)!!.groupValues
                .let { Pair(it[1].toInt(), it[2].toInt()) }
        }

        fun execute(): Int {
            val (a, b) = operands
            return a * b
        }
    }

    companion object {
        fun buildInstruction(instruction: String): Instruction = when {
            instruction.startsWith("do(") -> Do
            instruction.startsWith("don't(") -> Dont
            instruction.startsWith("mul(") -> Multiplication(instruction)
            else -> throw IllegalArgumentException("Unsupported instruction: $instruction")
        }
    }
}


fun findValidMultiplicationInstructions(memory: String): List<Instruction.Multiplication> {
    val matches = Regex("""mul\(\d{1,3},\d{1,3}\)""").findAll(memory).toList()
    return matches.map { Instruction.Multiplication(it.value) }
}

fun findValidInstructions(memory: String): List<Instruction> {
    val matches = Regex("""don't\(\)|do\(\)|mul\(\d{1,3},\d{1,3}\)""").findAll(memory).toList()
    return matches.map { Instruction.buildInstruction(it.value) }
}

fun List<Instruction.Multiplication>.sum(): Int = sumOf { it.execute() }

fun List<Instruction>.collapseConditionals(): List<Instruction.Multiplication> {
    var enabled = true
    val activeInstructions = mutableListOf<Instruction.Multiplication>()
    forEach { instruction -> when (instruction) {
        is Instruction.Do -> enabled = true
        is Instruction.Dont -> enabled = false
        is Instruction.Multiplication -> if (enabled) activeInstructions.add(instruction)
    } }
    return activeInstructions
}