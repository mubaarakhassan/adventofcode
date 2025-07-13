package Day17

import readInput
import kotlin.math.*

fun main() {
    val input = readInput("Day17")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    // Objective: Find the highest Y position that still hits the target
    // Step 1: Parse target area coordinates
    // Step 2: For optimal height, vy should be as high as possible
    // Step 3: Maximum vy is |minY| - 1 (to not overshoot in one step)
    // Step 4: Calculate max height using triangular number formula

    val (_, _, minY, _) = parseTarget(input[0])
    val maxVy = abs(minY) - 1
    return maxVy * (maxVy + 1) / 2
}

fun part2(input: List<String>): Int {
    // Objective: Count all initial velocities that hit the target
    // Step 1: Parse target area
    // Step 2: Try all reasonable velocity combinations
    // Step 3: Simulate trajectory for each combination

    val (minX, maxX, minY, maxY) = parseTarget(input[0])
    var count = 0

    // vx range: minimum to reach target, maximum not to overshoot immediately
    for (vx in 1..maxX) {
        // vy range: minimum not to undershoot, maximum from part 1
        for (vy in minY..abs(minY)) {
            if (hitsTarget(vx, vy, minX, maxX, minY, maxY)) {
                count++
            }
        }
    }

    return count
}

fun parseTarget(input: String): List<Int> {
    val coords = input.removePrefix("target area: ")
        .split(", ")
        .flatMap { it.drop(2).split("..").map { it.toInt() } }
    return coords
}

fun hitsTarget(vx: Int, vy: Int, minX: Int, maxX: Int, minY: Int, maxY: Int): Boolean {
    var x = 0
    var y = 0
    var velX = vx
    var velY = vy

    while (x <= maxX && y >= minY) {
        x += velX
        y += velY

        if (x in minX..maxX && y in minY..maxY) return true

        velX = maxOf(0, velX - 1)
        velY--
    }

    return false
}