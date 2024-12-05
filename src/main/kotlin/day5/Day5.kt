package day5

import ext.sort
import kotlin.math.ceil

typealias PageNumber = Int

data class PageOrderingRule(
    val before: PageNumber,
    val after: PageNumber,
) {
    fun concernsPages(page1: PageNumber, page2: PageNumber): Boolean =
        (before to after).sort() == (page1 to page2).sort()

    fun areInCorrectOrder(page1: PageNumber, page2: PageNumber): Boolean =
        (before to after) == (page1 to page2)

    fun areInReverseOrder(page1: PageNumber, page2: PageNumber): Boolean =
        (before to after) == (page2 to page1)
}

data class Update(val pages: List<PageNumber>) {

    val middlePage: Int get() = pages[ceil(pages.size / 2.0).toInt() - 1]

    fun followsPageRules(pageOrderingRules: List<PageOrderingRule>): Boolean =
        pageOrderingRules.all { rule -> this.followsPageRule(rule) }

    fun followsPageRule(pageOrderingRule: PageOrderingRule): Boolean {
        val beforeIndex = pages.indexOf(pageOrderingRule.before)
        val afterIndex = pages.indexOf(pageOrderingRule.after)

        return if (beforeIndex == -1 || afterIndex == -1) {
            true
        } else {
            beforeIndex < afterIndex
        }
    }

    fun sortUsingRules(rules: List<PageOrderingRule>): Update {
        return Update(pages.sortedWith(PageOrderingRuleComparator(rules)))
    }
}

class PageOrderingRuleComparator(private val rules: List<PageOrderingRule>) : Comparator<PageNumber> {
    override fun compare(page1: PageNumber?, page2: PageNumber?): Int {
        if (page1 == null || page2 == null) return 0

        val rule = rules.find { it.concernsPages(page1, page2) } ?: return 0

        return if (rule.areInCorrectOrder(page1, page2)) {
            1
        } else if (rule.areInReverseOrder(page1, page2)) {
            -1
        } else {
            0
        }
    }
}

fun List<Update>.followingPageRules(pageOrderingRules: List<PageOrderingRule>): List<Update> =
    filter { it.followsPageRules(pageOrderingRules) }

fun List<Update>.notFollowingPageRules(pageOrderingRules: List<PageOrderingRule>): List<Update> =
    filterNot { it.followsPageRules(pageOrderingRules) }

fun List<Update>.sumOfMiddlePageNumbers(): Int = sumOf { it.middlePage }

fun parsePageOrderingRulesAndUpdates(input: String): Pair<List<PageOrderingRule>, List<Update>> {
    val (pageOrderingRulesInput, updatesInput) = input.split("\n\n")

    val pageOrderingRules = pageOrderingRulesInput.lines()
        .map { it.split('|') }
        .map { (before, after) -> PageOrderingRule(before.toInt(), after.toInt()) }

    val updates = updatesInput.lines()
        .map { it.split(',') }
        .map { Update(it.map { page -> page.toInt() }) }

    return pageOrderingRules to updates
}
