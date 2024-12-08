package util

fun <T> cartesianProduct(list1: List<T>, list2: List<T>): List<Pair<T, T>> {
    return list1.flatMap { a -> list2.map { b -> a to b } }
}
