package Day06

import readInput

fun main() {
    val input = readInput("Day06/Day06")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Long {
    // Objective: We need to calculate the number of days until a new lanternfish is created. Each lanternfish births another
    // fish if the day is 0. So, suppose you have a lanternfish with an internal timer value of 3:
    //
    //  - After one day, its internal timer would become 2.
    //  - After another day, its internal timer would become 1.
    //  - After another day, its internal timer would become 0.
    //  - After another day, its internal timer would reset to 6, and it would create a new lanternfish with an internal timer of 8.
    //  - After another day, the first lanternfish would have an internal timer of 5, and the second
    //    lanternfish would have an internal timer of 7.
    //
    // How many lanternfish would there be after x days?

    // Step 1: Iterate through each day and calculate the amount of days the fish has.
    // Step 2: Iterate through the current amount of fishes.
    // Step 3: Calculate if the fish needs to give birth; if it does increase the internal timer to 6 and add a new fish;
    //         if not then decrease the internal timer. (obviously adding the fish will be outside the iterating between fishes)
    // Step 4: Repeat this process until we arrive at the right day.

    return retrieveAmountOfFishes(80, input)
}

fun part2(input: List<String>): Long {
    // Objective: Same as part 1 but this with higher amount of days.
    return retrieveAmountOfFishes(256, input)
}

fun retrieveAmountOfFishes(days: Int, input: List<String>): Long {
    val fishes = (0..8).associateWith { 0L }.toMutableMap()
    val entries = input.first().split(",").groupingBy { it }.eachCount()

    for (entry in entries)
        fishes[entry.key.toInt()] = entry.value.toLong()

    for (day in 0 until days) {
        var temp = fishes[0] ?: 0
        fishes[0] = fishes[1] ?: 0
        fishes[1] = fishes[2] ?: 0
        fishes[2] = fishes[3] ?: 0
        fishes[3] = fishes[4] ?: 0
        fishes[4] = fishes[5] ?: 0
        fishes[5] = fishes[6] ?: 0
        fishes[6] = (fishes[7] ?: 0) + temp
        fishes[7] = fishes[8] ?: 0
        fishes[8] = temp

        println("Day ${day + 1} with lantern fish size ${fishes.values.sum()}")
    }

    return fishes.values.sum()
}
