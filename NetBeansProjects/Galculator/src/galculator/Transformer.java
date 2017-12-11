
package galculator;

/**
 *
 * @author Dundun
 */
public class Transformer {
  private static StringStack stack=new StringStack();
  
  public static void main(String[] args){
    // Test mit ganzrationaler Funktion 2. Grades
    String s="a * x * x + b * x + c";
    System.out.println(toUPN(s));
  }
  
  public static String toUPN(String s){
    String upn="";
    for (int i=0;i<s.length();i++) {
      int t=s.indexOf(' ',i);
      if(t==-1)t=s.length();
      String teil=s.substring(i,t);
      if (teil.charAt(0)+0>=48) {
        upn=upn+teil+" ";
      } else {
        if(stack.peak()!=null && punktrech(stack.peak()))upn=upn+stack.pull()+" ";
        stack.push(teil);
      }
      i=t;
    }
    while (stack.peak()!=null) {
      upn=upn+stack.pull()+" ";
    }
    return upn;
  }
  
  public static boolean punktrech(String s){
    char rech=s.charAt(0);
    return rech=='*' || rech=='/';
  }
  
}