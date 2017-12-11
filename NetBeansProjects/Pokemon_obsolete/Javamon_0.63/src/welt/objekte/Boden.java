package welt.objekte;

import java.awt.Color;
import java.awt.Graphics;
import spiel.Spielwelt;

public class Boden extends Objekt{
    
    public Boden(){
      super(true);
      setName("Boden");
    }

    @Override
    public void benutzt() {}

    @Override
    public void betreten() {}

    @Override
    public void paintComponent(Graphics g, int spot) {
      g.setColor(Spielwelt.BACKGROUND);
      g.fillRect(0,0, spot, spot);
      int b=Spielwelt.SPOT;
      g.setColor(Color.GREEN.darker());
      int[] gruene={1+5*b,1+6*b,3+5*b,3+6*b,7+7*b,7+8*b,9+7*b,9+8*b,2+12*b,
          2+13*b,4+12*b,4+13*b};
      for(int i:gruene) g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
    }
    
}