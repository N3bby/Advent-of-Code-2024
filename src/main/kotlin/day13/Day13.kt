package day13

import ext.isNaturalNumber
import util.LongPosition
import util.Offset

data class Button(val clawOffset: Offset)

data class InputCombination(val buttonAPresses: Long, val buttonBPresses: Long) {
    val tokenPrice get() = buttonAPresses * 3 + buttonBPresses
}

data class ClawMachine(
    private val buttonA: Button,
    private val buttonB: Button,
    private val prizePosition: LongPosition,
) {
    /**
     *  I didn't know how to do this, so I went to Reddit for a hint.
     *
     *   We can write the solution as a system of equations:
     *   A * ax + B * bx = px
     *   A * ay + B * by = py
     *
     *   Using a source I found on Google (https://www.slimleren.nl/onderwerpen/wiskunde/12.409/stelsels-vergelijkingen)
     *   and a lot of my time (it's been almost 10 years since I had to do something like this 😅)
     *   I found the following solution to this system. It can probably be simplified a lot...
     *   B = (py * ax - px * ay) / (by * ax - bx * ay)
     *   A = (px - (py * ax - px * ay) / (by * ax - bx * ay) * bx) / ax
     *
     *   Only if both results are natural numbers it will reach the prize
     *   Otherwise, this is an unwinnable claw machine
     */
    fun findCheapestInputCombination(): InputCombination? {
        val ax = buttonA.clawOffset.x.toDouble()
        val ay = buttonA.clawOffset.y.toDouble()
        val bx = buttonB.clawOffset.x.toDouble()
        val by = buttonB.clawOffset.y.toDouble()
        val px = prizePosition.x.toDouble()
        val py = prizePosition.y.toDouble()

        val bPresses = (py * ax - px * ay) / (by * ax - bx * ay)
        val aPresses = (px - (py * ax - px * ay) / (by * ax - bx * ay) * bx) / ax

        return if (aPresses.isNaturalNumber() && bPresses.isNaturalNumber()) {
            InputCombination(aPresses.toLong(), bPresses.toLong())
        } else {
            null
        }
    }
}

fun parseClawMachines(input: String, fixUnitConversion: Boolean = false): List<ClawMachine> {
    return input.split("\n\n").map { block ->
        val buttons = mutableListOf<Button>()
        var prizeLocation: LongPosition? = null
        block.lines().forEach { line ->
            when {
                line.startsWith("Button") -> {
                    val (x, y) = Regex("""Button .: X\+(\d+), Y\+(\d+)""").matchEntire(line)!!.destructured
                    buttons.add(Button(Offset(x.toInt(), y.toInt())))
                }

                line.startsWith("Prize") -> {
                    val (x, y) = Regex("""Prize: X=(\d+), Y=(\d+)""").matchEntire(line)!!.destructured
                    if (fixUnitConversion) {
                        prizeLocation = LongPosition(x.toLong() + 10000000000000, y.toLong() + 10000000000000)
                    } else {
                        prizeLocation = LongPosition(x.toLong(), y.toLong())
                    }
                }
            }
        }
        ClawMachine(buttons[0], buttons[1], prizeLocation!!)
    }
}
