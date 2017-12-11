package attacken.metall;

import attacken.Attacke;
import spiel.Typ;

public class Silberblick extends Attacke{

    public Silberblick(){
      super("Silberblick",0,30,95,Typ.METALL,false);
      tooltip="Senkt gegn. Verteidigung.";
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
      boolean ging= opfer.boostVert(-1);
      if(ging)spiel.Javamon.sout(opfer+"s Verteidigung sinkt.");
      else spiel.Javamon.sout("Verteidigung kann nicht mehr gesenkt werden.");
      return dmg;
    }
}