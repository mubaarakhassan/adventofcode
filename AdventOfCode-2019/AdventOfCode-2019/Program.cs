using AdventOfCode_2019.Day03;
using System;
using System.IO;
using System.Linq;

namespace AdventOfCode_2019
{
    class Program
    {
        static void Main(string[] args)
        {
            // This will get the current WORKING directory (i.e. \bin\Debug)
            string workingDirectory = Environment.CurrentDirectory;
            Console.WriteLine($"The current working directory is: {workingDirectory} !");
            // This will get the current PROJECT directory
            string projectDirectory = Directory.GetParent(workingDirectory).Parent.Parent.FullName;

            /*   Solution solutionDay1 = new Solution();

               // This will read the input file and create arrays for each new line
               string[] input = File.ReadAllLines($"{projectDirectory}\\Day01\\input.in");

               // Calculate the fuel
               var fuel = solutionDay1.RocketFuelEquationPart2(input);
               Console.WriteLine($"The fuel needed for going to space = {fuel} !");
           */

            //string[] file = File.ReadAllLines($"{projectDirectory}\\Day02\\input.in").FirstOrDefault().Split(','); ;

            //var input = file.Select(x => Int32.Parse(x)).ToArray();

            //Solution solutionDay2 = new Solution();
            //var result = solutionDay2.IntcodeProgram(input.ToArray(), 12, 2);
            //Console.WriteLine($"Day 1: The current value in the array = {result} !");

            //var result2 = solutionDay2.NounAndVerbPointer(input.ToArray());
            //Console.WriteLine($"Day 2: The current value in the array = {result2} !");
            var input = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4 , new ListNode(5 , null)))));
            Solution s = new Solution();
            var val = s.ReverseList(input);
        }
    }
}
