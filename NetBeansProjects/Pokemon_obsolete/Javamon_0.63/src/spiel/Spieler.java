package spiel;

import pokemon.Pokemon;

public class Spieler extends Trainer{
   Münzkorb münzkorb;
   int x,y,pokedex;
   java.util.ArrayList<welt.items.Item> items=new java.util.ArrayList<>();
    
  public Spieler(int X,int Y){
    super(null,0,DOWN);
    x=X;
    y=Y;
    münzkorb=new Münzkorb();
  }
    
  public void addItem(welt.items.Item i){
    items.add(i);
  }
  
  public void addItem(welt.items.Item it,int n){
    for(int i=0;i<n;i++)addItem(it);
  }
  
  public welt.items.Item[] getitems(){
    welt.items.Item[] i=new welt.items.Item[items.size()];
    for(int j=0;j<i.length;j++){
      i[j]=items.get(j);
    }
    return i;
  }
  
  public void removeItem(int index){
    items.remove(index);
  }
  
  public boolean addMünzen(int anz){
    return münzkorb.addMünzen(anz);
  }
  
  public int getMünzen(){
    return münzkorb.getMünzen();
  }
  
  public int hasKey(int keyNr){
    for(int i=0;i<items.size();i++){
      try{
        if(((welt.items.Schlüssel)items.get(i)).NR==keyNr)return i; 
      }catch(Exception ex){continue;}
    }
    return -1;
  }
  
    @Override
  public boolean givePokemon(Pokemon pok){
    for(int i=0;i<pokemon.length;i++){
      if(pokemon[i]==null){
        pokemon[i]=pok;
        pok.gefangen(true);
        return true;
      }
    }
    return false;
  }
}