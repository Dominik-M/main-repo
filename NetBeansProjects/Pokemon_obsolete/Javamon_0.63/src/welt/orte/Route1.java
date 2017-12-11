package welt.orte;

import spiel.Javamon;
import welt.objekte.Objekt;
import welt.objekte.Stein;
import welt.objekte.TrainerObjekt;

public class Route1 extends Ort {

    public Route1(Alabastia vor) {
      super("Route 1",19,40);
      objekte[9][39]=new welt.objekte.Tür(vor,13,0);
      standart=Objekt.WIESE;
      background=Objekt.WEG;
      if(Javamon.MAKE_FILES)tools.OrtComponent.writeOrt(this, new java.io.File(name+".ort"));
    }

    @Override
    public void erstelle() {
      if(objekte[9][33]!=null && ((TrainerObjekt)objekte[9][33]).isBesiegt()){
        objekte[9][33]=Objekt.WEG;
        for(int x=1;x<=6;x++)objekte[x][29]=Objekt.GRAS;
        objekte[8][33].setTrainer(null);
        objekte[10][33].setTrainer(null);
        poks.add(new pokemon.Rattfratz(2));
        poks.add(new pokemon.Rattfratz(3));
        poks.add(new pokemon.Rattfratz(4));
        poks.add(new pokemon.Rattfratz(5));
        poks.add(new pokemon.Taubsi(2));
        poks.add(new pokemon.Taubsi(3));
        poks.add(new pokemon.Taubsi(4));
        poks.add(new pokemon.Taubsi(5));
        return;
      }
      Stein s=Objekt.STEIN;
      for(int y=0;y<40;y++){
        objekte[0][y]=s;
        objekte[18][y]=s;
      }
      for(int x=1;x<18;x++){
        if(x==1)objekte[x][34]=new Stein(new welt.items.StatBonus(Javamon.KP));
        else objekte[x][34]=Objekt.STEIN;
      }
      for(int y=34;y<=38;y++){
        objekte[7][y]=Objekt.STEIN;
        objekte[8][y]=Objekt.GRAS;
        objekte[9][y]=Objekt.GRAS;
        objekte[10][y]=Objekt.GRAS;
        objekte[11][y]=Objekt.STEIN;
      }
      for(int x=11;x<=14;x++){
        objekte[x+3][30]=Objekt.GRAS;
        objekte[x+3][31]=Objekt.GRAS;
        objekte[x][32]=Objekt.GRAS;
        objekte[x][33]=Objekt.GRAS;
      }
      for(int x=1;x<=7;x++){
        for(int y=26;y<=32;y++)objekte[x][y]=Objekt.GRAS;
      }
      for(int x=1;x<=17;x++){
        if(x>=8&&x<=13)objekte[x][29]=Objekt.STUFE_UNTEN;
        else objekte[x][29]=Objekt.MOOS;
      }
      for(int x=8;x<=10;x++)for(int y=30;y<=33;y++)
          objekte[x][y]=new welt.objekte.Weg();
      for(int x=7;x<=11;x++)objekte[x][39]=Objekt.STEIN;
      for(int x=8;x<=17;x++){
        objekte[x][27]=Objekt.WEG;
        objekte[x][28]=Objekt.WEG;
      }
      for(int y=22;y<=26;y++){
        objekte[16][y]=Objekt.WEG;
        objekte[17][y]=Objekt.WEG;
      }
      objekte[16][23]=Objekt.STUFE_UNTEN;
      objekte[17][23]=Objekt.STUFE_UNTEN;
      for(int x=1;x<=10;x++)objekte[x][23]=Objekt.MOOS;
      for(int x=11;x<=15;x++)for(int y=22;y<=26;y++)objekte[x][y]=Objekt.GRAS;
      for(int x=3;x<=17;x++){
        objekte[x][20]=Objekt.WEG;
        objekte[x][21]=Objekt.WEG;
      }
      for(int x=1;x<=17;x++)objekte[x][19]=Objekt.STUFE_UNTEN;
      for(int x=3;x<=8;x++)objekte[x][12]=Objekt.STUFE_UNTEN;
      objekte[1][12]=Objekt.STEIN;
      objekte[2][12]=Objekt.STEIN;
      objekte[9][12]=Objekt.STEIN;
      objekte[10][12]=Objekt.STEIN;
      objekte[11][12]=Objekt.STEIN;
      objekte[12][12]=Objekt.STEIN;
      objekte[3][17]=Objekt.WEG;
      objekte[4][17]=Objekt.WEG;
      objekte[3][18]=Objekt.WEG;
      objekte[4][18]=Objekt.WEG;
      objekte[3][19]=Objekt.WEG;
      for(int x=3;x<=12;x++){
        objekte[x][15]=Objekt.WEG;
        objekte[x][16]=Objekt.WEG;
      }
      for(int x=13;x<=17;x++)for(int y=11;y<=16;y++)objekte[x][y]=Objekt.GRAS;
      objekte[11][14]=Objekt.WEG;
      objekte[12][14]=Objekt.WEG;
      objekte[11][13]=Objekt.WEG;
      objekte[12][13]=Objekt.WEG;
      objekte[11][11]=Objekt.WEG;
      objekte[12][11]=Objekt.WEG;
      for(int x=11;x<=17;x++){
        objekte[x][10]=Objekt.WEG;
        objekte[x][9]=Objekt.WEG;
      }
      for(int x=9;x<=17;x++)for(int y=5;y<=8;y++)objekte[x][y]=Objekt.GRAS;
      for(int x=1;x<=12;x++){
        objekte[x][5]=Objekt.STUFE_UNTEN;
        if(x<8)objekte[x][8]=Objekt.STUFE_UNTEN;
      }
      for(int y=4;y<=8;y++)objekte[8][y]=Objekt.MOOS;
      for(int x=9;x<=17;x++){
        objekte[x][4]=Objekt.WEG;
        objekte[x][3]=Objekt.WEG;
      }
      for(int x=1;x<=17;x++)objekte[x][1]=Objekt.STEIN;
      objekte[8][0]=Objekt.STEIN;
      objekte[11][0]=Objekt.STEIN;
      objekte[9][2]=Objekt.WEG;
      objekte[10][2]=Objekt.WEG;
      objekte[9][1]=Objekt.WEG;
      objekte[10][1]=Objekt.WEG;
      welt.orte.VertaniaCity vertania=new VertaniaCity(this);
      objekte[9][0]=new welt.objekte.Tür(vertania,19,39);
      objekte[10][0]=new welt.objekte.Tür(vertania,20,39);
      TrainerObjekt rivale=new TrainerObjekt(Javamon.getRivale());
      rivale.setColor(welt.objekte.FarbPalette.VIOLETT);
      objekte[9][33]=rivale;
      objekte[8][33].setTrainer(rivale);
      objekte[10][33].setTrainer(rivale);
      objekte[3][14]=new welt.objekte.Person(spiel.Trainer.DOWN,
              new welt.items.StatBonus(Javamon.ANGR),"Hallo!#"
              + " Ich arbeite im Markt von Vertania City.#Warum gebe ich dir nicht"
              + " einfach ein Werbegeschenk?!","Bis dann!");
      spiel.Trainer t=new spiel.Trainer("Angler Berthold",1000,spiel.Trainer.LEFT,
              new pokemon.Karpador(5),new pokemon.Karpador(10));
      t.setDialog1("Sieh mal was ich an Land gezogen habe!");
      t.setDialog2("Der große Fang war noch nicht dabei.");
      objekte[11][15]=new TrainerObjekt(t);
    }
    
    @Override
    public Objekt getObjekt(int x,int y){
      if(y>=40)return Objekt.STEIN;
      Objekt o;
      try{
        o=objekte[x][y];
        if(o==null)o=standart;
      }
      catch(Exception ex){o=standart;}
      return o;
    }

    @Override
    public int getStartX() {
        return 9;
    }

    @Override
    public int getStartY() {
        return 38;
    }
}