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

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 29.03.2016
 */
public class HunterAI extends AI {

    @Override
    public void preAct(Actor a) {
        GameGrid game = GameGrid.getInstance();
        if (Ship.class.isInstance(a)) {
            Ship me = (Ship) a;
            // shooting has highest priority
            if (shootEnemyInRange(me, 400)) {
                me.setBraking(true);
                return;
            }
            // head to nearest enemy
            if (headToNearest(me, true)) {
                me.setAccelerating(true);
                return;
            }
            // no enemys there, return to base
            if (game.isInLandingZone(me)) {
                me.stop();
                me.repair();
            } else {
                if (headToLandingZone(me)) {
                    me.setAccelerating(true);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "AI Hunter";
    }

}
