package ext

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SetExtensionsKtTest {

    @Test
    fun intersect_set_of_sets() {
        val intersection = setOf(
            setOf('a', 'b', 'c'),
            setOf('x', 'b', 'z'),
            setOf('x', 'b', 'c')
        ).intersect()
        Assertions.assertEquals(setOf('b'), intersection)
    }

}
