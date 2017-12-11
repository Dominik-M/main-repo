package attacken.unlicht;

import attacken.Attacke;
import spiel.Typ;

public class Verfolgung extends Attacke{
    
    public Verfolgung(){
      super("Verfolgung",40,30,100,Typ.UNLICHT,true);
    }

    @Override
    protected int effekt() {
      return dmg;
    }
}