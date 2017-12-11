/*
 * Copyright (C) 2015 Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
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

import java.awt.Color;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public final class Konstanten {

    public static final int XP_VERYLOW = 15, XP_LOW = 18, XP_NORMAL = 20, XP_HIGH = 22, XP_VERYHGH = 25;
    public static final String DEFAULT_FONT_NAME = "Consolas";
    public static final int POKE_ANZ = 6;

    public static final Color[] HAUTFARBEN = {new Color(255, 255, 255),
        new Color(225, 187, 187), new Color(185, 122, 87), new Color(160, 50, 0),
        new Color(102, 51, 0), new Color(180, 190, 0),
        new Color(240, 250, 0), new Color(80, 90, 0), new Color(180, 50, 0)};
    public static final Color ZIEGELSTEINROT = new Color(120, 20, 20);
    public static final Color WASSERBLAU = Color.BLUE;
    public static final Color VIOLETT = new java.awt.Color(150, 0, 150);
    public static final Color BACKGROUND = new Color(239, 228, 180);

    private Konstanten() {
    }
}
