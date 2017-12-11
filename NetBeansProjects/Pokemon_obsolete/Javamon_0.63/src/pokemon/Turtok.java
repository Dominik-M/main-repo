package pokemon;

import spiel.Typ;

public class Turtok extends Pokemon {
    
  public Turtok(int lvl){
    super("Turtok",79,73,120,85,90,68,Typ.WASSER,Typ.WASSER,lvl);
    ability=spiel.Fähigkeit.AQUAHÜLLE;
  }

  public Turtok(Schillok vor) {
    super("Turtok",79,73,120,85,90,68,Typ.WASSER,Typ.WASSER,vor);
  }

    @Override
    public Pokemon lvlUp() {
      if(getLevel()==2)lerne(new attacken.normal.Tackle());
      if(getLevel()==8)lerne(new attacken.wasser.Blubber());
      if(getLevel()==18)lerne(new attacken.wasser.Tsunami());
      if(getLevel()==40)lerne(new attacken.wasser.Hydropumpe());
      return null;
    } 
}