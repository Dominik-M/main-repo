package philosophen;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Philosoph extends Thread{
    private static int nextID=0;
    
    private int t=0,lifetime;
    public final int ID, MAX_THINK_TIME,MAX_EAT_TIME;
    public static final int WAIT_TIME=100,MAX_LIFETIME=1000;
    private final javax.swing.JProgressBar progressBar;
    
    public Philosoph(javax.swing.JProgressBar pB){
        ID=nextID;
        nextID++;
        MAX_THINK_TIME=(int) (Math.random()*40+40);
        MAX_EAT_TIME=(int) (Math.random()*40+30);
        lifetime=(int) (Math.random()*MAX_LIFETIME+MAX_LIFETIME/2);
        progressBar=pB;
    }
    
    public Philosoph(){
        this(null);
    }
    
    @Override
    public void run(){
        while (lifetime>0) {
            t++;
            lifetime--;
            switch (getZustand()) {
                case THINKING:
                    if (t >= MAX_THINK_TIME && Main.startEating(ID)) {
                        t = 0;
                    }else Main.increaseThinkPoints();
                    break;
                case HUNGRY:
                    if (Main.startEating(ID)) {
                        t = 0;
                    }
                    break;
                case EATING:
                    if (t >= MAX_EAT_TIME) {
                        Main.stopEating(ID);
                        t=0;
                    }else Main.increaseEatPoints();
                    break;
            }
            if (progressBar != null) {
                switch (getZustand()) {
                    case THINKING:
                        progressBar.setMaximum(MAX_THINK_TIME);
                        progressBar.setForeground(Color.GREEN);
                        break;
                    case EATING:
                        progressBar.setMaximum(MAX_EAT_TIME);
                        progressBar.setForeground(Color.YELLOW);
                        break;
                    case HUNGRY:
                        progressBar.setForeground(Color.BLUE);
                }
                progressBar.setValue(t);
            }
            try {
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException ex) {
                Logger.getLogger(Philosoph.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Main.die(ID);
        progressBar.setForeground(Color.RED);
        progressBar.setValue(progressBar.getMaximum());
    }
    
    public Zustand getZustand(){
        return Main.stateOf(ID);
    }
    
    public int getTime(){
        return t;
    }
}
