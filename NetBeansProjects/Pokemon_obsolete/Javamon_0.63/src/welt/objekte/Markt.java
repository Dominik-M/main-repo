package welt.objekte;

import spiel.Javamon;
import spiel.OutputListener;
import spiel.Trainer;
import welt.items.Item;

public class Markt extends Person implements OutputListener{
    private Item[] angebot;
    
    public Markt(){
        this(Trainer.DOWN);
    }
    
    public Markt(int blickrichtung,Item... waren){
      super(blickrichtung,"Yo, was brauchst du?");
      angebot=waren;
      this.setColor(new java.awt.Color(0,0,200));
      this.setSkinColor(new java.awt.Color(150,50,0));
    }

    @Override
    public void benutzt() {
      super.benutzt();
      Javamon.addListener(this);
    }

    @Override
    public void betreten() {}

    @Override
    public void ausgabeFertig() {
      Javamon.display(new spiel.Handel(angebot));
    }
}