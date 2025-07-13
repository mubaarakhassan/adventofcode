package Day20

import readInput

fun main() {
    val input = readInput("Day20")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    // Objective: Apply image enhancement algorithm 2 times, count lit pixels
    // Step 1: Parse enhancement algorithm and initial image
    // Step 2: Apply enhancement twice
    // Step 3: Count '#' pixels

    val (algorithm, image) = parseInput(input)
    val enhanced = (1..2).fold(image) { img, step -> enhance(img, algorithm, step) }
    return enhanced.sumOf { row -> row.count { it == '#' } }
}

fun part2(input: List<String>): Int {
    // Objective: Same as part 1 but apply 50 times

    val (algorithm, image) = parseInput(input)
    val enhanced = (1..50).fold(image) { img, step -> enhance(img, algorithm, step) }
    return enhanced.sumOf { row -> row.count { it == '#' } }
}

fun parseInput(input: List<String>): kotlin.Pair<String, List<String>> {
    val algorithm = input[0]
    val image = input.drop(2)
    return algorithm to image
}

fun enhance(image: List<String>, algorithm: String, step: Int): List<String> {
    val padding = 3
    val rows = image.size
    val cols = image[0].length

    // Determine infinite area character
    // If algorithm[0] is '#', infinite area flips each step
    val infiniteChar = when {
        algorithm[0] == '.' -> '.'
        step % 2 == 1 -> '#'
        else -> '.'
    }

    val paddedImage = (0 until rows + 2 * padding).map { row ->
        (0 until cols + 2 * padding).map { col ->
            val imageRow = row - padding
            val imageCol = col - padding
            when {
                imageRow in 0 until rows && imageCol in 0 until cols -> image[imageRow][imageCol]
                else -> infiniteChar
            }
        }.joinToString("")
    }

    return (1 until paddedImage.size - 1).map { row ->
        (1 until paddedImage[0].length - 1).map { col ->
            val index = getEnhancementIndex(paddedImage, row, col)
            algorithm[index]
        }.joinToString("")
    }
}

fun getEnhancementIndex(image: List<String>, row: Int, col: Int): Int {
    var binary = ""
    for (dr in -1..1) {
        for (dc in -1..1) {
            val char = image[row + dr][col + dc]
            binary += if (char == '#') '1' else '0'
        }
    }
    return binary.toInt(2)
}