package day12

import util.Grid
import util.Offset
import util.Position
import util.getContiguousGroups
import kotlin.math.abs

data class Region(private val positions: Set<Position>, private val grid: Grid<Char>) {

    fun priceOfFencing(): Int {
        return area * perimeter
    }

    fun priceOfFencingDiscounted(): Int {
        return area * sides
    }

    val valueInGrid: Char get() = grid.getAtPosition(positions.first())

    private val area: Int get() = positions.size

    private val perimeter: Int
        get() {
            return positions.sumOf { position ->
                val value = grid.getAtPosition(position)
                position.neighbours.count { !grid.isInBounds(it) || grid.getAtPosition(it) != value }
            }
        }

    val sides: Int
        get() {
            val corners = positions.flatMap { getCornersAtPosition(it, positions) }.toSet()
            return corners.size
        }
}

typealias Corner = Pair<Set<Position>, Set<Position>>

fun getCornersAtPosition(position: Position, positions: Set<Position>): Set<Corner> {
    return position.sectors.mapNotNull { sector ->
        val filledSpacesInSector = sector.filter { positions.contains(it) }
        val emptySpacesInSector = sector.filter { !positions.contains(it) }
        if(emptySpacesInSector.size == 2) {
            val offsetBetweenEmptySpaces = (emptySpacesInSector[0] - emptySpacesInSector[1]).abs()
            if (offsetBetweenEmptySpaces == Offset(1, 1)) {
                setOf(position) to emptySpacesInSector.toSet()
            } else {
                null
            }
        } else if(emptySpacesInSector.size == 1 || emptySpacesInSector.size == 3) {
            filledSpacesInSector.toSet() to emptySpacesInSector.toSet()
        } else {
            null
        }
    }.toSet()
}

fun getTotalPriceOfFencing(grid: Grid<Char>, pricingFunction: Region.() -> Int): Int {
    val regions = grid.getContiguousGroups().map { Region(it, grid) }
    return regions.sumOf { pricingFunction(it) }
}

