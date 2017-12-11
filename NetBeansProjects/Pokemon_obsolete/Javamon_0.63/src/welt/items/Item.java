package welt.items;

public abstract class Item implements java.io.Serializable{
  protected String name,info;
  private int wert;
  
  public Item(String bezeichnung,int w,String tooltip){
    name=bezeichnung;
    wert=w;
    info=tooltip;
  }
  
  public abstract boolean benutzt();
  
    @Override
  public String toString(){
    return name;
  }
    
  public int getWert(){
    return wert;
  }
  
  public String getToolTipText(){
    return info;
  }
  
    @Override
  public abstract Item clone();
}