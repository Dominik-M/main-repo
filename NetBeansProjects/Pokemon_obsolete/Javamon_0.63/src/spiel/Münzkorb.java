package spiel;

public class Münzkorb implements java.io.Serializable{
  private int münzen;
  
    /**
     * Adds the given amount of Münzen if the total amount is less than 10000.
     * @param anz Anzahl der erhaltenen Münzen.
     * @return true if the Münzen where added.
     */
    public boolean addMünzen(int anz){
    münzen+=anz;
    if(münzen>9999){
      münzen=9999;
      return false;
    }
    else if(münzen<0){
      münzen-=anz;
      return false;
    }
    else return true;
  }
  
  public int getMünzen(){
    return münzen;
  }
}