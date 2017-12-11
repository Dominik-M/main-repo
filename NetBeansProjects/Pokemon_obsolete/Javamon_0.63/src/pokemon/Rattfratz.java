package pokemon;

import spiel.Typ;

public class Rattfratz extends Pokemon{

    public Rattfratz(int lvl){
      super("Rattfratz",30,56,33,24,28,72,Typ.NORMAL,Typ.NORMAL,lvl);
      ability=spiel.Fähigkeit.MUNTERKEIT;
    }
    
    @Override
    public Pokemon lvlUp() {
      if(getLevel()==2)lerne(new attacken.normal.Tackle());
      if(getLevel()==6)lerne(new attacken.psycho.Doppelteam());
      return null;
    }
}