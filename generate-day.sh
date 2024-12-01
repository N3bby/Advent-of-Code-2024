DAY=$1

touch input/day"${DAY}".txt

mkdir src/main/kotlin/day"${DAY}"
echo "package day${DAY}" > src/main/kotlin/day"${DAY}"/Day"${DAY}".kt

mkdir src/test/kotlin/day"${DAY}"
echo "package day${DAY}

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day${DAY}KtTest {

    @Test
    fun \`part 1 - example input\`() {
        val input = \"\"\"

        \"\"\".trimIndent()

    }

}
" > src/test/kotlin/day"${DAY}"/Day"${DAY}"Test.kt
