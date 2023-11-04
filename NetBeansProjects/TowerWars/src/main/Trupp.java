/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.LinkedList;
import static main.Game.TOWER_HEIGHT;
import static main.Game.TOWER_WIDTH;

/**
 *
 * @author Dominik Messerschmidt
 */
public class Trupp
{

    public final Tower src, target;
    public final Team team;
    private final Vector2D pos, endPos, speed;
    private boolean running;
    private final LinkedList<TruppListener> listeners = new LinkedList<>();

    public Trupp(Tower source, Tower target, double speed)
    {
        this.src = source;
        this.team = source.getColor();
        this.target = target;
        pos = new Vector2D(source.getX() + TOWER_WIDTH / 2, source.getY() + TOWER_HEIGHT / 2);
        endPos = new Vector2D(target.getX() + TOWER_WIDTH / 2, target.getY() + TOWER_HEIGHT / 2);
        this.speed = endPos.sub(pos).getNormalized().mult(speed);
        running = true;
    }

    @Override
    public String toString()
    {
        return "Trupp at " + (int) pos.x + "|" + (int) pos.y + " Team: " + team;
    }

    public void addListener(TruppListener l)
    {
        listeners.add(l);
    }

    public boolean removeListener(TruppListener l)
    {
        return listeners.remove(l);
    }

    public boolean isRunning()
    {
        return running;
    }

    public void doStep()
    {
        if (running)
        {
            if (pos.distanceTo(endPos) <= speed.magnitude())
            {
                // reached the end
                pos.x = endPos.x;
                pos.y = endPos.y;
                running = false;
                for (TruppListener l : listeners)
                {
                    l.arrived(this);
                }
            }
            else
            {
                // go a step
                pos.x += speed.x;
                pos.y += speed.y;
            }
        }
    }

    public double getX()
    {
        return pos.x;
    }

    public void setX(double x)
    {
        this.pos.x = x;
    }

    public double getY()
    {
        return pos.y;
    }

    public void setY(double y)
    {
        this.pos.y = y;
    }

}
