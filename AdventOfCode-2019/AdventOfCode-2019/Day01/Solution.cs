using System;
using System.Collections.Generic;
using System.Text;

namespace AdventOfCode_2019.Day01
{
    class Solution
    {
        private const int divider = 3;
        private const int subtracter = 2;


        /// <summary>
        /// Calculates the amount of fuel needed to get to santa
        /// </summary>
        /// <param name="unitOfMass">The list of mass</param>
        /// <returns>The amount of fuel needed.</returns>
        public int RocketFuelEquationPart1(string[] unitOfMass)
        {
            int result = 0;

            foreach (var mass in unitOfMass)
            {
                // Take the mass and divide it by three.
                // Round down the number (it's an int so it automatically rounds it down).
                var module = Int32.Parse(mass) / divider;

                // Subtract by two
                module = module - subtracter;

                result += module;
            }

            return result;
        }

        /// <summary>
        /// Calculates the amount of fuel needed to get to santa
        /// </summary>
        /// <param name="mass">single mass</param>
        /// <returns>The total amount of fuel needed.</returns>
        private int RocketFuelEquationRecursion(int mass)
        {
            // Take the mass and divide it by three.
            // Round down the number (it's an int so it automatically rounds it down).
            var module = mass / divider;

            // Subtract by two
            module = module - subtracter;

            // Base case : if the value is goes lower than 0 end the recursion otherwise go further down.
            if (module < 0)
                return 0;

            return module += RocketFuelEquationRecursion(module); ;
        }

        /// <summary>
        ///  Calculates the amount of fuel needed to get to santa
        /// </summary>
        /// <param name="unitOfMass">The list of mass</param>
        /// <returns>The amount of fuel needed.</returns>
        public int RocketFuelEquationPart2(string[] unitOfMass)
        {
            int result = 0;

            foreach(var mass in unitOfMass)
            {
                result += RocketFuelEquationRecursion(Int32.Parse(mass));
            }

            return result;
        }
    }


}
