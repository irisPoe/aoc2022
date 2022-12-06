package puzzles

import Puzzle

class Puzzle06P1 : Puzzle() {
    override fun solve(lines : List<String>): String {
        val line = lines[0] // only one line in puzzle this time

        var index = 4
        val currentMarkerProgress = line.take(4).toMutableList()
        var markerPosition = -1

        while (markerPosition < 0) {
            if (currentMarkerProgress.toSet().size == 4) {
                markerPosition = index
            } else {
                currentMarkerProgress.removeFirst()
                currentMarkerProgress.add(line[index++])
            }
        }

        return markerPosition.toString()
    }
}

class Puzzle06P2 : Puzzle() {
    override fun solve(lines: List<String>): String {
        val line = lines[0] // only one line in puzzle this time

        var index = 4
        val currentMarkerProgress = line.take(14).toMutableList()
        var markerPosition = -1

        while (markerPosition < 0) {
            if (currentMarkerProgress.toSet().size == 14) {
                markerPosition = index
            } else {
                currentMarkerProgress.removeFirst()
                currentMarkerProgress.add(line[index++])
            }
        }

        return markerPosition.toString()
    }
}