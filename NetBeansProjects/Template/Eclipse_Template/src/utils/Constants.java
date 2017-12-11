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
 * Created 09.03.2016
 *
 * @author Dominik Messerschmidt
 * (dominik.messerschmidt@continental-corporation.com)
 *
 */
public final class Constants {

    /**
     * Applications name. TODO change this value.
     */
    public static final String APP_NAME = "Template";

    /**
     * If DEBUG_ENABLE the application provides more detailed runtime
     * information to the user.
     */
    public static final boolean DEBUG_ENABLE = true;

    /**
     * Default application language. The initial values of Text instances shall
     * be in this language.
     */
    public static final Language DEFAULT_LANGUAGE = Language.EN;

    /**
     * Filenames
     */
    public static final String SETTINGS_FILENAME = "settings.dat";
    public static final String INPUTCONFIG_FILENAME = "inputConfig.cfg";
    public static final String LOGGING_DIRECTORY = "logs/";
    public static final String LOGGING_FILENAME = "log_" + System.nanoTime() + ".txt";
    public static final String LOGGING_ERROR_FILENAME = "error_log_" + System.nanoTime() + ".txt";
    public static final String SOUND_DIRECTORY = "sound/";
    public static final String IMAGE_DIRECTORY = "images/";

    /**
     * Marker for transparency.
     */
    public static final java.awt.Color COLOR_TRANSPARENT = new java.awt.Color(250, 250, 250);

    /**
     * absolute screen resolution of the GUI. Screen is automatically scaled to
     * actual component size on paint.
     */
    public static final int WIDTH = 600, HEIGHT = 400;

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
