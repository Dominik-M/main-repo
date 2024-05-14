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
     *
     * @param item
     */
    public void addItem(Item item)
    {
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
