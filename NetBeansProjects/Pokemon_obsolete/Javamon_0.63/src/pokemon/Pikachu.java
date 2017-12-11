package pokemon;

import spiel.Typ;

public class Pikachu extends Pokemon{

    public Pikachu(int lvl){
      super("Pikachu",35,55,40,52,50,90,Typ.ELEKTRO,Typ.ELEKTRO,lvl);
      ability=spiel.Fähigkeit.STATIK;
    }
    
    @Override
    public Pokemon lvlUp() {
      if(getLevel()==2)lerne(new attacken.elektro.Donnerschock());
      if(getLevel()==5)lerne(new attacken.normal.Heuler());
      if(getLevel()==8)lerne(new attacken.normal.Kratzer());
      if(getLevel()==11)lerne(new attacken.elektro.Donnerwelle());
      if(getLevel()==16)lerne(new attacken.metall.Eisenschweif());
      if(getLevel()==19)lerne(new attacken.psycho.Doppelteam());
      return null;
    }
    
}