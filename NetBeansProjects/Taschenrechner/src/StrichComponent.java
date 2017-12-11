import java.awt.*;
import javax.swing.JComponent;

public class StrichComponent extends JComponent{
  public StrichComponent(){
    setPreferredSize(new Dimension(200, 200));
  }

  @Override
  public void paintComponent(Graphics g){
    Graphics2D gg=(Graphics2D)g;
    BasicStroke str=new BasicStroke(5);
    gg.setStroke(str);
    Rectangle.Double linie=new Rectangle.Double(0, 0, 200, 50);
    gg.fill(linie);
    gg.setColor(Color.red);
    gg.draw(linie);
  }
}
