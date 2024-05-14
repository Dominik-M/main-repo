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
import utils.Text;

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
        ATK_DOWN(Text.ATK_DOWN, Text.ATK_DOWN_APPLIED),
        BURN(Text.BURN, Text.BURN_DAMAGE);

        public final Text DESCR, MSG;

        private EffectBase(Text DESCR, Text MSG)
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
    public final int P;
    public final boolean SELF;

    Effect(EffectBase type, boolean self)
    {
        this(type, self, 100);
    }

    Effect(EffectBase type, boolean self, int p)
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
    }

    @Override
    public String toString()
    {
        if (P < 100)
        {
            return TYPE.toString() + " " + P + "%";
        }
        else
        {
            return TYPE.toString();
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
                    target.boostStateValue(Pokemon.Stat.ATK, -1);
                    IO.println(target + "s " + this.TYPE.MSG.toString(), IO.MessageType.IMPORTANT);
                    break;
                case BURN:
                    if (target.getBasis().ABILITY == Ability.AQUAHÜLLE)
                    {
                        IO.println(target.toString() + "s " + Text.ABILITY_AQUAHÜLLE.toString() + " " + Text.ABILITY_TOOLTIP_AQUAHÜLLE, IO.MessageType.IMPORTANT);
                    }
                    else if (target.isOK())
                    {
                        target.setStatus(Status.BRT);
                        IO.println(target + this.TYPE.MSG.toString(), IO.MessageType.IMPORTANT);
                    }
                    break;
                default:
                    IO.println(this.TYPE.DESCR.toString(), IO.MessageType.IMPORTANT);
                    break;
            }
        }
    }
}
