package attacken.elektro;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Status;
import spiel.Typ;

public class Donnerschock extends Attacke{

    public Donnerschock(){
      super("Donnerschock",40,30,95,Typ.ELEKTRO,false);
      tooltip="Stromschlag, der evtl. paralysiert.";
    }
    
    @Override
    public int effekt() {
      if(Math.random()*7<=1){
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
        }
      }
      return dmg;
    }
}