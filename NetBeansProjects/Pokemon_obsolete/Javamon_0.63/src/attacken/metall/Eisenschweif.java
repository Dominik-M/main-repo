package attacken.metall;

import attacken.Attacke;
import spiel.Typ;

public class Eisenschweif extends Attacke{
    
    public Eisenschweif(){
      super("Eisenschweif",90,15,70,Typ.METALL,true);
      tooltip="Starker Hieb mit einem stahlharten Schwanz.";
    }

    @Override
    protected int effekt() {
      return dmg;
    } 
}