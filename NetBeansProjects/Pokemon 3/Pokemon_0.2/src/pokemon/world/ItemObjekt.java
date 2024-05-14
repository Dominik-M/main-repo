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

import image.ImageIO.ImageFile;

/**
 *
 * @author Dominik
 */
public class ItemObjekt extends Objekt {

    private Item item;

    /**
     *
     * @param image
     * @param passable
     */
    public ItemObjekt(ImageFile image, boolean passable) {
        super(image, passable);
    }

    /**
     *
     * @param i
     */
    public void setItem(Item i) {
        item = i;
    }

    /**
     *
     * @return
     */
    public Item getItem() {
        return item;
    }

    /**
     *
     */
    @Override
    public void benutzt() {
        if (item != null) {

        }
    }
}
