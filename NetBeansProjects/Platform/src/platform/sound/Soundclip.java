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
package platform.sound;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * Merged class of javax.sound.sampled.Clip and javafx.scene.media.Media to
 * access both supported formats mp3 as well as wav and midi. Note: If you want
 * to use .mp3 files your Main Class has to extend
 * javafx.application.Application and call Application.launch() to init the
 * toolkit.
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 21.03.2016
 */
public class Soundclip
{

    private final File sourcefile;
    private Clip clip;
    private Media media;
    private MediaPlayer mediaplayer;

    public Soundclip(File file) throws Exception
    {
        sourcefile = file;
        if (sourcefile.getName().endsWith(".mp3"))
        {
            initMedia();
            clip = null;
        }
        else
        {
            initClip();
            media = null;
        }
        addLineListener();
    }

    private void initClip() throws Exception
    {

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sourcefile);
        AudioFormat af = audioInputStream.getFormat();
        int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
        byte[] audio = new byte[size];
        DataLine.Info info = new DataLine.Info(Clip.class, af, size);
        audioInputStream.read(audio, 0, size);
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(af, audio, 0, size);
    }

    private void initMedia()
    {
        media = new Media(sourcefile.toURI().toASCIIString());
        mediaplayer = new MediaPlayer(media);
        // while (mediaplayer.getStatus() != MediaPlayer.Status.READY);
    }

    public boolean isRunning()
    {
        if (clip != null)
        {
            return clip.isRunning();
        }
        else if (mediaplayer != null)
        {
            return mediaplayer.getStatus() == MediaPlayer.Status.PLAYING;
        }
        return false;
    }

    public long getMillisecondPosition()
    {
        if (clip != null)
        {
            return clip.getMicrosecondPosition() / 1000;
        }
        else if (mediaplayer != null)
        {
            return (long) (mediaplayer.getCurrentTime().toMillis());
        }
        return 0;
    }

    public void setMicrosecondPosition(long mys)
    {
        if (clip != null)
        {
            clip.setMicrosecondPosition(mys);
        }
        else if (mediaplayer != null)
        {
            mediaplayer.seek(mediaplayer.getStopTime().multiply(
                    1.0 * mys / this.getMicrosecondLength()));
        }
    }

    public long getMicrosecondLength()
    {
        if (clip != null)
        {
            return clip.getMicrosecondLength();
        }
        else if (mediaplayer != null)
        {
            return (long) (mediaplayer.getStopTime().toMillis() * 1000);
        }
        return 0;
    }

    public void stop()
    {
        if (clip != null)
        {
            clip.stop();
            clip.setFramePosition(0);
        }
        else if (mediaplayer != null)
        {
            mediaplayer.seek(mediaplayer.getStartTime());
            mediaplayer.stop();
        }
    }

    public void pause()
    {
        if (clip != null)
        {
            clip.stop();
        }
        else if (mediaplayer != null)
        {
            mediaplayer.pause();
        }
    }

    public void loop(int loops)
    {
        if (clip != null)
        {
            clip.loop(loops);
        }
        else if (mediaplayer != null)
        {
            mediaplayer.play();
        }
    }

    public void start()
    {
        if (clip != null)
        {
            clip.start();
        }
        else if (mediaplayer != null)
        {
            mediaplayer.play();
        }
    }

    public void addLineListener()
    {
        if (clip != null)
        {
            clip.addLineListener(Sound.sampledLineListener);
        }
        else if (mediaplayer != null)
        {
            Runnable lineEventStartInvoker = new Runnable()
            {

                @Override
                public void run()
                {
                    Sound.lineListener.update(LineListener.LineEvent.START);
                }
            };
            Runnable lineEventStopInvoker = new Runnable()
            {

                @Override
                public void run()
                {
                    Sound.lineListener.update(LineListener.LineEvent.STOP);
                }
            };
            mediaplayer.setOnPlaying(lineEventStartInvoker);
            mediaplayer.setOnPaused(lineEventStopInvoker);
            mediaplayer.setOnStopped(lineEventStopInvoker);
            mediaplayer.setOnStalled(lineEventStopInvoker);
            mediaplayer.setOnEndOfMedia(lineEventStopInvoker);
        }
    }
}
