using System;
using System.Text;
using System.Linq;

var items = System.IO.File.ReadAllLines(@"input.txt");

var matrix_array = new string[items.Length, items[0].Length];

FillMatrixArray(matrix_array, items);

var position_right = 3; // Initial position
var position_bottom = 1;
var tree_count = 0;

while (position_bottom < matrix_array.GetLength(0))
{
    if (position_right >= matrix_array.GetLength(1))
    {
        position_right = position_right - matrix_array.GetLength(1);
    }

    if(matrix_array[position_bottom, position_right] == "#")
    {
        matrix_array[position_bottom, position_right] = "X";
        tree_count++;
    }
    else
    {
        matrix_array[position_bottom, position_right] = "O";
    }

    position_right += 3;
    position_bottom++;
}

Console.WriteLine($"Trees {tree_count} encountered!");

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