package Day10

import readInput

fun main() {
    val input = readInput("Day10")

    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    //  Basically syntax is made of several lines containing chunks There are one or more chunks on
    //  each line, and chunks contain zero or more other chunks. Adjacent chunks are not separated
    //  by any delimiter; if one chunk stops, the next chunk (if any) can immediately start.
    //  Every chunk must open and close with one of four legal pairs of matching characters:
    //
    //  If a chunk opens with (, it must close with ).
    //  If a chunk opens with [, it must close with ].
    //  If a chunk opens with {, it must close with }.
    //  If a chunk opens with <, it must close with >.
    //
    // The first task is to find the first illegal character in each corrupted line.
    // The following task is to take the illegal characters and calculate the amount of points per character.
    // The points are made up as followed:
    //
    //  ): 3 points.
    //  ]: 57 points.
    //  }: 1197 points.
    //  >: 25137 points.

    // Step 1: Loop through the characters and place the opening character, which can be one of
    // these -> '([{<',  in a stack.
    // Step 2: If a closing character is found look into the top stack, if it is an opening character,
    // and it matches with closing character pop the character in the stack; if not then that
    // means we have found an illegal character. Place the illegal character in a list.
    // Step 3: Count the amount of found illegal characters by looking up inside a table.

    var points = 0
    val chunks = mutableListOf(
        Chunk('(', ')', 3),
        Chunk('[', ']', 57),
        Chunk('{', '}', 1197),
        Chunk('<', '>', 25137)
    )

     input.forEach {
        val stack = ArrayDeque<Char>()

         // To break the loop early if we have found the illegal character we have added lambda expression
         // with an anonymous function. A return statement in an anonymous function will return from the anonymous
         // function itself.
         run loop@ {
             it.forEach { c ->
                 val chunk = chunks.first { chunk -> chunk.opening == c || chunk.closing == c}
                 if(chunk.opening == c)
                     stack.add(c)
                 else if(stack.last() == chunk.opening && chunk.closing == c)
                     stack.removeLast()
                 else {
                     println("Expected ${chunks.first { it.opening == stack.last()}.closing}, but found $c instead.")
                     points += chunk.errorPoint
                     return@loop
                 }
             }
         }
    }

    return points
}

fun part2(input: List<String>): Long {
    // Part 2 is the same as part 1 but ignore the corrupt ones and play with the incomplete lines.
    // Find the series of closing brackets in the right order for those incomplete ones.
    // Then based on the given points and calculation we need to find the score for each line.
    // Finally, take the middle of the sorted scores.
    //
    // The points given per incomplete ones closing brackets is defined the following:
    //
    //  ): 1 point.
    //  ]: 2 points.
    //  }: 3 points.
    //  >: 4 points.
    //
    // Find the completion string for each incomplete line, score the completion strings,
    // and sort the scores. What is the middle score?

    val scores =  mutableListOf<Long>()
    val chunks = mutableListOf(
        Chunk('(', ')', 3, 1),
        Chunk('[', ']', 57, 2),
        Chunk('{', '}', 1197, 3),
        Chunk('<', '>', 25137, 4)
    )

    // To discard the corrupted lines we will go to the next element in the input.
    input.forEach next@{
        val stack = ArrayDeque<Char>()
        var points = 0L
        var complete = ""

        it.forEach { c ->
            val chunk = chunks.first { chunk -> chunk.opening == c || chunk.closing == c }
            if (chunk.opening == c)
                stack.add(c)
            else if (stack.last() == chunk.opening && chunk.closing == c)
                stack.removeLast()
            else
                return@next
        }

        stack.reversed().forEach {
            val chunk = chunks.first { chunk -> chunk.opening == it}
            points = (points * 5) + chunk.autoCompletePoint
            complete += chunk.closing
        }

        println("$complete - $points total points")
        scores.add(points)
    }

    return scores.sorted()[scores.size / 2]
}