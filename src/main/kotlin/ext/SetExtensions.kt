package ext

fun <T> Set<Set<T>>.intersect(): Set<T> {
    return this.reduce { acc, set -> acc.intersect(set) }
}
