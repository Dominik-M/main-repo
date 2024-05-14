package bohnen;

import utils.Karte;
import utils.Kartenstapel;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Spieler {
    private final String name;
    private final Kartenstapel hand;
    private final Samenplatz[] felder;
    private final Kartenstapel ablage;
    private int score;
    private boolean ready;
    
    public Spieler(String n){
        name=n;
        hand=new Kartenstapel();
        felder=new Samenplatz[Bohnanza.MAX_FELDER_ANZ];
        for(int i=0; i<felder.length; i++){
            felder[i]=new Samenplatz();
            if(i<Bohnanza.START_FELDER_ANZ)felder[i].setActive(true);
        }
        ablage=new Kartenstapel();
        score=0;
        ready=false;
    }
    
    void setReadyState(boolean fertig){
        ready=fertig;
    }
    
    void addCoin(){
        score++;
    }
    
    void addKarte(Karte k){
        hand.add(k);
    }
    
    boolean removeKarte(Karte k){
        return hand.remove(k);
    }
    
    Karte poll(){
        return hand.pull();
    }
    
    /**
     * Führt peek() auf den Hand Stack aus.
     * @return Die erste Karte auf der Hand oder null, wenn keine vorhanden.
     */
    public Karte peek(){
        return hand.peek();
    }
    
    public int getScore(){
        return score;
    }
    
    boolean feldkauf(){
        for(Samenplatz feld:felder){
            if(!feld.isActive()){
                if(score>=Bohnanza.FELDKOSTEN){
                    score-=Bohnanza.FELDKOSTEN;
                    feld.setActive(true);
                    return true;
                }else{
                    if(Bohnanza.DEBUG)System.out.println(this+" hat nicht genug Geld.");
                    return false;
                }
            }
        }
        if(Bohnanza.DEBUG)System.out.println(this+" besitzt schon die maximale Anzahl Felder.");
        return false;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    /**
     * Prüft ob der Spieler mit dieser Phase fertig ist.
     * @return true, wenn der Spieler die aktuelle Phase abschließen möchte.
     */
    public boolean isReady(){
        return ready;
    }
    
    /**
     * Liefert ein Array mit allen Karten auf der Hand.
     * @return der Rückgabewert von hand.getAll()
     */
    public Karte[] getKarten(){
        return hand.getAll();
    }
    
    /**
     * Liefert ein Array mit allen Karten auf der Ablage.
     * @return der Rückgabewert von ablage.getAll()
     */
    public Karte[] getAblageKarten(){
        return ablage.getAll();
    }
    
    Kartenstapel getAblage(){
        return ablage;
    }
    
    /**
     * Liefert ein Array mit allen Karten auf dem feld i.
     * Achtet nicht darauf ob das Feld auch aktiv ist!
     * @param i index des Feldes, darf nicht negativ sein und muss kleiner sein als MAX_FELDER_ANZ.
     * @return alle Karten auf felder[i]
     */
    public Karte[] getFeldKarten(int i){
        return felder[i].getKarten();
    }
    
    Samenplatz getFeld(int i){
        return felder[i];
    }
    
    public int getKartenAnz(){
        return hand.size();
    }
    
    /**
     * Zählt alle aktiven Felder.
     * @return Anzahl aller aktiven Felder
     */
    public int getFelderAnz(){
        int anz=0;
        for(Samenplatz s:felder)if(s.isActive())anz++;
        return anz;
    }
}
