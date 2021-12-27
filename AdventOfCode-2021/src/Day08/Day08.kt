package Day08

import readInput

fun main() {
    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    var display = SevenSegmentDisplay(mapOf(1 to 2, 4 to 4, 7 to 3, 8 to 7))
    var patterns = input.map {
            val split = it.split('|')
            Pair(split[0].split(" "), split[1].split(" "))
    }

    patterns.forEach { it ->
        // We only need to retrieve the second part of the patterns
        it.second.forEach {
            display.search(it)
        }
    }

    return display.occurrences()
}

fun part2(input: List<String>): Int {
    var output = 0;
    var display = SevenSegmentDisplay(mapOf(0 to 6, 1 to 2, 2 to 5, 3 to 5, 4 to 4, 5 to 5, 6 to 6, 7 to 3, 8 to 7, 9 to 6))

    var patterns = input.map {
        val split = it.split('|')
        Pair(
            split[0].trim(' ').split(" ").map { item -> item.toSortedSet().joinToString("") },
            split[1].trim(' ').split(" ").map { item -> item.toSortedSet().joinToString("") }
        )
    }

    patterns.forEach { it ->
        // We only want to map the known values first
        it.first.filter { it.length != 5 || it.length != 6 }.forEach {
            display.map(it)
        }

        // Now we can filter the 5 and 6
        it.first.filter { it.length == 5 || it.length == 6 }.forEach {
            display.map(it)
        }

        var decoded = "";

        it.second.forEach {
            decoded += display.decode(it)
        }

        println("${it.first}: $decoded")

        output += decoded.toInt()
    }

    return output
}