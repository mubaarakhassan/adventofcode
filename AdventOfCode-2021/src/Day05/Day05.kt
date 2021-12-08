package Day05

import readInput

fun main() {
    val input = readInput("Day05/Day05")

    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    // Objective: The submarine helpfully produces a list of nearby lines of vents. Each line of vents is given
    // as a line segment in the format x1,y1 -> x2,y2 where x1,y1 are the coordinates of one end the line segment and
    // x2,y2 are the coordinates of the other end. These line segments include the points at both ends. In other words:
    //  - An entry like 1,1 -> 1,3 covers points 1,1, 1,2, and 1,3.
    //  - An entry like 9,7 -> 7,7 covers points 9,7, 8,7, and 7,7.
    // To avoid the most dangerous areas, you need to determine the number of points where at least two lines overlap.
    // At how many points do at least two lines overlap?
    // For now, only consider horizontal and vertical lines: lines where either x1 = x2 or y1 = y2.

    // Step 1: Get the largest number
    // Step 2: Create a multidimensional diagram with the size as the largest  (size x size).
    // Step 3: Check if the line segments is possible. Lines where either x1 = x2 or y1 = y2 is possible.
    // Step 4: Iterate through the lines and mark the diagram with the points.
    // Step 5: Repeat step 4 until there are no more lines left.
    // Step 6: Count the amount of points the two lines overlap.

    var points = input.map { it -> it.split(" -> ").map { it -> it.split(",").map { it.toInt() } }.flatten() }.filter { it[0] == it[2] || it[1] == it[3] }
    var size = points.maxOf { it -> it.maxOf { it } }
    var diagram = Array(size + 1) { IntArray(size + 1) }

    for (point in points){
        drawLineFromPoints(point[0], point[1], point[2], point[3], diagram)
    }

    return diagram.sumOf() { it -> it.count() { it > 1 } }
}

fun part2(input: List<String>): Int {
    // Objective: Same as part 1 but this we will allow horizontal lines.

    // Step 1: Get the largest number
    // Step 2: Create a multidimensional diagram with the size as the largest  (size x size).
    // Step 3: Iterate through the lines and mark the diagram with the points.
    // Step 4: Repeat step 3 until there are no more lines left.
    // Step 5: Count the amount of points the two lines overlap.

    var points = input.map { it -> it.split(" -> ").map { it -> it.split(",").map { it.toInt() } }.flatten() }
    var size = points.maxOf { it -> it.maxOf { it } }
    var diagram = Array(size + 1) { IntArray(size + 1) }

    for (point in points){
        drawLineFromPoints(point[0], point[1], point[2], point[3], diagram)
    }

    return diagram.sumOf() { it -> it.count() { it > 1 } }
}

fun drawLineFromPoints(x0: Int, y0: Int, x1: Int, y1: Int, diagram: Array<IntArray>){
    var x = x0 - x1
    var y = y0 - y1

    while (x != 0 && y != 0){
        diagram[y + y1][x + x1] = diagram[y + y1][x + x1] + 1;

        // check if number is negative or positive
        if(x > 0)  x-- else x++
        if(y > 0)  y-- else y++

        if(x == 0 || y == 0){
            diagram[y + y1][x + x1] = diagram[y + y1][x + x1] + 1;
        }
    }

    while (x != 0 || y != 0)
    {
        diagram[y + y1][x + x1] = diagram[y + y1][x + x1] + 1;

        // check if number is negative or positive
        if(x == 0 && y > 0)  y--; if(y < 0) y++
        if(y == 0 && x > 0)  x--; if(x < 0) x++

        if(y == 0 && x == 0){
            diagram[y + y1][x + x1] = diagram[y + y1][x + x1] + 1;
        }
    }
}