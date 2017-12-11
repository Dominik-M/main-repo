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

import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created 09.03.2016
 *
 * @author Dominik Messerschmidt
 * (dominik.messerschmidt@continental-corporation.com)
 *
 */
public final class Constants {

    /**
     * Applications name.
     */
    public static final String APP_NAME = "Pokemon";
    /**
     * Applications Author and Co-authors
     */
    public static final String AUTHOR = "Dominik Messerschmidt (dominik.messerschmidt@continental-corporation.com)";
    public static final String[] COAUTHORS = new String[]{};

    /**
     * Date Time Format
     */
    public static final DateTimeFormatter DATETIMEFORMAT = DateTimeFormatter
            .ofPattern("LL_dd_yyyy_HH:mm:ss:SSS");

    /**
     * If DEBUG_ENABLE the application provides more detailed runtime
     * information to the user.
     */
    public static final boolean DEBUG_ENABLE = true;

    /**
     * Default application language. The initial values of Text instances shall
     * be in this language.
     */
    public static final Language DEFAULT_LANGUAGE = Language.DE;

    /**
     * Filenames
     */
    public static final String SETTINGS_FILENAME = "settings.dat";
    public static final String INPUTCONFIG_FILENAME = "inputConfig.cfg";
    public static final String LOGGING_DIRECTORY = "logs/";
    public static final String LOGGING_FILENAME = "log_"
            + LocalDateTime.now().format(DATETIMEFORMAT).replace(":", "_") + ".txt";
    public static final String LOGGING_ERROR_FILENAME = "error_log_"
            + LocalDateTime.now().format(DATETIMEFORMAT).replace(":", "_") + ".txt";
    public static final String SOUND_DIRECTORY = "sound/";
    public static final String IMAGE_DIRECTORY = "images/";

    public static final int XP_VERYLOW = 15, XP_LOW = 18, XP_NORMAL = 20, XP_HIGH = 22, XP_VERYHGH = 25;
    public static final int DIRECTION_INVALID = 0, DIRECTION_UP = 1, DIRECTION_RIGHT = 2, DIRECTION_DOWN = 3, DIRECTION_LEFT = 4;
    public static final String DEFAULT_FONT_NAME = "Consolas";
    public static final int POKE_ANZ = 6;

    public static final Color[] HAUTFARBEN = {new Color(255, 255, 255),
        new Color(225, 187, 187), new Color(185, 122, 87), new Color(160, 50, 0),
        new Color(102, 51, 0), new Color(180, 190, 0),
        new Color(240, 250, 0), new Color(80, 90, 0), new Color(180, 50, 0)};
    public static final Color ZIEGELSTEINROT = new Color(120, 20, 20);
    public static final Color WASSERBLAU = Color.BLUE;
    public static final Color VIOLETT = new java.awt.Color(150, 0, 150);

    /**
     * Marker for transparency.
     */
    public static final Color COLOR_TRANSPARENT = new Color(250, 250, 250);

    /**
     * ------------------- Graphic Constants --------------------- *
     */
    /**
     * absolute screen resolution of the GUI. Screen is automatically scaled to
     * actual component size on paint.
     */
    public static final int WIDTH = 600, HEIGHT = 400;
    /**
     * absolute bounds on screen of output text
     */
    public static final java.awt.Rectangle OUTPUT_BOUNDS = new java.awt.Rectangle(0, 2 * HEIGHT / 3, WIDTH, HEIGHT / 3);
    /**
     * Background color of output panel.
     */
    public static final Color OUTPUT_BACKGROUND = new Color(239, 228, 180);

    /**
     * Defines available languages for this application. Created 10.03.2016
     *
     * @author Dominik Messerschmidt
     * (dominik.messerschmidt@continental-corporation.com)
     *
     */
    public enum Language {

        /**
         * English
         */
        EN("English"),
        /**
         * German
         */
        DE("Deutsch");

        private String name;

        Language(String name) {
            this.name = name;
        }

        public String getFileName() {
            return "lang_" + name().toLowerCase() + ".txt";
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * cant create instance of this class.
     */
    private Constants() {
    }
}
