
package monopoly;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Handel extends JFrame implements ActionListener{
    private Strasse str;
    private Spieler s;
    private JTextField betrag;
    private JButton ok=new JButton("Annehmen");
    private JButton ne=new JButton("Ablehnen");
    
    public Handel(Strasse strass,Spieler sp){
      super("Verhandlung um "+strass);
      str=strass;
      s=sp;
      setLayout(new GridLayout(5,2));
      Container c=getContentPane();
      c.add(new JLabel("Besitzer: "+str.besitzer));
      c.add(new JLabel("Addresse: "+str.pos));
      c.add(new JLabel("Wert: "+str.kosten+" "+Monopoly.währung));
      c.add(new JLabel("Grundmiete: "+str.mieteNormal+" "+Monopoly.währung));
      c.add(new JLabel("Miete: "+str.miete+" "+Monopoly.währung));
      c.add(new JLabel("Anzahl Häuser: "+str.hausanz));
      c.add(new JLabel("Gebot von "+s+": "));
      betrag=new JTextField(""+str.kosten);
      c.add(betrag);
      ok.addActionListener(this);
      c.add(ok);
      ne.addActionListener(this);
      c.add(ne);
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      pack();
      setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if(e.getSource().equals(ok)){
        try{
          int preis=Integer.parseInt(betrag.getText());
          new StrasseKaufen(s,str,preis);
          dispose();
        }catch(Exception ex){}
      }else{
        Spielfeld.output.append(s+" hat das Angebot abgelehnt \n");
        dispose();
      }
    }
    
}
