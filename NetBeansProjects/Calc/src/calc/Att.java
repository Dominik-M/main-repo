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
public enum Att
{
    TACKLE(40, 100, Type.NORMAL, true);

    public int bs, gena;
    public Type type;
    public boolean phys;

    Att(int bs, int gena, Type type, boolean phys)
    {
        this.bs = bs;
        this.gena = gena;
        this.type = type;
        this.phys = phys;
    }
}