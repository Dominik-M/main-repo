package attacken.wasser;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Typ;

public class Platscher extends Attacke{
    
    public Platscher(){
      super("Platscher",0,40,100,Typ.WASSER,true);
      tooltip="Zappelt hilflos am Boden";
    }

    @Override
    protected int effekt() {
      Javamon.sout("Es hat keinen Effekt.");
      return dmg;
    }
}