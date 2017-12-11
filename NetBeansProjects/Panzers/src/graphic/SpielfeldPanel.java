/*
 * Copyright (C) 2015 Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import panzers.Box;
import panzers.ClockTimed;
import panzers.Drawable;
import panzers.Item;
import panzers.ItemEffect;
import panzers.Obstacle;
import panzers.Projectile;
import panzers.Tank;
import panzers.TankKI;
import sounds.Sound;
import utils.Vektor;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class SpielfeldPanel extends javax.swing.JPanel {
    
    public static final Color COLOR_TRANSPARENT=new Color(240,240,240);
    public static final Color BG_COLOR=Color.YELLOW.darker();
    public static final Color MUD_COLOR=new Color(145,74,22);
    // images
    private Image boxImage;
    private int[][] tankImagePlayer;
    private int[][] tankImageEnemy;
    private int[][] tankImageAlly;
    private int[][] planeImage;
    private int[][] standartGunImage;
    private Image ammoImage;
    private Image healthImage;
    private Image expImage;
    private Image explosImage;
    private Image kraterImage;
    private int[][] bulletImage;
    private int[][] flameImage;
    private Image bgImageScaled;
    private Image mapbgImage;
    private BufferedImage bgImage;
    
    private int fps=50;
    private Delay delay=new Delay(1000/fps);
    private long t0,delta_t;
    private double realFPS=0;
    private int tcount;
    private final Timer clock;
    private Tank mTank,yTank=null;
    private int hit=0;
    private final List<Tank> tanks=new CopyOnWriteArrayList();
    private final List<TankKI> kis=new CopyOnWriteArrayList();
    private final List<Box> boxes=new CopyOnWriteArrayList();
    private final List<Item> items=new CopyOnWriteArrayList();
    private final List<Obstacle> walls=new CopyOnWriteArrayList();
    private final List<Projectile> projectiles=new CopyOnWriteArrayList();
    private final List<Effect> effects=new CopyOnWriteArrayList();
    private int mouseX,mouseY;
    private int viewX,viewY;
    public final Rectangle BOUNDS;
    public final int MAP_WIDTH=150, MAP_HEIGHT=150;
    public final double MAPSCALEX,MAPSCALEY;
    private final LinkedList<Integer> pressedKeys=new LinkedList();
    private final LinkedList<Integer> pressedMouseKeys=new LinkedList();
    private List<TimedText> eventMessages=new CopyOnWriteArrayList();
    private TimedText hintMessage;
    private boolean drawTraces=true;

    /**
     * Creates new form SpielfeldPanel
     */
    public SpielfeldPanel() {
        initComponents();
        viewX=0;
        viewY=0;
        clock=new Timer(1000/fps, (ActionEvent e) -> {
            clockSignal();
        });
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    if(clock.isRunning()){
                        clock.stop();
                        repaint();
                        UpgradeScreen.show(getLocationOnScreen().x+getWidth()/3,getLocationOnScreen().y+getHeight()/3,mTank);
                        if(tanks.contains(mTank));
                        clock.start();
                    }else {
                        if(tanks.contains(mTank))
                          clock.start();
                    }
                }else if(e.getKeyCode() == KeyEvent.VK_CONTROL){
                    clock.stop();
                    repaint();
                }
                else if(!pressedKeys.contains(e.getKeyCode()))
                  pressedKeys.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_CONTROL){
                    if(tanks.contains(mTank))
                    clock.restart();
                }
                else if(pressedKeys.contains(e.getKeyCode()))
                  pressedKeys.remove((Integer)e.getKeyCode());
            }
        });
        clock.setDelay(1000/fps);
        clock.start();
        t0=System.nanoTime();
        tcount=0;
        try {
            tankImagePlayer = this.getImageRGB("/icons/car_green.png");
            tankImageEnemy = this.getImageRGB("/icons/car_red.png");
            tankImageAlly = this.getImageRGB("/icons/car_blue.png");
            planeImage = this.getImageRGB("/icons/plane.png");
            standartGunImage = this.getImageRGB("/icons/gun_green.png");
            boxImage = GUI.makeColorTransparent(getImage("/icons/box.png"), COLOR_TRANSPARENT);
            ammoImage = GUI.makeColorTransparent(getImage("/icons/item_ammo.png"), COLOR_TRANSPARENT);
            healthImage = GUI.makeColorTransparent(getImage("/icons/item_health.png"), COLOR_TRANSPARENT);
            expImage = GUI.makeColorTransparent(getImage("/icons/item_exp.png"), COLOR_TRANSPARENT);
            explosImage = GUI.makeColorTransparent(getImage("/icons/explosion.png"),COLOR_TRANSPARENT);
            kraterImage = GUI.makeColorTransparent(getImage("/icons/krater.png"),COLOR_TRANSPARENT);
            bulletImage = this.getImageRGB("/icons/projectile_bullet.png");
            flameImage = this.getImageRGB("/icons/projectile_flame.png");
            bgImage=ImageIO.read(getClass().getResource("/icons/bg_image.png"));
            
        } catch (IOException ex) {
            Logger.getLogger(SpielfeldPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(bgImage!=null){
          BOUNDS=new Rectangle(0,0,bgImage.getWidth(),bgImage.getHeight());
          bgImageScaled=bgImage.getScaledInstance(BOUNDS.width, BOUNDS.height, 0);
          mapbgImage=bgImage.getScaledInstance(MAP_WIDTH, MAP_HEIGHT, 0);
        }else{
            BOUNDS=new Rectangle(0,0,2048,2048);
        }
        MAPSCALEX=MAP_WIDTH*1.0/BOUNDS.width;
        MAPSCALEY=MAP_HEIGHT*1.0/BOUNDS.height;
    }
    
    public void setTank(Tank t){
        tanks.remove(mTank);
        mTank=t;
        if(t!=null){
          if(yTank!=null){
              if(t.playersTeam){
                t.setTankImage(scale(t.width,t.height,tankImageAlly));
                t.setGunImage(scale(t.width,t.height,standartGunImage));
            }else{
                t.setTankImage(scale(t.width,t.height,tankImageEnemy));
                t.setGunImage(scale(t.width,t.height,standartGunImage));
            }
          }else{
            t.setTankImage(scale(t.width,t.height,tankImagePlayer));
            t.setGunImage(scale(t.width,t.height,standartGunImage));
          }
          tanks.add(mTank);
        }
        if(!clock.isRunning())clock.start();
    }
    
    public void setTank2(Tank t){
        tanks.remove(yTank);
        yTank=t;
        if(t!=null){
          if(t.playersTeam){
                t.setTankImage(scale(t.width,t.height,tankImageAlly));
                t.setGunImage(scale(t.width,t.height,standartGunImage));
            }else{
                t.setTankImage(scale(t.width,t.height,tankImageEnemy));
                t.setGunImage(scale(t.width,t.height,standartGunImage));
            }
          tanks.add(yTank);
        }
    }
    
    void reset() {
        projectiles.clear();
        tanks.clear();
        boxes.clear();
        kis.clear();
        items.clear();
        eventMessages.clear();
        hintMessage=null;
    }
    
    public Tank getTank(){
        return mTank;
    }
    
    public void addTank(Tank t){
        if(clock.isRunning()){
            tanks.add(t);
            if(t.playersTeam){
                t.setTankImage(scale(t.width,t.height,tankImageAlly));
                t.setGunImage(scale(t.width,t.height,standartGunImage));
            }else{
                t.setTankImage(scale(t.width,t.height,tankImageEnemy));
                t.setGunImage(scale(t.width,t.height,standartGunImage));
            }
        }
    }
    
    public void addKI(TankKI ki){
        if(clock.isRunning()){
          kis.add(ki);
          addTank(ki.mTank);
        }
        if(ki.friend) printHint("Reinforcements arrived!");
        else printHint("Enemies appeared!");
    }
    
    public void addBox(Box b){
        if(clock.isRunning()){
            b.setImage(boxImage);
            boxes.add(b);
        }
        printHint("Supplies have arrived!");
    }
    
    public void addItem(Item i){
        if(clock.isRunning()){
            switch(i.effect){
                case AMMO:
                    i.setImage(ammoImage);
                    break;
                case HEALTH:
                    i.setImage(healthImage);
                    break;
                case EXP:
                    i.setImage(expImage);
                    break;
            }
            items.add(i);
        }
    }
    
    public void addObstacle(Obstacle o){
        if(clock.isRunning())
          walls.add(o);
    }
    
    public void addExplosion(int x, int y, int r){
        effects.add(new Krater(x,y,r/10,5000));
        effects.add(new Explosion(x,y,r));
    }
    
    public void addProjectile(Projectile p){
        if(bulletImage!=null){
            switch(p.getSource().toString()){
                case "Machine Gun":case "Small Gun": case "Gatlin Gun": case "Grenade Launcher":
                case "Rocket Launcher": case "Airstrike":
                    p.setImage(bulletImage);
                    break;
                case "Flamethrower":
                    p.setImage(flameImage);
                    break;
            }
        }
        projectiles.add(p);
    }
    
    private void removeProjectile(Projectile p) {
        if(p.getSource().areaEffect>1){
            addExplosion((int)p.getX(),(int)p.getY(),p.getSource().areaEffect*2);
            for(Tank t: tanks){
                boolean enemy=(t.playersTeam && !p.getSource().getOwner().playersTeam) ||
                        (!t.playersTeam && p.getSource().getOwner().playersTeam);
                double distance=new Vektor(p.getX()-t.getX(),p.getY()-t.getY()).getBetrag();
                if( enemy && distance<p.getSource().areaEffect)
                {
                    if(t.hit(p,distance)){
                        tankDestroyed(p.getSource().getOwner(),t);
                    }
                    if(p.getSource().getOwner().equals(mTank))hit=fps/5;
                }
            }
            for(Box b:boxes){
                double distance=new Vektor(p.getX()-b.x,p.getY()-b.y).getBetrag();
                if(distance<p.getSource().areaEffect){
                    if(b.hit(p,distance)){
                        boxes.remove(b);
                        if(b.item!=null)
                          addItem(new Item(b.x,b.y,b.width,b.height,b.item));
                    }
                }
            }
        }
        projectiles.remove(p);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        if(clock.isRunning() && yTank==null){
          mouseX=evt.getX();
          mouseY=evt.getY();
        }
    }//GEN-LAST:event_formMouseMoved

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        pressedMouseKeys.remove((Integer)evt.getButton());
    }//GEN-LAST:event_formMouseReleased

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        requestFocus();
    }//GEN-LAST:event_formMouseEntered

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        if(clock.isRunning())
            pressedMouseKeys.add((Integer)evt.getButton());
        else{
            for(Tank t:tanks){
                if(t.getTankShapeOnScreen().contains(evt.getX()-viewX, evt.getY()-viewY)){
                  UpgradeScreen.show(getLocationOnScreen().x+getWidth()/3,getLocationOnScreen().y+getHeight()/3,t);
                  if(tanks.contains(mTank))clock.restart();
                  break;
                }
            }
        }
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        formMouseMoved(evt);
    }//GEN-LAST:event_formMouseDragged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
 
    private void clockSignal(){
        // check pressed keys
        if(yTank==null){
            viewX=getWidth()-mTank.getX()-mouseX;
            viewY=getHeight()-mTank.getY()-mouseY;
            mTank.setAim(mouseX-viewX, mouseY-viewY);
            for (int mouseKey : pressedMouseKeys) {
                if (mouseKey == 1 && mTank.getPrimWeapon() != null) {
                    Projectile[] fired = mTank.fire(0);
                    for (Projectile p : fired) {
                        addProjectile(p);
                    }
                } else if (mouseKey == 3 && mTank.getSecWeapon() != null) {
                    Projectile[] fired = mTank.fire(1);
                    for (Projectile p : fired) {
                        addProjectile(p);
                    }
                }
            }
        }
        for(int pressedKey: pressedKeys)
          switch(pressedKey){
            case KeyEvent.VK_W: 
                if(yTank!=null){
                    yTank.accelerate(true, getDelay());
                    break;
                }
            case KeyEvent.VK_UP:
                mTank.accelerate(true, getDelay());
                break;
            case KeyEvent.VK_S: 
                if(yTank!=null){
                    yTank.accelerate(false, getDelay());
                    break;
                }
            case KeyEvent.VK_DOWN:
                mTank.accelerate(false, getDelay());
                break;
            case KeyEvent.VK_A:
                if(yTank!=null){
                    yTank.rotate(false, getDelay());
                    break;
                }
            case KeyEvent.VK_LEFT:
                mTank.rotate(false, getDelay());
                break;
            case KeyEvent.VK_D: 
                if(yTank!=null){
                    yTank.rotate(true, getDelay());
                    break;
                }
            case KeyEvent.VK_RIGHT:
                mTank.rotate(true, getDelay());
                break;
            case KeyEvent.VK_SPACE:
                if(yTank==null)
                  mTank.accelerate(mTank.getSpeed()<0, getDelay());
                else{
                    Projectile[] fired;
                    if (yTank.getPrimWeapon() != null) {
                        fired = yTank.fire(0);
                        for (Projectile p : fired) {
                            addProjectile(p);
                        }
                    }
                    if (yTank.getSecWeapon() != null) {
                        fired = yTank.fire(1);
                        for (Projectile p : fired) {
                            addProjectile(p);
                        }
                    }
                }
                break;
            case KeyEvent.VK_SHIFT:
                if(mTank!=null){
                    Projectile[] fired;
                    if (mTank.getPrimWeapon() != null) {
                        fired = mTank.fire(0);
                        for (Projectile p : fired) {
                            addProjectile(p);
                        }
                    }
                    if (mTank.getSecWeapon() != null) {
                        fired = mTank.fire(1);
                        for (Projectile p : fired) {
                            addProjectile(p);
                        }
                    }
                }
                break;
          }
        
        // Move tanks
        for (Tank t : tanks) {
            int oldX=t.getX(),oldY=t.getY();
            t.move(getDelay());
            // check borders
            if (t.getX() - t.width / 2 < 0) {
                t.setX(t.width / 2);
                t.stop();
            } else if (t.getX() + t.width / 2 > BOUNDS.getWidth()) {
                t.setX(BOUNDS.getWidth() - t.width / 2);
                t.stop();
            } else if (t.getY() - t.height / 2 < 0) {
                t.setY(t.height / 2);
                t.stop();
            } else if (t.getY() + t.height / 2 > BOUNDS.getHeight()) {
                t.setY(BOUNDS.getHeight() - t.height / 2);
                t.stop();
            }
            // draw traces
            if(drawTraces && (oldX!=t.getX() || oldY!=t.getY())){
                Vektor direction=t.getSpeedVektor();
                direction.div(direction.getBetrag());
                Vektor orth=direction.orthogonal();
                direction.mul(t.height/2);
                orth.mul(t.width/2);
                int tX=(int) (t.getX()+direction.getX()+orth.getX());
                int tY=(int) (t.getY()+direction.getY()+orth.getY());
                Color bg;
                try{bg=new Color(bgImage.getRGB(tX, tY));}
                catch(Exception ex){bg=BG_COLOR;}
                effects.add(new Trace(tX, tY, bg));
                tX-=2*orth.getX();
                tY-=2*orth.getY();
                try{bg=new Color(bgImage.getRGB(tX, tY));}
                catch(Exception ex){bg=BG_COLOR;}
                effects.add(new Trace(tX, tY, bg));
            }
            // check items
            for(Item i: items){
                if( i.x+i.width > t.getX()-t.width && i.x-i.width < t.getX()+t.width &&
                    i.y+i.height > t.getY()-t.height && i.y-i.height < t.getY()+t.height){
                    int tanklevel=t.getLevel();
                    if(t.consumeItem(i.effect)){
                        printTankEvent(i.effect.name()+" supplies collected",t,1000);
                        if(i.effect==ItemEffect.EXP && tanklevel!=t.getLevel()){
                            printTankEvent("Level Up!",t,1000);
                            if(yTank!=null){
                                t.weaponUpgrade();
                                t.randomUpgrades();
                            }
                        }
                        items.remove(i);
                    }
                }
            }
            // make tanks reload their guns
            t.refreshGuns(getDelay());
            // damage over time effects
            Projectile hit=t.getHit();
            if(hit!=null){
                hit.move(getDelay().MS);
                hit.setPos(t.getX(), t.getY());
                if(t.reduceHP(hit.getSource().getDamage()*getDelay().MS/100))
                    tankDestroyed(hit.getSource().getOwner(),t);
            }
        }
        if(yTank!=null){
            yTank.setAim(10*Math.cos(yTank.getDirection())+yTank.getX(),10*Math.sin(yTank.getDirection())+yTank.getY());
            mTank.setAim(10*Math.cos(mTank.getDirection())+mTank.getX(),10*Math.sin(mTank.getDirection())+mTank.getY());
        }
        
        // Move projectiles
        for(Projectile p: projectiles){
            double oldX=p.getX(),oldY=p.getY();
            if(!p.move(getDelay().MS)){
                removeProjectile(p);
            }else{
              if(p.getX()<0 || p.getX()>BOUNDS.getWidth()){
                if(p.getSource().reflectionOn)
                  p.getVektor().addVektor(new Vektor(-2*p.getVektor().getX(),0));
                else {
                    removeProjectile(p);
                    continue;
                }
              }
              if(p.getY()<0 || p.getY()>BOUNDS.getHeight()){
                if(p.getSource().reflectionOn)
                  p.getVektor().addVektor(new Vektor(0,p.getVektor().getY()*-2));
                else{
                    removeProjectile(p);
                    continue;
                }
              }
            }
            // check if something was hit
            for(Obstacle o:walls){
                if(polygonContainsLine(o.getShapeOnScreen(),
                        (int)oldX,(int)oldY,
                        new Vektor(p.getVektor().getX(),p.getVektor().getY()),
                        (int)p.getX(), (int)p.getY())){
                    if(o.hit(p)){
                        walls.remove(o);
                        java.awt.Rectangle bounds=o.getShapeOnScreen().getBounds();
                        effects.add(new Explosion(bounds.x+bounds.width/2,bounds.y+bounds.height/2,2*(bounds.height+bounds.width)));
                    }
                    removeProjectile(p);
                }
            }
            for(Tank t: tanks){
                boolean enemy=(t.playersTeam && !p.getSource().getOwner().playersTeam) ||
                        (!t.playersTeam && p.getSource().getOwner().playersTeam);
                if( enemy && polygonContainsLine(t.getTankShapeOnScreen(),
                        (int)oldX,(int)oldY,
                        new Vektor(p.getVektor().getX(),p.getVektor().getY()),
                        (int)p.getX(), (int)p.getY()))
                {
                    if(p.getSource().areaEffect<1 && t.hit(p)){
                        tankDestroyed(p.getSource().getOwner(),t);
                    }
                    if(p.getSource().getOwner().equals(mTank))hit=fps/5;
                    removeProjectile(p);
                }
            }
            for(Box b:boxes){
                if( p.getX() > b.x-b.width && p.getX() < b.x+b.width &&
                    p.getY() > b.y-b.height && p.getY() < b.y+b.height){
                    if(p.getSource().areaEffect<1 && b.hit(p)){
                        boxes.remove(b);
                        if(b.item!=null)
                          addItem(new Item(b.x,b.y,b.width,b.height,b.item));
                    }
                    removeProjectile(p);
                }
            }
        }
        
        // Send signal to KIs
        for(TankKI ki: kis){
            Projectile[] fired=ki.signal(this,getDelay());
            if(fired!=null)for(Projectile p:fired){
                addProjectile(p);
            }
        }
        // Send signal to effects
        for(Effect e:effects)
            if(!e.clockSignal(getDelay()))
                effects.remove(e);
        // Send signal to timedTexts
        for(TimedText e: eventMessages){
          if(e!=null)
            if(!e.clockSignal(getDelay()))
                eventMessages.remove(e);
        }
        if(hintMessage!=null){
            if(!hintMessage.clockSignal(getDelay()))
                hintMessage=null;
        }
        else if(mTank.getBonusPoints()>0){
            printHint(mTank.getBonusPoints()+" Bonus Points Available! PRESS ENTER");
        }
        // measure time between clockSignals (real fps)
        delta_t=System.nanoTime()-t0;
        t0=System.nanoTime();
        if(tcount>=500){
            realFPS=1000000000.0/delta_t;
            tcount=0;
        }else tcount+=delay.MS;
        repaint();
    }
    
    public void tankDestroyed(Tank source,Tank destroyed) {
        for(Tank t: getEnemys(!source.playersTeam))
          if (t.addExp(destroyed.getValue())) {
            printTankEvent("Level Up!", t, 1000);
            if (yTank != null) {
                t.weaponUpgrade();
                t.randomUpgrades();
            }
          }
        if (destroyed.equals(mTank)) {
            clock.stop();
        }else if(destroyed.equals(yTank)){
            setTank2(null);
        }
        else {
            for (TankKI ki : kis) {
                if (ki.mTank.equals(destroyed)) {
                    kis.remove(ki);
                }
            }
        }
        tanks.remove(destroyed);
        java.awt.Rectangle bounds = destroyed.getTankShapeOnScreen().getBounds();
        addExplosion(bounds.x + bounds.width / 2, bounds.y + bounds.height / 2, (bounds.height + bounds.width) * 2);
    }
    
    public int getTankX(){
        return mTank.getX();
    }
    
    public int getTankY(){
        return mTank.getY();
    }
    
    public int getTankCount(){
        return tanks.size();
    }
    
    public Vektor[] getEnemysCoords(boolean playersTeam){
        LinkedList<Vektor> list=new LinkedList();
        for(Tank t: tanks){
            if((t.playersTeam && !playersTeam) || (!t.playersTeam && playersTeam)){
                list.add(new Vektor(t.getX(),t.getY()));
            }
        }
        Vektor[] coords=new Vektor[list.size()];
        for(int i=0; i<coords.length; i++)coords[i]=list.get(i);
        return coords;
    }
    
    public Tank[] getEnemys(boolean playersTeam){
        LinkedList<Tank> list=new LinkedList();
        for(Tank t: tanks){
            if((t.playersTeam && !playersTeam) || (!t.playersTeam && playersTeam)){
                list.add(t);
            }
        }
        Tank[] t=new Tank[list.size()];
        for(int i=0; i<t.length; i++)t[i]=list.get(i);
        return t;
    }
    
    public final Delay getDelay(){
        return delay;
    }
    
    public void setFPS(int newFPS){
        fps=newFPS;
        delay=new Delay(1000/fps);
        clock.setDelay((int) delay.MS);
        if(tanks.contains(mTank))
          clock.restart();
    }
    
    @Override
    public void paintComponent(Graphics g){
//        Background
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.translate(viewX, viewY);
        g.drawImage(bgImageScaled, 0, 0, this);
//        Grid
//        int spot=20;
//        g.setColor(Color.DARK_GRAY);
//        for(int x=spot; x<getWidth(); x+=spot)g.drawLine(x, 0, x, getHeight());
//        for(int y=spot; y<getHeight(); y+=spot)g.drawLine(0, y, getWidth(), y);
        for(Effect e:effects){
            if(e.getClass()==Explosion.class)e.draw(g,explosImage);
            else if(e.getClass()==Krater.class)e.draw(g,kraterImage);
            else e.draw(g);
        }
        for(Item i:items)i.draw(g);
        for(Box b:boxes)b.draw(g);
        for(Obstacle o:walls)o.draw(g);
        for(Tank t: tanks)
          t.draw(g);
        for(Projectile p: projectiles)
            if(p.getX()<0 || p.getX()>BOUNDS.getWidth() || p.getY()<0 || p.getY()>BOUNDS.getHeight()) continue;
            else drawProjectile(g,p);
        g.setColor(Color.BLACK);
        // Texts
        g.setFont(new java.awt.Font("Impact", 0, 14));
        for(TimedText e: eventMessages)
          if(e!=null)    
            g.drawString(e.text, e.getX(), e.getY());
        g.translate(-viewX, -viewY);
        g.drawString("FPS: "+UpgradeScreen.round(realFPS, 2), 0, 14);
        if(hintMessage!=null){
            g.setFont(new java.awt.Font("Impact", 0, getWidth()/25));
            g.drawString(hintMessage.text, hintMessage.getX(), hintMessage.getY());
        }
        if(!clock.isRunning()){
            g.setFont(new java.awt.Font("Impact", 0, 32));
            if(!tanks.contains(mTank))
              g.drawString("GAME OVER", getWidth()/2, getHeight()/2);
            else g.drawString("PAUSE", getWidth()/2, getHeight()/2);
        }
        // minimap
        int mx,my;
        mx=getWidth()-MAP_WIDTH-10;
        my=getHeight()-MAP_HEIGHT-10;
        g.drawImage(mapbgImage, mx, my, this);
        g.drawRect(mx, my, MAP_WIDTH, MAP_HEIGHT);
        for(Tank t:tanks){
            if(t.playersTeam)g.setColor(Color.CYAN);
            else g.setColor(Color.RED);
            g.fillRect((int)(mx+MAPSCALEX*t.getX())-1, (int)(my+MAPSCALEY*t.getY())-1, 3, 3);
        }
        if(mTank!=null){
            g.setColor(Color.WHITE);
            g.fillRect((int)(mx+MAPSCALEX*mTank.getX())-1, (int)(my+MAPSCALEY*mTank.getY())-1, 3, 3);
        }
        g.setColor(Color.ORANGE.darker());
        for(Box b: boxes)g.fillRect((int)(mx+MAPSCALEX*b.x)-1, (int)(my+MAPSCALEY*b.y)-1, 3, 3);
        if(hit>0){
            hit--;
            g.drawLine(mouseX+20,mouseY+20,mouseX-20,mouseY-20);
            g.drawLine(mouseX+20,mouseY-20,mouseX-20,mouseY+20);
        }
    }
    
    public static void drawProgressBar(Graphics g,int x,int y,int width, int height, double value, double maxValue, Color bg, Color fg){
        g.setColor(bg);
        g.fillRect(x, y, width, height);
        g.setColor(fg);
        g.fillRect(x, y, (int)(width*value/maxValue), height);
    }
    
    private void drawProjectile(Graphics g, Projectile p){
        p.draw(g);
    }
    
    public static void drawLine(Graphics g,int x, int y, Vektor direction, int linelength, int width){
        Vektor v=new Vektor(direction.getX(), direction.getY());
        v.div(v.getBetrag());
        v.mul(linelength);
        for(int n=0; n<width; n++){
            g.drawLine(x-n, y-n,(int) (x+v.getX()-n), (int) (y+v.getY()-n));
            g.drawLine(x+n, y+n,(int) (x+v.getX()+n), (int) (y+v.getY()+n));
        }
    }
    
    public void printHint(String text){
        hintMessage=new TimedText(text,getWidth()/4, getHeight(),2000);
    }
    
    public void printTankEvent(String text,Tank t, double millis){
        for(TimedText msg:eventMessages)
            if(t.equals(msg.t))eventMessages.remove(msg);
        eventMessages.add(new TimedText(text,t,millis));
    }
    
    public static final BufferedImage getImage(String filename){
        try {
            return ImageIO.read(SpielfeldPanel.class.getResource(filename));
        } catch (IOException ex) {
            System.err.println(ex);
            return null;
        }
    }
    
    public final int[][] getImageRGB(String filename) throws IOException{
        BufferedImage image = ImageIO.read(getClass().getResource(filename));
        int[][] pixels = new int[image.getWidth()][image.getHeight()];
        for (int x = 0; x < pixels.length; x++) {
            for (int y = 0; y < pixels[x].length; y++) {
                pixels[x][y] = image.getRGB(x, y);
            }
        }
        return pixels;
    }
    
    public static int[][] scale(int width,int height,int[][] pixels){
        double xFaktor=1.0*pixels.length/width;
        double yFaktor=1.0*pixels[0].length/height;
        double x=0,y=0;
        int[][] newpixels=new int[width][height];
        for(int screenX=0; screenX<width; screenX++){
            y=0;
            for(int screenY=0; screenY<height; screenY++){
                newpixels[screenX][screenY]=pixels[(int)x][(int)y];
                y+=yFaktor;
            }
            x+=xFaktor;
        }
        return newpixels;
    }
    
    public static void drawPixels(Graphics g,int xOffset,int yOffset,int mX,int mY,double phi,int[][] pixels){
        for(int x=0; x<pixels.length; x++){
            for(int y=0; y<pixels[x].length; y++){
                g.setColor(new Color(pixels[x][y]));
                if(g.getColor().equals(COLOR_TRANSPARENT))continue;
                Vektor coords=new Vektor(x-mX,y-mY);
                coords.rotate(phi);
                g.fillRect((int)(xOffset+coords.getX()+0.5),(int)(yOffset+coords.getY()+0.5),1,1);
            }
        }
    }
    
    public static void drawPixels(Graphics g, int[][] pixels){
        for(int x=0; x<pixels.length; x++){
            for(int y=0; y<pixels[x].length; y++){
                g.setColor(new Color(pixels[x][y]));
                if(g.getColor().equals(COLOR_TRANSPARENT))continue;
                g.fillRect(x,y,1,1);
            }
        }
    }
    
    public static boolean polygonContainsLine(Polygon shape,int startX,int startY,Vektor direction,int endX,int endY){
        double length=direction.getBetrag();
        direction.div(length);
        for(double x=startX,y=startY,l=0; !((int)(x)==endX && (int)(y)==endY) && l<=length; l++){
            if(shape.contains((int)x, (int)y))return true;
            if((int)(x)!=endX)
              x+=direction.getX();
            if((int)(y)!=endY)
              y+=direction.getY();
        }
        return false;
    }
}

