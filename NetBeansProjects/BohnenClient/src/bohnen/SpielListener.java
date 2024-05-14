package bohnen;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public interface SpielListener {
    public static final String STACK="stapel",ABLAGE="ablage",FELD_POSTFIX="f",
            ABLAGE_POSTFIX="a",HAND_POSTFIX="h",SEPARATOR=".";
    
    public void ende();
    
    public void nextTurn(Spieler onTurn);
    
    public void nextPhase(int phase);
    
    public void coinsAdded(Spieler s, int anz);
    
    public void println(String text);
    
    /**
     * Tells that a Karte has been moved to another position.
     * The String params represent the name of a position e.g. "stack" for the Card Stack 
     * or in form of PlayerName+"."+POSTFIX+INDEX if the position depends on a specified player.
     * @param source the instance that called this method
     * @param k moved Karte
     * @param from where the Karte comes from
     * @param to where the Karte goes to
     */
    public void karteMoved(Object source,Bohnenkarte k,String from,String to);
    
}