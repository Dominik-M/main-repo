package pokemon;

import spiel.Typ;

public class Bisaknosp extends Pokemon{
    
    public Bisaknosp(int lvl){
      super("Bisaknosp",60,62,63,80,80,60,Typ.PFLANZE,Typ.GIFT,lvl);
      ability=spiel.Fähigkeit.IMMUNITÄT;
    }
    
    public Bisaknosp(Bisasam vor){
      super("Bisaknosp",60,62,63,80,80,60,Typ.PFLANZE,Typ.GIFT,vor);
    }

    @Override
    public Pokemon lvlUp() {
      if(getLevel()==2)lerne(new attacken.normal.Tackle());
      if(getLevel()==5)lerne(new attacken.normal.Heuler());
      if(getLevel()==8)lerne(new attacken.pflanze.Absorber());
      if(getLevel()==11)lerne(new attacken.pflanze.Wachstum());
      if(getLevel()==14)lerne(new attacken.pflanze.Rankenhieb());
      if(getLevel()==18)lerne(new attacken.pflanze.Giftpuder());
      if(getLevel()==21)lerne(new attacken.gift.Säure());
      if(getLevel()==24)lerne(new attacken.pflanze.Schlafpuder());
      if(getLevel()==28)lerne(new attacken.pflanze.Rasierblatt());
      if(getLevel()>=36)return new Bisaflor(this);
      return null;
    }
}