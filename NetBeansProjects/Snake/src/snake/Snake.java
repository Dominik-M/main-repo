package snake;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

/**
 *
 * @author Dundun
 */
public class Snake implements KeyListener,ActionListener{
  private Turtle snake=new Turtle();
  private Turtle weg=new Turtle();
  private static double nextX,nextY;
  private static final int dicke=10;
  
  public Snake(){
    snake.setPen(dicke);
    weg.setPen(dicke+1);
    weg.setColor(Turtle.anzeige.getBackground());
    snake.line(100);
    Turtle.show();
    Turtle.anzeige.addKeyListener(this);
    Timer t=new Timer(1000,this);
    t.setDelay(200);
    t.start();
  }
  
  public static void main(String[] args) {
    new Snake();
    Turtle rand=new Turtle();
    rand.setPen(dicke);
    rand.moveTo(Turtle.anzeige.getWidth()/2,Turtle.anzeige.getHeight()/2);
    rand.turnTo(0);
    rand.line(Turtle.anzeige.getWidth());
    rand.turn(270);
    rand.line(Turtle.anzeige.getHeight());
    rand.turn(270);
    rand.line(Turtle.anzeige.getWidth());
    rand.turn(270);
    rand.line(Turtle.anzeige.getHeight());
  }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
      switch(e.getKeyCode()){
          case KeyEvent.VK_UP:
              if(snake.grad0!=270)
              snake.turnTo(90);
              break;
          case KeyEvent.VK_LEFT:
              if(snake.grad0!=0)
              snake.turnTo(180);
              break;
          case KeyEvent.VK_RIGHT:
              if(snake.grad0!=180)
                snake.turnTo(0);
              break;
          case KeyEvent.VK_DOWN:
              if(snake.grad0!=90)
                snake.turnTo(270);
              break;
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      weg.lineTo(nextX, nextY);
      snake.line(10);
      nextX=snake.x0;
      nextY=snake.y0;
      Turtle.show();
    }
}