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
import utils.IO;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 29.08.2016
 */
public enum PokemonBasis
{

    BISASAM(Typ.PFLANZE, Typ.GIFT, Constants.XP_NORMAL, Ability.NOTDÜNGER, 45, 49, 49, 65, 65, 45, 70, 6900, new int[]
    {
        2, 3
    }, new AttackBase[]
    {
        AttackBase.TACKLE, AttackBase.HEULER
    }, 16),
    BISAKNOSP(Typ.PFLANZE, Typ.GIFT, Constants.XP_HIGH, Ability.NOTDÜNGER, 60, 62, 63, 80, 80, 60, 100, 13000, new int[]
    {
        1, 1
    }, new AttackBase[]
    {
        AttackBase.TACKLE, AttackBase.HEULER
    }, 32),
    BISAFLOR(Typ.PFLANZE, Typ.GIFT, Constants.XP_VERYHGH, Ability.NOTDÜNGER, 80, 82, 83, 100, 100, 80, 200, 100000, new int[]
    {
        1, 1
    }, new AttackBase[]
    {
        AttackBase.TACKLE, AttackBase.HEULER
    }),
    GLUMANDA(Typ.FEUER, Typ.NORMAL, Constants.XP_NORMAL, Ability.FEUERPANZER, 39, 52, 43, 60, 50, 65, 60, 8500, new int[]
    {
        1, 1, 6
    }, new AttackBase[]
    {
        AttackBase.KRATZER, AttackBase.HEULER, AttackBase.GLUT
    }, 16),
    GLUTEXO(Typ.FEUER, Typ.NORMAL, Constants.XP_HIGH, Ability.FEUERPANZER, 58, 64, 58, 80, 65, 80, 110, 19000, new int[]
    {
        1, 1, 1
    }, new AttackBase[]
    {
        AttackBase.KRATZER, AttackBase.HEULER, AttackBase.GLUT
    }, 36),
    GLURAK(Typ.FEUER, Typ.FLUG, Constants.XP_VERYHGH, Ability.FEUERPANZER, 78, 84, 78, 109, 85, 100, 170, 90500, new int[]
    {
        1, 1, 1
    }, new AttackBase[]
    {
        AttackBase.KRATZER, AttackBase.HEULER, AttackBase.GLUT
    }),
    SCHIGGY(Typ.WASSER, Typ.NORMAL, Constants.XP_NORMAL, Ability.AQUAHÜLLE, 44, 48, 65, 50, 64, 43, 50, 9000, new int[]
    {
        1, 4, 7
    }, new AttackBase[]
    {
        AttackBase.TACKLE, AttackBase.RUTENSCHLAG, AttackBase.BLUBBER
    }, 16),
    SCHILLOK(Typ.WASSER, Typ.NORMAL, Constants.XP_HIGH, Ability.AQUAHÜLLE, 59, 63, 80, 65, 80, 58, 100, 22500, new int[]
    {
        1, 1, 1
    }, new AttackBase[]
    {
        AttackBase.TACKLE, AttackBase.RUTENSCHLAG, AttackBase.BLUBBER
    }, 36),
    TURTOK(Typ.WASSER, Typ.NORMAL, Constants.XP_VERYHGH, Ability.AQUAHÜLLE, 79, 83, 100, 85, 105, 78, 160, 85500, new int[]
    {
        1, 1, 1
    }, new AttackBase[]
    {
        AttackBase.TACKLE, AttackBase.RUTENSCHLAG, AttackBase.BLUBBER
    });
    //TODO add Pokemons

    public final int ID, DELTAXP;
    private Typ typ1, typ2;
    private final int[] BASE_STATS; // Basiswerte
    final int[] learnLvls;
    final AttackBase[] learnAtts;
    public final int SIZE, WEIGHT; // Größe in cm und Gewicht in g.
    public final Ability ABILITY;
    public final int EVO_LVL;

    private PokemonBasis(Typ t1, Typ t2, int benxp, Ability ability, int bkp, int batk, int bvert, int bsatk, int bsvert, int binit, int size, int weight, int[] learnLvls, AttackBase[] learnAtts)
    {
        this(t1, t2, benxp, ability, bkp, batk, bvert, bsatk, bsvert, binit, size, weight, learnLvls, learnAtts, -1);
    }

    private PokemonBasis(Typ t1, Typ t2, int benxp, Ability ability, int bkp, int batk, int bvert, int bsatk, int bsvert, int binit, int size, int weight, int[] learnLvls, AttackBase[] learnAtts, int evolvl)
    {
        ID = this.ordinal();
        typ1 = t1;
        typ2 = t2;
        BASE_STATS = new int[]
        {
            bkp, batk, bvert, bsatk, bsvert, binit
        };
        DELTAXP = benxp;
        this.learnLvls = learnLvls;
        this.learnAtts = learnAtts;
        SIZE = size;
        WEIGHT = weight;
        ABILITY = ability;
        EVO_LVL = evolvl;
    }

    /**
     *
     * @return
     */
    public String getBaseName()
    {
        return name();
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

    @Override
    public String toString()
    {
        return IO.translate(name());
    }
}
