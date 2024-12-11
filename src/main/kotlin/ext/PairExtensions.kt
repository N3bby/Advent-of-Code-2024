package ext

fun <T: Comparable<T>> Pair<T, T>.sort(): Pair<T, T> {
    return if(first > second) {
        Pair(second, first)
    } else {
        this
    }
}

fun <T> Pair<T, T>.toList(): List<T> {
    return listOf(first, second)
}