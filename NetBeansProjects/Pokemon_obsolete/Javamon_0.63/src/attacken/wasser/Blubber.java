package attacken.wasser;

import attacken.Attacke;
import spiel.Typ;

public class Blubber extends Attacke{

    public Blubber(){
      super("Blubber",40,30,90,Typ.WASSER,false);
      tooltip="Angriff mit Sprühschaum.";
    }
    
    @Override
    public int effekt() {
      return dmg;
    }
}