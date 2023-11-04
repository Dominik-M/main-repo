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

import actors.Actor;
import actors.Projectile;
import java.util.List;
import utils.Constants;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 28.03.2016
 */
public abstract class Weapon implements java.io.Serializable {

    public Actor owner;
    public final int projectileSpeed;
    protected int cooldown = 0;
    private int damage;
    private int range;
    private final String name;

    public Weapon(String name, int damage, int projectileSpeed, int range) {
        this.projectileSpeed = projectileSpeed;
        this.damage = damage;
        this.range = range;
        this.name = name;
    }

    public abstract List<Projectile> shoot(int x, int y, double direction);

    public void refresh() {
        cooldown -= Constants.DELTA_T;
        if (cooldown < 0) {
            cooldown = 0;
        }
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getDataString() {
        return "Damage: " + this.damage + "\nRange: " + this.range + "\nProjectile Speed: "
                + this.projectileSpeed;
    }
}
