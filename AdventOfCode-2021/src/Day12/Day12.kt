package Day12

import readInput

fun main() {
    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    // Objective: Count paths from 'start' to 'end' visiting small caves at most once
    // Step 1: Build adjacency map of cave connections
    // Step 2: Use DFS to explore all paths
    // Step 3: Track visited small caves (lowercase)

    val connections = buildConnectionMap(input)
    return countPaths(connections, "start", "end", setOf(), false)
}

fun part2(input: List<String>): Int {
    // Objective: Same as part 1 but one small cave can be visited twice
    // Step 1: Same connection map
    // Step 2: Track if we've used our "twice visit" allowance

    val connections = buildConnectionMap(input)
    return countPaths(connections, "start", "end", setOf(), true)
}

fun buildConnectionMap(input: List<String>): Map<String, List<String>> {
    return input.flatMap { line ->
        val (a, b) = line.split("-")
        listOf(a to b, b to a)
    }.groupBy({ it.first }, { it.second })
}

fun countPaths(
    connections: Map<String, List<String>>,
    current: String,
    target: String,
    visited: Set<String>,
    canVisitTwice: Boolean
): Int {
    if (current == target) return 1
    if (current == "start" && visited.isNotEmpty()) return 0
    if (current.all { it.isLowerCase() } && current in visited) {
        if (!canVisitTwice || current == "end") return 0
        // Use our "visit twice" allowance
        return connections[current]?.sumOf {
            countPaths(connections, it, target, visited + current, false)
        } ?: 0
    }

    val newVisited = if (current.all { it.isLowerCase() }) visited + current else visited

    return connections[current]?.sumOf {
        countPaths(connections, it, target, newVisited, canVisitTwice)
    } ?: 0
}