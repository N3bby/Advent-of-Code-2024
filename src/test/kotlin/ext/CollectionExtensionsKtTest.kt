package ext

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CollectionExtensionsKtTest {

    @Test
    fun multipleMinBy() {
        val list = listOf(2, 2, 4)

        val mins = list.multipleMinBy { it }

        assertThat(mins).containsExactly(2, 2)
    }

}
