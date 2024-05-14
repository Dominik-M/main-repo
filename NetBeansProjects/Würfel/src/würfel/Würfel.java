
package würfel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Dundun
 */
public class Würfel extends JFrame implements ActionListener{
    private WürfelComponent würfel=new WürfelComponent();
    private JButton werfen;
    
    public Würfel(){
      super("Würfel");
      setLayout(new BorderLayout(20,20));
      getContentPane().add(würfel,BorderLayout.CENTER);
      werfen=new JButton("Würfeln");
      werfen.addActionListener(this);
      getContentPane().add(werfen,BorderLayout.SOUTH);
      pack();
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(würfel).start();
    }
}