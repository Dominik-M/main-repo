package welt.orte;

import java.util.ArrayList;
import java.util.Arrays;
import pokemon.Pokemon;
import spiel.Trainer;
import welt.objekte.Objekt;
import welt.objekte.TrainerObjekt;

public abstract class Ort implements java.io.Serializable{
  protected Objekt[][] objekte;
  protected Objekt standart=Objekt.STEIN;
  protected Objekt background=Objekt.HINTERGRUND;
  protected ArrayList<Pokemon> poks=new ArrayList();
  private ArrayList<Ort> nachbarn=new ArrayList();
  protected String name;
  private int startX,startY;
  public final int HOCH,BREIT;
  
  public Ort(String n,int breit,int hoch){
    name=n;
    BREIT=breit;
    HOCH=hoch;
    objekte=new Objekt[breit][hoch];
    startX=BREIT/2;
    startY=HOCH/2;
    erstelle();
  }
  
  public Ort(String n,Objekt[][] obj,Objekt std,Objekt bg,Pokemon... pokes){
      name=n;
      BREIT=obj.length;
      HOCH=obj[0].length;
      objekte=new Objekt[BREIT][HOCH];
      for(int x=0;x<objekte.length;x++) {
          System.arraycopy(obj[x], 0, objekte[x], 0, objekte[x].length);
      }
      standart=std;
      background=bg;
      if(pokes!=null)
        for(Pokemon p:pokes)poks.add(p);
  }
  
    @Override
  public String toString(){
    return name;
  }
    
    /**
     * Legt den Namen des Ortes fest. 
     * Der Name entspricht genau dem Wert, der bei Aufruf von toString() geliefert wird.
     * @param n Name des Ortes.
     */
    public void setName(String n){
      name=n;
  }

    /**
     * liefert das Objekt mit den gegebenen Koordinaten in diesem Ort. Befindet
     * sich kein Objekt an dieser Position (Eintrag ist null) oder liegen die
     * Koordinaten außerhalb des Ortes, wird das Standart Objekt zurückgegeben.
     * Diese Methode gibt niemals null zurück. Überschreibende Implementierungen
     * müssen das auch gewährleisten!
     *
     * @param x Die x Koordinate des Objekts
     * @param y Die y Koordinate des Objekts
     * @return Das Objekt an der gegebenen Position bzw. das Standart Objekt.
     * Niemals null
     */
    public Objekt getObjekt(int x, int y) {
        Objekt o;
        try {
            o = objekte[x][y];
            if (o == null) {
                o = standart;
            }
        } catch (Exception ex) {
            o = standart;
        }
        return o;
    }
    
    public void setStartPos(int x,int y){
        startX=x;
        startY=y;
    }

    /**
     * Liefert die x-Koordinate des Startpunktes in diesem Ort. Der Startpunkt
     * wird als Wiedereinstiegspunkt gewählt, wenn der Spieler besiegt wird.
     *
     * @return x-Koordinate des Startpunktes.
     */
    public int getStartX() {
        return startX;
    }

    /**
     * Liefert die y-Koordinate des Startpunktes in diesem Ort. Der Startpunkt
     * wird als Wiedereinstiegspunkt gewählt, wenn der Spieler besiegt wird.
     *
     * @return y-Koordinate des Startpunktes.
     */
    public int getStartY() {
        return startY;
    }

  
    /**
     * Liefert ein Objekt, das sich im Hintergrund der Darstellung befinden soll.
     * @return das Hintergrund Objekt des Ortes.
     */
    public Objekt getBackground(){
    return background;
  }
  
  public Pokemon getPok(){
    int zufall=(int)(Math.random()*poks.size());
    try{
      return poks.get(zufall);
    }catch(Exception e){
        return null;
    }
  }
  
  public Pokemon[] getPoks(){
      Pokemon[] feld=new Pokemon[poks.size()];
      for(int i=0;i<feld.length;i++) {
          feld[i]=poks.get(i);
      }
      return feld;
  }
  
  public void removePok(Pokemon pok){
    poks.remove(pok);
  }
  
  public void addPok(Pokemon pok){
    poks.add(pok);
  }
  
    /**
     * fügt einen Ort in die Liste der Nachbarorte ein, wenn noch nicht enthalten.
     * Ein Ort gilt dann als schon vorhanden, wenn einer der Orte in der Liste 
     * den gleichen Namen hat.
     * @param o hinzuzufügender Ort.
     * @return true, wenn der Ort aufgenommen wurde, false, wenn schon vorhanden.
     */
    public boolean addNachbarOrt(Ort o){
      if(o==null)return false;
      for(Ort n:nachbarn) {
          if(n.name==null)continue;
          else if(n.name.equals(o.name)) {
              return false;
          }
        }
      nachbarn.add(o);
      return true;
  }
  
