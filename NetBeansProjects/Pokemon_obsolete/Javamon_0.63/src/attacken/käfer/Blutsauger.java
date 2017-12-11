package attacken.käfer;

import attacken.Attacke;
import spiel.Javamon;
import spiel.Typ;

public class Blutsauger extends Attacke{
    
    public Blutsauger(){
      super("Blutsauger",35,20,90,Typ.KÄFER,true);
      tooltip="Enzieht dem Gegner die Lebensenergie";
    }

    @Override
    protected int effekt() {
      spiel.Kampf k=spiel.Javamon.getAktuellKampf();
      pokemon.Pokemon opfer;
      pokemon.Pokemon anwender;
      if(k.binDran()){
        opfer=k.getGegners();
        anwender=k.getSpielers();
      }else{
        opfer=k.getSpielers();
        anwender=k.getGegners();
      }
      double multi1=Javamon.MULTI[Javamon.TYPENANZ*opfer.getTyp1().index+
                    this.getTyp().index];
            double multi2=Javamon.MULTI[Javamon.TYPENANZ*opfer.getTyp2().index+
                    this.getTyp().index];
            double multi=multi1*multi2;
      anwender.nimmSchaden((int)(-opfer.getKP()/10*multi));
      return dmg;
    }
}