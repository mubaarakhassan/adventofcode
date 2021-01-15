using System;
using System.Linq;
using System.Text.RegularExpressions;

var items = System.IO.File.ReadAllLines(@"input.txt");
var count = 0;

foreach (var item in items)
{
    var matches = Regex.Match(item, "([0-9]+-[0-9]+) ([a-zA-Z]):(.*[a-zA-Z])");

    // Matches: 
    // 1. Is the two policy 
    // 2. Is the given letter that must appear 
    // 3. The third is the password.

    var policies = matches.Groups[1].Value.Split('-').Select(int.Parse).ToArray();
    var letter = char.Parse(matches.Groups[2].Value);
    var password = matches.Groups[3].Value;
    
    var occurance = password.Count(x => x == letter);

    if (occurance >= policies[0] && occurance <= policies[1])
    {
        count++;
    }
}

Console.WriteLine($"part one: {count} passwords found!");