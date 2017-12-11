package attacken.boden;

import attacken.Attacke;
import spiel.Typ;

public class Erdbeben extends Attacke{
    
    public Erdbeben(){
      super("Erdbeben",100,10,95,Typ.BODEN,true);
      tooltip="Starke Attacke. Wirkungslos gegen Flug-Pokemon.";
    }

    @Override
    protected int effekt() {
      return dmg;
    }
}