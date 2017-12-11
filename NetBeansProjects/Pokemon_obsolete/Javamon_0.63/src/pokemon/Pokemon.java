package pokemon;

import attacken.Attacke;
import attacken.AttackenEffekt;
import java.awt.Color;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import spiel.Fähigkeit;
import spiel.Javamon;
import spiel.Status;
import spiel.Typ;
import spiel.Wesen;

public class Pokemon implements java.io.Serializable{
  private String spitzname,name;
  protected Status status;
  private Wesen wesen;
  protected Fähigkeit ability;
  protected welt.items.Entwickler item;
  private boolean spielers,wild,boostable,confused;
  private int kp,maxkp,atk,vert,spezAtk,spezVert,init,ep=0,nextlvl,level=1,eV;
  private int basisKp,basisAtk,basisVert,basisSpezAtk,basisSpezVert,basisInit;
  private double atkBoost=0,vertBoost=0,spezAtkBoost=0,spezVertBoost=0,
                 initBoost=0,gena=1.0,flu=1.0;
  protected Typ typ1,typ2;
  protected Attacke[] atts=new Attacke[4];
  protected java.util.LinkedList<Attacke> bekannte=new java.util.LinkedList();
  protected Icon bild;
  
    /**
     * Erzeugt ein Pokemon aus den Basiswerten.
     * @param n Name des Pokemons.
     * @param kP Basis KP.
     * @param angriff Basis Angriffskraft.
     * @param verteidigung Basis Verteidigungswert.
     * @param spezAng Basis Spezial Angriff.
     * @param spezV Basis Spezial Verteidigung.
     * @param initiative Basis Init.
     * @param t1 Typ 1.
     * @param t2 Typ 2.
     * @param lvl Das Level des Pokemons, beträgt mindestens 1. 
     * Ist lvl kleiner 2 ist das Level 1 und alle Werte haben den Basiswert.
     */
    public Pokemon(String n,int kP,int angriff,int verteidigung,
          int spezAng,int spezV,int initiative,Typ t1,Typ t2,int lvl){
    name=n;
    basisKp=kP;
    basisAtk=angriff;
    basisVert=verteidigung;
    basisSpezAtk=spezAng;
    basisSpezVert=spezV;
    basisInit=initiative;
    setWesen(Wesen.SANFT);
    typ1=t1;
    typ2=t2;
    status=Status.OK;
    wild=true;
    spielers=false;
    boostable=true;
    try{
      bild=new javax.swing.ImageIcon(getClass().getResource("/pokemon/img/"+name.toLowerCase()+"Icon.png"));  
    }catch(Exception e){
      bild=new javax.swing.ImageIcon(getClass().getResource("/pokemon/img/abstract.png"));
    }
    
    maxkp=basisKp;
    kp=basisKp;
    atk=basisAtk;
    vert=basisVert;
    spezAtk=basisSpezAtk;
    spezVert=basisSpezVert;
    init=basisInit;
    while(lvl>1){
      levelUp();
      lvl--;
    }
  }
  
  /*
   * Erzeugt ein Pokemon aus Vorentwicklung
   */
  Pokemon(String n,int kP,int angriff,int verteidigung,
          int spezAng,int spezV,int initiative,Typ t1,Typ t2,
          Pokemon vor){
    name=n;
    spitzname=vor.spitzname;
    level=vor.level;
    nextlvl=vor.nextlvl;
    basisKp=(kP+vor.basisKp)/2;
    kp=vor.kp/2+level*kP/6;
    maxkp=vor.maxkp/2+level*kP/6;
    basisAtk=(angriff+vor.basisAtk)/2;
    atk=vor.atk/2+level*angriff/6;
    basisVert=(verteidigung+vor.basisVert)/2;
    vert=vor.vert/2+level*verteidigung/6;
    basisSpezAtk=(spezAng+vor.basisSpezAtk)/2;
    spezAtk=vor.spezAtk/2+level*spezAng/6;
    basisSpezVert=(spezV+vor.basisSpezVert)/2;
    spezVert=vor.spezVert/2+level*spezV/6;
    basisInit=(initiative+vor.basisInit)/2;
    init=vor.init/2+level*initiative/6;
    wesen=vor.wesen;
    ability=vor.ability;
    typ1=t1;
    typ2=t2;
    status=vor.status;
    eV=vor.eV+500;
    wild=vor.wild;
    spielers=vor.spielers;
    boostable=vor.boostable;
    confused=vor.confused;
    for(int i=0;i<atts.length;i++){
      atts[i]=vor.atts[i];
    }
    bekannte=vor.bekannte;
    try{
      java.awt.Image img = ImageIO.read(getClass().getResource("/pokemon/img/"+name.toLowerCase()+"Icon.png"));
      Javamon.makeColorTransparent(img, new Color(255,255,255));
      bild=new javax.swing.ImageIcon(img);
    }catch(Exception e){
      bild=new javax.swing.ImageIcon(getClass().getResource("/pokemon/img/abstract.png"));
    }
  }
  
