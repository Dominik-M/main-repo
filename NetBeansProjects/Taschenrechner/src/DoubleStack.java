public class DoubleStack {
  double wert;
  private DoubleStack next;
  
  public DoubleStack(){}
  
  public DoubleStack(double w){;
    wert=w;
  }
  
  public void push(){
    DoubleStack neu=new DoubleStack(this.wert);
    neu.next=this.next;
    this.next=neu;
  }
  
  public double pull(){
    if (this.next==null) return 0;
    double wert = this.next.wert;
    this.next=this.next.next;
    return wert;
  }
  
  public String toString(){
    DoubleStack lauf=this;
    String s=lauf.wert+" ";
    while(lauf.next!=null){
      lauf=lauf.next;
      s=s+" "+lauf.wert+" ";
    }
    return s;
  }
}