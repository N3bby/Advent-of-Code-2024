package day15

import util.Direction
import util.Offset

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