  /**
   * Erlernt Attacken des neuen Levels oder sorgt für Entwicklung.
   * @return null oder nach einer Entwicklung das neue Pokemon
   */
  protected Pokemon lvlUp(){
      return null;
  }
  
    /**
     * Lässt das Pokemon um eine Stufe aufsteigen, erzeugt die
     * neuen Werte und ruft lvlUp auf.
     * @return null oder nach einer Entwicklung das neue Pokemon
     */
    public final Pokemon levelUp(){
      level++;
      nextlvl+=getWerteSumme()/50+level;
      kp+=basisKp/6;
      maxkp+=basisKp/6;
      atk+=basisAtk/6;
      vert+=basisVert/6;
      spezAtk+=basisSpezAtk/6;
      spezVert+=basisSpezVert/6;
      init+=basisInit/6;
      return lvlUp();
    }
  
  public boolean isBsg(){
    return kp==0;
  }
  
  public boolean isOK(){
    for(Attacke a:atts){
      if(a==null)continue;
      if(a.getAP()<a.getMaxAP())return false;
    }
    return kp==maxkp&&status==Status.OK;
  }
  
  /** 
   * Berechnet den Schaden nur in Abhängigkeit der Eigenschaften des Angreifers.
   * @param welche Index der Attacke
   * @return Schaden vor Abzug von Verteidigung und Zusatzeffekten des Verteidigers.
   */
  public int angriff(int welche){
    Attacke att;
    if(welche<atts.length){
      att=atts[welche];
    }else{
      welche-=atts.length;
      att=bekannte.get(welche);
    }
    if(att==null||att.getAP()<=0) return 0;
    int dmg=0;
    if(att.isPhysisch())
      dmg = (int)((level+5)*(atk+atk*atkBoost)*att.getDmg());
    else{
      dmg = (int)((level+5)*(spezAtk+spezAtk*spezAtkBoost)*att.getDmg());
    }
    if(att.getTyp()==typ1 || att.getTyp()==typ2)dmg=(int)(dmg*1.25);
    return dmg;
  }
  
  public int angriff(Attacke att){
    for(int i=0;i<atts.length;i++){
      if(att.equals(atts[i]))return angriff(i);
    }
    return 0;
  }
  
  /** 
   * Errechnet den tatsächlich erlittenen Schaden mit den eigenen vert/vertBoost
   * Werten.
   * @param dmg Schaden (meistens von der angriff Methode)
   * @param mul Multiplikator für effektivität
   * (muss irgendwo außerhalb gegeben werden)
   * @return den tatsächlichen Schaden
   */
  public int verteidige(int dmg,boolean phys,double mul){
    if(dmg>0){
      if(ability==Fähigkeit.WUNDERWACHE&&mul<2){
        Javamon.sout(this+" schützt sich mit "+ability);
        return 0;
      }
      if(phys){
          dmg=(int)(((dmg/(50*(vert+vert*vertBoost)))+2)*mul);
      }else{
        dmg=(int)(((dmg/(50*(spezVert+spezVert*spezVertBoost)))+2)*mul);
      }
    }
    if(dmg>=kp){
      return kp;
    }
    return dmg; 
  }
  
  
  public boolean nimmSchaden(int n){
    if(n>=kp){
      kp=0;
      status=Status.BSG;
      return true;
    }
    kp-=n;
    if(kp>maxkp)kp=maxkp;
    return false;
  }
  
