package welt.items;

import spiel.Javamon;
import spiel.Status;
import spiel.Typ;

public class ItemBox extends javax.swing.JComboBox<Item>{
    public static final Item[] ALL_ITEMS={
          null,
          new Artefakt(),
          new Beleber(2),
          new Beleber(1),
          new Sonderbonbon(),
          new Trank(Trank.NORMAL),
          new Trank(Trank.SUPER),
          new Trank(Trank.HYPER),
          new Trank(Trank.TOP),
          new Heiler(),
          new Heiler(Status.BRT),
          new Heiler(Status.GFR),
          new Heiler(Status.GFT),
          new Heiler(Status.PAR),
          new Heiler(Status.SLF),
          new StatBonus(Javamon.ANGR),
          new StatBonus(Javamon.VERT),
          new StatBonus(Javamon.SPEZANGR),
          new StatBonus(Javamon.SPEZVERT),
          new StatBonus(Javamon.INIT),
          new StatBonus(Javamon.KP),
          new Entwickler(Typ.DRACHE),
          new Entwickler(Typ.BODEN),
          new Entwickler(Typ.EIS),
          new Entwickler(Typ.ELEKTRO),
          new Entwickler(Typ.FEUER),
          new Entwickler(Typ.FLUG),
          new Entwickler(Typ.GEIST),
          new Entwickler(Typ.GIFT),
          new Entwickler(Typ.KAMPF),
          new Entwickler(Typ.KÄFER),
          new Entwickler(Typ.METALL),
          new Entwickler(Typ.NORMAL),
          new Entwickler(Typ.PFLANZE),
          new Entwickler(Typ.WASSER),
        };
    
    public ItemBox(Item selected){
        super(ALL_ITEMS);
        this.setEditable(true);
        this.setSelectedItem(selected);
        this.setEditable(false);
    }
}