abstract class Effect implements Drawable,ClockTimed{
    private double lifeTime;
    final int x,y;
    
    Effect(int xPos,int yPos,double millis){
        lifeTime=millis;
        x=xPos;
        y=yPos;
    }
    
    @Override
    public boolean clockSignal(Delay delay) {
        lifeTime-=delay.MS;
        return lifeTime>0;
    }
    
    double getLifeTime(){
        return lifeTime;
    }
}

class Trace extends Effect{
    private final Color BG_COLOR;
    
    Trace(int xPos,int yPos,Color bg){
        super(xPos,yPos,2000);
        BG_COLOR=bg;
    }

    @Override
    public void draw(Graphics graphic) {
        Color c=SpielfeldPanel.MUD_COLOR;
        if(BG_COLOR.getBlue()==BG_COLOR.getGreen() && BG_COLOR.getGreen()==BG_COLOR.getRed())c=Color.BLACK;
        int r=c.getRed(),g=c.getGreen(),b=c.getBlue();
        int relR=BG_COLOR.getRed()-r;
        int relG=BG_COLOR.getGreen()-g;
        int relB=BG_COLOR.getBlue()-b;
        c=new Color((int)(r+(relR*(2000-getLifeTime())/2000)),(int)(g+(relG*(2000-getLifeTime())/2000)),(int)(b+(relB*(2000-getLifeTime())/2000)));
        graphic.setColor(c);
        graphic.fillRect(x, y, 5, 5);
    }

