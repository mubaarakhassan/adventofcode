using System;
using System.Linq;

var items = System.IO.File.ReadAllLines(@"input.txt");

var highestSeatId = 0;

foreach (var item in items)
{
    var rowMin = 0;
    var rowMax = 127;
    var columnMin = 0;
    var columnMax = 7;

    foreach (var character in item)
    {
        switch (character)
        {
            case 'F':
                rowMax = (rowMin + rowMax) / 2;
                //   Console.WriteLine($"F means to take the lower half, keeping rows {start} through {end}.");
                break;
            case 'B':
                rowMin = (rowMin + rowMax - 1) / 2 + 1;
                // Console.WriteLine($"B means to take the upper half, keeping rows {start} through {end}.");
                break;
            case 'L':
                columnMax = (columnMin + columnMax) / 2;
                // Console.WriteLine($"Left means to take the lower half, keeping rows {left} through {right}.");
                break;
            case 'R':
                columnMin = (columnMin + columnMax - 1) / 2 + 1;
                //Console.WriteLine($"Right means to take the upper half, keeping rows {left} through {right}.");
                break;
        }
    }

    var seatId = (rowMin * 8) + columnMin;

    highestSeatId = highestSeatId >= seatId ? highestSeatId : seatId;
}

Console.WriteLine($"Seat ID: {highestSeatId}");