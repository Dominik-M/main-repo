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
package world;

import java.awt.Color;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 * Created 31.10.2017
 */
public enum Terrain
{
    NONE(true, Color.lightGray),
    COAST(true, Color.cyan),
    SEA(true, Color.blue),
    GRAS(true, Color.green),
    MOUNTAIN(true, Color.gray),
    RIVER(true, Color.cyan),
    DESERT(true, Color.yellow);

    public final boolean BLOCKED;
    public final Color COLOR;

    Terrain(boolean blocked, Color color)
    {
        this.BLOCKED = blocked;
        this.COLOR = color;
    }
}
