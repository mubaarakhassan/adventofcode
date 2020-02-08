using AdventOfCode_2019.Day01;
using System;
using System.IO;

namespace AdventOfCode_2019
{
    class Program
    {
        static void Main(string[] args)
        {
            // This will get the current WORKING directory (i.e. \bin\Debug)
            string workingDirectory = Environment.CurrentDirectory;

            // This will get the current PROJECT directory
            //string projectDirectory = Directory.GetParent(workingDirectory).Parent.Parent.FullName;

            Solution s = new Solution();

            // This will read the input file and create arrays for each new line
            string[] input = File.ReadAllLines($"{workingDirectory}\\Day01\\input.in");
            
            // Calculate the fuel
            var fuel = s.RocketFuelEquationPart2(input);

            Console.WriteLine($"The fuel needed for going to space = {fuel} !");
        }
    }
}
