/**
 * Copyright (C) 2016 Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ais;

import actors.Ship;
import actors.ShipFactory;
import java.awt.Rectangle;
import java.util.List;
import main.SpaceInvader;
import platform.gamegrid.AI;
import platform.gamegrid.Actor;
import platform.utils.Utilities;
import platform.utils.Vector2D;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 26.03.2016
 */
public class BaseAI extends AI
{

    public static final AI AI_DUMMYFIGHTER = new BaseAI();
    public static final AI AI_HUNTER = new HunterAI();

    @Override
    public void preAct(Actor a)
    {
        if ((a.getSpeed() == 0 || Math.random() < 0.001) && SpaceInvader.getInstance().getMap() != null)
        {
            Vector2D direction;
            double destX, destY;
            if (SpaceInvader.getInstance().getMap().LANDING_ZONE != null)
            {
                Rectangle lzone = SpaceInvader.getInstance().getMap().LANDING_ZONE;
                destX = lzone.x + lzone.width / 2;
                destY = lzone.y + lzone.height / 2;
            }
            else
            {
                destX = SpaceInvader.getInstance().getWidth() * Math.random();
                destY = SpaceInvader.getInstance().getHeight() * Math.random();
            }
            direction = new Vector2D(destX - a.getX(), destY - a.getY());
            direction.normalize();
            a.setSpeed(direction.mult(ShipFactory.DEFAULT_SPEED_MED / 2));
        }
        if (Ship.class.isInstance(a))
        {
            if (!shootEnemyInRange((Ship) a, 400))
            {
                a.setDirection(Utilities.toDeg(a.getSpeedVector().getDirection()));
            }
        }
    }

    @Override
    public String toString()
    {
        return "AI Dummy Fighter";
    }

    /**
     * Lets the ship shoot at an enemy ship in range.
     *
     * @param me the ship under control
     * @param range the maximum distance to a target
     * @return true if an enemy ship is in range and the given ship started
     * shooting, false otherwise.
     */
    public static boolean shootEnemyInRange(Ship me, int range)
    {
        boolean enemyInRange = false;
        List<Ship> enemys = SpaceInvader.getInstance().getEnemyships(me.getTeam());
        for (Ship enemy : enemys)
        {
            if (enemy.getPosition().distanceTo(me.getPosition()) < range)
            {
                me.setDirection(Utilities.toDeg(enemy.getPosition().sub(me.getPosition())
                        .getDirection()) + 90);
                enemyInRange = true;
                break;
            }
        }
        me.setShooting(enemyInRange);
        return enemyInRange;
    }

    /**
     * Lets the given ship head to the nearest enemy or ally.
     *
     * @param me the ship under control
     * @param enemy true to head to nearest enemy, false to head to nearest
     * ally.
     * @return false if no target is available and the ship does not move, true
     * otherwise.
     */
    public static boolean headToNearest(Ship me, boolean enemy)
    {
        Ship nearest = getNearest(me, enemy);
        if (nearest != null)
        {
            Vector2D direction = nearest.getPosition().sub(me.getPosition());
            me.setDirection(Utilities.toDeg(direction.getDirection()) + 90);
            me.setSpeed(direction.getNormalized().mult(me.getMaxSpeed()));
            return true;
        }
        return false;
    }

    /**
     * Retrieve the nearest enemy or ally.
     *
     * @param me the ship under control
     * @param enemy true to get nearest enemy, false to get nearest ally.
     * @return nearest enemy or ally Ship, or null if no Ships available
     */
    public static Ship getNearest(Ship me, boolean enemy)
    {
        List<Ship> targets;
        if (enemy)
        {
            targets = SpaceInvader.getInstance().getEnemyships(me.getTeam());
        }
        else
        {
            targets = SpaceInvader.getInstance().getAllyships(me.getTeam());
        }
        if (targets.size() > 0)
        {
            double minDist = targets.get(0).getPosition().distanceTo(me.getPosition());
            Ship nearest = targets.get(0);
            for (int i = 1; i < targets.size(); i++)
            {
                double dist = targets.get(i).getPosition().distanceTo(me.getPosition());
                if (dist < minDist)
                {
                    minDist = dist;
                    nearest = targets.get(i);
                }
            }
            return nearest;
        }
        return null;
    }

