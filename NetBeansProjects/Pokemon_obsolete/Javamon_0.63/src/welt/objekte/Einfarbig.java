package welt.objekte;

import java.awt.Color;
import java.awt.Graphics;

public class Einfarbig extends Objekt{
    private Color farbe;
    
    public Einfarbig(){
      super(false);
      farbe=Color.BLACK;
      setName("Schwarz");
    }
    
    public Einfarbig(boolean betretbar,Color farb){
      super(betretbar);
      farbe=farb;
      setName(farbe.toString());
    }

    @Override
    public void benutzt() {}

    @Override
    public void betreten() {}

    @Override
    public void paintComponent(Graphics g, int spot) {
      g.setColor(farbe);
      g.fillRect(0,0, spot, spot);
    }  
}