package spiel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.Timer;
import welt.orte.Alabastia;
import welt.orte.Ort;

public class Spielwelt extends JComponent implements ActionListener, KeyListener,
        MouseListener,java.io.Serializable{
  public static final int SPOT=16;
  public static final int ZENTRAL_X=Javamon.BREIT/2-1,ZENTRAL_Y=Javamon.HOCH/2-1;
  public static final Color BACKGROUND=new Color(238, 244, 132);
  
  private static int spot=32; //Seitenlänge eines Spots in Pixeln
  java.util.LinkedList<pokemon.Pokemon> boxPoks=new java.util.LinkedList();
  private Timer timer,auto;
  private Spieler spieler;
  Trainer rivale;
  private Ort pos;
  private Ort zuletzt;
  private int destX,destY;
  private java.util.LinkedList<Integer> path=new java.util.LinkedList();
  boolean gitterAn=false;
  
  public Spielwelt(){
      this(new Alabastia());
  }
  
  public Spielwelt(Ort startort){
    setPreferredSize(new Dimension(Javamon.BREIT*spot,Javamon.HOCH*spot));
    setLayout(null);
    addKeyListener(this);
    pos=startort;
    zuletzt=pos;
    spieler=new Spieler(pos.getStartX(),pos.getStartY());
    timer=new Timer(100,this);
    timer.setDelay(300);
    auto=new Timer(100,this);
    auto.setDelay(300);
    Sound.playSound(pos.toString().toLowerCase()+".wav");
    this.addMouseListener(this);
    setFocusable(true);
    requestFocusInWindow(); 
  }
  
  public Spieler getSpieler(){
    return spieler;
  }
  
  public Ort getOrt(){
    return pos;
  }

    @Override
    public void actionPerformed(ActionEvent e) {
      if(Javamon.isPrinting())return;
      if(e.getSource().equals(auto)){
        if(path.isEmpty()){
          auto.stop();
          return;
        }else spieler.richtung=path.poll();
      }
      spieler.x+=spieler.richtung%2;
      spieler.y+=spieler.richtung/2;
      if(!isFrei()){
        spieler.x-=spieler.richtung%2;
        spieler.y-=spieler.richtung/2;
        return;
      }
      repaint();
      pos.getObjekt(spieler.x,spieler.y).begangen();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
      if(Javamon.isPrinting()){
        if(e.getKeyCode()==KeyEvent.VK_SPACE)
          Javamon.printNext();
        return;
      }
      int keyCode=e.getKeyCode();
      if(keyCode==Steuerung.getUp()){
          spieler.richtung=Trainer.UP;
          timer.start();
      }else if(keyCode==Steuerung.getLeft()){
          spieler.richtung=Trainer.LEFT;
          timer.start();
      }else if(keyCode==Steuerung.getDown()){
          spieler.richtung=Trainer.DOWN;
          timer.start();
      }else if(keyCode==Steuerung.getRight()){
          spieler.richtung=Trainer.RIGHT;
          timer.start();
      }else if(keyCode==Steuerung.getA()){
          welt.objekte.Objekt o=pos.getObjekt(
                  spieler.x+spieler.richtung%2,
                  spieler.y+spieler.richtung/2);
          o.benutzt();
          repaint();
      }else if(keyCode==Steuerung.getB()){
            timer.setDelay(100);
      }else if(keyCode==Steuerung.getEnter()){
          Javamon.display(new Inventar(false));
      }else if(keyCode==Steuerung.getSave()){
          Javamon.save();
      }else if(keyCode==Steuerung.getLoad()){
          try{Javamon.load();}
          catch(Exception ex){Javamon.sout(ex.toString());}
      }
    }

    @Override
  public void keyReleased(KeyEvent e) {
    if(e.getKeyCode()==KeyEvent.VK_W || e.getKeyCode()==KeyEvent.VK_A ||
            e.getKeyCode()==KeyEvent.VK_S || e.getKeyCode()==KeyEvent.VK_D ||
            e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_LEFT ||
            e.getKeyCode()==KeyEvent.VK_DOWN || e.getKeyCode()==KeyEvent.VK_RIGHT){
      timer.stop();
      repaint();
    }
    else if(e.getKeyCode()==KeyEvent.VK_SHIFT)timer.setDelay(300);
  }
      
  public void ortwechsel(Ort neuPos,int neuX, int neuY){
    if(neuPos!=null){
      pos=neuPos;
      spieler.x=neuX;
      spieler.y=neuY;
      Javamon.sout(pos.toString());
      repaint();
      if(pos.getClass()!=welt.orte.Haus.class)
        Sound.playSound(pos.toString().toLowerCase()+".wav");
    }
  }
  
  public void ortwechsel(){
    ortwechsel(zuletzt,zuletzt.getStartX(),zuletzt.getStartY());
  }
  
  public boolean größer(){
    spot+=SPOT;
    setPreferredSize(new Dimension(Javamon.BREIT*spot,Javamon.HOCH*spot));
    repaint();
    return spot<64;
  }
  
  public boolean kleiner(){
    spot-=SPOT;
    setPreferredSize(new Dimension(Javamon.BREIT*spot,Javamon.HOCH*spot));
    repaint();
    return spot>16;
  }
    
  public boolean isFrei(){
    return pos.getObjekt(spieler.x,spieler.y).isBegehbar();
  }
 
    @Override
  public void paintComponent(Graphics g){
    //Hintergrund
    for(int x=spieler.x-ZENTRAL_X,x1=0;x<spieler.x+Javamon.BREIT-ZENTRAL_X;x++,x1++){
      for(int y=spieler.y+-ZENTRAL_Y,y1=0;y<spieler.y+Javamon.HOCH-ZENTRAL_Y;y++,y1++){
        pos.getBackground().paintComponent(g.create(x1*spot,y1*spot,spot,spot),spot);
      }
    }
        
    // Zeichne Welt
    for(int x=spieler.x-ZENTRAL_X,x1=0;x<spieler.x+Javamon.BREIT-ZENTRAL_X;x++,x1++){
      for(int y=spieler.y+-ZENTRAL_Y,y1=0;y<spieler.y+Javamon.HOCH-ZENTRAL_Y;y++,y1++){
        pos.getObjekt(x,y).paintComponent(g.create(x1*spot,y1*spot,spot,spot),spot);
      }
    }
    
    // Zeichne Spieler
    Color hautfarbe=Color.WHITE;
    Color kleidungfarbe=Color.RED;
    g.setColor(kleidungfarbe);
    if(spieler.richtung==Trainer.LEFT){
      //nach links schauend
      int b=SPOT;
      int[] schwarze={5,6,7,8,9,10,4+b,11+b,3+2*b,12+2*b,2+3*b,3+3*b,12+3*b,
      1+4*b,11+4*b,12+4*b,13+4*b,2+5*b,3+5*b,7+5*b,8+5*b,9+5*b,10+5*b,11+5*b,
      12+5*b,13+5*b,3+6*b,5+6*b,8+6*b,9+6*b,10+6*b,11+6*b,12+6*b,13+6*b,
      3+7*b,5+7*b,8+7*b,11+7*b,12+7*b,3+8*b,11+8*b,4+9*b,9+9*b,10+9*b,12+9*b,
      5+10*b,6+10*b,7+10*b,8+10*b,9+10*b,12+10*b,6+11*b,7+11*b,10+11*b,12+11*b,
      6+12*b,7+12*b,10+12*b,12+12*b,5+13*b,8+13*b,9+13*b,10+13*b,11+13*b,
      5+14*b,10+14*b,6+15*b,7+15*b,8+15*b,9+15*b};
      int[] rote={5+b,6+b,7+b,8+b,9+b,10+b,4+2*b,5+2*b,6+2*b,7+2*b,8+2*b,9+2*b,
      10+2*b,11+2*b,5+3*b,6+3*b,7+3*b,8+3*b,9+3*b,10+3*b,11+3*b,6+4*b,7+4*b,
      8+4*b,9+4*b,10+4*b,4+5*b,5+5*b,6+5*b,5+9*b,11+9*b,10+10*b,11+10*b,11+11*b,
      11+12*b,6+13*b,7+13*b,6+14*b,7+14*b,8+14*b,9+14*b,10+14*b};
      int[] weisse={4+3*b,2+4*b,3+4*b,4+4*b,5+4*b,4+6*b,6+6*b,7+6*b,4+7*b,6+7*b,
      7+7*b,9+7*b,10+7*b,4+8*b,5+8*b,6+8*b,7+8*b,8+8*b,9+8*b,10+8*b,6+9*b,7+9*b,
      8+9*b,8+11*b,9+11*b,8+12*b,9+12*b};
      for(int i:rote){
        g.fillRect(spot*ZENTRAL_X+(i%b*(spot/b)),
          spot*ZENTRAL_Y+(i/b*(spot/b)),spot/b,spot/b);
      }
      g.setColor(Color.BLACK);
      for(int i:schwarze){
        g.fillRect(spot*ZENTRAL_X+(i%b*(spot/b)),
                spot*ZENTRAL_Y+(i/b*(spot/b)),spot/b,spot/b);
      }
      g.setColor(hautfarbe);
      for(int i:weisse){
        g.fillRect(spot*ZENTRAL_X+(i%b*(spot/b)),
                spot*ZENTRAL_Y+(i/b*(spot/b)),spot/b,spot/b);
      }
    }
    else if(spieler.richtung==Trainer.RIGHT){
      //nach rechts schauend
      int b=SPOT;
      int[] schwarze={5,6,7,8,9,10,4+b,11+b,3+2*b,12+2*b,3+3*b,12+3*b,13+3*b,
      2+4*b,3+4*b,4+4*b,14+4*b,2+5*b,3+5*b,4+5*b,5+5*b,6+5*b,7+5*b,8+5*b,
      12+5*b,13+5*b,2+6*b,3+6*b,4+6*b,5+6*b,6+6*b,7+6*b,10+6*b,12+6*b,
      3+7*b,4+7*b,7+7*b,10+7*b,12+7*b,4+8*b,12+8*b,3+9*b,5+9*b,6+9*b,11+9*b,
      3+10*b,6+10*b,7+10*b,8+10*b,9+10*b,10+10*b,3+11*b,5+11*b,8+11*b,9+11*b,
      3+12*b,5+12*b,8+12*b,9+12*b,4+13*b,5+13*b,6+13*b,7+13*b,10+13*b,
      5+14*b,10+14*b,6+15*b,7+15*b,8+15*b,9+15*b};
      int[] rote={5+b,6+b,7+b,8+b,9+b,10+b,4+2*b,5+2*b,6+2*b,7+2*b,8+2*b,9+2*b,
      10+2*b,11+2*b,4+3*b,5+3*b,6+3*b,7+3*b,8+3*b,9+3*b,10+3*b,5+4*b,6+4*b,
      7+4*b,8+4*b,9+4*b,9+5*b,10+5*b,11+5*b,4+9*b,10+9*b,4+10*b,5+10*b,4+11*b,
      4+12*b,8+13*b,9+13*b,6+14*b,7+14*b,8+14*b,9+14*b};
      int[] weisse={11+3*b,10+4*b,11+4*b,12+4*b,13+4*b,8+6*b,9+6*b,11+6*b,
      5+7*b,6+7*b,8+7*b,9+7*b,11+7*b,5+8*b,6+8*b,7+8*b,8+8*b,9+8*b,10+8*b,
      11+8*b,7+9*b,8+9*b,9+9*b,6+11*b,7+11*b,6+12*b,7+12*b};
      for(int i:rote){
        g.fillRect(spot*ZENTRAL_X+(i%b*(spot/b)),
          spot*ZENTRAL_Y+(i/b*(spot/b)),spot/b,spot/b);
      }
      g.setColor(Color.BLACK);
      for(int i:schwarze){
        g.fillRect(spot*ZENTRAL_X+(i%b*(spot/b)),
                spot*ZENTRAL_Y+(i/b*(spot/b)),spot/b,spot/b);
      }
      g.setColor(hautfarbe);
      for(int i:weisse){
        g.fillRect(spot*ZENTRAL_X+(i%b*(spot/b)),
                spot*ZENTRAL_Y+(i/b*(spot/b)),spot/b,spot/b);
      }
    }
    else if(spieler.richtung==Trainer.UP){
      //nach oben schauend
      int b=SPOT;
      int[] schwarze={5,6,7,8,9,10,4+b,11+b,3+2*b,12+2*b,3+3*b,12+3*b,
      2+4*b,3+4*b,12+4*b,13+4*b,2+5*b,3+5*b,4+5*b,11+5*b,12+5*b,13+5*b,
      1+6*b,3+6*b,4+6*b,5+6*b,6+6*b,7+6*b,8+6*b,9+6*b,10+6*b,11+6*b,12+6*b,
      14+6*b,1+7*b,4+7*b,5+7*b,6+7*b,7+7*b,8+7*b,9+7*b,10+7*b,11+7*b,14+7*b,
      2+8*b,3+8*b,6+8*b,7+8*b,8+8*b,9+8*b,12+8*b,13+8*b,2+9*b,3+9*b,4+9*b,
      5+9*b,10+9*b,11+9*b,12+9*b,13+9*b,1+10*b,3+10*b,4+10*b,6+10*b,7+10*b,
      8+10*b,9+10*b,11+10*b,12+10*b,14+10*b,1+11*b,3+11*b,4+11*b,11+11*b,
      12+11*b,14+11*b,2+12*b,3+12*b,4+12*b,5+12*b,10+12*b,11+12*b,12+12*b,
      13+12*b,3+13*b,5+13*b,6+13*b,7+13*b,8+13*b,9+13*b,10+13*b,12+13*b,
      3+14*b,7+14*b,8+14*b,12+14*b,4+15*b,5+15*b,6+15*b,9+15*b,10+15*b,11+15*b};
      int[] rote={5+b,6+b,7+b,8+b,9+b,10+b,4+2*b,5+2*b,6+2*b,7+2*b,8+2*b,9+2*b,
      10+2*b,11+2*b,4+3*b,5+3*b,6+3*b,7+3*b,8+3*b,9+3*b,10+3*b,11+3*b,4+4*b,
      5+4*b,6+4*b,7+4*b,8+4*b,9+4*b,10+4*b,11+4*b,5+5*b,6+5*b,7+5*b,8+5*b,
      9+5*b,10+5*b,6+9*b,7+9*b,8+9*b,9+9*b,5+10*b,10+10*b,5+11*b,6+11*b,
      9+11*b,10+11*b,6+12*b,7+12*b,8+12*b,9+12*b,4+13*b,11+13*b,
      4+14*b,5+14*b,6+14*b,9+14*b,10+14*b,11+14*b};
      int[] weisse={2+6*b,13+6*b,2+7*b,3+7*b,12+7*b,13+7*b,4+8*b,5+8*b,10+8*b,
      11+8*b,2+10*b,13+10*b,2+11*b,7+11*b,8+11*b,13+11*b};
      for(int i:rote){
        g.fillRect(spot*ZENTRAL_X+(i%b*(spot/b)),
          spot*ZENTRAL_Y+(i/b*(spot/b)),spot/b,spot/b);
      }
      g.setColor(Color.BLACK);
      for(int i:schwarze){
        g.fillRect(spot*ZENTRAL_X+(i%b*(spot/b)),
                spot*ZENTRAL_Y+(i/b*(spot/b)),spot/b,spot/b);
      }
      g.setColor(hautfarbe);
      for(int i:weisse){
        g.fillRect(spot*ZENTRAL_X+(i%b*(spot/b)),
                spot*ZENTRAL_Y+(i/b*(spot/b)),spot/b,spot/b);
      }
    }
    else if(spieler.richtung==Trainer.DOWN){
        // nach unten schauend
        int b=SPOT;
        int[] schwarze={5,6,7,8,9,10,4+b,11+b,3+2*b,12+2*b,3+3*b,12+3*b,
                        2+4*b,3+4*b,4+4*b,11+4*b,12+4*b,13+4*b,2+5*b,3+5*b,
                        5+5*b,6+5*b,7+5*b,8+5*b,9+5*b,10+5*b,12+5*b,13+5*b,
                        1+6*b,3+6*b,12+6*b,14+6*b,1+7*b,6+7*b,9+7*b,14+7*b,
                        2+8*b,3+8*b,6+8*b,9+8*b,12+8*b,13+8*b,2+9*b,3+9*b,
                        4+9*b,11+9*b,12+9*b,13+9*b,1+10*b,4+10*b,5+10*b,
                        6+10*b,7+10*b,8+10*b,9+10*b,10+10*b,11+10*b,14+10*b,
                        1+11*b,4+11*b,5+11*b,6+11*b,7+11*b,8+11*b,9+11*b,
                        10+11*b,11+11*b,14+11*b,2+12*b,3+12*b,4+12*b,7+12*b,
                        8+12*b,11+12*b,12+12*b,13+12*b,3+13*b,5+13*b,6+13*b,
                        9+13*b,10+13*b,12+13*b,3+14*b,7+14*b,8+14*b,12+14*b,
                        4+15*b,5+15*b,6+15*b,9+15*b,10+15*b,11+15*b};
        int[] rote={5+b,6+b,7+b,8+b,9+b,10+b,4+2*b,5+2*b,6+2*b,7+2*b,8+2*b,
                    9+2*b,10+2*b,11+2*b,4+3*b,5+3*b,6+3*b,7+3*b,8+3*b,
                    9+3*b,10+3*b,11+3*b,5+4*b,10+4*b,7+9*b,8+9*b,5+12*b,
                    6+12*b,9+12*b,10+12*b,4+13*b,7+13*b,8+13*b,11+13*b,
                    4+14*b,5+14*b,6+14*b,9+14*b,10+14*b,11+14*b};
        int[]weisse={6+4*b,7+4*b,8+4*b,9+4*b,4+5*b,11+5*b,2+6*b,4+6*b,5+6*b,
                     6+6*b,7+6*b,8+6*b,9+6*b,10+6*b,11+6*b,13+6*b,2+7*b,
                     3+7*b,4+7*b,5+7*b,7+7*b,8+7*b,10+7*b,11+7*b,12+7*b,13+7*b,
                     4+8*b,5+8*b,7+8*b,8+8*b,10+8*b,11+8*b,5+9*b,6+9*b,
                     9+9*b,10+9*b,2+10*b,3+10*b,12+10*b,13+10*b,2+11*b,
                     3+11*b,12+11*b,13+11*b};
      for(int i:rote){
        g.fillRect(spot*ZENTRAL_X+(i%b*(spot/b)),
          spot*ZENTRAL_Y+(i/b*(spot/b)),spot/b,spot/b);
      }
      g.setColor(Color.BLACK);
      for(int i:schwarze){
        g.fillRect(spot*ZENTRAL_X+(i%b*(spot/b)),
                spot*ZENTRAL_Y+(i/b*(spot/b)),spot/b,spot/b);
      }
      g.setColor(hautfarbe);
      for(int i:weisse){
        g.fillRect(spot*ZENTRAL_X+(i%b*(spot/b)),
                spot*ZENTRAL_Y+(i/b*(spot/b)),spot/b,spot/b);
      }
    }
    
    //zeichne schwarzes Gitter
    if(gitterAn){
      g.setColor(Color.BLACK);
      for(int i=0;i<Javamon.BREIT;i++){
        g.drawLine(i*spot,0,i*spot,Javamon.HOCH*spot);
      }
      for(int i=0;i<Javamon.HOCH;i++){
        g.drawLine(0,i*spot,Javamon.BREIT*spot,i*spot);
      }    
    }
  }
    
  public void stopp() {
    timer.stop();
    Sound.stopSound();
    timer.setDelay(300);
  }

    @Override
    public void mousePressed(MouseEvent e) {
      if(Javamon.isPrinting())Javamon.printNext();
      else{
        auto.setDelay(100);
        path.clear();
        destX=e.getX()/spot-ZENTRAL_X;
        destY=e.getY()/spot-ZENTRAL_Y;
        if(((destX==0&&destY/2==0)||(destY==0&&destX/2==0))&&
            !pos.getObjekt(spieler.x+destX,spieler.y+destY).isBegehbar()){
          spieler.richtung=destX+destY*2;
          pos.getObjekt(spieler.x+destX,spieler.y+destY).benutzt();
          repaint();
        }else{
          findeWegVon(spieler.x,spieler.y);
          auto.start();
        }
      }
    }
    
    public void findeWegVon(int x,int y){
      if(destX>0&&pos.getObjekt(x+1, y).isBegehbar()){
        destX--;
        path.offer(Trainer.RIGHT);
        findeWegVon(x+1,y);
      }
      if(destX<0&&pos.getObjekt(x-1, y).isBegehbar()){
        destX++;
        path.offer(Trainer.LEFT);
        findeWegVon(x-1,y);
      }
      if(destY>0&&pos.getObjekt(x,y+1).isBegehbar()){
        destY--;
        path.offer(Trainer.DOWN);
        findeWegVon(x,y+1);
      }
      if(destY<0&&pos.getObjekt(x, y-1).isBegehbar()){
        destY++;
        path.offer(Trainer.UP);
        findeWegVon(x,y-1);
      }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {auto.setDelay(300);}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}