package day02

import readInput

fun main() {
    val input = readInput("Day02/Day02")

    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    // Objective:  The submarine can take a series of commands like forward, down and up.
    //  - forward X increases the horizontal position by X units.
    //  - down X increases the depth by X units.
    //  - up X decreases the depth by X units.
    // After following these instructions, you would have a final horizontal position and depth.
    // What do you get if you multiply your final horizontal position by your final depth?

    // Step 1: Read the files line by line.
    // Step 2: Check if the line is going forward, down or up.
    // Step 3: Add or decrease it to horizontal position/depth.
    // Step 4: Multiply the final horizontal position and depth.
    var horizontal = 0;
    var depth = 0;

    input.forEach{ s ->
        var (direction, adjustment) = s.split(" ");
        when (direction) {
            "forward" -> horizontal += adjustment.toInt()
            "down" -> depth += adjustment.toInt()
            "up" -> depth -= adjustment.toInt()
        }
    }
    return (horizontal * depth);
}

fun part2(input: List<String>): Int {
    // Objective:   The submarine can take a series of commands like forward, down, up and aim.
    //  - down X increases the aim by X units.
    //  - up X decreases the aim by X units.
    //  - forward X does two things:
    //      - It increases your horizontal position by X units.
    //      - It increases your depth by your aim multiplied by X.
    // After following these instructions, you would have a final horizontal position and depth.
    // What do you get if you multiply your final horizontal position by your final depth?

    // Step 1: Read the files line by line.
    // Step 2: Check if the line is going forward, down or up.
    // Step 3: Add or decrease it to horizontal position/depth/aim.
    // Step 4: Multiply the final horizontal position and depth.
    var horizontal = 0;
    var depth = 0;
    var aim = 0;

    input.forEach{ s ->
        var (direction, adjustment) = s.split(" ");
        when (direction) {
            "forward" -> { horizontal += adjustment.toInt();  depth += aim * adjustment.toInt();}
            "down" -> aim += adjustment.toInt();
            "up" -> aim -= adjustment.toInt();
        }
    }
    return (horizontal * depth);
}
