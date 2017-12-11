public class Calculator{
  private static DoubleStack stack= new DoubleStack(0);
  
  public static String calc(String s){
    if(infix(s)){ s=Transformer.toUPN(s); }
    try{s=s+" = "+rechnen(s);}
    catch(NumberFormatException ex){}
    finally{
    deleteAll();
    return s;
    }
  }
  
  public static double rechnen(String s){
    int l=s.length();
    char rech;
    for (int i=0;i<l;i++) {
      String teil="";
      while (s.charAt(i)!=' ' && i<l) {
        teil=teil+s.charAt(i);
        i++;
      }
      if(teil.charAt(0)+0<48){ // Da Rechenzeichen kleiner sind als 48, folgt jetzt sicherlich eine Rechnung
        rech=teil.charAt(0);
        if(rech=='+')stack.wert=stack.pull()+stack.pull();//plus
        else if(rech=='-')stack.wert=-stack.pull()+stack.pull();//minus
        else if(rech=='*')stack.wert=stack.pull()*stack.pull();//mal
        else if(rech=='/'){
          double divident=stack.pull();
          stack.wert=stack.pull()/divident;               //geteilt;
        }
        stack.push();
      }
      else {
        stack.wert=machZahl(teil);
        stack.push();
      }
    }
    return stack.wert;
  }
  
  
  public static void deleteAll(){
    double schrott=stack.wert;
    while(stack.pull()!=0) schrott=stack.pull();
    stack.wert=0;
  }
  
  public static double machZahl(String s){
    int t=s.indexOf(' ',0);
    if(t==-1) t=s.length();
    String zstr=s.substring(0,t);
    double zahl=Double.parseDouble(zstr);
    return zahl;
  }
  
  public static boolean infix(String s){
    return s.charAt(s.indexOf(' ',0)+1)+0<48;
    //wenn nach dem ersten Leezeichen (nach der ersten Zahl) noch eine Zahl kommt ist es h�chstwahrscheinlich nicht Infix.
  }
}