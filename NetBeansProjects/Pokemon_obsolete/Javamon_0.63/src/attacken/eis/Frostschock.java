package attacken.eis;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Status;
import spiel.Typ;

public class Frostschock extends Attacke{
    
    public Frostschock(){
      super("Frostschock",10,5,70,Typ.EIS,false);
      tooltip="Friert den Gegner ein.";
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
      if(opfer.getStatus()==Status.OK){
        if(opfer.setStatus(Status.GFR))
        Javamon.sout(opfer+" wurde eingefroren.");
      }else if(opfer.getStatus()==Status.GFR){
        Javamon.sout(opfer+" ist bereits eingefroren.");
      }else{
        Javamon.sout("Die Attacke schlug fehl.");
      }
      return dmg;
    }
}