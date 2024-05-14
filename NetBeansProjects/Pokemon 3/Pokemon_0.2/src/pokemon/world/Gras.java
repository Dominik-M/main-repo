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
import pokemon.GameData;
import utils.Constants;

/**
 *
 * @author Dominik
 */
public class Gras extends Objekt {

    /**
     *
     */
    public Gras() {
        super(ImageFile.GRAS, true);
    }

    /**
     *
     */
    @Override
    public void betreten() {
        if (utils.Utilities.randomEvent(Constants.WILD_POK_APPEAR_PROPABILITY)) {
            GameData.getCurrentGame().startFight();
        }
    }
}
