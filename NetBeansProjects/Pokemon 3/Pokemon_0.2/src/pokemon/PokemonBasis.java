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

import pokemon.attacks.Attack.AttackBase;
import utils.Constants;
import utils.Text;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 29.08.2016
 */
public enum PokemonBasis
{

    BISASAM(Text.BISASAM, Typ.PFLANZE, Typ.GIFT, Constants.XP_NORMAL, Ability.NOTDÜNGER, 45, 49, 49, 65, 65, 45, 70, 6900, new int[]
    {
        2, 3
    }, new AttackBase[]
    {
        AttackBase.TACKLE, AttackBase.HEULER
    }),
    BISAKNOSP(Text.BISAKNOSP, Typ.PFLANZE, Typ.GIFT, Constants.XP_NORMAL, Ability.NOTDÜNGER, 45, 49, 49, 65, 65, 45, 70, 6900, new int[]
    {
    }, new AttackBase[]
    {
    }),
    BISAFLOR(Text.BISAFLOR, Typ.PFLANZE, Typ.GIFT, Constants.XP_NORMAL, Ability.NOTDÜNGER, 45, 49, 49, 65, 65, 45, 70, 6900, new int[]
    {
    }, new AttackBase[]
    {
    }),
    GLUMANDA(Text.GLUMANDA, Typ.FEUER, Typ.NORMAL, Constants.XP_NORMAL, Ability.FEUERPANZER, 39, 52, 43, 60, 50, 65, 60, 8500, new int[]
    {
        1, 1, 7
    }, new AttackBase[]
    {
        AttackBase.KRATZER, AttackBase.HEULER, AttackBase.GLUT
    });
    //TODO add Pokemons

    public final int ID, DELTAXP;
    private final Text NAME;    // originalname
    private Typ typ1, typ2;
    private int kp;
    private final int[] BASE_STATS; // Basiswerte
    final int[] learnLvls;
    final AttackBase[] learnAtts;
    public final int SIZE, WEIGHT; // Größe in cm und Gewicht in g.
    public final Ability ABILITY;

    private PokemonBasis(Text n, Typ t1, Typ t2, int benxp, Ability ability, int bkp, int batk, int bvert, int bsatk, int bsvert, int binit, int size, int weight, int[] learnLvls, AttackBase[] learnAtts)
    {
        NAME = n;
        ID = this.ordinal();
        typ1 = t1;
        typ2 = t2;
        BASE_STATS = new int[]
        {
            batk, bvert, bsatk, bsvert, binit
        };
        kp = bkp;
        DELTAXP = benxp;
        this.learnLvls = learnLvls;
        this.learnAtts = learnAtts;
        SIZE = size;
        WEIGHT = weight;
        ABILITY = ability;
    }

    /**
     *
     * @return
     */
    public String getBaseName()
    {
        return NAME.toString();
    }

    /**
     *
     * @return
     */
    public Typ getTyp1()
    {
        return typ1;
    }

    /**
     *
     * @param typ1Neu
     */
    public void setTyp1(Typ typ1Neu)
    {
        typ1 = typ1Neu;
    }

    /**
     *
     * @return
     */
    public Typ getTyp2()
    {
        return typ2;
    }

    /**
     *
     * @param typ2Neu
     */
    public void setTyp2(Typ typ2Neu)
    {
        typ2 = typ2Neu;
    }

    /**
     *
     * @return
     */
    public int getBaseKp()
    {
        return kp;
    }

    private void setKp(int kpNeu)
    {
        kp = kpNeu;
    }

    /**
     *
     * @param val a StateValue
     * @return
     */
    public int getStateValue(Pokemon.Stat val)
    {
        return BASE_STATS[val.ordinal()];
    }

    /**
     *
     * @param val a StateValue
     * @return
     */
    void setStateValue(Pokemon.Stat val, int value)
    {
        BASE_STATS[val.ordinal()] = value;
    }
}
