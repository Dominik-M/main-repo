package welt.objekte;

import java.awt.Color;
import java.awt.Graphics;
import spiel.Javamon;
import spiel.Spielwelt;
import spiel.Trainer;
import welt.items.Item;

public class Person extends Objekt{
    private spiel.Stringqueue dialog;
    private String text;
    protected int richtung;
    private Item item;
    private Color farbe;
    private Color hautfarbe;
    
    public Person(){
      super(false);
      dialog=new spiel.Stringqueue();
      richtung=Trainer.DOWN;
      farbe=new java.awt.Color((int)(Math.random()*Integer.MAX_VALUE));
      hautfarbe=FarbPalette.HAUTFARBEN[(int)(Math.random()*FarbPalette.HAUTFARBEN.length)];
    }
    
    public Person(int blickrichtung,String... texte){
      super(false);
      dialog=new spiel.Stringqueue();
      for(String txt:texte)dialog.add(txt);
      richtung=blickrichtung;
      farbe=new java.awt.Color((int)(Math.random()*Integer.MAX_VALUE));
      hautfarbe=FarbPalette.HAUTFARBEN[(int)(Math.random()*FarbPalette.HAUTFARBEN.length)];
    }
    
    public Person(int blickrichtung,Item anItem,String... texte){
      super(false);
      dialog=new spiel.Stringqueue();
      for(String txt:texte)dialog.add(txt);
      richtung=blickrichtung;
      item=anItem;
      farbe=new java.awt.Color((int)(Math.random()*Integer.MAX_VALUE));
      hautfarbe=FarbPalette.HAUTFARBEN[(int)(Math.random()*FarbPalette.HAUTFARBEN.length)];
    }

    @Override
    public void benutzt() {
      setRichtung(-Javamon.getSpieler().getRichtung());
      if(dialog.peak()!=null)text=dialog.pull();
      Javamon.sout(text);
      if(item!=null){
        Javamon.getSpieler().addItem(item);
        Javamon.sout(Javamon.getSpieler().toString()+" erhält "+item.toString());
        item=null;
      }
    }

    @Override
    public void betreten() {}

