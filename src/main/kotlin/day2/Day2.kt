package day2

import ext.dropAt
import kotlin.math.abs

data class Report(val levels: List<Int>) {
    val safe get() = allIncreasingOrDecreasing && changeIsGradual
    val safeWithDampener get() = permutations.any { it.allIncreasingOrDecreasing && it.changeIsGradual }

    private val changeIsGradual = levels.zipWithNext()
        .all { abs(it.first - it.second) in 1..3 }

    private val allIncreasingOrDecreasing =
            levels.sorted() == levels ||
            levels.sorted() == levels.reversed()

    private val permutations get() = levels.indices
        .map { levels.dropAt(it) }
        .map { Report(it) }
}

fun parseReports(lines: List<String>): List<Report> {
    return lines.map { report ->
        Report(
            report
                .split(" ")
                .map { level -> level.toInt() }
        )
    }
}

fun countSafeReports(reports: List<Report>): Int {
    return reports.count { it.safe }
}

fun countSafeReportsWithDampener(reports: List<Report>): Int {
    return reports.count { it.safeWithDampener }
}