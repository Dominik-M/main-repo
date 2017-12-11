package attacken.kampf;

import attacken.Attacke;
import spiel.Typ;

public class Fußkick extends Attacke{

    public Fußkick(){
      super("Fußkick",50,30,90,Typ.KAMPF,true);
      tooltip="Ein Tritt";
    }
    
    @Override
    protected int effekt() {
      return dmg;
    }
}