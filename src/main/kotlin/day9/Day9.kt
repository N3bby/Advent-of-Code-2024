package day9

import util.integerSequence
import util.swap

typealias FileId = Int
typealias Disk = MutableList<DiskElement>
typealias DiskMap = MutableList<DiskMapIndicator>

sealed class DiskMapIndicator() {
    abstract val length: Int

    data class File(val fileId: FileId, override val length: Int) : DiskMapIndicator()
    data class FreeSpace(override val length: Int) : DiskMapIndicator()
}

sealed class DiskElement {
    data class FileData(val fileId: FileId) : DiskElement()
    object FreeSpace : DiskElement()
}

fun Disk.defragment(): Disk {
    for (index in indices.reversed()) {
        val freeSpaceIndex = getFirstFreeSpaceIndex()
        if (freeSpaceIndex > index) return this

        swap(index, freeSpaceIndex)
    }
    return this
}

fun DiskMap.compact(): DiskMap {
    val files = getFiles()

    for (file in files.reversed()) {
        val fileIndex = indexOf(file)

        for ((index, indicator) in withIndex()) {
            if (indicator is DiskMapIndicator.FreeSpace) {
                val freeSpaceIndex = index
                val freeSpace = indicator

                if(freeSpaceIndex > fileIndex || freeSpace.length < file.length) continue

                val extraSpace = freeSpace.length - file.length

                this[freeSpaceIndex] = file
                this[fileIndex] = DiskMapIndicator.FreeSpace(freeSpace.length - extraSpace)

                if (extraSpace > 0) {
                    add(freeSpaceIndex + 1, DiskMapIndicator.FreeSpace(extraSpace))
                }
                break
            }
        }
    }

    return this
}

fun DiskMap.getFiles(): List<DiskMapIndicator.File> {
    return filterIsInstance<DiskMapIndicator.File>()
}

fun Disk.checksum(): Long {
    return this.mapIndexedNotNull { index, element ->
        when (element) {
            is DiskElement.FileData -> (index * element.fileId.toLong())
            else -> null
        }
    }.sum()
}

fun Disk.getFirstFreeSpaceIndex(): Int {
    return indexOf(DiskElement.FreeSpace)
}

fun Disk.printDisk(): String {
    val stringBuilder = StringBuilder()

    forEach {
        when (it) {
            is DiskElement.FileData -> stringBuilder.append(it.fileId)
            is DiskElement.FreeSpace -> stringBuilder.append(".")
        }
    }

    val disk = stringBuilder.toString()
    println(disk)
    return disk
}

fun createDisk(indicators: Sequence<DiskMapIndicator>): Disk {
    return indicators.flatMap { indicator ->
        when (indicator) {
            is DiskMapIndicator.File -> {
                val fileData = DiskElement.FileData(indicator.fileId)
                (0 until indicator.length).map { i -> fileData }
            }

            is DiskMapIndicator.FreeSpace -> {
                (0 until indicator.length).map { i -> DiskElement.FreeSpace }
            }
        }
    }.toMutableList()
}

fun parseDiskMap(input: String): Sequence<DiskMapIndicator> = sequence {
    var fileIdSequence = integerSequence().iterator()
    for (index in input.indices) {
        val value = input[index]
        when {
            index % 2 == 0 -> yield(
                DiskMapIndicator.File(fileIdSequence.next(), value.digitToInt())
            )

            else -> yield(
                DiskMapIndicator.FreeSpace(value.digitToInt())
            )
        }
    }
}
