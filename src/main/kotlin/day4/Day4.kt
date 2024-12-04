package day4

import ext.allIndexed
import util.Direction
import util.Grid
import util.Offset
import util.Position
import util.matchesKernelAtPosition

fun Grid<Char>.countWordMatchesAtPosition(position: Position, word: String): Int {
    return Direction.entries.count { direction ->
        hasWordAtPosition(position, direction, word)
    }
}

fun Grid<Char>.hasWordAtPosition(position: Position, direction: Direction, word: String): Boolean {
    val positionsToMatch = word.indices.map { i ->
        position + Offset(direction.offset.x * i, direction.offset.y * i)
    }

    return positionsToMatch.allIndexed { index, position ->
        isInBounds(position) && getAtPosition(position) == word[index]
    }
}

fun findWordMatchesInGrid(grid: Grid<Char>, word: String): Int {
    return grid.positions.sumOf { position ->
        grid.countWordMatchesAtPosition(position, word)
    }
}

fun findKernelMatchesInGrid(grid: Grid<Char>, kernels: List<Grid<Char>>): Int {
    return grid.positions.count { position ->
        kernels.any { kernel ->
            grid.matchesKernelAtPosition(position, kernel)
        }
    }
}

val xmasKernels = listOf<Grid<Char>>(
    Grid.fromString("""
        M.S
        .A.
        M.S
    """.trimIndent()),
    Grid.fromString("""
        S.M
        .A.
        S.M
    """.trimIndent()),
    Grid.fromString("""
        M.M
        .A.
        S.S
    """.trimIndent()),
    Grid.fromString("""
        S.S
        .A.
        M.M
    """.trimIndent()),
)