  /**
   * erlent eine neue Attacke, wenn noch weniger als vier bekannt sind. 
   * Ansonsten wird die Attacke zu den bekannten hinzugefügt.
   * @param neu die neue Attacke
   * @return ob es geklappt hat
   */
  public boolean lerne(Attacke neu){
    if(spielers)Javamon.sout(toString()+" lernt "+neu);
    for(int i=0;i<atts.length;i++){
      if(atts[i]==null){
        atts[i]=neu;
        return true;
      }
    }
    bekannte.add(neu);
    return false;
  }
  
  /**
   * erlernt eine Attacke an eine bestimmte Position, wodurch eine alte 
   * möglicherweise ersetzt wird.
   * @param wo Index für die neue Attacke
   * @param neu die neue Attacke
   */
  public void setzeAttAuf(int wo,Attacke neu){
    bekannte.add(atts[wo]);
    atts[wo]=neu;
    bekannte.remove(neu);
  }

    @Override
  public String toString(){
    if(spitzname!=null)return spitzname;
    else return name;
  }
    
  public String getOriginName(){
    return name;
  }
  
  /**
   * Gibt den Typ des Pokemons zurück
   * @return Der Typ
   */
  public Typ getTyp1(){
    return typ1;
  }
  
  public Typ getTyp2(){
    return typ2;
  }
  
  /**
   * erteilt Erfahrungspunkte an das Pokemon und ruft levelUp auf, wenn die
   * EP für das nächste Level erreicht sind.
   * @param anz Anzahl EP
   */
  public Pokemon bekommEP(int anz){
    ep+=anz;
    eV+=anz/level;
    Pokemon neu=null;
    while(ep>=nextlvl){
      ep-=nextlvl;
      eV+=150+level;
      neu=levelUp();
    }
    return neu;
  }
  
  public void verteilEV(){
    while(eV/100>0){
      int zufall=(int)(Math.random()*6);
      switch(zufall){
          case Javamon.ANGR:
            verteilEV(Javamon.ANGR);
            break;
          case Javamon.VERT:
            verteilEV(Javamon.VERT);
            break;
          case Javamon.SPEZANGR:
            verteilEV(Javamon.SPEZANGR);
            break;
          case Javamon.SPEZVERT:
            verteilEV(Javamon.SPEZVERT);
            break;
          case Javamon.INIT:
            verteilEV(Javamon.INIT);
            break;
          case Javamon.KP:
            verteilEV(Javamon.KP);
            break;
      }
    }
  }
  
  public void verteilEV(int stat){
    switch(stat){
      case Javamon.ANGR:
        atk+=basisAtk/10;
      break;
      case Javamon.VERT:
        vert+=basisVert/10;
      break;
      case Javamon.SPEZANGR:
        spezAtk+=basisSpezAtk/10;
      break;
      case Javamon.SPEZVERT:
        spezVert+=basisSpezVert/10;
      break;
      case Javamon.INIT:
        init+=basisInit/10;
      break;
      case Javamon.KP:
        kp+=basisKp/10;
        maxkp+=basisKp/10;
      break;
    }
    eV-=100;
  }
  
  public boolean gibItem(welt.items.Entwickler i){
    if(item!=null)return false;
    item=i;
    if(spielers)Javamon.sout(toString()+" erhält "+i);
    return true;
  }
  
  public welt.items.Entwickler nimmItem(){
    welt.items.Entwickler i=item;
    if(spielers&&item!=null)Javamon.sout(item+" von "+toString()+" genommen.");
    item=null;
    return i;
  }
  
  public welt.items.Item getItem(){
    return item;
  }
  
  public void heal(){
    kp=maxkp;
    status=Status.OK;
    for(Attacke a:atts){
      if(a!=null)a.resetAp();
    }
  }
  
  public boolean heal(int a){
    boolean geht=status!=Status.BSG&&kp<maxkp;
    if(geht){
      kp+=a;
    }
    if(kp>maxkp)kp=maxkp;
    return geht;
  }
  
  public boolean boostAtk(int n){
    atkBoost+=n*0.15;
    boolean gehtnich=!boostable || atkBoost>=1 || atkBoost<=-1;
    if(gehtnich){
      atkBoost-=n*0.15;
    }
    return !gehtnich;
  }
  
