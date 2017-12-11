package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Arbeiter extends Thread{
  private String name;
  private final Socket socket;
  private final PrintWriter raus;
  private final BufferedReader rein;
  private final java.util.LinkedList <ArbeiterListener> listener=new java.util.LinkedList();
  
  public Arbeiter(Socket s,ArbeiterListener... als)throws Exception{
    socket=s;
    listener.addAll(Arrays.asList(als));
    rein=new BufferedReader(new InputStreamReader(socket.getInputStream()));
    raus=new PrintWriter(socket.getOutputStream(),true);
  }
  
  public void addListener(ArbeiterListener neu){
      listener.add(neu);
  }
  
  public void removeListener(ArbeiterListener alt){
      listener.remove(alt);
  }
  
    @Override
  public void run() {
    String text;
    while(socket.isConnected()){
      try{
        text=rein.readLine();
      }catch(Exception ex){
          System.out.println("Fehler "+ex);
          if(ex.toString().endsWith("reset"))break;
          continue;
      }
      if(text==null) {
            break;
        }
      else if(text.startsWith("#name")){
          name=text.substring(5);
      }
      else {
            for(ArbeiterListener al:listener)al.hatGesagt(name+": "+text);
        }
    }
    for(ArbeiterListener al:listener)al.feuern(this);
  }
    
  public void send(String text){
    raus.println(text);
  }
  
    @Override
  public String toString(){
      return name;
  }
  
  public void close(){
      try {
          socket.close();
          rein.close();
          raus.close();
      } catch (IOException ex) {
          Logger.getLogger(Arbeiter.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
}