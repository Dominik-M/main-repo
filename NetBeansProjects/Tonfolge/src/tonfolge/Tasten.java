package tonfolge;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Tasten extends JPanel{
    public static final int TASTEN_ANZ=32;
    
    public Tasten(){
        setPreferredSize(new java.awt.Dimension(TASTEN_ANZ*10,50));
        setMinimumSize(getPreferredSize());
    }
    
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.BLACK);
        int min=60-TASTEN_ANZ/2;
        for(int i=0;i<TASTEN_ANZ;i++){
            int x=i*getWidth()/TASTEN_ANZ;
            g.drawString(min+i+"",x,getHeight());
            g.drawLine(x,0,x,getHeight());
        }
    }
}