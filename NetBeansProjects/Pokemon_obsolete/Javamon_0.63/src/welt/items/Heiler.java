package welt.items;

public class Heiler extends Item{
  spiel.Status stat;
    
  public Heiler(){
    super("Hyperheiler",400,"Heilt alle Statusveränderungen");
  }
  
  public Heiler(spiel.Status status){
    super("Hyperheiler",200,"Heilt alle Statusveränderungen");
    stat=status;
    if(stat==spiel.Status.BRT){
      name="Feuerheiler";
      info="Heilt Verbrennungen";
    }
    else if(stat==spiel.Status.GFR){
      name="Eisheiler";
      info="Befreit von Frost";
    }
    else if(stat==spiel.Status.GFT){
      name="Gegengift";
      info="Heilt Vergiftung";
    }
    else if(stat==spiel.Status.PAR){
      name="Anti-Paralyse";
      info="Heilt von Paralyse";
    }
    else if(stat==spiel.Status.SLF){
      name="Aufwecker";
      info="Heilt von Schlaf";
    }
  }

  @Override
  public boolean benutzt() {
    pokemon.Pokemon pok=new spiel.PokAuswahl(spiel.Javamon.getSpieler().getPoks()).getAuswahl();
    if(pok!=null){
      if(stat==spiel.Status.BRT&&pok.getStatus()==stat){
        pok.setStatus(spiel.Status.OK);
        return true;
      }
      else if(stat==spiel.Status.GFR&&pok.getStatus()==stat){
        pok.setStatus(spiel.Status.OK);
        return true;
      }
      else if(stat==spiel.Status.GFT&&pok.getStatus()==stat){
        pok.setStatus(spiel.Status.OK);
        return true;
      }
      else if(stat==spiel.Status.PAR&&pok.getStatus()==stat){
        pok.setStatus(spiel.Status.OK);
        return true;
      }
      else if(stat==spiel.Status.SLF&&pok.getStatus()==stat){
        pok.setStatus(spiel.Status.OK);
        return true;
      }else if(pok.getStatus()!=spiel.Status.OK &&pok.getStatus()!=spiel.Status.BSG){
        pok.setStatus(spiel.Status.OK);
        return true; 
      }
    }
    return false;
  }

    @Override
    public Item clone() {
      if(stat==null)return new Heiler();
      else return new Heiler(stat);
    }
}