package attacken;

import java.util.LinkedList;
import pokemon.Pokemon;
import spiel.Typ;

public class Attacke implements java.io.Serializable,Cloneable{
  protected String name,tooltip;
  protected int dmg,ap,maxAp,gena;
  protected boolean phys;
  private Typ typ;
  private LinkedList<AttackenEffekt> effekte;
  
  public Attacke(AttackenEnum attacke){
      name=attacke.name();
      tooltip=attacke.TOOLTIP;
      dmg=attacke.DMG;
      ap=attacke.AP;
      maxAp=attacke.AP;
      gena=attacke.GENA;
      phys=attacke.PHYS;
      typ=attacke.TYP;
      effekte=attacke.EFFEKTE;
  }
  
  public Attacke(String n,int schaden,int aP,int genauigkeit,Typ t,boolean physisch){
    name=n;
    dmg=schaden;
    ap=aP;
    maxAp=aP;
    gena=genauigkeit;
    typ=t;
    phys=physisch;
    tooltip="";
    effekte=new LinkedList();
  }
  
  public Attacke klone(){
        try {
            return (Attacke) this.clone();
        } catch (CloneNotSupportedException ex) {
           return null;
        }
  }
  
    @Override
  public String toString(){
    return name;
  }
    
  public String getToolTipText(){
    return tooltip;
  }
    
  public void resetAp(){
    ap=maxAp;
  }
  
  public Typ getTyp(){
    return typ;
  }
  
  public int getDmg(){
    return dmg;
  }
  
  public int getGena(){
    return gena;
  }
  
  public int getVP(){
    return 50;
  }
  
  public int getAP(){
    return ap;
  }
  
  public int getMaxAP(){
    return maxAp;
  }
  
  public boolean isPhysisch(){
    return phys;
  }
   
  public int benutzt(boolean getroffen,Pokemon anwender,Pokemon gegner){
    //Sound.playSoundClip(name.toLowerCase()+".wav");
    ap--;
    for(AttackenEffekt effekt:effekte){
        if(effekt.SELBST)anwender.erhalteEffekt(effekt);
        else gegner.erhalteEffekt(effekt);
    }
    if(getroffen)return effekt();
    else return 0;
  }
    /**
     * was passiert wenn die Attacke benutzt wird
     * @return gibt den Basisschaden der Attacke zurück
     */
    protected int effekt(){
        return dmg;
    }
}