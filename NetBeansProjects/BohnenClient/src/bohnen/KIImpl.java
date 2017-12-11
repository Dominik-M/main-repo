package bohnen;

import bohnen.gui.SpielbrettKonsole;


public class KIImpl extends KI {

    public KIImpl(SpielbrettKonsole spielbrett, Spieler[] alle, int index) {
        super(spielbrett, alle, index);
    }
    
    @Override
    public void onTurn(){
        if(getMyPlayer().getScore()>Bohnanza.FELDKOSTEN && getMyPlayer().getFelderAnz()<Bohnanza.MAX_FELDER_ANZ)
            if(performFeldKauf())System.out.println(getMyPlayer()+" kauft ein neues Feld");
        if (getMyPlayer().getKartenAnz() > 0) {
            if (!performAblegAktion()) {
                performAbbauAktion(getErnteFeld());
                performAblegAktion();
            }
            while (performAblegAktion());
        }else{
            performEndPhaseAktion();
        }
        performEndPhaseAktion();
        performKarteZiehen();
        while (getMyPlayer().getAblageKarten().length > 0) {
            if (!performAblegAktion(0)) {
               performAbbauAktion(getErnteFeld());
            }
        }
        performEndPhaseAktion();
        performKarteZiehen();
    }
    
    public int getErnteFeld(){
        int max=0,index=0;
        for(int i=0; i<getMyPlayer().getFelderAnz(); i++){
          int n=Bohnanza.getBohnenWert(((Bohnenkarte)getMyPlayer().getFeldKarten(i)[0]).SORTE,getMyPlayer().getFeldKarten(i).length);
          if(n>max){
              max=n;
              index=i;
          }
        }
        return index;
    }
}