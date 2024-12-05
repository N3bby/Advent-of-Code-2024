package day5

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.readInput

class Day5KtTest {

    @Test
    fun `part 1 - example input`() {
        val input = """
            47|53
            97|13
            97|61
            97|47
            75|29
            61|13
            75|53
            29|13
            97|29
            53|29
            61|53
            97|53
            61|29
            47|13
            75|47
            97|75
            47|61
            75|61
            47|29
            75|13
            53|13
            
            75,47,61,53,29
            97,61,53,29,13
            75,29,13
            75,97,47,61,53
            61,13,29
            97,13,75,29,47
        """.trimIndent()

        val (pageOrderingRules, updates) = parsePageOrderingRulesAndUpdates(input)

        val updatesFollowingPageRules = updates.followingPageRules(pageOrderingRules)
        val result = updatesFollowingPageRules.sumOfMiddlePageNumbers()

        assertThat(result).isEqualTo(143)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(5)

        val (pageOrderingRules, updates) = parsePageOrderingRulesAndUpdates(input)

        val updatesFollowingPageRules = updates.followingPageRules(pageOrderingRules)
        val result = updatesFollowingPageRules.sumOfMiddlePageNumbers()

        assertThat(result).isEqualTo(4905)
    }

    @Test
    fun `part 2 - example input`() {
        val input = """
            47|53
            97|13
            97|61
            97|47
            75|29
            61|13
            75|53
            29|13
            97|29
            53|29
            61|53
            97|53
            61|29
            47|13
            75|47
            97|75
            47|61
            75|61
            47|29
            75|13
            53|13
            
            75,47,61,53,29
            97,61,53,29,13
            75,29,13
            75,97,47,61,53
            61,13,29
            97,13,75,29,47
        """.trimIndent()

        val (pageOrderingRules, updates) = parsePageOrderingRulesAndUpdates(input)

        val correctlyOrderedUpdates = updates
            .notFollowingPageRules(pageOrderingRules)
            .map { update -> update.sortUsingRules(pageOrderingRules) }
        val result = correctlyOrderedUpdates.sumOfMiddlePageNumbers()

        assertThat(result).isEqualTo(123)
    }

    @Test
    fun `part 2 - puzzle input`() {
        val input = readInput(5)

        val (pageOrderingRules, updates) = parsePageOrderingRulesAndUpdates(input)

        val correctlyOrderedUpdates = updates
            .notFollowingPageRules(pageOrderingRules)
            .map { update -> update.sortUsingRules(pageOrderingRules) }
        val result = correctlyOrderedUpdates.sumOfMiddlePageNumbers()

        assertThat(result).isEqualTo(6204)
    }
}

