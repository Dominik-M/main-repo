import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Bildschirmschoner extends JFrame implements ActionListener{
  public Bildschirmschoner(){
    super("Blah");
    Container c=getContentPane();
    //einige Buttons
    JButton bu=new JButton("Blah");
    c.add(bu, BorderLayout.NORTH);
    bu=new JButton("unten");
    bu.addActionListener(this);
    c.add(bu, BorderLayout.SOUTH);
    //mittiger Anzeigebereich: der Strich
    StrichComponent sc=new StrichComponent();
    c.add(sc, BorderLayout.CENTER);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println("lass mich in Ruhe!");
    JButton b=(JButton)e.getSource();
    b.setText("ich wurde gedrückt");
  }
}