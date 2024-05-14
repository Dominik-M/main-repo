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

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Box implements Drawable{
    private int hp;
    public final int maxHP;
    public final int x,y;
    public final int width,height;
    private Image img;
    public final ItemEffect item;
    
    public Box(int xPos, int yPos, int w, int h,int hitpoints,ItemEffect i){
        x=xPos;
        y=yPos;
        width=w;
        height=h;
        hp=hitpoints;
        maxHP=hp;
        item=i;
        img=null;
    }
    
    @Override
    public void draw(Graphics g){
        if(img==null){
          g.setColor(Color.orange.darker());
          g.fillRect(x-width/2, y-height/2, width, height);
        }else{
            g.drawImage(img, x-width/2, y-height/2, null);
        }
        SpielfeldPanel.drawProgressBar(g, x-width/2, y+height/2+height/4, width, height/5, hp, maxHP, Color.red, Color.GREEN);
    }

    public boolean hit(Projectile p) {
        hp-=p.source.getDamage();
        return hp<=0;
    }
    
    public boolean hit(Projectile p, double dist){
        if(dist>0){
          hp-=(p.source.getDamage()*p.source.areaEffect/(dist+p.source.areaEffect));
          return hp<=0;
        }
        else 
          return hit(p);
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
