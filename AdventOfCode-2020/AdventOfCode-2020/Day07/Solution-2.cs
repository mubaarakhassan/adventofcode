using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;

var items = System.IO.File.ReadAllLines(@"input.txt");

Dictionary<string, List<(string Name, int Amount)>> bagDictionary = new();

foreach (var item in items)
{
    var splittedItems = item.Split(new string[] { "contain", ",", "", "." }, 10, StringSplitOptions.RemoveEmptyEntries).ToList();

    splittedItems.RemoveAll(x => x.Contains("no other bags"));

    var bags = splittedItems.Select((value, index) => new { 
                                                    Value = value,
                                                    Name = ExtractName(splittedItems[index]), 
                                                    Amount = index != 0 ? ExtractAmount(splittedItems[index]) : 0 
                                                    }).ToDictionary(x => x.Value, x => (x.Name, x.Amount) );

    foreach(var kvp in bags)
    {
        bagDictionary.Add(kvp);
    }
}

Console.WriteLine($"Number of shiny gold bag {CountContainedBags("shiny gold")} found!");

// Check if the contents of the key has a shiny bag. If it does return true, otherwise use recursion to check if the other bags has a shiny bag.
// A check could be added if the currentBag has already been traversed. This could improve performance.. but alas.
bool ContainShinyBags(string currentBag, string bagToBeFound) => bagDictionary[currentBag].Where(x => x.Item1.Contains(bagToBeFound)).Any()
                                                                || bagDictionary[currentBag].Where(x => ContainShinyBags(x.Item1, bagToBeFound)).Any();

// Same as the ContainShinyBags function but this time calculate the amount of bags by using the x + (x * the bags within it) formula recursively.
int CountContainedBags(string bagToBeFound) => bagDictionary[bagToBeFound].Select(x => x.Amount + (x.Amount * CountContainedBags(x.Name))).Sum();

string ExtractName(string value) => Regex.Replace(value, @"[\d-]", "")
                                    .Replace(".", "")
                                    .Replace("bags", "")
                                    .Replace("bag", "").Trim();

int ExtractAmount(string value) => int.Parse(Regex.Match(value, @"\d").Value);