  public boolean boostVert(int n){
    vertBoost+=n*0.15;
    boolean gehtnich=!boostable || vertBoost>=1 || vertBoost<=-1;
    if(gehtnich){
      vertBoost-=n*0.15;
    }
    return !gehtnich;
  }
  
  public boolean boostSpezAtk(int n){
    spezAtkBoost+=n*0.15;
    boolean gehtnich=!boostable || spezAtkBoost>=1 || spezAtkBoost<=-1;
    if(gehtnich){
      spezAtkBoost-=n*0.15;
    }
    return !gehtnich;
  }
  
  public boolean boostSpezVert(int n){
    spezVertBoost+=n*0.15;
    boolean gehtnich=!boostable || spezVertBoost>=1 || spezVertBoost<=-1;
    if(gehtnich){
      spezVertBoost-=n*0.15;
    }
    return !gehtnich;
  }
  
  public boolean boostInit(int n){
    initBoost+=n*0.15;
    boolean gehtnich=!boostable || initBoost>=1 || initBoost<=-1;
    if(gehtnich){
      initBoost-=n*0.15;
    }
    return !gehtnich;
  }
  
  public boolean boostGena(int n){
    gena+=n*0.1;
    boolean gehtnicht=!boostable || gena<0.4;
    if(ability==Fähigkeit.ADLERAUGE){
      gehtnicht=true;
      Javamon.sout(ability+" von "+this+" verhindert Verlust von Genauigkeit.");
    }
    if(gehtnicht){
      gena-=n*0.1;
    }
    return !gehtnicht;
  }
  
  public boolean boostFlu(int n){
    flu-=n*0.1;
    boolean gehtnicht=!boostable || flu<0.4;
    if(gehtnicht){
      flu+=n*0.1;
    }
    return !gehtnicht;
  }
  
  public void resetBoosts(){
    atkBoost=0;
    vertBoost=0;
    spezAtkBoost=0;
    spezVertBoost=0;
    initBoost=0;
    gena=1;
    flu=1;
    boostable=true;
    confused=false;
  }
  
  public Attacke[] getAttacken(){
    return atts;
  }
  
  public Attacke[] getBekannte(){
    Attacke[] att=new Attacke[bekannte.size()];
    for(int i=0;i<att.length;i++)att[i]=bekannte.get(i);
    return att;
  }
    
  public int getLevel(){
    return level;
  }
  
  public int getEP(){
    return ep;
  }
  
  public int getBenEP(){
    return nextlvl;
  }
  
  public int getKP(){
    return kp;
  }
  
  public int getInit(){
    if(status==Status.PAR)return 1;
    return (int)(init*(1+initBoost));
  }
  
  public int getMaxKP(){
    return maxkp;
  }
  
  public Icon getIcon(){
    return bild;
  }
  
  public Status getStatus(){
    return status;
  }
  
  public Wesen getWesen(){
    return wesen;
  }
  
