package spiel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PokAuswahl extends javax.swing.JDialog implements ActionListener{
    private javax.swing.JButton[] auswahl;
    private pokemon.Pokemon[] poks;
    private pokemon.Pokemon auswahlPok;
    
  public PokAuswahl(pokemon.Pokemon... poks){
    setTitle("Welches Pokemon?");
    setModal(true);
    setLayout(new java.awt.FlowLayout());
    setLocation(Javamon.getLoc());
    this.poks=poks;
    auswahl=new javax.swing.JButton[poks.length];
    for(int i=0;i<poks.length;i++){
      if(poks[i]==null)continue;
      auswahl[i]=new javax.swing.JButton(poks[i].toString());
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
          auswahlPok=poks[i];
        }
      }
      dispose();
    }
    
    public pokemon.Pokemon getAuswahl(){
      while(this.isVisible()){}
      return auswahlPok;
    }
}