package welt.objekte;

import java.awt.Graphics;
import spiel.Javamon;
import spiel.Spielwelt;

public class Heilquelle extends Objekt{
    private int vorrat;
    
    public Heilquelle(){
      super(true);
      setName("Heilfläche");
    }
    
    public Heilquelle(int n){
      super(false);
      setName("Heilquelle");
      vorrat=n;
    }
    
    @Override
    public void benutzt() {
      if(begehbar)return;
      if(vorrat<=0){
        Javamon.sout("Die Heilquelle ist aufgebraucht.");
        return;
      }
      if(Javamon.getSpieler().isOK())return;
      Javamon.sout("Deine Pokemon wurden geheilt.");
      Javamon.getSpieler().heile();
      vorrat--;
    }

    @Override
    public void betreten() {  
      if(Javamon.getSpieler().isOK())return;
      Javamon.sout("Deine Pokemon wurden geheilt.");
      Javamon.getSpieler().heile();
    }
    
    @Override
    public void paintComponent(Graphics g,int spot) {
      int b=Spielwelt.SPOT;
      int[] blaue={1,7,13,b,1+b,6+b,8+b,13+b,14+b,15+b,5+2*b,9+2*b,4+3*b,10+3*b,
      3+4*b,7+4*b,11+4*b,2+5*b,6+5*b,8+5*b,12+5*b,1+6*b,5+6*b,9+6*b,13+6*b,
      7*b,4+7*b,7+7*b,10+7*b,14+7*b,1+8*b,5+8*b,9+8*b,13+8*b,2+9*b,6+9*b,8+9*b,12+9*b,
      3+10*b,7+10*b,11+10*b,4+11*b,10+11*b,5+12*b,9+12*b,13*b,1+13*b,6+13*b,
      8+13*b,13+13*b,14+13*b,15+13*b,1+14*b,7+14*b,13+14*b,1+15*b,13+15*b};
      g.setColor(java.awt.Color.CYAN);
      for(int i:blaue){
          g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
      }
      if(!begehbar){
        g.setColor(java.awt.Color.GREEN.darker());
        for(int i=0,j=0;i<b*b;i++){
          if(j<blaue.length&&blaue[j]==i)j++;
          else g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
        }
      }
    }
}