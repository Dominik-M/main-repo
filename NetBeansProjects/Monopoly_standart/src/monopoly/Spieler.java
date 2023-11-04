package monopoly;

import java.awt.Color;

public class Spieler {
  String name;
  int geld;
  Strasse[] strassen;
  Color farbe=null;
  int pos=0;
  int timer=0;
  
  public Spieler(String n,int startgeld){
    name=n;
    geld=startgeld;
    strassen=new Strasse[28];
  }
  
    @Override
  public String toString(){
    return name;
  }
  
  public void add(Strasse s){
    if(s.besitzer!=null && s.besitzer.equals(this)){ return; }
    Strasse s3=null;
    Strasse s4=null;
    for(Strasse s2: strassen){
      if(s2==null)break;
      if(s.farbeNormal.equals(s2.farbeNormal) && !s2.equals(s)){
        s.miete=s.miete+s.mieteNormal;
        s2.miete=s2.miete+s2.mieteNormal;
        if(s3==null){
          s3=s2;
        }else if(s4==null){
          s3.miete=s3.miete+s3.mieteNormal;
          s4=s2;
        }else{
          s4.miete=s4.miete+s4.mieteNormal;
          break;
        }
      }
    }
    s.besitzer=this;
    for(int i=0;i<28;i++){
      if(strassen[i]==null){
        strassen[i]=s;
        break;
      }
    }
  }
  
  public void remove(Strasse s){
    Strasse s3=null;
    Strasse s4=null;
    int i=0;
    boolean flag=false;
    while(i<strassen.length){
      Strasse s2=strassen[i];
      if(s2==null)break;
      if(s2.equals(s)||flag){
        strassen[i]=strassen[++i];
        if(!flag){
          flag=true;
          continue;
        }
      }
      if(s.farbeNormal.equals(s2.farbeNormal)){
        s.miete=s.miete-s.mieteNormal;
        if(s3==null){
          s3=s2;
        }else if(s4==null){
          s4=s2;
        }else{
          break;
        }
      }
    }
    strassen[i]=null;
    s.besitzer=null;
    return;    
  }
  
  public void zahle(int betrag){
    geld=geld-betrag;  
  }
  
  public void zahle(int betrag,Spieler nehmer){
    geld=geld-betrag;
    nehmer.geld=nehmer.geld+betrag;
  }
}
