
package galculator;

import eds.StringStack;

/**
 *
 * @author Dundun
 */
public class Transformer {
  private static StringStack stack=new StringStack();
  
  public static String toUPN(String s){
    String upn="";
    for(int i=0;i<s.length();i++){
      char teil=s.charAt(i);
      if(teil+0>=48 && teil+0<=57)upn=upn+teil;
      else{
        if(teil=='(') {
              stack.push(teil+"");
              continue;
          }
        else if(teil==')'){
          while(stack.peak()!=null){
            if(stack.peak().startsWith("(")){
              stack.pull();
              break;
            }
            upn=upn+" "+stack.pull();
          }
        }
        else{
          upn=upn+" ";
          if(stack.peak()!=null && !strichrech(stack.peak()))upn=upn+stack.pull()+" ";
          stack.push(teil+"");
        }
      }
    }
    while (stack.peak()!=null) {
      upn=upn+" "+stack.pull();
    }
    return upn;
  }
  
  public static boolean strichrech(String s){
    char rech=s.charAt(0);
    return rech=='+' || rech=='-';
  }
  
}