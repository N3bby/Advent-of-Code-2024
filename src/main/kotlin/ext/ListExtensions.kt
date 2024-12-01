package ext

fun <T> List<T>.steppedBy(step: Int, offset: Int = 0): List<T> {
    return this.filterIndexed { index, _ -> index % step == offset }
}

fun List<Int>.multiplication(): Int = reduce { acc, number -> acc * number }
fun List<Long>.multiplication(): Long = reduce { acc, number -> acc * number }

fun <T> List<T>.multiplicationOf(block: (T) -> Int): Int = this.map(block).multiplication()

fun <T> List<T>.rotateLeft(n: Int) = drop(n) + take(n)
fun <T> List<T>.rotateRight(n: Int) = takeLast(n) + dropLast(n)

fun <T: Comparable<T>> List<T>.getAllUniqueCombinations(): Set<Pair<T, T>> {
    return this
        .flatMap { outer -> map { inner -> Pair(outer, inner) } }
        .filter { (val1, val2) -> val1 != val2 }
        .map { it.sort() }
        .toSet()
}
