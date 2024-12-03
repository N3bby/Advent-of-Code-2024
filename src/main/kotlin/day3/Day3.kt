package day3

sealed class Instruction {
    data object Do : Instruction() {
        override fun toString(): String = "do()"
    }

    data object Dont : Instruction() {
        override fun toString(): String = "don't()"
    }

    data class Multiplication(private val a: Int, private val b: Int) : Instruction() {
        override fun toString(): String = "mul($a,$b)"

        fun execute(): Int {
            return a * b
        }

        companion object {
            fun fromMatchResult(instruction: MatchResult): Multiplication {
                val (a, b) = instruction.destructured
                return Multiplication(a.toInt(), b.toInt())
            }
        }
    }

    companion object {
        fun fromMatchResult(instruction: MatchResult): Instruction = when {
            instruction.value == "do()" -> Do
            instruction.value == "don't()" -> Dont
            instruction.value.startsWith("mul(") -> Multiplication.fromMatchResult(instruction)
            else -> throw IllegalArgumentException("Unsupported instruction: $instruction")
        }
    }
}

fun findValidMultiplicationInstructions(memory: String): List<Instruction.Multiplication> {
    val matches = Regex("""mul\((\d{1,3}),(\d{1,3})\)""").findAll(memory).toList()
    return matches.map { Instruction.Multiplication.fromMatchResult(it) }
}

fun findValidInstructions(memory: String): List<Instruction> {
    val matches = Regex("""mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)""").findAll(memory).toList()
    return matches.map { Instruction.fromMatchResult(it) }
}

fun List<Instruction.Multiplication>.execute(): Int = sumOf { it.execute() }

fun List<Instruction>.collapseConditionals(): List<Instruction.Multiplication> {
    var enabled = true
    val activeInstructions = mutableListOf<Instruction.Multiplication>()
    forEach { instruction ->
        when (instruction) {
            is Instruction.Do -> enabled = true
            is Instruction.Dont -> enabled = false
            is Instruction.Multiplication -> if (enabled) activeInstructions.add(instruction)
        }
    }
    return activeInstructions
}
