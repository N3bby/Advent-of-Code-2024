package ext

fun <T: Comparable<T>> Pair<T, T>.sort(): Pair<T, T> {
    return if(first > second) {
        Pair(second, first)
    } else {
        this
    }
}
