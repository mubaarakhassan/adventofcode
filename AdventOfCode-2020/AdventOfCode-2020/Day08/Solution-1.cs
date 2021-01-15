using System;
using System.Collections.Generic;

var items = System.IO.File.ReadAllLines(@"input.txt");

var visited = new HashSet<int>();
var accumulator = 0;
var count = 0;

while (visited.Add(count))
{
    (string action, int pos) = (items[count][0..3], int.Parse(items[count][4..^0]));

    switch (action)
    {
        case string a when a == "nop": count++; break;
        case string b when b == "acc": accumulator += pos; count++; break;
        case string c when c == "jmp": count += pos; break;
        case null: throw new NotSupportedException();
    }
}

Console.WriteLine($"The value of the accumulator is {accumulator}!");