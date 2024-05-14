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
public final class Calc
{

    // Static class
    private Calc()
    {

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Context ctx = new Context();
        System.out.println("Z = " + ctx.Z);
        int dmg = getDmg(ctx);
        System.out.println("Dmg = " + dmg);
    }

    public static int getDmg(Context ctx)
    {
        int dmg = ctx.user.lvl * 2 / 5 + 2;

        return dmg;
    }

}
