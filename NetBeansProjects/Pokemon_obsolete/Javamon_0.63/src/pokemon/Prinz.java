package pokemon;

import spiel.Typ;

public class Prinz extends Pokemon{
    
    public Prinz(int lvl){
      super("Der Prinz",44,95,68,35,32,112,Typ.KAMPF,Typ.NORMAL,lvl);
      bild=new javax.swing.ImageIcon(getClass().getResource("/pokemon/img/prinzIcon.png"));
      ability=spiel.Fähigkeit.FLEXIBILITÄT;
    }
    
    public Prinz(PrinzDunkel vor){
      super("Der Prinz",44,95,68,35,32,112,Typ.KAMPF,Typ.NORMAL,vor);
      bild=new javax.swing.ImageIcon(getClass().getResource("/pokemon/img/prinzIcon.png"));
    }

    @Override
    protected Pokemon lvlUp() {
      if(getLevel()==2) lerne(new attacken.kampf.Fußkick());
      if(getLevel()==5) lerne(new attacken.psycho.Doppelteam());
      if(getLevel()==12) lerne(new attacken.metall.Stahlklinge());
      if(getLevel()==16) lerne(new attacken.metall.Schwertertanz());
      if(getLevel()==22) lerne(new attacken.kampf.Zeitlupe());
      if(getLevel()==25) lerne(new attacken.boden.Sandsturm());
      if(getLevel()==31) lerne(new attacken.kampf.Wuchtschlag());
      if(getLevel()==40) lerne(new attacken.wasser.Wasserklinge());
      if(item!=null && item.typ==Typ.UNLICHT)return new PrinzDunkel(this);
      return null;
    }   
}