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
 * Created Jun 17, 2021
 */
public class Context
{

    public int generation;
    public Poc user, target;
    public boolean rh, lv, ls, nm, crt, b, rlr, rll;

    public final int Z;

    public Context()
    {
        generation = 8;
        user = Poc.FIRST;
        target = Poc.FIRST;

        Z = 100 - (int) (Math.random() * 16);
    }

}
