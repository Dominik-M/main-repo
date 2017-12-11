package mathe;

import eds.IntListe;

public class Primzahlen {
  
  public static int[] gibPrim(int von,int bis){
    if(von<2) von=2;
    if(bis<=von) return null;
    IntListe zahlen=new IntListe();
    for(int i=von;i<=bis;i++){
      boolean flag=true;
      for(int j=2;j<i;j++){
        if(i%j==0){
          flag=false;
          break;
        }
      }
      if(flag)zahlen.add(i);
    }
    int size=zahlen.getSize();
    int[] prims=new int[size];
    for(int i=0;i<size;i++){
      prims[i]=zahlen.get(i);
    }
    return prims;
  }
  
  public static int[] gibPrim(int bis){
    return gibPrim(2,bis);  
  }
  
  public static boolean isPrim(int n){
    for(int i=2;i<n;i++){
      if(n%i==0){
        return false;
      }
    }
    return true;
  }
}
