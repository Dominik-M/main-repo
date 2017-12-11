package attacken;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import pokemon.Pokemon;
import spiel.Javamon;
import spiel.Status;

/**
 *
 * @author Dundun
 */
public enum AttackenEffekt {
    ATK_UP(true,100,"Steigert ATK Wert."),ATK_UP_STARK(true,100,"Steigert ATK Wert stark."),
    ATK_DWN(false,100,"Senkt ATK Wert des Gegners."),ATK_DWN_STARK(false,100,"Senkt ATK Wert des Gegners stark."),
    OK(true,100),GFT(false,100),PAR(false,100),SLF(false,100),BRT(false,100),GFR(false,100),BSG(false,100),
    GFT_10(false,10),GFT_20(false,20),PAR_20(false,20),PAR_15(false,15),BRT_10(false,10),BRT_20(false,20),
    CONFUSE(false,100),CONFUSE_10(false,10);
    
    public final boolean SELBST;
    public final int WERT;
    public final String TOOLTIP;

    AttackenEffekt(boolean ziel,int wert){
        this(ziel,wert,"");
    }
    
    AttackenEffekt(boolean ziel,int wert,String tooltip){
        SELBST=ziel;
        WERT=wert;
        TOOLTIP=tooltip;
    }
    
    public void anwendenAuf(Pokemon ziel){
        // erzeuge Zahl in [0;100[
        int zufall=(int)(Math.random()*100);
        switch(this){
            case ATK_DWN:
                if (zufall < WERT) {
                  if (ziel.boostAtk(-1)) 
                    Javamon.sout("Angriff von " + this.toString() + " sinkt.");
                  else
                    Javamon.sout("Angriff von " + this.toString() + " kann nicht weiter gesenkt werden.");
                }
                break;
            case ATK_DWN_STARK:
                if(zufall<WERT){
                  if(ziel.boostAtk(-2))
                    Javamon.sout("Angriff von "+this.toString()+" sinkt stark!");
                  else 
                    Javamon.sout("Angriff von "+this.toString()+" kann nicht weiter gesenkt werden.");
                }
                break;
            case ATK_UP:
                if(zufall<WERT){
                  if(ziel.boostAtk(1))
                    Javamon.sout("Angriff von "+this.toString()+" nimmt zu.");
                  else
                    Javamon.sout("Angriff von "+this.toString()+" kann nicht weiter gesteigert werden.");
                }
                break;
            case ATK_UP_STARK:
                if(zufall<WERT){
                  if(ziel.boostAtk(2))
                    Javamon.sout("Angriff von "+this.toString()+" steigt stark!");
                  else
                    Javamon.sout("Angriff von "+this.toString()+" kann nicht weiter gesteigert werden.");
                }
                break;
            case GFT: case GFT_20: case GFT_10:
                if(zufall<WERT)
                    ziel.setStatus(Status.GFT);
                break;
            case PAR: case PAR_15: case PAR_20:
                if(zufall<WERT)
                    ziel.setStatus(Status.PAR);
                break;
            case SLF:
                if(zufall<WERT)
                    ziel.setStatus(Status.SLF);
                break;
            case BRT: case BRT_10: case BRT_20:
                if(zufall<WERT)
                    ziel.setStatus(Status.BRT);
                break;
            case CONFUSE: case CONFUSE_10:
                if(zufall<WERT)
                    ziel.confuse();
        }
    }
    
    public static void printAll(PrintWriter writer) throws IOException{
        for(AttackenEffekt a:values()) {
            writer.println(a+" "+a.TOOLTIP);
        }
    }
}