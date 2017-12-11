package programm;

import eds.DoubleStack;
import mathe.Mathe;

public class Rechner {
  public static final String[] OPERATORS={"+","-","*","/","^","r"};
  private static DoubleStack stack;
  
  public static String rechne(String r){
    stack=new DoubleStack();
    r=galculator.Transformer.toUPN(r);
    System.out.println(r);
    String[] args=r.split(" ");
    for(String s:args){
      try{
        stack.push(Double.parseDouble(s));
      }catch(NumberFormatException ex){
        double erste;
        double zweite;
        switch(s){
            case "+":
                stack.push(stack.pull()+stack.pull());
                break;
            case "-":
                erste=stack.pull();
                zweite=stack.pull();
                stack.push(zweite-erste);
                break;
            case "*":
                stack.push(stack.pull()*stack.pull());
                break;
            case "/":
                erste=stack.pull();
                zweite=stack.pull();
                stack.push(zweite/erste);
                break;
            case "^":
                erste=stack.pull();
                zweite=stack.pull();
                stack.push(Mathe.potenz(zweite,(int)(erste)));
                break;
            case "r":
                erste=stack.pull();
                zweite=stack.pull();
                stack.push(Mathe.wurzel((int)zweite,erste));
                break;
            case "n":
                erste=stack.pull();
                zweite=stack.pull();
                stack.push(Mathe.binomial((int)zweite,(int)erste));
                break;
            case "!":
                stack.push(Mathe.fakultät((int)stack.pull()));
                break;
            case "s":
                stack.push(Math.sin(stack.pull()));
                break;
            case "c":
                stack.push(Math.cos(stack.pull()));
                break;
            case "t":
                stack.push(Math.tan(stack.pull()));
                break;
        }
      }
    }
    return stack.pull()+"";
  }
}