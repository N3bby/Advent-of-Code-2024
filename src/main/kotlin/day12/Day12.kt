package day12

import util.Grid
import util.Position
import util.getContiguousGroups

data class Region(private val positions: Set<Position>, private val grid: Grid<Char>) {
    val priceOfFencing get() = area * perimeter

    private val area get() = positions.size
    private val perimeter
        get() = positions.sumOf { position ->
            val value = grid.getAtPosition(position)
            position.neighbours.count { !grid.isInBounds(it) || grid.getAtPosition(it) != value }
        }
}

fun getTotalPriceOfFencing(grid: Grid<Char>): Int {
    val regions = grid.getContiguousGroups().map{ Region(it, grid) }
    return regions.sumOf { it.priceOfFencing }
}

