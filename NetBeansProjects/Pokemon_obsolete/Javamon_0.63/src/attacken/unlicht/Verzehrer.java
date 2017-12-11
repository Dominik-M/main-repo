package attacken.unlicht;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Typ;

public class Verzehrer extends Attacke{
  
  public Verzehrer(){
    super("Verzehrer",999,5,35,Typ.UNLICHT,true);
    tooltip="K.O.-Attacke: Verschlingt den Gegner";
  }

    @Override
    protected int effekt() {
      spiel.Kampf k=Javamon.getAktuellKampf();
      if(k.binDran()){
        k.getGegners().nimmSchaden(k.getGegners().getKP());
      }else{
        k.getSpielers().nimmSchaden(k.getSpielers().getKP());
      }
      Javamon.sout("K.O. Treffer!");
      return dmg;
    }
}