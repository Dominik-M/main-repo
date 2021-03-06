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
import utils.IO;

/**
 *
 * @author Dominik
 */
public class Water extends Objekt
{

    /**
     *
     * @param img
     */
    public Water(ImageFile img)
    {
        super(img, false);
    }

    /**
     *
     */
    @Override
    public void benutzt()
    {
        //TODO surfing?surf:fish
        IO.println("Ein ruhiges Gewässer. Hier kann man bestimmt gut Schwimmen oder Fischen.", IO.MessageType.IMPORTANT);
    }

    /**
     *
     */
    @Override
    public void betreten()
    {
        //TODO start fight
    }
}
