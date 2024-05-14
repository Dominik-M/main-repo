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
package utils;

import graphic.EvolutionPanel;
import graphic.MainFrame;
import graphic.MainPanel;
import pokemon.Ability;
import pokemon.Pokemon;
import pokemon.PokemonBasis;
import pokemon.Typ;
import pokemon.attacks.Attack;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 26.03.2016
 */
public class Utilities
{

    /**
     * Rounds a double value at the given decimal place.
     *
     * @param d The double value to round.
     * @param n Number of decimal places.
     * @return String representation of the rounded number.
     */
    public static String round(double d, int n)
    {
        if (n <= 0)
        {
            return String.valueOf((int) d);
        }
        String s = String.valueOf(d);
        int i = s.indexOf(".") + 1;
        if (i + n < s.length())
        {
            return s.substring(0, i + n);
        }
        else
        {
            while (s.length() < i + n)
            {
                s += "0";
            }
            return s;
        }
    }

    /**
     * Converts an angle in radiant to degrees.
     *
     * @param rad The angle in radiant
     * @return the angle in degrees
     */
    public static double toDeg(double rad)
    {
        return rad * 180 / Math.PI;
    }

    /**
     * Converts an angle in degrees to radiant.
     *
     * @param degree The angle in degree
     * @return the angle in radiant
     */
    public static double toRad(double degree)
    {
        return degree / 180 * Math.PI;
    }

    /**
     * Inverts the given direction, i.e. rotation of 180 degrees. The direction
     * must be one of the four direction constants: Either DIRECTION_UP,
     * DIRECTION_DOWN, DIRECTION_LEFT or DIRECTION_RIGHT.
     *
     * @param direction a direction constant
     * @return the inverted direction or DIRECTION_INVALID if no valid direction
     * was given.
     */
    public static int invertDirection(int direction)
    {
        switch (direction)
        {
            case Constants.DIRECTION_UP:
                return Constants.DIRECTION_DOWN;
            case Constants.DIRECTION_RIGHT:
                return Constants.DIRECTION_LEFT;
            case Constants.DIRECTION_LEFT:
                return Constants.DIRECTION_RIGHT;
            case Constants.DIRECTION_DOWN:
                return Constants.DIRECTION_UP;
            default:
                return Constants.DIRECTION_INVALID;
        }
    }

    /**
     * Fetch and multiply Typ factor for damage calculation if an Attack with
     * the given Typ attTyp is used on a target with the given Typs in
     * targetTyps.
     *
     * @param attTyp Typ of the attack used
     * @param targetTyps Typs of the target
     * @return resulting damage factor
     */
    public static double getTypFactor(Typ attTyp, Typ... targetTyps)
    {
        double factor = 1;
        for (Typ targetTyp : targetTyps)
        {
            factor *= Constants.TYP_FACTOR_TABLE[Constants.NUMBER_OF_TYPS * targetTyp.INDEX + attTyp.INDEX];
        }
        return factor;
    }

    /**
     * Calculates if a "random" event with the given probability occurs.
     * Actually generates a pseudo random number X between 0 and 100 and returns
     * X less than percent.
     *
     * @param percent
     * @return
     */
    public static boolean randomEvent(int percent)
    {
        return ((int) (Math.random() * 100)) < percent;
    }

    /**
     * Calculates damage dealt of the Attack att by Pokemon user to Pokemon
     * target (inclusive output prints) following the official formular (see
     * http://www.pokewiki.de/Schaden). First thougt about calling the Pokemon
     * parameters source and dest or Tx and Rx...
     *
     * @param att The Attack used by the attacking Pokemon
     * @param user The attacking Pokemon
     * @param target The defending Pokemon
     * @return damage which will be dealt to target
     */
    public static int calculateDamage(Attack att, Pokemon user, Pokemon target)
    {
        // before calculating, check the types for immunity
        double typfactor = getTypFactor(att.BASE.TYP, target.getBasis().getTyp1(), target.getBasis().getTyp2());
        if ((typfactor == 0)
                || (att.BASE.TYP == Typ.BODEN && target.getBasis().ABILITY == Ability.SCHWEBE)
                || ((target.getBasis().ABILITY == Ability.WUNDERWACHE) && ((int) (typfactor + 0.5)) <= 1))
        {
            IO.println(IO.translate("NO_EFFECT_ON") + target.toString(), IO.MessageType.IMPORTANT);
            return 0;
        }
        if (typfactor > 1.1)
        {
            IO.println(IO.translate("VERY_EFFECTIVE"), IO.MessageType.IMPORTANT);
        }
        else if (typfactor < 0.9)
        {
            IO.println(IO.translate("NOT_EFFECTIVE"), IO.MessageType.IMPORTANT);
        }
        // base damage
        int damage = att.getDmg();
        // ability factors
        // Notdünger: Planzen Attacken unter 1/3 der maxKP um 50% stärker
        if ((user.getBasis().ABILITY == Ability.NOTDÜNGER) && (user.getKp() < user.getMaxKp() / 3))
        {
            damage += damage / 2;
        }
        // level factor
        damage *= user.getLvl() * 2 / 5 + 2;
        // state value factor
        damage = 2 + damage * (att.isPhys() ? user.getStateValue(Pokemon.Stat.ATK) : user.getStateValue(Pokemon.Stat.SPEZ_ATK))
                / 50 / (att.isPhys() ? target.getStateValue(Pokemon.Stat.VERT) : target.getStateValue(Pokemon.Stat.SPEZ_VERT));
        // critical hit?
        if ((utils.Utilities.randomEvent(user.getBasis().getStateValue(Pokemon.Stat.INIT) / 5)))
        {
            damage *= 2;
            IO.println(IO.translate("CRITICAL_HIT"), IO.MessageType.IMPORTANT);
        }
        // Z-Factor
        damage = damage * 100 / (100 - (int) (Math.random() * 15));
        // STAB factor
        damage = (int) (damage * ((att.BASE.TYP == user.getBasis().getTyp1() || att.BASE.TYP == user.getBasis().getTyp2()) ? 1.5 : 1));
        // typfactor
        damage = (int) (damage * typfactor);
        // damage is at least 1
        return damage == 0 ? 1 : damage;
    }

    public static int calculateCatchPropability(int rate, int targetLvl, int rareness)
    {
        //TODO implement catchrate calculation
        return rate / targetLvl;
    }

    public static void evolve(MainPanel parent, Pokemon pok, PokemonBasis next)
    {
        MainFrame.FRAME.setMainPanel(new EvolutionPanel(parent, pok, next));
    }
}
