package puzzles

import Puzzle

class Puzzle07P1 : Puzzle() {
    override fun solve(lines : List<String>): String {
        val rootNode = parseFilesystem(lines)
        return getSizeOfNodeBigger100000(rootNode).toString()
    }
}

fun getSizeOfNodeBigger100000 (currentNode: FileSystemNode): Long {
    var size: Long = 0

    if (currentNode.size <= 100000) {
        size += currentNode.size
    }

    for (child in currentNode.children.filter { child -> child.type == Type.DIR }) {
        size += getSizeOfNodeBigger100000(child)
    }

    return size
}

fun parseFilesystem(lines: List<String>): FileSystemNode {
    var currentNode: FileSystemNode? = FileSystemNode(
        Type.DIR, mutableListOf(), null, null, "/"
    )

    val rootNode = currentNode!!

    for (line in lines.drop(1)) {
        if (currentNode == null)
            throw RuntimeException("currentNode cannot be null")

        when (line[0]) {
            '$' -> currentNode = executeCommand(currentNode, line.substring(2))
            'd' -> currentNode.children.add(
                FileSystemNode(Type.DIR, mutableListOf(), null, currentNode, line.split(' ')[1]))
            else -> currentNode.children.add(
                FileSystemNode(Type.FILE, mutableListOf(), line.split(' ')[0].toLong(), currentNode, line.split(' ')[1])
            )
        }
    }

    return rootNode
}

fun executeCommand(currentNode: FileSystemNode, command: String): FileSystemNode? {
    val splitCommand = command.split(' ')
    return when (splitCommand[0]) {
        "ls" -> currentNode
        else -> {
            when (splitCommand[1]) {
                ".." -> currentNode.parentNode
                else -> currentNode.children.find { child -> child.name == splitCommand[1] && child.type == Type.DIR }
            }
        }
    }
}

data class FileSystemNode (
    val type: Type,
    val children: MutableList<FileSystemNode>,
    val fileSize: Long?,
    val parentNode: FileSystemNode?,
    val name: String) {
    val size: Long
        get() = if (type == Type.FILE) fileSize!! else children.sumOf { child -> child.size }
}

enum class Type {
    FILE,
    DIR
}

class Puzzle07P2 : Puzzle() {
    override fun solve(lines: List<String>): String {
        val rootNode = parseFilesystem(lines)
        val currentSpaceLeft = 70000000 - rootNode.size
        val spaceNeeded = 30000000 - currentSpaceLeft

        val sizesOfAllDirectories = getSizesAllDirectories(rootNode)
        return sizesOfAllDirectories.filter { dirSize -> dirSize >= spaceNeeded }.minOf { it }.toString()
    }
}

fun getSizesAllDirectories (currentNode: FileSystemNode): Set<Long> {
    var directorySizeList = mutableSetOf<Long>()

    for (child in currentNode.children.filter { child -> child.type == Type.DIR }) {
        directorySizeList.addAll(getSizesAllDirectories(child))
    }

    directorySizeList.add(currentNode.size)
    return directorySizeList
}
