using System;
using System.Text.RegularExpressions;
using System.Collections.Generic;
using System.Linq;

var items = System.IO.File.ReadAllText(@"input.txt").Split(new string[] { "\r\n\r\n" }, StringSplitOptions.RemoveEmptyEntries);

var counts = 0;

foreach(var item in items)
{
    var answers = item.Split(new string[] { "\n" }, StringSplitOptions.RemoveEmptyEntries);
    
    var list = new List<char>();

    foreach(var g in answers)
    {   
        var normalized  = Regex.Replace(g, @"\s", ""); 
        list.AddRange(normalized);
    }

    counts += list.GroupBy(x => x)
                .Where(g => g.Count() == answers.Count())
                .Count();
}

Console.WriteLine($"Counts: {counts} found!");
