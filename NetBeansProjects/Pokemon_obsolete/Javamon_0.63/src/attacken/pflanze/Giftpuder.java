package attacken.pflanze;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Status;
import spiel.Typ;

public class Giftpuder extends Attacke{
    
    public Giftpuder(){
      super("Giftpuder",0,15,90,Typ.PFLANZE,false);
      tooltip="Stößt giftiges Puder aus.";
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
        if(opfer.setStatus(Status.GFT))
        Javamon.sout(opfer+" wurde vergiftet.");
      }else if(opfer.getStatus()==Status.GFT){
        Javamon.sout(opfer+" ist bereits vergiftet.");
      }else{
        Javamon.sout("Die Attacke schlug fehl.");
      }
      return dmg;
    }
}