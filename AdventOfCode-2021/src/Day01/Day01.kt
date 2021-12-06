package day01

import readInput

fun main() {
    val input = readInput("Day01/Day01")

    println(part1(input))
    println(part2(input))
}


fun part1(input: List<String>): Int {
    // Objective: Count the number of times a depth measurement increases from the previous measurement

    // Step 1: Read the files line by line
    // Step 2: Retrieve the current and previous measurement value.
    // Step 3: Compare the retrieved values if it has increased or decreased from the previous measurement.

    val depths = input.map { it.toInt() };
    var count = 0;

    depths.zipWithNext(){s1, s2 -> if(s1 < s2) count++;}
    return count;
}

fun part2(input: List<String>): Int {
    // Objective: Start by comparing the first and second three-measurement windows. The measurements in the first
    // window are marked A (199, 200, 208); their sum is 199 + 200 + 208 = 607. The second window is marked B (200, 208, 210);
    // Count the number of times the sum of measurements in this sliding window increases

    // Step 1: Read the files line by line
    // Step 2: Retrieve the first, second and third measurement windows and get the sum.
    // Step 3: Compare the retrieved sum value with the previous measurement and check if it has increased or decreased.

    val depths = input.map { it.toInt() };
    var count = 0;
    val sums = depths.windowed(3) { it.sum() }

    sums.zipWithNext(){s1, s2 -> if(s1 < s2) count++;}
    return count;
}