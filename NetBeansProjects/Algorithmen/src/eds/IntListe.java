
package eds;

public class IntListe {
  private IntKnoten erster=new IntKnoten(0);
  private int size=0;
  
  public IntListe(){}
  
  public IntListe(int... zahlen){
    for(int x:zahlen){
      add(x);
    }
  }
  
  public void add(int x){
    getLast().next=new IntKnoten(x);
    size++;
  }
  
  public void add(int... zahlen){
    for(int x:zahlen){
      add(x);
    }
  }
  
  private IntKnoten getLast(){
    IntKnoten letzter=erster;
    for(int i=0;i<size;i++){
      letzter=letzter.next;
    }
    return letzter;
  }
  
  public int get(int pos){
    IntKnoten lauf=erster;
    for(int i=0;i<=pos;i++){
      if(lauf.next!=null) lauf=lauf.next;
      else break;
    }
    return lauf.wert;
  }
  
  public void tausche(int pos1,int pos2){
    if(pos1>size || pos2>size ) return;
    IntKnoten x=erster;
    IntKnoten y=erster;
    for(int i=0;i<pos1;i++){
      x=x.next;
    }
    for(int i=0;i<pos2;i++){
      y=y.next;
    }//TODO funktionierend machen
  }
  
  public int delete(int pos){
    IntKnoten lauf=erster;
    while(pos>0){
      lauf=lauf.next;
      if(lauf==null)return -1;
      pos--;
    }
    if(lauf.next==null)return -1;
    int wert=lauf.next.wert;
    lauf.next=lauf.next.next;
    size--;
    return wert;
  }
  
  public int getSize(){
    return size;
  }
  
    @Override
  public String toString(){
    String s="Zahlen:";
    IntKnoten lauf=erster;
    for(int i=0;i<size;i++){
      lauf=lauf.next;
      s=s+" "+lauf.wert;
    }
    return s;  
  }
}

class IntKnoten{
  IntKnoten next;
  int wert;
  
  IntKnoten(int zahl){
    wert=zahl;  
  }
}