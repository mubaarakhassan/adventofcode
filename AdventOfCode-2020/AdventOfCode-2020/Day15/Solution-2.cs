using System;
using System.Collections.Generic;

var starting = new[] { 9, 6, 0, 10, 18, 2, 1 };
var spoken = new Dictionary<int, int>();
var last = 0;

for (int turn = 1; turn <= 30000000; turn++)
{
    var current = turn <= starting.Length
        ? starting[turn - 1]
        : spoken.TryGetValue(last, out int lastTurn) ? turn - 1 - lastTurn : 0;

    if (turn > 1) spoken[last] = turn - 1;
    last = current;
}

Console.WriteLine($"30000000th number: {last}");