  public final void setWesen(Wesen w){
      if (wesen != null) {
          if (wesen == Wesen.TAPFER) {
              basisAtk += 10;
              basisVert -= 10;
          } else if (wesen == Wesen.FORSCH) {
              basisAtk += 10;
              basisSpezAtk -= 10;
          } else if (wesen == Wesen.SCHEU) {
              basisAtk += 10;
              basisSpezAtk -= 10;
          } else if (wesen == Wesen.FLINK) {
              basisAtk += 10;
              basisSpezAtk -= 10;
          } else if (wesen == Wesen.BRUTAL) {
              basisVert += 10;
              basisAtk -= 10;
          } else if (wesen == Wesen.NAIV) {
              basisVert += 10;
              basisSpezAtk -= 10;
          } else if (wesen == Wesen.MUTIG) {
              basisVert += 10;
              basisSpezVert -= 10;
          } else if (wesen == Wesen.EIFRIG) {
              basisVert += 10;
              basisInit -= 10;
          } else if (wesen == Wesen.HART) {
              basisSpezAtk += 10;
              basisAtk -= 10;
          } else if (wesen == Wesen.ROBUST) {
              basisSpezAtk += 10;
              basisVert -= 10;
          } else if (wesen == Wesen.ZAGHAFT) {
              basisSpezAtk += 10;
              basisSpezVert -= 10;
          } else if (wesen == Wesen.FROH) {
              basisSpezAtk += 10;
              basisInit -= 10;
          } else if (wesen == Wesen.HITZIG) {
              basisSpezVert += 10;
              basisAtk -= 10;
          } else if (wesen == Wesen.PFIFFIG) {
              basisSpezVert += 10;
              basisVert -= 10;
          } else if (wesen == Wesen.FIES) {
              basisSpezVert += 10;
              basisSpezAtk -= 10;
          } else if (wesen == Wesen.LOCKER) {
              basisSpezVert += 10;
              basisInit -= 10;
          } else if (wesen == Wesen.ERNST) {
              basisInit += 10;
              basisAtk -= 10;
          } else if (wesen == Wesen.KAUZIG) {
              basisInit += 10;
              basisVert -= 10;
          } else if (wesen == Wesen.STUR) {
              basisInit += 10;
              basisSpezAtk -= 10;
          } else if (wesen == Wesen.RUHIG) {
              basisInit += 10;
              basisSpezVert -= 10;
          } else if (wesen == Wesen.KÜHN) {
              basisSpezAtk -= 15;
              basisSpezVert -= 15;
              basisAtk += 10;
              basisVert += 10;
              basisInit += 10;
          } else if (wesen == Wesen.STOLZ) {
              basisAtk -= 10;
              basisVert -= 10;
              basisInit -= 10;
              basisSpezAtk += 15;
              basisSpezVert += 15;
          }
      }
      wesen = w;
      if (wesen != null) {
          if (wesen == Wesen.TAPFER) {
              basisAtk -= 10;
              basisVert += 10;
          } else if (wesen == Wesen.FORSCH) {
              basisAtk -= 10;
              basisSpezAtk += 10;
          } else if (wesen == Wesen.SCHEU) {
              basisAtk -= 10;
              basisSpezAtk += 10;
          } else if (wesen == Wesen.FLINK) {
              basisAtk -= 10;
              basisSpezAtk += 10;
          } else if (wesen == Wesen.BRUTAL) {
              basisVert -= 10;
              basisAtk += 10;
          } else if (wesen == Wesen.NAIV) {
              basisVert -= 10;
              basisSpezAtk += 10;
          } else if (wesen == Wesen.MUTIG) {
              basisVert -= 10;
              basisSpezVert += 10;
          } else if (wesen == Wesen.EIFRIG) {
              basisVert -= 10;
              basisInit += 10;
          } else if (wesen == Wesen.HART) {
              basisSpezAtk -= 10;
              basisAtk += 10;
          } else if (wesen == Wesen.ROBUST) {
              basisSpezAtk -= 10;
              basisVert += 10;
          } else if (wesen == Wesen.ZAGHAFT) {
              basisSpezAtk -= 10;
              basisSpezVert += 10;
          } else if (wesen == Wesen.FROH) {
              basisSpezAtk -= 10;
              basisInit += 10;
          } else if (wesen == Wesen.HITZIG) {
              basisSpezVert -= 10;
              basisAtk += 10;
          } else if (wesen == Wesen.PFIFFIG) {
              basisSpezVert -= 10;
              basisVert += 10;
          } else if (wesen == Wesen.FIES) {
              basisSpezVert -= 10;
              basisSpezAtk += 10;
          } else if (wesen == Wesen.LOCKER) {
              basisSpezVert -= 10;
              basisInit += 10;
          } else if (wesen == Wesen.ERNST) {
              basisInit -= 10;
              basisAtk += 10;
          } else if (wesen == Wesen.KAUZIG) {
              basisInit -= 10;
              basisVert += 10;
          } else if (wesen == Wesen.STUR) {
              basisInit -= 10;
              basisSpezAtk += 10;
          } else if (wesen == Wesen.RUHIG) {
              basisInit -= 10;
              basisSpezVert += 10;
          } else if (wesen == Wesen.KÜHN) {
              basisSpezAtk += 15;
              basisSpezVert += 15;
              basisAtk -= 10;
              basisVert -= 10;
              basisInit -= 10;
          } else if (wesen == Wesen.STOLZ) {
              basisAtk += 10;
              basisVert += 10;
              basisInit += 10;
              basisSpezAtk -= 15;
              basisSpezVert -= 15;
          }
          if (basisKp < 10) {
              basisKp = 10;
          }
          if (basisAtk < 10) {
              basisAtk = 10;
          }
          if (basisVert < 10) {
              basisVert = 10;
          }
          if (basisSpezAtk < 10) {
              basisSpezAtk = 10;
          }
          if (basisSpezVert < 10) {
              basisSpezVert = 10;
          }
          if (basisInit < 10) {
              basisInit = 10;
          }
      }
  }
  
