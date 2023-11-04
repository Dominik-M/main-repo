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
public enum Weapon implements Serializable
{

    /**
     * "Nothing", 0, 0, false
     */
    NONE("Nothing", 0, 0, false),
    /**
     * "Dagger", 1, 20, false
     */
    DAGGER("Dagger", 1, 20, false),
    /**
     * "Sword", 2, 80, false
     */
    SWORD("Sword", 2, 80, false),
    /**
     * "Spear", 1, 100, true
     */
    SPEAR("Spear", 1, 100, true),
    /**
     * "Longsword", 2, 250, true
     */
    LONGSWORD("Longsword", 2, 250, true),
    /**
     * "Battle Axe", 3, 200, false
     */
    BATTLEAXE("Battle Axe", 3, 200, false),
    /**
     * "Halberd", 3, 350, true
     */
    HALBERD("Halberd", 3, 350, true),
    /**
     * "Zweihander", 3, true
     */
    ZWEIHANDER("Zweihander", 3, 380, true),
    /**
     * "Dragonsword", 4, 500, false
     */
    DRAGONSWORD("Dragonsword", 4, 500, false),
    /**
     * "Dragonsword +5", 5, 1000, false
     */
    DRAGONSWORD5("Dragonsword +5", 5, 1000, false);

    public final String name;
    public final int atk, value;
    public final boolean canDiagonal;

    Weapon(String n, int a, int v, boolean d)
    {
        name = n;
        atk = a;
        value = v;
        canDiagonal = d;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
