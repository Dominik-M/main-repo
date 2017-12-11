package pokemon;

import spiel.Fähigkeit;
import spiel.Typ;

public class Dahaka extends Pokemon{

    public Dahaka(int lvl){
      super("Dahaka",375,62,26,28,135,60,Typ.GEIST,Typ.BODEN,lvl);
      bild=new javax.swing.ImageIcon(getClass().getResource("/pokemon/img/dahakaIcon.jpg"));
      ability=Fähigkeit.WUNDERWACHE;
    }
    
    @Override
    public Pokemon lvlUp() {
      if(getLevel()==2)lerne(new attacken.unlicht.Verfolgung());
      if(getLevel()==7)lerne(new attacken.psycho.Doppelteam());
      if(getLevel()==13)lerne(new attacken.pflanze.Rankenhieb());
      if(getLevel()==18)lerne(new attacken.gestein.Steinwurf());
      if(getLevel()==24)lerne(new attacken.boden.Erdbeben());
      if(getLevel()==32)lerne(new attacken.unlicht.Verzehrer());
      return null;
    }
}