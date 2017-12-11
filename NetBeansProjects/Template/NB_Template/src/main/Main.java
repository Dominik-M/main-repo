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

import template.graphic.InputConfig;
import template.graphic.MainFrame;
import template.graphic.ProgressPanel;
import template.sound.Sound;
import template.utils.IO;
import template.utils.Text;

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
        MainFrame.setNimbusLookAndFeel();
        MainFrame.FRAME.setMainPanel(ProgressPanel.getInstance());
        MainFrame.FRAME.setVisible(true);
        Sound.initClips();
        MainFrame.FRAME.setMainPanel(new template.graphic.MainPanel());

        // TODO Enter your application logic here
        template.utils.IO.println(Text.HELLO.toString(), template.utils.IO.MessageType.IMPORTANT);
    }

    public static void reset() {
        IO.println("Reset trigerred", IO.MessageType.DEBUG);
        // TODO reset application
    }

    public static void writeAllFiles() {
        template.utils.IO.initLogs();
        InputConfig.saveConfig();
        Text.createAllLanguageFiles();
        template.utils.Settings.saveSettings();
        java.io.File dir = new java.io.File(template.utils.Constants.IMAGE_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdir();
        }
        dir = template.sound.Sound.SOUNDFILE;
        if (!dir.exists()) {
            dir.mkdir();
        }
    }
}
