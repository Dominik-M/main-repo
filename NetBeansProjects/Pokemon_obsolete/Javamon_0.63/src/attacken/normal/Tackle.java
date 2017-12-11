package attacken.normal;

import attacken.Attacke;
import spiel.Typ;

public class Tackle extends Attacke{

  public Tackle(){
    super("Tackle",40,35,95,Typ.NORMAL,true);
  }
  
  @Override
  public int effekt() {
    return dmg;
  }
}