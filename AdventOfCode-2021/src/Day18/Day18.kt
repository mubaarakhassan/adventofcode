package Day18

import readInput

fun main() {
    val input = readInput("Day18")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Long {
    // Objective: Add all snailfish numbers and calculate magnitude
    // Step 1: Parse each line as a snailfish number
    // Step 2: Add them sequentially, reducing after each addition
    // Step 3: Calculate magnitude of final result

    return input.map { parseSnailfish(it) }
        .reduce { acc, num -> add(acc, num) }
        .magnitude()
}

fun part2(input: List<String>): Long {
    // Objective: Find the largest magnitude from adding any two numbers
    // Step 1: Parse all numbers
    // Step 2: Try all pairs (both orders since addition isn't commutative)
    // Step 3: Return maximum magnitude

    val numbers = input.map { parseSnailfish(it) }
    return numbers.indices.maxOf { i ->
        numbers.indices.filter { it != i }.maxOf { j ->
            add(numbers[i].copy(), numbers[j].copy()).magnitude()
        }
    }
}

sealed class SnailfishNumber {
    abstract fun magnitude(): Long
    abstract fun copy(): SnailfishNumber
}

data class Regular(var value: Int) : SnailfishNumber() {
    override fun magnitude() = value.toLong()
    override fun copy() = Regular(value)
}

data class Pair(var left: SnailfishNumber, var right: SnailfishNumber) : SnailfishNumber() {
    override fun magnitude() = 3 * left.magnitude() + 2 * right.magnitude()
    override fun copy() = Pair(left.copy(), right.copy())
}

fun parseSnailfish(input: String): SnailfishNumber {
    val tokens = input.replace(",", "").toList()
    return parse(tokens, 0).first
}

fun parse(tokens: List<Char>, index: Int): kotlin.Pair<SnailfishNumber, Int> {
    return if (tokens[index] == '[') {
        val (left, leftEnd) = parse(tokens, index + 1)
        val (right, rightEnd) = parse(tokens, leftEnd)
        Pair(left, right) to rightEnd + 1
    } else {
        Regular(tokens[index].digitToInt()) to index + 1
    }
}

fun add(left: SnailfishNumber, right: SnailfishNumber): SnailfishNumber {
    val result = Pair(left, right)
    reduce(result)
    return result
}

fun reduce(number: SnailfishNumber) {
    while (true) {
        if (explode(number, 0).first) continue
        if (split(number)) continue
        break
    }
}

fun explode(number: SnailfishNumber, depth: Int): kotlin.Pair<Boolean, kotlin.Pair<Int?, Int?>> {
    when (number) {
        is Regular -> return false to (null to null)
        is Pair -> {
            if (depth >= 4 && number.left is Regular && number.right is Regular) {
                val leftVal = (number.left as Regular).value
                val rightVal = (number.right as Regular).value
                return true to (leftVal to rightVal)
            }

            val (leftExploded, leftValues) = explode(number.left, depth + 1)
            if (leftExploded) {
                if (leftValues.second != null) {
                    addToLeftmost(number.right, leftValues.second!!)
                }
                if (leftValues.first != null && leftValues.second != null) {
                    number.left = Regular(0)
                }
                return true to (leftValues.first to null)
            }

            val (rightExploded, rightValues) = explode(number.right, depth + 1)
            if (rightExploded) {
                if (rightValues.first != null) {
                    addToRightmost(number.left, rightValues.first!!)
                }
                if (rightValues.first != null && rightValues.second != null) {
                    number.right = Regular(0)
                }
                return true to (null to rightValues.second)
            }

            return false to (null to null)
        }
    }
}

fun split(number: SnailfishNumber): Boolean {
    when (number) {
        is Regular -> return false
        is Pair -> {
            if (number.left is Regular && (number.left as Regular).value >= 10) {
                val value = (number.left as Regular).value
                number.left = Pair(Regular(value / 2), Regular((value + 1) / 2))
                return true
            }
            if (split(number.left)) return true

            if (number.right is Regular && (number.right as Regular).value >= 10) {
                val value = (number.right as Regular).value
                number.right = Pair(Regular(value / 2), Regular((value + 1) / 2))
                return true
            }
            return split(number.right)
        }
    }
}

fun addToLeftmost(number: SnailfishNumber, value: Int) {
    when (number) {
        is Regular -> number.value += value
        is Pair -> addToLeftmost(number.left, value)
    }
}

fun addToRightmost(number: SnailfishNumber, value: Int) {
    when (number) {
        is Regular -> number.value += value
        is Pair -> addToRightmost(number.right, value)
    }
}