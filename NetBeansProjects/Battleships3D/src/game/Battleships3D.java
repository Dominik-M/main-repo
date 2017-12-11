/*
 * Copyright (C) 2017 Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com>
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
package game;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 * Created 24.04.2017
 */
public class Battleships3D
{

    /**
     * Maximum x coordinate.
     */
    public static final int DEFAULT_WIDTH = 8;

    /**
     * Maximum y coordinate.
     */
    public static final int DEFAULT_HEIGHT = 8;

    /**
     * Maximum z coordinate.
     */
    public static final int DEFAULT_DEPTH = 8;
    private ShipType[][][] spots;
    private char[] xAchsisChars, yAchsisChars, zAchsisChars;

    public Battleships3D()
    {
        init();
    }

    public final void init()
    {
        spots = new ShipType[DEFAULT_WIDTH][DEFAULT_HEIGHT][DEFAULT_DEPTH];
        xAchsisChars = new char[DEFAULT_WIDTH];
        yAchsisChars = new char[DEFAULT_HEIGHT];
        zAchsisChars = new char[DEFAULT_DEPTH];
        for (int x = 0; x < DEFAULT_WIDTH; x++)
        {
            for (int y = 0; y < DEFAULT_HEIGHT; y++)
            {
                for (int z = 0; z < DEFAULT_DEPTH; z++)
                {
                    spots[x][y][z] = ShipType.NONE;
                }
            }
        }
        for (int i = 0; i < xAchsisChars.length; i++)
        {
            xAchsisChars[i] = (char) ('A' + i);
        }
        for (int i = 0; i < yAchsisChars.length; i++)
        {
            yAchsisChars[i] = (char) ('1' + i);
        }
        for (int i = 0; i < zAchsisChars.length; i++)
        {
            zAchsisChars[i] = (char) ('α' + i);
        }
    }

    public boolean isInGrid(int x, int y, int z)
    {
        return x >= 0 && x < DEFAULT_WIDTH && y >= 0 && y < DEFAULT_HEIGHT && z >= 0 && z < DEFAULT_DEPTH;
    }

    public ShipType getShipAt(int x, int y, int z)
    {
        if (isInGrid(x, y, z))
        {
            return spots[x][y][z];
        }
        return ShipType.NONE;
    }

    /**
     * Retrieve a String representation for a given 3D position. x is
     * represented by latin letters, y is represented by numbers and z is
     * represented by greek letters. E.g. "D 4 α" for 3|4|0.
     *
     * @param x x coordinate of the position.
     * @param y y coordinate of the position.
     * @param z z coordinate of the position.
     * @return String representation for a given 3D position.
     */
    public String getCoordinateString(int x, int y, int z)
    {
        String separator = " ";
        if (isInGrid(x, y, z))
        {
            return xAchsisChars[x] + separator + yAchsisChars[y] + separator + zAchsisChars[z];
        }
        return x + separator + y + separator + z;
    }
}
