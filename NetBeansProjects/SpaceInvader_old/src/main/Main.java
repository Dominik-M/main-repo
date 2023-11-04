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
package main;

import armory.Factory;
import graphic.InputConfig;
import graphic.MainFrame;
import graphic.ProgressPanel;
import sound.Sound;
import utils.IO;
import utils.Settings;
import utils.Text;

/**
 * Created 09.03.2016
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 *
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        writeAllFiles();
        if (Text.getCurrentLanguage() != Settings.getSettings().preferredLanguage) {
            Text.switchLanguage(Settings.getSettings().preferredLanguage);
        }
        MainFrame.setNimbusLookAndFeel();
        MainFrame.FRAME.setMainPanel(ProgressPanel.getInstance());
        MainFrame.FRAME.setVisible(true);
        Sound.initClips();
        image.ImageIO.initAllSprites();
        reset();
    }

    public static void reset() {
        IO.println("Reset trigerred", IO.MessageType.DEBUG);
        //graphic.GameGrid.reset();
        MainFrame.FRAME.setMainPanel(new graphic.MenuPanel());
    }

    public static void writeAllFiles() {
        utils.IO.initLogs();
        InputConfig.saveConfig();
        Text.createAllLanguageFiles();
        utils.Settings.saveSettings();
        java.io.File dir = new java.io.File(utils.Constants.IMAGE_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdir();
            IO.println("Created directory " + dir, IO.MessageType.DEBUG);
        }
        dir = new java.io.File(utils.Constants.SOUND_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdir();
            IO.println("Created directory " + dir, IO.MessageType.DEBUG);
        }
        dir = new java.io.File(utils.Constants.SAVES_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdir();
            IO.println("Created directory " + dir, IO.MessageType.DEBUG);
        }
    }

    /**
     * Post run actions. Executed just before logs are closed, settings are
     * saved and applications shutdown.
     */
    public static void postRun() {
        IO.println("Produced Ships in this match: " + Factory.getProducedShips(),
                IO.MessageType.NORMAL);
        IO.println("Produced Weapons in this match: " + Factory.getProducedWeapons(),
                IO.MessageType.NORMAL);
    }
}
