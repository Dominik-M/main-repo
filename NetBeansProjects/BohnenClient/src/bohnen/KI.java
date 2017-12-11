package bohnen;

import bohnen.gui.SpielbrettKonsole;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public abstract class KI implements SpielListener{
  
  protected final Spieler[] alleSpieler;
  private final int mSpielerIndex;
  private final SpielbrettKonsole spiel;
  
  public KI(SpielbrettKonsole spielbrett,Spieler[] alle,int index){
      alleSpieler=alle;
      mSpielerIndex=index;
      spiel=spielbrett;
  }
  
  public boolean performAktion(String in){
      if(Bohnanza.DEBUG)System.out.println(getMyPlayer()+": "+in);
      sleep(100/spiel.getSpeed());
      boolean geht=spiel.dispatchInput(getMyPlayer(),in);
      spiel.updateUI();
      return geht;
  }
  
  public boolean performAblegAktion(){
      return performAktion("drop");
  }
  
  public boolean performAblegAktion(int index){
      return performAktion("drop"+ABLAGE_POSTFIX+index);
  }
  
  public boolean performAbbauAktion(int feldID){
      return performAktion("ernte"+feldID);
  }
  
  public boolean performEndPhaseAktion(){
      return performAktion("next");
  }
  
  public boolean performFeldKauf(){
      return performAktion("buy");
  }
  
  public boolean performKarteZiehen(){
      return performAktion("pull");
  }
  
  public Spieler getMyPlayer(){
      return alleSpieler[mSpielerIndex];
  }
  
  public void sleep(int millis){
      try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(SpielbrettKonsole.class.getName()).log(Level.SEVERE, null, ex);
        }
  }

    @Override
    public void nextTurn(Spieler onTurn) {
        if(onTurn.equals(getMyPlayer())){
            new Thread(new Runnable() {

                @Override
                public void run() {
                  onTurn();
                }
            }).start();
        }
    }
    
    public abstract void onTurn();
    
    @Override
    public void nextPhase(int phase) {
        
    }

    @Override
    public void ende() {
    }

    @Override
    public void karteMoved(Object source, Bohnenkarte k, String from, String to) {
    }

    @Override
    public void coinsAdded(Spieler s, int anz) {
        
    }

    @Override
    public void println(String text) {
        
    }
}