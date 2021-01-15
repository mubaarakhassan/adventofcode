using System;
using System.Collections.Generic;

var items = System.IO.File.ReadAllLines(@"input.txt");

IterateThroughActions(visited: new HashSet<int>(), recursive: true);

bool IterateThroughActions(int count = 0, int accumulator = 0, HashSet<int> visited = null, bool recursive = false)
{
    while (visited.Add(count))
    {
        if (count >= items.Length)
        {
            Console.WriteLine($"The value of the accumulator is {accumulator}!");
            return true;
        }

        (string action, int pos) = (items[count][0..3], int.Parse(items[count][4..^0]));

        switch (action)
        {
            case string a when a == "nop": if (recursive && IterateThroughActions(count + pos, accumulator, new HashSet<int>(visited))) return true; count++; break;
            case string b when b == "acc": accumulator += pos; count++; break;
            case string c when c == "jmp": if (recursive && IterateThroughActions(count + 1, accumulator, new HashSet<int>(visited))) return true; count += pos; break;
            case null: throw new NotSupportedException();
        }
    }

    return false;
}