    /**
     * Lets the given ship head to the landing zone.
     *
     * @param me the ship under control
     * @return true if the ship started moving.
     */
    public static boolean headToLandingZone(Ship me)
    {
        int cX, cY;
        if (SpaceInvader.getInstance().getMap().LANDING_ZONE != null)
        {
            cX = (int) SpaceInvader.getInstance().getMap().LANDING_ZONE.getCenterX();
            cY = (int) SpaceInvader.getInstance().getMap().LANDING_ZONE.getCenterY();
        }
        else
        {
            me.setSpeed(Vector2D.NULLVECTOR);
            return false;
        }
        Vector2D direction = new Vector2D(cX, cY).sub(me.getPosition());
        me.setDirection(Utilities.toDeg(direction.getDirection()) + 90);
        me.setSpeed(direction.getNormalized().mult(me.getMaxSpeed()));
        return true;
    }

    /**
     * Lets the given ship orbit around the given position with given distance.
     *
     * @param me the ship under control
     * @param center position to orbit around
     * @param distance distance to center position
     */
    public static void orbit(Ship me, Vector2D center, int distance)
    {
        Vector2D distVector = me.getPosition().sub(center);
        Vector2D direction;
        if (distVector.magnitude() < distance - 50)
        {
            direction = distVector;
        }
        else if (distVector.magnitude() > distance + 50)
        {
            direction = distVector.invert();
        }
        else
        {
            direction = distVector.getOrthogonal();
        }
        // me.setDirection(Utilities.toDeg(direction.getDirection()) + 90);
        me.setSpeed(direction.getNormalized().mult(me.getMaxSpeed()));
    }

    /**
     * Get an acceleration vector to align the given Actor to a leader. A leader
     * shall be a ship and must not be controlled by a Wingman-AI. If there is
     * no leader ship in range the controlled Actor does not move. The behaviour
     * in a group is oriented on swarm behaviour of flock birds with the
     * difference that the flock sticks to a leader.
     *
     * @param me the ship under control
     * @param roo Radius of observation. Determines the maximum difference to
     * the leader. If the difference gets greater the flock will dissolve.
     * @param rcrit least difference to the leader and surrounding allys.
     * @return A Vector2D defining the acceleration currently needed to stick to
     * the flock.
     */
    public static Vector2D getFlockAlignAcceleration(Actor me, Ship leader, int roo, int rcrit)
    {
        Vector2D acceleration = new Vector2D(0, 0);
        if (leader != null)
        {
            List<Ship> allys = SpaceInvader.getInstance().getAllyships(me.getTeam());
            allys.remove(me);
            Vector2D sepVector = new Vector2D(0, 0);
            int sepcount = 0;
            Vector2D distVector;
            for (Ship ally : allys)
            {
                // separate from allys
                if (ally.getBounds().intersects(me.getBounds()))
                {
                    distVector = ally.getPosition().sub(me.getPosition());
                    if (ally.getXOnScreen() == me.getXOnScreen()
                            && ally.getYOnScreen() == me.getYOnScreen())
                    {
                        distVector.x = 1;
                        distVector.y = 1;
                    }
                    else
                    {
                        distVector.normalize();
                    }
                    sepVector.x -= distVector.x;
                    sepVector.y -= distVector.y;
                    // sepcount++;
                    sepcount = 1;
                }
            }
            distVector = leader.getPosition().sub(me.getPosition());
            Vector2D speedDiff = leader.getSpeedVector().sub(me.getSpeedVector());
            Vector2D alignVector = Vector2D.NULLVECTOR;
            Vector2D cohVector = Vector2D.NULLVECTOR;
            if (sepcount > 0)
            {
                sepVector = sepVector.mult(1.0 / sepcount);
            }
            if (distVector.magnitude() > rcrit)
            {
                cohVector = distVector.getNormalized();
            }
            if (speedDiff.magnitude() > 0 && distVector.magnitude() < roo)
            {
                alignVector = speedDiff.getNormalized();
            }
            acceleration.x = cohVector.x + sepVector.x + alignVector.x;
            acceleration.y = cohVector.y + sepVector.y + alignVector.y;
            me.setDirection(leader.getDirection());
        }
        return acceleration;
    }

    public static Ship getFlockLeaderInRange(Actor me, int roo)
    {
        Ship leader = null;
        for (Ship ally : SpaceInvader.getInstance().getAllyships(me.getTeam()))
        {
            double dist = me.getPosition().distanceTo(ally.getPosition());
            // Wingman cannot be a leader
            if ((ally.getAI() == null || !WingmanAI.class.isInstance(ally.getAI())) && roo > dist)
            {
                leader = ally;
                break;
            }
        }
        return leader;
    }
}
