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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Item implements Drawable{
    public final ItemEffect effect;
    public final int x,y,width,height;
    private Image image;
    
    public Item(int xPos, int yPos, int w, int h,ItemEffect e){
        x=xPos;
        y=yPos;
        width=w;
        height=h;
        effect=e;
        image=null;
    }
    
    public void setImage(Image img){
        image=img.getScaledInstance(width, height, 0);
    }
    
    @Override
    public void draw(Graphics g){
        if (image == null) {
            switch (effect) {
                case HEALTH:
                    g.setColor(Color.white);
                    g.fillRect(x - width / 2, y - height / 2, width, height);
                    break;
                case EXP:
                    g.setColor(Color.yellow);
                    g.fillRect(x - width / 2, y - height / 2, width, height);
                    break;
                case AMMO:
                    g.setColor(Color.green);
                    g.fillRect(x - width / 2, y - height / 2, width, height);
                    break;
            }
        }else{
            g.drawImage(image, x-width/2, y-height/2, null);
        }
    }

    @Override
    public void draw(Graphics g, Image image) {
        if(this.image==null)this.image=image.getScaledInstance(width, height, 0);
        this.draw(g);
    }
}