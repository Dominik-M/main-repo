/*
 * Copyright (C) 2019 Dominik Messerschmidt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package main;

import java.util.LinkedList;

/**
 * Simple Container class for pair of two Objects
 *
 * @author Dominik Messerschmidt
 */
public class IntTuple
{

    public final int X, Y;

    public IntTuple(int x, int y)
    {
        X = x;
        Y = y;
    }

    public boolean equals(IntTuple other)
    {
        return X == other.X && Y == other.Y;
    }

    public static LinkedList<IntTuple> toTileList(int[] field)
    {
        LinkedList<IntTuple> list = new LinkedList<>();
        for (int i = 0; i + 1 < field.length; i += 2)
        {
            list.add(new IntTuple(field[i], field[i + 1]));
        }
        return list;
    }

    public static LinkedList<IntTuple> parse(String input)
    {
        LinkedList<IntTuple> retVal = new LinkedList<>();
        int caret = 0;
        while (caret >= 0 && caret < input.length())
        {
            int start = input.indexOf("(", caret);
            if (start < 0)
            {
                break;
            }
            int end = input.indexOf(",", start);
            if (end < 0 || end - start < 2)
            {
                break;
            }
            int x = Integer.parseInt(input.substring(start + 1, end));

            start = end;
            end = input.indexOf(")", start);
            if (end < 0 || end - start < 2)
            {
                break;
            }
            int y = Integer.parseInt(input.substring(start + 1, end));
            retVal.add(new IntTuple(x, y));
            caret = end;
        }
        return retVal;
    }

}
