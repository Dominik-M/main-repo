package monopoly;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class StrasseInfo extends JFrame {
  
  public StrasseInfo(Strasse s){
    super(s.toString());
    Container c=getContentPane();
    setLayout(new GridLayout(9,2));
    c.add(new JLabel("Name:   "));
    c.add(new JLabel(s.name));
    c.add(new JLabel("Adresse:  "));
    c.add(new JLabel(" "+s.pos));
    c.add(new JLabel("Besitzer:  "));
    JLabel b=new JLabel();
    if(s.besitzer==null){ b.setText(" Niemand"); }
    else{ b.setText(s.besitzer.toString()); }
    c.add(b);
    c.add(new JLabel("Wert:  "));
    c.add(new JLabel(s.kosten+" "+Monopoly.währung));
    c.add(new JLabel("Grundmiete:  "));
    c.add(new JLabel(s.mieteNormal+" "+Monopoly.währung));
    c.add(new JLabel("Tatsächliche Miete:  "));
    c.add(new JLabel(s.miete+" "+Monopoly.währung));
    c.add(new JLabel("Anzahl Häuser:  "));
    c.add(new JLabel(" "+s.hausanz));
    c.add(new JLabel("Kosten pro Haus:  "));
    c.add(new JLabel(s.hauskosten+" "+Monopoly.währung));
    JLabel ak=new JLabel();
    if(s.aktiv){ ak.setText("Die Strasse ist aktiv"); }
    else{
      ak.setText("Die Strasse ist belastet mit "+s.kosten/2+" "+Monopoly.währung);
      ak.setBackground(Color.red);
    }
    c.add(ak);
    pack();
    setVisible(true);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
  }  
}