using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace AdventOfCode_2019.Day02
{
    // The opcode indicates what to do
    enum Opcode
    {
        Addition = 1,
        Multiplication = 2,
        None = 99
    }

    class Solution
    {
        // Day 1
        public int IntcodeProgram(int[] input, int replaceFirstPostion, int replaceSecondPosition)
        {
            if (input == null) return 0;

            var currentOpcodePosition = 0;
            
            try
            {
                // Before running the program, replace position 1  and replace position 2 with the selected values placed in the function parameter.
                input[1] = replaceFirstPostion;
                input[2] = replaceSecondPosition;

                // We want to run the program until it finishes
                while (input[currentOpcodePosition] != (int)Opcode.None)
                {
                    var currentValueSelected = input[currentOpcodePosition];
                    switch (currentValueSelected)
                    {
                        case (int)Opcode.Addition:
                        case (int)Opcode.Multiplication:
                            CalculateSelectedOpcodeValue(input, currentOpcodePosition);
                            break;
                        default:
                            throw new Exception($"We encountered an unknown opcode: {currentValueSelected} that means something went wrong.");
                    }

                    // Add it by 4
                    currentOpcodePosition += 4;
                }

                return input[0];
            }
            catch (Exception exception)
            {
                // We want to handle an excpetion if something goes wrong.
                Console.WriteLine("Exception: " + exception.Message);
                return 0;
            }
        }

        // Day 2
        public int NounAndVerbPointer(int[] input)
        {
            if (input == null) return 0;

            var result = 0;
            var toBeFoundOutput = 19690720;

            // By looping through all the possible nouns and verbs we will hopefully find the correct output.
            // Each of the two input values for the for loop will be between 0 and 99.
            for (int noun = 0; noun < 100; noun++)
            {
                for (int verb = 0; verb < 100; verb++)
                {
                    var copyInput = input.ToArray();

                    // Run the int code program to see if we can find the correct output.
                    result = IntcodeProgram(copyInput, noun, verb);

                    if (result == toBeFoundOutput)
                    {
                        // Output has been found now get the correct value. 
                        // The correct value is 100 * noun + verb? (For example, if noun=12 and verb=2, the answer would be 1202.)
                        result = (noun * 100) + verb;
                        return result;
                    }
                }
            }

            return result;
        }


        private void CalculateSelectedOpcodeValue(int[] input, int currentOpcodePosition)
        {
            // Opcode adds or multiplies (depending on the selected opcode) together numbers read from two positions and stores the result in a third position.
            var firstPostion = input[currentOpcodePosition + 1];
            var secondPosition = input[currentOpcodePosition + 2];
            var thirdPosition = input[currentOpcodePosition + 3];

            // Overwrite the value at the thirdposition.
            if (input[currentOpcodePosition] == (int)Opcode.Addition)
                input[thirdPosition] = input[firstPostion] + input[secondPosition];
            else
                input[thirdPosition] = input[firstPostion] * input[secondPosition];
        }
    }
}