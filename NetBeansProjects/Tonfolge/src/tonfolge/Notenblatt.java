package tonfolge;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;


/**
 *
 * @author Dundun
 */
public class Notenblatt extends JPanel implements MouseListener,MouseMotionListener{
    public static final int LINIEN_ANZ=20;
    private int linienAbstand=20;
    private int taktbreite=80;
    private int taktAnz=8;
    private int cursor=0,ton=0,mouseX=0,mouseY=0;
    private boolean active=false,flag=false;
    private java.util.LinkedList<Note> noten;
    
    public Notenblatt(){
        setPreferredSize(new java.awt.Dimension(taktAnz*taktbreite,linienAbstand*LINIEN_ANZ));
        setMinimumSize(getPreferredSize());
        noten=new java.util.LinkedList();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getWidth(),getHeight());
        int max=60+LINIEN_ANZ;
        linienAbstand=this.getHeight()/LINIEN_ANZ;
        taktbreite=linienAbstand*4;
        if(taktbreite<80)taktbreite=80;
        if(active){
          g.setColor(Color.BLUE);
          int x=mouseX*(taktbreite/4);
          int y=(max-mouseY)*linienAbstand/2-linienAbstand/2;
          g.fillOval(x,y,linienAbstand,linienAbstand);
          g.drawString("Tick: "+(cursor+1)+" Tonhöhe: "+ton,x,y);
        }
        g.setColor(Color.RED);
        for(Note n:noten){
            int x=(n.tick)*(taktbreite/4);
            int y=(max-n.ton)*linienAbstand/2-linienAbstand/2;
            g.fillOval(x,y,linienAbstand,linienAbstand);
            for(int i=1;i<n.len;i++)g.drawOval(x+i*(taktbreite/4),y,linienAbstand,linienAbstand);
        }
        g.setColor(Color.BLACK);
        for(int i=1;i<LINIEN_ANZ;i++){
            int y=i*linienAbstand;
            g.drawLine(0,y,getWidth(),y);
            g.drawChars(((max-i*2)+"").toCharArray(),0,2,0,y);
        }
        for(int i=1;i<taktAnz;i++){
            int x=i*taktbreite;
            g.drawLine(x,0,x,getHeight());
        }
    }
    
    public void füllTonfolge(Tonfolge tf){
        for(Note n:noten){
            tf.neueNote(n.ton,n.tick,n.len);
        }
    }
    
    public void save(File f){
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(noten);
            fos.close();
            oos.close();
            flag=false;
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
    
    public void lade(File f){
        FileInputStream fis;
        try {
            fis = new FileInputStream(f);
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                noten = (java.util.LinkedList) ois.readObject();
                fis.close();
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Notenblatt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clear(){
        flag=false;
        noten.clear();
    }
    
    public void addTakt(){
      taktAnz++;
      setPreferredSize(new java.awt.Dimension(taktAnz*taktbreite,linienAbstand*LINIEN_ANZ));
    }
    
    public void zufallNoten(int n){
        for(int i=0;i<n;i++){
            int l=1;
            while(Math.random()<0.4)l++;
            noten.add(new Note(l,1+(int)(Math.random()*4*taktAnz),60+(int)(LINIEN_ANZ*2*(Math.random()-0.5))));
        }
    }
    
    public boolean wurdeGeändert(){
        return flag;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if(cursor/4>=taktAnz-1){
            taktAnz++;
            setPreferredSize(new java.awt.Dimension(taktAnz*taktbreite,linienAbstand*LINIEN_ANZ));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        boolean neu=true;
        for(Note n:noten){
            if(n.tick==cursor && n.ton==ton){
                noten.remove(n);
                neu=false;
                break;
            }
        }
        if(neu){
            noten.add(new Note(1+e.getX()/(taktbreite/4)-cursor,cursor,ton));
        }
        flag=true;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        active=true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        active=false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        cursor=e.getX()/(taktbreite/4);
        ton=(60+LINIEN_ANZ)-e.getY()/(linienAbstand/2);
        int neuX=e.getX()/(taktbreite/4);
        int neuY=(60+LINIEN_ANZ)-e.getY()/(linienAbstand/2);
        if(neuX!=mouseX || neuY!=mouseY){
            mouseX=neuX;
            mouseY=neuY;
            repaint();
        }
    }
}
class Note implements java.io.Serializable{
    final int len,tick,ton;
    
    Note(int length,int pos,int tonhöhe){
        len=length;
        tick=pos;
        ton=tonhöhe;
    }
}