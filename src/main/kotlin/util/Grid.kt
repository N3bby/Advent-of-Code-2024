package util

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

    fun getAtPosition(position: Position): T {
        return rows[position.y][position.x]
    }

    fun getPositionsWhere(predicate: (cell: T) -> Boolean): List<Position> {
        val matchingCells = mutableListOf<Position>()

        for (x in 0..<width) {
            for (y in 0..<height) {
                val position = Position(x, y)
                if (predicate(getAtPosition(position))) matchingCells.add(position)
            }
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
}
