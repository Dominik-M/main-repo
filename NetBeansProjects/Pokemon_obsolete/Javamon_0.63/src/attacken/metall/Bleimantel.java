package attacken.metall;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Typ;

public class Bleimantel extends Attacke{

    public Bleimantel(){
      super("Bleimantel",0,10,100,Typ.METALL,true);
      tooltip="Steigert die Verteidigung.";
    }
    
    @Override
    protected int effekt() {
      spiel.Kampf k=spiel.Javamon.getAktuellKampf();
      pokemon.Pokemon anwender;
      if(k.binDran()){
        anwender=k.getSpielers();
      }else{
        anwender=k.getGegners();
      }
      if(anwender.boostVert(2))Javamon.sout(anwender+"s Verteidigung steigt stark.");
      else Javamon.sout("Verteidigung kann nicht mehr erhöht werden");
      return dmg;
    }
}