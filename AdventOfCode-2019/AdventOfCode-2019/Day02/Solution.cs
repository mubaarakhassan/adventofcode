using System;
using System.Collections.Generic;
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
        public int IntcodeProgram(int[] input, int replaceFirstPostion, int replaceSecondPosition)
        {
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
                            CalculateSelectedOpcodeValue(input, currentOpcodePosition, Opcode.Addition);
                            break;
                        case (int)Opcode.Multiplication:
                            CalculateSelectedOpcodeValue(input, currentOpcodePosition, Opcode.Multiplication);
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

        private void CalculateSelectedOpcodeValue(int[] input, int currentOpcodePosition, Opcode opcode)
        {
            // Opcode adds or multiplies (depending on the selected opcode) together numbers read from two positions and stores the result in a third position.
            var firstPostion = input[currentOpcodePosition + 1];
            var secondPosition = input[currentOpcodePosition + 2];
            var thirdPosition = input[currentOpcodePosition + 3];

            // Overwrite the value at the thirdposition.
            if (opcode == Opcode.Addition)
                input[thirdPosition] = input[firstPostion] + input[secondPosition];
            else
                input[thirdPosition] = input[firstPostion] * input[secondPosition];
        }
    }
}