package pokemon;

import spiel.Typ;

public class Schiggy extends Pokemon{

    public Schiggy(int lvl){
      super("Schiggy",44,48,70,50,64,45,Typ.WASSER,Typ.WASSER,lvl);
      ability=spiel.Fähigkeit.AQUAHÜLLE;
    }
    
    @Override
    public Pokemon lvlUp() {
      if(getLevel()==2)lerne(new attacken.normal.Tackle());
      if(getLevel()==5)lerne(new attacken.normal.Heuler());
      if(getLevel()==8)lerne(new attacken.wasser.Blubber());
      if(getLevel()==13)lerne(new attacken.wasser.Tsunami());
      if(getLevel()>=16)return new Schillok(this);
      return null;
    }
    
}