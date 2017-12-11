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

import armory.Factory;
import armory.Weapon;
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

    public static final String APP_NAME = "Space Invader";
    public static final String AUTHOR = "Dominik Messerschmidt (dominik.messerschmidt@continental-corporation.com)";
    public static final String[] COAUTHORS = new String[]{"Dragos Valentin Lupu (dragos.lupu@continental-corporation.com)"};

    /**
     * If DEBUG_ENABLE the application provides more detailed runtime
     * information to the user.
     */
    public static final boolean DEBUG_ENABLE = true;

    /**
     * Date Time Format
     */
    public static final DateTimeFormatter DATETIMEFORMAT = DateTimeFormatter
            .ofPattern("LL_dd_yyyy_HH:mm:ss:SSS");

    /**
     * Default application language. The initial values of Text instances shall
     * be in this language.
     */
    public static final Language DEFAULT_LANGUAGE = Language.EN;

    /**
     * Filenames
     */
    public static final String SETTINGS_FILENAME = "settings.dat";
    public static final String INPUTCONFIG_FILENAME = "inputConfig.dat";
    public static final String LOGGING_DIRECTORY = "logs/";
    public static final String LOGGING_FILENAME = "log_"
            + LocalDateTime.now().format(DATETIMEFORMAT).replace(":", "_") + ".txt";
    public static final String LOGGING_ERROR_FILENAME = "error_log_"
            + LocalDateTime.now().format(DATETIMEFORMAT).replace(":", "_") + ".txt";
    public static final String SAVEFILE_BASENAME_ARCADE = "save_arcade_";
    public static final String SAVEFILE_BASENAME_ADVENTURE = "save_adventure_";
    public static final String SAVEFILE_EXTENSION = ".dat";
    public static final String SOUND_DIRECTORY = "sound/";
    public static final String IMAGE_DIRECTORY = "images/";
    public static final String SAVES_DIRECTORY = "saves/";
    public static final String ICON_DIRECTORY = IMAGE_DIRECTORY + "icons/";
    public static final java.awt.Color COLOR_TRANSPARENT = new java.awt.Color(250, 250, 250);

    /**
     * Images
     */
    public static final String IMAGENAME_SPACESHIP = "spaceship.gif";
    public static final String IMAGENAME_CRUISER = "cruiser.gif";
    public static final String IMAGENAME_CARRIER = "carrier.gif";
    public static final String IMAGENAME_SPACESHIPACC = "spaceshipAcc.gif";
    public static final String IMAGENAME_FIGHTER = "fighter.gif";
    public static final String IMAGENAME_TURRET = "turret.gif";
    public static final String IMAGENAME_ALIEN = "alien.gif";
    public static final String IMAGENAME_ALIENFIGHTER = "alienfighter.gif";
    public static final String IMAGENAME_ALIEN_RED = "alienRed.gif";
    public static final String IMAGENAME_ALIEN_MOTHERSHIP = "alienMothership.gif";
    public static final String IMAGENAME_BOMB = "bomb.gif";
    public static final String IMAGENAME_EXPLOSION = "explosion1.gif";

    /**
     * Maps
     */
    public static final String MAPNAME_EARTH = "map_earth.gif";
    public static final int DEFAULT_MAP_WIDTH = 2600;
    public static final int DEFAULT_MAP_HEIGHT = 1800;

    /**
     * absolute screen resolution of the GUI. Screen is automatically scaled to
     * actual component size on paint.
     */
    public static final int WIDTH = 900, HEIGHT = 600;
    /**
     * Minimap bounds on screen
     */
    public static final java.awt.Rectangle MINIMAP_BOUNDS = new java.awt.Rectangle(WIDTH - 210,
            HEIGHT - 110, 200, 100);
    public static final int MINIMAP_POINT_SIZE = 2;
    /**
     * Event timer
     */
    public static final int TIMEOUT_MESSAGE = 180;
    public static final int TIMEOUT_LEVEL = 300;

    /**
     * Output settings
     */
    public static final int MAX_MESSAGE_COUNT = 5;
    public static final int LINEWIDTH = 60;

    /**
     * Time difference in millis between simulation steps. Has no relation to
     * the FPS rate.
     */
    public static final int DELTA_T = 20;

    /**
     * maximum number of available savefiles
     */
    public static final int MAX_SAVE_SLOTS = 3;

    /**
     * Directions
     */
    public static final int NORTH = 0;
    public static final int NORTHEAST = 45;
    public static final int EAST = 90;
    public static final int SOUTHEAST = 135;
    public static final int SOUTH = 180;
    public static final int SOUTHWEST = 225;
    public static final int WEST = 270;
    public static final int NORTHWEST = 315;

    /**
     * Balancing settings for Guns
     */
    public static final int DEFAULT_FLEET_SIZE = 4;
    public static final int HP_UPGRADE_PRICE = 50;
    public static final int DEFAULT_PROJECTILESPEED_SLOW = 400;
    public static final int DEFAULT_PROJECTILESPEED_MEDIUM = 800;
    public static final int DEFAULT_PROJECTILESPEED_FAST = 2000;
    public static final int DEFAULT_FIRERATE_SLOW = 10;
    public static final int DEFAULT_FIRERATE_MEDIUM = 40;
    public static final int DEFAULT_FIRERATE_FAST = 250;
    public static final int DEFAULT_DAMAGE_LOW = 1;
    public static final int DEFAULT_DAMAGE_MEDIUM = 2;
    public static final int DEFAULT_DAMAGE_HIGH = 4;
    public static final int DEFAULT_DAMAGE_EXTREME = 10;
    public static final int DEFAULT_RANGE_SHORT = 10000;
    public static final int DEFAULT_RANGE_MEDIUM = 20000;
    public static final int DEFAULT_RANGE_WIDE = 50000;
    public static final int DEFAULT_SALVO_COUNT = 6;
    public static final double DEFAULT_HEATRATE = 7;
    public static final String[] WEAPONNAMES = new String[]{"Gun", "3Burst Gun", "Sniper Gun",
        "5BurstGun", "Salvo Gun", "Rapid Gun", "8BurstGun", "Shockwave"};
    public static final Weapon[] WEAPONS = new Weapon[]{Factory.getGun(), Factory.get3BurstGun(),
        Factory.getRifleGun(), Factory.get5BurstGun(), Factory.getSalvoGun(),
        Factory.getRapidGun(), Factory.get8BurstGun(), Factory.getSchockwave()};
    /**
     * Balancing settings for Credits
     */
    public static final int[] WEAPONPRICE = new int[]{0, 50, 90, 300, 600, 3000, 5000, 8000};
    public static final int PRIZE_FIGHTER = 50;
    public static final int PRIZE_WINGMAN = 200;
    public static final int PRIZE_FIGHTER_SQUAD = (DEFAULT_FLEET_SIZE + 1) * PRIZE_FIGHTER;
    public static final int PRIZE_HUNTER_SQUAD = (DEFAULT_FLEET_SIZE + 1) * PRIZE_WINGMAN;
    public static final int PRIZE_CRUISER = 3000;
    public static final int PRIZE_CARRIER = 10000;

    /**
     * Balancing settings for Ships
     */
    public static final int DEFAULT_ACCEL_LOW = 50;
    public static final int DEFAULT_ACCEL_MED = 400;
    public static final int DEFAULT_ACCEL_HIGH = 2000;
    public static final int DEFAULT_SPEED_VERYLOW = 20;
    public static final int DEFAULT_SPEED_LOW = 50;
    public static final int DEFAULT_SPEED_MED = 300;
    public static final int DEFAULT_SPEED_HIGH = 600;

    /**
     * Teams
     */
    public enum Team {

        EARTH, ALIEN, PASSIV;

        public boolean isEnemy(Team other) {
            return this == EARTH && other == ALIEN || this == ALIEN && other == EARTH;
        }

        public boolean isAlly(Team other) {
            return this == other;
        }
    }

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
        DE("Deutsch"),
        /**
         * Rom�nesc
         */
        RO("Rom�nesc");

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
