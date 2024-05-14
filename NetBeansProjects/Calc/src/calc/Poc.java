/*
 * Copyright (C) 2021 Dominik Messerschmidt <dominikmesserschmidt@googlemail.com>
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
package calc;

/**
 *
 * @author Dominik Messerschmidt <dominikmesserschmidt@googlemail.com>
 */
public enum Poc
{
    FIRST(5, 7, 8, 5, 6, 9, Item.NONE);
    public int lvl, angr, spangr, def, spdef, init;
    public Item item;

    Poc(int lvl, int angr, int spangr, int def, int spdef, int init, Item item)
    {
        this.lvl = lvl;
        this.angr = angr;
        this.spangr = spangr;
        this.def = def;
        this.spdef = spdef;
        this.init = init;
    }
}
