using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;

var items = System.IO.File.ReadAllText(@"input.txt").Split(new string[] { "\r\n\r\n" }, StringSplitOptions.RemoveEmptyEntries);

var passportsCount = 0;

/**
* byr (Birth Year) - four digits; at least 1920 and at most 2002.
* iyr (Issue Year) - four digits; at least 2010 and at most 2020.
* eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
* hgt (Height) - a number followed by either cm or in:
* If cm, the number must be at least 150 and at most 193.
* If in, the number must be at least 59 and at most 76.
* hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
* ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
* pid (Passport ID) - a nine-digit number, including leading zeroes.
* cid (Country ID) - ignored, missing or not.
**/
var patternsDictionary = new Dictionary<string,string>
{
    {"byr","^([1][9][2-9][0-9]|[2][0][0][0-2])$"},
    {"iyr","^([2][0][1][0-9]|2020)$"},
    {"eyr","^([2][0][2][0-9]|2030)$"},
    {"hgt","^([1][5-8][0-9]|[1][9][0-3])cm|(^([5][9]|[6][0-9]|[7][0-6])in)$"},
    {"hcl","^(#([0-9]|[a-zA-Z]){6})$"},
    {"ecl","^(amb|blu|brn|gry|grn|hzl|oth)$"},
    {"pid","^([0-9]{9})$"},
};

foreach(var item in items)
{
    var matches = Regex.Matches(item, "((byr|iyr|eyr|hgt|hcl|ecl|pid|cid):([^\\s]+))");
    
    if(matches.Count >= 7)
    {
        // HashSet allows only the unique values to the list
        var occuranceHash = new HashSet<string>();

        foreach (Match match in matches)
        {
            var field = match.Groups[2].Value;
            var fieldValue = match.Groups[3].Value;
            
            var isValid = field == "cid" ? true : Regex.IsMatch(fieldValue, patternsDictionary[field]);

            if(isValid)
            {
                occuranceHash.Add(field);
            }
        }

        if(occuranceHash.Count == 8 || occuranceHash.Count == 7 && !occuranceHash.Contains("cid"))
        {
            passportsCount++;
        }
    }
}

Console.WriteLine($"Passports {passportsCount} found!");