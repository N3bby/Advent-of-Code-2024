package day19

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.readInput

class Day19KtTest {

    @Test
    fun `part 1 - example input`() {
        val input = """
            r, wr, b, g, bwu, rb, gb, br
    
            brwrr
            bggr
            gbbr
            rrbgbr
            ubwu
            bwurrg
            brgr
            bbrgwb
        """.trimIndent()

        val (towels, designs) = parseTowelsAndDesigns(input)
        val possibleDesigns = designs.count { it.getPossibleCombinations(towels) > 0 }

        assertThat(possibleDesigns).isEqualTo(6)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(19)

        val (towels, designs) = parseTowelsAndDesigns(input)
        val cache = mutableMapOf<String, Long>()
        val possibleDesigns = designs.count { it.getPossibleCombinations(towels, cache) > 0 }

        assertThat(possibleDesigns).isEqualTo(300)
    }

    @Test
    fun `part 2 - example input`() {
        val input = """
            r, wr, b, g, bwu, rb, gb, br
    
            brwrr
            bggr
            gbbr
            rrbgbr
            ubwu
            bwurrg
            brgr
            bbrgwb
        """.trimIndent()

        val (towels, designs) = parseTowelsAndDesigns(input)
        val possibleCombinations = designs.map { it to it.getPossibleCombinations(towels) }

        assertThat(possibleCombinations).containsExactly(
            TowelDesign("brwrr") to 2,
            TowelDesign("bggr") to 1,
            TowelDesign("gbbr") to 4,
            TowelDesign("rrbgbr") to 6,
            TowelDesign("ubwu") to 0,
            TowelDesign("bwurrg") to 1,
            TowelDesign("brgr") to 2,
            TowelDesign("bbrgwb") to 0
        )
        assertThat(possibleCombinations.sumOf { it.second }).isEqualTo(16)
    }

    @Test
    fun `part 2 - puzzle input`() {
        val input = readInput(19)

        val (towels, designs) = parseTowelsAndDesigns(input)
        val cache = mutableMapOf<String, Long>()
        val possibleCombinations = designs.sumOf { it.getPossibleCombinations(towels, cache) }

        assertThat(possibleCombinations).isEqualTo(624802218898092)
    }
}

