package attacken.wasser;

import attacken.Attacke;
import spiel.Typ;

public class Wasserklinge extends Attacke{
    
    public Wasserklinge(){
      super("Wasserklinge",70,15,95,Typ.WASSER,true);
    }

    @Override
    protected int effekt() {
      return dmg;
    }
}