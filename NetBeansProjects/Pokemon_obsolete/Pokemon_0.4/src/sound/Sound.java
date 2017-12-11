/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sound;

import graphic.ProgressPanel;
import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import spiel.IO;

/**
 *
 * @author Dominik
 */
public class Sound {

    public static final File SOUNDFILE = new File("sound/");
    private static final LinkedList<String> soundfiles = new LinkedList<>();
    private static final LinkedList<Clip> clips = new LinkedList<>();
    private static Clip currentClip;
    private static boolean soundOn = true;
    private static final LinkedList<LineListener> listener = new LinkedList<>();

    public static String getSoundFileName(int i) {
        return soundfiles.get(i);
    }

    public static int getSoundfilesLength() {
        return soundfiles.size();
    }

    /**
     * Adds a Soundclip to the cliplist.
     *
     * @param file the Soundfile to add
     * @return index of the added Soundclip in the list or -1 if there was an
     * error.
     */
    public static int addSoundFile(File file) {
        Clip c = initClip(file);
        if (c != null) {
            soundfiles.add(file.getName());
            clips.add(c);
            return clips.size() - 1;
        }
        return -1;
    }

    public static void addLineListener(LineListener ll) {
        listener.add(ll);
    }

    public static boolean removeLineListener(LineListener ll) {
        return listener.remove(ll);
    }

    public static boolean isPlaying() {
        return (currentClip != null) && (currentClip.isRunning());
    }

    public static long getMicrosecondPosition() {
        if (currentClip != null) {
            return currentClip.getMicrosecondPosition();
        }
        return 0;
    }

    public static void setMicrosecondPosition(long mys) {
        if (currentClip != null) {
            currentClip.setMicrosecondPosition(mys);
        }
    }

    public static long getMicrosecondLength() {
        if (currentClip != null) {
            return currentClip.getMicrosecondLength();
        } else {
            return 1;
        }
    }

    public static boolean isSoundOn() {
        return soundOn;
    }

    public static void setSoundOn(boolean on) {
        soundOn = on;
        if (currentClip != null) {
            if (!soundOn) {
                stopSound();
            } else {
                startSound();
            }
        }
    }

    public static boolean startSound() {
        if (currentClip != null && soundOn) {
            currentClip.start();
        } else {
            return false;
        }
        return true;
    }

    public static void stopSound() {
        if (currentClip != null) {
            currentClip.stop();
        }
    }

    public static boolean playSoundClip(String name, int loops) {
        for (int i = 0; i < soundfiles.size(); i++) {
            if (soundfiles.get(i).equals(name)) {
                return playSoundClip(i, loops);
            }
        }
        return false;
    }

    public static boolean playSoundClip(int index) {
        return playSoundClip(index, 1);
    }

    public static boolean playSoundClip(int index, int loops) {
        try {
            Clip soundclip = clips.get(index);
            if (currentClip != null) {
                currentClip.stop();
            }
            currentClip = soundclip;
            soundclip.stop();
            soundclip.setFramePosition(0);
            if (soundOn) {
                if (loops > 1) {
                    soundclip.loop(loops);
                } else if (loops < 0) {
                    soundclip.loop(Clip.LOOP_CONTINUOUSLY);
                } else {
                    soundclip.start();
                }
            }
        } catch (Exception e) {
            IO.IOMANAGER.println("Sound Fehler: " + e.toString(), IO.MessageType.ERROR);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void initClips() {
        ProgressPanel.setProgress(0);
        ProgressPanel.setText("Searching Soundfiles...");
        if (!SOUNDFILE.exists() || !SOUNDFILE.isDirectory()) {
            IO.IOMANAGER.println("Sound directory not found.", IO.MessageType.ERROR);
            return;
        }
        File[] files = SOUNDFILE.listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {
                String name = file.getName();
                int index = name.lastIndexOf(".");
                if (index > 0) {
                    return name.substring(index).matches(".mid|.wav");
                }
                return false;
            }
        });
        int totallength = 0;
        int length = 0;
        for (File f : files) {
            IO.IOMANAGER.println(f.toString(), IO.MessageType.DEBUG);
            totallength += f.length();
        }
        ProgressPanel.setText("Loading Sounds...");
        for (int i = 0; i < files.length; i++) {
            if (Sound.addSoundFile(files[i]) < 0) {
                IO.IOMANAGER.println("Error while loading " + files[i], IO.MessageType.ERROR);
            }
            length += files[i].length();
            ProgressPanel.setProgress((int) (length * 100 / totallength));
        }
    }

    public static Clip initClip(File file) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat af = audioInputStream.getFormat();
            int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
            byte[] audio = new byte[size];
            DataLine.Info info = new DataLine.Info(Clip.class, af, size);
            audioInputStream.read(audio, 0, size);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(af, audio, 0, size);
            clip.addLineListener(new LineListener() {

                @Override
                public void update(LineEvent event) {
                    for (LineListener l : listener) {
                        l.update(event);
                    }
                }
            });
            return clip;
        } catch (Exception e) {
            IO.IOMANAGER.println("Sound Fehler: " + e.toString(), IO.MessageType.ERROR);
            //e.printStackTrace();
        }
        return null;
    }
}
