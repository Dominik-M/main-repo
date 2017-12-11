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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

/**
 * Created 10.03.2016
 *
 * @author Dominik Messerschmidt
 * (dominik.messerschmidt@continental-corporation.com)
 *
 */
public class Settings implements java.io.Serializable, java.lang.Cloneable {

    /**
     *
     */
    private static final long serialVersionUID = 7826059120096517414L;

    /**
     *
     */
    public static final File SETTINGS_FILE = new File(utils.Constants.SETTINGS_FILENAME);
    private static Settings settings = loadSettings();
    private static final LinkedList<SettingsListener> LISTENER = new LinkedList<>();

    /**
     * GUI settings
     */
    public java.awt.Dimension preferredSize = new java.awt.Dimension(Constants.GRAPHIC_WIDTH,
            Constants.GRAPHIC_HEIGHT);

    /**
     *
     */
    public java.awt.Point preferredWindowPosition = new java.awt.Point();

    /**
     *
     */
    public java.awt.Font font = new java.awt.Font(Constants.DEFAULT_FONT_NAME, 0, 16);

    /**
     *
     */
    public java.awt.Color backgroundColor = new Color(200, 200, 200);

    /**
     *
     */
    public java.awt.Color fontColor = Color.BLACK;
    public boolean showCloseWarning = true,
            loggingEnabled = true,
            soundOn = true,
            mouseEnabled = true,
            drawBounds = false,
            drawLifes = true,
            consoleVisible = true;

    /**
     *
     */
    public Constants.Language preferredLanguage = Constants.DEFAULT_LANGUAGE;

    /**
     *
     */
    public int fps = 60;

    /**
     * Loads Settings from settingsfile.
     *
     * @return
     */
    public static Settings loadSettings() {
        try {
            FileInputStream fis = new FileInputStream(SETTINGS_FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Settings s = (Settings) ois.readObject();
            fis.close();
            ois.close();
            IO.println("loaded Settings File", IO.MessageType.DEBUG);
            return s;
        } catch (Exception ex) {
            IO.println("failed to load Settings File", IO.MessageType.ERROR);
            IO.printException(ex);
            return new Settings();
        }
    }

    /**
     *
     */
    public static void saveSettings() {
        try {
            FileOutputStream fos = new FileOutputStream(SETTINGS_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(settings);
            fos.close();
            oos.close();
            IO.println("saved Settings File", IO.MessageType.DEBUG);
        } catch (Exception ex) {
            IO.println("failed to save Settings File", IO.MessageType.ERROR);
            IO.printException(ex);
        }
    }

    /**
     *
     * @param l
     */
    public static void addSettingsListener(SettingsListener l) {
        if (!LISTENER.contains(l)) {
            LISTENER.add(l);
        }
    }

    /**
     *
     * @param l
     * @return
     */
    public static boolean removeSettingsListener(SettingsListener l) {
        return LISTENER.remove(l);
    }

    /**
     *
     * @return
     */
    public static Settings getSettings() {
        try {
            return (Settings) settings.clone();
        } catch (CloneNotSupportedException ex) {
            IO.printException(ex);
            return settings;
        }
    }

    /**
     *
     * @param s
     */
    public static void setSettings(Settings s) {
        settings = s;
        for (SettingsListener l : LISTENER) {
            l.settingsChanged();
        }
    }
}
