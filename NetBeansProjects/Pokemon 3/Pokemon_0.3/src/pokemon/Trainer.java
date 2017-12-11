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
package pokemon;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Constants;
import utils.Dictionary;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 * Created 28.07.2016
 */
public class Trainer extends utils.SerializableReflectObject {

    private int direction;
    private int x, y;
    private final Pokemon[] pokemon = new Pokemon[Constants.POKE_ANZ];
    private int geld;
    private String name;

    /**
     *
     * @param x
     * @param y
     * @param direction
     * @param geld
     * @param name
     * @param poks
     */
    public Trainer(int x, int y, int direction, int geld, String name, Pokemon... poks) {
        setPos(x, y);
        this.direction = direction;
        this.geld = geld;
        this.name = name;
        setPoks(poks);
    }

    /**
     *
     * @param n
     * @param poks
     */
    public Trainer(String n, Pokemon... poks) {
        this(0, 0, Constants.DIRECTION_INVALID, 0, n, poks);
    }

    /**
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @param xNeu
     * @param yNeu
     */
    public final void setPos(int xNeu, int yNeu) {
        x = xNeu;
        y = yNeu;
    }

    /**
     *
     * @return
     */
    public int getDirection() {
        return direction;
    }

    /**
     *
     * @param directionNeu
     */
    public void setDirection(int directionNeu) {
        direction = directionNeu;
    }

    /**
     *
     * @return
     */
    public int getGeld() {
        return geld;
    }

    /**
     *
     * @param geldNeu
     */
    public void setGeld(int geldNeu) {
        geld = geldNeu;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param nameNeu
     */
    public void setName(String nameNeu) {
        name = nameNeu;
    }

    /**
     *
     * @return
     */
    public Pokemon[] getPoks() {
        return pokemon;
    }

    /**
     *
     * @param poks
     */
    public final void setPoks(Pokemon[] poks) {
        for (int i = 0; i < pokemon.length; i++) {
            pokemon[i] = null;
        }
        for (int i = 0; i < pokemon.length; i++) {
            if (i < poks.length) {
                givePokemon(poks[i]);
            } else {
                break;
            }
        }
    }

    /**
     *
     * @param pok
     * @return
     */
    public boolean givePokemon(Pokemon pok) {
        for (int i = 0; i < pokemon.length; i++) {
            if (pokemon[i] == null) {
                pokemon[i] = pok;
                pok.gefangen(this, false);
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param index
     * @return
     */
    public boolean removePok(int index) {
        int anz = 0;
        for (int i = 0; i < pokemon.length; i++) {
            if (pokemon[i] == null) {
                continue;
            }
            anz++;
        }
        if (anz <= 1) {
            return false;
        }
        pokemon[index].freilasen();
        pokemon[index] = null;
        for (int i = index; i < pokemon.length - 1; i++) {
            tausche(i, i + 1);
        }
        return true;
    }

    /**
     *
     * @param i
     * @param j
     */
    public void tausche(int i, int j) {
        Pokemon merk = pokemon[i];
        pokemon[i] = pokemon[j];
        pokemon[j] = merk;
    }

    public Pokemon getFirst() {
        for (Pokemon pokemon1 : pokemon) {
            if (pokemon1 != null && !pokemon1.isBsg()) {
                return pokemon1;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public boolean isDefeated() {
        for (Pokemon pok : pokemon) {
            if (pok == null) {
                continue;
            }
            if (!pok.isBsg()) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return
     */
    public boolean isOK() {
        for (Pokemon pok : pokemon) {
            if (pok == null) {
                continue;
            } else if (!pok.isOK()) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     */
    public void heal() {
        for (Pokemon pok : pokemon) {
            if (pok != null) {
                pok.heal();
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     *
     * @return
     */
    @Override
    public Dictionary<String, Object> getAttributes() {
        Dictionary<String, Object> values = new Dictionary<>();
        for (Field f : Trainer.class.getDeclaredFields()) {
            try {
                values.add(f.getName(), f.get(this));
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < pokemon.length; i++) {
            if (pokemon[i] == null) {
                values.add("pokemon[" + i + "]", "NULL");
            } else {
                values.add("pokemon[" + i + "]", pokemon[i]);
            }
        }
        return values;
    }
}
