package day20

import util.Grid
import util.Position
import util.computeDistancesFromStart
import java.util.stream.Collectors.toSet

typealias SecondsSaved = Int

data class Racetrack(
    private val _tracks: Set<Position>,
    val start: Position,
    val end: Position,
) {
    val tracks = _tracks + start + end
    private val distances = computeDistancesFromStart(start, tracks)

    fun findCheats(cheatDistance: Int): Set<Cheat> {
        return tracks
            .parallelStream()
            .flatMap { track ->
                track.getCellsWithinRadius(cheatDistance)
                    .map { skipToTrack ->
                        Cheat(track, skipToTrack, findSecondsSaved(distances, track, skipToTrack))
                    }
                    .filter { it.secondsSaved > 0 }
                    .stream()
            }
            .collect(toSet())
    }

    private fun findSecondsSaved(
        distances: Map<Position, Int>,
        position: Position,
        skipToPosition: Position
    ): SecondsSaved {
        if (!distances.containsKey(skipToPosition)) return 0
        val difference = distances.getValue(skipToPosition) - distances.getValue(position)
        return difference - position.manhattanDistanceFrom(skipToPosition) // Subtract the extra steps for going through the wall
    }
}

data class Cheat(val startPosition: Position, val endPosition: Position, val secondsSaved: Int)

fun parseRacetrack(input: String): Racetrack {
    val grid = Grid.fromString(input)

    val tracks = grid.getPositionsWhereValue { it == '.' }.toSet()
    val start = grid.getPositionsWhereValue { it == 'S' }[0]
    val end = grid.getPositionsWhereValue { it == 'E' }[0]

    return Racetrack(tracks, start, end)
}