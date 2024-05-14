package bohnen;

import utils.Karte;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Bohnenkarte extends Karte{
    
    private static int nextID=0;
    
    public final Bohne SORTE;
    public final int ID;

    public Bohnenkarte(Bohne bohnensorte) {
        super(bohnensorte.NAME);
        SORTE=bohnensorte;
        ID=nextID;
        nextID++;
    }
    
    @Override
    public String toString(){
        return super.toString();
    }
}