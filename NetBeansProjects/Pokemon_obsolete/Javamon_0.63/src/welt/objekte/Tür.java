package welt.objekte;

import java.awt.Color;
import java.awt.Graphics;
import spiel.Javamon;
import spiel.Spielwelt;
import welt.orte.Ort;

public class Tür extends Objekt{
    private Ort ziel;
    private int x,y,key;
    
    public Tür(Ort wohin,int neuX,int neuY,int schlüssel){
      super(false);
      ziel=wohin;
      x=neuX;
      y=neuY;
      key=schlüssel;
    }
    
    public Tür(Ort wohin,int neuX,int neuY){
      super(true);
      ziel=wohin;
      x=neuX;
      y=neuY;
    }
    
    public Tür(){
        super(true);
        ziel=null;
        x=0;
        y=0;
    }
    
    public void setZiel(Ort neu,int neuX,int neuY){
        ziel=neu;
        x=neuX;
        y=neuY;
    }
    
    public Ort getZielOrt(){
        return ziel;
    }
    
    public java.awt.Point getZielKoords(){
        return new java.awt.Point(x, y);
    }

    @Override
    public void benutzt() {
      if(!begehbar){
        int index=Javamon.getSpieler().hasKey(key);
        if(index>=0){
          Javamon.sout(Javamon.getSpieler().toString()+" benutzt "+
                  Javamon.getSpieler().getitems()[index].toString());
          Javamon.getSpieler().removeItem(index);
          Javamon.sout("Tür geöffnet.");
          begehbar=true;
        }else{
          Javamon.sout("Die Tür ist verschlossen.");
        }
      }
    }

    @Override
    public void betreten() {
      Javamon.Ortwechsel(ziel,x,y);
    }

    @Override
    public void paintComponent(Graphics g, int spot) {
      int b=Spielwelt.SPOT;
      int[] schwarze={2+b,3+b,4+b,5+b,6+b,7+b,8+b,9+b,10+b,11+b,12+b,13+b,
      2+2*b,13+2*b,2+3*b,5+3*b,6+3*b,7+3*b,8+3*b,9+3*b,10+3*b,13+3*b,2+4*b,
      4+4*b,11+4*b,13+4*b,2+5*b,4+5*b,11+5*b,13+5*b,2+6*b,4+6*b,11+6*b,13+6*b,
      2+7*b,4+7*b,11+7*b,13+7*b,2+8*b,5+8*b,6+8*b,7+8*b,8+8*b,9+8*b,10+8*b,
      12+8*b,13+8*b,2+9*b,11+9*b,13+9*b,2+10*b,12+10*b,13+10*b,2+11*b,13+11*b,
      2+12*b,13+12*b,2+13*b,13+13*b,2+14*b,3+14*b,4+14*b,5+14*b,6+14*b,7+14*b,
      8+14*b,9+14*b,10+14*b,11+14*b,12+14*b,13+14*b,15*b,1+15*b,14+15*b,15+15*b
      };
      int[] graue={0,2,13,15,b,2*b,3*b,4*b,5*b,6*b,7*b,8*b,9*b,10*b,11*b,12*b,
      13*b,14*b,15+b,15+2*b,15+3*b,15+4*b,15+5*b,15+6*b,15+7*b,15+8*b,15+9*b,
      15+10*b,15+11*b,15+12*b,15+13*b,15+14*b};
      int[] helle={1,3,4,5,6,7,8,9,10,11,12,14,1+b,14+b,1+2*b,14+2*b,1+3*b,
          14+3*b,9+4*b,8+5*b,10+5*b,7+6*b,9+6*b,10+6*b,6+7*b,8+7*b,9+7*b,10+7*b,
      4+10*b,11+10*b,5+11*b,6+11*b,7+11*b,8+11*b,9+11*b,10+11*b,11+11*b,5+12*b,
      6+12*b,7+12*b,8+12*b,9+12*b,10+12*b,11+12*b,2+15*b,3+15*b,4+15*b,5+15*b,
      6+15*b,7+15*b,8+15*b,9+15*b,10+15*b,11+15*b,12+15*b,13+15*b};
      int[] weisse={1+4*b,5+4*b,6+4*b,7+4*b,8+4*b,10+4*b,14+4*b,1+5*b,5+5*b,
      6+5*b,7+5*b,9+5*b,14+5*b,1+6*b,5+6*b,6+6*b,8+6*b,14+6*b,1+7*b,5+7*b,7+7*b,
      14+7*b,1+8*b,14+8*b,1+9*b,12+9*b,14+9*b,1+10*b,5+10*b,6+10*b,7+10*b,
      8+10*b,9+10*b,10+10*b,14+10*b,1+11*b,4+11*b,14+11*b,1+12*b,4+12*b,14+12*b,
      1+13*b,14+13*b,1+14*b,14+14*b};
      g.setColor(Color.BLACK);
      for(int i:schwarze){
          g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
      }
      g.setColor(Color.PINK.darker().darker());
      for(int i:graue){
          g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
      }
      g.setColor(Color.GRAY);
      for(int i:helle){
          g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
      }
      g.setColor(Color.WHITE);
      for(int i:weisse){
          g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
      }
    }
}