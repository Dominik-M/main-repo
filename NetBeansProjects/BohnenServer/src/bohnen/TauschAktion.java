package bohnen;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class TauschAktion extends Aktion{
    public final Bohnenkarte KARTE1, KARTE2;
    public final Spieler S2;
    
    /**
     * Eine Tausch Aktion beinhaltet Informationen über einen durchzuführenden Tausch.
     * 
     * @param s1 Der Spieler, der den Tausch anbietet
     * @param k1 Die gebotene Karte (darf nicht null sein)
     * @param s2 Tauschpartner
     * @param k2 Die von s2 im Austausch gebotene Karte, wenn null wird k1 verschenkt.
     */
    public TauschAktion(Spieler s1, Bohnenkarte k1,Spieler s2, Bohnenkarte k2) {
        super(s1, TAUSCH_AKTION);
        KARTE1=k1;
        KARTE2=k2;
        S2=s2;
    }
}