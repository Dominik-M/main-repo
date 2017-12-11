package attacken.pflanze;

import attacken.Attacke;
import spiel.Typ;

public class Absorber extends Attacke{

    public Absorber(){
      super("Absorber",20,25,90,Typ.PFLANZE,false);
      tooltip="Absorbiert KP des Gegners";
    }
    
    @Override
    protected int effekt() {
      spiel.Kampf k=spiel.Javamon.getAktuellKampf();
      pokemon.Pokemon anwender;
      if(k.binDran()){
        anwender=k.getSpielers();
      }else{
        anwender=k.getGegners();
      }
      anwender.nimmSchaden(-anwender.getMaxKP()/12);
      return dmg;
    }   
}