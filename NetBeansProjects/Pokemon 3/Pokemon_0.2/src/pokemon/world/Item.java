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
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Dictionary;

/**
 *
 * @author Dominik
 */
public class Item extends utils.SerializableReflectObject {
//TODO implement Items

    @Override
    public Dictionary<String, Object> getAttributes() {
        Dictionary<String, Object> values = new Dictionary<>();
        for (Field f : this.getClass().getDeclaredFields()) {
            try {
                if (!Modifier.isStatic(f.getModifiers())) {
                    values.add(f.getName(), f.get(this));
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return values;
    }
}
