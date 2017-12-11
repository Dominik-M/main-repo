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
package panzers;

import graphic.Delay;
import graphic.SpielfeldPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import utils.Vektor;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Gun {
    private double dmg; // Attack damage
    public final double INITIAL_DAMAGE,MAX_DAMAGE;
    public final int areaEffect;
    public final double projectileSpeed; // Speed of fired projectiles
    public final int projectileLength, projectileWidth;
    public final boolean drawProjectilesAsLine,reflectionOn;
    private int range; // Range of fired projectiles in pixels
    public final int INITIAL_RANGE,MAX_RANGE;
    private double reloadTime; // Remaining time to reload in ms
    private double firerate; // Rate of fire in shots per second
    public final double INITIAL_FIRERATE,MAX_FIRERATE;
    private double accuracy; // accuracy in percent
    public final double INITIAL_ACCURACY,MAX_ACCURACY;
    private int ammo,maxAmmo;
    public final int INITIAL_AMMO,MAX_AMMO;
    private final String name;
    private Tank owner;
    
    public Gun(String n,int damage, double speed, int range,double shotsPerSecond,
            double hitchance,int ammoMax, int pLength, int pSize,
            boolean drawLines,boolean reflectProjectiles,int explosRange){
        name=n;
        INITIAL_DAMAGE=damage;
        MAX_DAMAGE=damage*10;
        dmg=damage;
        projectileSpeed=speed;
        projectileLength=pLength;
        projectileWidth=pSize;
        INITIAL_RANGE=range;
        MAX_RANGE=range*2;
        this.range=range;
        INITIAL_FIRERATE=shotsPerSecond;
        MAX_FIRERATE=shotsPerSecond*3;
        firerate=shotsPerSecond;
        INITIAL_ACCURACY=hitchance;
        if(INITIAL_ACCURACY+50<100)
          MAX_ACCURACY=INITIAL_ACCURACY+50;
        else MAX_ACCURACY=100;
        accuracy=hitchance;
        INITIAL_AMMO=ammoMax;
        MAX_AMMO=ammoMax*5;
        maxAmmo=ammoMax;
        ammo=maxAmmo;
        owner=null;
        drawProjectilesAsLine=drawLines;
        reflectionOn=reflectProjectiles;
        areaEffect=explosRange;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public LinkedList<Projectile> fire(double x1, double y1, double x2, double y2){
        LinkedList<Projectile> shots=new LinkedList();
        while(reloadTime<=0 && ammo>0){
          Vektor v=new Vektor(x2-x1,y2-y1);
          v.div(v.getBetrag());
          v.mul(projectileSpeed);
          double phi=(Math.random()*Math.PI/2-Math.PI/4)*(100.0-accuracy)/100.0;
          v.rotate(phi);
          ammo--;
          Color c;
          if(owner.playersTeam)c=Color.cyan;
          else c=Color.red.darker();
          reloadTime+=(1000/firerate);
          shots.add(new Projectile(this,x1,y1,v,projectileLength,projectileWidth,range,c));
        }
        return shots;
    }
    
    public void refresh(Delay delay){
        if(reloadTime>0){
            reloadTime-=delay.MS;
        }
    }
    
    public void reload(){
        ammo=maxAmmo;
    }
    
    public int getAmmo(){
        return ammo;
    }
    
    public int getMaxAmmo(){
        return maxAmmo;
    }
    
    public boolean increaseAmmo(double faktor){
        if(maxAmmo>=MAX_AMMO)return false;
        maxAmmo+=faktor*INITIAL_AMMO;
        if(maxAmmo>=MAX_AMMO){
            maxAmmo=MAX_AMMO;
        }
        return true;
    }
    
    public double getDamage(){
        return dmg;
    }
    
    public boolean increaseDamage(double faktor){
        if(dmg>=MAX_DAMAGE)return false;
        dmg+=INITIAL_DAMAGE*faktor;
        if(dmg>MAX_DAMAGE){
            dmg=MAX_DAMAGE;
        }
        return true;
    }
    
    public double getFirerate(){
        return firerate;
    }
    
    public boolean increaseFirerate(double faktor){
        if(firerate>=MAX_FIRERATE)return false;
        firerate+=INITIAL_FIRERATE*faktor;
        if(firerate>MAX_FIRERATE){
            firerate=MAX_FIRERATE;
        }
        return true;
    }
    
    public int getRange(){
        return range;
    }
    
    public boolean increaseRange(double faktor){
        if(range>=MAX_RANGE)return false;
        range+=INITIAL_RANGE*faktor;
        if((int)range>MAX_RANGE){
            range=MAX_RANGE;
        }
        return true;
    }
    
    public double getAccuracy(){
        return accuracy;
    }
    
    private boolean setAccuracy(double hitchance){
        if((int)accuracy>=MAX_ACCURACY)return false;
        accuracy=hitchance;
        if((int)(accuracy+0.5)>=MAX_ACCURACY){
            accuracy=MAX_ACCURACY;
        }
        return true;
    }
    
    public boolean increaseAccuracy(double faktor){
        return setAccuracy(100-(100-accuracy)/faktor);
    }
    
    public Tank getOwner(){
        return owner;
    }
    
    void setOwner(Tank tank){
        if(owner==null)owner=tank;
    }
    
    public static final Gun getRandomGun(){
        int dmg=(int)(Math.random()*6+1);
        int speed=(int)(Math.random()*2000+10);
        int range=(int)(Math.random()*2000+50);
        int rate=(int)(Math.random()*10+1);
        double accuracy=Math.random()*50+50;
        int l=(int)(Math.random()*30+2);
        int muni=(int)(Math.random()*1000+100);
        return new Gun("Gun",dmg,speed,range,rate,accuracy,muni,l,dmg,false,false,1);
    }
    
    public static final Gun getSmallGun(){
        return new Gun("Small Gun",5,1000,1500,4,80,200,20,5,false,false,0);
    }
    
    public static final Gun getMachineGun(){
        return new Gun("Machine Gun",2,2000,2000,10,80,200,20,2,true,false,0);
    }
    
    public static final Gun getGrenadeLauncher(){
        return new Gun("Grenade Launcher",50,600,500,1,60,20,20,10,false,true,150);
    }
    
    public static final Gun getRicochetRifle(){
        return new Gun("Ricochet Rifle",15,800,4000,4,75,100,10,4,false,true,0);
    }
    
    public static final Gun getFlamethrower(){
        return new Gun("Flamethrower",2,500,200,40,50,600,15,10,false,true,0);
    }
    
    public static final Gun getLaser(){
        return new Gun("Instant Laser Gun",1,4000,3000,100,100,500,100,1,true,false,0);
    }
    
    public static final Gun getGatlinGun(){
        return new Gun("Gatlin Gun",5,2000,2000,100,30,1000,15,2,true,false,0);
    }
    
    public static final Gun getRocketLauncher(){
        return new Gun("Rocket Launcher",250,1500,800,1,80,10,20,10,false,false,250);
    }
    
    public static final Gun getBomber(){
        return new Airstrike();
    }
    
    public static final Gun[] getAllGuns(){
        return new Gun[]{
            Gun.getSmallGun(),
            Gun.getMachineGun(),
            Gun.getGrenadeLauncher(),
            Gun.getRicochetRifle(),
            Gun.getFlamethrower(),
            Gun.getLaser(),
            Gun.getGatlinGun(),
            getRocketLauncher(),
            getBomber()
        };
    }
}

class Airstrike extends Gun{
    
    Airstrike(){
        super("Airstrike",400,100,200,4,60,10,40,20,false,false,400);
    }
    
    @Override
    public LinkedList<Projectile> fire(double x1,double y1,double x2,double y2){
        LinkedList<Projectile> shots=super.fire(x1, y1, x2, y2);
        for(int i=0; i<shots.size(); i++)
            shots.set(i, new Bomb(shots.get(i)));
        return shots;
    }
    
    class Bomb extends Projectile{
        final int INITIAL_LIFETIME;
        private int[][] bulletImage;

        public Bomb(Gun weapon, double x0, double y0, Vektor speed, int len, int wid, int range, Color c) {
            super(weapon, x0, y0, speed, len, wid, range, c);
            INITIAL_LIFETIME=this.lifetime;
        }
        
        Bomb(Projectile p){
            this(p.source,p.getX(),p.getY(),p.v,p.length,p.width,p.source.getRange(),Color.CYAN);
        }
        
        @Override
        public void setImage(int[][] ppm){
            bulletImage=ppm;
        }
        
        @Override
        public void draw(Graphics g){
            if(bulletImage!=null){
              double faktor=(1000.0+lifetime)/INITIAL_LIFETIME;
              image=SpielfeldPanel.scale((int)(width*2*faktor), (int) (length*faktor), bulletImage);
            }
            super.draw(g);
        }
    }
}