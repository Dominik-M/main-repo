package pokemon;

import spiel.Typ;

public class Glutexo extends Pokemon{
 
    public Glutexo(int lvl){
      super("Glutexo",58,64,58,80,65,80,Typ.FEUER,Typ.FEUER,lvl);
      ability=spiel.Fähigkeit.FEUERPANZER;
    }
  
    Glutexo(Glumanda vor){
      super("Glutexo",58,64,58,80,65,80,Typ.FEUER,Typ.FEUER,vor);
    }

  @Override
  public Pokemon lvlUp() {
    if(getLevel()==2)lerne(new attacken.normal.Tackle());
    if(getLevel()==5)lerne(new attacken.normal.Heuler());
    if(getLevel()==11)lerne(new attacken.feuer.Glut());
    if(getLevel()==13) lerne(new attacken.metall.Silberblick());
    if(getLevel()==15) lerne(new attacken.metall.Kupferklaue());
    if(getLevel()==18) lerne(new attacken.feuer.Rauchwolke());
    if(getLevel()==23)lerne(new attacken.metall.Eisenschweif());
    if(getLevel()==38)lerne(new attacken.unlicht.Verfolgung());
    if(getLevel()==42) lerne(new attacken.feuer.Flammenwurf());
    if(getLevel()>=36){
      //Entwicklung zu Glurak
      return new Glurak(this);
    }
    return null;
  }
}