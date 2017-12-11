package spiel;

import java.io.IOException;
import java.io.PrintWriter;

public enum Fähigkeit {
    ADLERAUGE("Verhindert Verlust von Genauigkeit"),
    AQUAHÜLLE("Verhindert Verbrennung"),
    EXPIDERMIS("Heilt Statusprobleme"),
    STATIK("Paralysiert Gegner evtl. bei Berührung"),
    IMMUNITÄT("Verhindert Vergiftung"),
    FEUERPANZER("Verhindert Einfrieren"),
    FLEXIBILITÄT("Verhindert Paralyse"),
    KONZENTRATION("Verhindert Verwirrung"),
    MUNTERKEIT("Verhindert Einschlafen"),
    SCHWEBE("Immunität gegen Boden-Attacken"),
    WUNDERWACHE("Nur sehr effektive Attacken schaden");
    
    public final String tooltip;
    
    Fähigkeit(String beschreibung){
      tooltip=beschreibung;
    }
    
    public static void printAll(PrintWriter writer) throws IOException{
        for(Fähigkeit f:values()) {
            writer.println(f+" "+f.tooltip);
        }
    }
}