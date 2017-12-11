package spiel;

import pokemon.Pokemon;

public class Trainer implements java.io.Serializable{
  public static final int UP=-2,LEFT=-1,RIGHT=1,DOWN=2;
  int richtung;
  Pokemon[] pokemon=new Pokemon[Javamon.POKE_ANZ];
  private int geld,genesungen=0;
  String name,dialog1,dialog2;
  
  public Trainer(String n,int g,int blickrichtung, Pokemon... poks){
    name=n;
    geld=g;
    richtung=blickrichtung;
    dialog1="Du hast keine Chance!";
    dialog2="Ich habe versagt...";
    for(int i=0;i<poks.length && i<pokemon.length;i++){
      pokemon[i]=poks[i];
      poks[i].gefangen(false);
    }
  }
  
  /**
   * Lässt den aktuellen Trainer ein Pokemon erhalten, wenn er noch Platz hat.
   * @param pok Das zu erhaltende Pokemon
   * @return Ob der Trainer noch Platz hatte und das Pokemon erhielt
   */
  public boolean givePokemon(Pokemon pok){
    for(int i=0;i<pokemon.length;i++){
      if(pokemon[i]==null){
        pokemon[i]=pok;
        pok.gefangen(false);
        return true;
      }
    }
    return false;
  }
  
  public boolean removePok(int index){
    int anz=0;
    for(int i=0;i<pokemon.length;i++){
      if(pokemon[i]==null)continue;
      anz++;
    }
    if(anz<=1)return false;
    pokemon[index].freilasen();
    pokemon[index]=null;
    for(int i=index;i<pokemon.length-1;i++){
      tausche(i,i+1);
    }
    return true;
  }
  
  public Pokemon[] getPoks(){
    return pokemon;
  }
  
  public void setPoks(Pokemon[] poks){
      pokemon=poks;
  }
  
  public int getRichtung(){
    return richtung;
  }
  
  public void tausche(int i,int j){
    Pokemon merk=pokemon[i];
    pokemon[i]=pokemon[j];
    pokemon[j]=merk;
  }
  
  public boolean isBesiegt(){
    for(Pokemon pok:pokemon){
      if(pok==null) continue;
      if(!pok.isBsg()) return false;
    }
    return true;
  }
  
  public boolean isOK(){
    for(Pokemon pok:pokemon){
      if(pok==null)continue;
      else if(!pok.isOK())return false;
    }
    return true;
  }
  
  public void heile(){
    for(Pokemon pok: pokemon){
      if(pok!=null) pok.heal();
    }
  }
  
    @Override
  public String toString(){
    return name;
  }
  
  public boolean giveGeld(int n){
    if(geld+n<0)return false;
    else geld+=n;
    return true;
  }
  
  public int getGeld(){
      return geld;
  }
  
  public final int getGenesungen(){
      return genesungen;
  }
  
  public final boolean benutzGenesung(){
      if(genesungen>0){
          genesungen--;
          return true;
      }
      return false;
  }
  
  public String getDialog1(){
    return dialog1;
  }
  
  public void setDialog1(String text){
    dialog1=text;
  }
  
  public String getDialog2(){
    return dialog2;
  }
  
  public void setDialog2(String text){
    dialog2=text;
  }
}