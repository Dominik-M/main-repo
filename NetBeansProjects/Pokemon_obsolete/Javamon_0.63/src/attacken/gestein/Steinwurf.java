package attacken.gestein;

import attacken.Attacke;
import spiel.Typ;

public class Steinwurf extends Attacke{
    
    public Steinwurf(){
      super("Steinwurf",60,25,85,Typ.GESTEIN,true);
    }

    @Override
    protected int effekt() {
      return dmg;
    }
}