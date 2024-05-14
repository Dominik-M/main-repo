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

import graphic.SpielfeldPanel;
import java.awt.Color;
import java.awt.Graphics;
import static graphic.SpielfeldPanel.drawLine;
import java.awt.Image;
import utils.Vektor;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Projectile implements Drawable{
    final Gun source;
    final Vektor v;
    private double x,y;
    int length,width,lifetime;
    private final Color color;
    protected int[][] image;
    
    Projectile(Gun weapon,double x0, double y0, Vektor speed, int len, int wid,int range,Color c){
        source=weapon;
        v=speed;
        x=x0;
        y=y0;
        length=len;
        width=wid;
        lifetime=(int) (1000*range/v.getBetrag());
        color=c;
    }
    
    public boolean move(double millis){
        x+=v.getX()*millis/1000;
        y+=v.getY()*millis/1000;
        lifetime-=millis;
        return lifetime>0;
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public void setPos(int xPos, int yPos){
        x=xPos;
        y=yPos;
    }
    
    public Gun getSource(){
        return source;
    }
    
    public Vektor getVektor(){
        return v;
    }
    
    public void setImage(int[][] ppm){
        image=SpielfeldPanel.scale(width*2, length, ppm);
    }
    
    @Override
    public void draw(Graphics g){
        if(image==null){
          g.setColor(color);
          if(source.drawProjectilesAsLine)
            drawLine(g,(int)x,(int)y,v,length,width);
          else{
            int r=width+2;
            g.fillOval((int)x-r, (int)y-r, r, r);
          }
        }else{
          SpielfeldPanel.drawPixels(g, (int)x, (int)y, width/2, length/2, Math.atan2(v.getY(), v.getX())+Math.PI/2, image);
        }
    }

    @Override
    public void draw(Graphics g, Image image) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
