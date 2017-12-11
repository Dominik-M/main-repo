package welt.items;

public class Artefakt extends Item{
    
    public Artefakt(){
      super("unbezahlbares Artefakt",2500000,"Sehr Wertvoll!");
    }

    @Override
    public boolean benutzt() {
      return false;
    }

    @Override
    public Item clone() {
        return new Artefakt();
    }
    
}