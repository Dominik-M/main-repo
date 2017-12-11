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
package pokemon.attacks;

import pokemon.Ability;
import pokemon.Pokemon;
import pokemon.Status;
import utils.IO;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 * Created 06.08.2016
 */
public class Effect implements java.io.Serializable
{

    public enum EffectBase
    {
        ATK_DOWN("ATK_DOWN", "ATK_DOWN_APPLIED"),
        VERT_DOWN("VERT_DOWN", "VERT_DOWN_APPLIED"),
        SPEZ_ATK_DOWN("SPEZ_ATK_DOWN", "SPEZ_ATK_DOWN_APPLIED"),
        SPEZ_VERT_DOWN("SPEZ_VERT_DOWN", "SPEZ_VERT_DOWN_APPLIED"),
        INIT_DOWN("INIT_DOWN", "INIT_DOWN_APPLIED"),
        ATK_UP("ATK_UP", "ATK_UP_APPLIED"),
        VERT_UP("VERT_UP", "VERT_UP_APPLIED"),
        SPEZ_ATK_UP("SPEZ_ATK_UP", "SPEZ_ATK_UP_APPLIED"),
        SPEZ_VERT_UP("SPEZ_VERT_UP", "SPEZ_VERT_UP_APPLIED"),
        INIT_UP("INIT_UP", "INIT_UP_APPLIED"),
        BURN("BURN", "BURN_DAMAGE"),
        FREEZE("FREEZE", "FROZEN"),
        POISON("POISON", "POISON_DAMAGE"),
        PARALYSE("PARALYSE", "PARALYSED");

        public final String DESCR, MSG;

        private EffectBase(String DESCR, String MSG)
        {
            this.DESCR = DESCR;
            this.MSG = MSG;
        }

        @Override
        public String toString()
        {
            return name();
        }
    }

    //TODO implement effects
    public final EffectBase TYPE;
    public final int P, VALUE;
    public final boolean SELF;

    Effect(EffectBase type, boolean self)
    {
        this(type, self, 1, 100);
    }

    Effect(EffectBase type, boolean self, int value)
    {
        this(type, self, value, 100);
    }

    Effect(EffectBase type, boolean self, int value, int p)
    {
        if (type == null)
        {
            throw new java.lang.IllegalArgumentException("Effect type must not be null!");
        }
        if (p <= 0 || p > 100)
        {
            throw new java.lang.IllegalArgumentException("Effect probability invalid! " + p);
        }
        TYPE = type;
        SELF = self;
        P = p;
        VALUE = value;
    }

    @Override
    public String toString()
    {
        if (P < 100)
        {
            return IO.translate(TYPE.DESCR) + " (" + P + "%)";
        }
        else
        {
            return IO.translate(TYPE.DESCR);
        }
    }

