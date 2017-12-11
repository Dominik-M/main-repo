package attacken.feuer;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Status;
import spiel.Typ;

public class Glut extends Attacke{

    public Glut(){
      super("Glut",60,20,95,Typ.FEUER,false);
    }
    
    @Override
    public int effekt() {
      spiel.Kampf k=spiel.Javamon.getAktuellKampf();
      pokemon.Pokemon opfer;
      if(k.binDran()){
        opfer=k.getGegners();
      }else{
        opfer=k.getSpielers();
      }
      if(opfer.getStatus()==Status.OK&&Math.random()*7<=1){
        if(opfer.setStatus(Status.BRT))
        Javamon.sout(opfer+" brennt.");
      }
      return dmg;
    } 
}