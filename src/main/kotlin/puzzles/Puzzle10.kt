package puzzles

import Puzzle

class Puzzle10P1 : Puzzle() {
    override fun solve(lines : List<String>): String {
        val cpuCommands = getCPUCommands(lines)
        var xValue = 1
        var currentCycle = 0
        var interestingSignalStrengthSum = 0

        var index: Int = 0
        for (command in cpuCommands) {
            for (commandCycle in 0 until command.type.getCycleTime()) {
                if (commandCycle == command.type.getCycleTime() - 1)
                    xValue = command.type.adaptValueOnCylceEnd(xValue, command.value)

                currentCycle++

                val signalStrengthVal = getInterestingSignalStrength(currentCycle, xValue)
                interestingSignalStrengthSum += signalStrengthVal
            }
            index++
        }

        return interestingSignalStrengthSum.toString()
    }
}

fun getInterestingSignalStrength(currentCycle: Int, currentXValue: Int): Int =
    if (currentCycle == 19 || (currentCycle - 19) % 40 == 0)
        (currentCycle + 1) * currentXValue
    else
        0

fun getCPUCommands(lines: List<String>): List<CPUCommand> =
    lines.map { line ->
        line.split(' ').let { splitLine ->
            when (splitLine[0]) {
                "noop" -> CPUCommand(CPUCommandType.NOOB, null)
                else -> CPUCommand(CPUCommandType.ADD, splitLine[1].toInt())
        } }
    }

data class CPUCommand (val type: CPUCommandType, val value: Int?)

enum class CPUCommandType {
    NOOB {
        override fun getCycleTime(): Int = 1
        override fun adaptValueOnCylceEnd(initialValue: Int, value: Int?): Int = initialValue
    },
    ADD {
        override fun getCycleTime(): Int = 2
        override fun adaptValueOnCylceEnd(initialValue: Int, value: Int?): Int = initialValue + value!!
    };

    abstract fun getCycleTime(): Int
    abstract fun adaptValueOnCylceEnd(initialValue: Int, value: Int?): Int
}


class Puzzle10P2 : Puzzle() {
    override fun solve(lines: List<String>): String {
        val cpuCommands = getCPUCommands(lines)
        var xValue = 1
        var currentCycle = 0

        var index: Int = 0
        for (command in cpuCommands) {
            for (commandCycle in 0 until command.type.getCycleTime()) {
                if (isInSpritePosition(currentCycle % 40, xValue))
                    print('#')
                else
                    print('.')

                if ((currentCycle + 1) % 40 == 0)
                    println()

                if (commandCycle == command.type.getCycleTime() - 1)
                    xValue = command.type.adaptValueOnCylceEnd(xValue, command.value)

                currentCycle++
            }
            index++
        }

        println()
        return "no solution needed"
    }

    fun isInSpritePosition(horizontalScreenPosition: Int, currentXValue: Int): Boolean =
        horizontalScreenPosition == currentXValue - 1 || horizontalScreenPosition == currentXValue || horizontalScreenPosition == currentXValue + 1
}