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
public class ActorFactory
{

    public static final Actor getDefaultActorOfType(Actor.ActorType type)
    {
        Actor a;
        switch (type)
        {
            case BARBAR:
                // Actor(String name, int hp, int atk, int def, int speed, int res, int gold, boolean enemy, ActorType type)
                a = new Actor("Barbar", 8, 2, 0, 1, 20, false, type);
                a.setWeapon(Weapon.BATTLEAXE);
                break;
            case MAGE:
                a = new Actor("Wizard", 4, 1, 0, 6, 0, false, type);
                a.setWeapon(Weapon.DAGGER);
                a.addItem(Item.HEAL);
                a.addItem(Item.HEAL);
                a.addItem(Item.FIREBALL);
                a.addItem(Item.FIREBALL);
                a.addItem(Item.HOLYNOVA);
                break;
            case ELF:
                a = new Actor("Elf", 6, 2, 0, 4, 50, false, type);
                a.setWeapon(Weapon.SWORD);
                a.addItem(Item.THROWING_KNIFE);
                a.addItem(Item.THROWING_KNIFE);
                a.addItem(Item.THROWING_KNIFE);
                a.addItem(Item.CROSSBOW);
                a.addItem(Item.CROSSBOW);
                break;
            case DWARF:
                a = new Actor("Dwarf", 7, 2, 0, 3, 100, false, type);
                a.setWeapon(Weapon.SWORD);
                a.addItem(Item.DIFFUSE_KIT);
                a.addItem(Item.DIFFUSE_KIT);
                a.addItem(Item.DIFFUSE_KIT);
                a.addItem(Item.DIFFUSE_KIT);
                break;
            case GOBLIN:
                a = new Actor("Goblin", 1, 1, 12, 1, Game.roll(10), true, type);
                a.setWeapon(Weapon.SWORD);
                break;
            case ORC:
                a = new Actor("Orc", 1, 2, 6, 2, Game.roll(30), true, type);
                a.setWeapon(Weapon.BATTLEAXE);
                break;
            case SCELETON:
                a = new Actor("Sceleton", 1, 2, 8, 1, Game.roll(20), true, type);
                a.setWeapon(Weapon.SWORD);
                break;
            case MUMMY:
                a = new Actor("Mummy", 1, 2, 4, 1, Game.roll(20), true, type);
                a.setWeapon(Weapon.SWORD);
                break;
            case TROLL:
                a = new Actor("Troll", 3, 2, 5, 1, Game.roll(50), true, type);
                a.setWeapon(Weapon.BATTLEAXE);
                break;
            case KNIGHT:
                a = new Actor("Death Knight", 2, 3, 8, 3, Game.roll(80), true, type);
                a.setWeapon(Weapon.HALBERD);
                break;
            case DEMON:
                a = new Actor("Demon", 4, 2, 8, 4, Game.roll(200), true, type);
                a.setWeapon(Weapon.DRAGONSWORD);
                break;
            default:
                a = new Actor();
                break;
        }
        return a;
    }
}
