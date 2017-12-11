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
import java.awt.event.ActionEvent;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class SeekBar extends javax.swing.JComponent implements LineListener{
    private int min, max, value;
    private Color barColor;
    private final javax.swing.Timer clock = new javax.swing.Timer(100, new java.awt.event.ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            setValue((int)(sound.Sound.getMicrosecondPosition() * max / sound.Sound.getMicrosecondLength()));
        }
    });
    
    public SeekBar()
    {
        min = 0;
        max = 100;
        value = 0;
        barColor = Color.cyan;
        setPreferredSize(new java.awt.Dimension(200,30));
        sound.Sound.addLineListener(this);
    }
    
    public void setColor(Color c)
    {
        barColor = c;
        repaint();
    }
    
    public void setValue(int val)
    {
        if(val <= max && val >= min)
            value = val;
        repaint();
    }
    
    public int getValue()
    {
        return value;
    }
    
    public boolean setMinimum(int minValue)
    {
        if(min >= 0 && min <= max)
        {
            min = minValue;
            if(value < min)
                setValue(min);
            return true;
        }
        else return false;
    }
    
    public int getMinimum()
    {
        return min;
    }
    
    public boolean setMaximum(int maxValue)
    {
        if(max >= 0 && max >= min)
        {
            max = maxValue;
            if(value > max)
                setValue(max);
            return true;
        }
        else return false;
    }
    
    public int getMaximum()
    {
        return max;
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        int barHeight = this.getHeight()/2;
        int barWidth = this.getWidth() * value / (max-min);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(barColor);
        g.fillRect(0, 0, barWidth, barHeight);
        g.setColor(barColor.darker());
        g.fillOval(barWidth-barHeight/2, 0, barHeight, barHeight);
        g.setColor(Color.black);
        g.drawRect(0, 1, getWidth()-1, barHeight);
        //g.drawString(value+" / "+max, 0, this.getHeight());
        g.drawString(value/60+":"+value%60+" / "+max/60+":"+max%60, 0, this.getHeight());
        g.drawOval(barWidth-barHeight/2, 0, barHeight, barHeight);
    }

    @Override
    public void update(LineEvent event) {
        if(event.getType() == LineEvent.Type.STOP)
        {
            //clock.stop();
        }
        else if(event.getType() == LineEvent.Type.START)
        {
            clock.start();
        }
    }
}