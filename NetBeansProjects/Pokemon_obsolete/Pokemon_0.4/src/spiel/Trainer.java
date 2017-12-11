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
package spiel;

import pokemon.Pokemon;

/**
 *
 * @author Dominik
 */
public class Trainer implements java.io.Serializable {

    private int direction;
    private int x, y;
    private final Pokemon[] pokemon = new Pokemon[Konstanten.POKE_ANZ];
    private int geld;
    private String name;

    public Trainer(String n, Pokemon... poks) {
        setPos(x, y);
        direction = InputListener.INVALID;
        geld = 0;
        name = n;
        setPoks(poks);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public final void setPos(int xNeu, int yNeu) {
        x = xNeu;
        y = yNeu;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int directionNeu) {
        direction = directionNeu;
    }

    public int getGeld() {
        return geld;
    }

    public void setGeld(int geldNeu) {
        geld = geldNeu;
    }

    public String getName() {
        return name;
    }

    public void setName(String nameNeu) {
        name = nameNeu;
    }

    public Pokemon[] getPoks() {
        return pokemon;
    }

    public final void setPoks(Pokemon[] poks) {
        for (int i = 0; i < pokemon.length; i++) {
            if (i >= poks.length) {
                pokemon[i] = null;
            } else {
                pokemon[i] = poks[i];
            }
        }
    }

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

    public void tausche(int i, int j) {
        Pokemon merk = pokemon[i];
        pokemon[i] = pokemon[j];
        pokemon[j] = merk;
    }

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
}
