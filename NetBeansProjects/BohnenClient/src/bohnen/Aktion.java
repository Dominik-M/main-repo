package bohnen;

public class Aktion {
    public final int AKTION_ID;
    public final Spieler SOURCE;
    public static final int END_PHASE_AKTION=0,KARTE_ZIEHEN_AKTION=1,
            ABBAU_AKTION=2,ANBAU_AKTION=3,TAUSCH_AKTION=4,FELDKAUF_AKTION=5;
    
    public Aktion(Spieler source, int id){
        SOURCE=source;
        AKTION_ID=id;
    }
}