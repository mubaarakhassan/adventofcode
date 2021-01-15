using System;
using System.Linq;

var adapters = System.IO.File.ReadAllLines(@"input.txt").Select(int.Parse).OrderBy(x => x).ToArray();
var joltages = new[] { 0 }.Concat(adapters).Concat(new[] { adapters.Max() + 3 }).ToArray();

var diffs = joltages.Zip(joltages.Skip(1), (a, b) => b - a).ToArray();
var ones = diffs.Count(x => x == 1);
var threes = diffs.Count(x => x == 3);

Console.WriteLine($"Result: {ones * threes}");