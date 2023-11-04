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

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;

/**
 * Created 10.03.2016
 *
 * @author Dominik Messerschmidt
 * (dominik.messerschmidt@continental-corporation.com)
 *
 */
public class Settings
{

    public static final File SETTINGS_FILE = new File(Constants.DATA_DIRECTORY
            + Constants.SETTINGS_FILENAME);
    private static final Dictionary<String, Object> SETTINGS = new Dictionary<String, Object>();
    private static final LinkedList<SettingsListener> listener = new LinkedList<>();

    private Settings()
    {
    }

    public static boolean addListener(SettingsListener l)
    {
        return listener.add(l);
    }

    public static boolean removeListener(SettingsListener l)
    {
        return listener.remove(l);
    }

    public static Dictionary<String, Object> get()
    {
        return SETTINGS.clone();
    }

    public static Object get(String key)
    {
        return SETTINGS.get(key.toLowerCase());
    }

    public static boolean set(String key, Object value)
    {
        if (SETTINGS.containsKey(key.toLowerCase()))
        {
            if (SETTINGS.get(key.toLowerCase()) == null
                    || SETTINGS.get(key.toLowerCase()).getClass().isInstance(value))
            {
                if (SETTINGS.set(key.toLowerCase(), value))
                {
                    IO.println("Settings.set(): " + key + " = " + value, IO.MessageType.DEBUG);
                    for (SettingsListener l : listener)
                    {
                        l.preferenceChanged(key, value);
                    }
                    return true;
                }
                else
                {
                    IO.println("Settings.set(): Failed to set " + key + " = " + value,
                            IO.MessageType.ERROR);
                    return false;
                }
            }
            else
            {
                IO.println("Settings.set(): Type mismatch ERROR: " + key + " " + value,
                        IO.MessageType.ERROR);
                return false;
            }
        }
        else
        {
            if (SETTINGS.add(key.toLowerCase(), value))
            {
                IO.println("Settings.set(): Added dynamic preference key: " + key,
                        IO.MessageType.DEBUG);
                if (addDefaultInterpreterCommand(key, value))
                {
                    IO.println(
                            "Settings.set(): Added default Interpreter Command: " + Interpreter.get(key),
                            IO.MessageType.DEBUG);
                }
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public static boolean addDefaultInterpreterCommand(String key, Object value)
    {
        if (Integer.class.isInstance(value))
        {
            return Interpreter.add(new Command(key, "Sets " + key, "int")
            {

                @Override
                public boolean execute(String... params)
                {
                    int value = Integer.parseInt(params[0]);
                    Settings.set(this.name(), value);
                    return true;
                }
            });
        }
        else if (Boolean.class.isInstance(value))
        {
            return Interpreter.add(new Command(key, "Sets " + key, "Boolean")
            {

                @Override
                public boolean execute(String... params)
                {
                    boolean value = Boolean.parseBoolean(params[0]);
                    Settings.set(this.name(), value);
                    return true;
                }
            });
        }
        else if (Double.class.isInstance(value))
        {
            return Interpreter.add(new Command(key, "Sets " + key, "int")
            {

                @Override
                public boolean execute(String... params)
                {
                    double value = Double.parseDouble(params[0]);
                    Settings.set(this.name(), value);
                    return true;
                }
            });
        }
        else if (String.class.isInstance(value))
        {
            return Interpreter.add(new Command(key, "Sets " + key, "int")
            {

                @Override
                public boolean execute(String... params)
                {
                    String value = params[0];
                    Settings.set(this.name(), value);
                    return true;
                }
            });
        }
        else
        {
            return false;
        }
    }

    public static int setAll(Dictionary<String, Object> settings)
    {
        int n = 0;
        for (String key : settings.getKeys())
        {
            if (set(key, settings.get(key)))
            {
                n++;
            }
        }
        return n;
    }

    public static boolean isDefined(String key)
    {
        return SETTINGS.containsKey(key.toLowerCase());
    }

    @SuppressWarnings("rawtypes")
    public static boolean isDefined(String key, Class c)
    {
        if (SETTINGS.containsKey(key.toLowerCase()))
        {
            return c.isInstance(SETTINGS.get(key.toLowerCase()));
        }
        else
        {
            return false;
        }
    }

    public static LinkedList<String> getKeys()
    {
        return SETTINGS.getKeys();
    }

    public static void printPrefs(PrintStream out)
    {
        SETTINGS.printRecursive(out);
    }

    public static void resetSettings()
    {
        set("screenwidth", Constants.DEFAULT_WIDTH);
        set("screenheight", Constants.DEFAULT_HEIGHT);
        set("preferredSize", new java.awt.Dimension(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT));
        set("preferredWindowPosition", new java.awt.Point());
        set("font", new java.awt.Font("Consolas", 0, 26));
        set("backgroundColor", Color.WHITE);
        set("fontColor", Color.BLACK);
        set("showCloseWarning", true);
        set("loggingEnabled", true);
        set("soundOn", true);
        set("showConsole", true);
        set("preferredLanguage", Constants.DEFAULT_LANGUAGE);
        set("autoKeyAdd", Boolean.TRUE);
    }

    public static boolean loadSettings()
    {
        try
        {
            FileInputStream fis = new FileInputStream(SETTINGS_FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            @SuppressWarnings("unchecked")
            Dictionary<String, Object> settings = (Dictionary<String, Object>) ois.readObject();
            fis.close();
            ois.close();
            int n = setAll(settings);
            IO.println("Settings.loadSettings(): Succesfully loaded " + n + " preferences",
                    IO.MessageType.DEBUG);
            return true;
        }
        catch (Exception ex)
        {
            IO.println("failed to load Settings File", IO.MessageType.ERROR);
            IO.printException(ex);
            return false;
        }
    }

    public static void saveSettings()
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(SETTINGS_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(SETTINGS);
            fos.close();
            oos.close();
            IO.println("saved Settings File", IO.MessageType.DEBUG);
        }
        catch (Exception ex)
        {
            IO.println("failed to save Settings File", IO.MessageType.ERROR);
            IO.printException(ex);
        }
    }
}
