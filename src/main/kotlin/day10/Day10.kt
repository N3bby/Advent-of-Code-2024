package day10

import util.Grid
import util.Position

data class Hike(val reachableSummits: Set<Position>, val paths: List<Set<Position>>) {

    val score: Int get() = reachableSummits.size
    val rating: Int get() = paths.size

    operator fun plus(other: Hike): Hike {
        return Hike(
            reachableSummits = reachableSummits + other.reachableSummits,
            paths = paths + other.paths
        )
    }

    companion object {
        fun empty() = Hike(emptySet(), emptyList())
    }
}

fun findTrailHeads(grid: Grid<Int>): List<Position> {
    return grid.getPositionsWhere { grid.getAtPosition(it) == 0 }
}

fun findReachableSummits(position: Position, grid: Grid<Int>, visited: Set<Position> = emptySet()): Hike {
    if (grid.getAtPosition(position) == 9) return Hike(setOf(position), listOf(visited + position))

    val nextPositions = position.neighbours
        .filter { neighbourPosition -> grid.isInBounds(neighbourPosition) }
        .filter { neighbourPosition -> !visited.contains(neighbourPosition) }
        .filter { neighbourPosition -> grid.getAtPosition(position) + 1 == grid.getAtPosition(neighbourPosition) }

    return nextPositions
        .map { nextPos -> findReachableSummits(nextPos, grid, visited + position) }
        .fold(Hike.empty()) { hike1, hike2 -> hike1 + hike2 }
}
