/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import philosophen.Main;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Panel extends JPanel{
    
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.BLACK);
        g.drawOval(getWidth()/4, getHeight()/4, getWidth()/2, getHeight()/2);
        g.setFont(new java.awt.Font("Impact", 0, 20));
        g.drawString("P0 "+Main.stateOf(0), getWidth()/2, getHeight()/4);
        g.drawString("P1 "+Main.stateOf(1), getWidth()/4*3, getHeight()/3);
        g.drawString("P2 "+Main.stateOf(2), getWidth()/4*3, getHeight()/4*3);
        g.drawString("P3 "+Main.stateOf(3), getWidth()/2/3, getHeight()/4*3);
        g.drawString("P4 "+Main.stateOf(4), getWidth()/4, getHeight()/3);
        int i=0;
        if(Main.isFree(i))
            g.setColor(Color.BLACK);
        else g.setColor(Color.WHITE);
        g.drawLine(getWidth()/10*4, getHeight()/10*3, getWidth()/10*5, getHeight()/10*4);
        i++;
        if(Main.isFree(i))
            g.setColor(Color.BLACK);
        else g.setColor(Color.WHITE);
        g.drawLine(getWidth()/10*7, getHeight()/10*4, getWidth()/10*6, getHeight()/10*4);
        i++;
        if(Main.isFree(i))
            g.setColor(Color.BLACK);
        else g.setColor(Color.WHITE);
        g.drawLine(getWidth()/10*5, getHeight()/10*5, getWidth()/10*6, getHeight()/10*6);
        i++;
        if(Main.isFree(i))
            g.setColor(Color.BLACK);
        else g.setColor(Color.WHITE);
        g.drawLine(getWidth()/10*4, getHeight()/10*5, getWidth()/10*3, getHeight()/10*6);
        i++;
        if(Main.isFree(i))
            g.setColor(Color.BLACK);
        else g.setColor(Color.WHITE);
        g.drawLine(getWidth()/10*5, getHeight()/10*6, getWidth()/10*5, getHeight()/10*7);
    }
}
