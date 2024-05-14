
package eds;

/**
 *
 * @author Dundun
 */
public class DoubleStack {
  private double wert;
  private DoubleStack next;
  
  public DoubleStack(){}
  
  private DoubleStack(double w){
    wert=w;
  }
  
  public void push(){
    if(isEmpty())return;
    DoubleStack neu=new DoubleStack(next.peak());
    neu.next=this.next;
    this.next=neu;
  }
  
  public void push(double zahl){
    DoubleStack neu=new DoubleStack(zahl);
    neu.next=this.next;
    this.next=neu;
  }
  
  public double peak(){
    if(isEmpty())return wert;
    return next.wert;
  }
  
  public double pull(){
    if (isEmpty()) return wert;
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
  
  public boolean isEmpty(){
    return next==null;
  }
}