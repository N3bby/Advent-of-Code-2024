package util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoundsTest {

    @Test
    fun `loop - loops position within bounds`() {
        val bounds = Bounds(4, 4)
        val position1 = bounds.loop(Position(5, 5))
        val position2 = bounds.loop(Position(-1, 1))

        assertThat(position1).isEqualTo(Position(1, 1))
        assertThat(position2).isEqualTo(Position(3, 1))
    }

}
