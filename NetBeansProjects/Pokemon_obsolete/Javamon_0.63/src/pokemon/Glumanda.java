package pokemon;

import spiel.Typ;

public class Glumanda extends Pokemon{
  
  public Glumanda(int lvl){
    super("Glumanda",39,65,43,60,50,65,Typ.FEUER,Typ.FEUER,lvl);
    ability=spiel.Fähigkeit.FEUERPANZER;
  }
  
  @Override
  public Pokemon lvlUp(){
    if(getLevel()==2) lerne(new attacken.normal.Kratzer());
    if(getLevel()==5) lerne(new attacken.normal.Heuler());
    if(getLevel()==8) lerne(new attacken.feuer.Glut());
    if(getLevel()==10) lerne(new attacken.metall.Silberblick());
    if(getLevel()==13) lerne(new attacken.metall.Kupferklaue());
    if(getLevel()==18) lerne(new attacken.feuer.Rauchwolke());
    if(getLevel()==26) lerne(new attacken.feuer.Flammenwurf());
    if(getLevel()>=16){
      //Entwicklung zu Glutexo
      return new Glutexo(this);
    }
    return null;  
  }
}