package attacken.kampf;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Typ;

public class Zeitlupe extends Attacke{

    public Zeitlupe(){
      super("Zeitlupe",0,5,100,Typ.KAMPF,false);
      tooltip="Senkt Init- und Flucht-Werte enorm";
    }
    
    @Override
    protected int effekt() {
      spiel.Kampf k=spiel.Javamon.getAktuellKampf();
      k.getSpielers().boostFlu(-10);
      k.getGegners().boostFlu(-10);
      k.getSpielers().boostInit(-6);
      k.getGegners().boostInit(-6);
      Javamon.sout("Alles verlangsamt sich...");
      return dmg;
    }
    
}