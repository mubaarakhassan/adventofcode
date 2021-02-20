using System;

var instructions = System.IO.File.ReadAllLines(@"input.txt");
var (x, y, wx, wy) = (0, 0, 10, 1);

foreach (var instruction in instructions)
{
    var action = instruction[0];
    var value = int.Parse(instruction[1..]);

    switch (action)
    {
        case 'N': wy += value; break;
        case 'S': wy -= value; break;
        case 'E': wx += value; break;
        case 'W': wx -= value; break;
        case 'L': for (int i = 0; i < value / 90; i++) (wx, wy) = (-wy, wx); break;
        case 'R': for (int i = 0; i < value / 90; i++) (wx, wy) = (wy, -wx); break;
        case 'F': (x, y) = (x + wx * value, y + wy * value); break;
    }
}

Console.WriteLine($"Manhattan distance: {Math.Abs(x) + Math.Abs(y)}");