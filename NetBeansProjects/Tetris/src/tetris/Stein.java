package tetris;

import java.awt.Color;

public class Stein {
  Color farbe;
  Spot c;
  Spot[] spots;
    
  public Stein(Color f,Spot dreh, Spot[] rest){
    farbe=f;
    c=dreh;
    spots=rest;
  }
  
  public Stein(Color f,int drehx, int drehy, int... xy){
    farbe=f;
    c=new Spot(drehx,drehy);
    spots=Spot.arrayAus(xy);
  }
  
  public Spot[] spots(){
    Spot[] alle=new Spot[spots.length+1];
    alle[0]=c;
    int i=1;
    for(Spot s: spots){
      alle[i++]=s;
    }
    sortiereNachY(alle);
    return alle;
  }
  
  public static void sortiereNachY(Spot[] spots){
      for(int i=0;i<spots.length;i++){
          int min=i;
          for(int j=i+1;j<spots.length;j++){
              if(spots[j].y<spots[min].y){
                  min=j;
              }
          }
          Spot merk=spots[i];
          spots[i]=spots[min];
          spots[min]=merk;
      }
  }
  
  public Stein transformiert(int dr, int dx, int dy){
    Spot[] neuspots=new Spot[spots.length];
    for(int i=0;i<spots.length;i++){
      neuspots[i]=spots[i].transformiert(c, dr, dx, dy);
    }
    return new Stein(farbe,c.transformiert(c, dr, dx, dy),neuspots);
  }
  
  public Color getColor(){
    return farbe;
  }
  
  public void setColor(Color f){
    farbe=f;
  }
  
    @Override
  public String toString(){
    String coords=c.toString();
    for(Spot s: spots){
      coords=coords+" "+s.toString();  
    }
    return coords;
  }
}