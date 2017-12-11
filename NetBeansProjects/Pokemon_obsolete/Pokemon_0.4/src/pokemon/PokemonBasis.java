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

import language.Text;
import spiel.Konstanten;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public enum PokemonBasis {

    BISASAM(Text.BISASAM, Typ.PFLANZE, Typ.GIFT, Konstanten.XP_NORMAL, 45, 49, 49, 65, 65, 45, 70, 6900),
    BISAKNOSP(Text.BISAKNOSP, Typ.PFLANZE, Typ.GIFT, Konstanten.XP_NORMAL, 45, 49, 49, 65, 65, 45, 70, 6900),
    BISAFLOR(Text.BISAFLOR, Typ.PFLANZE, Typ.GIFT, Konstanten.XP_NORMAL, 45, 49, 49, 65, 65, 45, 70, 6900);

    public final int ID, DELTAXP;
    private Text name;    // originalname
    private Typ typ1, typ2;
    private int kp, atk, vert, spezAtk, spezVert, init; // Basiswerte
    public final int SIZE, WEIGHT; // Größe in cm und Gewicht in g.

    private PokemonBasis(Text n, Typ t1, Typ t2, int bkp, int batk, int bvert, int bsatk, int bsvert, int binit, int benxp, int size, int weight) {
        name = n;
        ID = this.ordinal();
        typ1 = t1;
        typ2 = t2;
        kp = bkp;
        atk = batk;
        vert = bvert;
        spezAtk = bsatk;
        spezVert = bsvert;
        init = binit;
        DELTAXP = benxp;
        SIZE = size;
        WEIGHT = weight;
    }

    public String getBaseName() {
        return name.toString();
    }

    private void setName(Text nameNeu) {
        name = nameNeu;
    }

    public Typ getTyp1() {
        return typ1;
    }

    public void setTyp1(Typ typ1Neu) {
        typ1 = typ1Neu;
    }

    public Typ getTyp2() {
        return typ2;
    }

    public void setTyp2(Typ typ2Neu) {
        typ2 = typ2Neu;
    }

    public int getBaseKp() {
        return kp;
    }

    private void setKp(int kpNeu) {
        kp = kpNeu;
    }

    public int getBaseAtk() {
        return atk;
    }

    private void setAtk(int atkNeu) {
        atk = atkNeu;
    }

    public int getBaseVert() {
        return vert;
    }

    private void setVert(int vertNeu) {
        vert = vertNeu;
    }

    public int getBaseSpezAtk() {
        return spezAtk;
    }

    private void setSpezAtk(int spezAtkNeu) {
        spezAtk = spezAtkNeu;
    }

    public int getBaseSpezVert() {
        return spezVert;
    }

    private void setSpezVert(int spezVertNeu) {
        spezVert = spezVertNeu;
    }

    public int getBaseInit() {
        return init;
    }

    private void setInit(int initNeu) {
        init = initNeu;
    }
}
