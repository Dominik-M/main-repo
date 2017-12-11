package attacken.unlicht;

import attacken.Attacke;
import spiel.Typ;

public class Biss extends Attacke{

    public Biss(){
      super("Biss",60,25,95,Typ.UNLICHT,true);
    }
    
    @Override
    protected int effekt() {
      return dmg;
    }
}