    @Override
    public void draw(Graphics g, Image image) {
        this.draw(g);
    }
    
}

class Krater extends Effect{
    final int r;
    private Image img;

    public Krater(int xPos, int yPos, int radius, double millis) {
        super(xPos, yPos, millis);
        r=radius;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(SpielfeldPanel.MUD_COLOR);
        g.fillOval(x+r, y+r, r*2, r*2);
    }

    @Override
    public void draw(Graphics g, Image image) {
        if(img==null)
            img=image.getScaledInstance(r*2, r*2, 0);
        g.drawImage(img, x-r, y-r, null);
    }
    
}

class Explosion extends Effect{
    public final int MAX_SIZE;
    public final double EXPAND_SPEED;
    private double r;

    public Explosion(int xPos,int yPos,int size) {
        super(xPos,yPos,200);
        MAX_SIZE=size;
        EXPAND_SPEED=MAX_SIZE/1000.0;
        r=0;
        Sound.playSoundClip(Sound.EXPLOS_SOUND);
    }

    @Override
    public void draw(Graphics g) {
//        if(explosImage==null){
          g.setColor(Color.orange);
          g.fillOval(x-(int)r, y-(int)r, (int)r*2, (int)r*2);
//        }else{
//        //    SpielfeldPanel.drawPixels(g, x+(int)r, y+(int)r, (int)r*2, (int)r*2, 0, SpielfeldPanel.scale((int)r*2,(int)r*2,explosImage));
//        }
    }
    
    @Override
    public boolean clockSignal(Delay delay){
        if(!super.clockSignal(delay))return false;
        else{
            r+=EXPAND_SPEED*delay.MS;
            return true;
        }
    }

    @Override
    public void draw(Graphics g, Image image) {
        g.drawImage(image.getScaledInstance((int)r*2, (int)r*2, 0), x-(int)r, y-(int)r, null);
    }
}

class TimedText implements ClockTimed{
    String text;
    private int x,y;
    Tank t;
    double lifeTime;
    
    TimedText(String txt, int xPos, int yPos, double millis){
        text=txt;
        lifeTime=millis;
        x=xPos;
        y=yPos;
        t=null;
    }
    
    TimedText(String txt, Tank tank, double millis){
        text=txt;
        t=tank;
        lifeTime=millis;
    }
    
    int getX(){
        if(t!=null)return (t.getX()-t.width);
        else return x;
    }
    
    int getY(){
        if(t!=null)return (t.getY()-t.height);
        else return y;
    }
    
    @Override
    public boolean clockSignal(Delay delay){
        lifeTime-=delay.MS;
        return lifeTime>0;
    }
}