package pokemon;

import spiel.Typ;

public class Garados extends Pokemon{
    
    public Garados(int lvl){
      super("Garados",95,120,79,60,100,81,Typ.WASSER,Typ.DRACHE,lvl);
    }

    public Garados(Karpador vor){
      super("Garados",95,120,79,60,100,81,Typ.WASSER,Typ.DRACHE,vor);
    }
    @Override
    protected Pokemon lvlUp() {
      if(getLevel()==2)lerne(new attacken.normal.Tackle());
      if(getLevel()==5)lerne(new attacken.metall.Silberblick());
      if(getLevel()==21)lerne(new attacken.unlicht.Biss());
      if(getLevel()==25)lerne(new attacken.normal.Hyperstrahl(this));
      if(getLevel()==32)lerne(new attacken.wasser.Hydropumpe());
      if(getLevel()==40)lerne(new attacken.drache.Windhose());
      return null;
    }
    
}