package bohnen.gui;

import bohnen.Bohne;
import bohnen.Bohnenkarte;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class KartenView extends javax.swing.JPanel {
    public static final int LEFT=1,DOWN=0,RIGHT=2;
    public static final BufferedImage[] IMAGES_DOWN=loadImages(DOWN),IMAGES_LEFT=loadImages(LEFT),IMAGES_RIGHT=loadImages(RIGHT);

    private final CopyOnWriteArrayList<Bohnenkarte> bohnen;
    private final CopyOnWriteArrayList<BufferedImage> images;
    private final CopyOnWriteArrayList<Image> scaledImages;
    private boolean vertlay,visible; 
    private int direction,gap; // gap=distance from image border and component border
    private String pos; // Description of this position on screen
    
    public KartenView() {
        initComponents();
        bohnen=new CopyOnWriteArrayList();
        images=new CopyOnWriteArrayList();
        scaledImages=new CopyOnWriteArrayList();
        direction=DOWN;
        gap=0;
//        addKarte(new Bohnenkarte(Bohne.GARTENBOHNE));
//        addKarte(new Bohnenkarte(Bohne.SOJABOHNE));
//        addKarte(new Bohnenkarte(Bohne.BRECHBOHNE));
    }
    
    public KartenView(String position,MouseListener ml, MouseMotionListener mml){
        this();
        pos=position;
        this.addMouseListener(ml);
        this.addMouseMotionListener(mml);
    }
    
    public void setVerdeckt(boolean verdeckt){
        this.visible = !verdeckt;
        refreshImages();
    }
    
    public void setDirection(int dir){
        direction=dir;
        refreshImages();
    }
    
    public void setVertical(boolean vertical){
        vertlay=vertical;
    }

    public void addKarte(Bohnenkarte neu) {
        bohnen.add(neu);
        BufferedImage img;
        if(visible)
            img=getImage(neu.SORTE,direction);
        else
            img=getImage(null,direction);
        images.add(img);
        scaledImages.add(scaleImage(img));
    }
    
    public void addFirst(Bohnenkarte neu){
        bohnen.add(0, neu);
        BufferedImage img;
        if(visible)
            img=getImage(neu.SORTE,direction);
        else
            img=getImage(null,direction);
        images.add(0,img);
        scaledImages.add(0,scaleImage(img));
    }
    
    public Bohnenkarte peek(){
        if(bohnen.size()<1)return null;
        return bohnen.get(0);
    }
    
    public Image peekImage(){
        if(scaledImages.size()<1)return null;
        return scaledImages.get(0);
    }
    
    public Bohnenkarte poll(){
        if(images.size()>0 && scaledImages.size()>0 && bohnen.size()>0){
          images.remove(0);
          scaledImages.remove(0);
          return bohnen.remove(0);
        }
        return null;
    }
    
    public void setPosName(String position){
        pos=position;
    }
    
    @Override
    public String toString(){
        if(pos!=null) return pos;
        else return super.toString();
    }
    
    @Override
    public void paintComponent(Graphics g){
        g.setColor(this.getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        if (scaledImages.size() > 0) {
            if (vertlay) {
                int ygap;
                if(scaledImages.size()>1)
                    ygap = gap / scaledImages.size();
                else
                    ygap = 0;
                for (int i = scaledImages.size()-1; i >=0; i--) {
                    g.drawImage(scaledImages.get(i), 0, i * ygap, null);
                    if(!visible && i>10) break;
                }
            } else {
                int xgap;
                if(scaledImages.size()>1)
                    xgap = gap / (scaledImages.size());
                else
                    xgap = 0;
                for (int i = scaledImages.size()-1; i >=0; i--) {
                    g.drawImage(scaledImages.get(i), i * xgap, 0, null);
                    if(!visible && i>10) break;
                }
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(122, 200));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        scaledImages.clear();
        for(BufferedImage img: images)
            scaledImages.add(scaleImage(img));
    }//GEN-LAST:event_formComponentResized

    private void refreshImages(){
        images.clear();
        scaledImages.clear();
        for (Bohnenkarte b : bohnen) {
            BufferedImage img;
            if (visible) {
                img = getImage(b.SORTE,direction);
            } else {
                img = getImage(null,direction);
            }
            images.add(img);
            scaledImages.add(scaleImage(img));
        }
    }
    
    public static BufferedImage[] loadImages(int direction){
        BufferedImage[] images=new BufferedImage[Bohne.values().length+1];
        try {
            images[0]=loadImage(null,direction);
            for(int i=1; i<images.length; i++)
                images[i]=loadImage(Bohne.values()[i-1],direction);
        } catch (IOException ex) {
            Logger.getLogger(KartenView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return images;
    }
    
    public static BufferedImage getImage(Bohne sorte, int direction) {
        switch (direction) {
            case DOWN:
                if (sorte == null) {
                    return IMAGES_DOWN[0];
                } else {
                    return IMAGES_DOWN[sorte.ordinal() + 1];
                }
            case LEFT:
                if (sorte == null) {
                    return IMAGES_LEFT[0];
                } else {
                    return IMAGES_LEFT[sorte.ordinal() + 1];
                }
            case RIGHT:
                if (sorte == null) {
                    return IMAGES_RIGHT[0];
                } else {
                    return IMAGES_RIGHT[sorte.ordinal() + 1];
                }
        }
        return null;
    }
    
    private static BufferedImage loadImage(Bohne sorte, int direction) throws IOException{
        String präfix;
        switch(direction){
                case LEFT:
                    präfix="/icons_left/";
                    break;
                case RIGHT:
                    präfix="/icons_right/";
                    break;
                default:
                    präfix="/icons/";
            }
        if(sorte!=null){
            return ImageIO.read(KartenView.class.getResource(präfix+sorte.getFileName()));
        }else{
            return ImageIO.read(KartenView.class.getResource(präfix+"ic_back.png"));
        }
    }
    
    private Image scaleImage(BufferedImage img){
        double ratio=400.0/244; // scalefactor: y = x * ratio
        if(direction!=DOWN)ratio=1.0/ratio;
        int width,height;
        if(vertlay){
            width=getWidth();
       //     if(width<=0)width=244;
            height=(int)(width*ratio);
            gap=getHeight()-height;
            if(gap<0)gap=0;
        }else{
            height=getHeight();
       //     if(height<=0)height=400;
            width=(int)(height/ratio);
            gap=getWidth()-width;
            if(gap<0)gap=0;
        }
        if(width==0 || height==0)return null;
        return img.getScaledInstance(width, height, 0);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}