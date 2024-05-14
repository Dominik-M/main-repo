package bohnen;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class TauschAktion extends Aktion{
    public final Bohnenkarte KARTE1, KARTE2;
    public final Spieler S2;
    
    public TauschAktion(Spieler s1, Bohnenkarte k1,Spieler s2, Bohnenkarte k2) {
        super(s1, TAUSCH_AKTION);
        KARTE1=k1;
        KARTE2=k2;
        S2=s2;
    }
}