package attacken.feuer;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Status;
import spiel.Typ;

public class Rauchwolke extends Attacke{

    public Rauchwolke(){
      super("Rauchwolke",0,25,100,Typ.FEUER,false);
      tooltip="Beißender Rauch vernebelt die Sicht";
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
      if(opfer.boostGena(-1))Javamon.sout(opfer+"s Genauigkeit sinkt.");
      else Javamon.sout("Genauigkeit kann nicht mehr gesenkt werden.");
      if(opfer.getStatus()==Status.OK&&Math.random()*9<=1){
        opfer.setStatus(Status.BRT);
        Javamon.sout(opfer+" fängt Feuer.");
      }
      return dmg;
    }
}