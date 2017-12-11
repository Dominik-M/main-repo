package welt.objekte;

import java.awt.Graphics;

public class Deko extends Objekt{
    private final int[] farben;
    
    public Deko(String n, int[] pixel){
      super(false);
      farben=pixel;
      setName(n);
    }
    
    public Deko(int[] pixel){
      super(false);
      farben=pixel;
    }

    @Override
    public void benutzt() {}

    @Override
    protected void betreten() {}

    @Override
    public void paintComponent(Graphics g, int spot) {
      int b=spiel.Spielwelt.SPOT;
      for(int i=0;i<farben.length;i++){
        g.setColor(new java.awt.Color(farben[i]));
        g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
      }
    }
}