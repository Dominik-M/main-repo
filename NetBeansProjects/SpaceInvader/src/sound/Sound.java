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
package sound;

import graphic.ProgressPanel;
import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import javax.sound.sampled.Clip;
import sound.LineListener.LineEvent;
import utils.IO;
import utils.Settings;
import utils.Text;

/**
 * Created 10.03.2016
 *
 * @author Dominik Messerschmidt
 * (dominik.messerschmidt@continental-corporation.com)
 *
 */
public class Sound {

    public static final File SOUNDFILE = new File(utils.Constants.SOUND_DIRECTORY);
    private static final utils.Dictionary<File, Soundclip> soundclips = new utils.Dictionary<>();
    private static Soundclip currentClip;
    private static final LinkedList<LineListener> listener = new LinkedList<>();
    static final LineListener lineListener = new LineListener() {

        @Override
        public void update(LineEvent event) {
            for (LineListener l : listener) {
                l.update(event);
            }
        }
    };
    static final javax.sound.sampled.LineListener sampledLineListener = new javax.sound.sampled.LineListener() {

        @Override
        public void update(javax.sound.sampled.LineEvent event) {
            if (event.getType() == javax.sound.sampled.LineEvent.Type.START) {
                for (LineListener l : listener) {
                    l.update(LineEvent.START);
                }
            } else if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
                for (LineListener l : listener) {
                    l.update(LineEvent.STOP);
                }
            }
        }
    };

    private Sound() {
    }

    public static LinkedList<File> getSoundFiles() {
        return soundclips.getKeys();
    }

    public static boolean containsSoundfile(File f) {
        return soundclips.containsKey(f);
    }

    /**
     * Initializes a Soundclip and adds it to the cliplist.
     *
     * @param file the Soundfile to add
     * @return true if the soundfile was added to the list or has already been
     * contained.
     */
    public static boolean addSoundFile(File file) {
        if (soundclips.containsKey(file)) {
            return true;
        }
        Soundclip c = initClip(file);
        if (c != null) {
            if (soundclips.add(file, c)) {
                IO.println("initialized soundfile: " + file.getName(), IO.MessageType.DEBUG);
                return true;
            }
        }
        return false;
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

    public static long getMillisecondPosition() {
        if (currentClip != null) {
            return currentClip.getMillisecondPosition();
        }
        return 0;
    }

    public static void setMicrosecondPosition(long mys) {
        if (currentClip != null) {
            currentClip.setMicrosecondPosition(mys);
            IO.println("sound microsecond position set to " + mys, IO.MessageType.DEBUG);
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
        return Settings.getSettings().soundOn;
    }

    public static void setSoundOn(boolean on) {
        Settings.getSettings().soundOn = on;
        if (currentClip != null) {
            if (!isSoundOn()) {
                pause();
            } else {
                play();
            }
        }
    }

    public static boolean play() {
        if (currentClip != null && isSoundOn()) {
            currentClip.start();
        } else {
            return false;
        }
        return true;
    }

    public static void pause() {
        if (currentClip != null) {
            currentClip.pause();
        }
    }

    public static void stop() {
        if (currentClip != null) {
            currentClip.stop();
        }
    }

    /**
     * Plays the soundclip with the specified name.
     *
     * @param file the soundfile.
     * @param loops number of loops. If less than 0 it loops continuously.
     * @return true if the soundfile was loaded succesfully, false otherwise.
     */
    public static boolean playSoundClip(File file, int loops) {
        try {
            Soundclip soundclip = soundclips.get(file);
            if (currentClip != null) {
                currentClip.stop();
            }
            currentClip = soundclip;
            soundclip.stop();
            if (isSoundOn()) {
                if (loops > 1) {
                    soundclip.loop(loops);
                } else if (loops < 0) {
                    soundclip.loop(Clip.LOOP_CONTINUOUSLY);
                } else {
                    soundclip.start();
                }
                IO.println("Now playing sounfile: " + file.getName(), IO.MessageType.DEBUG);
            }
        } catch (Exception ex) {
            IO.println("Sound error: " + ex.toString(), IO.MessageType.ERROR);
            IO.printException(ex);
            return false;
        }
        return true;
    }

    /**
     * Initializes all compatible soundfiles in the specified sound directory.
     * Supported soundfile formats are .wav and .mid. Announces the
     * ProgressPanel to display the progress.
     */
    public static File[] initClips() {
        ProgressPanel.setProgress(0);
        ProgressPanel.setText(Text.SEARCHING_SOUNDFILES.toString());
        File[] files = listSoundFilesInDirectory(SOUNDFILE);
        return initClips(files);
    }

    public static File[] initClips(File[] files) {
        long totallength = 0;
        long length = 0;
        java.util.LinkedList<File> added = new java.util.LinkedList<File>();
        IO.println("found soundfiles:", IO.MessageType.DEBUG);
        for (File f : files) {
            IO.println(f.toString(), IO.MessageType.DEBUG);
            totallength += f.length();
        }
        ProgressPanel.setText(Text.LOADING_SOUNDFILES.toString());
        ProgressPanel.setProgress(0);
        for (int i = 0; i < files.length; i++) {
            if (Sound.addSoundFile(files[i])) {
                added.add(files[i]);
            } else {
                IO.println("Error while loading " + files[i], IO.MessageType.ERROR);
            }
            length += files[i].length();
            ProgressPanel.setProgress((int) (length * 100 / totallength));
        }
        File[] addedFiles = new File[added.size()];
        for (int i = 0; i < addedFiles.length; i++) {
            addedFiles[i] = added.get(i);
        }
        ProgressPanel.setText(Text.READY.toString());
        ProgressPanel.setProgress(100);
        return addedFiles;
    }

    public static File[] listSoundFilesInDirectory(File directory) {
        if (!directory.exists() || !directory.isDirectory()) {
            IO.println("Sound directory not found.", IO.MessageType.ERROR);
            return null;
        }
        File[] files = directory.listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {
                String name = file.getName();
                int index = name.lastIndexOf(".");
                if (index > 0) {
                    return name.substring(index).matches(".mid|.wav|.mp3");
                }
                return false;
            }
        });
        return files;
    }

    /**
     * Initialize a given soundfile. Reads data from the file and stores it in a
     * buffer.
     *
     * @param file a soundfile with a supported format. Supported soundfile
     * formats are .wav and .mid.
     * @return The clip containing sound data or null if there was an error.
     */
    public static Soundclip initClip(File file) {
        try {
            Soundclip clip = new Soundclip(file);
            return clip;
        } catch (Exception ex) {
            IO.println("Sound error: " + ex.toString(), IO.MessageType.ERROR);
            IO.printException(ex);
        }
        return null;
    }
}
