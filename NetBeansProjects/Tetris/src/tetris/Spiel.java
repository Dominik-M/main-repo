package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.JComponent;
import javax.swing.Timer;

public class Spiel extends JComponent implements ActionListener, KeyListener{
  public static final int SPOT=25; //Seitenlänge eines Spots in Pixeln
  protected Timer timer;
  protected Feld feld;
  private boolean soundOn=true,musikAn=true;
  private Clip music;
    
  public Spiel(Feld feld){ 
    this.feld=feld;
    if(!feld.neuerStein())
    throw new IllegalArgumentException("Der erste Stein hat nicht Platz.");
    timer=new Timer(300,this);
    timer.setDelay(500);
    setPreferredSize(new Dimension(feld.breite()*SPOT, feld.hoehe()*SPOT));
    setLayout(null);
    addKeyListener(this);
    setFocusable(true);
    requestFocusInWindow();
    music=initialClip("Sound/Tetris_Theme_A.wav");
    playSound(music,100);
  }

    @Override
  public void paintComponent(Graphics g){
    Stein akt=feld.getStein();
    // Hintergrund
    g.setColor(Color.BLACK);
    g.fillRect(0,0,feld.breite()*SPOT,feld.hoehe()*SPOT);
    //Zeichne Belegte Spots
    g.setColor(akt.getColor().darker());
    for(int i=0;i<feld.breite();i++){
      for(int j=0;j<feld.hoehe();j++){
        if(feld.belegt(i,j)){g.fillRect(i*SPOT,j*SPOT,SPOT,SPOT);}
      }
    }
    //Zeichne aktiven Stein
    g.setColor(akt.getColor().brighter());
    for(Spot s: akt.spots()){
      g.fillRect(s.x*SPOT,s.y*SPOT,SPOT,SPOT);
    }
    //Zeichne Gitter
    g.setColor(Color.GREEN);
    for(int i=0;i<feld.breite();i++){
      g.drawLine(i*SPOT,0,i*SPOT,feld.hoehe()*SPOT);
    }
    for(int i=0;i<feld.hoehe();i++){
      g.drawLine(0,i*SPOT,feld.breite()*SPOT,i*SPOT);
    }
  }

    @Override
  public void actionPerformed(ActionEvent e) {
    removeKeyListener(this);
    if(!feld.setStein(feld.getStein().transformiert(0,0,1)))feld.fixiere();
    addKeyListener(this);
    repaint();
  }

    @Override
  public void keyTyped(KeyEvent e) {  }

    @Override
  public void keyPressed(KeyEvent ke){
    switch(ke.getKeyCode()){
      case KeyEvent.VK_LEFT: //eine Position nach links
        feld.setStein(feld.getStein().transformiert(0,-1,0));  
        break;
      case KeyEvent.VK_RIGHT://eine Position nach rechts
        feld.setStein(feld.getStein().transformiert(0,1,0));
        break;
      case KeyEvent.VK_UP: //gegen den Uhrzeigersinn drehen
        feld.setStein(feld.getStein().transformiert(1,0,0));
        break;
      case KeyEvent.VK_DOWN://fallen lassen
        feld.setStein(feld.getStein().transformiert(0,0,1));
        break;
      case KeyEvent.VK_SPACE://Pause ein- bzw. ausschalten
        if(timer.isRunning()){
          timer.stop();
          music.stop();
        }else{
          timer.start();
          if(musikAn)music.start();
        }
        break;
      case KeyEvent.VK_W: //Cheat: Stein bewegt sich nach oben
        feld.setStein(feld.getStein().transformiert(0,0,-1));
        System.out.println("Du hast geschummelt");
        break;
      case KeyEvent.VK_A: //Cheat: alle Zeilen werden gelöscht
        System.out.println("Du hast geschummelt");
        for(int i=0;i<feld.belegt.length;i++)feld.belegt[i]=false;
        break;
      case KeyEvent.VK_S: //Cheat: Neuer Stein
        feld.neuerStein();
        System.out.println("Du hast geschummelt");
        break;
      case KeyEvent.VK_T: //Timer beschleunigen
       if(timer.getDelay()>10) timer.setDelay(timer.getDelay()-10);
        break;
      case KeyEvent.VK_ENTER://neues Spiel
        feld.reset();
        timer.stop();
        break; 
    }
    repaint();
  }
    
    public Clip initialClip(String filename) {
        try{
            AudioInputStream aIS = AudioSystem.getAudioInputStream(new java.io.File(filename));
            AudioFormat af     = aIS.getFormat();
            int size      = (int) (af.getFrameSize() * aIS.getFrameLength());
            byte[] audio       = new byte[size];
            DataLine.Info info      = new DataLine.Info(Clip.class, af, size);
            aIS.read(audio, 0, size);
                Clip sound = (Clip) AudioSystem.getLine(info);
                sound.open(af, audio, 0, size);
                return sound;
        }catch(UnsupportedAudioFileException | IOException | LineUnavailableException e){ 
            javax.swing.JOptionPane.showMessageDialog(this, e,"Sound fail:"+filename,javax.swing.JOptionPane.ERROR_MESSAGE);
        }   
        return null;
    }
    
    public void playSound(Clip sound,int loops){
      if(sound!=null&&soundOn){
          if(loops==1){
              sound.setFramePosition(0);
              sound.start();
          }
          else sound.loop(loops);
      }
    }
    
    public void setSound(boolean an){
        soundOn=an;
    }
    
    public void setMusik(boolean an){
        musikAn=an;
        if(an){
            if(!music.isRunning()) {
                music.start();
            }
        }
        else if(music.isRunning())music.stop();
    }
    
    public boolean soundAn(){
        return soundOn && music.isRunning();
    }

    @Override
  public void keyReleased(KeyEvent e) {    }

}