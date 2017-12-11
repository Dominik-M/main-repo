package attacken.feuer;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Status;
import spiel.Typ;

public class Flammenwurf extends Attacke{
    
    public Flammenwurf(){
      super("Flammenwurf",70,15,95,Typ.FEUER,false);
      tooltip="Speiht Feuer. Verursacht evtl. Brand.";
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
      if(opfer.getStatus()==Status.OK&&Math.random()*6<=1){
        if(opfer.setStatus(Status.BRT))
        Javamon.sout(opfer+" brennt.");
      }
      return dmg;
    }
}