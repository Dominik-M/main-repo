package welt.objekte;

import spiel.Javamon;
import spiel.OutputListener;

public class Münzhändler extends Person implements OutputListener{
    
  public Münzhändler(int blickrichtung){
    super(blickrichtung);
  }
  
  public Münzhändler(){}
    
    @Override
  public void benutzt(){
    super.benutzt();
    Javamon.sout("Brauchst du Spielmünzen? #Eine Münze kostet 20 Geld.");
    Javamon.addListener(this);
  }

    @Override
    public void ausgabeFertig() {
      int anz;
      try{
        anz = Integer.parseInt(javax.swing.JOptionPane.showInputDialog("Wie viele Münzen?"));
        if(anz<=0)throw new java.lang.NumberFormatException();
      }catch(Exception ex){
        Javamon.sout("Da ist was schief gelaufen...");
        return;
      }
      spiel.Spieler spieler=Javamon.getSpieler();
      if(spieler.giveGeld(-20*anz)){
        Javamon.sout(spieler+" erhält "+anz+" Münzen.");
        if(!spieler.addMünzen(anz)) Javamon.sout("Dein Münzkorb ist voll.");
      }else{
        Javamon.sout("Du hast zu wenig Geld.");
      }
    }
}