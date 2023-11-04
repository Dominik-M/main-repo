/**
 * Copyright (C) 2016 Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package platform.utils;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 26.03.2016
 */
public class Utilities
{

    /**
     * Rounds a double value at the given decimal place.
     *
     * @param d The double value to round.
     * @param n Number of decimal places.
     * @return String representation of the rounded number.
     */
    public static String round(double d, int n)
    {
        if (n <= 0)
        {
            return String.valueOf((int) d);
        }
        String s = String.valueOf(d);
        int i = s.indexOf(".") + 1;
        if (i + n < s.length())
        {
            return s.substring(0, i + n);
        }
        else
        {
            while (s.length() < i + n)
            {
                s += "0";
            }
            return s;
        }
    }

    /**
     * Convert an angle given in radiant to degree. Makes sure to have no
     * multiple rotations: The returned value is always between (inclusive) 0
     * and 360.
     *
     * @param rad an angle in radiant.
     * @return the given angle in degrees.
     */
    public static double toDeg(double rad)
    {
        double deg = rad * 180 / Math.PI;
        while (deg > 360)
        {
            deg -= 360;
        }
        while (deg < 0)
        {
            deg += 360;
        }
        return deg;
    }

    /**
     * Convert an angle given in degrees to radiant. Makes sure to have no
     * multiple rotations: The returned value is always between (inclusive) 0
     * and 2PI.
     *
     * @param degree an angle in degrees.
     * @return the given angle in radiant.
     */
    public static double toRad(double degree)
    {
        while (degree > 360)
        {
            degree -= 360;
        }
        while (degree < 0)
        {
            degree += 360;
        }
        return degree / 180 * Math.PI;
    }
}
