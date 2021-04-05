using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;

var lines = System.IO.File.ReadAllLines(@"input.txt");
var memory = new Dictionary<int, long>();
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
        var addr = int.Parse(match.Groups[1].Value);
        var value = long.Parse(match.Groups[2].Value);

        var binary = Convert.ToString(value, 2).PadLeft(36, '0').ToCharArray();
        for (int i = 0; i < 36; i++)
            if (mask[i] != 'X')
                binary[i] = mask[i];

        memory[addr] = Convert.ToInt64(new string(binary), 2);
    }
}

Console.WriteLine($"Sum: {memory.Values.Sum()}");