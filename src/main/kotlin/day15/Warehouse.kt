package day15

import util.Bounds
import util.Grid
import util.Position

data class Warehouse(
    private val robot: Position,
    private val boxes: Set<Box>,
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
        val nextPosition = robot + movement.offset
        val boxAtNextPosition = findBoxAt(nextPosition)
        if (boxAtNextPosition != null) {
            val moveableBoxes = boxAtNextPosition.getAffectedBoxes(movement, this)
            val anyBoxMovesIntoWall = moveableBoxes.any { it.movesIntoWall(movement, this) }
            return if (moveableBoxes.isEmpty() || anyBoxMovesIntoWall) {
                this
            } else {
                copy(
                    robot = nextPosition,
                    boxes - moveableBoxes + moveableBoxes.map { it.move(movement) }
                )
            }
        }
        if (isFree(nextPosition)) {
            return copy(robot = nextPosition)
        }
        return this
    }

    fun getGpsCoordinates(): List<Int> {
        return boxes
            .map { it.position }
            .map { (x, y) -> 100 * y + x }
    }

    fun findBoxAt(position: Position): Box? {
        return boxes.find { it.occupiesPosition(position) }
    }

    fun isWall(position: Position): Boolean {
        return walls.contains(position)
    }

    fun isFree(position: Position): Boolean {
        return boxes.none { it.occupiesPosition(position) } && !walls.contains(position)
    }

    fun print() {
        val bounds = Bounds(
            walls.maxOf { position -> position.x } + 1,
            walls.maxOf { position -> position.y } + 1
        )

        val grid = Grid.Companion.generate(bounds) { position ->
            when {
                robot == position -> '@'
                findBoxAt(position) != null -> when {
                    findBoxAt(position) is Box.RegularBox -> 'O'
                    findBoxAt(position) is Box.WideBox && findBoxAt(position)!!.position == position -> '['
                    findBoxAt(position) is Box.WideBox && findBoxAt(position)!!.position != position -> ']'
                    else -> '?'
                }
                isWall(position) -> '#'
                else -> '.'
            }
        }.print()

        println(grid)
        println()
    }
}
