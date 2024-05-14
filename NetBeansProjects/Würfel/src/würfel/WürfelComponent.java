package würfel;

import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class WürfelComponent extends JPanel implements Runnable{
    private int augen,delay,loops;
    private Color background;
    
    public WürfelComponent(){
        setSize(new java.awt.Dimension(100, 100));
        setMinimumSize(getSize());
        setPreferredSize(getSize());
        augen=6;
        loops=10;
        delay=100;
        background=Color.WHITE;
    }
    
    @Override
    public void paintComponent(Graphics g){
        g.setColor(background);
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.BLACK);
        int x=getWidth()/7;
        int y=getHeight()/7;
        //oben links g.fillOval(x,y,x,y);
        //oben mitte g.fillOval(3*x,y,x,y);
        //oben rechts g.fillOval(5*x,y,x,y);
        //mitte links g.fillOval(x,3*y,x,y);
        //mitte mitte g.fillOval(3*x,3*y,x,y);
        //mitte rechts g.fillOval(5*x,3*y,x,y);
        //unten links g.fillOval(x,5*y,x,y);
        //unten mitte g.fillOval(3*x,5*y,x,y);
        //unten rechts g.fillOval(5*x,5*y,x,y);
        if(augen==6){
            g.fillOval(x,y,x,y);
            g.fillOval(5*x,y,x,y);
            g.fillOval(x,3*y,x,y);
            g.fillOval(5*x,3*y,x,y);
            g.fillOval(x,5*y,x,y);
            g.fillOval(5*x,5*y,x,y);
        }else if(augen==5){
            g.fillOval(x,y,x,y);
            g.fillOval(5*x,y,x,y);
            g.fillOval(3*x,3*y,x,y);
            g.fillOval(x,5*y,x,y);
            g.fillOval(5*x,5*y,x,y);
        }else if(augen==4){
            g.fillOval(x,y,x,y);
            g.fillOval(5*x,y,x,y);
            g.fillOval(x,5*y,x,y);
            g.fillOval(5*x,5*y,x,y);
        }else if(augen==3){
            g.fillOval(x,y,x,y);
            g.fillOval(3*x,3*y,x,y);
            g.fillOval(5*x,5*y,x,y);
        }else if(augen==2){
            g.fillOval(5*x,y,x,y);
            g.fillOval(x,5*y,x,y);
        }else {
            g.fillOval(3*x,3*y,x,y);
        }
    }
    
    public void werfen(){
        augen=(int)(Math.random()*6+1);
    }

    @Override
    public void run() {
        for(int i=0;i<loops;i++){
          werfen();
          repaint();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                Logger.getLogger(Würfel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int getAugen(){
        return augen;
    }
    
    public int getDelay(){
        return delay;
    }
    
    public void setDelay(int milis){
        delay=milis;
    }
    
    public int getLoops(){
        return loops;
    }
    
    public void setLoops(int durchläufe){
        loops=durchläufe;
    }
    
    public void setBGColor(Color bgColor){
        background=bgColor;
        repaint();
    }
}