    /**
     * Applys this effect on the given target Pokemon (during fight).
     *
     * @param target Pokemon which is affected by this effect.
     */
    public void apply(Pokemon target)
    {
        if (target != null)
        {
            switch (this.TYPE)
            {
                case ATK_DOWN:
                    if (target.boostStateValue(Pokemon.Stat.ATK, -1 * VALUE))
                    {
                        IO.println(target + "s " + IO.translate(TYPE.MSG), IO.MessageType.IMPORTANT);
                    }
                    break;
                case VERT_DOWN:
                    if (target.boostStateValue(Pokemon.Stat.VERT, -1 * VALUE))
                    {
                        IO.println(target + "s " + IO.translate(TYPE.MSG), IO.MessageType.IMPORTANT);
                    }
                    break;
                case SPEZ_ATK_DOWN:
                    if (target.boostStateValue(Pokemon.Stat.SPEZ_ATK, -1 * VALUE))
                    {
                        IO.println(target + "s " + IO.translate(TYPE.MSG), IO.MessageType.IMPORTANT);
                    }
                    break;
                case SPEZ_VERT_DOWN:
                    if (target.boostStateValue(Pokemon.Stat.SPEZ_VERT, -1 * VALUE))
                    {
                        IO.println(target + "s " + IO.translate(TYPE.MSG), IO.MessageType.IMPORTANT);
                    }
                    break;
                case INIT_DOWN:
                    if (target.boostStateValue(Pokemon.Stat.INIT, -1 * VALUE))
                    {
                        IO.println(target + "s " + IO.translate(TYPE.MSG), IO.MessageType.IMPORTANT);
                    }
                    break;
                case ATK_UP:
                    if (target.boostStateValue(Pokemon.Stat.ATK, VALUE))
                    {
                        IO.println(target + "s " + IO.translate(TYPE.MSG), IO.MessageType.IMPORTANT);
                    }
                    break;
                case VERT_UP:
                    if (target.boostStateValue(Pokemon.Stat.VERT, VALUE))
                    {
                        IO.println(target + "s " + IO.translate(TYPE.MSG), IO.MessageType.IMPORTANT);
                    }
                    break;
                case SPEZ_ATK_UP:
                    if (target.boostStateValue(Pokemon.Stat.SPEZ_ATK, VALUE))
                    {
                        IO.println(target + "s " + IO.translate(TYPE.MSG), IO.MessageType.IMPORTANT);
                    }
                    break;
                case SPEZ_VERT_UP:
                    if (target.boostStateValue(Pokemon.Stat.SPEZ_VERT, VALUE))
                    {
                        IO.println(target + "s " + IO.translate(TYPE.MSG), IO.MessageType.IMPORTANT);
                    }
                    break;
                case INIT_UP:
                    if (target.boostStateValue(Pokemon.Stat.VERT, VALUE))
                    {
                        IO.println(target + "s " + IO.translate(TYPE.MSG), IO.MessageType.IMPORTANT);
                    }
                    break;
                case BURN:
                    if (target.getBasis().ABILITY == Ability.AQUAHÜLLE)
                    {
                        IO.println(target.toString() + "s " + IO.translate("ABILITY_AQUAHÜLLE") + " " + IO.translate("ABILITY_TOOLTIP_AQUAHÜLLE"), IO.MessageType.IMPORTANT);
                    }
                    else if (target.getStatus() == Status.OK)
                    {
                        target.setStatus(Status.BRT);
                        IO.println(target + IO.translate(TYPE.MSG), IO.MessageType.IMPORTANT);
                    }
                    break;
                case FREEZE:
                    if (target.getBasis().ABILITY == Ability.FEUERPANZER)
                    {
                        IO.println(target.toString() + "s " + IO.translate("ABILITY_FEUERPANZER") + " " + IO.translate("ABILITY_TOOLTIP_FEUERPANZER"), IO.MessageType.IMPORTANT);
                    }
                    else if (target.getStatus() == Status.OK)
                    {
                        target.setStatus(Status.GFR);
                        IO.println(target + IO.translate(TYPE.MSG), IO.MessageType.IMPORTANT);
                    }
                    break;
                case POISON:
                    if (target.getBasis().ABILITY == Ability.IMMUNITÄT)
                    {
                        IO.println(target.toString() + "s " + IO.translate("ABILITY_IMMUNITÄT") + " " + IO.translate("ABILITY_TOOLTIP_IMMUNITÄT"), IO.MessageType.IMPORTANT);
                    }
                    else if (target.getStatus() == Status.OK)
                    {
                        target.setStatus(Status.GFT);
                        IO.println(target + IO.translate(TYPE.MSG), IO.MessageType.IMPORTANT);
                    }
                    break;
                case PARALYSE:
                    if (target.getBasis().ABILITY == Ability.FLEXIBILITÄT)
                    {
                        IO.println(target.toString() + "s " + IO.translate("ABILITY_FLEXIBILITÄT") + " " + IO.translate("ABILITY_TOOLTIP_FLEXIBILITÄT"), IO.MessageType.IMPORTANT);
                    }
                    else if (target.getStatus() == Status.OK)
                    {
                        target.setStatus(Status.PAR);
                        IO.println(target + IO.translate(TYPE.MSG), IO.MessageType.IMPORTANT);
                    }
                    break;
                default:
                    IO.println(IO.translate(TYPE.MSG), IO.MessageType.IMPORTANT);
                    IO.println("Warning: Unknown effect type " + TYPE, IO.MessageType.ERROR);
                    break;
            }
        }
    }
}
