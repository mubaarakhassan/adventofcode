using System;
using System.Linq;

var grid = System.IO.File.ReadAllLines(@"input.txt").Select(line => line.ToCharArray()).ToArray();
var dirs = new[] { (-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1) };

int CountVisible(char[][] g, int r, int c) => dirs.Count(d =>
{
    for (int i = 1; ; i++)
    {
        int nr = r + d.Item1 * i, nc = c + d.Item2 * i;
        if (nr < 0 || nr >= g.Length || nc < 0 || nc >= g[0].Length) return false;
        if (g[nr][nc] == '#') return true;
        if (g[nr][nc] == 'L') return false;
    }
});

while (true)
{
    var newGrid = grid.Select((row, r) => row.Select((seat, c) => seat switch
    {
        'L' when CountVisible(grid, r, c) == 0 => '#',
        '#' when CountVisible(grid, r, c) >= 5 => 'L',
        _ => seat
    }).ToArray()).ToArray();

    if (grid.SelectMany(x => x).SequenceEqual(newGrid.SelectMany(x => x))) break;
    grid = newGrid;
}

Console.WriteLine($"Occupied seats: {grid.SelectMany(x => x).Count(c => c == '#')}");