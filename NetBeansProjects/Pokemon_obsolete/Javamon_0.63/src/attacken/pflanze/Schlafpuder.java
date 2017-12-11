package attacken.pflanze;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Status;
import spiel.Typ;

public class Schlafpuder extends Attacke{

    public Schlafpuder(){
      super("Schlafpuder",0,15,80,Typ.PFLANZE,false);
      tooltip="Schläfert den Gegner ein";
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
      if(opfer.getStatus()==Status.SLF){
        Javamon.sout(opfer+" schläft bereits.");
      }else if(opfer.getStatus()==Status.OK){
        if(opfer.setStatus(Status.SLF))
        Javamon.sout(opfer+" ist eingeschlafen.");
      }else{
        Javamon.sout("Die Attacke schlug fehl.");
      }
      return dmg;
    }
}