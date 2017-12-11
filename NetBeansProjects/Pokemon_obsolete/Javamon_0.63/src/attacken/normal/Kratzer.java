package attacken.normal;

import attacken.Attacke;
import spiel.Typ;

public class Kratzer extends Attacke{

    public Kratzer(){
      super("Kratzer",50,35,85,Typ.NORMAL,true);
      tooltip="Hieb mit Klauen o.Ä.";
    }
    
    @Override
    protected int effekt() {
      return dmg;
    }
}