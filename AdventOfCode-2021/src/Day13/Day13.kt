package Day13

import readInput

fun main() {
    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    // Objective: Count visible dots after first fold
    // Step 1: Parse dots and fold instructions
    // Step 2: Apply first fold operation
    // Step 3: Count unique dots

    val (dots, folds) = parseInput(input)
    val foldedDots = applyFold(dots, folds.first())
    return foldedDots.size
}

fun part2(input: List<String>): String {
    // Objective: Apply all folds and display the resulting pattern
    // Step 1: Parse input
    // Step 2: Apply all folds sequentially
    // Step 3: Render the final grid as ASCII art

    val (dots, folds) = parseInput(input)
    val finalDots = folds.fold(dots) { currentDots, fold -> applyFold(currentDots, fold) }

    return renderDots(finalDots)
}

fun parseInput(input: List<String>): Pair<Set<Pair<Int, Int>>, List<Pair<Char, Int>>> {
    val emptyLineIndex = input.indexOf("")

    val dots = input.take(emptyLineIndex).map { line ->
        val (x, y) = line.split(",").map { it.toInt() }
        x to y
    }.toSet()

    val folds = input.drop(emptyLineIndex + 1).map { line ->
        val parts = line.removePrefix("fold along ").split("=")
        parts[0][0] to parts[1].toInt()
    }

    return dots to folds
}

fun applyFold(dots: Set<Pair<Int, Int>>, fold: Pair<Char, Int>): Set<Pair<Int, Int>> {
    val (axis, line) = fold

    return dots.map { (x, y) ->
        when (axis) {
            'x' -> if (x > line) (2 * line - x) to y else x to y
            'y' -> if (y > line) x to (2 * line - y) else x to y
            else -> x to y
        }
    }.toSet()
}

fun renderDots(dots: Set<Pair<Int, Int>>): String {
    val maxX = dots.maxOf { it.first }
    val maxY = dots.maxOf { it.second }

    return (0..maxY).joinToString("\n") { y ->
        (0..maxX).joinToString("") { x ->
            if ((x to y) in dots) "#" else "."
        }
    }
}