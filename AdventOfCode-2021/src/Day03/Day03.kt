package Day03

import readInput

fun main() {
    val input = readInput("Day03")

    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    // Objective: The diagnostic report (your puzzle input) consists of a list of binary numbers. The objective is to
    // read each bit of the input by the corresponding position (vertically) of all numbers in the diagnostic report. We
    // need to retrieve the most common occurring and least common occurring bit, also called the gamma rate and the epsilon rate.
    // After retrieving that information for the diagnostic report we can then multiply them together to calculate the
    // power consumption.

    // Step 1: Read the files line by line.
    // Step 2: Iterate through the line by character (bit).
    // Step 3: Iterate the files again but the starting index is the index of the bit of step 2.
    // Step 4: After retrieving all the characters place and setting the most common gamma rate. Repeat this process again until the last character of step 2.
    // Step 5: Calculate the most occurring character and least occurring character.
    // Step 5: Multiply the final result.

    var common_gamma_rate = ""
    var uncommon_gamma_rate = ""

    input.first().forEachIndexed { index, _ ->
        var amount = input.filter { it[index] == '0'; }

        common_gamma_rate += if (amount.size > (input.size / 2)) '0' else '1'
        uncommon_gamma_rate += if (common_gamma_rate.last() == '0') '1' else '0'
    }

    return calculateBinaryToDecimal(common_gamma_rate) * calculateBinaryToDecimal(uncommon_gamma_rate)
}

fun part2(input: List<String>): Int {
    // Objective: Verify the life support rating, which can be determined by multiplying the oxygen generator rating by
    // the CO2 scrubber rating. Both the oxygen generator rating and the CO2 scrubber rating are values that can be found
    // in your diagnostic report. Both values are located using a similar process that involves filtering out values until
    // only one remains. Before searching for either rating value, start with the full list of binary numbers from your diagnostic
    // report and consider just the first bit of those numbers. Then:
    //  - Keep only numbers selected by the bit criteria for the type of rating value for which you are searching. Discard numbers which do not match the bit criteria.
    //  - If you only have one number left, stop; this is the rating value for which you are searching.
    //  - Otherwise, repeat the process, considering the next bit to the right.

    // Step 1: Read the files line by line and retrieve the first character (which in this case is the index).
    // Step 2: Filter the list and find the most common or uncommon bit.
    // Step 3: Increase the index and replace the current list with the filtered list.
    // Step 4: Keep repeating step 2 and 3 until the list size is 2.
    // Step 5: Find the rating value of the most common or uncommon (depending on oxygen or scrubber) at the index position of each item in the list.
    // Step 5: Calculate the found rating value into decimal.
    // Step 5: Multiply the final result with the other rating.

    val oxygenGeneratorRating = getRating(0, input, '1', '0')
    val co2ScrubberRating = getRating(0, input, '0', '1')

    return oxygenGeneratorRating * co2ScrubberRating
}

fun getRating(index: Int, list: List<String>, common: Char, uncommon: Char): Int {
    // Base case is when we only have 2 values left.
    if (list.size == 2) {
        val rating = if (list[0][index] == common) list[0] else list[1]
        return calculateBinaryToDecimal(rating)
    }
    // By using 0 we can get both the most common and least common bit for the generators.
    val amount = list.filter { it[index] == '0'; }.size

    return getRating(
        index + 1,
        list.filter { it[index] == if (amount > (list.size / 2)) uncommon else common },
        common,
        uncommon
    )
}

fun calculateBinaryToDecimal(input: String): Int {
    return (Integer.parseInt(input, 2))
}