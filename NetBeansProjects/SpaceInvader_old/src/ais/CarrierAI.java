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
import actors.Carrier;
import actors.Ship;
import graphic.GameGrid;
import utils.Vector2D;

/**
 * An AI designed for controlling Carrier Ships. If fleets are garrisoned
 * inside, they will deploy during fight.
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 30.03.2016
 */
public class CarrierAI extends AI {

    @Override
    public void preAct(Actor a) {
        if (!headToLandingZone(a) && a.getSpeed() == 0) {
            Vector2D direction = new Vector2D(GameGrid.getInstance().getMap().WIDTH / 2, GameGrid.getInstance().getMap().HEIGHT / 2);
            a.setDirection(utils.Utilities.toDeg(direction.getDirection()));
        }
        if (Ship.class.isInstance(a)) {
            ((Ship) a).setAccelerating(true);
        }
        if (Carrier.class.isInstance(a)) {
            Carrier me = ((Carrier) a);
            if (me.getCapacity() * me.getHP() / me.getMaxHp() < me.getContentSize()) {
                for (Ship deployed : me.deployFirst(5)) {
                    GameGrid.getInstance().addActor(deployed);
                }
            }
            AI.shootEnemyInRange(me, 600);
        }
    }

    @Override
    public String toString() {
        return "Carrier AI";
    }
}
