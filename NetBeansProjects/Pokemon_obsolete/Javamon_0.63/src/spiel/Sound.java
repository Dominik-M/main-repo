package spiel;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Sound {
  private static Clip sound;
   
      public static void playSoundClip(String filename) {
        if(!Javamon.SoundOn())return;
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new java.io.File("sound/"+filename));
            AudioFormat af     = audioInputStream.getFormat();
            int size      = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
            byte[] audio       = new byte[size];
            DataLine.Info info      = new DataLine.Info(Clip.class, af, size);
            audioInputStream.read(audio, 0, size);
                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(af, audio, 0, size);
                clip.start();
        }catch(Exception e){ System.out.println("Sound Fehler:#"+e.toString()); }   
    }
  
    public static void playSound(String filename) {
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new java.io.File("sound/"+filename));
            AudioFormat af     = audioInputStream.getFormat();
            int size      = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
            byte[] audio       = new byte[size];
            DataLine.Info info      = new DataLine.Info(Clip.class, af, size);
            audioInputStream.read(audio, 0, size);
            if(sound!=null && sound.isRunning())
                sound.close();
            sound = (Clip) AudioSystem.getLine(info);
            sound.open(af, audio, 0, size);
            playSound();
        }catch(Exception e){
            System.out.println("Sound Fehler:#"+e.toString());
        }   
    }
    
    public static void playSound(){
      if(sound!=null&&Javamon.SoundOn())sound.loop(100);
    }
    
    public static void stopSound(){
      if(sound!=null)sound.stop();
    }
}