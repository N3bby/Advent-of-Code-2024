package day8

import util.Bounds
import util.Grid
import util.Position
import util.cartesianProduct

data class Antenna(val position: Position, val frequency: Char)

fun Pair<Antenna, Antenna>.getAntinode(bounds: Bounds): List<Position> {
    val (antenna1, antenna2) = this

    val deltaX = antenna1.position.x - antenna2.position.x
    val deltaY = antenna1.position.y - antenna2.position.y

    val antinodeX = antenna1.position.x + deltaX
    val antinodeY = antenna1.position.y + deltaY

    return if (bounds.contains(Position(antinodeX, antinodeY))) {
        listOf(Position(antinodeX, antinodeY))
    } else {
        emptyList()
    }
}

fun Pair<Antenna, Antenna>.getAntinodeWithResonantHarmonics(bounds: Bounds): List<Position> {
    val (antenna1, antenna2) = this

    val deltaX = antenna1.position.x - antenna2.position.x
    val deltaY = antenna1.position.y - antenna2.position.y

    var antinodeX = antenna1.position.x
    var antinodeY = antenna1.position.y
    val antinodes = mutableListOf<Position>()

    while (bounds.contains(Position(antinodeX, antinodeY))) {
        antinodes.add(Position(antinodeX, antinodeY))
        antinodeX += deltaX
        antinodeY += deltaY
    }

    return antinodes
}

fun findAntinodes(bounds: Bounds, antennas: List<Antenna>, considerResonantHarmonics: Boolean): Set<Position> {
    val antennasByFrequency = antennas
        .groupBy(Antenna::frequency)
        .map { it.value }

    return antennasByFrequency
        .flatMap { it.getCombinations() }
        .flatMap { combination ->
            if(considerResonantHarmonics) {
                combination.getAntinodeWithResonantHarmonics(bounds)
            } else {
                combination.getAntinode(bounds)
            }
        }
        .toSet()
}

fun List<Antenna>.getCombinations(): List<Pair<Antenna, Antenna>> {
    return cartesianProduct(this, this).filter { it.first != it.second }
}

fun parseAntennas(input: String): Pair<Bounds, List<Antenna>> {
    val grid = Grid.fromString(input)
    val antennas = mutableListOf<Antenna>()

    grid.positions.forEach { position ->
        val character = grid.getAtPosition(position)
        if (Regex("""[a-zA-Z\d]""").matches(character.toString())) {
            antennas.add(Antenna(position, character))
        }
    }

    return grid.bounds to antennas
}
