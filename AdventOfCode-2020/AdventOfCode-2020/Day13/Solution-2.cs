using System;
using System.Linq;

var buses = System.IO.File.ReadAllLines(@"input.txt")[1]
    .Split(',')
    .Select((bus, i) => new { bus, i })
    .Where(x => x.bus != "x")
    .Select(x => (id: long.Parse(x.bus), offset: (long)x.i))
    .ToArray();

var (time, step) = (0L, 1L);

foreach (var (id, offset) in buses)
{
    while ((time + offset) % id != 0)
        time += step;
    step *= id;
}

Console.WriteLine($"Earliest timestamp: {time}");