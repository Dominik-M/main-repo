/**
 * Copyright (C) 2016 Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package armory;

import java.util.LinkedList;
import java.util.List;

import utils.Constants;
import utils.Vector2D;
import actors.Projectile;

/**
 * 
 * @author Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com> Created 29.03.2016
 */
public class BurstGun extends Gun {
	private int bursts;
	private int burstAngle;

	BurstGun(String name, int damage, int projectileSpeed, int firerate, int range, int bursts,
	int burstAngle) {
		super(name, damage, projectileSpeed, firerate, range);
		this.bursts = bursts;
		this.burstAngle = burstAngle;
	}

	@Override
	public List<Projectile> shoot(int x, int y, double directionRad) {
		LinkedList<Projectile> shots = new LinkedList<>();
		if (cooldown == 0) {
			double angle = utils.Utilities.toRad(-burstAngle / 2.0);
			double step = utils.Utilities.toRad(1.0 * burstAngle / bursts);
			for (int i = 0; i < bursts; i++) {
				shots.add(new Projectile(this, x, y, new Vector2D(Math.cos(directionRad + angle),
				Math.sin(directionRad + angle)).mult(projectileSpeed)));
				angle += step;
			}
			cooldown = Constants.DELTA_T * 1000 / this.getFirerate();
		}
		return shots;
	}

	public int getBursts() {
		return bursts;
	}

	public void setBursts(int bursts) {
		this.bursts = bursts;
	}

	public int getBurstAngle() {
		return burstAngle;
	}

	public void setBurstAngle(int burstAngle) {
		this.burstAngle = burstAngle;
	}

}
