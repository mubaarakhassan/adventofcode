using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;

var lines = System.IO.File.ReadAllLines(@"input.txt");
var memory = new Dictionary<long, long>();
var mask = "";

foreach (var line in lines)
{
    if (line.StartsWith("mask"))
    {
        mask = line[7..];
    }
    else
    {
        var match = Regex.Match(line, @"mem\[(\d+)\] = (\d+)");
        var addr = long.Parse(match.Groups[1].Value);
        var value = long.Parse(match.Groups[2].Value);

        var addresses = new List<long> { 0 };
        var binary = Convert.ToString(addr, 2).PadLeft(36, '0');

        for (int i = 0; i < 36; i++)
        {
            var bit = mask[i] == '0' ? binary[i] : mask[i];
            if (bit == 'X')
            {
                addresses = addresses.SelectMany(a => new[] { a, a | (1L << (35 - i)) }).ToList();
            }
            else if (bit == '1')
            {
                addresses = addresses.Select(a => a | (1L << (35 - i))).ToList();
            }
        }

        foreach (var a in addresses)
            memory[a] = value;
    }
}

Console.WriteLine($"Sum: {memory.Values.Sum()}");