/*
 * Copyright (C) 2015 Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package sounds;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Sound {
    public static final Clip EXPLOS_SOUND=initClip("explos.wav",Sound.class);
    
    public static void playSoundClip(Clip soundclip) {
        try{
            soundclip.stop();
            soundclip.setFramePosition(0);
            soundclip.start();
        }catch(Exception e){ System.out.println("Sound Fehler:#"+e.toString()); }   
    }
    
    public static Clip initClip(String filename,Class c){
        try{
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream((c.getResourceAsStream("/sounds/"+filename)));
            AudioFormat af     = audioInputStream.getFormat();
            int size      = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
            byte[] audio       = new byte[size];
            DataLine.Info info      = new DataLine.Info(Clip.class, af, size);
            audioInputStream.read(audio, 0, size);
                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(af, audio, 0, size);
                return clip;
        }catch(Exception e){ System.out.println("Sound Fehler:#"+e.toString()); }
        return null;
    }
}
