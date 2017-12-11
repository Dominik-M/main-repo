package tools;

import attacken.Attacke;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import pokemon.Pokemon;
import spiel.Typ;

public class Pokedex {
    private static PrintWriter writer;
    private static BufferedReader reader;
    private static java.io.File file=new java.io.File("pokedex.txt");
    
//    public static void main(String[] args){
//        new PokedexGUI().setVisible(true);
//    }
    
    static void setFile(java.io.File f){
        file=f;
    }
    
    public static void schreibeAlle() throws FileNotFoundException{
        int i=1;
        writer=new PrintWriter(file);
        schreibePok(new pokemon.Bisasam(1),i++);
        schreibePok(new pokemon.Bisaknosp(1),i++);
        schreibePok(new pokemon.Bisaflor(1),i++);
        schreibePok(new pokemon.Glumanda(1),i++);
        schreibePok(new pokemon.Glutexo(1),i++);
        schreibePok(new pokemon.Glurak(1),i++);
        schreibePok(new pokemon.Schiggy(1),i++);
        schreibePok(new pokemon.Schillok(1),i++);
        schreibePok(new pokemon.Turtok(1),i++);
        schreibePok(new pokemon.MegaGlurakX(1),i++);
        schreibePok(new pokemon.MegaGlurakY(1),i++);
        schreibePok(new pokemon.Taubsi(1),i++);
        schreibePok(new pokemon.Tauboga(1),i++);
        schreibePok(new pokemon.Tauboss(1),i++);
        schreibePok(new pokemon.Rattfratz(1),i++);
        schreibePok(new pokemon.Pikachu(1),i++);
        schreibePok(new pokemon.Karpador(1),i++);
        schreibePok(new pokemon.Garados(1),i++);
        writer.close();
    }
    
    public static void schreibePoks(Pokemon[] poks) throws FileNotFoundException{
        writer=new PrintWriter(file);
        for(int i=0;i<poks.length;i++)schreibePok(poks[i],i);
    }
    
    public static void schreibePok(Pokemon pok,int id) throws FileNotFoundException{
        String titel="#"+id+" "+pok.getOriginName()+" "+pok.getTyp1()+" "+pok.getTyp2(),
               werte=pok.getMaxKP()+" "+pok.getAtk()+" "+pok.getVert()+
                " "+pok.getSpezAtk()+" "+pok.getSpezVert()+" "+pok.getInit(),
                attacken="";
        for(Attacke att:pok.getAttacken()){
            if(att==null)break;
            attacken=attacken+att+" ";
        }
        for(Attacke att:pok.getBekannte()){
            if(att==null)break;
            attacken=attacken+att+" ";
        }
        if(writer==null) {
            writer=new PrintWriter(new java.io.File(pok.toString()+".txt"));
        }
        writer.println(titel);
        writer.println(werte);
        writer.println(attacken);
    }
    
    public static Pokemon getPokemon(int id,int lvl) throws IOException{
        reader=new BufferedReader(new java.io.FileReader(file));
        String n,line=reader.readLine();
        int kp,atk,vert,sAtk,sVert,init,cursor;
        Typ typ1,typ2;
        // Suche Anfangsposition des gesuchten Pokedexeintrags
        while(line!=null){
            if(line.startsWith("#"+id))break;
            line=reader.readLine();
        }
        if(line==null)return null;
        cursor=line.indexOf(" ")+1;
        n=line.substring(cursor,line.indexOf(" ",++cursor));
        cursor=line.indexOf(" ",cursor+1);
        typ1=Typ.valueOf(line.substring(++cursor,line.indexOf(" ",cursor)));
        cursor=line.indexOf(" ",cursor)+1;
        typ2=Typ.valueOf(line.substring(cursor));
        line=reader.readLine();
        cursor=line.indexOf(" ");
        kp=Integer.parseInt(line.substring(0,cursor));
        cursor++;
        atk=Integer.parseInt(line.substring(cursor,line.indexOf(" ",cursor)));
        cursor=line.indexOf(" ",cursor)+1;
        vert=Integer.parseInt(line.substring(cursor,line.indexOf(" ",cursor)));
        cursor=line.indexOf(" ",cursor)+1;
        sAtk=Integer.parseInt(line.substring(cursor,line.indexOf(" ",cursor)));
        cursor=line.indexOf(" ",cursor)+1;
        sVert=Integer.parseInt(line.substring(cursor,line.indexOf(" ",cursor)));
        cursor=line.indexOf(" ",cursor)+1;
        init=Integer.parseInt(line.substring(cursor));
        reader.close();
        return new Pokemon(n,kp,atk,vert,sAtk,sVert,init,typ1,typ2,lvl);
    }
    
    public static String getNameVon(int id) throws IOException{
        reader=new BufferedReader(new java.io.FileReader(file));
        String n,line=reader.readLine();
        if(line==null)return null;
        // Suche Anfangsposition des gesuchten Pokedexeintrags
        while(line!=null){
            if(line.startsWith("#"+id))break;
            line=reader.readLine();
        }
        int cursor=line.indexOf(" ")+1;
        n=line.substring(cursor,line.indexOf(" ",1+cursor));
        reader.close();
        return n;
    }
    
    public static Pokemon[] getAll() throws IOException{
        int i=1;
        java.util.LinkedList<Pokemon> poks=new java.util.LinkedList<>();
        Pokemon pok;
        while((pok=getPokemon(i,1))!=null){
            poks.add(pok);
            i++;
        }
        Pokemon[] alle=new Pokemon[poks.size()];
        for(i=0;i<alle.length;i++){
            alle[i]=poks.get(i);
        }
        return alle;
    }
}