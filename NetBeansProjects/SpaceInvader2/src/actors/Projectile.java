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
package actors;

import armory.Weapon;
import main.SpaceInvader;
import platform.gamegrid.Actor;
import platform.utils.Utilities;
import platform.utils.Vector2D;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 26.03.2016
 */
public class Projectile extends Actor
{

    public final Weapon source;
    private int lifetime;

    public Projectile(Weapon source, int x, int y, Vector2D velocity)
    {
        super(source.getProjectileImage());
        setTeam(source.owner.getTeam());
        this.source = source;
        setX(x);
        setY(y);
        // To be physically right...
        this.setSpeed(velocity.add(source.owner.getSpeedVector()));
        // Easier to control...
        // this.setSpeed(velocity);
        this.setDirection(Utilities.toDeg(velocity.getDirection()) + 90);
        lifetime = source.getRange() / source.projectileSpeed;
    }

    @Override
    public void act()
    {
        move();
        // lifetime limited by distance
        // lifetime -= getSpeed() * SpaceInvader.DELTA_T / 1000.0;
        // lifetime limited by active time
        lifetime -= SpaceInvader.DELTA_T;
        if (!SpaceInvader.getInstance().isInGrid(getXOnScreen(), getYOnScreen()) || lifetime <= 0)
        {
            invalidate();
        }
    }

    @Override
    public void move()
    {
        Vector2D speedvector = this.getSpeedVector();
        speedvector = speedvector.mult(SpaceInvader.DELTA_T / 1000.0);
        x += speedvector.x;
        y += speedvector.y;
    }

}
