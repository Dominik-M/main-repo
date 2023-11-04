/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import util.Vector2D;

/**
 *
 * @author Dominik Messerschmidt
 */
public class Pickup
{

    private static int idcounter = 0;

    public enum Type
    {
        EXTRA_BALL,
        BALL_GROW,
        BALL_SPEED_UP,
        BALL_SPEED_DOWN,
        PADDEL_GROW,
        PADDEL_SHRINK,
        PADDEL_SPEED_UP,
        PADDEL_SPEED_DOWN,
        EXTRA_LIFE,
        EXTRA_SCORE
    };
    public final int ID;
    public final Type type;
    private Vector2D position;
    private int lifetime;

    public Pickup(Type t)
    {
        type = t;
        position = Vector2D.NULLVECTOR;
        ID = idcounter++;
    }

    public Vector2D getPosition()
    {
        return position;
    }

    public void setPosition(Vector2D position)
    {
        this.position = position;
    }

    public int getLifetime()
    {
        return lifetime;
    }

    public void setLifetime(int lifetime)
    {
        this.lifetime = lifetime;
    }

    @Override
    public String toString()
    {
        return type.name() + "#" + ID;
    }

}
