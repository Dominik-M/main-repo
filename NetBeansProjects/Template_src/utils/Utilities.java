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
package utils;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 26.03.2016
 */
public class Utilities {

    /**
     * Rounds a double value at the given decimal place.
     *
     * @param d The double value to round.
     * @param n Number of decimal places.
     * @return String representation of the rounded number.
     */
    public static String round(double d, int n) {
        if (n <= 0) {
            return String.valueOf((int) d);
        }
        String s = String.valueOf(d);
        int i = s.indexOf(".") + 1;
        if (i + n < s.length()) {
            return s.substring(0, i + n);
        } else {
            while (s.length() < i + n) {
                s += "0";
            }
            return s;
        }
    }

    public static double toDeg(double rad) {
        return rad * 180 / Math.PI;
    }

    public static double toRad(double degree) {
        return degree / 180 * Math.PI;
    }

    public static int invertDirection(int direction) {
        if (direction == Constants.DIRECTION_UP) {
            return Constants.DIRECTION_DOWN;
        } else if (direction == Constants.DIRECTION_RIGHT) {
            return Constants.DIRECTION_LEFT;
        } else if (direction == Constants.DIRECTION_LEFT) {
            return Constants.DIRECTION_RIGHT;
        } else if (direction == Constants.DIRECTION_DOWN) {
            return Constants.DIRECTION_UP;
        } else {
            return Constants.DIRECTION_INVALID;
        }
    }
}
