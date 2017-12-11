/*
 * Copyright (C) 2016 Dundun
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
package spiel;

import static spiel.InputListener.DOWN;
import static spiel.InputListener.INVALID;
import static spiel.InputListener.LEFT;
import static spiel.InputListener.RIGHT;
import static spiel.InputListener.UP;

/**
 *
 * @author Dundun
 */
public class Utils {

    public static int invertDirection(int direction) {
        if (direction == UP) {
            return DOWN;
        } else if (direction == RIGHT) {
            return LEFT;
        } else if (direction == LEFT) {
            return RIGHT;
        } else if (direction == DOWN) {
            return UP;
        } else {
            return INVALID;
        }
    }
}
