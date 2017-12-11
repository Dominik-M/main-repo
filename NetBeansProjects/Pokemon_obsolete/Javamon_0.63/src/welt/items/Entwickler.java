package welt.items;

import spiel.Typ;

public class Entwickler extends Item{
    public final Typ typ;
    
    public Entwickler(Typ sorte){
      super("Stein",15000,"Sorgt bei bestimmten Pokemon für Entwicklung");
      typ=sorte;
      if(typ==Typ.DRACHE) name="Drachenhaut";
      else if(typ==Typ.ELEKTRO) name="Donnerstein";
      else if(typ==Typ.FEUER) name="Feuerstein";
      else if(typ==Typ.FLUG) name="Goldschwinge";
      else if(typ==Typ.PFLANZE) name="Blattstein";
      else if(typ==Typ.WASSER) name="Wasserstein";
      else if(typ==Typ.KÄFER) name="Chitinpanzer";
      else if(typ==Typ.KAMPF) name="Machoband";
      else if(typ==Typ.BODEN) name="Pudersand";
      else if(typ==Typ.EIS) name="Ewiges Eis";
      else if(typ==Typ.GEIST) name="Ektoplasma";
      else if(typ==Typ.GIFT) name="Zigaretten";
      else if(typ==Typ.METALL) name="Titan";
      else if(typ==Typ.NORMAL) name="Mondstein";
    }

    @Override
    public boolean benutzt() {
      pokemon.Pokemon pok=new spiel.PokAuswahl(spiel.Javamon.getSpieler().getPoks()).getAuswahl();
      if(pok==null)return false;
      return pok.gibItem(this);
    }

    @Override
    public Item clone() {
        return new Entwickler(typ);
    }
}