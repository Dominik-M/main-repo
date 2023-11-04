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
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;
import utils.Constants.Team;
import utils.Text;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 28.03.2016
 */
public class Fighter extends Ship {

    private Weapon mainWeapon;

    public Fighter(int x, int y, Team team, int maxHp, double maxSpeed, double maxAccel,
            double maxRot, Weapon mainWeapon, String... spritenames) {
        super(x, y, team, maxHp, maxSpeed, maxAccel, maxRot, spritenames);
        setMainWeapon(mainWeapon);
    }

    @Override
    public void act() {
        super.act();
        if (mainWeapon != null) {
            mainWeapon.refresh();
        }
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

    public Weapon getMainWeapon() {
        return mainWeapon;
    }

    public void setMainWeapon(Weapon mainWeapon) {
        this.mainWeapon = mainWeapon;
        mainWeapon.owner = this;
    }

    @Override
    public List<Weapon> getWeapons() {
        LinkedList<Weapon> weapons = new LinkedList<Weapon>();
        if (mainWeapon != null) {
            weapons.add(mainWeapon);
        }
        return weapons;
    }

    @Override
    public String getDataString() {
        return Text.CLASS + ": " + Text.FIGHTER + "\n" + super.getDataString() + "\n"
                + Text.MAINWEAPON + ": " + mainWeapon;
    }
}
