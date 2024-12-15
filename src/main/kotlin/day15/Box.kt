package day15

import util.Offset
import util.Position

sealed class Box(val position: Position) {

    abstract fun getAffectedBoxes(movement: RobotMovement, warehouse: Warehouse): Set<Box>
    abstract fun occupiesPosition(position: Position): Boolean
    abstract fun move(movement: RobotMovement): Box
    abstract fun movesIntoWall(movement: RobotMovement, warehouse: Warehouse): Boolean

    class RegularBox(position: Position) : Box(position) {
        override fun getAffectedBoxes(movement: RobotMovement, warehouse: Warehouse): Set<Box> {
            val nextPosition = position + movement.offset

            val nextBox = warehouse.findBoxAt(nextPosition)
            return if (nextBox == null) {
                setOf(this)
            } else {
                setOf(this) + nextBox.getAffectedBoxes(movement, warehouse)
            }
        }

        override fun occupiesPosition(position: Position): Boolean {
            return this.position == position
        }

        override fun move(movement: RobotMovement): Box {
            return RegularBox(position + movement.offset)
        }

        override fun movesIntoWall(movement: RobotMovement, warehouse: Warehouse): Boolean {
            return warehouse.isWall(position + movement.offset)
        }
    }

    class WideBox(position: Position) : Box(position) {
        val positions
            get() = listOf(
                position,
                position + Offset(1, 0)
            )

        override fun getAffectedBoxes(movement: RobotMovement, warehouse: Warehouse): Set<Box> {
            val nextBoxes = positions.mapNotNull { warehouse.findBoxAt(it + movement.offset) }.filter { it != this }
            return if(nextBoxes.isEmpty()) {
                setOf(this)
            } else {
                setOf(this) + nextBoxes.flatMap { it.getAffectedBoxes(movement, warehouse) }
            }
        }

        fun getPotentiallyMovableBoxes(movement: RobotMovement, warehouse: Warehouse): Set<WideBox> {
            return positions
                .mapNotNull { warehouse.findBoxAt(it + movement.offset) }
                .filter { it != this }
                .flatMap { box ->
                    (box as WideBox).getPotentiallyMovableBoxes(movement, warehouse)
                }.toSet()
        }

        override fun occupiesPosition(position: Position): Boolean {
            return positions.contains(position)
        }

        override fun move(movement: RobotMovement): Box {
            return WideBox(position + movement.offset)
        }

        override fun movesIntoWall(movement: RobotMovement, warehouse: Warehouse): Boolean {
            return positions.any { warehouse.isWall(it + movement.offset) }
        }
    }

}
