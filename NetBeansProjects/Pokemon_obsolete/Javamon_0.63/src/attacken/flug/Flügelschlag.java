package attacken.flug;

import attacken.Attacke;
import spiel.Typ;

public class Flügelschlag extends Attacke{
    
    public Flügelschlag(){
      super("Flügelschlag",60,20,85,Typ.FLUG,true);
    }

    @Override
    protected int effekt() {
      return dmg;
    }
  
}