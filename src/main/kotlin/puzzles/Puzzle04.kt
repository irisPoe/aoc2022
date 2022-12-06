package puzzles

import Puzzle

class Puzzle04P1 : Puzzle() {
    override fun solve(lines : List<String>): String =
        lines.sumOf { line ->
            val assignments = parseLine(line)

            if (doesAssignmentContainAssignment(assignments.first, assignments.second))
                1.toInt()
            else if (doesAssignmentContainAssignment(assignments.second, assignments.first))
                1
            else
                0
        }.toString()

    private fun parseLine(line: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        val splitElvesAssignment = line.split('-', ',')
        return Pair(
            Pair(splitElvesAssignment[0].toInt(), splitElvesAssignment[1].toInt()),
            Pair(splitElvesAssignment[2].toInt(), splitElvesAssignment[3].toInt()))
    }

    private fun doesAssignmentContainAssignment(assignment1: Pair<Int, Int>, assignment2: Pair<Int, Int>) =
        assignment2.first >= assignment1.first && assignment2.second <= assignment1.second
}

class Puzzle04P2 : Puzzle() {
    override fun solve(lines : List<String>): String =
        lines.sumOf { line ->
            val assignments = parseLine(line)

            if (doesAssignmentOverlapWithAssignment(assignments.first, assignments.second))
                1.toInt()
            else if (doesAssignmentOverlapWithAssignment(assignments.second, assignments.first))
                1
            else
                0
        }.toString()

        private fun parseLine(line: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {
            val splitElvesAssignment = line.split('-', ',')
            return Pair(
                Pair(splitElvesAssignment[0].toInt(), splitElvesAssignment[1].toInt()),
                Pair(splitElvesAssignment[2].toInt(), splitElvesAssignment[3].toInt()))
        }

        private fun doesAssignmentOverlapWithAssignment(assignment1: Pair<Int, Int>, assignment2: Pair<Int, Int>) =
            (assignment1.first .. assignment1.second).any { number ->
                (assignment2.first .. assignment2.second).contains(number) }
}