package attacken.pflanze;

import attacken.Attacke;
import spiel.Typ;

public class Rankenhieb extends Attacke{

    public Rankenhieb(){
      super("Rankenhieb",55,25,85,Typ.PFLANZE,true);
      tooltip="Peitschenschlag mit Ranken o.Ä.";
    }
    
    @Override
    protected int effekt() {
      return dmg;
    }
    
}