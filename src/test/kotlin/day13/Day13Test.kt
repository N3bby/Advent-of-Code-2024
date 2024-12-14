package day13

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.readInput

class Day13KtTest {

    @Test
    fun `part 1 - example input`() {
        val input = """
           Button A: X+94, Y+34
           Button B: X+22, Y+67
           Prize: X=8400, Y=5400
        
           Button A: X+26, Y+66
           Button B: X+67, Y+21
           Prize: X=12748, Y=12176
        
           Button A: X+17, Y+86
           Button B: X+84, Y+37
           Prize: X=7870, Y=6450
        
           Button A: X+69, Y+23
           Button B: X+27, Y+71
           Prize: X=18641, Y=10279
        """.trimIndent()

        val clawMachines = parseClawMachines(input)
        val totalCheapestPrice = clawMachines
            .mapNotNull { it.findCheapestInputCombination() }
            .sumOf { it.tokenPrice }

        assertThat(totalCheapestPrice).isEqualTo(480)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(13)

        val clawMachines = parseClawMachines(input)
        val totalCheapestPrice = clawMachines
            .mapNotNull { it.findCheapestInputCombination() }
            .sumOf { it.tokenPrice }

        assertThat(totalCheapestPrice).isEqualTo(27157)
    }

    @Test
    fun `part 2 - puzzle input`() {
        val input = readInput(13)

        val clawMachines = parseClawMachines(input, fixUnitConversion = true)
        val totalCheapestPrice = clawMachines
            .mapNotNull { it.findCheapestInputCombination() }
            .sumOf { it.tokenPrice }

        assertThat(totalCheapestPrice).isEqualTo(104015411578548)

    }
}

