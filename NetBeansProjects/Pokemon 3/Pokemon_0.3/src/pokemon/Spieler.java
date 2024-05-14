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
package pokemon;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import pokemon.world.Item;
import utils.Dictionary;
import utils.IO;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 22.07.2016
 */
public class Spieler extends Trainer
{

    private final Dictionary<Item, Integer> ITEMS = new Dictionary<>();

    /**
     *
     */
    public Spieler()
    {
        super("Spieler");
    }

    @Override
    public boolean givePokemon(Pokemon pok)
    {
        if (super.givePokemon(pok))
        {
            IO.println(this + " gets " + pok, IO.MessageType.DEBUG);
            return true;
        }
        IO.println(this + " cannot take any more Pokemons.", IO.MessageType.DEBUG);
        return false;
    }

    //TODO implement
    /**
     * Adds the given number of Items of the given type.
     *
     * @param item
     * @param number
     */
    public void addItem(Item item, int number)
    {
        if (ITEMS.containsKey(item))
        {
            ITEMS.set(item, ITEMS.get(item) + number);
        }
        else
        {
            ITEMS.add(item, number);
        }
    }

    public void addItem(Item item)
    {
        addItem(item, 1);
    }

    public Item getItem(int index)
    {
        return ITEMS.getKey(index);
    }

    public String[] getItemStrings()
    {
        String[] strings = new String[ITEMS.size()];
        for (int i = 0; i < strings.length; i++)
        {
            strings[i] = ITEMS.getElement(i) + "x " + ITEMS.getKey(i).toString();
        }
        return strings;
    }

    public void removeItem(int index)
    {
        removeItem(index, 1);
    }

    public void removeItem(int index, int n)
    {
        Item key = ITEMS.getKey(index);
        if (ITEMS.get(key) > n)
        {
            ITEMS.set(key, ITEMS.get(key) - n);
        }
        else
        {
            ITEMS.remove(key);
        }
    }

    public boolean hasItem(Item i)
    {
        return ITEMS.containsKey(i);
    }

    public boolean hasItem(Item i, int number)
    {
        return ITEMS.containsKey(i) && ITEMS.get(i) >= number;
    }

    @Override
    public void setName(String nameNeu)
    {
        super.setName(nameNeu);
        IO.setTranslation("PLAYER", nameNeu);
    }

    /**
     *
     * @return
     */
    @Override
    public Dictionary<String, Object> getAttributes()
    {
        Dictionary<String, Object> values = new Dictionary<>();
        values.addAll(super.getAttributes());
        for (Field f : Spieler.class.getDeclaredFields())
        {
            try
            {
                values.add(f.getName(), f.get(this));
            } catch (IllegalArgumentException ex)
            {
                Logger.getLogger(Spieler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex)
            {
                Logger.getLogger(Spieler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return values;
    }
}
