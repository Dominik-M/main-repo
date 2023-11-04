/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Dominik Messerschmidt
 */
public class Tower
{

    private Team color;
    private int x, y, level, value, maxValue, tickCountProduce, ticksNeededProduce, tickCountSpawn, ticksNeededSpawn;
    private boolean canSpawn;
    private Tower target;

    public Tower(int x, int y, Team c, int v, int max, int prodspeed, int spawnspeed)
    {
        this.x = x;
        this.y = y;
        color = c;
        value = v;
        this.level = 1;
        this.maxValue = max;
        canSpawn = true;
        tickCountProduce = 0;
        ticksNeededProduce = prodspeed;
        tickCountSpawn = 0;
        ticksNeededSpawn = spawnspeed;
        System.out.println("Create new " + this.toString());
    }

    @Override
    public String toString()
    {
        return "Tower at " + x + "|" + y + ", " + color + ", " + value;
    }

    public Team getColor()
    {
        return color;
    }

    public void setColor(Team color)
    {
        this.color = color;
        this.tickCountSpawn = 0;
        this.tickCountProduce = 0;
        canSpawn = false;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Tower getTarget()
    {
        return target;
    }

    public void setTarget(Tower target)
    {
        if (this.equals(target))
        {
            this.target = null;
        }
        else
        {
            this.target = target;
        }
        // changing the target resets the spawn
        tickCountSpawn = 0;
    }

    public boolean spawn()
    {
        if (target != null && value > 0 && canSpawn)
        {
            value--;
            canSpawn = false;
            return true;
        }
        return false;
    }

    public void tick()
    {
        // ticksNeededProduce <= 0 means cannot produce at all
        if (value < maxValue && ticksNeededProduce > 0)
        {
            tickCountProduce++;
            if (tickCountProduce >= ticksNeededProduce)
            {
                tickCountProduce -= ticksNeededProduce;
                value++;
            }
        }
        if (!canSpawn && ticksNeededSpawn > 0)
        {
            tickCountSpawn++;
            if (tickCountSpawn >= ticksNeededSpawn)
            {
                tickCountSpawn -= ticksNeededSpawn;
                canSpawn = true;
            }
        }
    }
}
