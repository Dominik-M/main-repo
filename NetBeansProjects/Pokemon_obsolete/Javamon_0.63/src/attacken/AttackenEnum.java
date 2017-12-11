package attacken;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.LinkedList;
import spiel.Typ;

/**
 *
 * @author Dundun
 */
public enum AttackenEnum {
    // Boden
    
    // Drache
    
    // Eis
    
    // Elektro
    
    // Feuer
    
    // Flug
    
    // Geist
    
    // Kampf
    
    // Käfer
    
    // Metall
    
    // Normal
    TACKLE(40,35,95,true,Typ.NORMAL);
    
    // Pflanze
    
    // Psycho
    
    // Unlicht
    
    // Wasser
    
    public final String TOOLTIP;
    public final int DMG,AP,GENA;
    public final boolean PHYS;
    public final Typ TYP;
    public final LinkedList<AttackenEffekt> EFFEKTE;
    
    AttackenEnum(int dmg,int ap, int gena,boolean phys,Typ typ){
        this(dmg,ap,gena,phys,typ,"");
    }
    
    AttackenEnum(int dmg,int ap, int gena,boolean phys,Typ typ,String tooltip,AttackenEffekt... effekte){
        DMG=dmg;
        AP=ap;
        GENA=gena;
        PHYS=phys;
        TYP=typ;
        TOOLTIP=tooltip;
        EFFEKTE=new LinkedList();
        EFFEKTE.addAll(Arrays.asList(effekte));
    }
    
    public static void printAll(PrintWriter writer) throws IOException{
        for(AttackenEnum a:values()) {
            writer.println(a+" "+a.TOOLTIP);
        }
    }
}