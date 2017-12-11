package attacken.geist;

import attacken.Attacke;
import spiel.Typ;

public class Spukball extends Attacke{
    
    public Spukball(){
      super("Spukball",80,15,95,Typ.GEIST,false);
    }

    @Override
    protected int effekt() {
      return dmg;
    }
}