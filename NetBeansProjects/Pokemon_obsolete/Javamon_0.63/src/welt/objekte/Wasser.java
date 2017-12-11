package welt.objekte;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import spiel.Javamon;
import spiel.OutputListener;
import spiel.Spielwelt;

public class Wasser extends Objekt implements OutputListener,ActionListener{
    private static Timer t=new Timer(200,new Wasser());
    
    public Wasser(){
      super(false);
      setName("Wasser");
    }

    @Override
    public void benutzt() {
      if(!t.isRunning()){
        Javamon.sout(Javamon.getSpieler()+" wirft die Angel aus.");
        t.start();
      }
    }

    @Override
    public void betreten() {}

    @Override
    public void paintComponent(Graphics g, int spot) {
      g.setColor(FarbPalette.WASSERBLAU);
      g.fillRect(0,0, spot, spot);
      int b=Spielwelt.SPOT;
      int[] weisse={4,5,12,13,3+b,4+b,11+b,12+b,3+2*b,4+2*b,11+2*b,12+2*b,
      4+3*b,5+3*b,12+3*b,13+3*b,5+4*b,6+4*b,7+4*b,13+4*b,14+4*b,15+4*b,5*b,
      1+5*b,8+5*b,9+5*b,2+6*b,3+6*b,10+6*b,11+6*b,3+7*b,4+7*b,11+7*b,12+7*b,
      4+8*b,5+8*b,12+8*b,13+8*b,3+9*b,4+9*b,11+9*b,12+9*b,3+10*b,4+10*b,
      11+10*b,12+10*b,4+11*b,5+11*b,12+11*b,13+11*b,5+12*b,6+12*b,7+12*b,
      13+12*b,14+12*b,15+12*b,13*b,1+13*b,8+13*b,9+13*b,2+14*b,3+14*b,10+14*b,
      11+14*b,3+15*b,4+15*b,11+15*b,12+15*b};
      g.setColor(java.awt.Color.WHITE);
      for(int i:weisse)g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
    }

    @Override
    public void ausgabeFertig() {
      int lvl=15+(int)(Math.random()*20-10);
      pokemon.Pokemon pok;
      if(lvl<=22)pok=new pokemon.Karpador(lvl);
      else pok=new pokemon.Garados(lvl-5);
      Javamon.starteKampf(pok);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if(Math.random()*50<=1){
        t.stop();
        Javamon.sout("Da hat etwas angebissen!");
        Javamon.addListener(this);
      }else if(!Javamon.isPrinting())Javamon.sout("...");
    }
}