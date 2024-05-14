package konstanten;

/**
 *
 * @author Dominik
 */
public enum Monat {
    JANUAR(31),
    FEBRUAR(28),
    MÄRZ(31),
    APRIL(30),
    MAI(31),
    JUNI(30),
    JULI(31),
    AUGUST(31),
    SEPTEMBER(30),
    OKTOBER(31),
    NOVEMBER(30),
    DEZEMBER(31);
    
    public final int TAGE;
    
    Monat(int anzahlTage)
    {
        TAGE = anzahlTage;
    }
}
