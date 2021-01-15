using System;
using System.Linq;

var numbers = System.IO.File.ReadAllLines(@"input.txt").Select(long.Parse).ToArray();

for (int i = 25; i < numbers.Length; i++)
{
    var preamble = numbers[(i - 25)..i];
    var target = numbers[i];

    if (!preamble.SelectMany((x, idx) => preamble.Skip(idx + 1).Select(y => x + y)).Contains(target))
    {
        Console.WriteLine($"Invalid number: {target}");
        break;
    }
}