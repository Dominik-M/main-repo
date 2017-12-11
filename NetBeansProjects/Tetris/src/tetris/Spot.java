package tetris;

public class Spot {
  public final int x;
  public final int y;
  
  public Spot(int ix, int ypsilon){
    x=ix;
    y=ypsilon;            
  }
  
  public Spot transformiert(Spot c, int dr, int dx, int dy){
    int hor=x;
    int ver=y;
    if(dr%4!=0){
      hor=hor-c.x;
      ver=ver-c.y;
      int merk;
      while(dr>0){
        merk=hor;
        hor=(ver*-1);
        ver=merk;
        dr--;
      }
      hor=hor+c.x;
      ver=ver+c.y;
    }
    hor=hor+dx;
    ver=ver+dy;
    return new Spot(hor, ver);
  }
  
  public static Spot[] arrayAus(int... xy)throws IllegalArgumentException{
    if(xy.length%2!=0) throw new IllegalArgumentException();
    Spot[] spots=new Spot[xy.length/2];  
    int i=0; 
    while(i<xy.length){
      spots[i/2]=new Spot(xy[i],xy[i+1]);
      i=i+2;
    }
    return spots;        
  }
  
    @Override
  public String toString(){
    return "["+x+"/"+y+"]";
  }
}