package attacken.metall;

import attacken.Attacke;
import spiel.Typ;

public class Stahlklinge extends Attacke{
    
    public Stahlklinge(){
      super("Stahlklinge",60,20,95,Typ.METALL,true);
    }

    @Override
    protected int effekt() {
      return dmg;
    }
}