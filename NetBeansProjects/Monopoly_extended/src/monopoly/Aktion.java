package monopoly;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Aktion extends JFrame implements ActionListener{
  Spieler s;
  JComboBox eingabe=new JComboBox(Monopoly.namen);
  Timer t;
    
  public Aktion(Spieler wer){
    super("Aktion");
    s=wer;
    Container c=getContentPane();
    setLayout(new GridLayout(6,1));
    setMinimumSize(new Dimension(300,150));
    c.add(eingabe);
    JButton info=new JButton("Straßeninfo");
    info.addActionListener(this);
    info.setMinimumSize(new Dimension(100,50));
    c.add(info);
    JButton kauf=new JButton("Kaufen/Verkaufen");
    kauf.addActionListener(this);
    kauf.setMinimumSize(new Dimension(150,50));
    c.add(kauf);
    JButton bau=new JButton("Haus bauen");
    bau.addActionListener(this);
    bau.setMinimumSize(new Dimension(100,50));
    c.add(bau);
    JButton hyp=new JButton("Hypothek aufnehmen/abzahlen");
    hyp.addActionListener(this);
    hyp.setMinimumSize(new Dimension(150,50));
    c.add(hyp);
    String besitz="Strassen im Besitz: ";
    for(Strasse str: wer.strassen){
      if(str==null){ continue; }
      besitz=besitz+"("+str.pos+") "+str.name+" ";
    }
    c.add(new JLabel(besitz));
    pack();
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    t=new Timer(500,this);
    t.start();
  }
    
    @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource().equals(t)){
        t.stop();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        return;
    }
    Strasse str;
    boolean unbes;
    try{
      str=(Strasse)Monopoly.get(eingabe.getSelectedIndex()+"");
      unbes=str.besitzer==null;
    }
    catch(Exception ex){
      Spielfeld.output.append("Strasse nicht gefunden \n");
      return;
    }
    JButton b=(JButton)e.getSource();
    if(b.getText().equals("Straßeninfo")){
      new StrasseInfo(str);
    }
    else if(b.getText().equals("Kaufen/Verkaufen")){
      if(unbes){ Spielfeld.output.append("Niemand besitzt diese Strasse"+"\n"); }
      else{
        if(str.besitzer.equals(s)){
          new StrasseKaufen(null,str,str.kosten);
        }else{
          new Handel(str,s);
        }
      }
    }
    else if(b.getText().equals("Haus bauen")){
      if(!unbes && str.besitzer.equals(s)){ str.hausbau(); }
      else{ Spielfeld.output.append("Die Strasse gehört einem anderen"+"\n"); }
    }
    else{
      if(unbes){ Spielfeld.output.append("Niemand besitzt diese Strasse"+"\n"); }
      else{ str.hypothek(s); }
    }
  }    
}