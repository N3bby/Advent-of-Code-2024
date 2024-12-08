package util

data class Bounds(val width: Int, val height: Int) {
    fun contains(position: Position): Boolean {
        return position.x in 0 until width &&
                position.y in 0 until height
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

    companion object {
        fun fromString(input: String): Grid<Char> {
            val rows = input.lines().map { it.toList() }
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

