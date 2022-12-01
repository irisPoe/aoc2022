import java.io.File

abstract class Puzzle {
    private val puzzle: String
    private val part: String

    init {
        val solverName = this.javaClass.name
        this.puzzle = solverName.takeLast(4).take(2)
        this.part = solverName.takeLast(2)
    }

    val name get() = "Puzzle: $puzzle Part: $part"

    val basePath: String get() = "puzzles/$puzzle/$part"

    fun solveFile(inputFile: File): String {
        val input = inputFile.readLines()
        return solve(input)
    }

    protected abstract fun solve(lines: List<String>): String

}