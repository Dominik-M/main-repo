/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Dominik Messerschmidt
 */
public class Settings implements Serializable
{

    private static Settings instance;

    private final LinkedList<SettingsListener> LISTENER = new LinkedList<>();

    private int colorSelectThreshold;
    private String defaultDirectory, logoPath;
    private int defaultLogoPositionX, defaultLogoPositionY, logoRotation, logoScale;

    private Settings()
    {
        System.out.println("Creating new instance of Settings");
    }

    public static final Settings getInstance()
    {
        if (instance == null)
        {
            synchronized (Settings.class)
            {
                if (instance == null)
                {
                    instance = new Settings();
                }
            }
        }
        return instance;
    }

    public void addSettingsListener(SettingsListener l)
    {
        LISTENER.add(l);
    }

    public boolean removeSettingsListener(SettingsListener l)
    {
        return LISTENER.remove(l);
    }

    private void notifyListeners()
    {
        for (SettingsListener l : LISTENER)
        {
            l.onSettingChanged();
        }
    }

    public int getColorSelectThreshold()
    {
        return colorSelectThreshold;
    }

    public void setColorSelectThreshold(int colorSelectThreshold)
    {
        if (this.colorSelectThreshold != colorSelectThreshold)
        {
            this.colorSelectThreshold = colorSelectThreshold;
            System.out.println("Color select threshold changed to " + this.colorSelectThreshold);
            notifyListeners();
        }
    }

    public String getDefaultDirectory()
    {
        return defaultDirectory;
    }

    public void setDefaultDirectory(String defaultDirectory)
    {
        this.defaultDirectory = defaultDirectory;
        System.out.println("Default directory changed to " + this.defaultDirectory);
        notifyListeners();
    }

    public String getLogoPath()
    {
        return logoPath;
    }

    public void setLogoPath(String logoPath)
    {
        this.logoPath = logoPath;
        System.out.println("Logo path changed to " + this.logoPath);
        notifyListeners();
    }

    public int getDefaultLogoPositionX()
    {
        return defaultLogoPositionX;
    }

    public void setDefaultLogoPositionX(int defaultLogoPositionX)
    {
        if (this.defaultLogoPositionX != defaultLogoPositionX)
        {
            this.defaultLogoPositionX = defaultLogoPositionX;
            System.out.println("Default logo position changed to "
                    + "X: " + this.defaultLogoPositionX
                    + " Y: " + this.defaultLogoPositionY);
            notifyListeners();
        }
    }

    public int getDefaultLogoPositionY()
    {
        return defaultLogoPositionY;
    }

    public void setDefaultLogoPositionY(int defaultLogoPositionY)
    {
        if (this.defaultLogoPositionY != defaultLogoPositionY)
        {
            this.defaultLogoPositionY = defaultLogoPositionY;
            System.out.println("Default logo position changed to "
                    + "X: " + this.defaultLogoPositionX
                    + " Y: " + this.defaultLogoPositionY);
            notifyListeners();
        }
    }

    public int getLogoRotation()
    {
        return logoRotation;
    }

    public void setLogoRotation(int logoRotation)
    {
        if (this.logoRotation != logoRotation)
        {
            this.logoRotation = logoRotation;
            System.out.println("Logo rotation changed to " + logoRotation);
            notifyListeners();
        }
    }

    public int getLogoScale()
    {
        return logoScale;
    }

    public void setLogoScale(int logoScale)
    {
        if (this.logoScale != logoScale)
        {
            this.logoScale = logoScale;
            System.out.println("Logo scale changed to " + logoScale);
            notifyListeners();
        }
    }

    public static void initSettings()
    {
        getInstance().setColorSelectThreshold(32);
        getInstance().setDefaultDirectory("D:\\Bilder");
        getInstance().setLogoPath("D:\\Bilder\\Phuong\\Logo_edit.png");
    }

    public static boolean load(File file)
    {
        synchronized (Settings.class)
        {
            try
            {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Settings s = (Settings) ois.readObject();
                fis.close();
                ois.close();
                System.out.println("Loaded Settings from file: " + file);
                instance = s;
                return true;
            }
            catch (Exception ex)
            {
                System.err.println("failed to load " + file);
            }
        }
        return false;
    }

    public static boolean save(File file)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(instance);
            fos.close();
            oos.close();
            System.out.println("Settings saved in " + file);
            return true;
        }
        catch (Exception ex)
        {
            System.err.println("failed to save " + file);
            return false;
        }
    }
}
