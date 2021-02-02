using System;
using System.Linq;

var grid = System.IO.File.ReadAllLines(@"input.txt").Select(line => line.ToCharArray()).ToArray();
var dirs = new[] { (-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1) };

int CountAdjacent(char[][] g, int r, int c) => dirs.Count(d =>
    r + d.Item1 >= 0 && r + d.Item1 < g.Length &&
    c + d.Item2 >= 0 && c + d.Item2 < g[0].Length &&
    g[r + d.Item1][c + d.Item2] == '#');

while (true)
{
    var newGrid = grid.Select((row, r) => row.Select((seat, c) => seat switch
    {
        'L' when CountAdjacent(grid, r, c) == 0 => '#',
        '#' when CountAdjacent(grid, r, c) >= 4 => 'L',
        _ => seat
    }).ToArray()).ToArray();

    if (grid.SelectMany(x => x).SequenceEqual(newGrid.SelectMany(x => x))) break;
    grid = newGrid;
}

Console.WriteLine($"Occupied seats: {grid.SelectMany(x => x).Count(c => c == '#')}");