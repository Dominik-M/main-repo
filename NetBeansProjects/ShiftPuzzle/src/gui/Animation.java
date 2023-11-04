/*
 * Copyright (C) 2019 Dominik Messerschmidt
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
package gui;

import java.util.LinkedList;
import main.IntTuple;

/**
 *
 * @author Dominik Messerschmidt
 */
public class Animation
{

    private final int startX, startY, endX, endY, duration;
    private int t;
    private final LinkedList<AnimationListener> listener;

    public Animation(int startX, int startY, int endX, int endY, int duration)
    {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.duration = duration;
        t = 0;
        listener = new LinkedList<>();
    }

    public void addAnimationListener(AnimationListener l)
    {
        listener.add(l);
    }

    public boolean removeAnimationListener(AnimationListener l)
    {
        return listener.remove(l);
    }

    public boolean isRunning()
    {
        return t < duration;
    }

    public void doStep(int deltaT)
    {
        if (t >= duration)
        {
            for (AnimationListener l : listener)
            {
                l.animationComplete();
            }
        }
        else
        {
            t += deltaT;
        }
    }

    public IntTuple getValues()
    {
        int x, y;
        if (t >= duration)
        {
            x = endX;
            y = endY;
        }
        else
        {
            int progress = 100 * t / duration;
            x = startX + ((endX - startX) * progress / 100);
            y = startY + ((endY - startY) * progress / 100);
        }
        return new IntTuple(x, y);
    }
}
