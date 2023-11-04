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
public final class Constants
{

    /**
     * Applications name.
     */
    public static final String APP_NAME = "Pokemon";
    /**
     * Applications Author and Co-authors
     */
    public static final String AUTHOR = "Dominik Messerschmidt (dominik.messerschmidt@continental-corporation.com)";

    /**
     *
     */
    public static final String[] COAUTHORS = new String[]
    {
    };

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
    public static final IO.Language DEFAULT_LANGUAGE = IO.Language.DE;

    /*
     * Filenames
     */
    /**
     * "settings.dat".
     */
    public static final String SETTINGS_FILENAME = "settings.dat";

    /**
     * "images.dat".
     */
    public static final String IMAGE_CACHE_FILENAME = "images.dat";

    /**
     * "save.dat".
     */
    public static final String SAVE_FILENAME = "save.dat";

    /**
     * "inputConfig.cfg".
     */
    public static final String INPUTCONFIG_FILENAME = "inputConfig.cfg";

    /**
     * "logs/".
     */
    public static final String LOGGING_DIRECTORY = "logs/";
    /**
     * "data/".
     */
    public static final String DATA_DIRECTORY = "data/";

    /**
     *
     */
    public static final String LOGGING_FILENAME = "log_"
            + LocalDateTime.now().format(DATETIMEFORMAT).replace(":", "_") + ".txt";

    /**
     *
     */
    public static final String LOGGING_ERROR_FILENAME = "error_log_"
            + LocalDateTime.now().format(DATETIMEFORMAT).replace(":", "_") + ".txt";

    /**
     *
     */
    public static final String SOUND_DIRECTORY = "sound/";

    /**
     *
     */
    public static final String IMAGE_DIRECTORY = "images/";
    /**
     * ------------------- Sounds --------------------- *
     */
    public static final int SOUND_LOOP_CONTINUOUSLY = -1;
    /**
     * Soundfile path of Alabastia theme
     */
    public static final String SOUND_FILENAME_ALABASTIA = SOUND_DIRECTORY + "alabastia.wav";

    /**
     * Soundfile path of Battle theme
     */
    public static final String SOUND_FILENAME_BATTLE = SOUND_DIRECTORY + "battle.wav";

    /**
     * Soundfile path of Route 4 theme
     */
    public static final String SOUND_FILENAME_ROUTE4 = SOUND_DIRECTORY + "route4.wav";

    /**
     * Soundfile path of Vertania City theme
     */
    public static final String SOUND_FILENAME_VERTANIA = SOUND_DIRECTORY + "vertaniaCity.wav";

    /**
     * ------------------- Graphic Constants --------------------- *
     */
    /**
     * absolute screen resolution of the GUI. Screen is automatically scaled to
     * actual component size on paint.
     */
    public static final int GRAPHIC_WIDTH = 256, GRAPHIC_HEIGHT = 256;
    public static final int POK_SPRITE_WIDTH = 64, POK_SPRITE_HEIGHT = 64;
    /**
     * Size of frame borders in px
     */
    public static final int BORDER_SIZE = 8;
    /**
     * absolute bounds on screen of output text.
     */
    public static final java.awt.Rectangle OUTPUT_BOUNDS = new java.awt.Rectangle(0, 2 * GRAPHIC_HEIGHT / 3, GRAPHIC_WIDTH, GRAPHIC_HEIGHT / 3);

    /**
     * absolute bounds on screen of main menu in game.
     */
    public static final java.awt.Rectangle MENU_BOUNDS = new java.awt.Rectangle(2 * GRAPHIC_WIDTH / 3 - BORDER_SIZE * 3, 0, GRAPHIC_WIDTH / 3 + BORDER_SIZE * 3, GRAPHIC_HEIGHT);
    /**
     * absolute bounds on screen of item menu in game.
     */
    public static final java.awt.Rectangle ITEM_MENU_BOUNDS = new java.awt.Rectangle(0, 0, 2 * GRAPHIC_WIDTH / 3 + BORDER_SIZE * 3, GRAPHIC_HEIGHT);
    /**
     * Background color of output panel.
     */
    public static final Color OUTPUT_BACKGROUND = new Color(239, 228, 180);
    /**
     * Marker for transparency.
     */
    public static final Color COLOR_TRANSPARENT = new Color(250, 250, 250);
    /**
     * Size of the marker indicating selected options in px
     */
    public static final int SELECTION_MARKER_SIZE = 10;
    /**
     * Max number of characters per output line
     */
    public static final int LINEWIDTH = 22;
    /**
     * number of lines in the output frame
     */
    public static final int OUTPUT_LINES = 3;
    /**
     *
     */
    public static final String DEFAULT_FONT_NAME = "Monospaced";

