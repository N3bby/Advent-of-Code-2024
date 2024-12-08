package util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CollectionsKtTest {

    @Test
    fun cartesianProduct() {
        val list1 = listOf('a', 'b')
        val list2 = list1

        val result = cartesianProduct(list1, list2)

        assertThat(result).containsExactlyInAnyOrder(
            'a' to 'a',
            'a' to 'b',
            'b' to 'a',
            'b' to 'b'
        )

    }

}
