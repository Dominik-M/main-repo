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

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 14.09.2016
 */
public class SortedStringTree {

    private SortedStringTree lson, rson;
    private String value;

    public SortedStringTree() {
        this("");
    }

    public SortedStringTree(String s) {
        value = s;
    }

    public boolean add(String s) {
        if (!isNullOrEmpty(s)) {
            if (value.equals(s)) {
                return false;
            } else if (isEmpty()) {
                value = s;
                return true;
            } else if (s.compareTo(value) < 0) {
                if (lson != null) {
                    return lson.add(s);
                } else {
                    lson = new SortedStringTree(s);
                    return true;
                }
            } else {
                if (rson != null) {
                    return rson.add(s);
                } else {
                    rson = new SortedStringTree(s);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean contains(String s) {
        if (isNullOrEmpty(s)) {
            return false;
        } else if (s.equals(value)) {
            return true;
        } else if (s.compareTo(value) < 0) {
            if (lson != null) {
                return lson.contains(s);
            } else {
                return false;
            }
        } else {
            if (rson != null) {
                return rson.contains(s);
            }
            return false;
        }
    }

    public boolean isEnd() {
        return lson == null && rson == null;
    }

    public boolean isEmpty() {
        return isEnd() && isNullOrEmpty(value);
    }

    @Override
    public String toString() {
        if (lson != null && rson != null) {
            return lson.toString() + "," + value + "," + rson.toString();
        } else if (lson != null) {
            return lson.toString() + "," + value;
        } else if (rson != null) {
            return value + "," + rson;
        } else {
            return value;
        }
    }

    public boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }
}
