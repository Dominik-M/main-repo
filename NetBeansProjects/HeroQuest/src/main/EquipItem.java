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
public class EquipItem extends Item
{

    public final Equip e;

    public EquipItem(Equip equip)
    {
        super(equip.toString(), equip.description, equip.value, false);
        e = equip;
    }

    @Override
    public boolean use()
    {
        Actor a = Game.getInstance().getActorOnTurn();
        Actor target = Game.getInstance().getActorAt(Game.getInstance().getSelectedX(), Game.getInstance().getSelectedY());
        if (target == null)
        {
            target = a;
        }
        target.addEquip(e);
        Game.getInstance().message(target + " equipped " + e);
        return true;
    }

    @Override
    public boolean canUse()
    {
        return true;
    }
}
