package wirreszeug;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class WirresZeug extends JFrame implements KeyListener{
  private String[] zeug={"w","i","r","r","e","s"," ","Z","e","u","g"," "};
  private int pos=0;
  private JTextArea text=new JTextArea("Der schreibt nur wirres Zeug! \n");
  
  public WirresZeug(){
    super("Neues Textdokument");
    setLayout(new BorderLayout());
    text.addKeyListener(this);
    text.setLineWrap(true);
    text.setEditable(false);
    text.setFont(new Font(Font.DIALOG,25,25));
    JScrollPane sp=new JScrollPane(text);
    getContentPane().add(sp);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    pack();
    setExtendedState(MAXIMIZED_BOTH);
    setVisible(true);
  }
  public static void main(String[] args) {
    new WirresZeug();
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if(e.getKeyCode()==KeyEvent.VK_ENTER) text.append("\n");
    else{
      text.append(zeug[pos]);
      pos++;
      if(pos>=zeug.length){
        pos=0;
        int size=(int)(Math.random()*70+15);
        text.setFont(new Font(Font.MONOSPACED,size,size));
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }
}
