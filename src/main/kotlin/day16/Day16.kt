package day16

import ext.accumulationMap
import util.*
import kotlin.math.min

fun Path.getScore(): Int {
    var direction = Direction.RIGHT.offset
    var score = 0

    zipWithNext().map { (current, next) ->
        var directionToNext = next - current
        if (directionToNext != direction) {
            score += 1001
        } else {
            score += 1
        }
        direction = directionToNext
    }

    return score
}

data class Maze(
    val bounds: Bounds,
    private val walkable: Set<Position>,
    private val start: Position,
    private val end: Position,
) {
    val nodes by lazy { walkable + start + end }
    val distances by lazy { computeDistancesFromStart() }
    val shortestPath by lazy { getShortestPath()!! }

    fun getLowestScore(): Int {
        return distances[end]!!
    }

    fun getShortestPaths(): Set<Path> {
        val pathsChecked = mutableSetOf<Path>()
        var pathsToCheck = mutableSetOf<Path>(shortestPath)

        while (pathsToCheck.isNotEmpty()) {
            val optimalPaths: Set<Path> = pathsToCheck.flatMap {
                it.accumulationMap { pathSoFar ->
                    pathSoFar.last().neighbours
                        .filter { nodes.contains(it) }
                        .filter { !shortestPath.contains(it) }
                        .mapNotNull { getShortestPath(pathSoFar + it) }
                        .filter { it.reversed().getScore() == getLowestScore() }
                }.flatMap { it }
            }.toSet()

            pathsChecked.addAll(pathsToCheck)
            pathsToCheck = (optimalPaths - pathsChecked).toMutableSet()
        }

        return pathsChecked
    }

    // Gets "a" shortest path
    fun getShortestPath(visited: Path = listOf(end)): Path? {
        if (visited.last() == start) return visited

        val neighbourClosestToStart = visited.last().neighbours
            .filter { nodes.contains(it) }
            .filter { !visited.contains(it) }
            .minByOrNull { distances[it]!! }

        if (neighbourClosestToStart == null) return null

        return getShortestPath(visited + neighbourClosestToStart)
    }

    // Dijkstra: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Algorithm
    fun computeDistancesFromStart(): Map<Position, Int> {
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
            val currentDirection = if (visitedNeighbour != null) currentNode - visitedNeighbour else Offset(1, 0)

            currentNode.neighbours
                .filter { unvisited.contains(it) }
                .forEach { neighbour ->
                    val directionToNeighbour = neighbour - currentNode
                    val distanceFromCurrent = if (currentDirection != directionToNeighbour) 1001 else 1
                    distances[neighbour] = min(
                        distances[neighbour]!!,
                        distances[currentNode]!! + distanceFromCurrent
                    )
                }

            unvisited.remove(currentNode)
        }

        return distances
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
