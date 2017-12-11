package pokemon;

import spiel.Typ;

public class PrinzDunkel extends Pokemon{
    
    public PrinzDunkel(int lvl){
      super("Dunkler Prinz",36,90,42,55,12,100,Typ.KAMPF,Typ.UNLICHT,lvl);
      bild=new javax.swing.ImageIcon(getClass().getResource("/pokemon/img/prinzdunkelIcon.png"));
    }
    
    public PrinzDunkel(Prinz vor){
      super("Dunkler Prinz",36,90,42,55,12,100,Typ.KAMPF,Typ.UNLICHT,vor);
      bild=new javax.swing.ImageIcon(getClass().getResource("/pokemon/img/prinzdunkelIcon.png"));
    }

    @Override
    protected Pokemon lvlUp() {
      if(getLevel()==2)lerne(new attacken.kampf.Fußkick());
      if(getLevel()==8)lerne(new attacken.boden.Sandwirbel());
      if(getLevel()==15)lerne(new attacken.boden.Sandsturm());
      if(getLevel()==21)lerne(new attacken.metall.Silberblick());
      if(getLevel()==29)lerne(new attacken.metall.Stahlklinge());
      if(getLevel()==35)lerne(new attacken.metall.Schwertertanz());
      if(getLevel()==41)lerne(new attacken.unlicht.Verfolgung());
      if(item!=null && item.typ==Typ.WASSER)return new Prinz(this);
      return null;
    }
    
}