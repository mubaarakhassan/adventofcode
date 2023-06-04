package Day09

import readInput

fun main() {
    val input = readInput("Day09")
    println("Sum of the low point is ${part1(input)}")
    println("Sum of the three largest basins ${part2(input)}" )
}

fun part1(input: List<String>): Int {

    val results = mutableListOf<Int>()

    for ((i, items) in input.withIndex()){
        loop@ for ((j, item) in items.withIndex()) {
            val up = i - 1
            val down = i + 1
            val left = j - 1
            val right = j + 1
            val point = item.digitToInt()

            // Check if the current location is lower than its adjacent locations
            // If not, skip to the next location
            if(up >= 0 && point > input[up][j].digitToInt()) continue@loop

            if(down <= (input.size - 1) && point > input[down][j].digitToInt()) continue@loop

            if(left >= 0 && point > input[i][left].digitToInt()) continue@loop

            if(right <= (items.length - 1) && point > input[i][right].digitToInt()) continue@loop

            results.add(point + 1)
        }
    }
    return results.sum()
}

fun part2(input: List<String>): Int {

    // Objective: Find the sizes of the three largest basins and calculate their product.

    // Step 1: Identify the low points
    // Step 2: Assign locations to basins
    // Step 3: Calculate the size of each basin
    // Step 4: Find the three largest basins
    // Step 5: Multiply the sizes together

    val heightMap = input.map { row ->
        row.map { it.toString().toInt() }
    }

    val numRows = heightMap.size
    val numCols = heightMap[0].size

    val visited = mutableSetOf<Pair<Int, Int>>()
    val basinSizes = mutableMapOf<Pair<Int, Int>, Int>()

    val directions = listOf(
        Pair(-1, 0),  // Up
        Pair(1, 0),   // Down
        Pair(0, -1),  // Left
        Pair(0, 1)    // Right
    )

    // Depth-first search (DFS) function to explore basins from each low point
    fun dfs(point: Pair<Int, Int>, basin: Pair<Int, Int>) {
        visited.add(point)
        basinSizes[basin] = basinSizes.getOrDefault(basin, 0) + 1

        for (direction in directions) {
            val newRow = point.first + direction.first
            val newCol = point.second + direction.second
            val newPoint = Pair(newRow, newCol)

            if (newRow in 0 until numRows && newCol in 0 until numCols) {
                val height = heightMap[newRow][newCol]

                if (height < 9 && newPoint !in visited) {
                    dfs(newPoint, basin)
                }
            }
        }
    }

    for (i in 0 until numRows) {
        for (j in 0 until numCols) {
            val point = Pair(i, j)
            if (point !in visited && heightMap[i][j] < 9) {
                dfs(point, point)
            }
        }
    }

    val largestBasins = basinSizes.values.sortedDescending().take(3)
    return largestBasins.reduce { acc, basinSize -> acc * basinSize }
}

