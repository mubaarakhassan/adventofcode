fun main() {
    fun part1(input: List<String>): Int {
        // Objective: Count the number of times a depth measurement increases from the previous measurement
        // Step 1: Read the files line by line
        // Step 2: Retrieve the measurement value.
        // Step 3: Compare the retrieved value if it has increased or decreased from the previous measurement.

        var depths = input.map { it.toInt() };

        var previous = 0;
        var current = 0;
        var count = 0;

        depths.forEachIndexed { index, element ->
            previous = current;
            current = element;

            if(index == 0)
                println("$element (N/A - no previous measurement)");
            else
                if(current > previous){
                    count++;
                    println("$element (increased)");
                }
                else
                    println("$element (decreased)");
        };

        return count
    }

    fun part1Improved(input: List<String>): Int {
        // Objective: Count the number of times a depth measurement increases from the previous measurement
        // Step 1: Read the files line by line
        // Step 2: Retrieve the measurement value.
        // Step 3: Compare the retrieved value if it has increased or decreased from the previous measurement.

        var depths = input.map { it.toInt() };
        var count = 0;

        depths.zipWithNext(){s1, s2 -> if(s1 < s2) count++;}

        return count;
    }

    fun part2(input: List<String>): Int {
        // Objective: Start by comparing the first and second three-measurement windows. The measurements in the first
        // window are marked A (199, 200, 208); their sum is 199 + 200 + 208 = 607. The second window is marked B (200, 208, 210);
        // Count the number of times the sum of measurements in this sliding window increases
        // Step 1: Read the files line by line
        // Step 2: Retrieve the first, second and third measurement windows and get the sum.
        // Step 3: Compare the retrieved sum value with the previous measurement and check if it has increased or decreased.

        var depths = input.map { it.toInt() };

        var previousSum = 0;
        var currentSum = 0;
        var count = 0;

        depths.forEachIndexed { index, element ->
            var updatedIndex = index + 2;
            if(updatedIndex < depths.size){
                var previousPrevious = depths[updatedIndex - 2];
                var previous = depths[updatedIndex - 1];
                var current = depths[updatedIndex];

                previousSum = currentSum;
                currentSum = (current + previous + previousPrevious);

                if(updatedIndex == 2)
                    println("$currentSum (N/A - no previous measurement)");
                else
                    if(currentSum > previousSum){
                        count++;
                        println("$currentSum (increased)");
                    }
                    else
                        println("$currentSum (decreased)");
            }
        };

        return count;
    }

    fun part2Improved(input: List<String>): Int {
        // Objective: Start by comparing the first and second three-measurement windows. The measurements in the first
        // window are marked A (199, 200, 208); their sum is 199 + 200 + 208 = 607. The second window is marked B (200, 208, 210);
        // Count the number of times the sum of measurements in this sliding window increases
        // Step 1: Read the files line by line
        // Step 2: Retrieve the first, second and third measurement windows and get the sum.
        // Step 3: Compare the retrieved sum value with the previous measurement and check if it has increased or decreased.

        var depths = input.map { it.toInt() };
        var count = 0;

        var sums = depths.windowed(3) { it.sum() }
        sums.zipWithNext(){s1, s2 -> if(s1 < s2) count++;}

        return count;
    }

    val input = readInput("Day01/Day01")

    println(part1(input))
    println(part2(input))

    println(part1Improved(input))
    println(part2Improved(input))
}
