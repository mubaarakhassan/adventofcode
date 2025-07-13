package Day14

import readInput

fun main() {
    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Long {
    // Objective: Apply polymerization rules 10 times, find most common - least common
    // Step 1: Parse template and rules
    // Step 2: Track pair counts instead of full string (efficient)
    // Step 3: Apply rules 10 times
    // Step 4: Count character frequencies and find difference

    return solve(input, 10)
}

fun part2(input: List<String>): Long {
    // Objective: Same as part 1 but 40 iterations
    // Step 1: Same approach but more iterations

    return solve(input, 40)
}

fun solve(input: List<String>, steps: Int): Long {
    val template = input[0]
    val rules = input.drop(2).associate { line ->
        val (pair, insert) = line.split(" -> ")
        pair to insert[0]
    }

    // Count pairs in initial template
    var pairCounts = template.zipWithNext()
        .groupingBy { "${it.first}${it.second}" }
        .eachCount()
        .mapValues { it.value.toLong() }
        .toMutableMap()

    repeat(steps) {
        val newPairCounts = mutableMapOf<String, Long>()

        pairCounts.forEach { (pair, count) ->
            val insert = rules[pair]
            if (insert != null) {
                val leftPair = "${pair[0]}$insert"
                val rightPair = "$insert${pair[1]}"
                newPairCounts[leftPair] = newPairCounts.getOrDefault(leftPair, 0) + count
                newPairCounts[rightPair] = newPairCounts.getOrDefault(rightPair, 0) + count
            } else {
                newPairCounts[pair] = newPairCounts.getOrDefault(pair, 0) + count
            }
        }

        pairCounts = newPairCounts
    }

    // Count character frequencies
    val charCounts = mutableMapOf<Char, Long>()
    pairCounts.forEach { (pair, count) ->
        charCounts[pair[0]] = charCounts.getOrDefault(pair[0], 0) + count
        charCounts[pair[1]] = charCounts.getOrDefault(pair[1], 0) + count
    }

    // Add first and last characters (they're only counted once)
    charCounts[template.first()] = charCounts.getOrDefault(template.first(), 0) + 1
    charCounts[template.last()] = charCounts.getOrDefault(template.last(), 0) + 1

    // Divide by 2 since each char is counted twice (except first and last)
    val actualCounts = charCounts.mapValues { it.value / 2 }

    return actualCounts.maxOf { it.value } - actualCounts.minOf { it.value }
}