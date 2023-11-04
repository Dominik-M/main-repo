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
package dmsr.anno.world;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 * Created 31.10.2017
 */
public class Map
{

    private final Terrain[][] GROUND;
    private Terrain defaultGround;
    public final int WIDTH, HEIGHT;

    public Map(int width, int height)
    {
        WIDTH = width;
        HEIGHT = height;
        GROUND = new Terrain[width][height];
        defaultGround = Terrain.NONE;
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                GROUND[x][y] = Terrain.GRAS;
            }
        }
    }

    public Terrain getGround(int x, int y)
    {
        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT)
        {
            return GROUND[x][y];
        }
        return defaultGround;
    }

    public void setDefaultTerrain(Terrain t)
    {
        if (t != null)
        {
            defaultGround = t;
        }
    }

    public Terrain getDefaultTerrain()
    {
        return defaultGround;
    }
}
