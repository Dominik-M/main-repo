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
import utils.DynText;
import utils.IO;

/**
 *
 * @author Dominik
 */
public class Sign extends Objekt
{

    private final String text;
    private final DynText dText;

    /**
     *
     * @param txt Text on the sign
     */
    public Sign(String txt)
    {
        super(ImageFile.SCHILD, false);
        text = txt;
        dText = null;
    }

    /**
     *
     * @param txt Text on the sign, dynamic.
     */
    public Sign(DynText txt)
    {
        super(ImageFile.SCHILD, false);
        text = null;
        dText = txt;
    }

    /**
     *
     */
    @Override
    public void benutzt()
    {
        if (dText != null)
        {
            IO.println(dText.toString(), IO.MessageType.IMPORTANT);
        }
        else
        {
            IO.println(text, IO.MessageType.IMPORTANT);
        }
    }
}
