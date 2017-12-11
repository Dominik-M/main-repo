package welt.items;

import spiel.Javamon;
import spiel.Status;

public class Beleber extends Item{
    private int p;
    
    /**
     * 
     * @param teil soll >=1 sein. Anteil an KP. 1=voll.
     */
    public Beleber(int teil){
      super("Beleber",3000/teil,"Belebt ein besiegtes Pokemon wieder");
      p=teil;
      if(teil==1) name="Top-Beleber";
    }

    @Override
    public boolean benutzt() {
      pokemon.Pokemon pok=new spiel.PokAuswahl(spiel.Javamon.getSpieler().getPoks()).getAuswahl();
      if(pok!=null){
        if(pok.isBsg()){
          pok.setStatus(Status.OK);
          pok.heal(pok.getMaxKP()/p);
          Javamon.sout(pok+" wurde wiederbelebt.");
          return true;
        }else Javamon.sout("Das hätte keinen Effekt");
      }
      return false;
    }
    
    @Override
    public Beleber clone(){
        return new Beleber(p);
    }
}