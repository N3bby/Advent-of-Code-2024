package util

import java.lang.IllegalArgumentException
import kotlin.math.*

data class Position(val x: Int, val y: Int) : Comparable<Position> {
    operator fun plus(offset: Offset): Position {
        return Position(x + offset.x, y + offset.y)
    }

    operator fun minus(offset: Offset): Position {
        return Position(x - offset.x, y - offset.y)
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

    fun toOffset(): Offset {
        return Offset(x, y)
    }

    override fun compareTo(other: Position): Int {
        val xDiff = x - other.x
        val yDiff = y - other.y
        if (xDiff != 0) return xDiff
        if (yDiff != 0) return yDiff
        return 0
    }
}

data class Line(val pos1: Position, val pos2: Position) {

    val manhattanDistance: Int get() = pos1.manhattanDistanceFrom(pos2)

    fun crossesX(x: Int): Boolean {
        return x in (min(pos1.x, pos2.x)..max(pos1.x, pos2.x))
    }

    fun crossesY(y: Int): Boolean {
        return y in (min(pos1.y, pos2.y)..max(pos1.y, pos2.y))
    }

}

data class Offset(val x: Int, val y: Int)

enum class Direction(val offset: Offset) {
    RIGHT(Offset(1, 0)),
    RIGHT_UP(Offset(1, 1)),
    UP(Offset(0, 1)),
    LEFT_UP(Offset(-1, 1)),
    LEFT(Offset(-1, 0)),
    LEFT_DOWN(Offset(-1, -1)),
    DOWN(Offset(0, -1)),
    RIGHT_DOWN(Offset(1, -1));

    fun turnRight(): Direction = when (this) {
        RIGHT -> DOWN
        DOWN -> LEFT
        LEFT -> UP
        UP -> RIGHT
        else -> throw IllegalArgumentException("Cannot rotate right from $this")
    }

    fun toChar(): Char {
        return when (this) {
            RIGHT -> '>'
            UP -> '^'
            LEFT -> '<'
            DOWN -> 'v'
            else -> throw IllegalArgumentException("No char for $this")
        }
    }
}
