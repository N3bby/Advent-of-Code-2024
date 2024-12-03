package day3

data class MultiplicationInstruction(private val instruction: String) {
    override fun toString(): String = instruction

    private val operands by lazy {
        Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
            .find(instruction)!!.groupValues
            .let { Pair(it[1].toInt(), it[2].toInt()) }
    }

    fun execute(): Int {
        val (a, b) = operands
        return a * b
    }
}

fun findValidMultiplicationInstructions(memory: String): List<MultiplicationInstruction> {
    val matches = Regex("""mul\(\d{1,3},\d{1,3}\)""").findAll(memory).toList()
    return matches.map { MultiplicationInstruction(it.value) }
}

fun List<MultiplicationInstruction>.sum(): Int = sumOf { it.execute() }