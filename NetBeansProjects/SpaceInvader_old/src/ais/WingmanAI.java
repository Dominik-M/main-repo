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
import utils.Constants;
import utils.Vector2D;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 29.03.2016
 */
public class WingmanAI extends AI {

    public static final int ORDER_STAY = 1, ORDER_FOLLOW = 2;
    private int order;
    public Ship leader;
    public int radiusOfObservation;
    public int leastDiff;

    public WingmanAI() {
        this(null, 200, 50);
    }

    public WingmanAI(Ship leader) {
        this(leader, 200, 50);
    }

    public WingmanAI(Ship leader, int radiusOfObservation, int leastDiff) {
        this.radiusOfObservation = radiusOfObservation;
        this.leastDiff = leastDiff;
        this.leader = leader;
        order = ORDER_FOLLOW;
    }

    public WingmanAI(int radiusOfObservation, int leastDiff) {
        this(null, radiusOfObservation, leastDiff);
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public void preAct(Actor a) {
        GameGrid game = GameGrid.getInstance();
        if (Ship.class.isInstance(a)) {
            Ship me = (Ship) a;
            if (this.leader != null && this.leader.isInvalid()) {
                this.leader = null;
            }
            Ship leader = this.leader;
            if (order == ORDER_STAY) {
                leader = null;
            } else if (leader == null) {
                leader = getFlockLeaderInRange(me, radiusOfObservation);
                if (leader != null) {
                    this.leader = leader;
                    if (leader.equals(game.getMShip())) {
                        order = ORDER_FOLLOW;
                        game.addToFleet(this);
                    }
                }
            }
            // stick to flock
            Vector2D accel = getFlockAlignAcceleration(a, leader, radiusOfObservation, leastDiff)
                    .mult(me.getMaxAcceleration() * Constants.DELTA_T / 1000.0);
            if (accel.magnitude() == 0) {
                me.stop();
            } else {
                me.setSpeed(me.getSpeedVector().add(accel));
            }
            // shoot
            boolean shooting = shootEnemyInRange(me, 400);
            if (game.isInLandingZone(a) && !shooting) {
                me.repair();
            }
            if (!shooting && this.leader != null) {
                me.setDirection(this.leader.getDirection());
            }
        }
    }

    @Override
    public String toString() {
        return "KI Wingman";
    }

}
