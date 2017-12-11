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

import actors.Actor;
import actors.Ship;
import graphic.GameGrid;
import java.util.List;
import utils.Vector2D;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 26.03.2016
 */
public abstract class AI implements java.io.Serializable {

    public static final AI AI_DUMMYFIGHTER = new DummyAI();
    public static final AI AI_HUNTER = new HunterAI();

    public abstract void preAct(Actor a);

    /**
     * Lets the given Actor rotate until heading to the given direction.
     *
     * @param me Actor to rotate
     * @param directionDeg direction in degree
     * @return true if the Actor now heads to the given direction
     */
    public static boolean rotateTo(Actor me, int directionDeg) {
        if (Ship.class.isInstance(me)) {
            if ((int) me.getDirection() == directionDeg) {
                ((Ship) me).stopRotate();
                return true;
            } else if (me.getDirection() > directionDeg) {
                ((Ship) me).rotateLeft();
            } else {
                ((Ship) me).rotateRight();
            }
            return false;
        } else {
            me.setDirection(directionDeg);
            return true;
        }
    }

    /**
     * Lets the ship shoot at an enemy ship in range.
     *
     * @param me the ship under control
     * @param range the maximum distance to a target
     * @return true if an enemy ship is in range and the given ship started
     * shooting, false otherwise.
     */
    public static boolean shootEnemyInRange(Ship me, int range) {
        Ship nearestEnemy = null;
        double minDist = 0;
        List<Ship> enemys = GameGrid.getInstance().getEnemyships(me.team);
        for (Ship enemy : enemys) {
            double dist = enemy.getPosition().distanceTo(me.getPosition());
            if (dist < range) {
                if (nearestEnemy == null || dist < minDist) {
                    nearestEnemy = enemy;
                    minDist = dist;
                    rotateTo(me, (int) (utils.Utilities.toDeg(enemy.getPosition().sub(me.getPosition())
                            .getDirection()) + 90));
                }
            }
        }
        me.setShooting(nearestEnemy != null);
        return nearestEnemy != null;
    }

    /**
     * Lets the given ship head to the nearest enemy or ally. Does not start
     * moving, just sets the direction.
     *
     * @param me the ship under control
     * @param enemy true to head to nearest enemy, false to head to nearest
     * ally.
     * @return false if no target is available and the ship does not move, true
     * otherwise.
     */
    public static boolean headToNearest(Ship me, boolean enemy) {
        List<Ship> targets;
        if (enemy) {
            targets = GameGrid.getInstance().getEnemyships(me.team);
        } else {
            targets = GameGrid.getInstance().getAllyships(me.team);
        }
        if (targets.size() > 0) {
            double minDist = targets.get(0).getPosition().distanceTo(me.getPosition());
            Ship nearest = targets.get(0);
            for (int i = 1; i < targets.size(); i++) {
                double dist = targets.get(i).getPosition().distanceTo(me.getPosition());
                if (dist < minDist) {
                    minDist = dist;
                    nearest = targets.get(i);
                }
            }
            Vector2D direction = nearest.getPosition().sub(me.getPosition());
            rotateTo(me, (int) (utils.Utilities.toDeg(direction.getDirection()) - 90));
            return true;
        }
        return false;
    }

    /**
     * Lets the given Actor head to the landing zone. Does not start moving,
     * just sets the direction.
     *
     * @param me the Actor under control
     * @return true if the ship is now heading to the langing zone.
     */
    public static boolean headToLandingZone(Actor me) {
        int cX, cY;
        if (GameGrid.getInstance().getMap().LANDING_ZONE != null) {
            cX = (int) GameGrid.getInstance().getMap().LANDING_ZONE.getCenterX();
            cY = (int) GameGrid.getInstance().getMap().LANDING_ZONE.getCenterY();
        } else {
            return false;
        }
        Vector2D direction = new Vector2D(cX, cY).sub(me.getPosition());
        rotateTo(me, (int) (utils.Utilities.toDeg(direction.getDirection()) - 90));
        return true;
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
    public static Vector2D getFlockAlignAcceleration(Actor me, Ship leader, int roo, int rcrit) {
        Vector2D acceleration = new Vector2D(0, 0);
        if (leader != null) {
            List<Ship> allys = GameGrid.getInstance().getAllyships(me.team);
            allys.remove(me);
            Vector2D sepVector = new Vector2D(0, 0);
            int sepcount = 0;
            Vector2D distVector;
            for (Ship ally : allys) {
                // separate from allys
                if (ally.getBounds().intersects(me.getBounds())) {
                    distVector = ally.getPosition().sub(me.getPosition());
                    if (ally.getXOnScreen() == me.getXOnScreen()
                            && ally.getYOnScreen() == me.getYOnScreen()) {
                        distVector.x = 1;
                        distVector.y = 1;
                    } else {
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
            if (sepcount > 0) {
                sepVector = sepVector.mult(1.0 / sepcount);
            }
            if (distVector.magnitude() > rcrit) {
                cohVector = distVector.getNormalized();
            }
            if (speedDiff.magnitude() > 0 && distVector.magnitude() < roo) {
                alignVector = speedDiff.getNormalized();
            }
            acceleration.x = cohVector.x + sepVector.x + alignVector.x;
            acceleration.y = cohVector.y + sepVector.y + alignVector.y;
        }
        return acceleration;
    }

    public static Ship getFlockLeaderInRange(Actor me, int roo) {
        Ship leader = null;
        for (Ship ally : GameGrid.getInstance().getAllyships(me.team)) {
            double dist = me.getPosition().distanceTo(ally.getPosition());
            // Wingman cannot be a leader
            if ((ally.getAI() == null || !WingmanAI.class.isInstance(ally.getAI())) && roo > dist) {
                leader = ally;
                break;
            }
        }
        return leader;
    }
}
