package day7

enum class Operator(
    val operateInverse: (Long, Long) -> Long,
    val canOperateInverse: (Long, Long) -> Boolean,
) {
    PLUS(Long::minus, { x, y -> true }),
    MULIPLY(Long::div, { x, y -> x % y == 0L }),
    COMBINE(
        { result, number -> result.toString().removeSuffix(number.toString()).toLong() },
        { result, number -> result != number && result.toString().endsWith(number.toString()) }
    ),
}

data class Equation(val result: Long, val numbers: List<Long>) {
    fun isValid(operators: List<Operator>): Boolean {
        return canProduce(operators, result, numbers)
    }

    private fun canProduce(operators: List<Operator>, result: Long, numbers: List<Long>): Boolean {
        if (numbers.size == 1) {
            return numbers.first() == result
        }
        return operators
            .filter { operator -> operator.canOperateInverse(result, numbers.last()) }
            .any { operator ->
                canProduce(
                    operators = operators,
                    result = operator.operateInverse(result, numbers.last()),
                    numbers = numbers.dropLast(1)
                )
            }
    }
}

fun List<Equation>.totalCalibrationResult(operators: List<Operator>) =
    parallelStream()
        .filter { t -> t.isValid(operators) }
        .mapToLong(Equation::result)
        .sum()

fun parseEquations(input: String): List<Equation> {
    return input.lines().map { line ->
        val (result, numbersStr) = line.split(':')
        val numbers = numbersStr.trim().split(' ').map { it.toLong() }
        Equation(result.toLong(), numbers)
    }
}
