package day14

import ext.multiplication
import util.*
import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.File
import javax.imageio.ImageIO

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
        .fold(this) { robots, second ->
            robots.map { robot -> robot.move(bounds) }
        }
}

fun List<Robot>.moveUntilChristmasTree(bounds: Bounds, limit: Int = 15000) {
    var second = 0
    var robots = this

    while (second < limit) {
        robots = robots.map { robot -> robot.move(bounds) }
        second++

        val grid = robots.toGrid(bounds)
        val hasABlockOfRobots = grid.positions
            .any { grid.matchesKernelAtPosition(it, blockDetectionKernel) }

        if (hasABlockOfRobots) {
            println("Rendering $second")
            robots.renderToFile(bounds, second)
            return
        }
    }
}

fun List<Robot>.getSecondWithLeastVariance(bounds: Bounds, limit: Int = 10000): Int {
    val variances = mutableListOf<Int>()
    (0 until limit)
        .fold(this) { robots, second ->
            val movedRobots = robots.map { robot -> robot.move(bounds) }

            val totalVariance = robots.zipWithNext().sumOf { (robot1, robot2) ->
                robot1.position.manhattanDistanceFrom(robot2.position)
            }
            variances.add(totalVariance)

            movedRobots
        }
    return variances.indexOf(variances.min())
}

fun List<Robot>.getSafetyFactor(bounds: Bounds): Int {
    return bounds.quadrants.map { quadrant ->
        count { robot -> quadrant.contains(robot.position) }
    }.multiplication()
}

fun List<Robot>.renderToFile(bounds: Bounds, second: Int) {
    val image = BufferedImage(bounds.width, bounds.height, TYPE_INT_RGB)
    val white = Color(255, 255, 255)
    val black = Color(0, 0, 0)
    for (y in 0 until bounds.height) {
        for (x in 0 until bounds.width) {
            val isRobotHere = any { it.position == Position(x, y) }
            if (isRobotHere) {
                image.setRGB(x, y, black.rgb)
            } else {
                image.setRGB(x, y, white.rgb)
            }
        }
    }
    ImageIO.write(image, "png", File("img/$second.png"))
}

fun List<Robot>.print(bounds: Bounds, second: Int) {
    val stringBuilder = StringBuilder()
    stringBuilder.append("After $second seconds:\n")
    for (y in 0 until bounds.height) {
        for (x in 0 until bounds.width) {
            val robotsHere = count { it.position == Position(x, y) }
            stringBuilder.append(if (robotsHere > 0) robotsHere else '.')
        }
        stringBuilder.append('\n')
    }
    stringBuilder.append('\n')
    println(stringBuilder.toString())
}

fun List<Robot>.toGrid(bounds: Bounds): Grid<Int> {
    return Grid.generate(bounds) { position ->
        count { it.position == position }
    }
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
