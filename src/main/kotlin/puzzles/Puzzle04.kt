package puzzles

import Puzzle

class Puzzle03P1 : Puzzle() {
    override fun solve(lines : List<String>): String =
        lines.sumOf { line ->
            val itemLists = getBothItemLists(line)
            val commonItems = getCommonItems(itemLists[0], itemLists[1])

            commonItems.sumOf { item -> getValueOfItem(item) }
        }.toString()
}

fun getBothItemLists(line: String): List<String> =
    listOf(line.substring(0, line.length/2), line.substring(line.length/2, line.length))

fun getCommonItems(itemList1: String, itemList2: String): Set<Char> {
    val commonItems = mutableSetOf<Char>()

    itemList1.iterator().forEach { item ->
        run {
            if (itemList2.contains(item)) {
                commonItems.add(item)
            }
        }
    }

    return commonItems
}

fun getValueOfItem(item: Char): Int {
    val asciiValue = item.code
    return if (asciiValue < 91) asciiValue - 38 else asciiValue - 96
}

class Puzzle03P2 : Puzzle() {
    override fun solve(lines : List<String>): String {
        var lineIndex = 0
        var sumOfValues = 0

        while (lineIndex + 3 <= lines.size) {
            val commonItemsFirstTwoLines = getCommonItems(lines[lineIndex], lines[lineIndex + 1])
            val commonItemsFirstAndThirdLine = getCommonItems(lines[lineIndex], lines[lineIndex + 2])

            val commonItem = commonItemsFirstTwoLines.find { item -> commonItemsFirstAndThirdLine.contains(item) }
            sumOfValues += commonItem?.let { getValueOfItem(it) } ?: 0

            lineIndex += 3
        }

        return sumOfValues.toString()
    }
}