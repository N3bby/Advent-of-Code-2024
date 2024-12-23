package util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PositionTest {

    @Test
    fun getCellsWithinRadius() {
        val cellsWithinRadius1 = Position(2, 2).getCellsWithinRadius(1)
        assertThat(cellsWithinRadius1).containsExactlyInAnyOrder(
            Position(3, 2),
            Position(1, 2),
            Position(2, 3),
            Position(2, 1)
        )

        val cellsWithinRadius2 = Position(2, 2).getCellsWithinRadius(2)
        assertThat(cellsWithinRadius2).containsExactlyInAnyOrder(
            // Left
            Position(1, 2),
            Position(0, 2),
            // Right
            Position(3, 2),
            Position(4, 2),
            // Up
            Position(2, 3),
            Position(2, 4),
            // Down
            Position(2, 1),
            Position(2, 0),
            // Top left
            Position(1, 3),
            // Top right
            Position(3, 3),
            // Bottom left
            Position(1, 1),
            // Bottom right
            Position(3, 1),
        )
    }


}