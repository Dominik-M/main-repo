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
package utils;

import java.lang.reflect.Field;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 14.03.2016
 * @param <K>
 * @param <E>
 */
public class Dictionary<K extends Object, E extends Object> extends SerializableReflectObject {

    private final java.util.LinkedList<K> keys;
    private final java.util.LinkedList<E> elements;

    /**
     *
     */
    public Dictionary() {
        keys = new java.util.LinkedList<>();
        elements = new java.util.LinkedList<>();
    }

    /**
     *
     * @param key
     * @return
     */
    public boolean containsKey(K key) {
        for (K k : keys) {
            if (k.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param key
     * @return
     */
    public E get(K key) {
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).equals(key)) {
                return elements.get(i);
            }
        }
        return null;
    }

    /**
     *
     * @param key
     * @param element
     * @return
     */
    public boolean add(K key, E element) {
        if (!containsKey(key)) {
            keys.add(key);
            elements.add(element);
            return true;
        }
        return false;
    }

    /**
     *
     * @param values
     * @return
     */
    public int addAll(Dictionary<K, E> values) {
        int count = 0;
        for (int i = 0; i < values.size(); i++) {
            if (add(values.keys.get(i), values.elements.get(i))) {
                count++;
            }
        }
        return count;
    }

    public void clear() {
        keys.clear();
        elements.clear();
    }

    /**
     *
     * @return
     */
    public int size() {
        return keys.size();
    }

    /**
     *
     * @param key
     * @return
     */
    public E remove(K key) {
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).equals(key)) {
                E removed = elements.remove(i);
                keys.remove(i);
                return removed;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public java.util.LinkedList<K> getKeys() {
        return new java.util.LinkedList<>(keys);
    }

    /**
     *
     * @return
     */
    public java.util.LinkedList<E> getElements() {
        return new java.util.LinkedList<>(elements);
    }

    /**
     *
     * @return
     */
    @Override
    public Dictionary<String, Object> getAttributes() {
        Dictionary<String, Object> values = new Dictionary<>();
        for (Field f : Dictionary.class.getDeclaredFields()) {
            try {
                values.add(f.getName(), f.get(this));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                IO.printException(ex);
            }
        }
        for (K key : keys) {
            values.add(key.toString(), get(key));
        }
        return values;
    }
}
