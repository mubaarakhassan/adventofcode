package Day11

import readInput

fun main() {
    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    // Objective: Count total flashes after 100 steps
    // Step 1: Parse octopus energy levels into 2D grid
    // Step 2: For each step, increase all energy by 1
    // Step 3: Flash octopi with energy > 9, increasing neighbors
    // Step 4: Reset flashed octopi to 0, count flashes

    val grid = input.map { it.map { c -> c.digitToInt() }.toMutableList() }.toMutableList()
    var totalFlashes = 0

    repeat(100) {
        totalFlashes += simulateStep(grid)
    }

    return totalFlashes
}

fun part2(input: List<String>): Int {
    // Objective: Find the step when all octopi flash simultaneously
    // Step 1: Same simulation as part 1
    // Step 2: Check if all octopi have energy 0 (all flashed)

    val grid = input.map { it.map { c -> c.digitToInt() }.toMutableList() }.toMutableList()
    var step = 0

    while (true) {
        step++
        simulateStep(grid)

        if (grid.all { row -> row.all { it == 0 } }) {
            return step
        }
    }
}

fun simulateStep(grid: MutableList<MutableList<Int>>): Int {
    val flashed = mutableSetOf<Pair<Int, Int>>()
    val toFlash = mutableListOf<Pair<Int, Int>>()

    // Increase all energy levels by 1
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            grid[i][j]++
            if (grid[i][j] > 9) {
                toFlash.add(i to j)
            }
        }
    }

    // Process flashes
    while (toFlash.isNotEmpty()) {
        val (row, col) = toFlash.removeFirst()
        if ((row to col) in flashed) continue

        flashed.add(row to col)

        // Increase energy of adjacent octopi
        for (dr in -1..1) {
            for (dc in -1..1) {
                val newRow = row + dr
                val newCol = col + dc
                if (newRow in grid.indices && newCol in grid[0].indices &&
                    (newRow to newCol) !in flashed) {
                    grid[newRow][newCol]++
                    if (grid[newRow][newCol] > 9) {
                        toFlash.add(newRow to newCol)
                    }
                }
            }
        }
    }

    // Reset flashed octopi to 0
    flashed.forEach { (row, col) -> grid[row][col] = 0 }

    return flashed.size
}