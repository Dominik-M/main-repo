package spiel;

public class Stringqueue implements java.io.Serializable{
  private Stringqueue next;
  public String wert;
  
  private Stringqueue(String text){
    wert=text;
  }
  
  public Stringqueue(){}
  
  public void add(String text){
    if(next==null)next=new Stringqueue(text);
    else next.add(text);
  }
  
  public String pull(){
    if(next==null)return null;
    else{
      Stringqueue merk=next;
      next=next.next;
      return merk.wert;
    }
  }
  
  public String peak(){
    if(next==null)return null;
    else return next.wert;
  }
  
  public boolean isEmpty(){
    return next==null;
  }

  public int size(){
      if(next==null)return 0;
      else return 1+next.size();
  }
  
    public String[] toArray() {
        String[] feld=new String[size()];
        Stringqueue lauf=next;
        for(int i=0;i<feld.length;i++){
            feld[i]=lauf.wert;
            lauf=lauf.next;
        }
        return feld;
    }
}