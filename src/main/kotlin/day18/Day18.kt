package day18

import util.Bounds
import util.Position
import util.findShortestPath
import util.firstUsingBinarySearchIndexed

fun getAmountOfStepsUntilExit(bounds: Bounds, bytes: List<Position>, fallenBytes: Int): Int {
    val shortestPath = findShortestPath(
        start = bounds.topLeft,
        end = bounds.bottomRight,
        bounds = bounds,
        blockedTiles = bytes.take(fallenBytes).toSet()
    )
    return shortestPath!!.size - 1
}

fun getFirstByteThatBlocksPath(bounds: Bounds, bytes: List<Position>): Position {
    return bytes.firstUsingBinarySearchIndexed { (index, _) ->
        findShortestPath(
            start = bounds.topLeft,
            end = bounds.bottomRight,
            bounds = bounds,
            blockedTiles = bytes.take(index + 1).toSet()
        ) == null
    } ?: throw IllegalStateException("No byte found that blocks the path")
}

fun parseBytes(input: String): List<Position> {
    return input.lines().map { line ->
        val (x, y) = line.split(",")
        Position(x.trim().toInt(), y.trim().toInt())
    }
}
