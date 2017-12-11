package tetris;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.Clip;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class Tetris extends JFrame implements FeldListener,ActionListener{
  private int score;
  private JLabel scorelabel;
  private Spiel spiel;
  private Clip fixiert,voll;
  private JMenuBar menuBar=new JMenuBar();
  private JMenu option=new JMenu("Optionen");
  private JCheckBoxMenuItem soundAn=new JCheckBoxMenuItem("Sound",true);
  private JCheckBoxMenuItem musikAn=new JCheckBoxMenuItem("Musik",true);
 
  public Tetris(int width,int height){
    super("Tetris");
    Feld feld=new Feld(width, height);
    spiel=new Spiel(feld);
    getContentPane().add(spiel, BorderLayout.CENTER);
    scorelabel=new JLabel("SCORE: 0");
    getContentPane().add(scorelabel, BorderLayout.SOUTH);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    fixiert=spiel.initialClip("Sound/pluck.wav");
    voll=spiel.initialClip("Sound/explos.wav");
    soundAn.addActionListener(this);
    musikAn.addActionListener(this);
    option.add(musikAn);
    option.add(soundAn);
    menuBar.add(option);
    this.setJMenuBar(menuBar);
    pack();
    setVisible(true);
    feld.addFeldListener(this);
  }

    @Override
  public void volleZeilen(int abraum){
    score+=abraum;
    scorelabel.setText("SCORE: "+score);
    spiel.playSound(voll,1);
    if(spiel.timer.getDelay()>40) spiel.timer.setDelay(spiel.timer.getDelay()-10);
  }

    @Override
  public void reset(int breit, int hoch){
    score=0;
    scorelabel.setText("SCORE: 0");
  }
    
    @Override
  public String toString(){
    return "Tetris ("+spiel.feld.breite()+"x"+spiel.feld.hoehe()+") "+scorelabel.getText();    
  }

   public static void main(String[] args) {
     new Tetris(12,25);
   }

    @Override
    public void steinFixiert() {
        spiel.playSound(fixiert,1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(soundAn)){
            spiel.setSound(soundAn.getState());
        }else if(e.getSource().equals(musikAn)){
            spiel.setMusik(musikAn.getState());
        }
    }
}