package Day04

import readInput

fun main() {
    val input = readInput("Day04/Day04")

    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    // Objective: The submarine has a bingo subsystem to help passengers (currently, you and the giant squid) pass the time.
    // It automatically generates a random order in which to draw numbers and a random set of boards. If one of the board
    // wins the board can now be calculated. Start by finding the sum of all unmarked numbers on that board; in this case,
    // the sum. (e.g. is 188. Then, multiply that sum by the number that was just called when the board won, 24, to get the final score,  188 * 24 = 4512)

    // Step 1: Retrieve the first line and place it as 'draws'.
    // Step 2: Retrieve the boards by checking if there are spaces between the lines.
    // Step 3: Place the retrieved boards each inside a separate multidimensional array.
    // Step 4: Loop through the 'draws' and keep marks of score of the boards.
    // Step 5: Repeat this process until one wins because it has at least one complete row or column of marked numbers.

    var draws = input.first().split(",").map { it.toInt() };
    var boards = createBoards(input.drop(1));

    loop@ for (draw in draws){
        for (board in boards) {
            for (row in board.rows)
                row.rowItems.map { if (it.num == draw) it.marked = true; }


            if(board.validateWinningCondition())
                return board.rows.sumOf { it.getTotalUnmarkedSum() * draw };
        }
    }

    return 0;
}

fun part2(input: List<String>): Int {
    // Objective: Figure out which board will win last. Once it wins, what would its final score be?

    // Step 1: Retrieve the first line and place it as 'draws'.
    // Step 2: Retrieve the boards by checking if there are spaces between the lines.
    // Step 3: Place the retrieved boards each inside a separate multidimensional array.
    // Step 4: Loop through the 'draws' and keep marks of score of the boards.
    // Step 5: Repeat this process until the last one wins.

    var draws = input.first().split(",").map { it.toInt() };
    var boards = createBoards(input.drop(1));
    var winners = linkedSetOf <Int>(); // only allows unique boards and maintains the order.

    for (draw in draws){
        for (board in boards) {
            for (row in board.rows)
                row.rowItems.map { if (it.num == draw) it.marked = true; }

            if(board.validateWinningCondition())
                winners.add(board.id);

            if(winners.size == boards.size) // Wait until the last winner.
                return boards[winners.last()].rows.sumOf { it.getTotalUnmarkedSum() * draw;  }
        }
    }

    return 0;
}

fun createBoards(input: List<String>) : List<Board> {
    var rawBoards = input.filter { !it.isNullOrBlank() }.chunked(5);
    var boards = mutableListOf<Board>();

    for ((i, items) in rawBoards.withIndex()){
        var rows = mutableListOf<Row>();
        for ((j,item) in items.withIndex()){
            var rowItem = item.split("\\s".toRegex()).filter { !it.isNullOrBlank() }.map { RowItem(it.toInt(), false) }
            rows.add(j, Row(rowItem));
        }

        boards.add(i, Board(i, rows));
    }

    return boards;
}