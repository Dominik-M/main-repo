package pokemon;

import spiel.Typ;

public class Bisasam extends Pokemon{
    
    public Bisasam(int lvl){
      super("Bisasam",45,52,52,65,65,40,Typ.PFLANZE,Typ.GIFT,lvl);
      ability=spiel.Fähigkeit.IMMUNITÄT;
    }

    @Override
    public Pokemon lvlUp() {
      if(getLevel()==2)lerne(new attacken.normal.Tackle());
      if(getLevel()==5)lerne(new attacken.normal.Heuler());
      if(getLevel()==7)lerne(new attacken.pflanze.Absorber());
      if(getLevel()==10)lerne(new attacken.pflanze.Wachstum());
      if(getLevel()==12)lerne(new attacken.pflanze.Rankenhieb());
      if(getLevel()==15)lerne(new attacken.pflanze.Stachelspore());
      if(getLevel()==18)lerne(new attacken.pflanze.Giftpuder());
      if(getLevel()==21)lerne(new attacken.gift.Säure());
      if(getLevel()==24)lerne(new attacken.pflanze.Schlafpuder());
      if(getLevel()>=16)return new Bisaknosp(this);
      return null;
    }
}