    /**
     * Löscht den Ort aus der Liste der Nachbarorte, der den gleichen Namen hat,
     * wie der gegebene Ort.
     * @param o der zu löschende Nachbarort.
     * @return true, wenn der Ort enthalten war und rausgeschmissen wurde,
     * false, wenn er gar nicht erst enthalten war.
     */
    public boolean removeNachbarOrt(Ort o){
      if(o==null)return false;
      for(Ort n:nachbarn) {
          if(n.name==null)continue;
          else if(n.name.equals(o.name)) {
              nachbarn.remove(n);
              return true;
          }
      }
      return false;
  }
  
  public Ort[] getNachbarn(){
      Ort[] orte=new Ort[nachbarn.size()];
      for(int i=0;i<orte.length;i++)orte[i]=nachbarn.get(i);
      return orte;
  }
  
  public abstract void erstelle();
  
  public static Haus zeichneHaus(Ort o,int x,int y,int breit,int hoch,boolean bewohnt){
    Haus haus=null;
    for(int x1=0;x1<breit;x1++){
      for(int y1=0;y1<hoch;y1++){
        try{
          if(bewohnt&&x1==breit/2&&y1==hoch-1){
            haus=new Haus(o,x+x1,y+y1,breit,hoch);
            haus.name=null;
            o.objekte[x+x1][y+y1]=new welt.objekte.Tür(haus,haus.getX(),haus.getY());
          }else if(y1<hoch/2){
            o.objekte[x+x1][y+y1]=new welt.objekte.Dach(y1==0,y1==hoch/2-1,x1==0,x1==breit-1);
          }else{
            if(x1==0){
              if(y1==hoch-1)o.objekte[x+x1][y+y1]=Objekt.ECKE_LINKS;
              else o.objekte[x+x1][y+y1]=Objekt.FENSTER_LINKS;
            }
            else if(x1==breit-1){
              if(y1==hoch-1)o.objekte[x+x1][y+y1]=Objekt.ECKE_RECHTS;
              else o.objekte[x+x1][y+y1]=Objekt.FENSTER_RECHTS;
            }
            else if(y1==hoch-1){
              if(bewohnt)o.objekte[x+x1][y+y1]=Objekt.FENSTER_UNTEN;
              else o.objekte[x+x1][y+y1]=Objekt.HAUSWAND_UNTEN;
            }
            else o.objekte[x+x1][y+y1]=Objekt.FENSTER;
          }
        }catch(Exception ex){continue;}
      }
    }
    return haus;
  }
  
  public static Haus zeichneHaus(Ort o,String name,int x,int y,int breit,int hoch){
    Haus haus=zeichneHaus(o,x,y,breit,hoch,true);
    haus.name=name;
    return haus;
  }
  
  public static Haus zeichneHütte(Ort o,int x,int y,int breit,int hoch,boolean bewohnt){
    Haus haus=null;
    o.objekte[x][y]=Objekt.HÜTTENDACH_LINKS;
    o.objekte[x][y+1]=Objekt.HÜTTE_LINKS;
    o.objekte[x+1][y]=Objekt.HÜTTENDACH;
    if(bewohnt){
      haus=new Haus(o,x+1,y+1,3+breit,1+hoch);
      o.objekte[x+1][y+1]=new welt.objekte.Tür(haus,haus.getX(),haus.getY());
    }else{
      o.objekte[x+1][y+1]=Objekt.FENSTER_UNTEN;
    }
    for(int x1=0;x1<breit;x1++){
      o.objekte[x+2+x1][y]=Objekt.HÜTTENDACH;
      o.objekte[x+2+x1][y+1]=Objekt.FENSTER_UNTEN;
    }
    o.objekte[x+2+breit][y]=Objekt.HÜTTENDACH_RECHTS;
    o.objekte[x+2+breit][y+1]=Objekt.HÜTTE_RECHTS;
    return haus;
  }
  
