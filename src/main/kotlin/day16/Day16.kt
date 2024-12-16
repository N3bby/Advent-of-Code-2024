package day16

import util.Bounds
import util.Grid
import util.Offset
import util.Position
import kotlin.math.min

data class Maze(
    val bounds: Bounds,
    private val walkable: Set<Position>,
    private val start: Position,
    private val end: Position,
) {
    val nodes by lazy { walkable + start + end }

    fun getLowestScore(): Int {
        val distances = mutableMapOf<Position, Int>()
        nodes.forEach { distances[it] = Int.MAX_VALUE }
        distances[start] = 0

        val unvisited = nodes.toMutableSet()
        while (true) {
            if (unvisited.isEmpty() || unvisited.all { distances[it] == Int.MAX_VALUE }) break

            val currentNode = unvisited.minBy { position -> distances[position]!! }
            val visitedNeighbour = currentNode.neighbours
                .filter { nodes.contains(it) }
                .find { !unvisited.contains(it) }
            val currentDirection = if(visitedNeighbour != null) currentNode - visitedNeighbour else Offset(1, 0)

            currentNode.neighbours
                .filter { unvisited.contains(it) }
                .forEach { neighbour ->
                    val directionToNeighbour = neighbour - currentNode
                    val distanceFromCurrent = if(currentDirection != directionToNeighbour) 1001 else 1
                    distances[neighbour] = min(
                        distances[neighbour]!!,
                        distances[currentNode]!! + distanceFromCurrent
                    )
                }

            unvisited.remove(currentNode)
        }

        return distances[end]!!
    }
}

fun parseMaze(string: String): Maze {
    val walkable = mutableSetOf<Position>()
    var start: Position? = null
    var end: Position? = null

    val grid = Grid.fromString(string)
    grid.positions.forEach { position ->
        when (grid.getAtPosition(position)) {
            '.' -> walkable.add(position)
            'S' -> start = position
            'E' -> end = position
        }
    }

    return Maze(grid.bounds, walkable, start!!, end!!)
}
