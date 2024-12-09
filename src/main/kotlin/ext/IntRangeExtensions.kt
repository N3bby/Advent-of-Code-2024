package ext

fun IntRange.contains(other: IntRange): Boolean {
    return this.contains(other.first) && this.contains(other.last)
}

fun IntRange.overlaps(other: IntRange): Boolean {
    return this.contains(other.first) || this.contains(other.last) || other.contains(this.first) || other.contains(this.last)
}

fun IntRange.canContain(other: IntRange): Boolean {
    return this.size() >= other.size()
}

fun IntRange.size(): Int {
    return last - first
}
