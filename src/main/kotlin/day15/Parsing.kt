package day15

import util.Grid
import util.Position

fun parseWarehouseAndMovements(input: String, expandWarehouse: Boolean = false): Pair<Warehouse, List<RobotMovement>> {
    var (warehouseString, movementString) = input.split("\n\n")
    if(expandWarehouse) warehouseString = expandWarehouse(warehouseString)

    return parseWarehouse(warehouseString, expandWarehouse) to parseMovement(movementString)
}

fun expandWarehouse(input: String): String {
    return input
        .replace("#", "##")
        .replace(".", "..")
        .replace("O", "O.")
        .replace("@", "@.")
}

fun parseWarehouse(input: String, expandWarehouse: Boolean): Warehouse {
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

    return Warehouse(robot!!, obstacles.map {
        if(expandWarehouse) Box.WideBox(it) else Box.RegularBox(it)
    }.toSet(), walls)
}

fun parseMovement(input: String): List<RobotMovement> {
    return input.lines().joinToString("").map { RobotMovement.fromChar(it) }
}
