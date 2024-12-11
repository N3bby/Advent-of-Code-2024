package day11

import ext.splitInTwo
import ext.toList

typealias BlinksLeft = Int
typealias AmountOfStones = Long

@JvmInline
value class Stone(val value: Long) {
    fun blink(): List<Stone> {
        return when {
            value == 0L -> listOf(Stone(1))
            value.toString().length % 2 == 0 -> value.toString()
                .splitInTwo()
                .toList()
                .map { Stone(it.toLong()) }
            else -> listOf(Stone(value * 2024))
        }
    }

    fun stonesAfterBlinks(blinks: BlinksLeft): AmountOfStones {
        val cachedValue = cache[this]?.get(blinks)
        if(cachedValue != null) return cachedValue

        if(blinks == 0) return 1
        val stonesAfterBlinks = blink().sumOf { it.stonesAfterBlinks(blinks - 1) }

        if(!cache.contains(this)) cache[this] = mutableMapOf()
        cache[this]?.set(blinks, stonesAfterBlinks)

        return stonesAfterBlinks
    }
}

val cache = mutableMapOf<Stone, MutableMap<BlinksLeft, AmountOfStones>>()

fun List<Stone>.stonesAfterBlinks(blinks: Int): Long {
    return sumOf { it.stonesAfterBlinks(blinks) }
}

fun parseStones(input: String): List<Stone> {
    return input.split(' ').map { Stone(it.toLong()) }
}

