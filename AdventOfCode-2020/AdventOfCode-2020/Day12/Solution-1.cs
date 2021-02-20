using System;
using System.Linq;

var instructions = System.IO.File.ReadAllLines(@"input.txt");
var (x, y, dir) = (0, 0, 0); // East = 0, South = 1, West = 2, North = 3
var moves = new[] { (1, 0), (0, -1), (-1, 0), (0, 1) };

foreach (var instruction in instructions)
{
    var action = instruction[0];
    var value = int.Parse(instruction[1..]);

    switch (action)
    {
        case 'N': y += value; break;
        case 'S': y -= value; break;
        case 'E': x += value; break;
        case 'W': x -= value; break;
        case 'L': dir = (dir + 4 - value / 90) % 4; break;
        case 'R': dir = (dir + value / 90) % 4; break;
        case 'F': (x, y) = (x + moves[dir].Item1 * value, y + moves[dir].Item2 * value); break;
    }
}

Console.WriteLine($"Manhattan distance: {Math.Abs(x) + Math.Abs(y)}");