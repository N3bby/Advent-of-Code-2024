package day15

import day15.RobotMovement.entries
import util.Bounds
import util.Direction
import util.Grid
import util.Offset
import util.Position

enum class RobotMovement(val char: Char, val offset: Offset) {
    UP('^', Direction.DOWN.offset),
    DOWN('v', Direction.UP.offset),
    LEFT('<', Direction.LEFT.offset),
    RIGHT('>', Direction.RIGHT.offset);

    companion object {
        fun fromChar(char: Char): RobotMovement {
            return entries.find { it.char == char }
                ?: throw IllegalArgumentException("No movement registered for character '$char'")
        }
    }
}

data class Warehouse(
    private val robot: Position,
    private val obstacles: Set<Position>,
    private val walls: Set<Position>,
) {
    fun executeMovements(movements: List<RobotMovement>, debug: Boolean = false): Warehouse {
        if(debug) {
            println("Initial state")
            print()
        }
        return movements.fold(this) { warehouse, movement ->
            if(debug) println("Move ${movement.char}")
            warehouse.move(movement).also { if(debug) it.print() }
        }
    }

    private fun move(movement: RobotMovement): Warehouse {
        if(movementHitsWall(robot, movement)) {
            return this
        } else {
            val obstaclesToMove = getObstaclesInLineWithMovement(robot, movement)
            return copy(
                robot = robot + movement.offset,
                obstacles = obstacles - obstaclesToMove + obstaclesToMove.map { obstacle -> obstacle + movement.offset }
            )
        }

    }

    private fun getObstaclesInLineWithMovement(position: Position, movement: RobotMovement): Set<Position> {
        val nextPosition = position + movement.offset
        return if(obstacles.contains(nextPosition)) {
            setOf(nextPosition) + getObstaclesInLineWithMovement(nextPosition, movement)
        } else {
            emptySet()
        }
    }

    private fun movementHitsWall(position: Position, movement: RobotMovement): Boolean {
        val nextPosition = position + movement.offset
        return if(walls.contains(nextPosition)) {
            true
        } else if(obstacles.contains(nextPosition)) {
            movementHitsWall(nextPosition, movement)
        } else {
            false
        }
    }

    fun getGpsCoordinates(): List<Int> {
        return obstacles.map { (x, y) -> 100 * y + x }
    }

    fun print() {
        val bounds = Bounds(
            walls.maxOf { position -> position.x } + 1,
            walls.maxOf { position -> position.y } + 1
        )

        val grid = Grid.generate(bounds) { position ->
            when {
                robot == position -> '@'
                obstacles.contains(position) -> 'O'
                walls.contains(position) -> '#'
                else -> '.'
            }
        }.print()

        println(grid)
        println()
    }
}

fun parseWarehouseAndMovements(input: String): Pair<Warehouse, List<RobotMovement>> {
    val (warehouseString, movementString) = input.split("\n\n")
    return parseWarehouse(warehouseString) to parseMovement(movementString)
}

fun parseWarehouse(input: String): Warehouse {
    var robot: Position? = null
    val obstacles = mutableSetOf<Position>()
    val walls = mutableSetOf<Position>()

    val grid = Grid.fromString(input)
    grid
        .positions
        .forEach { position ->
            when (grid.getAtPosition(position)) {
                '#' -> walls.add(position)
                'O' -> obstacles.add(position)
                '@' -> robot = position
            }
        }

    return Warehouse(robot!!, obstacles, walls)
}

fun parseMovement(input: String): List<RobotMovement> {
    return input.lines().joinToString("").map { RobotMovement.fromChar(it) }
}
