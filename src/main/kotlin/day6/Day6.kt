package day6

import util.Direction
import util.Grid
import util.Position
import kotlin.streams.asStream

data class Bounds(val width: Int, val height: Int) {
    val positions = sequence {
        for (y in 0 until height) {
            for (x in 0 until width) {
                yield(Position(x, y))
            }
        }
    }
}

data class Guard(
    val position: Position,
    val direction: Direction,
    val visitedPositions: List<Pair<Position, Direction>>
) {
    val nextPosition: Position get() = position + direction.offset

    fun canMoveForward(map: Map): Boolean = !map.isObstructed(nextPosition)

    fun moveForward(map: Map): Guard {
        if (map.isObstructed(nextPosition)) {
            throw IllegalStateException("Cannot move forward, obstruction at $nextPosition")
        }
        return Guard(nextPosition, direction, visitedPositions + (position to direction))
    }

    fun turnRight(): Guard = Guard(position, direction.turnRight(), visitedPositions)
}

data class Map(
    private val obstructions: List<Position>,
    val bounds: Bounds
) {
    fun isInBounds(position: Position): Boolean {
        return position.x in 0 until bounds.width && position.y in 0 until bounds.height
    }

    fun isObstructed(position: Position): Boolean {
        return obstructions.contains(position)
    }

    fun addObstruction(position: Position): Map {
        return Map(obstructions + position, bounds)
    }

    fun toString(guard: Guard, manualObstruction: List<Position> = emptyList()): String {
        val stringBuilder = StringBuilder()

        for (y in bounds.height - 1 downTo 0) {
            for (x in 0 until bounds.width) {
                val position = Position(x, y)
                when {
                    position == guard.position -> stringBuilder.append(guard.direction.toChar())
                    manualObstruction.contains(position) -> stringBuilder.append('O')
                    obstructions.contains(position) -> stringBuilder.append('#')
                    else -> stringBuilder.append('.')
                }
            }
            stringBuilder.append('\n')
        }

        return stringBuilder.toString()
    }
}

fun Guard.move(map: Map): Guard {
    return if (this.canMoveForward(map)) {
        this.moveForward(map)
    } else {
        this.turnRight()
    }
}

fun Guard.moveUntilOutOfBounds(map: Map): Guard {
    var guard = this
    while (map.isInBounds(guard.position)) {
        guard = guard.move(map)
        // println(map.toString(guard))
    }
    return guard
}

fun Guard.runsInLoop(map: Map): Boolean {
    var guard = this;

    val isInLoop: () -> Boolean = {
        guard.visitedPositions.contains(guard.position to guard.direction)
    }

    while (!isInLoop()) {
        guard = guard.move(map)

        if (!map.isInBounds(guard.position)) {
            return false
        }
    }
    return true
}

fun findManualObstructionsThatCauseLoop(map: Map, guard: Guard): Int {
    val positionsToCheck = guard.moveUntilOutOfBounds(map).visitedPositions.map { it.first }.toSet()
    var positionsChecked = 0

    return positionsToCheck.parallelStream().filter { position ->
        positionsChecked++
        if(positionsChecked % 1000 == 0) println("${positionsChecked}/${positionsToCheck.count()}")

        if (map.isObstructed(position) || guard.position == position) {
            false
        } else {
            val manuallyObstructedMap = map.addObstruction(position)
            guard.runsInLoop(manuallyObstructedMap)
        }
    }.count().toInt()
}


fun parseMap(input: String): Pair<Map, Guard> {
    val obstructions = mutableListOf<Position>()
    var guardPosition: Position? = null
    var guardDirection: Direction? = null

    val grid = Grid.fromString(input.lines().reversed().joinToString("\n"))
    grid.positions.forEach { position ->
        when (grid.getAtPosition(position)) {
            '#' -> obstructions.add(position)
            '^' -> {
                guardPosition = position
                guardDirection = Direction.UP
            }

            '<' -> {
                guardPosition = position
                guardDirection = Direction.LEFT
            }

            '>' -> {
                guardPosition = position
                guardDirection = Direction.RIGHT
            }

            'v' -> {
                guardPosition = position
                guardDirection = Direction.DOWN
            }
        }
    }

    val map = Map(obstructions, Bounds(grid.width, grid.height))
    val guard = Guard(guardPosition!!, guardDirection!!, emptyList())

    return map to guard
}