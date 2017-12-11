package monopoly;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Spielfeld extends JFrame implements ActionListener{
  static Feld[] felder;
  Spieler[] spieler;
  Container c;
  JPanel mitte=new JPanel(new GridLayout(2,1));
  JPanel mitteUnten;
  JPanel oben=new JPanel(new GridLayout(1,11,10,10));
  JPanel rechts=new JPanel(new GridLayout(9,1,10,10));
  JPanel unten=new JPanel(new GridLayout(1,11,10,10));
  JPanel links=new JPanel(new GridLayout(9,1,10,10));
  static JTextArea output=new JTextArea();
  JScrollPane sp;
  JButton würfel=new JButton("Würfeln");
  JButton aktion=new JButton("Aktion");
  int amZug=0;
  int bank;

  public Spielfeld(Spieler[] alleSpieler, Feld[] alleFelder){
    super("Monopoly");
    spieler=alleSpieler;
    felder=alleFelder;
    c=getContentPane();
    würfel.addActionListener(this);
    aktion.addActionListener(this);
    output.append("Willkommen bei Monopoly! Das Spiel möge beginnen."+"\n");
    output.setLineWrap(true);
    output.setEditable(false);
    output.setFont(new Font(Font.MONOSPACED,16,16));
    sp=new JScrollPane(output);
    zeichne();
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }
  
  public void zeichne(){
    for(Feld f:felder){
      try{
        Strasse s=(Strasse)f;
        if(s.besitzer==null){ f.farbe=s.farbeNormal; }
        else{ f.farbe=s.besitzer.farbe.brighter(); }
      }catch(Exception e){ f.farbe=Color.WHITE; }
    }
    for(Spieler s: spieler){
        if(s.pos>=0 && s.pos<40){
          felder[s.pos].farbe=s.farbe;
        }
    }
    c.removeAll();
    oben=new JPanel(new GridLayout(1,11,10,10));
    rechts=new JPanel(new GridLayout(9,1,10,10));
    unten=new JPanel(new GridLayout(1,11,10,10));
    links=new JPanel(new GridLayout(9,1,10,10));
    mitte=new JPanel(new GridLayout(2,1));
    mitteUnten=new JPanel(new GridLayout(1+spieler.length/2,2,40,20));
    mitteUnten.removeAll();
    // Ränder
    //rechts
    for(int i=11;i<20;i++){
      try{ rechts.add(new StrasseComponent((Strasse)felder[i])); }
      catch(Exception e){ rechts.add(new StrasseComponent(felder[i])); }
    }
    //oben
    for(int i=0;i<11;i++){
      try{ oben.add(new StrasseComponent((Strasse)felder[i])); }
      catch(Exception e){ oben.add(new StrasseComponent(felder[i])); }
    }
    //unten
    for(int i=30;i>19;i--){
      try{ unten.add(new StrasseComponent((Strasse)felder[i])); }
      catch(Exception e){ unten.add(new StrasseComponent(felder[i])); }
    }
    //links
    for(int i=39;i>30;i--){
      try{ links.add(new StrasseComponent((Strasse)felder[i])); }
      catch(Exception e){ links.add(new StrasseComponent(felder[i])); }
    }
    c.add(oben,BorderLayout.NORTH); 
    c.add(rechts,BorderLayout.EAST);
    c.add(unten,BorderLayout.SOUTH);
    c.add(links,BorderLayout.WEST);
    // mittiger Anzeigebereich
    sp=new JScrollPane(output);
    mitte.add(sp);
    for(int i=0;i<spieler.length;i++){
      mitteUnten.add(new SpielerLabel(spieler[i]));
    }
    mitteUnten.add(würfel);
    mitteUnten.add(aktion);
    mitte.add(mitteUnten);
    c.add(mitte,BorderLayout.CENTER);
    pack();
    setExtendedState(JFrame.MAXIMIZED_BOTH);
  }

    @Override
  public void actionPerformed(ActionEvent e) {
    JButton wer=(JButton)e.getSource();
    if(spieler[amZug].pos==-1){
      gefangen(spieler[amZug]);
      return;
    }
    if(spieler[amZug].pos<-1){
      if(spieler[amZug].pos==-2){
        if(spieler[amZug].timer==0){
          output.append("Sie haben Ihre Zeit abgesessen, nächste Runde dürfen Sie weiterwürfeln \n");
          spieler[amZug].pos=10;
          amZug++;
          if(amZug>=spieler.length){ amZug=0; }
          output.append(spieler[amZug]+" ist jetzt am Zug \n");
          return;
        }
        spieler[amZug].pos=-1;
        amZug++;
        if(amZug>=spieler.length){ amZug=0; }
        output.append(spieler[amZug]+" ist jetzt am Zug \n");
        return;
      }
      int wurf=(int)(Math.random()*10+1);
      if(wurf==10){
        output.append("eine 10! Sie sind frei und dürfen weiterwürfeln \n");
        spieler[amZug].pos=10;
        spieler[amZug].timer=0;
        return;
      }else{
        output.append("eine "+wurf+", versuchen Sie es nochmal \n");
        spieler[amZug].pos++;
        return;
      }
    }
    if(wer.equals(aktion)){ 
      new Aktion(spieler[amZug]);
      zeichne();
    }
    else{
      if(spieler[amZug].geld<0 || spieler[amZug].timer>0){ schulden(spieler[amZug]); }
      int wurf=(int)(Math.random()*10+2);
      output.append(spieler[amZug]+" würfelt eine "+wurf+"\n");
      spieler[amZug].pos=spieler[amZug].pos+wurf;
      if(spieler[amZug].pos>=40){
        output.append(spieler[amZug]+" zieht über Los und bekommt 4000 "+Monopoly.währung+"\n");
        spieler[amZug].pos=spieler[amZug].pos-40;
        spieler[amZug].geld=spieler[amZug].geld+4000;
        zeichne();
      }
      output.append(spieler[amZug]+" steht jetzt auf "+felder[spieler[amZug].pos]+"\n");
      ereignis(felder[spieler[amZug].pos]);
      amZug++;
      if(amZug>=spieler.length){ amZug=0; }
      output.append("\n"+spieler[amZug]+" ist jetzt am Zug"+"\n"+"\n");
    }
  }
  
  public void ereignis(Feld wo){
    if(wo.name.equals(felder[22].name)){ zufallEreignis(); }
    else if(wo.name.equals(felder[2].name)){ zufallEreignis(); }
    else if(wo.pos==4){
      output.append(spieler[amZug]+" zahlt 4000 "+Monopoly.währung+" "+wo+" an die Bank"+"\n");
      spieler[amZug].zahle(4000);
      bank=bank+4000;
    }
    else if(wo.pos==10){ output.append("Nur zu Besuch \n"); }
    else if(wo.pos==20){ 
      output.append(spieler[amZug]+" bekommt die "+bank+" "+Monopoly.währung+" von der Bank"+"\n");
      spieler[amZug].geld=spieler[amZug].geld+bank;
      bank=0;
    }
    else if(wo.pos==30){
      gefangen(spieler[amZug]);
    }else if(wo.pos==38){
      output.append(spieler[amZug]+" zahlt 2000 "+Monopoly.währung+" Zusatzsteuern an die Bank"+"\n");
      spieler[amZug].zahle(2000);
      bank=bank+2000;
    }
    else if(wo.pos==0){
      output.append("Genau auf Los! "+spieler[amZug]+" erhält weitere 4000 "+Monopoly.währung+"\n");
      spieler[amZug].geld=spieler[amZug].geld+4000;
    }
    else{  // Straßen
      Strasse s=(Strasse)wo;
      if(s.besitzer==null){
        new StrasseKaufen(spieler[amZug],s,s.kosten);
      }
      else if(s.besitzer.pos<0){ output.append(s.besitzer+" sitzt im "+felder[10]+" und bekommt keine Miete"+"\n"); }
      else if(wo.aktiv){
        spieler[amZug].zahle(s.miete, s.besitzer);
        output.append(spieler[amZug]+" zahlte "+s.miete+" "+Monopoly.währung+" Miete an "+s.besitzer+"\n");
      }
      else{ output.append(s+" ist inaktiv"+"\n"); }
    }
    zeichne();
  }
  
  public void zufallEreignis(){
    int wurf=(int)(Math.random()*10+1);
    if(wurf<3){
      output.append("Nochmal würfeln"+"\n");
      amZug--;
    }
    else if(wurf<5){
      int betrag=(int)(Math.random()*10+1)*250;
      output.append(spieler[amZug]+" erhält "+betrag+" "+Monopoly.währung+"\n");
      spieler[amZug].zahle(-betrag);
    }
    else if(wurf<7){
      int betrag=(int)(Math.random()*10+1)*200;
      output.append(spieler[amZug]+" verliert "+betrag+" "+Monopoly.währung+"\n");
      spieler[amZug].zahle(betrag);
      bank=bank+betrag;
    }
    else if(wurf<9){
      int wohin=(int)(Math.random()*40);
      output.append(spieler[amZug]+" begibt sich zu "+felder[wohin]+"\n");
      if(spieler[amZug].pos>wohin){
        output.append(spieler[amZug]+" zieht über Los und bekommt 4000 "+Monopoly.währung+"\n");
        spieler[amZug].geld=spieler[amZug].geld+4000;
        zeichne();
      }
      spieler[amZug].pos=wohin;
      ereignis(felder[wohin]);
    }
    else if(wurf==9){
      output.append(spieler[amZug]+" hat Geburtstag und bekommt 1000 "+Monopoly.währung+" von allen Mitspielern"+"\n");
      for(Spieler s: spieler){
        s.zahle(1000, spieler[amZug]);
      }
    }
    else{
      gefangen(spieler[amZug]);
    }
  }
  
  public void gefangen(Spieler s){
    if(s.timer>0){
      output.append(s+" geht mit Schulden ins "+felder[10]+" !"+"\n");
      s.timer=1;
      schulden(s);
      return;
    }
    if(s.timer==0){
      output.append(s+", gehen Sie in das "+felder[10]+", begeben Sie sich direkt dorthin und ziehen Sie keine 4000 "
              +Monopoly.währung+" ein, wenn Sie über Los kommen!"+"\n");
      s.pos=-1;
      int runden=(int)(Math.random()*4+2);
      output.append(s+" bleibt maximal "+runden+" Runden im "+felder[10]+"\n");
      s.timer=-runden;
    }else{
      output.append("Sie haben 3 Versuche eine 10 zu Würfeln, um sofort freizukommen"+"\n");
      s.pos=-5;
      s.timer++;
    }
  }
  
  public void schulden(Spieler s){
    if(s.timer==0){
      output.append(s+" hat Schulden. Sie haben 3 Runden Zeit eine positive Bilanz zu bekommen sonst verlieren Sie das Spiel"+"\n");
      s.timer=4;
    }else if(s.timer==1){
      if(Monopoly.namen==Monopoly.NAMEN_NAZI){
        output.append(s+" wurde vergast \n");
      }
      output.append(s+" hat verloren"+"\n");
      for(int i=0;i<s.strassen.length;i++){
        if(s.strassen[0]!=null){
          s.remove(s.strassen[0]);
        }
      }
      Spieler[] neu=new Spieler[spieler.length-1];
      for(int i=0,j=0;i<spieler.length;i++){
        if(spieler[i].equals(s)){ continue; }
        neu[j++]=spieler[i];
      }
      if(neu.length==1){
        System.out.println(neu[0]+" hat gewonnen!");
        System.exit(0);
      }
      spieler=neu;
    }
    else{
      if(s.geld>0){
        output.append(s+" ist raus aus den Schulden"+"\n");
        s.timer=0;
      }else{
        s.timer--;
        output.append(s+", noch "+(s.timer-1)+" Runden bis zur Niederlage"+"\n");
      }
    }
  }
}