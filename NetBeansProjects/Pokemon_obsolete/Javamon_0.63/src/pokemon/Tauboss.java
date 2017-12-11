package pokemon;

import spiel.Typ;

public class Tauboss extends Pokemon{
    
    public Tauboss(int lvl){
      super("Tauboss",83,80,75,60,60,101,Typ.FLUG,Typ.NORMAL,lvl);
      ability=spiel.Fähigkeit.ADLERAUGE;
    }
    
    public Tauboss(Tauboga vor){
      super("Tauboss",83,80,75,60,60,101,Typ.FLUG,Typ.NORMAL,vor);
    }

    @Override
    protected Pokemon lvlUp() {
      if(getLevel()==2)lerne(new attacken.flug.Windstoß());
      if(getLevel()==12)lerne(new attacken.psycho.Doppelteam());
      if(getLevel()==22)lerne(new attacken.flug.Flügelschlag());
      if(getLevel()==32)lerne(new attacken.unlicht.Verfolgung());
      if(getLevel()==42)lerne(new attacken.drache.Windhose());
      return null;
    }
}