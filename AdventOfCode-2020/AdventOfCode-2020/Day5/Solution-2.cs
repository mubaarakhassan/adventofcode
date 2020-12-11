using System;
using System.Collections.Generic;
using System.Linq;

var items = System.IO.File.ReadAllLines(@"input.txt");

var seatList = FillSeatIds(items);

var seatId = seatList.Where((item, index) => seatList[index + 1] - item != 1).First() + 1;

Console.WriteLine($"Seat {seatId} found!");

List<int> FillSeatIds(string[] items)
{
    var list = new List<int>();

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
                    break;
                case 'B':
                    rowMin = (rowMin + rowMax - 1) / 2 + 1;
                    break;
                case 'L':
                    columnMax = (columnMin + columnMax) / 2;
                    break;
                case 'R':
                    columnMin = (columnMin + columnMax - 1) / 2 + 1;
                    break;
                default:
                    throw new NotImplementedException();
            }
        }

        var seatId = (rowMin * 8) + columnMin;

        list.Add(seatId);
    }

    list.Sort();

    return list;
}