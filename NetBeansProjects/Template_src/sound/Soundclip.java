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

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * Merged class of javax.sound.sampled.Clip and javafx.scene.media.Media to
 * access both supported formats mp3 as well as wav and midi.
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 21.03.2016
 */
public class Soundclip {

    private static boolean toolkitInitialized = false;
    private final File sourcefile;
    private Clip clip;

    public Soundclip(File file) throws Exception {
        sourcefile = file;
        if (sourcefile.getName().endsWith(".mp3")) {
            if (!toolkitInitialized) {
                // TODO initialize javafx toolkit
                toolkitInitialized = true;
            }
            throw new java.lang.IllegalArgumentException("cannot read mp3 files");
        } else {
            initClip();
        }
        addLineListener();
    }

    private void initClip() throws Exception {

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sourcefile);
        AudioFormat af = audioInputStream.getFormat();
        int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
        byte[] audio = new byte[size];
        DataLine.Info info = new DataLine.Info(Clip.class, af, size);
        audioInputStream.read(audio, 0, size);
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(af, audio, 0, size);
    }

    public boolean isRunning() {
        if (clip != null) {
            return clip.isRunning();
        }
        return false;
    }

    public long getMillisecondPosition() {
        if (clip != null) {
            return clip.getMicrosecondPosition() / 1000;
        }
        return 0;
    }

    public void setMicrosecondPosition(long mys) {
        if (clip != null) {
            clip.setMicrosecondPosition(mys);
        }
    }

    public long getMicrosecondLength() {
        if (clip != null) {
            return clip.getMicrosecondLength();
        }
        return 0;
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
        }
    }

    public void pause() {
        if (clip != null) {
            clip.stop();
        }
    }

    public void loop(int loops) {
        if (clip != null) {
            clip.loop(loops);
        }
    }

    public void start() {
        if (clip != null) {
            clip.start();
        }
    }

    public void addLineListener() {
        if (clip != null) {
            clip.addLineListener(Sound.sampledLineListener);
        }
    }
}
