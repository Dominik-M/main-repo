package attacken.boden;

import attacken.Attacke;
import spiel.Typ;

public class Sandwirbel extends Attacke{

    public Sandwirbel(){
      super("Sandwirbel",0,20,90,Typ.BODEN,false);
      tooltip="Senkt gegn. Genauigkeit.";
    }
    
    @Override
    protected int effekt() {
      spiel.Kampf k=spiel.Javamon.getAktuellKampf();
      pokemon.Pokemon opfer;
      if(k.binDran()){
        opfer=k.getGegners();
      }else{
        opfer=k.getSpielers();
      }
      boolean ging= opfer.boostGena(-1);
      if(ging)spiel.Javamon.sout(opfer+"s Genauigkeit sinkt.");
      else spiel.Javamon.sout("Genauigkeit kann nicht mehr gesenkt werden.");
      return dmg;
    }
}