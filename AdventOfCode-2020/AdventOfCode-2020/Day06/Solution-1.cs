using System;
using System.Linq;
using System.Text.RegularExpressions;

var items = System.IO.File.ReadAllText(@"input.txt").Split(new string[] { "\r\n\r\n" }, StringSplitOptions.RemoveEmptyEntries);

var counts = items.Select(s => Regex.Replace(s, @"\s", "").Select(c => c).Distinct().Count());

Console.WriteLine($"Counts: {counts.Sum()} found!");
