package welt.orte;

import welt.objekte.Objekt;

public class Haus extends Ort{
    
    public Haus(Ort woher,int altX,int altY,int breit,int hoch){
      super(null,breit,hoch+1);
      objekte[objekte.length/2][objekte[objekte.length/2].length-1]=
              new welt.objekte.Tür(woher,altX,altY);
      standart=Objekt.SCHWARZ;
    }
    
    public Haus(Ort woher,String name,int altX,int altY,int breit,int hoch){
      super(name,breit,hoch+1);
      objekte[objekte.length/2][objekte[objekte.length/2].length-1]=
              new welt.objekte.Tür(woher,altX,altY);
      standart=Objekt.SCHWARZ;
    }

    @Override
    public void erstelle() {
      for(int x=0;x<objekte.length;x++){
        for(int y=0;y<objekte[x].length;y++){
          if(y==objekte[x].length-1){
            objekte[x][y]=Objekt.SCHWARZ;
          }else{
            objekte[x][y]=Objekt.HINTERGRUND;
          }
        }
      }
    }
    
    public int getX(){
      return objekte.length/2;
    }
    
    public int getY(){
      return objekte[objekte.length/2].length-1;
    }

    @Override
    public int getStartX() {
      return getX();
    }

    @Override
    public int getStartY() {
      return getY();
    }
}