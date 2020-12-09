using System;
using System.Text;
using System.Collections.Generic;
using System.Text.RegularExpressions;

var items = System.IO.File.ReadAllText(@"input.txt").Split(new string[] { "\r\n\r\n" }, StringSplitOptions.RemoveEmptyEntries);

var passportsCount = 0;

foreach(var item in items)
{
    var matches = Regex.Matches(item, "((byr|iyr|eyr|hgt|hcl|ecl|pid|cid):[^\\s]+)");
    
    if(matches.Count >= 7)
    {
        //HashSet allows only the unique values to the list
        var occuranceHash = new HashSet<string>();

        foreach (Match match in matches)
        {
            occuranceHash.Add(match.Value.Substring(0,3));
        }

        if(occuranceHash.Count == 8 || occuranceHash.Count == 7 && !occuranceHash.Contains("cid"))
        {
            passportsCount++;
        }
    }
}

Console.WriteLine($"Passports {passportsCount} found!");