using System;
using System.Linq;

var numbers = System.IO.File.ReadAllLines(@"input.txt").Select(long.Parse).ToArray();
var invalidNumber = 21806024L; // Result from part 1

for (int i = 0; i < numbers.Length; i++)
{
    for (int j = i + 1; j < numbers.Length; j++)
    {
        var range = numbers[i..(j + 1)];
        var sum = range.Sum();

        if (sum == invalidNumber)
        {
            Console.WriteLine($"Weakness: {range.Min() + range.Max()}");
            return;
        }

        if (sum > invalidNumber) break;
    }
}