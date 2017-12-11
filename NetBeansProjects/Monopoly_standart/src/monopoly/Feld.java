package monopoly;

import java.awt.Color;

public class Feld {
  String name;
  int pos;
  boolean aktiv=true;
  Color farbe;
  
  public Feld(String n,int wo,Color farb){
    name=n;
    pos=wo;
    farbe=farb;
  }
  
    @Override
  public String toString(){
    return name;
  }
}
