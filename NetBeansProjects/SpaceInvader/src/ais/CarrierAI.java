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

import java.awt.Rectangle;

import main.SpaceInvader;
import platform.gamegrid.Actor;
import platform.utils.Utilities;
import platform.utils.Vector2D;
import actors.Carrier;
import actors.Ship;

/**
 * An AI designed for controlling Carrier Ships. If fleets are garrisoned
 * inside, they will deploy during fight.
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 30.03.2016
 */
public class CarrierAI extends BaseAI
{

    @Override
    public void preAct(Actor a)
    {
        if (a.getSpeed() == 0 && SpaceInvader.getInstance().getMap() != null
                && SpaceInvader.getInstance().getMap().LANDING_ZONE != null)
        {
            Rectangle lzone = SpaceInvader.getInstance().getMap().LANDING_ZONE;
            Vector2D direction = new Vector2D(lzone.x - a.getX(), lzone.y - a.getY());
            direction.normalize();
            a.setSpeed(direction.mult(20));
            a.setDirection(Utilities.toDeg(a.getSpeedVector().getDirection()));
        }
        if (Carrier.class.isInstance(a))
        {
            Carrier me = ((Carrier) a);
            if (me.getCapacity() * me.getHP() / me.getMaxHp() < me.getContentSize())
            {
                for (Ship deployed : me.deployFirst(5))
                {
                    SpaceInvader.getInstance().addActor(deployed);
                }
            }
            shootEnemyInRange(me, 600);
        }
    }

    @Override
    public String toString()
    {
        return "Carrier AI";
    }
}
