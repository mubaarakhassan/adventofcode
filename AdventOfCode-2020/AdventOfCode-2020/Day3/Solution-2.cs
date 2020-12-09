using System;
using System.Text;
using System.Linq;

var items = System.IO.File.ReadAllLines(@"input.txt");

var matrix_array = new string[items.Length, items[0].Length];

FillMatrixArray(matrix_array, items);

var movementPatterns = new int[,]
{
    {1,1},
    {1,3},
    {1,5},
    {1,7},
    {2,1}
};

double treeSum = 0;

for (int i = 0; i < movementPatterns.GetLength(0); i++)
{
    var positionRight = movementPatterns[i,1]; // Initial position
    var positionBottom =  movementPatterns[i,0];
    var treeCount = 0;

    while (positionBottom < matrix_array.GetLength(0))
    {
        if (positionRight >= matrix_array.GetLength(1))
        {
            positionRight = positionRight - matrix_array.GetLength(1);
        }

        if (matrix_array[positionBottom, positionRight] == "#")
        {
            matrix_array[positionBottom, positionRight] = "X";
            treeCount++;
        }
        else
        {
           matrix_array[positionBottom, positionRight] = "O";
        }

        positionRight +=  movementPatterns[i,1];
        positionBottom +=  movementPatterns[i,0];
    }

    treeSum = treeSum == 0 ? treeCount : treeSum * treeCount;
}

Console.WriteLine($"Trees {treeSum} encountered!");

PrintMatrixString(matrix_array);

void PrintMatrixString(string[,] matrix, string delimiter = "\n")
{
    for (int i = 0; i < matrix_array.GetLength(0); i++)
    {
        for (int k = 0; k < matrix_array.GetLength(1); k++)
        {
            Console.Write(matrix_array[i, k]);
        }

        Console.WriteLine();
    }
}

void FillMatrixArray(string[,] matrix, string[] items)
{
    for (int i = 0; i < items.Length; i++)
    {
        var itemchar = items[i].ToCharArray();

        for (int j = 0; j < itemchar.Length; j++)
        {
            matrix[i, j] = itemchar[j].ToString();
        }
    }
}