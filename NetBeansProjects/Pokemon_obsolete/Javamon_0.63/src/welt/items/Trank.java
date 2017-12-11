package welt.items;

public class Trank extends Item{
  public static final int NORMAL=50,SUPER=150,HYPER=400,TOP=999;
  private int p;
    
  public Trank(int power){
    super("Trank("+power+")",4*power,"Stellt bis zu "+power+" KP wieder her.");
    p=power;
    if(p==NORMAL)name="Trank";
    else if(p==SUPER)name="Supertrank";
    else if(p==HYPER)name="Hypertrank";
    else if(p==TOP){
        name="Top-Trank";
        info="Stellt alle KP wieder her.";
    }
  }

    @Override
    public boolean benutzt() {
      pokemon.Pokemon pok=new spiel.PokAuswahl(spiel.Javamon.getSpieler().getPoks()).getAuswahl();
      if(pok!=null){
        if(p==TOP)return pok.heal(pok.getMaxKP());
        return pok.heal(p);
      }else return false;
    }

    @Override
    public Item clone() {
        return new Trank(p);
    }
}