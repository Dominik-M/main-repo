package monopoly;

import java.awt.Color;

public class Strasse extends Feld {
  int miete;
  int mieteNormal;
  int kosten;
  int hauskosten=0;
  int hausanz=0;
  Spieler besitzer=null;
  Color farbeNormal;
  
  // default Constructor
  public Strasse(String n,int wo,int wert,Color farb){
    super(n,wo,farb);
    if(wo%10==5){
      kosten=4000;
      miete=500;
    }else if(wo==12 || wo==28){
      kosten=3000;
      miete=300*((int)(Math.random()*5+1));
    }else{
      miete=wert/10;
      hauskosten=wert/2;
      kosten=wert;
    }
    mieteNormal=miete;
    farbeNormal=farbe;
  }
  
  // manueller Constructor
  public Strasse(String n,int wo,int wert,int hauskost,int miet,Color farb){
    super(n,wo,farb);
    kosten=wert;
    miete=miet;
    if(wo==12 || wo==28){
      miete=miet*((int)(Math.random()*10+1));
    }
    hauskosten=hauskost;
    mieteNormal=miete;
    farbeNormal=farbe;
  }
  
  public void hausbau(){
    if(!aktiv || hauskosten==0){ Spielfeld.output.append("Hausbau hier nicht möglich"+"\n"); }
    else if(besitzer.geld<hauskosten){ Spielfeld.output.append("Zu wenig Geld "+"\n"); }
    else if(hausanz>=5){Spielfeld.output.append("Die Strasse ist voll! \n");
    }
    else{
      besitzer.zahle(hauskosten);
      miete=miete+mieteNormal*4;
      hausanz++;
      Spielfeld.output.append(besitzer+" baut ein Haus auf "+name+"\n");
    }
  }
  
  public void hypothek(Spieler zahler){
    if(aktiv){
      if(!zahler.equals(besitzer)){
        Spielfeld.output.append("Die Strasse gehört einem anderen"+"\n");
        return;
      }
      Spielfeld.output.append(besitzer+" nimmt eine Hypothek für "+this+" auf."+"\n");
      Spielfeld.output.append(this+" ist jetzt belastet mit "+kosten/2+"\n");
      aktiv=false;
      besitzer.zahle(kosten/-2);
    }else{
      if(zahler.geld<kosten/2){ Spielfeld.output.append("Zu wenig Geld"+"\n"); }
      Spielfeld.output.append(zahler+" zahlt die Hypothek von "+this+" ab."+"\n");
      aktiv=true;
      zahler.zahle(kosten/2);
    }
  }
}
