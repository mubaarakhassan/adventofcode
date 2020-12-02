using System;
using System.Linq;
using System.Text.RegularExpressions;

var items = System.IO.File.ReadAllLines(@"input.txt");
var count = 0;

foreach (var item in items)
{
    var matches = Regex.Match(item, "([0-9]+-[0-9]+) ([a-zA-Z]):(.*[a-zA-Z])");

    // Matches: 
    // 1. Is the policy 
    // 2. Is the given letter that must appear 
    // 3. The third is the password.

    var policy = matches.Groups[1].Value.Split('-').Select(int.Parse).ToArray();
    var letter = char.Parse(matches.Groups[2].Value);
    var password = matches.Groups[3].Value;
    
    var occurance = password.Count(x => x == letter);

    if (occurance >= policy[0] && occurance <= policy[1])
    {
        count++;
    }
}

Console.WriteLine($"part one: {count} passwords found!");

count = 0;

foreach (var item in items)
{
    var matches = Regex.Match(item, "([0-9]+-[0-9]+) ([a-zA-Z]):(.*[a-zA-Z])");

    // Matches: 
    // 1. Is the policy 
    // 2. Is the given letter that must appear 
    // 3. The third is the password.

    var policy = matches.Groups[1].Value.Split('-').Select(int.Parse).ToArray();
    var letter = char.Parse(matches.Groups[2].Value);
    var password = matches.Groups[3].Value;

    if(password[policy[0]] == letter ^ password[policy[1]] == letter)
    {
        count++;
    }        
}

Console.WriteLine($"part two: {count} passwords found!");
