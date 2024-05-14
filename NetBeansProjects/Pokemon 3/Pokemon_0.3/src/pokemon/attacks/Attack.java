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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;
import pokemon.Typ;
import utils.Dictionary;
import utils.IO;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 * Created 06.08.2016
 */
public class Attack extends utils.SerializableReflectObject
{

    /**
     *
     */
    public enum AttackBase
    {

        TACKLE("TACKLE", "TACKLE_DESCR", 35, 40, 90, Typ.NORMAL, true, false),
        KRATZER("KRATZER", "KRATZER_DESCR", 35, 40, 100, Typ.NORMAL, true, false),
        GLUT("GLUT", "GLUT_DESCR", 25, 40, 100, Typ.FEUER, false, false, new Effect(Effect.EffectBase.BURN, false, 1, 10)),
        RUTENSCHLAG("RUTENSCHLAG", "RUTENSCHLAG_DESCR", 30, 0, 100, Typ.NORMAL, true, false, new Effect(Effect.EffectBase.VERT_DOWN, false)),
        HEULER("HEULER", "HEULER_DESCR", 40, 0, 100, Typ.NORMAL, true, false, new Effect(Effect.EffectBase.ATK_DOWN, false)),
        BLUBBER("BLUBBER", "BLUBBER_DESCR", 30, 20, 100, Typ.WASSER, false, false, new Effect(Effect.EffectBase.INIT_DOWN, false, 1, 50));
        public final String NAME, DESCR;//Name and description references
        public final int AP, DMG, GENA; // attack points and basic attack damage
        public final Typ TYP; // attack type
        public final boolean PHYS, SELF; // physical or special attack and aim on self or opponent
        public final Effect[] EFFECTS;

        AttackBase(String name, String descr, int ap, int dmg, int gena, Typ typ, boolean phys, boolean self, Effect... effects)
        {
            NAME = name;
            DESCR = descr;
            AP = ap;
            DMG = dmg;
            GENA = gena;
            TYP = typ;
            PHYS = phys;
            SELF = self;
            if (effects != null && effects.length > 0)
            {
                EFFECTS = new Effect[effects.length];
                System.arraycopy(effects, 0, EFFECTS, 0, effects.length);
            }
            else
            {
                EFFECTS = new Effect[0];
            }
        }

        public Attack getInstance()
        {
            return new Attack(this);
        }
    }

    //TODO implement attacks
    private int ap;

    /**
     *
     */
    public final AttackBase BASE;

    /**
     *
     * @param basis
     */
    public Attack(AttackBase basis)
    {
        BASE = basis;
        init();
    }

    private void init()
    {
        ap = BASE.AP;
    }

    @Override
    public String toString()
    {
        return IO.translate(BASE.NAME);
    }

    /**
     * Get the Attack Points left.
     *
     * @return Attack Points
     */
    public int getAP()
    {
        return ap;
    }

    /**
     *
     * @param ap
     */
    public void setAP(int ap)
    {
        this.ap = ap;
    }

    @Override
    public Dictionary<String, Object> getAttributes()
    {
        Dictionary<String, Object> values = new Dictionary<>();
        for (Field f : this.getClass().getDeclaredFields())
        {
            try
            {
                if (!Modifier.isStatic(f.getModifiers()))
                {
                    values.add(f.getName(), f.get(this));
                }
            } catch (IllegalArgumentException | IllegalAccessException ex)
            {
                Logger.getLogger(Attack.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Field f : this.BASE.getClass().getDeclaredFields())
        {
            try
            {
                if (!Modifier.isStatic(f.getModifiers()))
                {
                    values.add(f.getName(), f.get(BASE));
                }
            } catch (IllegalArgumentException | IllegalAccessException ex)
            {
                Logger.getLogger(Attack.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return values;
    }

    /**
     * Returns the Base damage of this attack.
     *
     * @return
     */
    public int getDmg()
    {
        return BASE.DMG;
    }

    public boolean isPhys()
    {
        return BASE.PHYS;
    }
}
