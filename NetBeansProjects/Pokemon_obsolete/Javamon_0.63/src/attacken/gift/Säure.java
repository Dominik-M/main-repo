package attacken.gift;

import attacken.Attacke;
import spiel.Typ;

public class Säure extends Attacke{
    
    public Säure(){
      super("Säure",65,20,80,Typ.GIFT,false);
    }

    @Override
    protected int effekt() {
      return dmg;
    }
}