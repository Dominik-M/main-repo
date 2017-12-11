package spiel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Verlernen extends javax.swing.JDialog implements ActionListener{
  private javax.swing.JButton[] auswahl;
  private pokemon.Pokemon pok;
  private attacken.Attacke neu;
    
  public Verlernen(attacken.Attacke neu,pokemon.Pokemon pok){
    setTitle("Welche Attacke soll verlernt werden?");
    setModal(true);
    setLayout(new java.awt.FlowLayout());
    setLocation(Javamon.getLoc());
    this.pok=pok;
    this.neu=neu;
    attacken.Attacke[] atts=pok.getAttacken();
    auswahl=new javax.swing.JButton[atts.length];
    for(int i=0;i<atts.length;i++){
      auswahl[i]=new javax.swing.JButton(atts[i].toString());
      auswahl[i].addActionListener(this);
      getContentPane().add(auswahl[i]);
    }
    setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
    pack();
    setVisible(true);
  }

    @Override
    public void actionPerformed(ActionEvent e) {
      for(int i=0;i<auswahl.length;i++){
        if(e.getSource().equals(auswahl[i])){
          pok.setzeAttAuf(i,neu);
          break;
        }
      }
      dispose();
    }
}