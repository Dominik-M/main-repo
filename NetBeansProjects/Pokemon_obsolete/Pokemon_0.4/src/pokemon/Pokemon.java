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

import pokemon.attacks.Attack;
import spiel.Trainer;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Pokemon implements java.io.Serializable {

    private PokemonBasis basis;
    private String name;    // Spitzname
    private int lvl, xp, benXp, maxKp, kp;
    private double atk, vert, spezAtk, spezVert, init;
    private boolean wild;
    private Trainer OT;
    private Status status;

    public Pokemon(PokemonBasis pok, int lvl) {
        basis = pok;
        name = basis.getBaseName();
        xp = 0;
        benXp = basis.DELTAXP;
        for (int i = 0; i < lvl; i++) {
            lvlUp();
        }
    }

    public Pokemon(int pokID, int lvl) {
        this(PokemonBasis.values()[pokID], lvl);
    }

    public final void lvlUp() {
        xp = 0;
        benXp += basis.DELTAXP;
        lvl++;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setName(String nameNeu) {
        name = nameNeu;
    }

    public PokemonBasis getBasis() {
        return basis;
    }

    public int getLvl() {
        return lvl;
    }

    private void setLvl(int lvlNeu) {
        lvl = lvlNeu;
    }

    public int getXp() {
        return xp;
    }

    public void addXp(int xpNeu) {
        while (xp + xpNeu >= benXp) {
            lvlUp();
            xpNeu -= (benXp - xp);
        }
        xp += xpNeu;
    }

    public int getBenXp() {
        return benXp;
    }

    public void setBenXp(int benXpNeu) {
        benXp = benXpNeu;
    }

    public int getMaxKp() {
        return maxKp;
    }

    public void setMaxKp(int maxKpNeu) {
        maxKp = maxKpNeu;
    }

    public int getKp() {
        return kp;
    }

    private void setKp(int kpNeu) {
        kp = kpNeu;
    }

    public double getAtk() {
        return atk;
    }

    public void setAtk(double atkNeu) {
        atk = atkNeu;
    }

    public double getVert() {
        return vert;
    }

    public void setVert(double vertNeu) {
        vert = vertNeu;
    }

    public double getSpezAtk() {
        return spezAtk;
    }

    public void setSpezAtk(double spezAtkNeu) {
        spezAtk = spezAtkNeu;
    }

    public double getSpezVert() {
        return spezVert;
    }

    public void setSpezVert(double spezVertNeu) {
        spezVert = spezVertNeu;
    }

    public double getInit() {
        return init;
    }

    public void setInit(double initNeu) {
        init = initNeu;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status statusNeu) {
        status = statusNeu;
    }

    public boolean isWild() {
        return wild;
    }

    public void freilasen() {
        wild = true;
    }

    public void gefangen(Trainer t, boolean b) {
        wild = false;
        if (OT == null) {
            OT = t;
        }
    }

    public int attack(Attack att) {
        return 1;
    }

    public int defend(Attack att, int calcDmg) {
        return 1;
    }

    public boolean damage(int dmg) {
        return kp > 0;
    }

    public boolean isBsg() {
        return kp <= 0;
    }

    public boolean isOK() {
        return kp == maxKp && status == Status.OK;
    }

    public void heal() {
        kp = maxKp;
        setStatus(Status.OK);
    }
}
