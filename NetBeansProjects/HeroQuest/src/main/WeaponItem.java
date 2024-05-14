/*
 * Copyright (C) 2023 Dominik Messerschmidt
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
package main;

/**
 *
 * @author Dominik Messerschmidt
 */
public class WeaponItem extends Item
{

    public final Weapon w;

    public WeaponItem(Weapon weapon)
    {
        super(weapon.toString(), "A " + weapon.toString() + ". ATK: " + weapon.atk + (weapon.canDiagonal ? " can attack diagonal" : ""), weapon.value, false);
        w = weapon;
    }

    @Override
    public boolean use()
    {
        Actor a = Game.getInstance().getActorOnTurn();
        Actor target = Game.getInstance().getActorAt(Game.getInstance().getSelectedX(), Game.getInstance().getSelectedY());
        if (target == null || (a.isHero() != target.isHero()))
        {
            target = a;
        }
        Weapon oldWeapon = target.getWeapon();
        if (oldWeapon != Weapon.NONE)
        {
            target.addItem(new WeaponItem(oldWeapon));
            Game.getInstance().message(target + " unequipped " + oldWeapon + ". And ");
        }
        target.setWeapon(w);
        Game.getInstance().message(target + " equipped " + w);
        return true;
    }

    @Override
    public boolean canUse()
    {
        return true;
    }
}
