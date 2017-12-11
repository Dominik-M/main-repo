package welt.items;

public class Schlüssel extends Item{
    public final int NR;
    
    public Schlüssel(int nr){
      super("Schlüssel NR."+nr,0,"Öffnet Türen");
      NR=nr;
    }

    @Override
    public boolean benutzt() {
      return false;
    }

    @Override
    public Item clone() {
        return new Schlüssel(NR);
    }
}