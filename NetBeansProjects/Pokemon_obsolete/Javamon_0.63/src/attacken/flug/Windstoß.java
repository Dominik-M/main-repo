package attacken.flug;

import attacken.Attacke;
import spiel.Typ;

public class Windstoß extends Attacke{
    
    public Windstoß(){
      super("Windstoß",40,35,95,Typ.FLUG,false);
    }

    @Override
    protected int effekt() {
      return dmg;
    }
    
}