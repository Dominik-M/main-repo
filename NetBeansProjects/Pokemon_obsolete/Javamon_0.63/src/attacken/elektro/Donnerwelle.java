package attacken.elektro;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Status;
import spiel.Typ;

public class Donnerwelle extends Attacke{
    
    public Donnerwelle(){
      super("Donnerwelle",0,20,95,Typ.ELEKTRO,false);
      tooltip="Paralysiert den Gegner.";
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