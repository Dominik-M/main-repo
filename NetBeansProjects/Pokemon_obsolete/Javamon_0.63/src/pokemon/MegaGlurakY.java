package pokemon;

import spiel.Typ;

public class MegaGlurakY extends Pokemon{
    
    public MegaGlurakY(int lvl){
      super("Mega-GlurakY",78,104,78,159,115,100,Typ.FEUER,Typ.FLUG,lvl);
    }
    
    public MegaGlurakY(Glurak vor){
      super("Mega-GlurakY",78,104,78,159,115,100,Typ.FEUER,Typ.FLUG,vor);
    }

    @Override
    protected Pokemon lvlUp() {
      if(getLevel()==2)lerne(new attacken.normal.Kratzer());
      return null;
    }
}