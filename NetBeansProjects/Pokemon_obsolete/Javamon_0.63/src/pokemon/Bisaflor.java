package pokemon;

import spiel.Typ;

public class Bisaflor extends Pokemon{
    
    public Bisaflor(int lvl){
      super("Bisaflor",80,82,83,105,105,70,Typ.PFLANZE,Typ.GIFT,lvl);
      ability=spiel.Fähigkeit.IMMUNITÄT;
    }
    
    Bisaflor(Bisaknosp vor){
      super("Bisaflor",80,82,83,105,105,70,Typ.PFLANZE,Typ.GIFT,vor);
    }

    @Override
    protected Pokemon lvlUp() {
      if(getLevel()==2)lerne(new attacken.normal.Tackle());
      if(getLevel()==5)lerne(new attacken.normal.Heuler());
      if(getLevel()==12)lerne(new attacken.pflanze.Schlafpuder());
      if(getLevel()==16)lerne(new attacken.pflanze.Absorber());
      if(getLevel()==20)lerne(new attacken.pflanze.Wachstum());
      if(getLevel()==24)lerne(new attacken.gift.Säure());
      if(getLevel()==41)lerne(new attacken.pflanze.Solarstrahl());
      return null;
    }
}