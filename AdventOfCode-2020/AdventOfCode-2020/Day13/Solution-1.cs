using System;
using System.Linq;

var lines = System.IO.File.ReadAllLines(@"input.txt");
var timestamp = int.Parse(lines[0]);
var buses = lines[1].Split(',').Where(x => x != "x").Select(int.Parse);

var (id, wait) = buses.Select(bus => (bus, wait: bus - timestamp % bus)).OrderBy(x => x.wait).First();

Console.WriteLine($"Result: {id * wait}");