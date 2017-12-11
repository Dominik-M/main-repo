package pokemon;

import spiel.Typ;

public class Schillok extends Pokemon{
    
    public Schillok(int lvl){
      super("Schillok",59,63,80,65,80,58,Typ.WASSER,Typ.WASSER,lvl);
      ability=spiel.Fähigkeit.AQUAHÜLLE;
    }
    
    public Schillok(Schiggy vor){
      super("Schillok",59,63,80,65,80,58,Typ.WASSER,Typ.WASSER,vor);
    }

    @Override
    public Pokemon lvlUp() {
      if(getLevel()==2)lerne(new attacken.normal.Tackle());
      if(getLevel()==5)lerne(new attacken.normal.Heuler());
      if(getLevel()==11)lerne(new attacken.wasser.Blubber());
      if(getLevel()>=36)return new Turtok(this);
      return null;
    }
    
}