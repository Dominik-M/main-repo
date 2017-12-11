package bohnen;

import static bohnen.SpielListener.*;
import java.util.LinkedList;
import utils.Karte;
import utils.Kartenstapel;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Bohnanza {
    public static final boolean DEBUG=false;
    public static final int[][] COIN_VALUES={
 //       {0,0,2,3,3,3,3}, // Kidneybohne
        {0,0,2,3,3,3,3}, // Gartenbohne
        {0,0,1,2,3,4,4,4,4}, // Rote Bohne
        {0,0,1,1,2,3,4,4,4,4,4}, // Augenbohne
//        {0,0,1,1,2,3,4,4,4,4,4,4,4}, // Kaffeebohne
        {0,0,1,1,2,2,3,4,4,4,4,4,4}, // Sojabohne
        {0,0,0,1,1,2,3,4,4,4,4,4,4,4,4}, // Brechbohne
        {0,0,0,1,1,2,2,3,4,4,4,4,4,4,4,4,4}, // Saubohne
        {0,0,0,1,1,1,2,2,3,4,4,4,4,4,4,4,4,4,4}, // Feuerbohne
//        {0,0,0,1,1,1,2,2,3,3,3,4,4,4,4,4,4,4,4,4,4}, // Limabohne
        {0,0,0,0,1,1,2,2,3,3,4,4,4,4,4,4,4,4,4,4,4} // Blaue Bohne
    };
    public static final int START_FELDER_ANZ=2, RUNDEN_ANZ=3;
    public static final int MAX_FELDER_ANZ=3;
    public static final int MAX_HANDKARTEN=7;
    public static final int ZIEHKARTEN=3,HANDELKARTEN=2;
    public static final int FELDKOSTEN=3;
    public static final byte ABLEGPHASE_0=0,ABLEGPHASE_1=1,
            HANDELPHASE_0=2,HANDELPHASE_1=3, ZIEHPHASE=4,
            ERSTE_PHASE=ABLEGPHASE_0,LETZTE_PHASE=ZIEHPHASE;
    
    private final LinkedList<SpielListener> listener=new LinkedList<>();
    
    private Kartenstapel spielkarten;
    
    private Kartenstapel ablage;
    
    private final Spieler[] spieler;
    
    private int phase;
    
    private int runde;
    
    private int amZug;
    
    private boolean running=false;
    
    public Bohnanza(String spieler1, String spieler2, String spieler3, String... weitere){
        spieler=new Spieler[3+weitere.length];
        spieler[0]=new Spieler(spieler1);
        spieler[1]=new Spieler(spieler2);
        spieler[2]=new Spieler(spieler3);
        for(int n=0; n<weitere.length; n++){
            Spieler neu=new Spieler(weitere[n]);
            spieler[n+3]=neu;
        }
        ablage=new Kartenstapel();
        amZug=0;
        phase=ERSTE_PHASE;
        runde=0;
    }
    
    public void start(){
        for(int i=0; i<spieler.length; i++)
            spieler[i]=new Spieler(spieler[i].toString());
        spielkarten=new Kartenstapel();
        ablage=new Kartenstapel();
        for(Bohne sorte:Bohne.values()){
            for(int i=0; i<sorte.VALUE; i++){
                spielkarten.add(new Bohnenkarte(sorte));
            }
        }
        spielkarten.mischen();
        for (Spieler s : spieler) {
            for (int i = 0; i < Bohnanza.MAX_HANDKARTEN; i++) {
                Karte gezogen=karteZiehen();
                s.addKarte(gezogen);
                for (SpielListener sl : listener) {
                    sl.karteMoved(this, (Bohnenkarte) gezogen, STACK, s + SEPARATOR + HAND_POSTFIX);
                }
            }
        }
        amZug=-1;
        phase=ERSTE_PHASE;
        runde=0;
        nextTurn();
        running=true;
        if(DEBUG){
            System.out.println("Neues Spiel gestartet.");
            System.out.print("Spieler: (1)"+getAmZug());
            for(int i=1; i<spieler.length; i++) {
                System.out.print(", ("+(i+1)+")"+spieler[i]);
            }
            System.out.println("\nStapelgröße: "+spielkarten.size());
        }
    }
    
    public boolean isRunning(){
        return running;
    }
    
    public void println(String text){
        if(DEBUG){
            System.out.println(text);
            for(SpielListener sl: listener)sl.println(text);
        }
    }
    
    public static int getBohnenWert(Bohne sorte,int anz){
        try{
            return COIN_VALUES[sorte.ordinal()][anz];
        }catch(Exception ex){
            System.err.println("Kein Wert gefunden für "+sorte+" x "+anz+":\n"+ex);
            return 0;
        }
    }
    
    public String findKarteByID(int id){
        for(Karte k:spielkarten.getAll()){
            try{if(((Bohnenkarte)k).ID==id)return STACK;}
            catch(Exception ex){}
        }
        for(Karte k:ablage.getAll()){
            try{if(((Bohnenkarte)k).ID==id)return ABLAGE;}
            catch(Exception ex){}
        }
        for(Spieler s:spieler){
          Karte[] k=s.getKarten();
          for(int i=0; i<k.length; i++){
            try{if(((Bohnenkarte)k[i]).ID==id)return s.toString()+SEPARATOR+HAND_POSTFIX+i;}
            catch(Exception ex){}
          }
          k=s.getAblageKarten();
          for(int i=0; i<k.length; i++){
            try{if(((Bohnenkarte)k[i]).ID==id)return s.toString()+SEPARATOR+ABLAGE_POSTFIX+i;}
            catch(Exception ex){}
          }
          for(int j=0; j<s.getFelderAnz(); j++){
              k=s.getAblageKarten();
              for(int i=0; i<k.length; i++){
                try{if(((Bohnenkarte)k[i]).ID==id)return s.toString()+SEPARATOR+FELD_POSTFIX+j+" "+i;}
                catch(Exception ex){}
              }
          }
        }
        return null;
    }
    
    public final Spieler getAmZug(){
        return spieler[amZug];
    }
    
    public final int getAmZugIndex(){
        return amZug;
    }
    
    public Spieler[] getSpieler(){
        Spieler[] kopie=new Spieler[spieler.length];
        System.arraycopy(spieler,0,kopie,0,spieler.length);
        return kopie;
    }
    
    public Karte[] getStapelKarten(){
        if(spielkarten!=null)
          return spielkarten.getAll();
        else return new Karte[0];
    }
    
    public Karte[] getAblageKarten(){
        if(ablage!=null)
          return ablage.getAll();
        else return new Karte[0];
    }
    
    public int getPhase(){
        return phase;
    }
    
    /**
     * performs the given Action if it is valid.
     * @param a an Instance of Aktion, AnAbbauAktion or TauschAktion.
     * @return true if the Action has been performed.
     */
    public synchronized boolean performAktion(Aktion a){
        boolean ok=false;
        switch(a.AKTION_ID){
            case Aktion.ANBAU_AKTION:
                return performAnbau((AnAbbauAktion)a);
            case Aktion.ABBAU_AKTION:
                return performAbbau((AnAbbauAktion)a);
            case Aktion.TAUSCH_AKTION:
                if(phase==HANDELPHASE_1){
                    return performTausch((TauschAktion)a);
                }else println("Tauschen nicht möglich in der aktuellen Phase.");
            break;
            case Aktion.KARTE_ZIEHEN_AKTION:
                if (a.SOURCE.equals(getAmZug())) {
                    if (phase == ZIEHPHASE) {
                        for (int i = 0; i < ZIEHKARTEN && getAmZug().getKartenAnz() < MAX_HANDKARTEN; i++) {
                            Karte gezogen = karteZiehen();
                            println(getAmZug() + " zieht " + gezogen);
                            for (SpielListener sl : listener) {
                                sl.karteMoved(this, (Bohnenkarte) gezogen, STACK, getAmZug() + SEPARATOR + HAND_POSTFIX);
                            }
                            getAmZug().addKarte(gezogen);
                        }
                        nextPhase();
                        return true;
                    }
                    if (phase == HANDELPHASE_0) {
                        for (int i = 0; i < HANDELKARTEN; i++) {
                            Karte neu=karteZiehen();
                            getAmZug().getAblage().add(neu);
                            for(SpielListener sl: listener)sl.karteMoved(this, (Bohnenkarte) neu, STACK, getAmZug()+SEPARATOR+ABLAGE_POSTFIX);
                        }
                        nextPhase();
                        return true;
                    }
                }
            break;
            case Aktion.END_PHASE_AKTION:
                a.SOURCE.setReadyState(true);
                if (validState()) {
                    nextPhase();
                    return true;
                }
            break;
            case Aktion.FELDKAUF_AKTION:
                if(getAmZug().equals(a.SOURCE)){
                    int prevScore=a.SOURCE.getScore();
                    boolean geht=a.SOURCE.feldkauf();
                    if(prevScore!=a.SOURCE.getScore())
                        for(SpielListener sl: listener)sl.coinsAdded(a.SOURCE, a.SOURCE.getScore());
                    return geht;
                }else println(a.SOURCE+" ist nicht am Zug.");
        }
        return ok;
    }
    
    private boolean performAnbau(AnAbbauAktion a){
        String woher=findKarteByID(((Bohnenkarte)a.karte).ID);
        if(woher==null){
            println("Karte nicht gefunden");
        }
        else if(woher.contains(a.SOURCE+SEPARATOR)){
            if(woher.contains(HAND_POSTFIX)){
                if(a.SOURCE.peek().equals(a.karte)){
                    Samenplatz feld=a.SOURCE.getFeld(a.feldID);
                    if(feld.isFrei() || feld.getSorte()==((Bohnenkarte)a.karte).SORTE){
                        feld.ablegen(a.SOURCE.poll());
                        if(phase==ABLEGPHASE_0 && a.SOURCE.equals(getAmZug()))nextPhase();
                        for(SpielListener sl:listener)sl.karteMoved(this,a.karte, woher, a.SOURCE+SEPARATOR+FELD_POSTFIX+a.feldID);
                        return true;
                    }else println("Feld ist mit einer anderen Sorte belegt");
                }else println("Karte nicht an erster Stelle");
            }
            else if(woher.contains(ABLAGE_POSTFIX)){
                Samenplatz feld=a.SOURCE.getFeld(a.feldID);
                if(feld.isFrei() || feld.getSorte()==((Bohnenkarte)a.karte).SORTE){
                    feld.ablegen(a.karte);
                    a.SOURCE.getAblage().remove(a.karte);
                    for(SpielListener sl:listener)sl.karteMoved(this,a.karte, woher, a.SOURCE+SEPARATOR+FELD_POSTFIX+a.feldID);
                    return true;
                }else println("Feld ist mit einer anderen Sorte belegt");
            }else println("Karte liegt auf der falschen Position");
        }else println("Karte gehört einem anderen");
        return false;
    }
    
    private boolean performAbbau(AnAbbauAktion a){
        Karte[] feldkarten=a.SOURCE.getFeldKarten(a.feldID);
        if(feldkarten.length>0){
          int wert=Bohnanza.getBohnenWert(((Bohnenkarte)feldkarten[0]).SORTE, feldkarten.length);
          for(int i=0;i<wert;i++)a.SOURCE.addCoin();
            for (Karte k : a.SOURCE.getFeld(a.feldID).abbauen()) {
                ablage.add(k);
                for(SpielListener sl: listener)sl.karteMoved(this, (Bohnenkarte) k,a.SOURCE+SEPARATOR+FELD_POSTFIX+a.feldID, ABLAGE);
            }
            if(wert>0){
                for(SpielListener sl: listener)sl.coinsAdded(a.SOURCE, a.SOURCE.getScore());
            }
            return true;
        }
        return false;
    }
    
    private boolean performTausch(TauschAktion a){
        if(!a.SOURCE.equals(getAmZug())){
            println(a.SOURCE + " ist nicht am Zug");
        } else {
            String woher1 = findKarteByID(((Bohnenkarte) a.KARTE1).ID);
            if (woher1.startsWith(a.SOURCE.toString()) && woher1.contains(ABLAGE_POSTFIX)) {
                if (a.KARTE2 == null) {
                  a.SOURCE.getAblage().remove(a.KARTE1);
                  a.S2.getAblage().add(a.KARTE1);
                  for(SpielListener sl:listener){
                      sl.karteMoved(this,a.KARTE1,woher1, a.S2+SEPARATOR+ABLAGE_POSTFIX);
                  }
                  println(a.SOURCE+" schenkt "+a.S2+" "+a.KARTE1);
                  return true;
                } else {
                    String woher2 = findKarteByID(((Bohnenkarte) a.KARTE2).ID);
                    if (woher2.contains(a.S2.toString() + SEPARATOR + HAND_POSTFIX)) {
                        if(!a.SOURCE.getAblage().remove(a.KARTE1)){
                            println(a.KARTE1+" nicht gefunden");
                            return false;
                        }
                        a.S2.getAblage().add(a.KARTE1);
                        for(SpielListener sl:listener) sl.karteMoved(this,a.KARTE1, woher1, a.S2+SEPARATOR+ABLAGE_POSTFIX);
                        if(!a.S2.removeKarte(a.KARTE2)){
                            println(a.KARTE2+" nicht gefunden");
                            return false;
                        }
                        a.SOURCE.getAblage().add(a.KARTE2);
                        for(SpielListener sl:listener) sl.karteMoved(this,a.KARTE2,woher2, a.SOURCE+SEPARATOR+ABLAGE_POSTFIX);
                    } else if (woher2.contains(a.S2.toString() + SEPARATOR + ABLAGE_POSTFIX)) {
                        if(!a.SOURCE.getAblage().remove(a.KARTE1)){
                            println(a.KARTE1+" nicht gefunden");
                            return false;
                        }
                        a.S2.getAblage().add(a.KARTE1);
                        for(SpielListener sl:listener) sl.karteMoved(this,a.KARTE1, woher1, a.S2+SEPARATOR+ABLAGE_POSTFIX);
                        if(!a.S2.getAblage().remove(a.KARTE2)){
                            println(a.KARTE2+" nicht gefunden");
                            return false;
                        }
                        a.SOURCE.getAblage().add(a.KARTE2);
                        for(SpielListener sl:listener) sl.karteMoved(this,a.KARTE2,woher2, a.SOURCE+SEPARATOR+ABLAGE_POSTFIX);
                    }
                }
            }
        }
        return false;
    }
    
    private Karte karteZiehen(){
        if(spielkarten.peek()==null){
            while(ablage.peek()!=null){
                Karte k=ablage.pull();
                spielkarten.add(k);
                for(SpielListener s:listener)s.karteMoved(this, (Bohnenkarte) k, ABLAGE, STACK);
            }
            spielkarten.mischen();
            runde++;
            if(runde>=RUNDEN_ANZ)for(SpielListener sl: listener)sl.ende();
        }
        return spielkarten.pull();
    }
    
    /**
     * Ermittelt ob im aktuellen Zustand die Phase beendet werden kann.
     * @return
     */
    public boolean validState(){
        switch(phase){
            case ABLEGPHASE_0:
                if(getAmZug().getKartenAnz()>0){
                  println(getAmZug()+" muss noch Karten ablegen.");
                  return false;
                }else return true;
            case HANDELPHASE_0: case ZIEHPHASE:
                println(getAmZug()+" muss noch Karten ziehen.");
                return false;
            case ABLEGPHASE_1: 
                return getAmZug().isReady();
            case HANDELPHASE_1:
                for(Spieler s: spieler){
                    if(s.getAblage().size()>0){
                        println(s+" hat noch Karten auf der Ablage");
                        return false;
                    }
                }
        }
        return true;
    }
    
    private void nextPhase(){
        phase++;
        if (phase > LETZTE_PHASE) {
            phase = ERSTE_PHASE;
            nextTurn();
        }
        for (Spieler s : spieler) {
            s.setReadyState(false);
        }
        for(SpielListener sl : listener){
            sl.nextPhase(phase);
        }
    }
    
    private void nextTurn(){
        amZug++;
        if(amZug>=spieler.length)amZug=0;
        for(SpielListener sl: listener){
            sl.nextTurn(spieler[amZug]);
        }
    }
    
    public void addSpielListener(SpielListener sl){
        listener.add(sl);
    }
    
    public void removeSpielListener(SpielListener sl){
        listener.remove(sl);
    }
}