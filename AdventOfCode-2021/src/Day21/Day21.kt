package Day21

import readInput

fun main() {
    val input = readInput("Day21")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    // Objective: Play with deterministic dice, multiply losing score by dice rolls
    // Step 1: Parse starting positions
    // Step 2: Simulate game with deterministic 100-sided die
    // Step 3: Return loser score * total rolls

    val positions = input.map { it.split(": ")[1].toInt() }.toMutableList()
    val scores = mutableListOf(0, 0)
    var currentPlayer = 0
    var diceValue = 1
    var totalRolls = 0

    while ((scores.maxOrNull() ?: 0) < 1000) {
        val rollSum = (0..2).sumOf {
            val roll = diceValue
            diceValue = if (diceValue == 100) 1 else diceValue + 1
            totalRolls++
            roll
        }

        positions[currentPlayer] = ((positions[currentPlayer] - 1 + rollSum) % 10) + 1
        scores[currentPlayer] += positions[currentPlayer]

        currentPlayer = 1 - currentPlayer
    }

    return (scores.minOrNull() ?: 0) * totalRolls
}

fun part2(input: List<String>): Long {
    // Objective: Count wins in quantum game where dice splits reality
    // Step 1: Parse starting positions
    // Step 2: Use memoized recursion to count all possible outcomes
    // Step 3: Return max wins

    val positions = input.map { it.split(": ")[1].toInt() }
    val memo = mutableMapOf<GameState, kotlin.Pair<Long, Long>>()
    val (wins1, wins2) = countWins(GameState(positions[0], positions[1], 0, 0, 0), memo)
    return maxOf(wins1, wins2)
}

data class GameState(val pos1: Int, val pos2: Int, val score1: Int, val score2: Int, val player: Int)

fun countWins(state: GameState, memo: MutableMap<GameState, kotlin.Pair<Long, Long>>): kotlin.Pair<Long, Long> {
    if (state.score1 >= 21) return 1L to 0L
    if (state.score2 >= 21) return 0L to 1L

    if (state in memo) return memo[state]!!

    var wins1 = 0L
    var wins2 = 0L

    // All possible outcomes of rolling three quantum dice (sum 3-9)
    val rollOutcomes = mapOf(3 to 1, 4 to 3, 5 to 6, 6 to 7, 7 to 6, 8 to 3, 9 to 1)

    rollOutcomes.forEach { (rollSum, frequency) ->
        val newState = if (state.player == 0) {
            val newPos = ((state.pos1 - 1 + rollSum) % 10) + 1
            val newScore = state.score1 + newPos
            GameState(newPos, state.pos2, newScore, state.score2, 1)
        } else {
            val newPos = ((state.pos2 - 1 + rollSum) % 10) + 1
            val newScore = state.score2 + newPos
            GameState(state.pos1, newPos, state.score1, newScore, 0)
        }

        val (subWins1, subWins2) = countWins(newState, memo)
        wins1 += subWins1 * frequency
        wins2 += subWins2 * frequency
    }

    memo[state] = wins1 to wins2
    return wins1 to wins2
}