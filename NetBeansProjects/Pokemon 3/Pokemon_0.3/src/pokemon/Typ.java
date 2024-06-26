/*
 * Copyright (C) 2015 Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
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
package pokemon;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public enum Typ {

    /**
     *
     */
    NORMAL(0),

    /**
     *
     */
    FEUER(1),

    /**
     *
     */
    WASSER(2),

    /**
     *
     */
    PFLANZE(3),

    /**
     *
     */
    EIS(4),

    /**
     *
     */
    GEIST(5),

    /**
     *
     */
    ELEKTRO(6),

    /**
     *
     */
    GESTEIN(7),

    /**
     *
     */
    BODEN(7),

    /**
     *
     */
    KÄFER(8),

    /**
     *
     */
    GIFT(8),

    /**
     *
     */
    PSYCHO(9),

    /**
     *
     */
    DRACHE(10),

    /**
     *
     */
    FLUG(10),

    /**
     *
     */
    KAMPF(11),

    /**
     *
     */
    METALL(12),

    /**
     *
     */
    UNLICHT(13);
  
    /**
     *
     */
    public final int INDEX;
  
  Typ(int i){
    INDEX=i;
  }
}