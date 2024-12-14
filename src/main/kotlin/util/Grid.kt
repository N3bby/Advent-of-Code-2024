package util

import kotlin.math.ceil

data class Bounds(val width: Int, val height: Int) {

    val quadrants: List<Region>
        get() = listOf(
            Region(0, width / 2, 0, height / 2),
            Region(0, width / 2, ceil(height/2.0).toInt(), height),
            Region(ceil(width / 2.0).toInt(), width, 0, height / 2),
            Region(ceil(width / 2.0).toInt(), width, ceil(height/2.0).toInt(), height),
        )

    fun contains(position: Position): Boolean {
        return position.x in 0 until width &&
                position.y in 0 until height
    }

    fun loop(position: Position): Position {
        val x = position.x % width
        val y = position.y % height
        return Position(
            if(x < 0) width + x else x,
            if(y < 0) height + y else y
        )
    }
}

data class Region(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int) {
    fun contains(position: Position): Boolean {
        return position.x in minX until maxX &&
                position.y in minY until maxY
    }
}

/**
 * Grid with origin defined in the top left corner
 */
data class Grid<T>(val rows: List<List<T>>) {

    val width = rows[0].size
    val height = rows.size

    val columns: List<List<T>>
        get() {
            return (0..<width).map { column -> rows.map { it[column] } }
        }

    val bounds get() = Bounds(width, height)

    val positions = sequence {
        for (x in 0..<width) {
            for (y in 0..<height) {
                yield(Position(x, y))
            }
        }
    }

    fun isInBounds(position: Position): Boolean = bounds.contains(position)

    fun getAtPosition(position: Position): T {
        return rows[position.y][position.x]
    }

    fun getPositionsWhere(predicate: (position: Position) -> Boolean): List<Position> {
        val matchingCells = mutableListOf<Position>()

        positions.forEach { position ->
            if (predicate(position)) matchingCells.add(position)
        }

        return matchingCells
    }

    fun insertRow(index: Int, row: List<T>): Grid<T> {
        if (row.size != width) throw IllegalArgumentException("Inserted row (width ${row.size}) must be the same width as the grid (width $width)")
        return Grid(rows.take(index) + listOf(row) + rows.drop(index))
    }

    fun insertColumn(index: Int, column: List<T>): Grid<T> {
        if (column.size != height) throw IllegalArgumentException("Inserted column (height ${column.size}) must be the same height as the grid (height $height)")
        return Grid(
            rows.mapIndexed { rowIndex, row ->
                row.take(index) + listOf(column[rowIndex]) + row.drop(index)
            }
        )
    }

    fun print(highlighter: (Position) -> T?): String {
        val stringBuilder = StringBuilder()
        for (y in 0 until height) {
            for (x in 0 until width) {
                val position = Position(x, y)
                val value = getAtPosition(position)
                val highlighted = highlighter(position)
                stringBuilder.append(highlighted ?: value)
            }
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }

    companion object {
        fun fromString(input: String): Grid<Char> {
            val rows = input.lines().map { it.toList() }
            return Grid(rows)
        }

        fun <T> fromString(input: String, transform: (Char) -> T): Grid<T> {
            val rows = input.lines().map { line -> line.toCharArray().map(transform) }
            return Grid(rows)
        }
    }
}

fun Grid<Char>.matchesKernelAtPosition(position: Position, kernel: Grid<Char>, wildcard: Char = '.'): Boolean {
    return kernel.positions.all { kernelPosition ->
        if (kernel.getAtPosition(kernelPosition) == wildcard) {
            true
        } else {
            val positionToCheck = position + kernelPosition.toOffset()
            isInBounds(positionToCheck) && getAtPosition(positionToCheck) == kernel.getAtPosition(kernelPosition)
        }
    }
}

fun <T> Grid<T>.getContiguousGroups(): Set<Set<Position>> {
    val visited: MutableSet<Position> = HashSet(width * height)
    val groups = mutableSetOf<Set<Position>>()

    for (position in positions) {
        if (visited.contains(position)) continue

        val group = getContiguousGroup(position, getAtPosition(position))
        groups.add(group)
        visited.addAll(group)
    }

    return groups
}

fun <T> Grid<T>.getContiguousGroup(
    position: Position,
    value: T,
    visited: MutableSet<Position> = mutableSetOf<Position>(),
): Set<Position> {
    visited.add(position)

    val positionsToVisit = position.neighbours
        .filter { isInBounds(it) }
        .filter { getAtPosition(it) == value }
        .filter { !visited.contains(it) }

    positionsToVisit.forEach { positionToVisit -> getContiguousGroup(positionToVisit, value, visited) }

    return visited
}
