package day9

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import util.readInput

class Day9KtTest {

    @Test
    fun `part 1 - example input`() {
        val input = "2333133121414131402"

        val diskMap = parseDiskMap(input)
        val disk = createDisk(diskMap)
        val checksumOfDefragmentedDisk = disk.defragment().checksum()

        assertThat(checksumOfDefragmentedDisk).isEqualTo(1928)
    }

    @Test
    fun `part 1 - puzzle input`() {
        val input = readInput(9)

        val diskMap = parseDiskMap(input)
        val disk = createDisk(diskMap)
        val checksumOfDefragmentedDisk = disk.defragment().checksum()

        assertThat(checksumOfDefragmentedDisk).isEqualTo(6360094256423)
    }

    @Test
    fun `part 2 - example input`() {
        val input = "2333133121414131402"

        val diskMap = parseDiskMap(input)
        val disk = createDisk(diskMap)
        val checksumOfDefragmentedDisk = disk.compact().checksum()

        assertThat(checksumOfDefragmentedDisk).isEqualTo(2858)
    }

    @Test
    @Disabled("Test takes 5 seconds to run")
    fun `part 2 - puzzle input`() {
        val input = readInput(9)

        val diskMap = parseDiskMap(input)
        val disk = createDisk(diskMap)
        val checksumOfDefragmentedDisk = disk.compact().checksum()

        assertThat(checksumOfDefragmentedDisk).isEqualTo(6379677752410)
    }
}

