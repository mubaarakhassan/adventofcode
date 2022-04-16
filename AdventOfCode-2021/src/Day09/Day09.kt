package Day09

import readInput

fun main() {
    val input = readInput("Day09")
    println("Sum of the low point is ${println(part1(input))}")
    //println(part2(input))
}

fun part1(input: List<String>): Int {
    // Basically loop through the initial input values and then loop again inside the value you are looping from.
    // While looping through check if the nested loop value check the four adjacent locations (up, down, left, and right)
    // if the value is lower add it to the results array. e.g. ;
    // 1, 0, 5 and 5 (the lowest values are from left to right and top to bottom).
    //  2*1*9994321*0*
    //  3987894921
    //  98*5*6789892
    //  8767896789
    //  989996*5*678

    val results = mutableListOf<Int>()

    for ((i, items) in input.withIndex()){
        loop@ for ((j, item) in items.withIndex()) {
            val up = i - 1
            val down = i + 1
            val left = j - 1
            val right = j + 1
            val point = item.digitToInt()

            if(up >= 0 && point > input[up][j].digitToInt()) continue@loop

            if(down <= (input.size - 1) && point > input[down][j].digitToInt()) continue@loop

            if(left >= 0 && point > input[i][left].digitToInt()) continue@loop

            if(right <= (items.length - 1) && point > input[i][right].digitToInt()) continue@loop

            println("We found the lowest point which is $point")
            results.add(point + 1)
        }
    }
    return results.sum()
}
