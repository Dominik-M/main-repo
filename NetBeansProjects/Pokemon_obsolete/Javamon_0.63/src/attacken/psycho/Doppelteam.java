package attacken.psycho;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Typ;

public class Doppelteam extends Attacke{

    public Doppelteam(){
      super("Doppelteam",0,20,100,Typ.PSYCHO,false);
      tooltip="Projeziert Spiegelbilder";
    }
    
    @Override
    public int effekt() {
      spiel.Kampf k=spiel.Javamon.getAktuellKampf();
      pokemon.Pokemon anwender;
      if(k.binDran()){
        anwender=k.getSpielers();
      }else{
        anwender=k.getGegners();
      }
      boolean ging= anwender.boostFlu(2);
      if(ging)Javamon.sout(anwender+"s Fluchtwert setigt stark.");
      else Javamon.sout("Fluchtwert kann nicht mehr erhöht werden.");
      return dmg;
    }
    
}