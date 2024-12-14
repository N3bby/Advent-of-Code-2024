package day13

import util.Offset
import util.Position
import util.cartesianProduct
import kotlin.math.floor
import kotlin.math.max

data class Button(val clawOffset: Offset)

data class InputCombination(val buttonAPresses: Int, val buttonBPresses: Int) {
    val tokenPrice get() = buttonAPresses * 3 + buttonBPresses
}

data class ClawMachine(
    private val buttonA: Button,
    private val buttonB: Button,
    private val prizePosition: Position,
) {

    fun findCheapestInputCombination(): InputCombination? {
        return getAllInputCombinations()
            .filter { winsPrize(it) }
            .minByOrNull { it.tokenPrice }
    }

    fun winsPrize(inputCombination: InputCombination): Boolean {
        val (buttonAPresses, buttonBPresses) = inputCombination
        val clawPosition = Position(
            buttonAPresses * buttonA.clawOffset.x + buttonBPresses * buttonB.clawOffset.x,
            buttonAPresses * buttonA.clawOffset.y + buttonBPresses * buttonB.clawOffset.y,
        )
        return clawPosition == prizePosition
    }

    private fun getAllInputCombinations(): List<InputCombination> {
        return cartesianProduct(
            (1..getMaxButtonPresses(buttonA)).toList(),
            (1..getMaxButtonPresses(buttonB)).toList()
        ).map { (buttonAPresses, buttonBPresses) ->
            InputCombination(buttonAPresses, buttonBPresses)
        }
    }

    private fun getMaxButtonPresses(button: Button): Int = max(
        floor(prizePosition.x / button.clawOffset.x.toDouble()).toInt(),
        floor(prizePosition.y / button.clawOffset.y.toDouble()).toInt()
    )
}

fun parseClawMachines(input: String): List<ClawMachine> {
    return input.split("\n\n").map { block ->
        val buttons = mutableListOf<Button>()
        var prizeLocation: Position? = null
        block.lines().forEach { line ->
            when {
                line.startsWith("Button") -> {
                    val (x, y) = Regex("""Button .: X\+(\d+), Y\+(\d+)""").matchEntire(line)!!.destructured
                    buttons.add(Button(Offset(x.toInt(), y.toInt())))
                }

                line.startsWith("Prize") -> {
                    val (x, y) = Regex("""Prize: X=(\d+), Y=(\d+)""").matchEntire(line)!!.destructured
                    prizeLocation = Position(x.toInt(), y.toInt())
                }
            }
        }
        ClawMachine(buttons[0], buttons[1], prizeLocation!!)
    }
}
