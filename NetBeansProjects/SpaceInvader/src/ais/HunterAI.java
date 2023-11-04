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
import main.SpaceInvader;
import platform.gamegrid.Actor;
import platform.utils.Utilities;
import platform.utils.Vector2D;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 29.03.2016
 */
public class HunterAI extends BaseAI
{

    public Ship leader;

    public HunterAI()
    {

    }

    public HunterAI(Ship leader)
    {
        this.leader = leader;
    }

    @Override
    public void preAct(Actor a)
    {
        SpaceInvader game = SpaceInvader.getInstance();
        if (Ship.class.isInstance(a))
        {
            Ship me = (Ship) a;
            // shooting has highest priority
            if (shootEnemyInRange(me, 400))
            {
                //Ship target = getNearest(me, true);
                //orbit(me, target.getPosition(), 200);
                return;
            }
            // head to nearest enemy
            else if (headToNearest(me, true))
            {
                return;
            }
            // no enemys there, return to base
            if (game.isInLandingZone(me))
            {
                me.setSpeed(Vector2D.NULLVECTOR);
                if (me.getHP() < me.getMaxHp())
                {
                    me.repair();
                }
            }
            if (leader != null)
            {
                orbit(me, leader.getCenterPosition(), 200);
                me.setDirection(Utilities.toDeg(me.getSpeedVector().getDirection()) + 90);
            }
            else
            {
                // headToLandingZone(me);
                if (game.getMap().LANDING_ZONE != null)
                {
                    orbit(me, new Vector2D(game.getMap().LANDING_ZONE.getCenterX(), game.getMap().LANDING_ZONE.getCenterY()), 200);
                    me.setDirection(Utilities.toDeg(me.getSpeedVector().getDirection()) + 90);
                }
            }
        }
    }

    @Override
    public String toString()
    {
        return "AI Hunter";
    }

}
