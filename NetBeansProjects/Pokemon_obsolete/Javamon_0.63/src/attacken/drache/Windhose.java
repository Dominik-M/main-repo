package attacken.drache;

import attacken.Attacke;
import spiel.Typ;

public class Windhose extends Attacke{

    public Windhose(){
      super("Windhose",80,15,100,Typ.DRACHE,false);
      tooltip="Erzeugt einen sengenden Sturm.";
    }
    
    @Override
    protected int effekt() {
      return dmg;
    }
    
}