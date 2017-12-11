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

import java.util.LinkedList;
import pokemon.attacks.Attack;
import utils.Constants;
import utils.Decision;
import utils.DecisionCallback;
import utils.Dictionary;
import utils.IO;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Pokemon extends utils.SerializableReflectObject
{

    public enum Stat
    {
        KP,
        ATK,
        VERT,
        SPEZ_ATK,
        SPEZ_VERT,
        INIT;

        @Override
        public String toString()
        {
            return IO.translate(name());
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
    private int lvl, xp, benXp, kp;
    private final int[] STATS = new int[Stat.values().length];
    private final int[] DVs = new int[Stat.values().length];
    private final int[] FPs = new int[Stat.values().length];
    private ValueFactor[] boosts;
    private ValueFactor gena, flu;
    private boolean wild;
    private Trainer OT;
    private Status status;
    private final Attack[] attacks;
    private Wesen wesen;

    /**
     *
     * @param pok
     * @param lvl
     */
    public Pokemon(PokemonBasis pok, int lvl)
    {
        if (lvl <= 0)
        {
            throw new IllegalArgumentException("Pokemon(): Level must be greater than 0 [" + lvl + "]");
        }
        basis = pok;
        attacks = new Attack[Constants.MAX_ATTACK_COUNT];
        init();
        setLevel(lvl);
        kp = getMaxKp();
    }

    private void init()
    {
        name = basis.toString();
        this.lvl = -1;
        xp = 0;
        benXp = basis.DELTAXP;
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

    private void setLevel(int lvl)
    {
        this.benXp = basis.DELTAXP * lvl;
        LinkedList<Attack> learnable = new LinkedList<>();
        for (int i = this.lvl + 1; i <= lvl; i++)
        {
            for (int j = 0; j < basis.learnLvls.length; j++)
            {
                if (basis.learnLvls[j] == i)
                {
                    learnable.add(new Attack(basis.learnAtts[j]));
                }
            }
        }
        //random attack learn
        for (int i = 0; i < attacks.length; i++)
        {
            if (learnable.size() > 0)
            {
                if (attacks[i] == null)
                {
                    int index = (int) (Math.random() * learnable.size());
                    attacks[i] = learnable.get(index);
                    learnable.remove(index);
                }
            }
            else
            {
                break;
            }
        }
        this.lvl = lvl;
        calculateStats();
    }

    /**
     * Increments the level of this Pokemon by one, sets experience points to
     * zero, increases needed xp to next level, learns Attacks if possible and
     * calculates new stat values. Will also prompt decision by user if needed.
     * So be careful where this function is called!
     */
    public final void lvlUp()
    {
        xp = 0;
        benXp += basis.DELTAXP;
        lvl++;
        int[] oldStats = new int[]
        {
            getMaxKp(),
            getStateValue(Pokemon.Stat.ATK),
            getStateValue(Pokemon.Stat.VERT),
            getStateValue(Pokemon.Stat.SPEZ_ATK),
            getStateValue(Pokemon.Stat.SPEZ_VERT),
            getStateValue(Pokemon.Stat.INIT)
        };
        calculateStats();
        IO.println(this + " " + IO.translate("LVL_UP") + " " + lvl, IO.MessageType.IMPORTANT);
        IO.println("KP steigen um " + (getMaxKp() - oldStats[0]), IO.MessageType.IMPORTANT);
        IO.println("ATK steigt um " + (getStateValue(Pokemon.Stat.ATK) - oldStats[1]), IO.MessageType.IMPORTANT);
        IO.println("VERT steigt um " + (getStateValue(Pokemon.Stat.VERT) - oldStats[2]), IO.MessageType.IMPORTANT);
        IO.println("SPEZ ATK steigt um " + (getStateValue(Pokemon.Stat.SPEZ_ATK) - oldStats[3]), IO.MessageType.IMPORTANT);
        IO.println("SPEZ VERT steigt um " + (getStateValue(Pokemon.Stat.SPEZ_VERT) - oldStats[4]), IO.MessageType.IMPORTANT);
        IO.println("INIT steigt um " + (getStateValue(Pokemon.Stat.INIT) - oldStats[5]), IO.MessageType.IMPORTANT);

        for (int i = 0; i < basis.learnLvls.length; i++)
        {
            if (basis.learnLvls[i] == lvl)
            {
                learnAttack(new Attack(basis.learnAtts[i]));
            }
        }
    }

    private void calculateStats()
    {
        double part = 1.0 * kp / getMaxKp();
        for (Stat stat : Stat.values())
        {
            if (stat == Stat.KP)
            {
                setStateValue(stat, (int) ((2 * basis.getStateValue(stat) + DVs[stat.ordinal()] + FPs[stat.ordinal()] / 4 + 100) * lvl / 100.0 + 10));
            }
            else
            {
                setStateValue(stat, (int) (((2.0 * basis.getStateValue(stat) + DVs[stat.ordinal()] + FPs[stat.ordinal()] / 4.0) * lvl / 100.0 + 5) * wesen.getFactor(stat)));
            }
            // Für das Wesen muss bei einer positiven Auswirkung auf den Statuswert 1,1 und bei einer negativen Auswirkung 0,9 eingesetzt werden, sonst 1
        }
        kp = (int) (getMaxKp() * part);
    }

    /**
     * Adds a given amount of effort value to the specified Stat type. The total
     * amount of FP is limited by Constants.FP_MAX_TOTAL and the number of
     * effort values per Stat type by Constants.FP_MAX_STAT.
     *
     * @param stat Stat type
     * @param n number of effort values
     * @return effectively added effort values.
     */
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

    /**
     * Retrieve effort value of this Pokemon corresponding to the given Stat
     * type.
     *
     * @param stat Stat type
     * @return Effort value of the stat, or 0 if the given stat is invalid
     */
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
    public final int getMaxKp()
    {
        return getStateValue(Stat.KP);
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
        if (val == Stat.KP)
        {
            return STATS[val.ordinal()];
        }
        return boosts[val.ordinal() - Stat.ATK.ordinal()].calc(STATS[val.ordinal()]);
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

    public Wesen getWesen()
    {
        return wesen;
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
        return kp == getMaxKp() && status == Status.OK;
    }

    /**
     * Completely heals this Pokemon. Sets KP and AP values to maximum AND set
     * status to Status.OK.
     */
    public void heal()
    {
        kp = getMaxKp();
        setStatus(Status.OK);
        for (Attack a : attacks)
        {
            if (a != null)
            {
                a.setAP(a.BASE.AP);
            }
        }
    }

    /**
     * Restores the given amount of KP of this Pokemon. If the Pokemon is KO it
     * will be revived.
     *
     * @param numKp number of max restored kp.
     * @return actually restored kp.
     *
     */
    public int heal(int numKp)
    {
        if (status == Status.BSG && numKp > 0)
        {
            status = Status.OK;
        }
        if (kp + numKp > getMaxKp())
        {
            numKp = getMaxKp() - kp;
        }
        kp += numKp;
        return numKp;
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
        int index = val.ordinal() - Stat.ATK.ordinal();
        ValueFactor old = boosts[index];
        boosts[index] = old.boost(steps);
        return old != boosts[index];
    }

    /**
     * Retrieve an array containing all known Attacks.
     *
     * @return The array of Attacks known by this Pokemon
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

    /**
     * Sets the given Attack at the next free position in the array if it is not
     * already full. Otherwise a Decision to replace an Attack will be prompted
     * to the user, so use this function carefully! The array can be accessed
     * directly by getAttacks() instead.
     *
     * @param a Attack to add.
     * @return true if the Attack was added to the array, false if not.
     */
    public boolean learnAttack(Attack a)
    {
        for (int i = 0; i < attacks.length; i++)
        {
            if (attacks[i] == null)
            {
                attacks[i] = a;
                IO.println(this + " " + IO.translate("LEARNS") + " " + a, IO.MessageType.IMPORTANT);
                return true;
            }
        }
        IO.println(this + " versucht " + a + " zu lernen. Aber " + this + " kann nicht mehr als " + attacks.length + " Attacken kennen.", IO.MessageType.IMPORTANT);
        IO.println("Soll eine andere Attacke zugunsten von " + a + " vergessen werden?", IO.MessageType.IMPORTANT);
        // prompt decision

        IO.promptDecision(Decision.YES_NO, (int index) ->
        {
            final DecisionCallback innerCallback = new DecisionCallback()
            {
                @Override
                public void decisionEntered(int index)
                {
                    IO.println(name + " hat " + attacks[index] + " vergessen.", IO.MessageType.IMPORTANT);
                    IO.println(name + " " + IO.translate("LEARNS") + " " + a, IO.MessageType.IMPORTANT);
                    attacks[index] = a;
                }
            };
            if (index == 0)
            {
                IO.println("Welche Attacke soll vergessen werden?", IO.MessageType.IMPORTANT);
                IO.println("", IO.MessageType.IMPORTANT);
                IO.promptDecision(getAttackDecision(), innerCallback);
            }
            else
            {
                IO.println(name + " hat " + a + " nicht erlernt.", IO.MessageType.IMPORTANT);
            }
        });
        return false;
    }

    public Decision getAttackDecision()
    {
        String[] texts = new String[attacks.length];
        for (int i = 0; i < texts.length; i++)
        {
            if (attacks[i] != null)
            {
                texts[i] = attacks[i].BASE.NAME;
            }
            else
            {
                texts[i] = "DEFAULT_TEXT";
            }
        }
        return new Decision(Constants.OUTPUT_BOUNDS, attacks.length / 2, attacks.length / 2, texts);
    }

    public void evolve(PokemonBasis next)
    {
        if (this.name.equals(basis.toString()))
        {
            this.name = next.toString();
        }
        this.basis = next;
        int[] oldStats = new int[]
        {
            getMaxKp(),
            getStateValue(Pokemon.Stat.ATK),
            getStateValue(Pokemon.Stat.VERT),
            getStateValue(Pokemon.Stat.SPEZ_ATK),
            getStateValue(Pokemon.Stat.SPEZ_VERT),
            getStateValue(Pokemon.Stat.INIT)
        };
        calculateStats();
        IO.println("KP steigen um " + (getMaxKp() - oldStats[0]), IO.MessageType.DEBUG);
        IO.println("ATK steigt um " + (getStateValue(Pokemon.Stat.ATK) - oldStats[1]), IO.MessageType.DEBUG);
        IO.println("VERT steigt um " + (getStateValue(Pokemon.Stat.VERT) - oldStats[2]), IO.MessageType.DEBUG);
        IO.println("SPEZ ATK steigt um " + (getStateValue(Pokemon.Stat.SPEZ_ATK) - oldStats[3]), IO.MessageType.DEBUG);
        IO.println("SPEZ VERT steigt um " + (getStateValue(Pokemon.Stat.SPEZ_VERT) - oldStats[4]), IO.MessageType.DEBUG);
        IO.println("INIT steigt um " + (getStateValue(Pokemon.Stat.INIT) - oldStats[5]), IO.MessageType.DEBUG);
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
        for (Stat s : Stat.values())
        {
            values.add(s.name(), getStateValue(s));
        }
        values.add("gena", gena);
        values.add("flu", flu);
        for (int i = 1; i < Stat.values().length; i++)
        {
            values.add(Stat.values()[i].name() + "_factor", boosts[i - 1]);
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
