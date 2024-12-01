package util

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Position(val x: Int, val y: Int): Comparable<Position> {
    operator fun plus(offset: Offset): Position {
        return Position(x + offset.x, y + offset.y)
    }

    operator fun minus(position: Position): Offset {
        return Offset(x - position.x, y - position.y)
    }

    val neighbours: List<Position>
        get() {
            return listOf(
                this + Offset(1, 0),
                this + Offset(-1, 0),
                this + Offset(0, 1),
                this + Offset(0, -1),
            )
        }

    fun manhattanDistanceFrom(other: Position): Int {
        return abs(other.x - x) + abs(other.y - y)
    }

    override fun compareTo(other: Position): Int {
        val xDiff = x - other.x
        val yDiff = y - other.y
        if(xDiff != 0) return xDiff
        if(yDiff != 0) return yDiff
        return 0
    }
}

data class Line(val pos1: Position, val pos2: Position) {

    val manhattanDistance: Int get() = pos1.manhattanDistanceFrom(pos2)

    fun crossesX(x: Int): Boolean {
        return x in (min(pos1.x, pos2.x) .. max(pos1.x, pos2.x))
    }

    fun crossesY(y: Int): Boolean {
        return y in (min(pos1.y, pos2.y) .. max(pos1.y, pos2.y))
    }

}

data class Offset(val x: Int, val y: Int)
