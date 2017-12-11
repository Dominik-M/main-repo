package spiel;

import java.io.IOException;
import java.io.PrintWriter;

public enum Typ {
  NORMAL(0),FEUER(1),WASSER(2),PFLANZE(3),EIS(4),GEIST(5),  ELEKTRO(6),
  GESTEIN(7),BODEN(7),KÄFER(8),GIFT(8),PSYCHO(9),DRACHE(10),FLUG(10),
  KAMPF(11),METALL(12),UNLICHT(13);
  
  public final int index;
  
  Typ(int i){
    index=i;
  }
  
  public static void printAll(PrintWriter writer) throws IOException{
        for(Typ t:values()) {
            writer.println(t.name());
        }
    }
}