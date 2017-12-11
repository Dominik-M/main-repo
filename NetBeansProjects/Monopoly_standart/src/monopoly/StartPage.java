package monopoly;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class StartPage extends JFrame implements ActionListener{
  JComboBox anz;
  Container c;
  JLabel[] label=new JLabel[10];
  JTextField[] namen=new JTextField[10];
  JComboBox<String>[] farben=new JComboBox[10];
  JComboBox geld=new JComboBox(new String[]{ // Startgeld für jeden Spieler
      "2500","5000","7500","10000","15000","20000","25000","30000","35000","40000","45000","50000","100000",Integer.MAX_VALUE/2+""
  });
  JComboBox währungen=new JComboBox(new String[]{
      "€","DM","$","Goldmünzen","Sesterzen","Backsteine","Kekse" // Währungen können beliebig ergänzt werden
  });
  JComboBox version=new JComboBox(new String[]{"Standartversion"});
  
  public StartPage(){
    // Fenstereinstellungen
    super("Monopoly - Einstellungen");
    c=getContentPane();
    setLayout(new FlowLayout(FlowLayout.CENTER,25,25));
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //globale Einstellungen
    c.add(new JLabel("Globale Einstellungen: "));
    c.add(version);
    c.add(new JLabel("Startgeld: "));
    c.add(geld);
    c.add(new JLabel("Währung: "));
    c.add(währungen);
    // Spielereinstellungen
    c.add(new JLabel("Spielereinstellungen:"));
    anz = new JComboBox(new String[]{"2","3","4","5","6","7","8","9","10"});
    anz.addActionListener(this);
    c.add(anz);
    for(int i=0;i<label.length;i++){
      label[i]=new JLabel("Spieler "+(i+1));
      c.add(label[i]);
      label[i].setVisible(false);
      namen[i]=new JTextField(label[i].getText());
      c.add(namen[i]);
      namen[i].setVisible(false);
      farben[i]=new JComboBox(new String[]{"rot","grün","blau","dunkelblau","grau","gelb","schwarz","orange","pink","weis","braun"});
      c.add(farben[i]);
      farben[i].setVisible(false);
    }
    JButton start=new JButton("Starten");
    start.addActionListener(
          new ActionListener(){
              @Override
              public void actionPerformed(ActionEvent e){
                starten();
              }
          });
    c.add(start);
    actionPerformed(null);
    pack();
    setExtendedState(JFrame.MAXIMIZED_BOTH);
  }

    @Override
    public void actionPerformed(ActionEvent e) {
      for(int i=0;i<label.length;i++){
        if(i<=anz.getSelectedIndex()+1){
        label[i].setVisible(true);
        namen[i].setVisible(true);
        farben[i].setVisible(true);
        }else{
        label[i].setVisible(false);
        namen[i].setVisible(false);
        farben[i].setVisible(false);
        }
      }
      repaint();
    }
    
  public void starten(){
    Monopoly.währung=(String)währungen.getSelectedItem();
    Monopoly.namen=Monopoly.NAMEN_STANDART;
    // erzeuge Felder
    int i=0;
    Monopoly.felder[i]=new Feld(Monopoly.namen[i],i,Color.WHITE);
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,1000,1000,80,Color.BLUE.darker());
    i++;
    Monopoly.felder[i]=new Feld(Monopoly.namen[i],i,Color.WHITE);
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,1200,1000,120,Color.BLUE.darker());
    i++;
    Monopoly.felder[i]=new Feld(Monopoly.namen[i],i,Color.WHITE);
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,4000,0,500,Color.LIGHT_GRAY);
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,2000,1000,140,Color.CYAN.brighter());
    i++;
    Monopoly.felder[i]=new Feld(Monopoly.namen[i],i,Color.WHITE);
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,2000,1000,140,Color.CYAN.brighter());
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,2400,1000,180,Color.CYAN.brighter());
    i++;
    Monopoly.felder[i]=new Feld(Monopoly.namen[i],i,Color.GRAY.brighter());
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,2800,2000,200,Color.MAGENTA.darker());
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,3000,0,80,Color.GRAY);
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,2800,2000,200,Color.MAGENTA.darker());
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,3200,2000,250,Color.MAGENTA.darker());
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,4000,0,500,Color.LIGHT_GRAY);
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,3600,2000,280,Color.ORANGE.darker());
    i++;
    Monopoly.felder[i]=new Feld(Monopoly.namen[i],i,Color.WHITE);
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,3600,2000,280,Color.ORANGE.darker());
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,4000,2000,320,Color.ORANGE.darker());
    i++;
    Monopoly.felder[i]=new Feld(Monopoly.namen[i],i,Color.WHITE);
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,4400,3000,360,Color.RED.brighter());
    i++;
    Monopoly.felder[i]=new Feld(Monopoly.namen[i],i,Color.WHITE);
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,4400,3000,360,Color.RED.brighter());
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,4800,3000,400,Color.RED.brighter());
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,4000,0,500,Color.LIGHT_GRAY);
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,5200,3000,440,Color.YELLOW.brighter());
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,5200,3000,440,Color.YELLOW.brighter());
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,3000,0,80,Color.GRAY);
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,5600,3000,580,Color.YELLOW.brighter());
    i++;
    Monopoly.felder[i]=new Feld(Monopoly.namen[i],i,Color.WHITE);
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,6000,4000,520,Color.GREEN.brighter());
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,6000,4000,520,Color.GREEN.brighter());
    i++;
    Monopoly.felder[i]=new Feld(Monopoly.namen[i],i,Color.WHITE);
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,6400,4000,560,Color.GREEN.brighter());
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,4000,0,500,Color.LIGHT_GRAY);
    i++;
    Monopoly.felder[i]=new Feld(Monopoly.namen[i],i,Color.WHITE);
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,7000,4000,700,Color.BLUE.brighter().brighter());
    i++;
    Monopoly.felder[i]=new Feld(Monopoly.namen[i],i,Color.WHITE);
    i++;
    Monopoly.felder[i]=new Strasse(Monopoly.namen[i],i,8000,4000,1000,Color.BLUE.brighter().brighter());
    // erzeuge Spieler
    Spieler[] spieler=new Spieler[anz.getSelectedIndex()+2];
    for(int n=0;n<spieler.length;n++){
      spieler[n]=new Spieler(namen[n].getText(),Integer.parseInt((String)geld.getSelectedItem()));
      String s=(String)farben[n].getSelectedItem();
      switch (s) {
        case "blau": spieler[n].farbe=Color.CYAN; break;
        case "grün": spieler[n].farbe=Color.GREEN; break;
        case "dunkelblau": spieler[n].farbe=Color.BLUE; break;
        case "rot": spieler[n].farbe=Color.RED; break;
        case "grau": spieler[n].farbe=Color.GRAY; break;
        case "schwarz": spieler[n].farbe=Color.BLACK; break;
        case "gelb": spieler[n].farbe=Color.YELLOW; break;
        case "orange": spieler[n].farbe=Color.ORANGE; break;
        case "pink": spieler[n].farbe=Color.PINK; break;
        case "weis": spieler[n].farbe=Color.WHITE; break;
        case "braun": spieler[n].farbe=Color.ORANGE.darker().darker();break;
      } 
    }
    new Spielfeld(spieler,Monopoly.felder);
    dispose();
  }
}