package pokemon;

import spiel.Typ;

public class Karpador extends Pokemon{
    
    public Karpador(int lvl){
      super("Karpador",25,15,55,15,30,70,Typ.WASSER,Typ.DRACHE,lvl);
      ability=spiel.Fähigkeit.EXPIDERMIS;
    }

    @Override
    protected Pokemon lvlUp() {
      if(getLevel()==2)lerne(new attacken.wasser.Platscher());
      if(getLevel()==10)lerne(new attacken.normal.Tackle());
      if(getLevel()>=20)return new Garados(this);
      return null;
    }
}