package monopoly;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class StrasseKaufen extends JFrame implements ActionListener{
  Spieler sp;
  Strasse str;
  Timer t;
  int betrag;

  public StrasseKaufen(Spieler käufer,Strasse s,int kosten){
    super("Transaktion");
    betrag=kosten;
    sp=käufer;
    str=s;
    Container c=getContentPane();
    setLayout(new GridLayout(3,1));
    JLabel txt=new JLabel();
    if(sp==null){ txt.setText("Will "+s.besitzer+" "+s+" für "+kosten+" "+Monopoly.währung+" an die Bank verkaufen?"); }
    else if(s.besitzer==null){ txt.setText("Will "+käufer+" "+s+" für "+kosten+" "+Monopoly.währung+" kaufen?"); }
    else{ txt.setText("Will "+käufer+" "+s+" für "+kosten+" "+Monopoly.währung+" von "+s.besitzer+" kaufen?"); }
    txt.setFont(new Font(Font.MONOSPACED,200,20));
    c.add(txt);
    JButton ja=new JButton("Ja");
    ja.setFont(new Font(Font.MONOSPACED,60,60));
    ja.addActionListener(this);
    c.add(ja);
    JButton nein=new JButton("Nein");
    nein.setFont(new Font(Font.MONOSPACED,60,60));
    nein.addActionListener(this);
    c.add(nein);
    pack();
    t=new Timer(500,this);
    t.start();
  }

    @Override
    public void actionPerformed(ActionEvent e) {
      if(e.getSource().equals(t)){
        t.stop();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
      }else{
      JButton wer=(JButton)e.getSource();
      if(wer.getText().equals("Ja")){
        if(sp==null){
          if(!str.aktiv){
            Spielfeld.output.append(str+" ist belastet und die Bank will sie nicht kaufen."+"\n");
            return;  
          }
          Spielfeld.output.append(str.besitzer+" hat "+str+" verkauft"+"\n");
          str.besitzer.zahle(-betrag);
          str.besitzer.remove(str);
        }
        else if(sp.geld<betrag){ Spielfeld.output.append("Zu wenig Geld"+"\n"); }
        else if(str.besitzer==null){  
          Spielfeld.output.append(sp+" hat "+str+" gekauft"+"\n");
          sp.add(str);
          sp.zahle(betrag);
        }else{
          Spielfeld.output.append(sp+" kauft "+str+" von "+str.besitzer+"für "+betrag+" "+Monopoly.währung+" \n");
          sp.zahle(betrag, str.besitzer);
          str.besitzer.remove(str);
          sp.add(str);
        }
      }else{
        Spielfeld.output.append("Transaktion abgebrochen"+"\n");         
      }
      this.dispose();
    }
    }
}
