package monopoly;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StrasseComponent extends JPanel{
  
  public StrasseComponent(Strasse s){
    setLayout(new GridLayout(2,1));
    setBackground(s.farbe);
    JLabel name=new JLabel(s.name);
    add(name);
    JLabel wert=new JLabel(s.kosten+" "+Monopoly.währung);
    add(wert);
  }
  
  public StrasseComponent(Feld f){
    setBackground(f.farbe);
    JLabel name=new JLabel(f.name);
    add(name);
  }
}
