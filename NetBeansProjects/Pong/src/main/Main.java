package main;

import graphic.PongPanel;
import graphic.SimControlPanel;
import java.awt.BorderLayout;
import template.graphic.InputConfig;
import template.graphic.MainFrame;
import template.utils.IO;
import template.utils.Text;

public class Main {

    private static PongPanel p;

    /**
     * @param args
     */
    public static void main(String[] args) {
        writeAllFiles();
        MainFrame.setNimbusLookAndFeel();
        p = new graphic.PongPanel(new Pong());
        MainFrame.FRAME.getContentPane().add(new SimControlPanel(p), BorderLayout.SOUTH);
        MainFrame.FRAME.setMainPanel(p);
        MainFrame.FRAME.setVisible(true);
        p.setRunning(false);
    }

    public static void reset() {
        IO.println("Reset trigerred", IO.MessageType.DEBUG);
        p.setRunning(false);
        p.PONG.init();
    }

    public static void writeAllFiles() {
        template.utils.IO.initLogs();
        java.io.File dir = new java.io.File(template.utils.Constants.IMAGE_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdir();
        }
        dir = new java.io.File(template.utils.Constants.DATA_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdir();
        }
        dir = template.sound.Sound.SOUNDFILE;
        if (!dir.exists()) {
            dir.mkdir();
        }
        InputConfig.saveConfig();
        Text.createAllLanguageFiles();
        template.utils.Settings.saveSettings();
    }
}
