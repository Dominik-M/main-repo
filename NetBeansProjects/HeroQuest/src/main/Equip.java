/*
 * Copyright (C) 2023 Dominik Messerschmidt
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

import java.io.Serializable;

/**
 *
 * @author Dominik Messerschmidt
 */
public enum Equip implements Serializable
{
    HELMET("Helmet", "A steel helmet that increases defense by 1", 200),
    SHIELD("Shield", "A shield that increases defense by 1", 250),
    ARMOR("Armor", "An armor that increases defense by 1", 400);

    public final String name, description;
    public final int value;

    Equip(String n, String d, int v)
    {
        name = n;
        description = d;
        value = v;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
