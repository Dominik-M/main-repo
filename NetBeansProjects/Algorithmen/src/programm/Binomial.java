package programm;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import programm.BinomialPanel;

public class Binomial extends JFrame{
    
  public Binomial(){
    super("Binomialverteilung");
    setLayout(new FlowLayout());
    getContentPane().add(new BinomialPanel());
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    pack();
  }
}