    /**
     *
     */
    public static final Color[] HAUTFARBEN =
    {
        new Color(255, 255, 255),
        new Color(225, 187, 187), new Color(185, 122, 87), new Color(160, 50, 0),
        new Color(102, 51, 0), new Color(180, 190, 0),
        new Color(240, 250, 0), new Color(80, 90, 0), new Color(180, 50, 0)
    };

    /**
     *
     */
    public static final Color ZIEGELSTEINROT = new Color(120, 20, 20);

    /**
     *
     */
    public static final Color WASSERBLAU = Color.BLUE;

    /**
     *
     */
    public static final Color VIOLETT = new java.awt.Color(150, 0, 150);

    /*
     * ------------------- Game balance settings --------------------- *
     */
    /**
     * Maxmimum number of Pokemon a Trainer may carry.
     */
    public static final int POKE_ANZ = 6;

    /**
     * Maximum number of Attacks a Pokemon may know at once.
     */
    public static final int MAX_ATTACK_COUNT = 4;
    /**
     * Base values to calculate needed XP
     */
    public static final int XP_VERYLOW = 10, XP_LOW = 15, XP_NORMAL = 20, XP_HIGH = 25, XP_VERYHGH = 30;
    /**
     * Heading directions
     */
    public static final int DIRECTION_INVALID = 0, DIRECTION_UP = 1, DIRECTION_RIGHT = 2, DIRECTION_DOWN = 3, DIRECTION_LEFT = 4;

    /**
     * Raw rates for catch propability calculation.
     */
    public static final int CATCHRATE_LOW = 500, CATCHRATE_MED = 2000, CATCHRATE_HIGH = 5000, CATCHRATE_MAX = -1;
    /**
     * Propability in percent to wake up if asleep and using an attack during
     * fight.
     */
    public static final int WAKEUP_PROPABILITY = 30;

    /**
     * Propability in percent to perform an attack during fight in spite of
     * being paralyzed.
     */
    public static final int PARRESIST_PROPABILITY = 70;

    /**
     * Propability in percent to face a wild Pokemon when entering grass.
     */
    public static final int WILD_POK_APPEAR_PROPABILITY = 20;

    /**
     * Total number of Pokemon/Attack Typs.
     */
    public static final int NUMBER_OF_TYPS = 14;

    /**
     * Maximum Deteminant Value per Stat of a Pokemon.
     */
    public static final int DV_MAX = 31;

    /**
     * Minimal Deteminant Value per Stat of a Pokemon.
     */
    public static final int DV_MIN = 0;

    /**
     * Maximum Effort Values per Stat of a Pokemon.
     */
    public static final int FP_MAX_STAT = 255;
    /**
     * Total maximum Effort Values of a Pokemon.
     */
    public static final int FP_MAX_TOTAL = 510;

    /**
     * Damage factor table of Typ efficiency.
     */
    public static final double[] TYP_FACTOR_TABLE =
    {
        1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.5, 1.0, 1.0, 2.0, 1.0, 1.0,
        1.0, 0.5, 2.0, 0.5, 0.5, 1.0, 1.0, 1.5, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0,
        1.0, 0.4, 0.5, 2.5, 0.5, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
        1.0, 2.0, 0.2, 0.5, 2.0, 1.0, 0.5, 1.5, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0,
        1.0, 2.0, 0.3, 1.0, 0.5, 1.0, 1.0, 1.5, 1.0, 1.0, 1.0, 1.5, 1.5, 1.0,
        0.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5, 2.0, 1.0, 0.0, 0.0, 2.0,
        1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
        0.6, 0.5, 2.5, 3.0, 2.0, 1.0, 0.0, 0.5, 0.5, 1.0, 0.5, 1.5, 2.0, 1.0,
        1.0, 1.5, 1.0, 0.2, 1.5, 1.0, 1.0, 1.5, 0.5, 1.0, 1.5, 0.5, 1.0, 1.0,
        1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 2.0, 0.5, 1.0, 0.5, 1.0, 2.0,
        1.0, 0.5, 0.6, 0.4, 3.0, 1.0, 2.0, 0.0, 0.5, 0.5, 1.5, 0.5, 0.5, 1.0,
        1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.5, 2.0, 2.0, 1.0, 1.0, 0.5,
        0.4, 2.0, 1.0, 0.5, 1.0, 1.0, 2.0, 0.5, 0.0, 1.0, 0.5, 2.0, 0.5, 1.0,
        1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0, 1.5, 0.0, 1.0, 2.0, 1.0, 0.5
    };

    /**
     * System constants
     */
    public static final int INVALID_INDEX = -1;

    /**
     * cant create instance of this class.
     */
    private Constants()
    {
    }
}
