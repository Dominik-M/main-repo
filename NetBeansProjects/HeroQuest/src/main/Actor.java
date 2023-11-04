/*
 * Copyright (C) 2022 Dominik Messerschmidt
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

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Dominik Messerschmidt
 */
public class Actor implements Serializable
{

    public enum ActorType
    {
        BARBAR,
        MAGE,
        ELF,
        DWARF,
        GOBLIN,
        ORC,
        SCELETON,
        KNIGHT,
        MUMMY,
        TROLL,
        DEMON,
        UNDEFINED;

        boolean isUndead()
        {
            return this == DEMON || this == MUMMY || this == SCELETON;
        }
    }

    private int x, y, hp, maxHp, def, spd, res, gold, sleeptime;
    private String name;
    private boolean enemy;
    private ActorType type;
    private Weapon weapon;
    private final LinkedList<Equip> equipment = new LinkedList<>();
    private final LinkedList<Item> inventory = new LinkedList<>();

    /**
     * Actor(String n, int h, int d, int s, int r, int g, boolean e, ActorType
     * t)
     *
     * @param n Name
     * @param h Hitpoints
     * @param d Defense
     * @param s Speed
     * @param r Resistance
     * @param g Gold
     * @param e Enemy
     * @param t Type
     */
    public Actor(String n, int h, int d, int s, int r, int g, boolean e, ActorType t)
    {
        name = n;
        hp = h;
        maxHp = h;
        def = d;
        spd = s;
        res = r;
        enemy = e;
        gold = g;
        type = t;
        weapon = Weapon.NONE;
    }

    public Actor()
    {
        this("undefined", 0, 0, 0, 0, 0, true, ActorType.UNDEFINED);
        System.err.println("WARNG: Default constructor of Actor called. Undefined attributes");
    }

    @Override
    public String toString()
    {
        return name;
    }

    public boolean isHero()
    {
        return !enemy;
    }

    public boolean isEnemy()
    {
        return enemy;
    }

    public void setEnemy(boolean e)
    {
        enemy = e;
    }

    public ActorType getType()
    {
        return type;
    }

    public void setType(ActorType t)
    {
        type = t;
    }

    public Weapon getWeapon()
    {
        return weapon;
    }

    public void setWeapon(Weapon w)
    {
        weapon = w;
    }

    public Item[] getInventory()
    {
        Item[] items = new Item[inventory.size()];
        for (int i = 0; i < items.length; i++)
        {
            items[i] = inventory.get(i);
        }
        return items;
    }

    public void addItem(Item i)
    {
        inventory.add(i);
    }

    public boolean removeItem(Item i)
    {
        return inventory.remove(i);
    }

    public Equip[] getEquipment()
    {
        Equip[] equip = new Equip[equipment.size()];
        for (int i = 0; i < equip.length; i++)
        {
            equip[i] = equipment.get(i);
        }
        return equip;
    }

    public void addEquip(Equip e)
    {
        equipment.add(e);
    }

    public boolean removeEquip(Equip e)
    {
        return equipment.remove(e);
    }

    public int getGold()
    {
        return gold;
    }

    public void setGold(int g)
    {
        this.gold = g;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getHp()
    {
        return hp;
    }

    public void setHp(int hp)
    {
        this.hp = hp;
    }

    public int getMaxHp()
    {
        return maxHp;
    }

    public void setMaxHp(int hp)
    {
        this.maxHp = hp;
    }

    public int getAtk()
    {
        return weapon.atk;
    }

    public int getDef()
    {
        int equipbonus = 0;
        for (Equip e : equipment)
        {
            if (e == Equip.ARMOR || e == Equip.HELMET || e == Equip.SHIELD)
            {
                equipbonus++;
            }
        }
        return def + equipbonus;
    }

    public void setDef(int def)
    {
        this.def = def;
    }

    public int getSpd()
    {
        return spd;
    }

    public void setSpd(int spd)
    {
        this.spd = spd;
    }

    public int getRes()
    {
        return res;
    }

    public void setRes(int res)
    {
        this.res = res;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean hasItem(Item sel)
    {
        return inventory.contains(sel);
    }

}
