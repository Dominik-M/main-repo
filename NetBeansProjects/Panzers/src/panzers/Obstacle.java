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
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Obstacle implements Drawable{
    private final int x,y,width,height;
    private final boolean DESTRUCTABLE;
    private int hp;
    public final int maxHP;
    private Image img;
    
    public Obstacle(Rectangle shape,int hitpoints){
        x=shape.x;
        y=shape.y;
        width=shape.width;
        height=shape.height;
        DESTRUCTABLE=hitpoints>0;
        hp=hitpoints;
        maxHP=hp;
    }

    @Override
    public void draw(Graphics g) {
        if(img==null){
          g.setColor(Color.BLACK);
          g.fillRect(x, y, width, height);
        }else{
            g.drawImage(img, x, y, null);
        }
        if(DESTRUCTABLE){
            SpielfeldPanel.drawProgressBar(g, x, y+height+10, 100, 10, hp, maxHP, Color.red, Color.green);
        }
    }
    
    public boolean hit(Projectile p){
        if(DESTRUCTABLE){
            hp-=p.source.getDamage();
            return hp<0;
        }else
            return false;
    }
    
    public Polygon getShapeOnScreen(){
        return new Polygon(new int[]{x,x,x+width,x+width},new int[]{y,y+height,y+height,y},4);
    }
    
    public void setImage(Image image){
        img=image.getScaledInstance(width, height, 0);
    }

    @Override
    public void draw(Graphics g, Image image) {
        if(img==null)setImage(image);
        this.draw(g);
    }
}
