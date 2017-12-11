package attacken.boden;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Typ;

public class Sandsturm extends Attacke{

    public Sandsturm(){
      super("Sandsturm",20,20,95,Typ.BODEN,false);
      tooltip="Behindert die Sicht und fügt Schaden zu.";
    }
    
    @Override
    protected int effekt() {
      spiel.Kampf k=Javamon.getAktuellKampf();
      boolean ging= k.getSpielers().boostGena(-2);
      if(ging)Javamon.sout(k.getSpielers()+"s Genauigkeit sinkt stark.");
      else Javamon.sout("Genauigkeit kann nicht mehr gesenkt werden.");
      ging= k.getGegners().boostGena(-2);
      if(ging)Javamon.sout(k.getGegners()+"s Genauigkeit sinkt stark.");
      else Javamon.sout("Genauigkeit kann nicht mehr gesenkt werden.");
      return dmg;
    }
}