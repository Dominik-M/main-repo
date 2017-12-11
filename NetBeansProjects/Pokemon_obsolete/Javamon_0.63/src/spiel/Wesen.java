package spiel;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public enum Wesen {
  TAPFER("Geringerer Angriff, starke Verteidigung"),
  FORSCH("Weniger Angriff, höherer Spezialangriff"),
  SCHEU("Wenig Angriff, höhere Spezialverteidigung"),
  FLINK("Schwacher Angriff, hohe Initiative"),
  BRUTAL("Wenig Verteidigung, höhere Angriffswerte"),
  NAIV("Schwächere Verteidigung, hoher Spezialangriff"),
  MUTIG("Geringe Verteidigung, hohe Spezialverteidigung"),
  EIFRIG("Weniger Verteidigung, hohe Initiative"),
  HART("Weniger Spezialangriff, stärkerer Angriff"),
  ROBUST("Geringer Spezialangriff, hohe Verteidigung"),
  ZAGHAFT("Wenig Spezialangriff, hohe Spezialverteidigung"),
  FROH("Geringerer Spezialangriff, höhere Initiative"),
  HITZIG("Geringere Spezialverteidigung, hoher Angriff"),
  PFIFFIG("Wenig Spezialverteidigung, hohe Verteidigung"),
  FIES("Geringe Spezialverteidigung, hoher Spezialangriff"),
  LOCKER("Wenig Spezialverteidigung, höhere Initiative"),
  ERNST("Geringere Initiative, höherer Angriff"),
  KAUZIG("Wenig Initiative, starke Verteidigung"),
  STUR("Wenig Initiative, starker Spezialangriff"),
  RUHIG("Geringe Initiative, hohe Spezialverteidigung"),
  SANFT("Neutral"),
  MILD("Neutral"),
  SACHT("Neutral"),
  STILL("Neutral"),
  KÜHN("Hohe Spezialwerte"),
  STOLZ("Hohe Werte");
  
    
  public final String tooltip;
  
  private Wesen(String s){
    tooltip=s;
  }
  
  public static Wesen gibZufallWesen(){
    int index=(int)(Math.random()*values().length);
    return values()[index];
  }
  
  public static void printAll(PrintWriter writer) throws IOException{
        for(Wesen w:values()) {
            writer.println(w+" "+w.tooltip);
        }
    }
}