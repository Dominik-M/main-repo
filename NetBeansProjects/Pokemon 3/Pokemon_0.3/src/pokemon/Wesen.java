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

import utils.IO;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public enum Wesen
{

    /**
     * "Geringerer Angriff, starke Verteidigung".
     */
    TAPFER("WESEN_NAME_TAPFER", "WESEN_TOOLTIP_TAPFER", -1, 1, 0, 0, 0),
    /**
     * "Weniger Angriff, höherer Spezialangriff".
     */
    FORSCH("WESEN_NAME_FORSCH", "WESEN_TOOLTIP_FORSCH", -1, 0, 1, 0, 0),
    /**
     * "Wenig Angriff, höhere Spezialverteidigung".
     */
    SCHEU("WESEN_NAME_SCHEU", "WESEN_TOOLTIP_SCHEU", -1, 0, 0, 1, 0),
    /**
     * "Schwacher Angriff, hohe Initiative".
     */
    FLINK("WESEN_NAME_FLINK", "WESEN_TOOLTIP_FLINK", -1, 0, 0, 0, 1),
    /**
     * "Wenig Verteidigung, höhere Angriffswerte".
     */
    BRUTAL("WESEN_NAME_BRUTAL", "WESEN_TOOLTIP_BRUTAL", 1, -1, 0, 0, 0),
    /**
     * "Schwächere Verteidigung, hoher Spezialangriff".
     */
    NAIV("WESEN_NAME_NAIV", "WESEN_TOOLTIP_NAIV", 0, -1, 1, 0, 0),
    /**
     * "Geringe Verteidigung, hohe Spezialverteidigung".
     */
    MUTIG("WESEN_NAME_MUTIG", "WESEN_TOOLTIP_MUTIG", 0, -1, 0, 1, 0),
    /**
     * "Weniger Verteidigung, hohe Initiative".
     */
    EIFRIG("WESEN_NAME_EIFRIG", "WESEN_TOOLTIP_EIFRIG", 0, -1, 0, 0, 1),
    /**
     * "Weniger Spezialangriff, stärkerer Angriff".
     */
    HART("WESEN_NAME_HART", "WESEN_TOOLTIP_HART", 1, 0, -1, 0, 0),
    /**
     * "Geringer Spezialangriff, hohe Verteidigung".
     */
    ROBUST("WESEN_NAME_ROBUST", "WESEN_TOOLTIP_ROBUST", 0, 1, -1, 0, 0),
    /**
     * "Wenig Spezialangriff, hohe Spezialverteidigung".
     */
    ZAGHAFT("WESEN_NAME_ZAGHAFT", "WESEN_TOOLTIP_ZAGHAFT", 0, 0, -1, 1, 0),
    /**
     * "Geringerer Spezialangriff, höhere Initiative".
     */
    FROH("WESEN_NAME_FROH", "WESEN_TOOLTIP_FROH", 0, 0, -1, 0, 1),
    /**
     * "Geringere Spezialverteidigung, hoher Angriff".
     */
    HITZIG("WESEN_NAME_HITZIG", "WESEN_TOOLTIP_HITZIG", 1, 0, 0, -1, 0),
    /**
     * "Wenig Spezialverteidigung, hohe Verteidigung".
     */
    PFIFFIG("WESEN_NAME_PFIFFIG", "WESEN_TOOLTIP_PFIFFIG", 0, 1, 0, -1, 0),
    /**
     * "Geringe Spezialverteidigung, hoher Spezialangriff".
     */
    FIES("WESEN_NAME_FIES", "WESEN_TOOLTIP_FIES", 0, 0, 1, -1, 0),
    /**
     * "Wenig Spezialverteidigung, höhere Initiative".
     */
    LOCKER("WESEN_NAME_LOCKER", "WESEN_TOOLTIP_LOCKER", 0, 0, 0, -1, 1),
    /**
     * "Geringere Initiative, höherer Angriff".
     */
    ERNST("WESEN_NAME_ERNST", "WESEN_TOOLTIP_ERNST", 1, 0, 0, 0, -1),
    /**
     * "Wenig Initiative, starke Verteidigung".
     */
    KAUZIG("WESEN_NAME_KAUZIG", "WESEN_TOOLTIP_KAUZIG", 0, 1, 0, 0, -1),
    /**
     * "Wenig Initiative, starker Spezialangriff".
     */
    STUR("WESEN_NAME_STUR", "WESEN_TOOLTIP_STUR", 0, 0, 1, 0, -1),
    /**
     * "Geringe Initiative, hohe Spezialverteidigung".
     */
    RUHIG("WESEN_NAME_RUHIG", "WESEN_TOOLTIP_RUHIG", -1, 0, 0, 1, 0),
    /**
     * "Neutral".
     */
    SANFT("WESEN_NAME_SANFT", "WESEN_TOOLTIP_SANFT", 0, 0, 0, 0, 0),
    /**
     * "Neutral".
     */
    MILD("WESEN_NAME_MILD", "WESEN_TOOLTIP_MILD", 0, 0, 0, 0, 0),
    /**
     * "Neutral".
     */
    SACHT("WESEN_NAME_SACHT", "WESEN_TOOLTIP_SACHT", 0, 0, 0, 0, 0),
    /**
     * "Neutral".
     */
    STILL("WESEN_NAME_STILL", "WESEN_TOOLTIP_STILL", 0, 0, 0, 0, 0),
    /**
     * "Hohe Spezialwerte".
     */
    KÜHN("WESEN_NAME_KÜHN", "WESEN_TOOLTIP_KÜHN", 0, 0, 1, 1, 0),
    /**
     * "Besonders hohe Werte".
     */
    STOLZ("WESEN_NAME_STOLZ", "WESEN_TOOLTIP_STOLZ", 1, 1, 1, 1, 1);

    public final String TOOLTIP;
    public final String NAME;
    private final int[] STATS;

    private Wesen(String name, String descr, int atkEffect, int vertEffect, int spezAtkEffect, int spezVertEffect, int initEffect)
    {
        this.NAME = name;
        this.TOOLTIP = descr;
        STATS = new int[]
        {
            atkEffect, vertEffect, spezAtkEffect, spezVertEffect, initEffect
        };
    }

    @Override
    public String toString()
    {
        return IO.translate(NAME);
    }

    public double getFactor(Pokemon.Stat stat)
    {
        int val = STATS[stat.ordinal() - Pokemon.Stat.ATK.ordinal()];
        if (val < 0)
        {
            return 0.9;
        }
        if (val > 0)
        {
            return 1.1;
        }
        return 1;
    }

    /**
     *
     * @return
     */
    public static Wesen gibZufallWesen()
    {
        int index = (int) (Math.random() * values().length);
        return values()[index];
    }
}
