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
import main.SpaceInvader;
import platform.utils.IO;
import platform.utils.Vector2D;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 29.03.2016
 */
public class Gun extends Weapon {

    private int firerate;

    Gun(String name, int damage, int projectileSpeed, int firerate, int range) {
        this(name, damage, projectileSpeed, firerate, range, 0);
    }

    Gun(String name, int damage, int projectileSpeed, int firerate, int range, int spreadAngle) {
        super(name, damage, projectileSpeed, range, spreadAngle, GunFactory.IMAGENAME_BOMB);
        this.firerate = firerate;
    }

    @Override
    public List<Projectile> shoot(int x, int y, double directionRad) {
        java.util.LinkedList<Projectile> shots = new java.util.LinkedList<Projectile>();
        while (cooldown < SpaceInvader.DELTA_T) {
            double spreadRad = (Math.random() * Math.PI - Math.PI / 2) * getSpreadAngle() / 180;
            shots.add(new Projectile(this, x, y, new Vector2D(Math.cos(directionRad + spreadRad), Math
                    .sin(directionRad + spreadRad)).mult(projectileSpeed)));
            cooldown += SpaceInvader.DELTA_T * 1000 / firerate;
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
        return SpaceInvader.DELTA_T * 1000 / firerate;
    }

    @Override
    public String getDataString() {
        return super.getDataString() + "\n" + IO.translate("FIRERATE") + ": " + firerate;
    }
}
