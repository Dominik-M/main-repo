package pokemon;

import spiel.Typ;

public class Tauboga extends Pokemon{
    
    public Tauboga(int lvl){
      super("Tauboga",63,60,55,40,40,71,Typ.FLUG,Typ.NORMAL,lvl);
      ability=spiel.Fähigkeit.ADLERAUGE;
    }
    
    public Tauboga(Taubsi vor){
      super("Tauboga",63,60,55,50,50,71,Typ.FLUG,Typ.NORMAL,vor);
    }

    @Override
    protected Pokemon lvlUp() {
      if(getLevel()==2)lerne(new attacken.flug.Windstoß());
      if(getLevel()==24)lerne(new attacken.flug.Flügelschlag());
      if(getLevel()==34)lerne(new attacken.drache.Windhose());
      if(getLevel()>=31)return new Tauboss(this);
      return null;
    }
}