package tetris;

import java.awt.Color;
import java.util.LinkedList;

public class Feld {
  boolean[] belegt;
  int breit;
  int hoch;
  Stein akt;
  LinkedList<FeldListener> angemeldet=new LinkedList();
  
  public Feld(int b, int h){
    belegt=new boolean[b*h];
    breit=b;
    hoch=h;
  }
  
  public void addFeldListener(FeldListener wer){
    angemeldet.add(wer);
  }
  
  public int breite(){return breit;}
  
  public int hoehe(){return hoch;}
  
  public boolean setStein(Stein rock){
    Spot[] spots=rock.spots();
    for(int i=0;i<spots.length;i++){
      if(spots[i].y>=hoch){
        fixiere();
        return true;
      }
      if(this.belegt(spots[i].x,spots[i].y))return false;
    }
    akt=rock;
    return true;
  }
  
  public Stein getStein(){return akt;}
  
  public boolean belegt(int x, int y){
    if(y*breit+x>=belegt.length || x>=breit || x<0 || y<0){return true;}
    return belegt[y*breit+x];
  }
  
  public void reset(){
    for(int i=0;i<belegt.length;i++)belegt[i]=false;
    neuerStein();
    angemeldet.getFirst().reset(breit,hoch);
  }
  
  public void fixiere(){
    for(FeldListener fl:angemeldet)fl.steinFixiert();
    Spot[] spots=akt.spots();   
    for(int i=0;i<spots.length;i++)
      belegt[spots[i].y*breit+spots[i].x]=true;
    for(int i=0;i<spots.length;i++){
      int j=spots[i].y*breit;
      boolean voll=true;  
      while(j<spots[i].y*breit+breit){
        if(belegt[j]==false){
          voll=false;  
          break;}
        j++;
      }
      if(voll){ voll(j-1); }
   }
    if(!neuerStein()) {
      System.out.println("Game Over");
      System.out.println(angemeldet.getFirst().toString());
      System.exit(0); 
    }
  }
  
  public void voll(int j){
    int l=j-breit;
    while(l>=0){
      belegt[j--]=belegt[l--];
    }
    angemeldet.getFirst().volleZeilen(breit);
  }
  
  public boolean neuerStein(){
    Stein neu;
    int zufall=(int)(Math.random()*7+1);
    if(zufall==1)neu=new Stein(Color.GRAY,breit/2,0,breit/2+1,0,breit/2+2,0,breit/2-1,0);//Balken
    else if(zufall==2)neu=new Stein(Color.CYAN,breit/2,0,breit/2+1,0,breit/2,1,breit/2+1,1);// Quadrat-Klotz
    else if(zufall==3)neu=new Stein(Color.GREEN,breit/2,0,breit/2+1,0,breit/2,1,breit/2-1,1);//Z-links
    else if(zufall==4)neu=new Stein(Color.RED,breit/2,0,breit/2-1,0,breit/2,1,breit/2+1,1);// Z-rechts
    else if(zufall==5)neu=new Stein(Color.ORANGE,breit/2,0,breit/2,1,breit/2+1,0,breit/2+2,0);// L-rechts
    else if(zufall==6)neu=new Stein(Color.BLUE,breit/2,0,breit/2,1,breit/2-1,0,breit/2-2,0);// L-links
    else neu=new Stein(Color.YELLOW,breit/2,0,breit/2+1,0,breit/2,1,breit/2-1,0);// T-Stein
    if(this.setStein(neu)){
      akt=neu;
      return true;
    }
    else return false;
  }
}
