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

    @Test
    fun firstUsingBinarySearch() {
        val list = listOf(
            Position(1, 0),
            Position(2, 1),
            Position(3, 1),
            Position(4, 1)
        )
        assertThat(list.firstUsingBinarySearchIndexed { (_, value) -> value.y == 1 }).isEqualTo(Position(2, 1))

        // Test first element
        val list2 = listOf(
            Position(2, 1),
            Position(3, 1),
            Position(4, 1)
        )
        assertThat(list2.firstUsingBinarySearchIndexed { (_, value) -> value.y == 1 }).isEqualTo(Position(2, 1))

        // Test last element
        val list3 = listOf(
            Position(2, 0),
            Position(3, 0),
            Position(4, 1)
        )
        assertThat(list3.firstUsingBinarySearchIndexed { (_, value) -> value.y == 1 }).isEqualTo(Position(4, 1))
    }

}
