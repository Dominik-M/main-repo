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
import java.awt.Rectangle;
import utils.Vector2D;

/**
 * Basis AI. Flys around randomly and shoots enemys in range.
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 29.03.2016
 */
public class DummyAI extends AI {

    @Override
    public void preAct(Actor a) {
        if (a.getSpeed() == 0 && GameGrid.getInstance().getMap() != null && GameGrid.getInstance().getMap().LANDING_ZONE != null) {
            Rectangle lzone = GameGrid.getInstance().getMap().LANDING_ZONE;
            Vector2D direction = new Vector2D(lzone.x - a.getX(), lzone.y - a.getY());
            direction.normalize();
        }
        if (Ship.class.isInstance(a)) {
            ((Ship) a).setAccelerating(true);
            if (!shootEnemyInRange((Ship) a, 400)) {
                a.setDirection(utils.Utilities.toDeg(a.getSpeedVector().getDirection()));
            }
        }
    }

    @Override
    public String toString() {
        return "Dummy AI";
    }
}
