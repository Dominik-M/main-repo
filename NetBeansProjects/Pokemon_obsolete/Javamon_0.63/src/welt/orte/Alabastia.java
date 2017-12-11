package welt.orte;

import spiel.Javamon;
import spiel.Status;
import welt.items.*;
import welt.objekte.Objekt;

public class Alabastia extends Ort{
  
  public Alabastia(){
    super("Alabastia",29,30);
    standart=Objekt.WIESE;
    background=Objekt.BODEN;
    if(Javamon.MAKE_FILES)tools.OrtComponent.writeOrt(this, new java.io.File(name+".ort"));
  }
  
    @Override
    public void erstelle() {
      for(int x=0;x<objekte.length;x++){
        for(int y=0;y<objekte[x].length;y++){
          if(x==0||(y<=1&&(x<=11||x>=15))||x==objekte.length-1){
            objekte[x][y]=Objekt.STEIN;
          }else if((x>=12&&x<=14)||y>=12&&y<=14){
            objekte[x][y]=Objekt.WEG;
          }else{
            objekte[x][y]=Objekt.BODEN;
          }
        }
      }
      for(int x=2;x<=10;x++){
        for(int y=18;y<=23;y++)objekte[x][y]=new welt.objekte.Wiese(x>=4&&x<=8&&y>=20&&y<=21);
      }
      objekte[5][17]=new welt.objekte.Schild(name);
      objekte[1][24]=Objekt.MOOS;
      for(int x=2;x<=10;x++)objekte[x][24]=Objekt.STUFE_UNTEN;
      objekte[11][24]=Objekt.MOOS;
      objekte[12][0]=new welt.objekte.Stein(new Artefakt());
      objekte[14][0]=Objekt.STEIN;
      objekte[13][0]=new welt.objekte.Tür(new Route1(this),9,39,1);
      Haus haus=zeichneHaus(this,3,4,7,8,true);
      haus.objekte[1][0]=new welt.objekte.Stein(new Trank(50));
      haus.objekte[5][0]=new welt.objekte.Stein(new TM(new attacken.eis.Frostschock()));
      for(int x=2;x<=4;x++){
          haus.objekte[x][0]=Objekt.HEILFLÄCHE;
          haus.objekte[x][1]=Objekt.HEILFLÄCHE;
          haus.objekte[x][2]=Objekt.HEILFLÄCHE;
      }
      haus.objekte[4][3]=new welt.objekte.Person(spiel.Trainer.LEFT,"Hier kannst du dich erholen!");
      haus=zeichneHaus(this,17,4,7,8,true);
      haus.objekte[0][1]=new welt.objekte.Markt(1,
          new Trank(50),new Trank(Trank.SUPER),
          new StatBonus(Javamon.ANGR),new StatBonus(Javamon.VERT),
          new StatBonus(Javamon.SPEZANGR),new StatBonus(Javamon.SPEZVERT),
          new StatBonus(Javamon.INIT),new StatBonus(Javamon.KP),
          new Heiler(),new Heiler(Status.BRT),
          new Heiler(Status.GFR),new Heiler(Status.GFT),
          new Heiler(Status.PAR),new Heiler(Status.SLF),
          new Beleber(2),new Sonderbonbon(),
          new Entwickler(spiel.Typ.FEUER),new Entwickler(spiel.Typ.DRACHE)
      );
      haus.objekte[0][3]=new welt.objekte.Münzhändler(1);
      haus.objekte[0][4]=new welt.objekte.Münztauscher(1,
              new TM(new attacken.elektro.Donnerwelle()),
              new TM(new attacken.metall.Silberblick()),
              new TM(new attacken.psycho.Doppelteam()),
              new TM(new attacken.psycho.Konfusion()),
              new TM(new attacken.elektro.Donnerblitz()));
      haus.objekte[3][0]=Objekt.AUTOMAT;
      haus.objekte[1][0]=new welt.objekte.Automat(400);
      haus.objekte[2][0]=new welt.objekte.Automat(200);
      haus.objekte[4][0]=new welt.objekte.Automat(50);
      haus.objekte[5][0]=new welt.objekte.Automat(25);
      haus=zeichneHaus(this,"Eichs Labor",16,15,10,8);
      haus.objekte[5][0]=new welt.objekte.Box();
      haus.objekte[7][0]=new welt.objekte.Person(
            spiel.Trainer.DOWN,new Schlüssel(1),
            "Wenn du zur Route1 gehen möchtest, #brauchst du diesen Schlüssel!",
              "Viel Glück!");
      objekte[8][16]=new welt.objekte.Person(spiel.Trainer.DOWN,"Hallo!",
              "Wie gehts?","Die meisten Leute sind sehr unhöflich #und wollen"
              + " dich mit ihren Pokemon töten.","Sei auf der Hut!");
    }
    
    @Override
  public Objekt getObjekt(int x,int y){
    Objekt o;
    try{
      o=objekte[x][y];
      if(o==null)o=standart;
    }
    catch(Exception ex){
      if(y>=29&&x>0&&x<28)o=Objekt.WASSER;
      else o=standart;
    }
    return o;
  }

    @Override
    public int getStartX() {
      return 6;
    }

    @Override
    public int getStartY() {
      return 12;
    }
}