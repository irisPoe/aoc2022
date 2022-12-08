package puzzles

import Puzzle

class Puzzle08P1 : Puzzle() {
    override fun solve(lines : List<String>): String {
        val treeGrid = parseTreeGrid(lines)
        var visibleTreeCount = 0

        for (lineIndex in treeGrid.indices) {
            for (colIndex in 0 until treeGrid[0].size) {
                if (isVisibleFromOutside(lineIndex, colIndex, treeGrid))
                    visibleTreeCount++
            }
        }

        return visibleTreeCount.toString()
    }

    private fun isVisibleFromOutside(currentLineIndex: Int, currentColIndex: Int, treeGrid: Array<IntArray>): Boolean {
        // tree is on the edge
        if (currentLineIndex == 0 || currentColIndex == 0 || currentLineIndex == treeGrid.size - 1 || currentColIndex == treeGrid[0].size - 1)
            return true

        val currentTreeHeight = treeGrid[currentLineIndex][currentColIndex]
        var isVisible = true

        // check left
        for (colIndex in 0 until currentColIndex) {
            if (treeGrid[currentLineIndex][colIndex] >= currentTreeHeight)
                isVisible = false
        }

        if (isVisible)
            return true

        // check right
        isVisible = true
        for (colIndex in currentColIndex + 1 until treeGrid.size) {
            if (treeGrid[currentLineIndex][colIndex] >= currentTreeHeight)
                isVisible = false
        }

        if (isVisible)
            return true

        // check up
        isVisible = true
        for (lineIndex in 0 until currentLineIndex) {
            if (treeGrid[lineIndex][currentColIndex] >= currentTreeHeight)
                isVisible = false
        }

        if (isVisible)
            return true

        // check down
        isVisible = true
        for (lineIndex in currentLineIndex + 1 until treeGrid[0].size) {
            if (treeGrid[lineIndex][currentColIndex] >= currentTreeHeight)
                isVisible = false
        }

        return isVisible
    }
}

fun parseTreeGrid(lines: List<String>): Array<IntArray> =
    lines.map {line ->
        line.map { char -> char.digitToInt() }.toIntArray()
    }.toTypedArray()

class Puzzle08P2 : Puzzle() {
    override fun solve(lines: List<String>): String {
        val treeGrid = parseTreeGrid(lines)
        var highestVisibleTree = -1

        for (lineIndex in treeGrid.indices) {
            for (colIndex in 0 until treeGrid[0].size) {
                val scenicScore = getScenicScoreOfTree(lineIndex, colIndex, treeGrid)
                highestVisibleTree = if (scenicScore > highestVisibleTree) scenicScore else highestVisibleTree
            }
        }

        return highestVisibleTree.toString()
    }

    private fun getScenicScoreOfTree(currentLineIndex: Int, currentColIndex: Int, treeGrid: Array<IntArray>): Int {
        // tree is on the edge
        if (currentLineIndex == 0 || currentColIndex == 0 || currentLineIndex == treeGrid.size - 1 || currentColIndex == treeGrid[0].size - 1)
            return 0

        val currentTreeHeight = treeGrid[currentLineIndex][currentColIndex]

        var scenicScoreLeft = 0
        for (colIndex in currentColIndex - 1 downTo  0) {
            if (treeGrid[currentLineIndex][colIndex] >= currentTreeHeight) {
                scenicScoreLeft++
                break
            }
            else
                scenicScoreLeft++
        }

        var scenicScoreRight = 0
        for (colIndex in currentColIndex + 1 until treeGrid.size) {
            if (treeGrid[currentLineIndex][colIndex] >= currentTreeHeight){
                scenicScoreRight++
                break
            }
            else
                scenicScoreRight++
        }

        var scenicScoreUp = 0
        for (lineIndex in currentLineIndex - 1 downTo  0) {
            if (treeGrid[lineIndex][currentColIndex] >= currentTreeHeight){
                scenicScoreUp++
                break
            }
            else
                scenicScoreUp++
        }

        var scenicScoreDown = 0
        for (lineIndex in currentLineIndex + 1 until treeGrid[0].size) {
            if (treeGrid[lineIndex][currentColIndex] >= currentTreeHeight){
                scenicScoreDown++
                break
            }
            else
                scenicScoreDown++
        }

        return scenicScoreLeft * scenicScoreRight * scenicScoreUp * scenicScoreDown
    }
}