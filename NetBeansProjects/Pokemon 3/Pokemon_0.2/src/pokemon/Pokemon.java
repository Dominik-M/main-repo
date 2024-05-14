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
import utils.Constants;
import utils.Dictionary;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Pokemon extends utils.SerializableReflectObject
{

    public enum Stat
    {

        ATK,
        VERT,
        SPEZ_ATK,
        SPEZ_VERT,
        INIT;

        @Override
        public String toString()
        {
            return name();
        }
    }

    private enum ValueFactor
    {

        DEC_6(25),
        DEC_5(29),
        DEC_4(33),
        DEC_3(40),
        DEC_2(50),
        DEC_1(67),
        NORMAL(100),
        INC_1(150),
        INC_2(200),
        INC_3(250),
        INC_4(300),
        INC_5(350),
        INC_6(400);
        final int PERCENT;

        ValueFactor(int percent)
        {
            PERCENT = percent;
        }

        ValueFactor boost(int steps)
        {
            int index = this.ordinal() + steps;
            if (index <= 0)
            {
                return DEC_6;
            }
            else if (index >= INC_6.ordinal())
            {
                return INC_6;
            }
            else
            {
                return values()[index];
            }
        }

        int calc(int base)
        {
            return PERCENT * base / 100;
        }

        @Override
        public String toString()
        {
            return PERCENT + "%";
        }
    }

    private PokemonBasis basis;
    private String name;    // Spitzname
    private int lvl, xp, benXp, maxKp, kp;
    private final int[] STATS = new int[Stat.values().length];
    private final int[] DVs = new int[Stat.values().length];
    private final int[] FPs = new int[Stat.values().length];
    private ValueFactor[] boosts;
    private ValueFactor gena, flu;
    private boolean wild;
    private Trainer OT;
    private Status status;
    private final Attack[] attacks;
    private final java.util.LinkedList<Attack> allAtts = new java.util.LinkedList<>();
    private Wesen wesen;

    /**
     *
     * @param pok
     * @param lvl
     */
    public Pokemon(PokemonBasis pok, int lvl)
    {
        basis = pok;
        attacks = new Attack[Constants.MAX_ATTACK_COUNT];
        init();
        for (int i = 0; i < lvl; i++)
        {
            lvlUp();
        }
    }

    /**
     *
     * @param pokID
     * @param lvl
     */
    public Pokemon(int pokID, int lvl)
    {
        this(PokemonBasis.values()[pokID], lvl);
    }

    private void init()
    {
        name = basis.getBaseName();
        this.lvl = 0;
        xp = 0;
        benXp = basis.DELTAXP;
        maxKp = basis.getBaseKp();
        kp = maxKp;
        boosts = new ValueFactor[]
        {
            ValueFactor.NORMAL, ValueFactor.NORMAL, ValueFactor.NORMAL, ValueFactor.NORMAL, ValueFactor.NORMAL
        };
        gena = ValueFactor.NORMAL;
        flu = ValueFactor.NORMAL;
        wild = true;
        OT = null;
        status = Status.OK;
        for (int i = 0; i < DVs.length; i++)
        {
            DVs[i] = (int) ((Constants.DV_MAX - Constants.DV_MIN) * Math.random()) + Constants.DV_MIN;
        }
        for (int i = 0; i < FPs.length; i++)
        {
            FPs[i] = 0;
        }
        wesen = Wesen.gibZufallWesen();
    }

    /**
     *
     */
    public final void lvlUp()
    {
        xp = 0;
        benXp += basis.DELTAXP;
        lvl++;
        for (int i = 0; i < basis.learnLvls.length; i++)
        {
            if (basis.learnLvls[i] == lvl)
            {
                learnAttack(new Attack(basis.learnAtts[i]));
            }
        }
        calculateStats();
    }

    private void calculateStats()
    {
        for (Stat stat : Stat.values())
        {
            setStateValue(stat, (int) (((2.0 * basis.getStateValue(stat) + DVs[stat.ordinal()] + FPs[stat.ordinal()] / 4.0) * lvl / 100.0 + 5) * wesen.getFactor(stat)));
            // Für das Wesen muss bei einer positiven Auswirkung auf den Statuswert 1,1 und bei einer negativen Auswirkung 0,9 eingesetzt werden, sonst 1
        }
        double part = 1.0 * kp / maxKp;
        maxKp = (int) ((2 * basis.getBaseKp() + 100) * lvl / 100.0 + 10);
        kp = (int) (maxKp * part);
    }

    public int addFP(Stat stat, int n)
    {
        if (getTotalFP() + n > Constants.FP_MAX_TOTAL)
        {
            n = Constants.FP_MAX_TOTAL - getTotalFP();
        }
        if (getFP(stat) + n > Constants.FP_MAX_STAT)
        {
            n = Constants.FP_MAX_STAT - getFP(stat);
        }
        setFP(stat, getFP(stat) + n);
        return n;
    }

    private void setFP(Stat stat, int n)
    {
        if (stat != null)
        {
            FPs[stat.ordinal()] = n;
        }
    }

    public int getFP(Stat stat)
    {
        if (stat != null)
        {
            return FPs[stat.ordinal()];
        }
        else
        {
            return 0;
        }
    }

    public int getTotalFP()
    {
        int n = 0;
        for (Stat s : Stat.values())
        {
            n += getFP(s);
        }
        return n;
    }

    public int getDV(Stat stat)
    {
        if (stat != null)
        {
            return DVs[stat.ordinal()];
        }
        else
        {
            return 0;
        }
    }

    public int getTotalDV()
    {
        int n = 0;
        for (Stat s : Stat.values())
        {
            n += getDV(s);
        }
        return n;
    }

    @Override

    public String toString()
    {
        return name;
    }

    /**
     *
     * @param nameNeu
     */
    public void setName(String nameNeu)
    {
        name = nameNeu;
    }

    /**
     *
     * @return
     */
    public PokemonBasis getBasis()
    {
        return basis;
    }

    /**
     *
     * @return
     */
    public int getLvl()
    {
        return lvl;
    }

    private void setLvl(int lvlNeu)
    {
        lvl = lvlNeu;
    }

    /**
     *
     * @return
     */
    public int getXp()
    {
        return xp;
    }

    /**
     *
     * @param xpNeu
     */
    public void addXp(int xpNeu)
    {
        while (xp + xpNeu >= benXp)
        {
            xpNeu -= (benXp - xp);
            lvlUp();
        }
        xp += xpNeu;
    }

    /**
     *
     * @return
     */
    public int getBenXp()
    {
        return benXp;
    }

    /**
     *
     * @param benXpNeu
     */
    public void setBenXp(int benXpNeu)
    {
        benXp = benXpNeu;
    }

    /**
     *
     * @return
     */
    public int getMaxKp()
    {
        return maxKp;
    }

    /**
     *
     * @param maxKpNeu
     */
    public void setMaxKp(int maxKpNeu)
    {
        maxKp = maxKpNeu;
    }

    /**
     *
     * @return
     */
    public int getKp()
    {
        return kp;
    }

    /**
     *
     * @param val a StateValue
     * @return
     */
    public int getStateValue(Stat val)
    {
        return boosts[val.ordinal()].calc(STATS[val.ordinal()]);
    }

    /**
     *
     * @param val a StateValue
     * @return
     */
    private void setStateValue(Stat val, int value)
    {
        STATS[val.ordinal()] = value;
    }

    /**
     *
     * @return
     */
    public Status getStatus()
    {
        return status;
    }

    /**
     *
     * @param statusNeu
     */
    public void setStatus(Status statusNeu)
    {
        status = statusNeu;
    }

    /**
     *
     * @return
     */
    public boolean isWild()
    {
        return wild;
    }

    /**
     *
     */
    public void freilasen()
    {
        wild = true;
    }

    /**
     *
     * @param t
     * @param b
     */
    public void gefangen(Trainer t, boolean b)
    {
        wild = false;
        if (OT == null)
        {
            OT = t;
        }
    }

    /**
     *
     * @param dmg
     * @return
     */
    public int damage(int dmg)
    {
        if (dmg >= kp)
        {
            kp = 0;
            status = Status.BSG;
            return kp;
        }
        else
        {
            kp -= dmg;
            return dmg;
        }
    }

    public int getGena()
    {
        return gena.PERCENT;
    }

    public int getFlu()
    {
        return flu.PERCENT;
    }

    /**
     *
     * @return
     */
    public boolean isBsg()
    {
        return kp <= 0;
    }

    /**
     *
     * @return
     */
    public boolean isOK()
    {
        return kp == maxKp && status == Status.OK;
    }

    /**
     * Completely heals this Pokemon. Sets KP and AP values to maximum AND set
     * status to Status.OK.
     */
    public void heal()
    {
        kp = maxKp;
        setStatus(Status.OK);
        for (Attack a : attacks)
        {
            if (a != null)
            {
                a.setAP(a.BASE.AP);
            }
        }
    }

    public void resetBoosts()
    {
        for (int i = 0; i < boosts.length; i++)
        {
            boosts[i] = ValueFactor.NORMAL;
        }
        gena = ValueFactor.NORMAL;
        flu = ValueFactor.NORMAL;
    }

    public boolean boostStateValue(Stat val, int steps)
    {
        ValueFactor old = boosts[val.ordinal()];
        boosts[val.ordinal()] = old.boost(steps);
        return old != boosts[val.ordinal()];
    }

    /**
     *
     * @return
     */
    public Attack[] getAtacks()
    {
        return attacks;
    }

    /**
     * Get an attack of this Pokemon by index. The Index must be greater or
     * equal 0 and less than Constants.MAX_ATTACKS_COUNT (=4 by default)
     *
     * @param index Index of the Attack in the array.
     * @return Attack at the given index or null if index out of bounds.
     */
    public Attack getAttack(int index)
    {
        if (index < attacks.length && index >= 0)
        {
            return attacks[index];
        }
        return null;
    }

    public boolean learnAttack(Attack a)
    {
        for (int i = 0; i < attacks.length; i++)
        {
            if (attacks[i] == null)
            {
                attacks[i] = a;
                return true;
            }
        }
        allAtts.add(a);
        return false;
    }

    /**
     * private PokemonBasis basis; private String name; // Spitzname private int
     * lvl, xp, benXp, maxKp, kp; private final int[] STATS = new
     * int[Stat.values().length]; private final int[] DVs = new
     * int[Stat.values().length]; private final int[] FPs = new
     * int[Stat.values().length]; private ValueFactor[] boosts; private
     * ValueFactor gena, flu; private boolean wild; private Trainer OT; private
     * Status status; private final Attack[] attacks; private Wesen wesen;
     *
     * @return
     */
    @Override
    public Dictionary<String, Object> getAttributes()
    {
        Dictionary<String, Object> values = new Dictionary<>();
        values.add("basis", basis);
        values.add("basename", basis.getBaseName());
        values.add("ID", basis.ID);
        values.add("typ1", basis.getTyp1());
        values.add("typ2", basis.getTyp2());
        values.add("ability", basis.ABILITY);
        values.add("size", basis.SIZE);
        values.add("weight", basis.WEIGHT);
        values.add("deltaXP", basis.DELTAXP);
        values.add("baseKP", basis.getBaseKp());
        for (Stat s : Stat.values())
        {
            values.add("base" + s.name(), basis.getStateValue(s));
        }
        for (int i = 0; i < basis.learnAtts.length; i++)
        {
            values.add("learnAtts[" + i + "]", basis.learnAtts[i]);
            values.add("learnLvls[" + i + "]", basis.learnLvls[i]);
        }
        values.add("name", name);
        values.add("lvl", lvl);
        values.add("xp", xp);
        values.add("benXp", benXp);
        values.add("wild", wild);
        values.add("OT", OT);
        values.add("wesen", wesen);
        values.add("status", status);
        for (int i = 0; i < attacks.length; i++)
        {
            values.add("attacks[" + i + "]", attacks[i]);
        }
        for (int i = 0; i < allAtts.size(); i++)
        {
            values.add("allAtts[" + i + "]", allAtts.get(i));
        }
        values.add("kp", kp);
        values.add("maxKp", maxKp);
        for (Stat s : Stat.values())
        {
            values.add(s.name(), getStateValue(s));
        }
        values.add("gena", gena);
        values.add("flu", flu);
        for (Stat s : Stat.values())
        {
            values.add(s.name() + "_factor", boosts[s.ordinal()]);
        }
        values.add("totalDV", getTotalDV());
        for (Stat s : Stat.values())
        {
            values.add(s.name() + "_DV", getDV(s));
        }
        values.add("totalFP", getTotalFP());
        for (Stat s : Stat.values())
        {
            values.add(s.name() + "_FP", getFP(s));
        }
        return values;
    }
}
