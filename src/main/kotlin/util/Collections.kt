package util

fun <T> cartesianProduct(list1: List<T>, list2: List<T>): List<Pair<T, T>> {
    return list1.flatMap { a -> list2.map { b -> a to b } }
}

fun integerSequence(): Sequence<Int> {
    return generateSequence(0) { it + 1 }
}

fun <T> MutableList<T>.swap(index1: Int, index2: Int){
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}

/** Swaps the elements from range1 with range2. If range2 contains more elements, they are ignored */
fun <T> MutableList<T>.swap(range1: IntRange, range2: IntRange){
    range1.zip(range2).forEach {
        val temp = this[it.first]
        this[it.first] = this[it.second]
        this[it.second] = temp
    }
}
