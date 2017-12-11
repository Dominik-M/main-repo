package attacken.wasser;

import attacken.Attacke;
import spiel.Typ;

public class Hydropumpe extends Attacke{
    
    public Hydropumpe(){
      super("Hydropumpe",100,5,70,Typ.WASSER,false);
      tooltip="Schießt Wasser unter Hochdruch auf den Gegner.";
    }

    @Override
    protected int effekt() {
      return dmg;
    }
    
}