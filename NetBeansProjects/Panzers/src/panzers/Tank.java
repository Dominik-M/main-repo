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
import java.awt.Polygon;
import java.util.Arrays;
import java.util.LinkedList;
import static graphic.SpielfeldPanel.drawProgressBar;
import java.awt.Image;
import utils.Vektor;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Tank implements Drawable{
    public static final double MAX_BRAKE_ACCELERATION=300,MAX_ACCELERATION=400, MAX_SPEED=400;
    private double hp,maxHP; // Hitpoints, maximal hitpoints
    private double a; // Acceleration speed in pixels per squaresecond
    private double rotateSpeed; // Rotation speed
    protected double speed,maxSpeed; // Current speed and speedmaximum in pixels per second
    private LinkedList<Gun> weapons; // Mounted weapons
    private int exp,nextLvl,lvl,bonus;
    double x,y; // Current positions coordinates
    double aimX,aimY;
    double direction; // Current heading direction (radiant)
    public final int width,height; // Tanksize on screen
    private int[][] tankImage; // ppm array of the tank image
    private int[][] gunImage; // ppm array of the gun mounted on the tank
    private Color color;
    private Projectile hit;
    public final boolean playersTeam;
    public final int INITIAL_HP;
    
    public Tank(){
      this(1,1,10,1,0,0,0,10,15,Color.red,true);
    }
    
    public Tank(int hitpoints, double acc, double vMax, double rot, double posX, double posY, double dir, int tankwidth, int tankheight,Color c,boolean friendly,Gun... mountedWeapons){
        hp=hitpoints;
        maxHP=hp;
        INITIAL_HP=(int) hp;
        a=acc;
        rotateSpeed=rot;
        x=posX;
        y=posY;
        direction=dir;
        width=tankwidth;
        height=tankheight;
        weapons=new LinkedList();
        weapons.addAll(Arrays.asList(mountedWeapons));
        for(Gun g:weapons)g.setOwner(this);
        speed=0;
        maxSpeed=vMax;
        color=c;
        lvl=1;
        exp=0;
        nextLvl=50;
        bonus=0;
        playersTeam=friendly;
        hit=null;
    }
    
    public boolean reduceHP(double damage){
        hp-=damage;
        if(hit!=null && hit.lifetime<=0)hit=null;
        return hp<=0;
    }
    
    public boolean hit(Projectile p){
        if(hit==null && p.source.toString().equals("Flamethrower")){
            hit=p;
            hit.lifetime=5000;
        }
        return reduceHP(p.source.getDamage());
    }
    
    public boolean hit(Projectile p, double dist){
        if(dist>0){
            double damage=p.source.getDamage()*p.source.areaEffect/(dist+p.source.areaEffect);
            System.out.println(damage);
          return reduceHP(damage);
        }
        else 
          return hit(p);
    }
    
    public Projectile getHit(){
        return hit;
    }
    
    public Projectile[] fire(int gun){
        LinkedList<Projectile> shotsList=weapons.get(gun).fire(x, y, aimX, aimY);
        Projectile[] shots=new Projectile[shotsList.size()];
        for(int i=0; i<shots.length; i++)shots[i]=shotsList.get(i);
        return shots;
    }
    
    public Vektor getSpeedVektor(){
        return new Vektor(speed*Math.cos(direction),speed*Math.sin(direction));
    }
    
    public Gun getPrimWeapon(){
        if(weapons.size()>=1)
          return weapons.get(0);
        else return null;
    }
    
    public Gun getSecWeapon(){
        if(weapons.size()>=2)
          return weapons.get(1);
        else return null;
    }
    
    public Color getColor(){
        return color;
    }
    
    public int getHP(){
        return (int)hp;
    }
    
    public int getMaxHP(){
        return (int)maxHP;
    }
    
    public double getSpeed(){
        return speed;
    }
    
    public double getMaxSpeed(){
        return maxSpeed;
    }
    
    public double getAcceleration(){
        return a;
    }
    
    public double getDirection(){
        return direction+Math.PI;
    }
    
    public void stop(){
        speed=0;
    }
    
    private void levelUp(){
        exp-=nextLvl;
        lvl++;
        bonus+=6;
        nextLvl=50*lvl;
        hp=maxHP;
        this.increaseHP(1.0);
        for(Gun g:weapons)g.reload();
    }
    
    public boolean addExp(int n){
        exp+=n;
        boolean levelup=exp>=nextLvl;
        while(exp>=nextLvl)levelUp();
        return levelup;
    }
    
    public int getExp(){
        return exp;
    }
    
    public int getNextLevelExp(){
        return nextLvl;
    }
    
    public int getBonusPoints(){
        return bonus;
    }
    
    public boolean increaseHP(double faktor){
        if(spendPoints(1)){
          hp+=faktor*INITIAL_HP;
          maxHP+=faktor*INITIAL_HP;
          return true;
        }
        return false;
    }
    
    public boolean increaseAcceleration(double faktor){
        if(a+faktor*MAX_ACCELERATION/10<=MAX_ACCELERATION && spendPoints(1)){
          a+=faktor*MAX_ACCELERATION/10;
          return true;
        }
        return false;
    }
    
    public boolean increaseMaxSpeed(double faktor){
        if(maxSpeed+faktor*MAX_SPEED/10<=MAX_SPEED && spendPoints(1)){
          maxSpeed+=faktor*MAX_SPEED/10;
          return true;
        }
        return false;
    }
    
    public void setAim(double x, double y){
        aimX=x;
        aimY=y;
    }
    
    public void switchGuns(int index1,int index2){
        Gun g;
        if(index1>=0 && index2>=0 && index1<weapons.size() && index2<weapons.size()){
            g=weapons.set(index1, weapons.get(index2));
            if(g!=null){
                weapons.set(index2, g);
            }else{
                weapons.set(index1, g);
            }
        }
    }
    
    public boolean spendPoints(int n){
        if(bonus>=n){
            bonus-=n;
            return true;
        }return false;
    }
    
    public final void rotate(boolean clockwise, Delay delay){
        if(clockwise){
            direction+=rotateSpeed*delay.MS/1000;
        }else{
            direction-=rotateSpeed*delay.MS/1000;
        }
        while(direction<0)direction+=Math.PI*2;
        while(direction>Math.PI*2)direction-=Math.PI*2;
    }
    
    public final void move(Delay delay){
        Vektor v=getSpeedVektor();
        x-=v.getX()*delay.MS/1000.0;
        y-=v.getY()*delay.MS/1000.0;
    }
    
    public void accelerate(boolean forward,Delay delay){
        if(forward){
            if(speed<0 && MAX_BRAKE_ACCELERATION>a){
                speed+=MAX_BRAKE_ACCELERATION*delay.MS/1000;
                if(speed>0)speed=0;
            }else
              speed+=a*delay.MS/1000;
            if(speed>maxSpeed)speed=maxSpeed;
        }else {
            if(speed>0 && MAX_BRAKE_ACCELERATION>a){
                speed-=MAX_BRAKE_ACCELERATION*delay.MS/1000;
                if(speed<0)speed=0;
            }else{
                speed-=a*delay.MS/1000;
            }
            if(speed*(-1)>maxSpeed/2)speed=(-1)*maxSpeed/2;
        }
    }
    
    public final boolean consumeItem(ItemEffect i){
        switch(i){
            case HEALTH:
              if(hp==maxHP)break;
              hp=maxHP;
              return true;
            case AMMO:
              Gun g=getPrimWeapon();
              for(Gun w:weapons)if(1.0*w.getAmmo()/w.getMaxAmmo()<1.0*g.getAmmo()/g.getMaxAmmo())g=w;
              g.reload();
              return true;
//            case LASER:
//              Gun laser=Gun.getLaser();
//              for(Gun gun:weapons)
//                  if(laser.toString().equals(gun.toString()))
//                      return false;
//              addGun(laser);
//              return true;
            case EXP:
                addExp((int)(Math.random()*nextLvl+50));
                return true;
        }
        return false;
    }
    
    public int getValue(){
        int value=(int)maxHP;
        for(Gun g:weapons)
            value+=(int)(g.getDamage()*g.getFirerate()+g.getRange())/100;
        value+=(int)(a+maxSpeed)/20;
        return value/10+lvl*10;
    }
    
    public int getWeaponsSize(){
        return weapons.size();
    }
    
    public int getX(){
        return (int)x;
    }
    
    public int getY(){
        return (int)y;
    }
    
    public double getRadius(){
        return Math.sqrt(width*width+height*height)/2;
    }
    
    public void setX(double xPos){
        x=xPos;
    }
    
    public void setY(double yPos){
        y=yPos;
    }
    
    public Polygon getTankShapeOnScreen(){
        Vektor[] vectors=new Vektor[]{
            new Vektor(width/2,-1*height/2),
            new Vektor(-1*width/2,-1*height/2),
            new Vektor(-1*width/2,height/2),
            new Vektor(width/2,height/2)
        };
        // Transform Rectangle coordinates
        int[] xPoints=new int[4];
        int[] yPoints=new int[4];
        double phi=direction-Math.PI/2;
        for(int i=0; i<vectors.length; i++){
            Vektor v=vectors[i];
            v.rotate(phi);
            xPoints[i]=(int)(v.getX()+x);
            yPoints[i]=(int)(v.getY()+y);
        }
        return new Polygon(xPoints,yPoints,xPoints.length);
    }
    
    public void setTankImage(int[][] ppm){
        tankImage=ppm;
    }
    
    public void setGunImage(int[][] ppm){
        gunImage=ppm;
    }
    
    public int[][] getTankImage(){
        return tankImage;
    }
    
    public int[][] getGunImage(){
        return gunImage;
    }
    
    @Override
    public void draw(Graphics g){
        g.setColor(color);
        if(tankImage!=null){
          SpielfeldPanel.drawPixels(g, (int)x, (int)y,width/2,height/2, direction-Math.PI/2, tankImage);
        }else{
          Polygon shape=this.getTankShapeOnScreen();
          g.fillPolygon(shape);
        }
        double r=getRadius();
        if(gunImage!=null){
            Vektor aim=new Vektor(aimX-x,aimY-y);
            SpielfeldPanel.drawPixels(g, (int)x, (int)y,width/2,height/2, Math.atan2(aim.getY(), aim.getX())+Math.PI/2, gunImage);
        }else{
//          g.setColor(Color.green.darker());
//          drawLine(g, (int)x, (int)y, new Vektor(aimX-x,aimY-y), (int) r, 2);
        }
        if(hit!=null){
            hit.draw(g);
        }
        drawProgressBar(g,(int)(x-r),(int)(y+r),(int)r*2,(int)r/4,hp,maxHP,Color.RED,Color.GREEN);
        drawProgressBar(g,(int)(x-r),(int)(y+r+r/3),(int)r*2,(int)r/4,exp,nextLvl,Color.WHITE,Color.CYAN);
        g.setFont(new java.awt.Font("Impact", 0, 12));
        g.setColor(Color.BLACK);
        Gun gun;
        gun=getPrimWeapon();
        if(gun!=null)
          g.drawString(gun+": "+gun.getAmmo()+" / "+gun.getMaxAmmo(), (int) (x+width), (int)y);
        gun=getSecWeapon();
        if(gun!=null)
          g.drawString(gun+": "+gun.getAmmo()+" / "+gun.getMaxAmmo(), (int) (x+width), (int)y+12);
    }

    public void refreshGuns(Delay delay) {
        for(Gun g:weapons)g.refresh(delay);
    }

    public Gun[] getGuns() {
        Gun[] guns=new Gun[getWeaponsSize()];
        for(int i=0; i<guns.length; i++)guns[i]=weapons.get(i);
        return guns;
    }

    public int getLevel() {
        return lvl;
    }

    public void addGuns(Gun... selected) {
        for(Gun g:selected){
          g.setOwner(this);
          weapons.add(g);
        }
    }
    
    public void randomUpgrades(){
        while(bonus>0){
          int random=(int)(Math.random()*14);
            switch(random){
                case 0:
                //    increaseAcceleration(0.5);
                    break;
                case 1:
                //    increaseMaxSpeed(0.5);
                    break;
                case 2:
                    if(getPrimWeapon()!=null)if(getPrimWeapon().increaseAmmo(0.1))
                        spendPoints(1);
                    break;
                case 3:
                    if(getPrimWeapon()!=null)if(getPrimWeapon().increaseAccuracy(1.1))
                        spendPoints(1);
                    break;
                case 4:
                    if(getPrimWeapon()!=null)if(getPrimWeapon().increaseFirerate(0.1))
                        spendPoints(1);
                    break;
                case 5:
                    if(getPrimWeapon()!=null)if(getPrimWeapon().increaseRange(0.1))
                        spendPoints(1);
                    break;
                case 6:
                    if(getPrimWeapon()!=null)if(getPrimWeapon().increaseDamage(0.1))
                        spendPoints(1);
                    break;
                case 7:
                    if(getSecWeapon()!=null)if(getSecWeapon().increaseAmmo(0.1))
                        spendPoints(1);
                    break;
                case 8:
                    if(getSecWeapon()!=null)if(getSecWeapon().increaseAccuracy(1.1))
                        spendPoints(1);
                    break;
                case 9:
                    if(getSecWeapon()!=null)if(getSecWeapon().increaseFirerate(0.1))
                        spendPoints(1);
                    break;
                case 10:
                    if(getSecWeapon()!=null)if(getSecWeapon().increaseRange(0.1))
                        spendPoints(1);
                    break;
                case 11:
                    if(getSecWeapon()!=null)if(getSecWeapon().increaseDamage(0.1))
                        spendPoints(1);
                    break;
                default:
                    increaseHP(0.2);
                    break;
            }
        }
    }
    
    public static Tank getStandartTank(int x,int y,boolean playersTeam){
        return new Tank(200,100,200,Math.PI,
                x,y,Math.PI/2,30,50,Color.blue,playersTeam,
                Gun.getSmallGun());
    }
    
    public static Tank getMachineGunner(int x, int y, boolean playersTeam){
        return new Tank(100,300,400,Math.PI*2,
                x,y,Math.PI/2,30,50,Color.blue,playersTeam,
                Gun.getMachineGun(),Gun.getMachineGun());
    }
    
    public static Tank getSuperTank(int x,int y,boolean playersTeam){
        Tank t=new Tank(5000,200,400,Math.PI,
                x,y,Math.PI/2,35,60,Color.blue,playersTeam,
                Gun.getAllGuns());
        for(int i=1; i<100; i++)t.addExp(i*50);
        return t;
    }
    
    public static Tank getDuellTank(int x,int y, boolean playersTeam){
        Tank t=new Tank(2000,200,400,Math.PI,
                x,y,Math.PI/2,35,60,Color.blue,playersTeam);
        t.lvl=20;
        t.nextLvl=1000;
        return t;
    }
    
    public static Tank getPlane(boolean playersTeam){
        return new Plane(playersTeam);
    }

    public void weaponUpgrade() {
        if(lvl<=2){
            addGuns(Gun.getSmallGun());
        }else if(lvl<=4){
            addGuns(Gun.getMachineGun());
        }else if(lvl<=6){
            addGuns(Gun.getGrenadeLauncher());
        }else if(lvl<=8){
            addGuns(Gun.getRicochetRifle());
        }else if(lvl<=10){
            addGuns(Gun.getFlamethrower());
        }else if(lvl<=12){
            addGuns(Gun.getLaser());
        }else if(lvl<=14){
            addGuns(Gun.getGatlinGun());
        }
    }

    @Override
    public void draw(Graphics g, Image image) {
        
    }
}

class Plane extends Tank{
    
    Plane(boolean friendly){
        super(250000,400,500,Math.PI/4,0,0,Math.PI*3/2,30,60,Color.GRAY,friendly,Gun.getBomber(),Gun.getBomber());
    }

    @Override
    public void stop() {
        
    }
    
    @Override
    public void accelerate(boolean forward, Delay delay) {
        super.accelerate(forward, delay);
        if(speed<0)speed=0;
    }
}