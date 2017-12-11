package attacken.pflanze;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Typ;

public class Wachstum extends Attacke{

    public Wachstum(){
      super("Wachstum",0,25,100,Typ.PFLANZE,false);
      tooltip="Steigert Spezial-Werte.";
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
      boolean ging=anwender.boostSpezAtk(1)&&anwender.boostSpezVert(1);
      if(ging)Javamon.sout(anwender+"s Spezial steigt.");
      else Javamon.sout("Spezial kann nicht mehr erhöht werden.");
      return dmg;
    }
}