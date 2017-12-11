package pokemon;

import spiel.Typ;


public class Glurak extends Pokemon{
   
    public Glurak(int lvl){
      super("Glurak",78,84,78,109,85,100,Typ.FEUER,Typ.FLUG,lvl);
      ability=spiel.Fähigkeit.FEUERPANZER;
    }
    
    Glurak(Glutexo vor){
      super("Glurak",78,84,78,109,85,100,Typ.FEUER,Typ.FLUG,vor);
    }
    
  @Override
  public Pokemon lvlUp() {
    if(getLevel()==2)lerne(new attacken.normal.Kratzer());
    if(getLevel()==6)lerne(new attacken.normal.Heuler());
    if(getLevel()==12)lerne(new attacken.feuer.Glut());
    if(getLevel()==16)lerne(new attacken.metall.Silberblick());
    if(getLevel()==20)lerne(new attacken.metall.Eisenschweif());
    if(getLevel()==26)lerne(new attacken.feuer.Rauchwolke());
    if(getLevel()==38)lerne(new attacken.flug.Flügelschlag());
    if(getLevel()==42)lerne(new attacken.feuer.Flammenwurf());
    if(getLevel()==49)lerne(new attacken.drache.Windhose());
    if(item!=null){
      if(item.typ==Typ.DRACHE){
        item=null;
        return new MegaGlurakX(this);
      }
      else if(item.typ==Typ.FEUER){
        item=null;
        return new MegaGlurakY(this);
      }
    }
    return null;
  }    
}
