
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Rechner extends JFrame implements ActionListener {
    private static Rechner calc;
    private static JTextField eingabe;
    private static JLabel ausgabe;
    
    public Rechner(){
      // Fenstereigenschaften  
      super("UPN/Infix Rechner");
      setLayout(null);
      setSize(400,400);
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      int x = (d.width - getSize().width) / 2;
      int y = (d.height - getSize().height) / 2;
      setLocation(x, y);
      setResizable(false);
      Container c=getContentPane();
      // Textfelder und Ausrechnen
      JButton ausrechnen=new JButton("ausrechnen");
      c.add(ausrechnen);
      ausrechnen.setBounds(0,330,395,42);
      eingabe=new JTextField();
      eingabe.setText(null);
      eingabe.setBounds(0,0,400,40);
      c.add(eingabe);
      ausgabe=new JLabel();
      ausgabe.setBounds(50,50,350,40);
      c.add(ausgabe);
      // Eingabebuttons
      JButton eins=new JButton("1");
      eins.setBounds(5, 100, 50, 50);
      c.add(eins);
      JButton zwei=new JButton("2");
      zwei.setBounds(65, 100, 50, 50);
      c.add(zwei);
      JButton drei=new JButton("3");
      drei.setBounds(125, 100, 50, 50);
      c.add(drei);
      JButton vier=new JButton("4");
      vier.setBounds(5, 160, 50, 50);
      c.add(vier);
      JButton fünf=new JButton("5");
      fünf.setBounds(65, 160, 50, 50);
      c.add(fünf);
      JButton sechs=new JButton("6");
      sechs.setBounds(125, 160, 50, 50);
      c.add(sechs);
      JButton sieben=new JButton("7");
      sieben.setBounds(5, 220, 50, 50);
      c.add(sieben);
      JButton acht=new JButton("8");
      acht.setBounds(65, 220, 50, 50);
      c.add(acht);
      JButton neun=new JButton("9");
      neun.setBounds(125, 220, 50, 50);
      c.add(neun);
      JButton kreis=new JButton("0");
      kreis.setBounds(65, 280, 50, 50);
      c.add(kreis);
      JButton leer=new JButton("leer");
      leer.setBounds(125, 280, 250, 50);
      c.add(leer);
      JButton komma=new JButton(",");
      komma.setBounds(5, 280, 50, 50);
      c.add(komma);
      JButton pi=new JButton("pi");
      pi.setBounds(185, 100, 50, 50);
      c.add(pi);
      JButton e=new JButton("e");
      e.setBounds(245, 100, 50, 50);
      c.add(e);
      JButton vzw=new JButton("vz");
      vzw.setBounds(305, 100, 50, 50);
      c.add(vzw);
      JButton plus=new JButton("+");
      plus.setBounds(185, 160, 50, 50);
      c.add(plus);
      JButton minus=new JButton("-");
      minus.setBounds(245, 160, 50, 50);
      c.add(minus);
      JButton mal=new JButton("*");
      mal.setBounds(185, 220, 50, 50);
      c.add(mal);
      JButton geteilt=new JButton("/");
      geteilt.setBounds(245, 220, 50, 50);
      c.add(geteilt);
      JButton entf=new JButton("entf");
      entf.setBounds(305, 160, 60, 50);
      c.add(entf);
      JButton ac=new JButton("AC");
      ac.setBounds(305, 220, 60, 50);
      c.add(ac);
      // Ende der Eingabebuttons
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
      ausrechnen.addActionListener(this);
      eins.addActionListener(this);
      zwei.addActionListener(this);
      drei.addActionListener(this);
      vier.addActionListener(this);
      fünf.addActionListener(this);
      sechs.addActionListener(this);
      sieben.addActionListener(this);
      acht.addActionListener(this);
      neun.addActionListener(this);
      kreis.addActionListener(this);
      komma.addActionListener(this);
      leer.addActionListener(this);
      pi.addActionListener(this);
      e.addActionListener(this);
      vzw.addActionListener(this);
      plus.addActionListener(this);
      minus.addActionListener(this);
      mal.addActionListener(this);
      geteilt.addActionListener(this);
      entf.addActionListener(this);
      ac.addActionListener(this);
    }
    
    public static void main(String[] args){
      calc=new Rechner();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      String s=eingabe.getText();
      JButton wer=(JButton)e.getSource();
      if(wer.getText().equals("ausrechnen") && s.length()>0){
        s=Calculator.calc(s);
        ausgabe.setText(s);
        return;
      }
      if(wer.getText().equals("1")){
        eingabe.setText(s+"1");
        return;
      }
      if(wer.getText().equals("2")){
        eingabe.setText(s+"2");
        return;
      }
      if(wer.getText().equals("3")){
        eingabe.setText(s+"3");
        return;
      }
      if(wer.getText().equals("4")){
        eingabe.setText(s+"4");
        return;
      }
      if(wer.getText().equals("5")){
        eingabe.setText(s+"5");
        return;
      }
      if(wer.getText().equals("6")){
        eingabe.setText(s+"6");
        return;
      }
      if(wer.getText().equals("7")){
        eingabe.setText(s+"7");
        return;
      }
      if(wer.getText().equals("8")){
        eingabe.setText(s+"8");
        return;
      }
      if(wer.getText().equals("9")){
        eingabe.setText(s+"9");
        return;
      }
      if(wer.getText().equals("0")){
        eingabe.setText(s+"0");
        return;
      }
      if(wer.getText().equals(",")){
        eingabe.setText(s+".");
        return;
      }
      if(wer.getText().equals("leer")){
        if(s.length()<=0 || s.charAt(s.length()-1)!=' '){
          eingabe.setText(s+" ");
          return;
        }
      }
      if(wer.getText().equals("entf")){
        if(s.length()>0){
          s=s.substring(0, s.length()-1);
          eingabe.setText(s);
        }
        return;
      }
      if(wer.getText().equals("AC")){
        s="";
        eingabe.setText(s);
        ausgabe.setText(s);
        return;
      }
      if(wer.getText().equals("pi")){
        eingabe.setText(s+"3.141592654");
        return;
      }
      if(wer.getText().equals("e")){
        eingabe.setText(s+"2.718281828");
        return;
      }
      if(wer.getText().equals("+")){
        eingabe.setText(s+" + ");
        return;
      }
      if(wer.getText().equals("-")){
        eingabe.setText(s+" - ");
        return;
      }
      if(wer.getText().equals("*")){
        eingabe.setText(s+" * ");
        return;
      }
      if(wer.getText().equals("/")){
        eingabe.setText(s+" / ");
        return;
      }
      if(wer.getText().equals("vz")){
        if(s.length()<=0 || s.charAt(0)==' ') return;
        int letzte=0;
        for(int i=0;i<s.length();i++){
          if(s.charAt(i)==' '){
            letzte=i;
          }
        }
        String ende=s.substring(letzte);
        s=s.substring(0, letzte);
        if(ende.charAt(1)=='-'){
          ende=" "+ende.substring(2);  
        }else{
          ende=" -"+ende.substring(1);    
        }
        s=s+ende;
        eingabe.setText(s); 
      }
    } 
}