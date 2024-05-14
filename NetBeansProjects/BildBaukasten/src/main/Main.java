package main;

import gui.MainFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class Main
{

    public static final String OUTPUT_FILENAME = "ImageOutput.txt";
    public static final String INPUT_FILENAME = "images/";
    public static final File SETTINGS_FILE = new File("settings.dat");

    public static void main(String[] args)
    {
        if (!Settings.load(SETTINGS_FILE))
        {
            Settings.initSettings();
        }
        MainFrame.setNimbusLookAndFeel();
        MainFrame frame = new MainFrame();
        if (args.length > 0)
        {
            File f = new File(args[0]);
            if (f.exists())
            {
                frame.open(f);
            }
        }
        frame.setLocation(300, 200);
        frame.setTitle("BilderBaukasten");
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent we)
            {
                super.windowClosing(we); //To change body of generated methods, choose Tools | Templates.
                Settings.save(SETTINGS_FILE);
            }

        });
        frame.setVisible(true);
    }
}
