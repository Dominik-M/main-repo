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
package armory;

import actors.Projectile;
import java.util.List;
import utils.Constants;
import utils.Vector2D;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 29.03.2016
 */
public class Gun extends Weapon {

    private int firerate;

    Gun(String name, int damage, int projectileSpeed, int firerate, int range) {
        super(name, damage, projectileSpeed, range);
        this.firerate = firerate;
    }

    @Override
    public List<Projectile> shoot(int x, int y, double directionRad) {
        java.util.LinkedList<Projectile> shots = new java.util.LinkedList<Projectile>();
        if (cooldown == 0) {
            shots.add(new Projectile(this, x, y, new Vector2D(Math.cos(directionRad), Math
                    .sin(directionRad)).mult(projectileSpeed)));
            cooldown = Constants.DELTA_T * 1000 / firerate;
        }
        return shots;
    }

    public int getFirerate() {
        return firerate;
    }

    public void setFirerate(int firerate) {
        this.firerate = firerate;
    }

    public int getMaxCooldown() {
        return Constants.DELTA_T * 1000 / firerate;
    }
}
