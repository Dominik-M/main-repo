package attacken.pflanze;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Typ;

public class Solarstrahl extends Attacke{
    private boolean geladen;
    
    public Solarstrahl(){
      super("Solarstrahl",120,5,95,Typ.PFLANZE,false);
      geladen=false;
      tooltip="Absorbiert Licht in Runde 1. Erzeugt in Runde 2 einen Energiestrahl.";
    }

    @Override
    protected int effekt() {
      if(geladen){
        geladen=false;
        return dmg;
      }else{
       Javamon.sout("Sonnenlicht sammelt sich.");
       ap++;
       geladen=true;
       return 0;   
      }
    }
    
    @Override
    public int getGena(){
      if(geladen)return gena;
      else return 1000;
    }
}