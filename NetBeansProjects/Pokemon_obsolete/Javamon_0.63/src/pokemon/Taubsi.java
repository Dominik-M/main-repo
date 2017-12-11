package pokemon;

import spiel.Typ;

public class Taubsi extends Pokemon{
    
    public Taubsi(int lvl){
      super("Taubsi",40,45,40,30,30,56,Typ.FLUG,Typ.NORMAL,lvl);
      ability=spiel.Fähigkeit.ADLERAUGE;
    }

    @Override
    public Pokemon lvlUp() {
      if(getLevel()==2)lerne(new attacken.flug.Windstoß());
      if(getLevel()==5)lerne(new attacken.boden.Sandwirbel());
      if(getLevel()==9)lerne(new attacken.psycho.Doppelteam());
      if(getLevel()==18)lerne(new attacken.drache.Windhose());
      if(getLevel()>=16)return new Tauboga(this);
      return null;
    }
}