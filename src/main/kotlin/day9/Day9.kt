package day9

import ext.canContain
import util.integerSequence
import util.swap

typealias FileId = Int
typealias Disk = MutableList<DiskElement>

sealed class DiskMapIndicator {
    data class LengthOfFile(val fileId: FileId, val length: Int) : DiskMapIndicator()
    data class FreeSpace(val length: Int) : DiskMapIndicator()
}

sealed class DiskElement {
    data class FileData(val fileId: FileId) : DiskElement()
    object FreeSpace : DiskElement()
}

data class File(val fileId: FileId, val start: Int, val length: Int) {
    val range: IntRange = start until start + length
}

fun Disk.defragment(): Disk {
    for (index in indices.reversed()) {
        val freeSpaceIndex = getFirstFreeSpaceIndex()
        if (freeSpaceIndex > index) return this

        swap(index, freeSpaceIndex)
    }
    return this
}

fun Disk.compact(): Disk {
    val files = getFiles()

    for (file in files.reversed()) {
        val freeSpaceSpans = getSpansOfFreeSpace()
        freeSpaceSpans
            .firstOrNull { span -> span.first < file.start && span.canContain(file.range) }
            ?.also { span -> swap(file.range, span) }
    }

    return this
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

fun Disk.getSpansOfFreeSpace(): List<IntRange> {
    val spans = mutableListOf<IntRange>()
    var start = 0
    var currentLength = 0

    for (index in indices) {
        when (this[index]) {
            is DiskElement.FileData -> {
                if (currentLength > 0) {
                    spans.add(start until start + currentLength)
                    currentLength = 0
                }
            }

            DiskElement.FreeSpace -> {
                if (currentLength == 0) {
                    start = index
                }
                currentLength++
            }
        }
    }

    return spans
}

fun Disk.getFiles(): List<File> {
    val files = mutableListOf<File>()
    var fileId = 0
    var start = 0
    var currentLength = 0

    for (index in indices) {
        when (val element = this[index]) {
            is DiskElement.FileData -> {
                if (currentLength == 0) {
                    start = index
                }
                if (element.fileId != fileId) {
                    files.add(File(fileId, start, currentLength))
                    start = index
                    currentLength = 0
                }
                fileId = element.fileId
                currentLength++
            }

            DiskElement.FreeSpace -> {
                if (currentLength > 0) {
                    files.add(File(fileId, start, currentLength))
                    currentLength = 0
                }
            }
        }
    }

    if (currentLength != 0) {
        files.add(File(fileId, start, currentLength))
    }

    return files
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
            is DiskMapIndicator.LengthOfFile -> {
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
                DiskMapIndicator.LengthOfFile(fileIdSequence.next(), value.digitToInt())
            )

            else -> yield(
                DiskMapIndicator.FreeSpace(value.digitToInt())
            )
        }
    }
}
