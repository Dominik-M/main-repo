package welt.items;

public class Sonderbonbon extends Item{
    
    public Sonderbonbon(){
      super("Sonderbonbon",19500,"Lässt ein Pokemon ein Level aufsteigen");
    }

    @Override
    public boolean benutzt() {
      pokemon.Pokemon pok=new spiel.PokAuswahl(spiel.Javamon.getSpieler().getPoks()).getAuswahl();
      if(pok==null||pok.isBsg())return false;
      pokemon.Pokemon neu=pok.levelUp();
      if(neu!=null && 
        javax.swing.JOptionPane.showConfirmDialog(null,"Hey! "+pok.toString()+" will sich entwickeln!")
        ==javax.swing.JOptionPane.OK_OPTION){
        spiel.Trainer spieler=spiel.Javamon.getSpieler();
        for(int i=0;i<spieler.getPoks().length;i++){
          if(spieler.getPoks()[i].equals(pok)){
            spiel.Javamon.sout(pok.toString()+" entwickelt sich zu "+neu.toString());
            spieler.getPoks()[i]=neu;
            break;
          }
        }
      }
      return true;
    }

    @Override
    public Item clone() {
        return new Sonderbonbon();
    }
}