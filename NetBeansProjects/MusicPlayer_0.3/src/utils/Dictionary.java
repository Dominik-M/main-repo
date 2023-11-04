/**
 * Copyright (C) 2016 Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com>
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
package utils;

/**
 * 
 * @author Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com> Created 14.03.2016
 */
public class Dictionary<K extends Object, E extends Object> {
	private final java.util.LinkedList<K> keys;
	private final java.util.LinkedList<E> elements;

	public Dictionary() {
		keys = new java.util.LinkedList<K>();
		elements = new java.util.LinkedList<E>();
	}

	public boolean containsKey(K key) {
		for (K k : keys) {
			if (k.equals(key))
				return true;
		}
		return false;
	}

	public E get(K key) {
		for (int i = 0; i < keys.size(); i++)
			if (keys.get(i).equals(key))
				return elements.get(i);
		return null;
	}

	public E get(int i) {
		return elements.get(i);
	}

	public int size() {
		return keys.size();
	}

	public boolean add(K key, E element) {
		if (!containsKey(key)) {
			keys.add(key);
			elements.add(element);
			return true;
		}
		return false;
	}

	public E remove(K key) {
		for (int i = 0; i < keys.size(); i++)
			if (keys.get(i).equals(key)) {
				E removed = elements.remove(i);
				keys.remove(i);
				return removed;
			}
		return null;
	}

	@SuppressWarnings("unchecked")
	public K[] getKeys() {
		return (K[]) keys.toArray();
	}

	public K getKey(int i) {
		return keys.get(i);
	}
}