  public Fähigkeit getFähigkeit(){
    return ability;
  }
  
  public boolean setStatus(Status s){
    if(s==Status.GFT&&ability==Fähigkeit.IMMUNITÄT)
        Javamon.sout(ability+" schützt "+this+" vor Vergiftung.");
    else if(s==Status.SLF&&ability==Fähigkeit.MUNTERKEIT)
        Javamon.sout(ability+" schützt "+this+" vor Einschlafen.");
    else if(s==Status.GFR&&ability==Fähigkeit.FEUERPANZER)
        Javamon.sout(ability+" schützt "+this+" vor Einfrieren.");
    else if(s==Status.PAR&&ability==Fähigkeit.FLEXIBILITÄT)
        Javamon.sout(ability+" schützt "+this+" vor Paralyse.");
    else if(s==Status.BRT&&ability==Fähigkeit.AQUAHÜLLE)
        Javamon.sout(ability+" schützt "+this+" vor Verbrennungen.");
    else{
      status=s;
      return true;
    }
    return false;
  }
  
  public void setBoostable(boolean b){
    boostable=b;
  }
  
  public void confuse(){
    if(ability==Fähigkeit.KONZENTRATION)
        Javamon.sout(ability+" schützt "+this+" vor Verwirrung.");
    else if(confused)
        Javamon.sout(this+" ist bereits verwirrt.");
    else confused=true;
  }
  
  public boolean isConfused(){
    return confused;
  }
  
  public boolean isWild(){
    return wild;
  }

  public boolean isSpielers(){
    return spielers;
  }
  
  public void gefangen(boolean spielerBesitz){
    wild=false;
    spielers=spielerBesitz;
  }
  
  public void freilasen(){
    wild=true;
    spielers=false;
  }
  
  public void rename(String s){
    spitzname=s;
  }
  
    public int getAtk() {
        return (int)(atk*(1+atkBoost));
    }

    public int getVert() {
       return (int)(vert*(1+vertBoost));
    }

    public int getSpezAtk() {
      return (int)(spezAtk*(1+spezAtkBoost));
    }

    public int getSpezVert() {
      return (int)(spezVert*(1+spezVertBoost));
    }
    
    public double getGena(){
      return gena;
    }
    
    public double getFlu(){
      return flu;
    }
    
    public int getVP(){
      return basisInit/2;
    }
    
    public int getEV(){
      return eV/100;
    }
    
    public int getWerteSumme(){
      return basisKp+basisAtk+basisVert+basisSpezAtk+basisSpezVert+basisInit+
              maxkp+atk+vert+spezAtk+spezVert+init;
    }
    
    public boolean statplus(int stat){
      switch(stat){
          case Javamon.ANGR:
              atk+=basisAtk/10;
              basisAtk++;
              break;
          case Javamon.VERT:
              vert+=basisVert/10;
              basisVert++;
              break;
          case Javamon.SPEZANGR:
              spezAtk+=basisSpezAtk/10;
              basisSpezAtk++;
              break;
          case Javamon.SPEZVERT:
              spezVert+=basisSpezVert/10;
              basisSpezVert++;
              break;
          case Javamon.INIT:
              init+=basisInit/10;
              basisInit++;
              break;
          case Javamon.KP:
              kp+=basisKp/10;
              maxkp+=basisKp/10;
              basisKp++;
              break;
      }
      return true;
    }
    
    public void erhalteEffekt(AttackenEffekt effekt){
        effekt.anwendenAuf(this);
    }
}