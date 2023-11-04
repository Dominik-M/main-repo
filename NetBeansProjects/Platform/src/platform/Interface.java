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
package platform;

import java.awt.event.ActionListener;
import platform.graphic.InputConfig;
import platform.graphic.MainFrame;
import platform.graphic.MainPanel;
import platform.graphic.ProgressPanel;
import platform.image.ImageIO;
import platform.sound.Sound;
import platform.utils.Constants;
import platform.utils.IO;
import platform.utils.IO.Language;
import platform.utils.Interpreter;
import platform.utils.Settings;

/**
 * High Level Application Interface. Created 09.03.2016
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 *
 */
public class Interface
{

    public static ActionListener onReset, onClose;

    private Interface()
    {
    }

    /**
     * Triggers a reset if onReset is defined.
     */
    public static void reset()
    {
        IO.println("Reset trigerred", IO.MessageType.DEBUG);
        if (onReset != null)
        {
            onReset.actionPerformed(null);
        }
    }

    /**
     * Initializes the Application. Loads Settings and Input configuration,
     * initializes logging, creates missing directories, loads sounds and images
     * from the specified directories and inititalizes default Interpreter
     * Commands. Also displays the progress in the MainFrame instance. This
     * method shall be called on the applications startup.
     */
    public static void initAll()
    {
        if (!Settings.loadSettings())
        {
            Settings.resetSettings();
        }
        IO.initLogs();
        java.io.File dir = new java.io.File(Constants.IMAGE_DIRECTORY);
        if (!dir.exists())
        {
            dir.mkdir();
        }
        dir = new java.io.File(Constants.SOUND_DIRECTORY);
        if (!dir.exists())
        {
            dir.mkdir();
        }
        dir = new java.io.File(Constants.DATA_DIRECTORY);
        if (!dir.exists())
        {
            dir.mkdir();
        }
        InputConfig.saveConfig();
        boolean ok = IO.loadLanguageFile((Language) Settings.get("preferredLanguage"));
        IO.createAllLanguageFiles();
        Settings.saveSettings();
        if (!ok)
        {
            IO.loadLanguageFile((Language) Settings.get("preferredLanguage"));
        }
        MainFrame.setNimbusLookAndFeel();
        MainPanel oldMain = MainFrame.FRAME.setMainPanel(ProgressPanel.getInstance());
        MainFrame.FRAME.setVisible(true);
        Sound.initClips();
        ImageIO.initAllSprites();
        ProgressPanel.setText(IO.translate("FINISH"));
        Interpreter.initDefaultCommands();
        if (oldMain != null)
        {
            MainFrame.FRAME.setMainPanel(oldMain);
        }
    }

    /**
     * Exits the application. Closes logs and saves Settings and Configurations.
     * Triggers a close event if onClose is defined.
     */
    public static void shutdown()
    {
        if (onClose != null)
        {
            onClose.actionPerformed(null);
        }
        Settings.saveSettings();
        IO.saveLanguageFile();
        IO.closeLogs();
        System.exit(0);
    }
}