   public static Haus zeichnePoke(Ort o,int x,int y){
     Haus haus=zeichneHaus(o,x,y,5,6,true);
     for(int y1=0;y1<=2;y1++){
       haus.objekte[0][y1]=Objekt.STEIN;
       haus.objekte[4][y1]=Objekt.STEIN;
     }
     for(int x1=1;x1<=3;x1++)for(int y1=0;y1<=2;y1++)haus.objekte[x1][y1]=Objekt.HEILFLÄCHE;
     haus.objekte[4][3]=new welt.objekte.Box();
     haus.objekte[1][3]=new welt.objekte.Person(1,new welt.items.Sonderbonbon(), 
             "Du bist das erste Mal in "+o+
             "? #Dann bekommst du dieses Willkommensgeschenk!",
             "Willkommen im Pokemon-Center von "+o+
             "!#Hier kannst du deine Pokemon heilen.");
      o.objekte[x+3][y+5]=new welt.objekte.Schild(welt.objekte.Schild.POKE);
     return haus;
   }
   
   public static Haus zeichneMarkt(Ort o,int x,int y,welt.items.Item... angebot){
     Haus haus=zeichneHaus(o,x,y,5,6,true);
     haus.objekte[0][1]=new welt.objekte.Markt(1, angebot);
     o.objekte[x+3][y+5]=new welt.objekte.Schild(welt.objekte.Schild.MART);
     return haus;
   }

    public void setPoks(Pokemon[] pokSelection) {
        poks=new ArrayList();
        poks.addAll(Arrays.asList(pokSelection));
    }

    public void setStandart(Objekt objekt) {
        if(objekt!=null) {
            standart=objekt;
        }
    }

    public void setBackground(Objekt objekt) {
        if(objekt!=null) {
            background=objekt;
        }
    }
    
    public boolean setObjekt(int x,int y,Objekt o){
        if(x>=0 && x<BREIT && y>=0 && y<HOCH) {
            if(objekte[x][y]!=null && objekte[x][y].getClass()==TrainerObjekt.class){
                this.removeTrainerReferenz((TrainerObjekt)objekte[x][y]);
            }
            objekte[x][y]=o;
            return true;
        }
        return false;
    }
    
    /**
     * Fügt allen begehbaren Objekten dieses Ortes in Reichweite und Blickrichtung des gegebenen
     * TrainerObjekts das TrainerObjekt als Trainer Referenz hinzu.
     * Ist in der Blickrichtung ein unbegehbares Objekt werden darauffolgende Objekte
     * ausgelassen, selbst wenn die Reichweite ausreichen würde.
     * @param trainer das zu referenzierende TrainerObjekt
     */
    public void addTrainerReferenz(TrainerObjekt trainer) throws InstantiationException, IllegalAccessException{
        for(int x=0;x<objekte.length;x++){
            for(int y=0;y<objekte[x].length;y++){
                TrainerObjekt t=getObjekt(x,y).getTrainer();
                if(t!=null && t.equals(trainer)){
                    switch(t.getRichtung()){
                        case Trainer.UP:
                            for(int i=1;i<=t.getRange();i++){
                                Objekt o=getObjekt(x,y-i);
                                if(o.isBegehbar()){
                                    if(o.isStatic())o= o.klone();
                                    o.setTrainer(trainer);
                                    if(!setObjekt(x,y-i,o))break;
                                }else break;
                            }
                            break;
                        case Trainer.RIGHT:
                            for(int i=1;i<=t.getRange();i++){
                                Objekt o=getObjekt(x+i,y);
                                if(o.isBegehbar()){
                                    if(o.isStatic())o= o.klone();
                                    o.setTrainer(trainer);
                                    if(!setObjekt(x+i,y,o))break;
                                }else break;
                            }
                            break;
                        case Trainer.DOWN:
                            for(int i=1;i<=t.getRange();i++){
                                Objekt o=getObjekt(x,y+i);
                                if(o.isBegehbar()){
                                    if(o.isStatic())o= o.klone();
                                    o.setTrainer(trainer);
                                    if(!setObjekt(x,y+i,o))break;
                                }else break;
                            }
                            break;
                        case Trainer.LEFT:
                            for(int i=1;i<=t.getRange();i++){
                                Objekt o=getObjekt(x-i,y);
                                if(o.isBegehbar()){
                                    if(o.isStatic())o= o.klone();
                                    o.setTrainer(trainer);
                                    if(!setObjekt(x-i,y,o))break;
                                }else break;
                            }
                            break;
                    }
                    return;
                }
            }
        }
    }
    
    /**
     * Entfernt ein TrainerObjekt aus den Trainer Referenzen.
     * @param trainer der zu entfernende Trainer
     */
    public void removeTrainerReferenz(TrainerObjekt trainer){
        for(int x=0;x<objekte.length;x++){
            for(int y=0;y<objekte[x].length;y++){
                Objekt o=getObjekt(x,y);
                if(o.equals(trainer)) {
                    o.setTrainer(null);
                }
            }
        }
    }
}