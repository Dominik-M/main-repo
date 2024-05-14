/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 *
 * @author Dominik Messerschmidt
 */
public class Element
{

    public BufferedImage image;
    public String name;
    private final Dimension position, scaledSize;
    public boolean selected;

    public Element(BufferedImage img, String name)
    {
        image = img;
        this.name = name;
        position = new Dimension();
        scaledSize = new Dimension(img.getWidth(), img.getHeight());
        selected = false;
    }

    public int getX()
    {
        return position.width;
    }

    public int getY()
    {
        return position.height;
    }

    public int getScaledWidth()
    {
        return scaledSize.width;
    }

    public int getScaledHeight()
    {
        return scaledSize.height;
    }

    public void setX(int x)
    {
        position.width = x;
    }

    public void setY(int y)
    {
        position.height = y;
    }

    public void setScaledWidth(int w)
    {
        scaledSize.width = w;
    }

    public void setScaledHeight(int h)
    {
        scaledSize.height = h;
    }

    public int getRightBorder()
    {
        return scaledSize.width + position.width;
    }

    public int getBottomBorder()
    {
        return scaledSize.height + position.height;
    }

}
