package attacken.wasser; 

import attacken.Attacke;
import spiel.Typ;

public class Tsunami extends Attacke{
  private int ladung;
  
  public Tsunami(){
    super("Tsunami",50,10,80,Typ.WASSER,false);
    tooltip="Angriff über 2 Runden. Wird bei mehrfacher Anwendung stärker.";
  }

    @Override
    public int effekt() {
      ladung++;
      if(ladung%2==0){
        return ladung/2*dmg;
      }else{
        spiel.Javamon.sout("Das Wasser zieht sich zurück...");
        return 0;
      }
    }
    
    @Override
    public void resetAp(){
      ap=maxAp;
      ladung=0;
    }
}
