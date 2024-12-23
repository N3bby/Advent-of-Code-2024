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
        val possibleDesigns = designs.count { it.canBeMadeWith(towels) }

        assertThat(possibleDesigns).isEqualTo(6)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(19)

        val (towels, designs) = parseTowelsAndDesigns(input)
        val cache = mutableMapOf<String, Boolean>()
        val possibleDesigns = designs.count { it.canBeMadeWith(towels, cache) }

        assertThat(possibleDesigns).isEqualTo(300)

    }
}

