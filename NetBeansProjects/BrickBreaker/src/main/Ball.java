/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Rectangle;
import util.Vector2D;

/**
 *
 * @author Dominik Messerschmidt
 */
public class Ball
{

    private Vector2D position, speed;
    private int radius;

    public Ball()
    {
        position = new Vector2D();
        speed = new Vector2D();
    }

    public Vector2D getPosition()
    {
        return position;
    }

    public void setPosition(Vector2D position)
    {
        this.position = position;
    }

    public Vector2D getSpeed()
    {
        return speed;
    }

    public void setSpeed(Vector2D speed)
    {
        this.speed = speed;
    }

    public int getRadius()
    {
        return radius;
    }

    public void setRadius(int size)
    {
        this.radius = size;
    }

    public Rectangle getBounds()
    {
        return new Rectangle((int) position.x - radius, (int) position.y - radius, radius * 2, radius * 2);
    }

}
