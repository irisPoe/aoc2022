package puzzles

import Puzzle

class Puzzle05P1 : Puzzle() {
    override fun solve(lines : List<String>): String {
        val crateStack = lines
            .takeWhile { line -> line.startsWith("  ") || line.startsWith('[') }
            .getCrateStacks()

        lines
            .dropWhile { line -> !line.startsWith("move") }
            .map { line -> line.split(' ') }
            .map { splitLine -> Move(splitLine[3].toInt() - 1, splitLine[5].toInt() - 1, splitLine[1].toInt()) }
            .forEach { move -> move.doMove(crateStack)}

        return crateStack.joinToString(separator = "") { stack ->
            stack.crates.firstOrNull()?.replace("[", "")?.replace("]", "") ?: ""
        }
    }

    private fun Move.doMove(cratestacks: List<CrateStack>) {
        val elementsToMove = cratestacks[fromPosition].crates.take(amount)
        cratestacks[toPosition].crates.addAll(0, elementsToMove.reversed())

        cratestacks[fromPosition].crates = cratestacks[fromPosition].crates.drop(amount).toMutableList()
    }
}

fun List<String>.getCrateStacks(): MutableList<CrateStack> {
    val lines = this.map { line ->
        line
            .chunked(4)
            .map { chunk -> chunk.trim() }
    }

    val maxLineSize = lines.maxOf { it.size }
    val crateStacks = MutableList(maxLineSize) {
        CrateStack(mutableListOf())
    }

    for (index in 0..maxLineSize) {
        lines.map { line ->
            if (line.size > index && !line[index].isNullOrEmpty())
                crateStacks[index].crates.add(line[index])
        }
    }

    crateStacks.forEach { stack -> stack.crates }
    return crateStacks
}

data class CrateStack (var crates: MutableList<String>)
data class Move(val fromPosition: Int, val toPosition: Int, val amount: Int)

class Puzzle05P2 : Puzzle() {
    override fun solve(lines: List<String>): String {
        val crateStack = lines
            .takeWhile { line -> line.startsWith("  ") || line.startsWith('[') }
            .getCrateStacks()

        lines
            .dropWhile { line -> !line.startsWith("move") }
            .map { line -> line.split(' ') }
            .map { splitLine -> Move(splitLine[3].toInt() - 1, splitLine[5].toInt() - 1, splitLine[1].toInt()) }
            .forEach { move -> move.doMove(crateStack)}

        return crateStack.joinToString(separator = "") { stack ->
            stack.crates.firstOrNull()?.replace("[", "")?.replace("]", "") ?: ""
        }
    }

    private fun Move.doMove(cratestacks: List<CrateStack>) {
        val elementsToMove = cratestacks[fromPosition].crates.take(amount)
        cratestacks[toPosition].crates.addAll(0, elementsToMove)

        cratestacks[fromPosition].crates = cratestacks[fromPosition].crates.drop(amount).toMutableList()
    }
}