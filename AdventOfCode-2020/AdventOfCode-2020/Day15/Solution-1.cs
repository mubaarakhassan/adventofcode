using System;
using System.Collections.Generic;

var starting = new[] {9,6,0,10,18,2,1 };
var spoken = new Dictionary<int, int>();
var last = 0;

for (int turn = 1; turn <= 2020; turn++)
{
    var current = turn <= starting.Length
        ? starting[turn - 1]
        : spoken.ContainsKey(last) ? turn - 1 - spoken[last] : 0;

    if (turn > 1) spoken[last] = turn - 1;
    last = current;
}

Console.WriteLine($"2020th number: {last}");