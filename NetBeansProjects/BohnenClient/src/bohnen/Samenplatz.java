package bohnen;

import utils.Karte;
import utils.Kartenstapel;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Samenplatz {
    private boolean active;
    private final Kartenstapel bohnen;
    
    public Samenplatz(){
        active=false;
        bohnen=new Kartenstapel();
    }
    
    public boolean isFrei(){
        return active && bohnen.size()==0;
    }
    
    public boolean isActive(){
        return active;
    }
    
    void setActive(boolean aktiv){
        active=aktiv;
    }
    
    public Bohne getSorte(){
        if(bohnen.size()>0){
            return ((Bohnenkarte)bohnen.peek()).SORTE;
        }else{
            return null;
        }
    }
    
    Karte[] abbauen(){
        Karte[] karten=new Karte[bohnen.size()];
        for(int i=0; i<karten.length; i++){
            karten[i]=bohnen.pull();
        }
        return karten;
    }
    
    void ablegen(Karte k){
        bohnen.push(k);
    }
    
    public Karte[] getKarten(){
        return bohnen.getAll();
    }
}