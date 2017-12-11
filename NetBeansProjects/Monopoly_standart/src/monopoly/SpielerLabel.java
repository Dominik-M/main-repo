package monopoly;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SpielerLabel extends JPanel {
  
  public SpielerLabel(Spieler spieler){
    add(new JLabel(spieler.name+""));
    add(new JLabel("Geld: "+spieler.geld+" "+Monopoly.währung));
    add(new JLabel("Position: "+Monopoly.get(spieler.pos+"")));
    setBackground(spieler.farbe);
  }
}