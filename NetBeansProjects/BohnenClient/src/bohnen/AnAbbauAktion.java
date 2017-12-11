package bohnen;

/**
 *
 * @author Dundun
 */
public class AnAbbauAktion extends Aktion{
    public final int feldID;
    public final Bohnenkarte karte;
    
    public AnAbbauAktion(Spieler source,int feldnummer){
      super(source,Aktion.ABBAU_AKTION);
      feldID=feldnummer;
      karte=null;
    }
    
    public AnAbbauAktion(Spieler source, int feldnummer, Bohnenkarte neu){
        super(source,Aktion.ANBAU_AKTION);
        feldID=feldnummer;
        karte=neu;
    }
}