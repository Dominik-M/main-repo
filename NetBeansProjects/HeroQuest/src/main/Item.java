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
import main.Board.Field;

/**
 *
 * @author Dominik Messerschmidt
 */
public abstract class Item implements Serializable
{

    public static final Item POTION = new Item("Potion", "Heals a selected character 1 to 5 hp", 80, false)
    {
        @Override
        public boolean use()
        {
            Actor a = Game.getInstance().getActorOnTurn();
            Actor target = Game.getInstance().getActorAt(Game.getInstance().getSelectedX(), Game.getInstance().getSelectedY());
            if (target == null)
            {
                target = a;
            }
            int n = Game.roll(5);
            int hp = target.getHp() + n;
            target.setHp(target.getMaxHp() < hp ? target.getMaxHp() : hp);
            Game.getInstance().message(target + " healed " + n + " HP");
            return true;
        }

        @Override
        public boolean canUse()
        {
            return true;
        }
    }, HEAL = new Item("Heal", "Heals a selected character 2 to 4 hp", 100, true)
    {
        @Override
        public boolean use()
        {
            Actor a = Game.getInstance().getActorOnTurn();
            Actor target = Game.getInstance().getActorAt(Game.getInstance().getSelectedX(), Game.getInstance().getSelectedY());
            if (target == null)
            {
                target = a;
            }
            int n = Game.roll(3) + 1;
            int hp = target.getHp() + n;
            target.setHp(target.getMaxHp() < hp ? target.getMaxHp() : hp);
            Game.getInstance().message(target + " healed " + n + " HP");
            return true;
        }

        @Override
        public boolean canUse()
        {
            return true;
        }
    },
            THROWING_KNIFE = new Item("Throwing Knife", "Can attack a distant target with 1 attack dice", 30, true)
    {
        @Override
        public boolean use()
        {
            Actor a = Game.getInstance().getActorOnTurn();
            Actor target = Game.getInstance().getActorAt(Game.getInstance().getSelectedX(), Game.getInstance().getSelectedY());
            if (a != null && target != null && Game.getInstance().isInSight(a.getX(), a.getY()))
            {
                Game.getInstance().fight(a, 1, target);
                return true;
            }
            return false;
        }

        @Override
        public boolean canUse()
        {
            Actor a = Game.getInstance().getActorOnTurn();
            Actor target = Game.getInstance().getActorAt(Game.getInstance().getSelectedX(), Game.getInstance().getSelectedY());
            if (a != null && target != null && Game.getInstance().isInSight(a.getX(), a.getY()))
            {
                return true;
            }
            return false;
        }
    },
            CROSSBOW = new Item("Crossbow", "Can attack a distant target with 2 Attack dices", 90, true)
    {
        @Override
        public boolean use()
        {
            Actor a = Game.getInstance().getActorOnTurn();
            Actor target = Game.getInstance().getActorAt(Game.getInstance().getSelectedX(), Game.getInstance().getSelectedY());
            if (a != null && target != null && Game.getInstance().isInSight(a.getX(), a.getY()))
            {
                Game.getInstance().fight(a, 2, target);
                return true;
            }
            return false;
        }

        @Override
        public boolean canUse()
        {
            Actor a = Game.getInstance().getActorOnTurn();
            Actor target = Game.getInstance().getActorAt(Game.getInstance().getSelectedX(), Game.getInstance().getSelectedY());
            if (a != null && target != null && Game.getInstance().isInSight(a.getX(), a.getY()))
            {
                return true;
            }
            return false;
        }
    }, FIREBALL = new Item("Fireball", "Can attack a distant target with 3 Attack dices", 200, true)
    {
        @Override
        public boolean use()
        {
            Actor a = Game.getInstance().getActorOnTurn();
            Actor target = Game.getInstance().getActorAt(Game.getInstance().getSelectedX(), Game.getInstance().getSelectedY());
            if (a != null && target != null && Game.getInstance().isInSight(a.getX(), a.getY()))
            {
                Game.getInstance().fight(a, 3, target);
                return true;
            }
            return false;
        }

        @Override
        public boolean canUse()
        {
            Actor a = Game.getInstance().getActorOnTurn();
            Actor target = Game.getInstance().getActorAt(Game.getInstance().getSelectedX(), Game.getInstance().getSelectedY());
            if (a != null && target != null && Game.getInstance().isInSight(a.getX(), a.getY()))
            {
                return true;
            }
            return false;
        }
    }, HOLYNOVA = new Item("Holy Nova", "Attacks sorrounding enemys with 2 attack dices. Undeads receive double damage.", 300, true)
    {
        @Override
        public boolean use()
        {
            Actor a = Game.getInstance().getActorOnTurn();
            if (a != null)
            {
                for (Field f : Game.getInstance().getBoard().getAdjacentFields(a.getX(), a.getY(), true, true))
                {
                    Actor target = Game.getInstance().getActorAt(f.x, f.y);
                    if (target != null)
                    {
                        Game.getInstance().fight(a, target.getType().isUndead() ? 4 : 2, target);
                    }
                }
            }
            return true;
        }

        @Override
        public boolean canUse()
        {
            return true;
        }
    },
            DIFFUSE_KIT = new Item("Diffuse Kit", "Can remove a trap", 50, false)
    {
        @Override
        public boolean use()
        {
            Actor a = Game.getInstance().getActorOnTurn();
            int x = Game.getInstance().getSelectedX();
            int y = Game.getInstance().getSelectedY();
            if (a != null && Game.getInstance().isInSight(x, y))
            {
                if (Game.getInstance().getBoard().getField(x, y).getTrap())
                {
                    Game.getInstance().tryDiffuseTrap(x, y);
                }
                else
                {
                    Game.getInstance().message("Failed to remove trap");
                }
                return true;
            }
            return false;
        }

        @Override
        public boolean canUse()
        {
            Actor a = Game.getInstance().getActorOnTurn();
            if (a != null && Game.getInstance().isInSight(Game.getInstance().getSelectedX(), Game.getInstance().getSelectedY()))
            {
                return true;
            }
            return false;
        }
    };

    private String name, description;
    private int value;
    public final boolean consumesAction;

    public Item(String n, String d, int v, boolean action)
    {
        name = n;
        description = d;
        value = v;
        consumesAction = action;
    }

    private Item()
    {
        this("Unknown Item", "???", 0, false);
    }

    public abstract boolean use();

    public abstract boolean canUse();

    @Override
    public String toString()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public int getValue()
    {
        return value;
    }

}
