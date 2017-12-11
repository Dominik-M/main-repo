package welt.items;

import spiel.Javamon;

public class StatBonus extends Item{
    private int status;
    
    public StatBonus(int stat){
      super(null,5000,null);
      status=stat;
      switch(stat){
          case Javamon.ANGR:
            name="Protein";
            info="Steigert den Angriffswert";
            break;
          case Javamon.VERT:
            name="Eisen";
            info="Steigert den Verteidigungswert";
            break;
          case Javamon.SPEZANGR:
            name="Kalzium";
            info="Steigert den Spezial-Angriffswert";
            break;
          case Javamon.SPEZVERT:
            name="Zink";
            info="Seigert den Spezial-Verteidigungswert";
            break;
          case Javamon.INIT:
            name="Carbon";
            info="Steigert den Initiativewert";
            break;
          case Javamon.KP:
            name="Magnesium";
            info="Erhöht die maximal-KP";
            break;
      }
    }

    @Override
    public boolean benutzt() {
      pokemon.Pokemon pok=new spiel.PokAuswahl(spiel.Javamon.getSpieler().getPoks()).getAuswahl();
      if(pok!=null){
        return pok.statplus(status);
      }else return false;
    }   

    @Override
    public Item clone() {
      return new StatBonus(status);
    }
}