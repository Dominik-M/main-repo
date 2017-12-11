package attacken.pflanze;

import attacken.Attacke;
import spiel.Typ;

public class Rasierblatt extends Attacke{
    
    public Rasierblatt(){
      super("Rasierblatt",65,20,95,Typ.PFLANZE,false);
    }

    @Override
    protected int effekt() {
      return dmg;
    }
}