    @Override
    public void paintComponent(Graphics g, int spot) {
      int b=Spielwelt.SPOT;
      g.setColor(farbe);
      if(richtung==Trainer.LEFT){
        int[] schwarze={5,6,7,8,9,10,4+b,11+b,3+2*b,12+2*b,2+3*b,3+3*b,12+3*b,
      1+4*b,11+4*b,12+4*b,13+4*b,2+5*b,3+5*b,7+5*b,8+5*b,9+5*b,10+5*b,11+5*b,
      12+5*b,13+5*b,3+6*b,5+6*b,8+6*b,9+6*b,10+6*b,11+6*b,12+6*b,13+6*b,
      3+7*b,5+7*b,8+7*b,11+7*b,12+7*b,3+8*b,11+8*b,4+9*b,9+9*b,10+9*b,12+9*b,
      5+10*b,6+10*b,7+10*b,8+10*b,9+10*b,12+10*b,6+11*b,7+11*b,10+11*b,12+11*b,
      6+12*b,7+12*b,10+12*b,12+12*b,5+13*b,8+13*b,9+13*b,10+13*b,11+13*b,
      5+14*b,10+14*b,6+15*b,7+15*b,8+15*b,9+15*b};
      int[] rote={5+b,6+b,7+b,8+b,9+b,10+b,4+2*b,5+2*b,6+2*b,7+2*b,8+2*b,9+2*b,
      10+2*b,11+2*b,5+3*b,6+3*b,7+3*b,8+3*b,9+3*b,10+3*b,11+3*b,6+4*b,7+4*b,
      8+4*b,9+4*b,10+4*b,4+5*b,5+5*b,6+5*b,5+9*b,11+9*b,10+10*b,11+10*b,11+11*b,
      11+12*b,6+13*b,7+13*b,6+14*b,7+14*b,8+14*b,9+14*b,10+14*b};
      int[] weisse={4+3*b,2+4*b,3+4*b,4+4*b,5+4*b,4+6*b,6+6*b,7+6*b,4+7*b,6+7*b,
      7+7*b,9+7*b,10+7*b,4+8*b,5+8*b,6+8*b,7+8*b,8+8*b,9+8*b,10+8*b,6+9*b,7+9*b,
      8+9*b,8+11*b,9+11*b,8+12*b,9+12*b};
      for(int i:rote){
        g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
      }
      g.setColor(Color.BLACK);
      for(int i:schwarze){
        g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
      }
      g.setColor(hautfarbe);
      for(int i:weisse){
        g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
      }
      }else if(richtung==Trainer.RIGHT){
        int[] schwarze={5,6,7,8,9,10,4+b,11+b,3+2*b,12+2*b,3+3*b,12+3*b,13+3*b,
      2+4*b,3+4*b,4+4*b,14+4*b,2+5*b,3+5*b,4+5*b,5+5*b,6+5*b,7+5*b,8+5*b,
      12+5*b,13+5*b,2+6*b,3+6*b,4+6*b,5+6*b,6+6*b,7+6*b,10+6*b,12+6*b,
      3+7*b,4+7*b,7+7*b,10+7*b,12+7*b,4+8*b,12+8*b,3+9*b,5+9*b,6+9*b,11+9*b,
      3+10*b,6+10*b,7+10*b,8+10*b,9+10*b,10+10*b,3+11*b,5+11*b,8+11*b,9+11*b,
      3+12*b,5+12*b,8+12*b,9+12*b,4+13*b,5+13*b,6+13*b,7+13*b,10+13*b,
      5+14*b,10+14*b,6+15*b,7+15*b,8+15*b,9+15*b};
      int[] rote={5+b,6+b,7+b,8+b,9+b,10+b,4+2*b,5+2*b,6+2*b,7+2*b,8+2*b,9+2*b,
      10+2*b,11+2*b,4+3*b,5+3*b,6+3*b,7+3*b,8+3*b,9+3*b,10+3*b,5+4*b,6+4*b,
      7+4*b,8+4*b,9+4*b,9+5*b,10+5*b,11+5*b,4+9*b,10+9*b,4+10*b,5+10*b,4+11*b,
      4+12*b,8+13*b,9+13*b,6+14*b,7+14*b,8+14*b,9+14*b};
      int[] weisse={11+3*b,10+4*b,11+4*b,12+4*b,13+4*b,8+6*b,9+6*b,11+6*b,
      5+7*b,6+7*b,8+7*b,9+7*b,11+7*b,5+8*b,6+8*b,7+8*b,8+8*b,9+8*b,10+8*b,
      11+8*b,7+9*b,8+9*b,9+9*b,6+11*b,7+11*b,6+12*b,7+12*b};
        for(int i:rote){
          g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
        }
        g.setColor(Color.BLACK);
        for(int i:schwarze){
          g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
        }
        g.setColor(hautfarbe);
        for(int i:weisse){
          g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
        }
      }else if(richtung==Trainer.UP){
        int[] schwarze={5,6,7,8,9,10,4+b,11+b,3+2*b,12+2*b,3+3*b,12+3*b,
      2+4*b,3+4*b,12+4*b,13+4*b,2+5*b,3+5*b,4+5*b,11+5*b,12+5*b,13+5*b,
      1+6*b,3+6*b,4+6*b,5+6*b,6+6*b,7+6*b,8+6*b,9+6*b,10+6*b,11+6*b,12+6*b,
      14+6*b,1+7*b,4+7*b,5+7*b,6+7*b,7+7*b,8+7*b,9+7*b,10+7*b,11+7*b,14+7*b,
      2+8*b,3+8*b,6+8*b,7+8*b,8+8*b,9+8*b,12+8*b,13+8*b,2+9*b,3+9*b,4+9*b,
      5+9*b,10+9*b,11+9*b,12+9*b,13+9*b,1+10*b,3+10*b,4+10*b,6+10*b,7+10*b,
      8+10*b,9+10*b,11+10*b,12+10*b,14+10*b,1+11*b,3+11*b,4+11*b,11+11*b,
      12+11*b,14+11*b,2+12*b,3+12*b,4+12*b,5+12*b,10+12*b,11+12*b,12+12*b,
      13+12*b,3+13*b,5+13*b,6+13*b,7+13*b,8+13*b,9+13*b,10+13*b,12+13*b,
      3+14*b,7+14*b,8+14*b,12+14*b,4+15*b,5+15*b,6+15*b,9+15*b,10+15*b,11+15*b};
      int[] rote={5+b,6+b,7+b,8+b,9+b,10+b,4+2*b,5+2*b,6+2*b,7+2*b,8+2*b,9+2*b,
      10+2*b,11+2*b,4+3*b,5+3*b,6+3*b,7+3*b,8+3*b,9+3*b,10+3*b,11+3*b,4+4*b,
      5+4*b,6+4*b,7+4*b,8+4*b,9+4*b,10+4*b,11+4*b,5+5*b,6+5*b,7+5*b,8+5*b,
      9+5*b,10+5*b,6+9*b,7+9*b,8+9*b,9+9*b,5+10*b,10+10*b,5+11*b,6+11*b,
      9+11*b,10+11*b,6+12*b,7+12*b,8+12*b,9+12*b,4+13*b,11+13*b,
      4+14*b,5+14*b,6+14*b,9+14*b,10+14*b,11+14*b};
      int[] weisse={2+6*b,13+6*b,2+7*b,3+7*b,12+7*b,13+7*b,4+8*b,5+8*b,10+8*b,
      11+8*b,2+10*b,13+10*b,2+11*b,7+11*b,8+11*b,13+11*b};
        for(int i:rote){
          g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
        }
        g.setColor(Color.BLACK);
        for(int i:schwarze){
          g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
        }
        g.setColor(hautfarbe);
        for(int i:weisse){
          g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
        }
      }else{
          int[] schwarze={5,6,7,8,9,10,4+b,11+b,3+2*b,12+2*b,3+3*b,12+3*b,
                        2+4*b,3+4*b,4+4*b,11+4*b,12+4*b,13+4*b,2+5*b,3+5*b,
                        5+5*b,6+5*b,7+5*b,8+5*b,9+5*b,10+5*b,12+5*b,13+5*b,
                        1+6*b,3+6*b,12+6*b,14+6*b,1+7*b,6+7*b,9+7*b,14+7*b,
                        2+8*b,3+8*b,6+8*b,9+8*b,12+8*b,13+8*b,2+9*b,3+9*b,
                        4+9*b,11+9*b,12+9*b,13+9*b,1+10*b,4+10*b,5+10*b,
                        6+10*b,7+10*b,8+10*b,9+10*b,10+10*b,11+10*b,14+10*b,
                        1+11*b,4+11*b,5+11*b,6+11*b,7+11*b,8+11*b,9+11*b,
                        10+11*b,11+11*b,14+11*b,2+12*b,3+12*b,4+12*b,7+12*b,
                        8+12*b,11+12*b,12+12*b,13+12*b,3+13*b,5+13*b,6+13*b,
                        9+13*b,10+13*b,12+13*b,3+14*b,7+14*b,8+14*b,12+14*b,
                        4+15*b,5+15*b,6+15*b,9+15*b,10+15*b,11+15*b};
          int[] rote={5+b,6+b,7+b,8+b,9+b,10+b,4+2*b,5+2*b,6+2*b,7+2*b,8+2*b,
                    9+2*b,10+2*b,11+2*b,4+3*b,5+3*b,6+3*b,7+3*b,8+3*b,
                    9+3*b,10+3*b,11+3*b,5+4*b,10+4*b,7+9*b,8+9*b,5+12*b,
                    6+12*b,9+12*b,10+12*b,4+13*b,7+13*b,8+13*b,11+13*b,
                    4+14*b,5+14*b,6+14*b,9+14*b,10+14*b,11+14*b};
          int[] weisse={6+4*b,7+4*b,8+4*b,9+4*b,4+5*b,11+5*b,2+6*b,4+6*b,5+6*b,
                     6+6*b,7+6*b,8+6*b,9+6*b,10+6*b,11+6*b,13+6*b,2+7*b,
                     3+7*b,4+7*b,5+7*b,7+7*b,8+7*b,10+7*b,11+7*b,12+7*b,13+7*b,
                     4+8*b,5+8*b,7+8*b,8+8*b,10+8*b,11+8*b,5+9*b,6+9*b,
                     9+9*b,10+9*b,2+10*b,3+10*b,12+10*b,13+10*b,2+11*b,
                     3+11*b,12+11*b,13+11*b};
        for(int i:rote){
          g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
        }
        g.setColor(Color.BLACK);
        for(int i:schwarze){
          g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
        }
        g.setColor(hautfarbe);
        for(int i:weisse){
          g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
        }
      }
    }
    
    public Item getItem(){
        return item;
    }
    
    public void setItem(Item anItem){
        item=anItem;
    }
    
    public Color getColor(){
      return farbe;
    }
    
    public Color getSkinColor(){
        return hautfarbe;
    }
    
    public void setColor(Color kleidungsfarbe){
        if(kleidungsfarbe!=null)
          farbe=kleidungsfarbe;
    }
    
    public void setSkinColor(Color hautfarb){
        if(hautfarb!=null)
          hautfarbe=hautfarb;
    }
    
    public void setRichtung(int blickrichtung){
        richtung=blickrichtung;
    }
    
    public void setDialog(String... texte){
        dialog=new spiel.Stringqueue();
        for(String txt:texte)dialog.add(txt);
    }
    
    public void addDialog(String... texte){
      for(String txt:texte)dialog.add(txt);
    }
    
    public String[] getDialog(){
        return dialog.toArray();
    }
}