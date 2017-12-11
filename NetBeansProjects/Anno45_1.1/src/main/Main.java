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

import client.ClientFrame;
import graphic.InputConfig;
import graphic.MainFrame;
import graphic.ProgressPanel;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import sound.Sound;
import utils.IO;
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
        MainFrame.setNimbusLookAndFeel();
        MainFrame.FRAME.setMainPanel(ProgressPanel.getInstance());
        MainFrame.FRAME.setVisible(true);
        Sound.initClips();
        MainFrame.FRAME.setMainPanel(new graphic.MainPanel());

        // TODO Enter your application logic here
        utils.IO.println(Text.HELLO.toString(), utils.IO.MessageType.IMPORTANT);

        final ClientFrame frame = new ClientFrame();
        frame.setVisible(true);
        System.setOut(new PrintStream(new OutputStream() {

            @Override
            public void write(int b) throws IOException {
                frame.output.append((char) b + "");
            }
        }));
    }

    public static void reset() {
        IO.println("Reset trigerred", IO.MessageType.DEBUG);
        // TODO reset application
    }

    public static void writeAllFiles() {
        utils.IO.initLogs();
        InputConfig.saveConfig();
        Text.createAllLanguageFiles();
        utils.Settings.saveSettings();
        java.io.File dir = new java.io.File(utils.Constants.IMAGE_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdir();
        }
        dir = sound.Sound.SOUNDFILE;
        if (!dir.exists()) {
            dir.mkdir();
        }
    }
}
