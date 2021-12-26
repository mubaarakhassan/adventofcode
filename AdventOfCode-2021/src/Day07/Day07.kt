package Day07

import readInput
import kotlin.math.abs

fun main() {
    val input = readInput("Day07/Day07")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    val results = mutableListOf<Int>()
    var positions = input.first().split(',').map { it.toInt() }

    positions.forEach { position ->
        results.add(positions.sumOf { abs(it - position) })
    }

    return results.minOrNull() ?: 0
}

fun part2(input: List<String>): Int {
    val results = mutableListOf<Pair<Int, Int>>()
    var positions = input.first().split(',').map { it.toInt() }

    // Generalized formula: T(n) = n*(n+1)/2 we are using for retrieving the partial sum of n.
    // (see for more info about the formula: https://en.wikipedia.org/wiki/1_%2B_2_%2B_3_%2B_4_%2B_%E2%8B%AF)
    for (position in positions){
        results.add(Pair(position, positions.sumOf {
            val n = abs(it - position)
            (n * (n+1)) / 2
        }))
    }

    val minimum = results.minByOrNull { it.second }

    return minimum?.second ?: 0;
}