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

import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

import platform.gamegrid.Actor;
import armory.Weapon;

/**
 * A Turret is an Actor with a Weapon and the ability to shoot. Turret doesn't do anything by itself. It's remote
 * controlled mainly by its methods setX(), setY(), setShooting() and setDirection(). Turrets cannot be hit or killed
 * directly, they have to be inherited by an instance that calls invalidate() if the Turret should be removed.
 * 
 * @author Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com> Created 30.03.2016
 */
public class Turret extends Actor implements Shooting {

	private Weapon mainWeapon;
	private boolean shooting;
	public Actor owner;

	Turret(Actor owner, Weapon mainWeapon, String mainSprite, String... spritenames) {
		super(mainSprite, spritenames);
		this.owner = owner;
		setTeam(owner.getTeam());
		setMainWeapon(mainWeapon);
	}

	public Weapon getMainWeapon() {
		return mainWeapon;
	}

	public void setMainWeapon(Weapon mainWeapon) {
		mainWeapon.owner = owner;
		this.mainWeapon = mainWeapon;
	}

	@Override
	public void act() {
		// Turret doesn't do anything by itself but refreshing the gun. It's remote controlled mainly by its methods
		// setX(), setY(), setShooting() and setDirection().
		if (mainWeapon != null) {
			mainWeapon.refresh();
		}
	}

	@Override
	public boolean isShooting() {
		return shooting;
	}

	public void setShooting(boolean isShooting) {
		shooting = isShooting;
	}

	@Override
	public List<Projectile> shoot() {
		if (mainWeapon != null) {
			Rectangle bounds = this.getBounds();
			return mainWeapon.shoot((int) (bounds.getCenterX()), (int) (bounds.getCenterY()),
			getDirectionRad());
		}
		return new java.util.LinkedList<Projectile>();
	}

	@Override
	public List<Weapon> getWeapons() {
		LinkedList<Weapon> weapons = new LinkedList<Weapon>();
		if (mainWeapon != null) {
			weapons.add(mainWeapon);
		}
		return weapons;
	}
}
