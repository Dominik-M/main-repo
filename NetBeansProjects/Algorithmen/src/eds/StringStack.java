
package eds;

/**
 *
 * @author Dundun
 */
public class StringStack {
  private String wert;
  private StringStack next;
  
  public StringStack(){};
  
  public StringStack(String s){;
    wert=s;
  }
  
  public void push(String s){
    StringStack neu=new StringStack(s);
    neu.next=this.next;
    this.next=neu;
  }
  
  public String peak(){
    if (this.next==null) return null;
    return this.next.wert;
  }
  
  public String pull(){
    if (this.next==null) return null;
    String wert = this.next.wert;
    this.next=this.next.next;
    return wert;
  }
}
