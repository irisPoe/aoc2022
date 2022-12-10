package puzzles

import Puzzle

class Puzzle09P1 : Puzzle() {
    override fun solve(lines : List<String>): String {
        val allMotions = getMotions(lines)
        var currentPosition = RopePosition(
            Position(0, 0),
            Position(0, 0)
        )

        val visitedTailPositions = mutableSetOf<Position>()
        visitedTailPositions.add(currentPosition.tailPosition)

        for (motion in allMotions) {
            for (stepCount in 0 until motion.amountOfSteps) {
                currentPosition = performStep(currentPosition, motion.direction)
                visitedTailPositions.add(currentPosition.tailPosition)
            }
        }

        return visitedTailPositions.size.toString()
    }
}

fun getMotions(lines: List<String>): List<Motion> =
    lines.map { line ->
        line.split(' ')
            .let { splitLine -> Motion(Direction.valueOf(splitLine[0]), splitLine[1].toInt()) }
    }

data class Motion(val direction: Direction, val amountOfSteps: Int)
enum class Direction {
    U, D, R, L
}

data class Position(var xAxis: Int, var yAxis: Int)
data class RopePosition(
    var headPosition: Position,
    var tailPosition: Position)

fun performMotion(position: Position, direction: Direction): Position {
    when (direction) {
        Direction.U -> position.yAxis -= 1
        Direction.D -> position.yAxis++
        Direction.L -> position.xAxis -= 1
        Direction.R -> position.xAxis++
    }

    return position
}

fun performStep(currentPosition: RopePosition, stepDirection: Direction?, isTailing: Boolean = false):RopePosition {
    if (stepDirection == null && !isTailing)
        throw IllegalArgumentException()

    val newPosition = currentPosition.copy()

    if (!isTailing)
        newPosition.headPosition = performMotion(newPosition.headPosition, stepDirection!!)

    newPosition.tailPosition = getNewTailPosition(newPosition)
    return newPosition
}

fun getNewTailPosition(currentPosition: RopePosition): Position {
    val newTailPosition = currentPosition.tailPosition.copy()
    val xAxisDiff = currentPosition.headPosition.xAxis- currentPosition.tailPosition.xAxis
    val yAxisDiff = currentPosition.headPosition.yAxis - currentPosition.tailPosition.yAxis

    if (xAxisDiff == 0 && yAxisDiff == -2) // up
        newTailPosition.yAxis--
    else if (xAxisDiff == 0 && yAxisDiff == 2) // down
        newTailPosition.yAxis++
    else if (yAxisDiff == 0 && xAxisDiff == -2)// left
        newTailPosition.xAxis--
    else if (yAxisDiff == 0 && xAxisDiff == 2) // right
        newTailPosition.xAxis++
    else if ((xAxisDiff == -2 && yAxisDiff == -1) || (xAxisDiff == -1 && yAxisDiff == -2) || (xAxisDiff == -2 && yAxisDiff == -2)) { // diagonal left up
        newTailPosition.xAxis--
        newTailPosition.yAxis--
    } else if ((xAxisDiff == 2 && yAxisDiff == -1) || (xAxisDiff == 1 && yAxisDiff == -2) || (xAxisDiff == 2 && yAxisDiff == -2)) { // diagonal right up
        newTailPosition.xAxis++
        newTailPosition.yAxis--
    } else if ((xAxisDiff == -2 && yAxisDiff == 1) || (xAxisDiff == -1 && yAxisDiff == 2) || (xAxisDiff == -2 && yAxisDiff == 2)) { // diagonal left down
        newTailPosition.xAxis--
        newTailPosition.yAxis++
    } else if ((xAxisDiff == 2 && yAxisDiff == 1) || (xAxisDiff == 1 && yAxisDiff == 2) || (xAxisDiff == 2 && yAxisDiff == 2)) { // diagonal right down
        newTailPosition.xAxis++
        newTailPosition.yAxis++
    }

    return newTailPosition
}

class Puzzle09P2 : Puzzle() {
    override fun solve(lines: List<String>): String {
        val allMotions = getMotions(lines)

        var tailingPositions = Array(9) { RopePosition(
            Position(0, 0),
            Position(0, 0)
        )}

        val visitedTailPositions = mutableSetOf<Position>()
        visitedTailPositions.add(tailingPositions[8].tailPosition)

        for (motion in allMotions) {
            for (stepCount in 0 until motion.amountOfSteps) {
                val firstTailingPosition = tailingPositions[0]
                tailingPositions[0] = performStep(firstTailingPosition.copy(), motion.direction)

                for (i in 1..8) {
                    tailingPositions[i].headPosition = tailingPositions[i-1].tailPosition
                    tailingPositions[i] = performStep(tailingPositions[i].copy(), null, true)
                }

                visitedTailPositions.add(tailingPositions[8].tailPosition)
            }
        }

        return visitedTailPositions.size.toString()
    }
}