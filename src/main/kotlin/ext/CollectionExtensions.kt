package ext

fun <T, R: Comparable<R>> Collection<T>.multipleMinBy(transform: (T) -> R): List<T> {
    val minimumValue = minOf(transform)
    return filter { transform(it) == minimumValue }
}
