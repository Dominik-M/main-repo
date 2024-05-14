/*
 * Copyright (C) 2016 Dominik
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
package pokemon.world;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import utils.Constants;
import utils.Dictionary;
import utils.IO;

/**
 *
 * @author Dominik
 */
public class Item extends utils.SerializableReflectObject
{

    public static final Item POTION = new Item("POTION", Typ.POTION, 20);
    public static final Item SUPERPOTION = new Item("SUPERPOTION", Typ.POTION, 50);
    public static final Item HYPERPOTION = new Item("HYPERPOTION", Typ.POTION, 200);
    public static final Item TOPPOTION = new Item("TOPPOTION", Typ.POTION, Integer.MAX_VALUE);
    public static final Item POKEBALL = new Item("POKEBALL", Typ.POKEBALL, Constants.CATCHRATE_LOW);
    public static final Item SONDERBONBON = new Item("SONDERBONBON", Typ.SONDERBONBON, 5000);

    public enum Typ
    {
        POTION,
        POKEBALL,
        SONDERBONBON,
        MISC;

        @Override
        public String toString()
        {
            return name();
        }
    }

    private String name;
    private int value;
    public final Typ TYP;

    public Item(Typ typ)
    {
        this(typ, 0);
    }

    public Item(Typ typ, int value)
    {
        this(null, typ, value);
    }

    public Item(String name, Typ typ, int value)
    {
        this.name = name;
        this.TYP = typ;
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public boolean equals(Item other)
    {
        return this.TYP == other.TYP && this.value == other.value;
    }

    @Override
    public String toString()
    {
        if (name != null)
        {
            return IO.translate(name);
        }
        else if (value > 1)
        {
            return TYP.toString() + " " + value;
        }
        else
        {
            return TYP.toString();
        }
    }

    @Override
    public Dictionary<String, Object> getAttributes()
    {
        Dictionary<String, Object> values = new Dictionary<>();
        for (Field f : this.getClass().getDeclaredFields())
        {
            try
            {
                if (!Modifier.isStatic(f.getModifiers()))
                {
                    values.add(f.getName(), f.get(this));
                }
            } catch (IllegalArgumentException | IllegalAccessException ex)
            {
                IO.printException(ex);
            }
        }
        return values;
    }
}
