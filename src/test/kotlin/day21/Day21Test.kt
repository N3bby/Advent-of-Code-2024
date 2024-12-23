package day21

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day21KtTest {

    @Test
    fun `part 1 - example input`() {
        val input = """
            029A
            980A
            179A
            456A
            379A            
        """.trimIndent()

        val codes = input.lines().map { it.trim() }

        val shortestCodes = codes
            .map { it to getShortestActionSequence(it) }

        assertThat(shortestCodes.map { it.first to it.second.length }).containsExactly(
            "029A" to "<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A".length,
            "980A" to "<v<A>>^AAAvA^A<vA<AA>>^AvAA<^A>A<v<A>A>^AAAvA<^A>A<vA>^A<A>A".length,
            "179A" to "<v<A>>^A<vA<A>>^AAvAA<^A>A<v<A>>^AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A".length,
            "456A" to "<v<A>>^AA<vA<A>>^AAvAA<^A>A<vA>^A<A>A<vA>^A<A>A<v<A>A>^AAvA<^A>A".length,
            "379A" to "<v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A".length,
        )
    }
}

