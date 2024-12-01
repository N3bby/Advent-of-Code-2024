package ext

fun LongRange.contains(other: LongRange): Boolean {
    return other.first in this && other.last in this
}

fun LongRange.overlaps(other: LongRange): Boolean {
    return other.first in this || other.last in this || first in other || last in other
}

fun LongRange.intersect(other: LongRange): LongRange? {
    if (!overlaps(other)) return null
    if (contains(other)) return other
    if (other.contains(this)) return this

    val overlapsLowerEnd = other.first < first && other.last in this
    return if (overlapsLowerEnd) {
        first..other.last
    } else {
        other.first..last
    }
}

fun LongRange.distance(other: LongRange): List<LongRange> {
    if (contains(other)) return emptyList()
    if (!overlaps(other)) return listOf(other)

    if (other.contains(this)) {
        return if (other.last == last) {
            listOf(other.first..<first)
        } else if (other.first == first) {
            listOf(last + 1..other.last)
        } else {
            listOf(other.first..<first, last + 1..other.last)
        }
    }

    val overlapsLowerEnd = other.first < first && other.last in this
    return if (overlapsLowerEnd) {
        listOf(other.first..<first)
    } else {
        listOf(last + 1..other.last)
    }
}
