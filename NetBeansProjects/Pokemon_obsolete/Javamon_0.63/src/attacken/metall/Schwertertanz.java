package attacken.metall;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Typ;

public class Schwertertanz extends Attacke{
    
    public Schwertertanz(){
      super("Schwertertanz",0,15,100,Typ.METALL,true);
      tooltip="Schärft die Klingen, Angriff steigt deutlich";
    }

    @Override
    protected int effekt() {
      spiel.Kampf k=spiel.Javamon.getAktuellKampf();
      pokemon.Pokemon anwender;
      if(k.binDran())anwender=k.getSpielers();
      else anwender=k.getGegners();
      boolean ging= anwender.boostAtk(2);
      if(ging)Javamon.sout(anwender+"s Angriff setigt stark.");
      else Javamon.sout("Angriff kann nicht mehr erhöht werden.");
      return dmg;
    }
}