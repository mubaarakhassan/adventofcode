package Day15

import readInput
import java.util.*

fun main() {
    val input = readInput("Day15")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    // Objective: Find shortest path from top-left to bottom-right
    // Step 1: Parse risk level grid
    // Step 2: Use Dijkstra's algorithm to find minimum cost path
    // Step 3: Return the total risk of the path

    val grid = input.map { it.map { c -> c.digitToInt() } }
    return findShortestPath(grid)
}

fun part2(input: List<String>): Int {
    // Objective: Same as part 1 but expand grid 5x5 with increasing risk
    // Step 1: Expand grid according to rules
    // Step 2: Apply same pathfinding algorithm

    val originalGrid = input.map { it.map { c -> c.digitToInt() } }
    val expandedGrid = expandGrid(originalGrid)
    return findShortestPath(expandedGrid)
}

fun findShortestPath(grid: List<List<Int>>): Int {
    val rows = grid.size
    val cols = grid[0].size
    val distances = Array(rows) { IntArray(cols) { Int.MAX_VALUE } }
    val pq = PriorityQueue<Triple<Int, Int, Int>>(compareBy { it.first })

    distances[0][0] = 0
    pq.offer(Triple(0, 0, 0)) // (distance, row, col)

    val directions = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

    while (pq.isNotEmpty()) {
        val (dist, row, col) = pq.poll()

        if (row == rows - 1 && col == cols - 1) return dist
        if (dist > distances[row][col]) continue

        directions.forEach { (dr, dc) ->
            val newRow = row + dr
            val newCol = col + dc

            if (newRow in 0 until rows && newCol in 0 until cols) {
                val newDist = dist + grid[newRow][newCol]
                if (newDist < distances[newRow][newCol]) {
                    distances[newRow][newCol] = newDist
                    pq.offer(Triple(newDist, newRow, newCol))
                }
            }
        }
    }

    return distances[rows - 1][cols - 1]
}

fun expandGrid(grid: List<List<Int>>): List<List<Int>> {
    val rows = grid.size
    val cols = grid[0].size

    return (0 until rows * 5).map { row ->
        (0 until cols * 5).map { col ->
            val originalRow = row % rows
            val originalCol = col % cols
            val tileRow = row / rows
            val tileCol = col / cols
            val originalRisk = grid[originalRow][originalCol]
            val newRisk = originalRisk + tileRow + tileCol
            if (newRisk > 9) newRisk - 9 else newRisk
        }
    }
}