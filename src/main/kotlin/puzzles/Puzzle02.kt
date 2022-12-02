package puzzles

import Puzzle

const val lostValue = 0
const val drawValue = 3
const val winValue = 6

class Puzzle02P1 : Puzzle() {
    override fun solve(lines : List<String>): String = lines.sumOf { game ->
        val gamePlay = game.split(' ').toTypedArray()
        getShapeScore(gamePlay[1]) + getScoreOfOutcome(gamePlay)
    }.toString()
}

fun getShapeScore(shape: String): Int = when (shape) {
    "X" -> 1
    "Y" -> 2
    "Z" -> 3
    else -> throw RuntimeException("Shape for shape score not found.")
}

fun getScoreOfOutcome(game: Array<String>): Int = when (game[0]) {
    "A" -> { if (game[1] == "X") lostValue else if (game[1] == "Y") winValue else lostValue }
    "B" -> { if (game[1] == "Y") drawValue else if (game[1] == "Z") winValue else lostValue }
    "C" -> { if (game[1] == "Z") drawValue else if (game[1] == "X") winValue else lostValue }
    else -> throw RuntimeException("Shape for calculating outcome not found.")
}

fun getScoreOfOutcome(wonOrLostValue: String): Int = when (wonOrLostValue) {
    "X" -> lostValue
    "Y" -> drawValue
    "Z" -> winValue
    else -> throw RuntimeException("Won or lost value not found.")
}

fun getShapeScore(game: Array<String>): Int = when (game[1]) {
    "X" -> { if (game[0] == "A") 3 else if (game[0] == "B") 1 else 2 }
    "Y" -> { if (game[0] == "A") 1 else if (game[0] == "B") 2 else 3 }
    "Z" -> { if (game[0] == "A") 2 else if (game[0] == "B") 3 else 1 }
    else -> throw RuntimeException("Shape for shape score not found.")
}

class Puzzle02P2 : Puzzle() {
    override fun solve(lines : List<String>): String = lines.sumOf { line ->
        val gamePlay = line.split(' ').toTypedArray()
        getShapeScore(gamePlay) + getScoreOfOutcome(gamePlay[1])
    }.toString()
}