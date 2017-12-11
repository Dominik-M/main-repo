package utils;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Kartenstapel {
    private final LinkedList<Karte> stapel;
    
    public Kartenstapel(Karte... karten){
        stapel=new LinkedList<>();
        stapel.addAll(Arrays.asList(karten));
    }
    
    public void add(Karte neu){
        stapel.add(neu);
    }
    
    public void push(Karte neu){
        stapel.push(neu);
    }
    
    public Karte peek(){
        return stapel.peek();
    }
    
    public Karte pull(){
        return stapel.poll();
    }
    
    public boolean remove(Karte k){
        return stapel.remove(k);
    }
    
    public Karte[] getAll(){
        Karte[] karten=new Karte[stapel.size()];
        for(int i=0;i<karten.length;i++){
            karten[i]=stapel.get(i);
        }
        return karten;
    }
    
    public void mischen(){
        for(int i=0; i<stapel.size()*5; i++){
            int random=(int) (Math.random()*stapel.size());
            Karte merk=stapel.get(random);
            stapel.set(random,stapel.peek());
            stapel.removeFirst();
            stapel.add(merk);
        }
    }
    
    public int size(){
        return stapel.size();
    }
}
