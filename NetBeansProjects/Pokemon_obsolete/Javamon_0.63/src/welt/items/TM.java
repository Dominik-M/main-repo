package welt.items;

public class TM extends Item{
    private attacken.Attacke att;
    
    public TM(attacken.Attacke attacke){
      super("TM: "+attacke.toString(),1000+50*attacke.getDmg(),attacke.getToolTipText());
      att=attacke;
    }

    @Override
    public boolean benutzt() {
      pokemon.Pokemon pok=new spiel.PokAuswahl(spiel.Javamon.getSpieler().getPoks()).getAuswahl();
      if(pok!=null){
        pok.lerne((attacken.Attacke)att.klone()); 
        return true;
      }else return false;
    }

    @Override
    public Item clone() {
      return new TM(att);
    }
}