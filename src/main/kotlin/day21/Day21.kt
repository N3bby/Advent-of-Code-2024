package day21

import util.Direction
import util.Offset
import util.Position
import kotlin.math.sign

enum class RobotAction(val label: String) {
    LEFT("<"),
    RIGHT(">"),
    UP("^"),
    DOWN("v"),
    ACTIVATE("A");

    companion object {
        fun fromMovement(movement: Offset): RobotAction = when (movement) {
            Direction.LEFT.offset -> LEFT
            Direction.RIGHT.offset -> RIGHT
            Direction.UP.offset -> UP
            Direction.DOWN.offset -> DOWN
            else -> throw IllegalArgumentException("Invalid movement $movement")
        }
    }
}

typealias RobotActionSequence = List<RobotAction>

data class Button(val label: String, val position: Position)

class Keypad(private val buttons: List<Button>, var cursor: Position) {

    fun moveToButton(targetLabel: String): RobotActionSequence {
        val button = buttons.find { it.label == targetLabel } ?: throw Error("Button $targetLabel not found")
        val actions = mutableListOf<RobotAction>()
        while (cursor != button.position) {
            val nextStep = getNextStepTo(button.position)
            actions.add(nextStep)
            move(nextStep)
        }
        return actions + RobotAction.ACTIVATE
    }

    private fun move(action: RobotAction) {
        when (action) {
            RobotAction.LEFT -> cursor += Direction.LEFT.offset
            RobotAction.RIGHT -> cursor += Direction.RIGHT.offset
            RobotAction.UP -> cursor += Direction.UP.offset
            RobotAction.DOWN -> cursor += Direction.DOWN.offset
            RobotAction.ACTIVATE -> Unit
        }
    }

    // TODO Missing something here
    // This should keep in mind what the shortest sequence would be for the next keypad
    // I'm not sure if there's a clean way to do this, without holding a bunch of state in memory
    // This might require a brute force...
    private fun getNextStepTo(position: Position): RobotAction {
        val (deltaX, deltaY) = position - cursor

        val stepX = sign(deltaX.toDouble()).toInt()
        val stepY = sign(deltaY.toDouble()).toInt()

        // This is a bit cheaty; y = 0 has a gap due to the way I configure the numeric & directional keypads
        val rowHasGap = cursor.y == 0
        // This is a bit cheaty; x = 0 has a gap due to the way I configure the numeric & directional keypads
        val columnHasGap = cursor.x == 0

        // This if/else can probably be simplified?
        return if (stepY != 0 && columnHasGap) {
            RobotAction.fromMovement(Offset(0, stepY))
        } else if (stepX != 0 && rowHasGap) {
            RobotAction.fromMovement(Offset(stepX, 0))
        } else if (stepY != 0) {
            RobotAction.fromMovement(Offset(0, stepY))
        } else {
            RobotAction.fromMovement(Offset(stepX, 0))
        }
    }

}

fun getShortestActionSequence(code: String, keypad: Keypad): String {
    val actions = mutableListOf<RobotAction>()

    code.forEach { label ->
        actions.addAll(keypad.moveToButton(label.toString()))
    }

    return actions.joinToString("") { it.label }
}

fun getShortestActionSequence(code: String): String {
    val numericKeypad = createNumericKeypad()
    val numericKeypadSequence = getShortestActionSequence(code, numericKeypad)

    val directionalKeypad1 = createDirectionalKeypad()
    val directionalKeypad1Sequence = getShortestActionSequence(numericKeypadSequence, directionalKeypad1)

    val directionalKeypad2 = createDirectionalKeypad()
    val directionalKeypad2Sequence = getShortestActionSequence(directionalKeypad1Sequence, directionalKeypad2)

    return directionalKeypad2Sequence
}

fun calculateSequenceComplexity(value: Pair<String, String>): Int {
    return value.first.replace("A", "").toInt() * value.second.length
}

fun createNumericKeypad(): Keypad {
    val buttons = listOf(
        Button("7", Position(0, 3)),
        Button("8", Position(1, 3)),
        Button("9", Position(2, 3)),
        Button("4", Position(0, 2)),
        Button("5", Position(1, 2)),
        Button("6", Position(2, 2)),
        Button("1", Position(0, 1)),
        Button("2", Position(1, 1)),
        Button("3", Position(2, 1)),
        Button("0", Position(1, 0)),
        Button("A", Position(2, 0)),
    )
    return Keypad(buttons, Position(2, 0))
}

fun createDirectionalKeypad(): Keypad {
    val buttons = listOf(
        Button("^", Position(1, 0)),
        Button("A", Position(2, 0)),
        Button("<", Position(0, -1)),
        Button("v", Position(1, -1)),
        Button(">", Position(2, -1)),
    )
    return Keypad(buttons, Position(2, 0))
}
