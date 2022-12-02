package puzzles

import Puzzle

class Puzzle01P1 : Puzzle() {
    override fun solve(lines : List<String>): String {
        var maxAmountOfCalories = 0
        var noOfCaloriesForCurrentElve = 0

        for (line in lines) {
            if (line.isNullOrEmpty()) {
                if (noOfCaloriesForCurrentElve > maxAmountOfCalories) {
                    maxAmountOfCalories = noOfCaloriesForCurrentElve
                }

                noOfCaloriesForCurrentElve = 0
            } else {
                noOfCaloriesForCurrentElve += line.toInt()
            }
        }
        return maxAmountOfCalories.toString()
    }
}

class Puzzle01P2 : Puzzle() {
    override fun solve(lines: List<String>): String {
        var caloriesPerElve = mutableListOf<Int>()
        var noOfCaloriesForCurrentElve = 0

        for (line in lines) {
            if (line.isNullOrEmpty()) {
                caloriesPerElve.add(noOfCaloriesForCurrentElve)
                noOfCaloriesForCurrentElve = 0
            } else {
                noOfCaloriesForCurrentElve += line.toInt()
            }
        }

        caloriesPerElve.add(noOfCaloriesForCurrentElve)

        caloriesPerElve.sortDescending()
        var result = caloriesPerElve.take(3).reduce{ y, vars -> y + vars }
        return result.toString()
    }

}