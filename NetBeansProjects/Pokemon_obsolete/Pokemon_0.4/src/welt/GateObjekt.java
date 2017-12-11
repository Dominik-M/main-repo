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
package welt;

import image.ImageIO;
import spiel.Spielwelt;

/**
 *
 * @author Dominik
 */
public class GateObjekt extends Objekt {

    private final Ort dest;
    private final int destX, destY;

    public GateObjekt(Ort destination, int destX, int destY, ImageIO.ImageFile image) {
        super(image, true);
        this.dest = destination;
        this.destX = destX;
        this.destY = destY;
    }

    @Override
    public void betreten() {
        if (dest != null) {
            Spielwelt.getCurrentWorld().ortwechsel(dest, destX, destY);
        }
    }
}
