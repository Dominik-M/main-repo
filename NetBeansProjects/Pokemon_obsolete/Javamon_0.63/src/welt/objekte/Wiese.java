package welt.objekte;

import java.awt.Color;
import java.awt.Graphics;

public class Wiese extends Objekt{
    private boolean blumen;
    
    public Wiese(boolean bepflanzt){
      super(true);
      blumen=bepflanzt;
      if(blumen)setName("Blumen");
      else setName("Wiese");
    }

    @Override
    public void benutzt() {}

    @Override
    public void betreten() {}

    @Override
    public void paintComponent(Graphics g, int spot) {
      int b=spiel.Spielwelt.SPOT;
      int[] gruene={0,2,4,6,8,10,12,14,1+b,7+b,9+b,15+b,2*b,2+2*b,4+2*b,
      6+2*b,8+2*b,10+2*b,12+2*b,14+2*b,1+3*b,3+3*b,5+3*b,7+3*b,9+3*b,
      11+3*b,13+3*b,15+3*b,4*b,2+4*b,4+4*b,6+4*b,8+4*b,10+4*b,12+4*b,
      14+4*b,1+5*b,3+5*b,5+5*b,7+5*b,9+5*b,11+5*b,13+5*b,15+5*b,6*b,
      2+6*b,4+6*b,6+6*b,8+6*b,10+6*b,12+6*b,14+6*b,1+7*b,3+7*b,5+7*b,
      7+7*b,9+7*b,11+7*b,13+7*b,15+7*b,8*b,2+8*b,4+8*b,6+8*b,8+8*b,10+8*b,
      12+8*b,14+8*b,1+9*b,7+9*b,9+9*b,15+9*b,10*b,2+10*b,4+10*b,6+10*b,
      8+10*b,10+10*b,12+10*b,14+10*b,1+11*b,3+11*b,5+11*b,7+11*b,9+11*b,
      11+11*b,13+11*b,15+11*b,12*b,2+12*b,4+12*b,6+12*b,8+12*b,10+12*b,
      12+12*b,14+12*b,1+13*b,3+13*b,5+13*b,7+13*b,9+13*b,11+13*b,13+13*b,
      15+13*b,14*b,2+14*b,4+14*b,6+14*b,8+14*b,10+14*b,12+14*b,14+14*b,
      1+15*b,3+15*b,5+15*b,7+15*b,9+15*b,11+15*b,13+15*b,15+15*b};
      int[] dunkle={3+b,5+b,11+b,13+b,1+2*b,7+2*b,9+2*b,15+2*b,3+9*b,5+9*b,
      11+9*b,13+9*b,1+10*b,7+10*b,9+10*b,15+10*b};
      g.setColor(Color.GREEN);
      for(int i:gruene) g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
      g.setColor(Color.GREEN.darker());
      for(int i:dunkle) g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
      if(blumen){
        int[] pinke={8,10,14,9+b,12+b,13+b,15+b,8+2*b,11+2*b,14+2*b,11+3*b,
        14+3*b,9+4*b,12+4*b,13+4*b,9+5*b,15+5*b,8+6*b,10+6*b,11+6*b,14+6*b,
        9+7*b,13+7*b,15+7*b,8*b,2+8*b,6+8*b,1+9*b,4+9*b,5+9*b,7+9*b,10*b,
        3+10*b,6+10*b,3+11*b,6+11*b,1+12*b,4+12*b,5+12*b,1+13*b,7+13*b,14*b,
        2+14*b,3+14*b,6+14*b,1+15*b,5+15*b,7+15*b};
        int[] rote={12,13,11+b,14+b,10+2*b,15+2*b,9+3*b,15+3*b,8+4*b,14+4*b,
        8+5*b,13+5*b,9+6*b,12+6*b,10+7*b,11+7*b,4+8*b,5+8*b,3+9*b,6+9*b,2+10*b,
        7+10*b,1+11*b,7+11*b,12*b,6+12*b,13*b,5+13*b,1+14*b,4+14*b,2+15*b,
        3+15*b};
        int[] orangene={12+2*b,13+2*b,10+3*b,12+3*b,13+3*b,10+4*b,11+4*b,10+5*b,
        11+5*b,12+5*b,4+10*b,5+10*b,2+11*b,4+11*b,5+11*b,2+12*b,3+12*b,2+13*b,
        3+13*b,4+13*b};
        g.setColor(Color.MAGENTA.brighter());
      for(int i:pinke) g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
      g.setColor(Color.RED.darker());
      for(int i:rote) g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
      g.setColor(Color.ORANGE.brighter());
      for(int i:orangene) g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
      }
    }
}