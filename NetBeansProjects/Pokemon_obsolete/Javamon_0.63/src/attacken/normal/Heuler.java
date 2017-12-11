package attacken.normal;

import attacken.Attacke;
import spiel.Typ;


public class Heuler extends Attacke{
    
    public Heuler(){
      super("Heuler",0,35,100,Typ.NORMAL,false);
      tooltip="Senkt die Angriffskraft des Gegners.";
    }
    
    @Override
    public int effekt() {
      spiel.Kampf k=spiel.Javamon.getAktuellKampf();
      pokemon.Pokemon opfer;
      if(k.binDran()){
        opfer=k.getGegners();
      }else{
        opfer=k.getSpielers();
      }
      boolean ging= opfer.boostAtk(-1);
      if(ging)spiel.Javamon.sout(opfer+"s Angriff sinkt.");
      else spiel.Javamon.sout("Angriff kann nicht mehr gesenkt werden.");
      return dmg;
    }
}