package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Worker extends Thread{
  private String name;
  private final Socket socket;
  private final PrintWriter raus;
  private final BufferedReader rein;
  private final java.util.LinkedList <WorkerListener> listener=new java.util.LinkedList();
  
  public Worker(Socket s,WorkerListener... als)throws Exception{
    socket=s;
    listener.addAll(Arrays.asList(als));
    rein=new BufferedReader(new InputStreamReader(socket.getInputStream()));
    raus=new PrintWriter(socket.getOutputStream(),true);
  }
  
  public void addListener(WorkerListener neu){
      listener.add(neu);
  }
  
  public void removeListener(WorkerListener alt){
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
      if(text == null) {
            break;
        }
      else {
            for(WorkerListener al:listener){al.receive(name+": "+text);}
        }
    }
    for(WorkerListener al:listener)al.fire(this);
  }
    
  public void send(String text){
    raus.println(text);
  }
  
  public void setArbeiterName(String s){
      name=s;
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
          Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
}