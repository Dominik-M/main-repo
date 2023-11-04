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
 *
 * @author Dominik Messerschmidt
 */
public class ShiftPuzzle
{

    /**
     * Side length of the puzzle square in tiles
     */
    private int size;
    private int shifted = 0;
    /**
     * Ordinate values of tiles in puzzle square. Where highest number (=
     * size*size) is the free tile. Arranged like following example of solved
     * puzzle with size 3: 1 2 3 4 5 6 7 8 9
     */
    private int[][] tiles;

    public ShiftPuzzle(int size)
    {
        this.size = size;
        init();
    }

    private ShiftPuzzle(int size, int[][] tiles)
    {
        this.size = size;
        this.tiles = new int[size][];
        for (int x = 0; x < size; x++)
        {
            // Caution! System.arraycopy has implicit optimization an just copies pointers instead of values, i.e. NOT cloning them.
            // System.arraycopy(tiles, 0, this.tiles, 0, size);
            this.tiles[x] = new int[size];
            for (int y = 0; y < size; y++)
            {
                this.tiles[x][y] = tiles[x][y];
            }
        }
    }

    /**
     *
     * @return Copy of that instance
     */
    @Override
    public ShiftPuzzle clone()
    {
        return new ShiftPuzzle(size, tiles);
    }

    public final void init()
    {
        shifted = 0;
        System.out.println("SlidePuzzle(" + size + ").init()");
        if (size <= 1)
        {
            throw new IllegalArgumentException("SlidePuzzle.init() failed! Size must be greater than 1. Instance was created but is invalid. Please set size to valid value and recall init()");
        }
        tiles = new int[size][size];
        LinkedList<Integer> assignedValues = new LinkedList<>();
        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                int value;
                do
                {
                    value = (int) (Math.random() * size * size + 1);
                }
                while (assignedValues.contains(value));
                tiles[x][y] = value;
                assignedValues.add(value);
            }
        }
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public int getTile(int x, int y)
    {
        return tiles[x][y];
    }

    public boolean isComplete()
    {
        int value = 1;
        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                if (getTile(y, x) != value)
                {
                    return false;
                }
                value++;
            }
        }
        return true;
    }

    public boolean isFree(int x, int y)
    {
        if (checkBounds(x, y))
        {
            return getTile(x, y) == size * size;
        }
        else
        {
            return false;
        }
    }

    public void switchTilesForcefully(int x1, int y1, int x2, int y2)
    {
        System.out.println("Du hast geschummelt!");
        switchTiles(x1, y1, x2, y2);
    }

    public boolean shiftTile(int x, int y)
    {
        if (canMove(x, y))
        {
            //@tOdo This is a ver bad design. There should be a function providing all adjacent tiles and here just iterate through this collection, not do the same fuckting shit all the time again and write damn error prown replicated code AGAIN and AGAIn fuck shit nooeeewwwww

            if (isFree(x + 1, y)) // Right
            {
                switchTiles(x, y, x + 1, y);
            }
            else if (isFree(x - 1, y)) // Left
            {
                switchTiles(x, y, x - 1, y);
            }
            else if (isFree(x, y + 1)) // Down
            {
                switchTiles(x, y, x, y + 1);
            }
            else if (isFree(x, y - 1)) // Up
            {
                switchTiles(x, y, x, y - 1);
            }
            else
            {
                throw new IllegalArgumentException("SlidePuzzle.ShiftTile(" + x + "," + y + "): Invalid State ERROR!!!");
            }
            shifted++;
            return true;
        }
        return false;
    }

    private void switchTiles(int x1, int y1, int x2, int y2)
    {
        int value = tiles[x1][y1];
        tiles[x1][y1] = tiles[x2][y2];
        tiles[x2][y2] = value;
    }

    public boolean canMove(int x, int y)
    {
        return !isFree(x, y)
                && (isFree(x + 1, y) || isFree(x - 1, y) || isFree(x, y + 1) || isFree(x, y - 1));
    }

    public boolean checkBounds(int x, int y)
    {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    public LinkedList<IntTuple> getMoveableTiles()
    {
        LinkedList<IntTuple> result = new LinkedList<>();

        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                if (canMove(x, y))
                {
                    result.add(new IntTuple(x, y));
                }
            }
        }
        return result;
    }

    public IntTuple getFreeTile()
    {
        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                if (isFree(x, y))
                {
                    return new IntTuple(x, y);
                }
            }
        }
        return null;
    }

    public int getShifted()
    {
        return shifted;
    }

    @Override
    public String toString()
    {
        String str = "SlidePuzzle size " + size + "\n";
        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                str += "  " + tiles[x][y];
            }
            str += "\n";
        }
        return str;
    }

    public int[][] getTiles()
    {
        return clone().tiles;
    }
}
