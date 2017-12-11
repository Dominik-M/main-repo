package attacken.pflanze;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Status;
import spiel.Typ;

public class Stachelspore extends Attacke{
    
    public Stachelspore(){
      super("Stachelspore",0,15,90,Typ.PFLANZE,false);
      tooltip="Versprüht paralysierende Sporen.";
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
        if(opfer.setStatus(Status.PAR))
        Javamon.sout(opfer+" wurde paralysiert.");
      }else if(opfer.getStatus()==Status.PAR){
        Javamon.sout(opfer+" ist bereits paralysiert.");
      }else{
        Javamon.sout("Die Attacke schlug fehl.");
      }
      return dmg;
    }
}