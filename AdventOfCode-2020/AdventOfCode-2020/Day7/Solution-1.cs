using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;

var items = System.IO.File.ReadAllLines(@"input.txt");

Dictionary<string, List<(string Name, int Amount)>> bagDictionary = new();

foreach (var item in items)
{
    var splittedItems = item.Split(new string[] { "contain", ",", "", "." }, 10, StringSplitOptions.RemoveEmptyEntries).ToList();

    // Remove unused bags.
    splittedItems.RemoveAll(x => x.Contains("no other bags"));

    var bags = splittedItems.Select((value, index) => new { Name = ExtractName(splittedItems[index]), Amount = index != 0 ? ExtractAmount(splittedItems[index]) : 0 }).ToList();

    bagDictionary.Add(bags[0].Name, bags.GetRange(1, bags.Count - 1).Select(x => (x.Name, x.Amount)).ToList());
}

Console.WriteLine($"Shiny gold bag {bagDictionary.Count(x => ContainShinyBags(x.Key, "shiny gold"))} found!");

// Check if the contents of the key has a shiny bag. If it does return true, otherwise use recursion to check if the other bags has a shiny bag.
// A check could be added if the currentBag has already been traversed. This could improve performance.. but alas.
bool ContainShinyBags(string currentBag, string bagToBeFound) => bagDictionary[currentBag].Where(x => x.Item1.Contains(bagToBeFound)).Any() 
                                                                || bagDictionary[currentBag].Where(x => ContainShinyBags(x.Item1, bagToBeFound)).Any();


string ExtractName(string value) => Regex.Replace(value, @"[\d-]", "")
                                    .Replace(".", "")
                                    .Replace("bags", "")
                                    .Replace("bag", "").Trim();

int ExtractAmount(string value) => int.Parse(Regex.Match(value, @"\d").Value);