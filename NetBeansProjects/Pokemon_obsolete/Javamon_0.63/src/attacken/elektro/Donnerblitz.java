package attacken.elektro;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Status;
import spiel.Typ;

public class Donnerblitz extends Attacke{
    
    public Donnerblitz(){
      super("Donnerblitz",70,15,95,Typ.ELEKTRO,false);
    }

    @Override
    protected int effekt() {
      if(Math.random()*6<=1){
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