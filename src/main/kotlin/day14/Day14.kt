package day14

import ext.multiplication
import util.Bounds
import util.Offset
import util.Position

data class Robot(val position: Position, val velocity: Offset) {
    fun move(bounds: Bounds): Robot {
        return Robot(
            bounds.loop(position + velocity),
            velocity
        )
    }
}

fun List<Robot>.move(seconds: Int, bounds: Bounds): List<Robot> {
    return (0 until seconds)
        .fold(this) { robots, _ ->
            robots.map { robot -> robot.move(bounds) }
        }
}

fun List<Robot>.getSafetyFactor(bounds: Bounds): Int {
    return bounds.quadrants.map { quadrant ->
        count { robot -> quadrant.contains(robot.position) }
    }.multiplication()
}

fun List<Robot>.print(bounds: Bounds) {
    val stringBuilder = StringBuilder()
    for (y in 0 until bounds.height) {
        for (x in 0 until bounds.width) {
            val robotsHere = count { it.position == Position(x, y) }
            stringBuilder.append(if (robotsHere > 0) robotsHere else '.')
        }
        stringBuilder.append('\n')
    }
    println(stringBuilder.toString())
}

fun parseRobots(input: String): List<Robot> {
    val regex = Regex("""p=(\d+),(\d+) v=(-?\d+),(-?\d+)""")
    return input.lines()
        .map { line ->
            val (posX, posY, velX, velY) = regex.matchEntire(line)!!.destructured
            Robot(
                Position(posX.toInt(), posY.toInt()),
                Offset(velX.toInt(), velY.toInt())
            )
        }
}
