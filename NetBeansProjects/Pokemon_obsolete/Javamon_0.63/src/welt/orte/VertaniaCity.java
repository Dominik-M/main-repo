package welt.orte;

import spiel.Javamon;
import welt.objekte.Objekt;

public class VertaniaCity extends Ort{
    
    public VertaniaCity(Route1 vor){
      super("Vertania City",40,40);
      standart=Objekt.BODEN;
      objekte[19][39]=new welt.objekte.Tür(vor,9,0);
      objekte[20][39]=new welt.objekte.Tür(vor,10,0);
      if(Javamon.MAKE_FILES)tools.OrtComponent.writeOrt(this, new java.io.File(name+".ort"));
    }

    @Override
    public void erstelle() {
      zeichnePoke(this,21,25);
      zeichneMarkt(this,29,16,new welt.items.Trank(175));
      zeichneHütte(this,13,27,5,4,true);
      zeichneHütte(this,16,30,1,3,true);
      zeichneHütte(this,16,33,0,2,true);
      zeichneHütte(this,16,36,2,4,false);
      Haus haus=zeichneHaus(this,21,9,9,7,true);
      for(int x=1;x<8;x++)haus.objekte[x][0]=Objekt.AUTOMAT;
      haus.objekte[0][2]=new welt.objekte.Münzhändler(1);
      haus.objekte[0][4]=new welt.objekte.Münztauscher(1);
      for(int x=6;x<=13;x++)for(int y=5;y<=14;y++)objekte[x][y]=Objekt.MOOS;
    }
    
    @Override
    public Objekt getObjekt(int x,int y){
      if(y>=40)return Objekt.WIESE;
      else if(x<0)return Objekt.STEIN;
      else if(x>=40||y<0) return Objekt.MOOS;
      Objekt o;
      try{
        o=objekte[x][y];
        if(o==null){
          if(y==39)o=Objekt.STEIN;
          else o=standart;
        }
      }
      catch(Exception ex){o=Objekt.STEIN;}
      return o;
    }

    @Override
    public int getStartX() {
      return 23;
    }

    @Override
    public int getStartY() {
      return 31;
    }
}