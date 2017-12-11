package attacken.psycho;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Typ;

public class Konfusion extends Attacke{
    
    public Konfusion(){
      super("Konfusion",40,20,95,Typ.PSYCHO,false);
    }

    @Override
    protected int effekt() {
      if(Math.random()*5<=1){
        spiel.Kampf k=Javamon.getAktuellKampf();
        pokemon.Pokemon opfer;
        if(k.binDran()){
          opfer=k.getGegners();
        }else{
          opfer=k.getSpielers();
        }
        if(!opfer.isConfused()){
          opfer.confuse();
          if(opfer.isConfused()){
            Javamon.sout(opfer+" wurde verwirrt.");
          }
        }
      }
      return dmg;
    }
}