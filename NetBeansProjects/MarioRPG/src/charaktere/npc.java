package charaktere;

public abstract class npc {
  int id,lvl,ep,geld,hp,atk,def,matk,mdef;
  String name;
  boolean freund;
  
  public npc(int pid,String n){
    name=n;
    id=pid;
    lvl=1;
    hp=1;
  }
}