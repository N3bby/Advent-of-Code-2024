package day6

import util.Direction
import util.Grid
import util.Position

data class Bounds(val width: Int, val height: Int)

data class Guard(
    val position: Position,
    val direction: Direction,
    val visitedPositions: List<Pair<Position, Direction>>
) {
    private val nextPosition: Position get() = position + direction.offset

    private fun canMoveForward(map: Map): Boolean = !map.isObstructed(nextPosition)

    private fun moveForward(map: Map): Guard {
        if (map.isObstructed(nextPosition)) {
            throw IllegalStateException("Cannot move forward, obstruction at $nextPosition")
        }
        return Guard(nextPosition, direction, visitedPositions + (position to direction))
    }

    private fun turnRight(): Guard = Guard(position, direction.turnRight(), visitedPositions)

    fun move(map: Map): Guard {
        return if (this.canMoveForward(map)) {
            this.moveForward(map)
        } else {
            this.turnRight()
        }
    }

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

    fun withObstruction(position: Position): Map {
        return Map(obstructions + position, bounds)
    }

    fun toString(guard: Guard, manualObstructions: List<Position> = emptyList()): String {
        val stringBuilder = StringBuilder()

        for (y in bounds.height - 1 downTo 0) {
            for (x in 0 until bounds.width) {
                val position = Position(x, y)
                when {
                    position == guard.position -> stringBuilder.append(guard.direction.toChar())
                    manualObstructions.contains(position) -> stringBuilder.append('O')
                    obstructions.contains(position) -> stringBuilder.append('#')
                    else -> stringBuilder.append('.')
                }
            }
            stringBuilder.append('\n')
        }

        return stringBuilder.toString()
    }
}

fun Guard.moveUntilOutOfBounds(map: Map): Guard {
    var guard = this
    while (map.isInBounds(guard.position)) {
        guard = guard.move(map)
    }
    return guard
}

fun Guard.isRunningInLoop(map: Map): Boolean {
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

fun findDistinctVisitedPositions(map: Map, guard: Guard): Int {
    return guard.moveUntilOutOfBounds(map).visitedPositions.map { it.first }.toSet().size
}

fun findManualObstructionsThatCauseLoop(map: Map, guard: Guard): Int {
    val positionsToCheck = guard.moveUntilOutOfBounds(map).visitedPositions.map { it.first }.toSet()
    var positionsChecked = 0

    return positionsToCheck.parallelStream()
        .peek {
            positionsChecked++
            if (positionsChecked % 1000 == 0) println("${positionsChecked}/${positionsToCheck.count()}")
        }
        .filter { position ->
            when {
                map.isObstructed(position) || position == guard.position -> false
                else -> guard.isRunningInLoop(map.withObstruction(position))
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