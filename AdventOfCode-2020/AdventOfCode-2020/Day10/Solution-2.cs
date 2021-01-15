using System;
using System.Collections.Generic;
using System.Linq;

var adapters = System.IO.File.ReadAllLines(@"input.txt").Select(int.Parse).OrderBy(x => x).ToArray();
var joltages = new[] { 0 }.Concat(adapters).Concat(new[] { adapters.Max() + 3 }).ToArray();

var memo = new Dictionary<int, long>();

long CountWays(int index)
{
    if (index == joltages.Length - 1) return 1;
    if (memo.ContainsKey(index)) return memo[index];

    long ways = 0;
    for (int i = index + 1; i < joltages.Length && joltages[i] - joltages[index] <= 3; i++)
        ways += CountWays(i);

    return memo[index] = ways;
}

Console.WriteLine($"Arrangements: {CountWays(0)}");