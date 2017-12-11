package attacken.normal;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Typ;

public class Hyperstrahl extends Attacke{
    private pokemon.Pokemon anwender;
    
    public Hyperstrahl(pokemon.Pokemon pok){
      super("Hyperstrahl",120,5,95,Typ.NORMAL,pok.getSpezAtk()<pok.getAtk());
      tooltip="Strahl aus reiner Energie, verbraucht viel Kraft";
      anwender=pok;
    }

    @Override
    protected int effekt() {
      boolean ging= anwender.boostSpezAtk(-2);
      if(ging)Javamon.sout(anwender+"s Spezialangriff sinkt stark.");
      return dmg;
    }
}