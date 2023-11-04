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

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 29.03.2016
 */
public class SalvoGun extends Gun {

    private int heat;
    private int maxHeat;
    private double heatrate;
    private int salvos;

    SalvoGun(String name, int damage, int projectileSpeed, int firerate, int salvos, double heatrate) {
        this(name, damage, projectileSpeed, firerate, salvos, heatrate, 0);
    }

    SalvoGun(String name, int damage, int projectileSpeed, int firerate, int salvos,
            double heatrate, int spreadAngle) {
        super(name, damage, projectileSpeed, firerate, GunFactory.DEFAULT_RANGE_MEDIUM, spreadAngle);
        heat = 0;
        maxHeat = SpaceInvader.DELTA_T * 1000 * salvos / firerate;
        this.heatrate = heatrate;
        this.salvos = salvos;
    }

    @Override
    public List<Projectile> shoot(int x, int y, double directionRad) {
        java.util.LinkedList<Projectile> shots = new java.util.LinkedList<Projectile>();
        if (cooldown == 0) {
            shots.addAll(super.shoot(x, y, directionRad));
            heat += cooldown;
            if (maxHeat <= heat) {
                cooldown = (int) (heat * heatrate);
                heat = 0;
            }
        }
        return shots;
    }

    public int getHeat() {
        return heat;
    }

    public int getMaxHeat() {
        return maxHeat;
    }

    public double getHeatrate() {
        return heatrate;
    }

    public void setHeatrate(double heatrate) {
        this.heatrate = heatrate;
    }

    @Override
    public void setFirerate(int firerate) {
        super.setFirerate(firerate);
        maxHeat = SpaceInvader.DELTA_T * 1000 * salvos / firerate;
    }

    public int getSalvos() {
        return salvos;
    }

    public void setSalvos(int salvos) {
        if (salvos <= 0) {
            throw new java.lang.IllegalArgumentException("Salvo count must be greater than 0");
        }
        this.salvos = salvos;
        maxHeat = SpaceInvader.DELTA_T * salvos * 1000 / getFirerate();
    }

    @Override
    public int getMaxCooldown() {
        return (int) (maxHeat * heatrate);
    }
}
