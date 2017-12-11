package pokemon;

import spiel.Typ;

public class MegaGlurakX extends Pokemon{
    
    public MegaGlurakX(int lvl){
      super("Mega-GlurakX",88,130,111,130,85,100,Typ.FEUER,Typ.DRACHE,lvl);
    }
    
    public MegaGlurakX(Glurak vor){
      super("Mega-GlurakX",88,130,111,130,85,100,Typ.FEUER,Typ.DRACHE,vor);
    }

    @Override
    protected Pokemon lvlUp() {
      if(getLevel()==2)lerne(new attacken.normal.Kratzer());
      return null;   
    }
}