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
import utils.Text;

/**
 *
 * @author Dominik
 */
public class Sign extends Objekt {

    private final Text text;
    private final String sText;

    /**
     *
     * @param txt
     */
    public Sign(Text txt) {
        super(ImageFile.SCHILD, false);
        text = txt;
        sText = null;
    }

    /**
     *
     * @param txt
     */
    public Sign(String txt) {
        super(ImageFile.SCHILD, false);
        text = null;
        sText = txt;
    }

    /**
     *
     */
    @Override
    public void benutzt() {
        if (sText == null) {
            IO.println(text.toString(), IO.MessageType.IMPORTANT);
        } else {
            IO.println(sText, IO.MessageType.IMPORTANT);
        }
    }
}
