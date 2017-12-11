package attacken.metall;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Typ;

public class Kupferklaue extends Attacke{
    
    public Kupferklaue(){
      super("Kupferklaue",40,35,90,Typ.METALL,true);
      tooltip="Steigert evtl. die Angriffskraft.";
    }

    @Override
    protected int effekt() {
      if(Math.random()*9<=3){
        spiel.Kampf k=spiel.Javamon.getAktuellKampf();
        pokemon.Pokemon anwender;
        if(k.binDran())anwender=k.getSpielers();
        else anwender=k.getGegners();
        anwender.boostAtk(1);
        Javamon.sout("Angriff von "+anwender+" steigt.");
      }
      return dmg;
    }
}