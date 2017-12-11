package attacken.kampf;

import attacken.Attacke;
import spiel.Typ;

public class Wuchtschlag extends Attacke{
    
    public Wuchtschlag(){
      super("Wuchtschlag",100,10,45,Typ.KAMPF,true);
      tooltip="Kräftiger Schlag, etwas ungenau";
    }

    @Override
    protected int effekt() {
      return dmg;
    }
}